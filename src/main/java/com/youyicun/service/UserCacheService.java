package com.youyicun.service;

import com.youyicun.wechat.util.UserUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by johnny on 16/4/25.
 */
@Service
@Configuration
@EnableScheduling
public class UserCacheService {
    private Map<String,Object> user = new HashMap();

    @PostConstruct
    @Scheduled(fixedRate = 1000 * 60 * 60)
    public void updateCache() throws IOException {
        Map<String,Object> map = UserUtil.allUser();
        Map<String,Object> data = (Map<String, Object>) map.get("data");
        List<String> openIds = (List<String>) data.get("openid");
        Map<String,Object> res = new HashMap<>();
        for(String s:openIds){
            Map<String,Object> t = UserUtil.loadUserInfo(s);
            res.put(s,t);
        }
        user = res;
    }

    public Map<String, Object> getUser() {
        return user;
    }
}
