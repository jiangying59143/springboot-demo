package com.mall.demo.configure;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession//(maxInactiveIntervalInSeconds = 360)
public class SessionConfig {
}
