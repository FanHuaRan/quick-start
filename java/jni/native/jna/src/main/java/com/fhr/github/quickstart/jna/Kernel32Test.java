package com.fhr.github.quickstart.jna;

import com.sun.jna.Native;
import com.sun.jna.Structure;
import com.sun.jna.win32.StdCallLibrary;

/**
 * @author Fan Huaran
 * created on 2019/8/21
 * @description
 */
public class Kernel32Test {

    public interface Kernel32 extends StdCallLibrary {
        Kernel32 INSTANCE = (Kernel32) Native.load("kernel32", Kernel32.class);
        // Optional: wraps every call to the native library in a
        // synchronized block, limiting native calls to one at a time
        Kernel32 SYNC_INSTANCE = (Kernel32) Native.synchronizedLibrary(INSTANCE);

        void GetSystemTime(SYSTEMTIME result);
    }

    @Structure.FieldOrder({ "wYear", "wMonth", "wDayOfWeek", "wDay", "wHour", "wMinute", "wSecond", "wMilliseconds" })
    public static class SYSTEMTIME extends Structure {
        public short wYear;
        public short wMonth;
        public short wDayOfWeek;
        public short wDay;
        public short wHour;
        public short wMinute;
        public short wSecond;
        public short wMilliseconds;

        @Override
        public String toString() {
            return "SYSTEMTIME{" +
                    "wYear=" + wYear +
                    ", wMonth=" + wMonth +
                    ", wDayOfWeek=" + wDayOfWeek +
                    ", wDay=" + wDay +
                    ", wHour=" + wHour +
                    ", wMinute=" + wMinute +
                    ", wSecond=" + wSecond +
                    ", wMilliseconds=" + wMilliseconds +
                    '}';
        }
    }

    public static void main(String[] args) {
        SYSTEMTIME systemtime = new SYSTEMTIME();
        Kernel32.INSTANCE.GetSystemTime(systemtime);
        System.out.println(systemtime);
    }
}
