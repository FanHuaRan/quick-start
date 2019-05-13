package com.github.fhr.benchmark.redis.zrevrange;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Tuple;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author jian.zhang@dmall.com
 * @date 2018年11月13日
 */
public class RedisUtils {
    private JedisPool jedisPool;

    public String set(String key, String value, String nxxx, String expx, long time) {
        try (Jedis jedis = getJedis()) {
            return jedis.set(key, value, nxxx, expx, time);
        }
    }

    public String type(String key) {
        try (Jedis jedis = getJedis()) {
            return jedis.type(key);
        }
    }

    public Long ttl(String key) {
        try (Jedis jedis = getJedis()) {
            return jedis.ttl(key);
        }
    }

    public String get(String key) {
        try (Jedis jedis = getJedis()) {
            return jedis.get(key);
        }
    }

    public List<String> lrange(String key, long start, long end) {
        try (Jedis jedis = getJedis()) {
            return jedis.lrange(key, start, end);
        }
    }

    public Set<String> smembers(String key) {
        try (Jedis jedis = getJedis()) {
            return jedis.smembers(key);
        }
    }

    public Set<Tuple> zrangeWithScores(String key, long start, long end) {
        try (Jedis jedis = getJedis()) {
            return jedis.zrangeWithScores(key, start, end);
        }
    }

    public Long expire(String key, int seconds) {
        try (Jedis jedis = getJedis()) {
            return jedis.expire(key, seconds);
        }
    }

    public Long del(String key) {
        try (Jedis jedis = getJedis()) {
            return jedis.del(key);
        }
    }

    public String set(String key, String value) {
        try (Jedis jedis = getJedis()) {
            return jedis.set(key, value);
        }
    }

    public String setex(String key, int seconds, String value) {
        try (Jedis jedis = getJedis()) {
            return jedis.setex(key, seconds, value);
        }
    }

    public String hmset(String key, Map<String, String> hash) {
        try (Jedis jedis = getJedis()) {
            return jedis.hmset(key, hash);
        }
    }

    public Long zadd(String key, Map<String, Double> scoreMembers) {
        try (Jedis jedis = getJedis()) {
            return jedis.zadd(key, scoreMembers);
        }
    }

    public Long sadd(String key, String... members) {
        try (Jedis jedis = getJedis()) {
            return jedis.sadd(key, members);
        }
    }

    public Long rpush(String key, String... members) {
        try (Jedis jedis = getJedis()) {
            return jedis.rpush(key, members);
        }
    }

    public void hset(String key, String field, String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.hset(key, field, value);
        } finally {
            release(jedis);
        }
    }

    public String hget(String key, String field) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.hget(key, field);
        } finally {
            release(jedis);
        }
    }


    //批量插入Hash(用于批量插入黑名单)
    public void hsetBatch(String key, Set<String> fieldsAndValues) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            Pipeline pipeline = jedis.pipelined();
            for (String str : fieldsAndValues) {
                pipeline.hset(key, str, str);
            }
            pipeline.sync();
        } finally {
            release(jedis);
        }
    }

    //批量插入Hash(用于批量插入场景状态)
    public void hsetBatch(List<Map<String, String>> list) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            Pipeline pipeline = jedis.pipelined();
            for (Map<String, String> map : list) {
                pipeline.hset(map.get("key"), map.get("field"), map.get("value"));
            }
            pipeline.sync();
        } finally {
            release(jedis);
        }
    }

    //批量删除Hash(用于批量删除场景状态)
    public void hdelBatch(List<Map<String, String>> list) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            Pipeline pipeline = jedis.pipelined();
            for (Map<String, String> map : list) {
                pipeline.hdel(map.get("key"), map.get("field"));
            }
            pipeline.sync();
        } finally {
            release(jedis);
        }
    }


    public void hdel(String key, String... fields) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.hdel(key, fields);
        } finally {
            release(jedis);
        }
    }

    public boolean hexists(String key, String field) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.hexists(key, field);
        } finally {
            release(jedis);
        }
    }

    public Map<String, String> hgetAll(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.hgetAll(key);
        } finally {
            release(jedis);
        }
    }

    public Set<String> keys(String pattern) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.keys(pattern);
        } finally {
            release(jedis);
        }
    }

    public Set<String> hkeys(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.hkeys(key);
        } finally {
            release(jedis);
        }
    }

    public List<String> hvals(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.hvals(key);
        } finally {
            release(jedis);
        }
    }

    private Jedis getJedis() {
        return jedisPool.getResource();
    }

    private void release(Jedis jedis) {
        jedisPool.returnResourceObject(jedis);
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }


    public List<String> hmget(String key, String[] params) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.hmget(key, params);
        } finally {
            release(jedis);
        }
    }
}
