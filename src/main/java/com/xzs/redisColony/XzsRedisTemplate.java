package com.xzs.redisColony;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.JedisCluster;

/**
 * 自定义redisTemplate
 * 
 * @Description:TODO
 * @exception:
 * @author: 徐正顺
 * @time:2017年11月29日 下午2:57:55
 */
@Component
public class XzsRedisTemplate {
	private static final Logger LOGGER = LoggerFactory.getLogger(XzsRedisTemplate.class);

	@Autowired
	private JedisCluster jedisCluster;

	@Autowired
	private RedisProperties redisProperties;

	private static final String KEY_SPLIT = ":"; // 用于隔开缓存前缀与缓存键值

	/**
	 * 设置缓存
	 * 
	 * @param prefix
	 *            缓存前缀（用于区分缓存，防止缓存键值重复）
	 * @param key
	 *            缓存key
	 * @param value
	 *            缓存value
	 * @exception:
	 * @author: 徐正顺
	 * @time:2017年11月29日 下午2:58:17
	 */
	public void set(String prefix, String key, String value) {
		jedisCluster.set(prefix + KEY_SPLIT + key, value);
		LOGGER.debug("RedisUtil:set cache key={},value={}", prefix + KEY_SPLIT + key, value);
	}

	/**
	 * 
	 * 设置缓存，并且自己指定过期时间
	 * 
	 * @param prefix
	 * @param key
	 * @param value
	 * @param expireTime
	 *            过期时间
	 * @exception:
	 * @author: 徐正顺
	 * @time:2017年11月29日 下午2:58:40
	 */
	public void setWithExpireTime(String prefix, String key, String value, int expireTime) {
		jedisCluster.setex(prefix + KEY_SPLIT + key, expireTime, value);
		LOGGER.debug("RedisUtil:setWithExpireTime cache key={},value={},expireTime={}", prefix + KEY_SPLIT + key, value,
				expireTime);
	}

	/**
	 * 
	 * 设置缓存，并且由配置文件指定过期时间
	 * 
	 * @param prefix
	 * @param key
	 * @param value
	 * @exception:
	 * @author: 徐正顺
	 * @time:2017年11月29日 下午2:58:56
	 */
	public void setWithExpireTime(String prefix, String key, String value) {
		int EXPIRE_SECONDS = redisProperties.getTimeout();
		jedisCluster.setex(prefix + KEY_SPLIT + key, EXPIRE_SECONDS, value);
		LOGGER.debug("RedisUtil:setWithExpireTime cache key={},value={},expireTime={}", prefix + KEY_SPLIT + key, value,
				EXPIRE_SECONDS);
	}

	/**
	 * 
	 * 获取指定key的缓存
	 * 
	 * @param prefix
	 * @param key
	 * @exception:
	 * @author: 徐正顺
	 * @time:2017年11月29日 下午2:59:08
	 */
	public String get(String prefix, String key) {
		String value = jedisCluster.get(prefix + KEY_SPLIT + key);
		LOGGER.debug("RedisUtil:get cache key={},value={}", prefix + KEY_SPLIT + key, value);
		return value;
	}

	/**
	 * 删除指定key的缓存
	 * 
	 * @param prefix
	 * @param key
	 * 
	 * @exception:
	 * @author: 徐正顺
	 * @time:2017年11月29日 下午2:59:38
	 */
	public void deleteWithPrefix(String prefix, String key) {
		jedisCluster.del(prefix + KEY_SPLIT + key);
		LOGGER.debug("RedisUtil:delete cache key={}", prefix + KEY_SPLIT + key);
	}

	public void delete(String key) {
		jedisCluster.del(key);
		LOGGER.debug("RedisUtil:delete cache key={}", key);
	}

}