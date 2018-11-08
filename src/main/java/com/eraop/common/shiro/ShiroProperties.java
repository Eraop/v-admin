package com.eraop.common.shiro;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

/**
 * @author weiyi
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "shiro")
public class ShiroProperties {
    private int cookieTimeout = 86400;
    private List<String> anonUrl;
    private String loginUrl = "/login";
    private String successUrl = "/index";
    private String logoutUrl = "/logout";
    private String unauthorizedUrl = "/403";

}
