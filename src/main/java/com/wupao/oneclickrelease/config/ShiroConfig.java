package com.wupao.oneclickrelease.config;

import com.wupao.oneclickrelease.pojo.Menu;
import com.wupao.oneclickrelease.service.MenuService;
import com.wupao.oneclickrelease.service.impl.MenuServiceImpl;
import com.wupao.oneclickrelease.shiro.UserRealm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wuxianglong
 */
@Slf4j
@Configuration
public class ShiroConfig {

    @Bean
    public UserRealm realm() {
        return new UserRealm();
    }

    @Bean("securityManager")
    public DefaultWebSecurityManager getManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        // 使用自己的realm
        manager.setRealm(realm());
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        manager.setSubjectDAO(subjectDAO);
        ThreadContext.bind(manager);
        return manager;
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean factory(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        // 添加自己的过滤器并且取名为jwt
        Map<String, Filter> filterMap = new HashMap<>(16);
        filterMap.put("jwt", new JwtFilter());
        factoryBean.setFilters(filterMap);
        factoryBean.setSecurityManager(securityManager);
        Map<String, String> filterRuleMap = new LinkedHashMap<>();
        filterRuleMap.put("/user/login", "anon");
        filterRuleMap.put("/user/imgCode", "anon");
        // 开放API文档接口
        filterRuleMap.put("/swagger-ui.html", "anon");
        filterRuleMap.put("/doc.html", "anon");
        filterRuleMap.put("/webjars/**", "anon");
        filterRuleMap.put("/swagger-resources/**", "anon");
        filterRuleMap.put("/v2/**", "anon");
        filterRuleMap.put("/data/**", "anon");
        filterRuleMap.put("/check/**", "anon");
        filterRuleMap.put("/static/**", "anon");
        filterRuleMap.put("/erp/api/**", "anon");
        filterRuleMap.put("/**", "jwt");
        factoryBean.setFilterChainDefinitionMap(filterRuleMap);
        return factoryBean;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(getManager());
        return authorizationAttributeSourceAdvisor;
    }
}
