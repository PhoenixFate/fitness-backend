package com.phoenix.fitness.config;

import com.phoenix.fitness.modules.admin.oauth2.OAuth2Filter;
import com.phoenix.fitness.modules.admin.oauth2.OAuth2Realm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro配置
 *
 * @author Mark sm516116978@outlook.com
 */
@Configuration
public class ShiroConfig {

    @Bean("securityManager")
    public SecurityManager securityManager(OAuth2Realm oAuth2Realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(oAuth2Realm);
        securityManager.setRememberMeManager(null);
        return securityManager;
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        //oauth过滤
        Map<String, Filter> filters = new HashMap<>();
        filters.put("oauth2", new OAuth2Filter());
        shiroFilter.setFilters(filters);
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/webjars/**", "anon");
        filterMap.put("/druid/**", "anon");
        filterMap.put("/pad/**", "anon");
        filterMap.put("/customer/**", "anon");
        filterMap.put("/sys/login", "anon");
        filterMap.put("/sys/statistics/**", "anon");
        filterMap.put("/sys/gym/**", "anon");
        filterMap.put("/sys/partner/**", "anon");
        filterMap.put("/sys/actionType/**", "anon");
        filterMap.put("/sys/action/**", "anon");
        filterMap.put("/sys/classInfo/**", "anon");
        filterMap.put("/sys/coach/**", "anon");
        filterMap.put("/sys/customer/**", "anon");
        filterMap.put("/sys/contract/**", "anon");
        filterMap.put("/sys/customerShare/**", "anon");
        filterMap.put("/sys/equipment/**", "anon");
        filterMap.put("/sys/exercise/**", "anon");
        filterMap.put("/sys/order/**", "anon");
        filterMap.put("/sys/target/**", "anon");
        filterMap.put("/sys/targetType/**", "anon");
        filterMap.put("/sys/trainingPlan/**", "anon");
        filterMap.put("/sys/trainingGoal/**", "anon");
        filterMap.put("/sys/vipCard/**", "anon");
        filterMap.put("/sys/bill/**", "anon");
        filterMap.put("/sys/marketing/**", "anon");
        filterMap.put("/wechat/**", "anon");
        filterMap.put("/swagger/**", "anon");
        filterMap.put("/v2/api-docs", "anon");
        filterMap.put("/swagger-ui.html", "anon");
        filterMap.put("/swagger-resources/**", "anon");
        filterMap.put("/captcha.jpg", "anon");
        filterMap.put("/aaa.txt", "anon");
        filterMap.put("/**", "oauth2");
        shiroFilter.setFilterChainDefinitionMap(filterMap);
        return shiroFilter;
    }

    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
