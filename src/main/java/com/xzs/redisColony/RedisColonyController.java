package com.xzs.redisColony;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class RedisColonyController {


    @Autowired  
    private RedisTemplate redisTemplate;  
    @Autowired  
    private XzsRedisTemplate xyyRedisTemplate;  
	
	@RequestMapping(value="/redisTest")
	@ResponseBody
	public String getSessionId(HttpServletRequest request){
      
         
                xyyRedisTemplate.setWithExpireTime("koala","lytTest", "xzs1993lyt",5*60);  
                String string = xyyRedisTemplate.get("koala", "lytTest");
           System.err.println("***"+string);
        return string;  
    
	}
	@RequestMapping(value="/redisTest1")
	@ResponseBody
	public String getSessionId1(HttpServletRequest request){
      
               
		String string = xyyRedisTemplate.get("koala", "lytTest");
           System.err.println("***"+string);
        return string;  
    
	}
	
	
	@RequestMapping(value="/redisTest2")
	@ResponseBody
	public String getSessionId2(HttpServletRequest request){
      
            
		xyyRedisTemplate.deleteWithPrefix("koala", "lytTest");
		String string = xyyRedisTemplate.get("koala", "lytTest");
           System.err.println("***"+string);
        return string;  
    
	}
}
