package com.xzs.redis.service;

import java.util.List;
import java.util.Set;

public interface CacheService<K,V> {

	/**
	 * 存
	 * @Description:TODO
	 * @param key 
	 * @param value
	 * @param time 
	 *        过期时间，以秒为单位
	 * void
	 * @exception:
	 * @author: 徐正顺
	 * @time:2017年11月2日 下午3:44:27
	 */
	public void set(K key, V value,long time);
	/**
	 * 根据key取
	 * @Description:TODO
	 * @param key
	 * @return
	 * V
	 * @exception:
	 * @author: 徐正顺
	 * @time:2017年11月2日 下午12:25:24
	 */
	public V getValue(K key);
	

  
	/**
	 *   删除
     *
	 * @Description:TODO
	 * @param key 传入key的名称
	 * void
	 * @exception:
	 * @author: 徐正顺
	 * @time:2017年11月2日 下午3:48:15
	 */
    public void remove(K key);
    
    
   
}
