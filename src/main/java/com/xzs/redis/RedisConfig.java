package com.xzs.redis;

import org.springframework.context.annotation.Configuration;  
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;  
  
@Configuration  
//设定为60秒session过期
@EnableRedisHttpSession(maxInactiveIntervalInSeconds=60)  
public class RedisConfig{  
  
}  
