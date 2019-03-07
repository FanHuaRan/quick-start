package com.github.fhr.basic.limiter.guava.extension;

import com.google.common.math.LongMath;

import java.util.concurrent.TimeUnit;

import static java.lang.Math.min;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by Huaran Fan on 2019/3/6
 *
 * @description
 */
public abstract class SmoothRateLimiter extends RateLimiter {

    static final class SmoothWarmingUp extends SmoothRateLimiter {
        private final long warmupPeriodMicros;
        /**
         * The slope of the line from the stable interval (when permits == 0), to the cold interval
         * (when permits == maxPermits)
         */
        private double slope;

        private double thresholdPermits;
        private double coldFactor;

        SmoothWarmingUp(
                RateLimiter.SleepingStopwatch stopwatch, long warmupPeriod, TimeUnit timeUnit, double coldFactor) {
            super(stopwatch);
            this.warmupPeriodMicros = timeUnit.toMicros(warmupPeriod);
            this.coldFactor = coldFactor;
        }

        @Override
        void doSetRate(double permitsPerSecond, double stableIntervalMicros) {
            double oldMaxPermits = maxPermits;
            double coldIntervalMicros = stableIntervalMicros * coldFactor;
            thresholdPermits = 0.5 * warmupPeriodMicros / stableIntervalMicros;
            maxPermits =
                    thresholdPermits + 2.0 * warmupPeriodMicros / (stableIntervalMicros + coldIntervalMicros);
            slope = (coldIntervalMicros - stableIntervalMicros) / (maxPermits - thresholdPermits);
            if (oldMaxPermits == Double.POSITIVE_INFINITY) {
                // if we don't special-case this, we would get storedPermits == NaN, below
                storedPermits = 0.0;
            } else {
                storedPermits =
                        (oldMaxPermits == 0.0)
                                ? maxPermits // initial state is cold
                                : storedPermits * maxPermits / oldMaxPermits;
            }
        }

        @Override
        long storedPermitsToWaitTime(double storedPermits, double permitsToTake) {
            double availablePermitsAboveThreshold = storedPermits - thresholdPermits;
            long micros = 0;
            // measuring the integral on the right part of the function (the climbing line)
            if (availablePermitsAboveThreshold > 0.0) {
                double permitsAboveThresholdToTake = min(availablePermitsAboveThreshold, permitsToTake);
                // TODO(cpovirk): Figure out a good name for this variable.
                double length =
                        permitsToTime(availablePermitsAboveThreshold)
                                + permitsToTime(availablePermitsAboveThreshold - permitsAboveThresholdToTake);
                micros = (long) (permitsAboveThresholdToTake * length / 2.0);
                permitsToTake -= permitsAboveThresholdToTake;
            }
            // measuring the integral on the left part of the function (the horizontal line)
            micros += (long) (stableIntervalMicros * permitsToTake);
            return micros;
        }

        private double permitsToTime(double permits) {
            return stableIntervalMicros + permits * slope;
        }

        @Override
        double coolDownIntervalMicros() {
            return warmupPeriodMicros / maxPermits;
        }
    }

    /**
     * This implements a "bursty" RateLimiter, where storedPermits are translated to zero throttling.
     * The maximum number of permits that can be saved (when the RateLimiter is unused) is defined in
     * terms of time, in this sense: if a RateLimiter is 2qps, and this time is specified as 10
     * seconds, we can save up to 2 * 10 = 20 permits.
     */
    static final class SmoothBursty extends SmoothRateLimiter {
        /** The work (permits) of how many seconds can be saved up if this RateLimiter is unused? */
        final double maxBurstSeconds;

        SmoothBursty(SleepingStopwatch stopwatch, double maxBurstSeconds) {
            super(stopwatch);
            this.maxBurstSeconds = maxBurstSeconds;
        }

        @Override
        void doSetRate(double permitsPerSecond, double stableIntervalMicros) {
            double oldMaxPermits = this.maxPermits;
            maxPermits = maxBurstSeconds * permitsPerSecond;
            if (oldMaxPermits == Double.POSITIVE_INFINITY) {
                // if we don't special-case this, we would get storedPermits == NaN, below
                storedPermits = maxPermits;
            } else {
                storedPermits =
                        (oldMaxPermits == 0.0)
                                ? 0.0 // initial state
                                : storedPermits * maxPermits / oldMaxPermits;
            }
        }

        @Override
        long storedPermitsToWaitTime(double storedPermits, double permitsToTake) {
            return 0L;
        }

        @Override
        double coolDownIntervalMicros() {
            return stableIntervalMicros;
        }
    }

    /** The currently stored permits. */
    double storedPermits;

    /** The maximum number of stored permits. */
    double maxPermits;

    /**
     * The interval between two unit requests, at our stable rate. E.g., a stable rate of 5 permits
     * per second has a stable interval of 200ms.
     */
    double stableIntervalMicros;

    /**
     * The time when the next request (no matter its size) will be granted. After granting a request,
     * this is pushed further in the future. Large requests push this further than small requests.
     */
    private long nextFreeTicketMicros = 0L; // could be either in the past or future

    private SmoothRateLimiter(SleepingStopwatch stopwatch) {
        super(stopwatch);
    }

    @Override
    final void doSetRate(double permitsPerSecond, long nowMicros) {
        resync(nowMicros);
        double stableIntervalMicros = SECONDS.toMicros(1L) / permitsPerSecond;
        this.stableIntervalMicros = stableIntervalMicros;
        doSetRate(permitsPerSecond, stableIntervalMicros);
    }

    abstract void doSetRate(double permitsPerSecond, double stableIntervalMicros);

    @Override
    final double doGetRate() {
        return SECONDS.toMicros(1L) / stableIntervalMicros;
    }

    @Override
    final long queryEarliestAvailable(long nowMicros) {
        return nextFreeTicketMicros;
    }

    @Override
    final long reserveEarliestAvailable(int requiredPermits, long nowMicros) {
        resync(nowMicros);
        long returnValue = nextFreeTicketMicros;
        double storedPermitsToSpend = min(requiredPermits, this.storedPermits);
        double freshPermits = requiredPermits - storedPermitsToSpend;
        long waitMicros =
                storedPermitsToWaitTime(this.storedPermits, storedPermitsToSpend)
                        + (long) (freshPermits * stableIntervalMicros);

        this.nextFreeTicketMicros = LongMath.saturatedAdd(nextFreeTicketMicros, waitMicros);
        this.storedPermits -= storedPermitsToSpend;
        return returnValue;
    }

    /**
     * Translates a specified portion of our currently stored permits which we want to spend/acquire,
     * into a throttling time. Conceptually, this evaluates the integral of the underlying function we
     * use, for the range of [(storedPermits - permitsToTake), storedPermits].
     *
     * <p>This always holds: {@code 0 <= permitsToTake <= storedPermits}
     */
    abstract long storedPermitsToWaitTime(double storedPermits, double permitsToTake);

    /**
     * Returns the number of microseconds during cool down that we have to wait to get a new permit.
     */
    abstract double coolDownIntervalMicros();

    /** Updates {@code storedPermits} and {@code nextFreeTicketMicros} based on the current time. */
    void resync(long nowMicros) {
        // if nextFreeTicket is in the past, resync to now
        if (nowMicros > nextFreeTicketMicros) {
            double newPermits = (nowMicros - nextFreeTicketMicros) / coolDownIntervalMicros();
            storedPermits = min(maxPermits, storedPermits + newPermits);
            nextFreeTicketMicros = nowMicros;
        }
    }
}
