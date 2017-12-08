package com.shildon.redis.dl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 实现单redis实例的分布式锁
 * Created by Shildon on 2017/12/8.
 */
public class TinyDistributedLock {

	private static final Logger LOGGER = LoggerFactory.getLogger(TinyDistributedLock.class);

	// 等待锁时间
	private final static long WAIT_LOCK_TIME = 1000;

	private final static String UNLOCK_LUA = "if redis.call(\"get\",KEYS[1]) == ARGV[1] then\n" +
			"    return redis.call(\"del\",KEYS[1])\n" +
			"else\n" +
			"    return 0\n" +
			"end";

	// 可以封装一下jedis pool
	private Jedis jedis;

	public TinyDistributedLock() {

	}

	public TinyDistributedLock(Jedis jedis) {
		this.jedis = jedis;
	}

	/***
	 * 如果分布式锁不可用, 当前线程将继续请求, 直到锁可用.
	 * @param lockKey   锁的键值
	 * @param timeout   超时时间.单位:毫秒.
	 * @return 锁的值.
	 */
	public String lock(String lockKey, long timeout) {
		String lockVal = UUID.randomUUID().toString();

		for (; ; ) {
			if (!tryLock(lockKey, lockVal, timeout)) {
				try {
					TimeUnit.MILLISECONDS.sleep(WAIT_LOCK_TIME);
				} catch (InterruptedException e) {
					LOGGER.error("[RedisLock] sleep error.", e);
				}
			} else {
				break;
			}
		}
		return lockVal;
	}

	/***
	 * 尝试获得分布式锁, 如果锁可用, 返回true
	 * @param lockKey   锁的键值
	 * @param value     锁的值,推荐使用随机字符.
	 * @param timeout   超时时间.单位:毫秒.
	 * @return true/false
	 */
	public boolean tryLock(String lockKey, String value, long timeout) {
		Object result = jedis.set(lockKey, value, "NX", "PX", timeout);
		return "OK".equalsIgnoreCase(result + "");
	}

	/***
	 * 释放锁.
	 * @param lockKey   锁的键值
	 * @param value     锁的值.必须要和上锁的值一致.
	 */
	public void unlock(String lockKey, String value) {
		if (StringUtils.isBlank(lockKey) || StringUtils.isBlank(value)) {
			return;
		}
		jedis.eval(UNLOCK_LUA, 1, lockKey, value);
	}

}
