package com.xzs.redis.service.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.xzs.redis.service.CacheService;


/**
 * 操作redis
 * @Description:TODO
 * @param <K>
 * @param <V>
 * @exception:
 * @author: 徐正顺
 * @time:2017年11月2日 下午4:12:42
 */
@Service
public class RedisCacheServiceImpl<K,V> implements CacheService<K,V> {

    @Autowired
    RedisTemplate<K, V> redisTemplate;
    
    
//    @Autowired
//    protected HashOperations hashOperations;
    @Override
    public void set(K key, V value,long time) {
    	ValueOperations<K, V> opsForValue = redisTemplate.opsForValue();
        redisTemplate.opsForValue().set(key, value);
      if (time != -1) {
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
    }

	@Override
	public V getValue(K key) {
		// TODO Auto-generated method stub
		return redisTemplate.opsForValue().get(key);
	}

	@Override
	public void remove(K key) {
		//hashOperations.delete(key);
		redisTemplate.delete(key);
		
	}

	
	
}