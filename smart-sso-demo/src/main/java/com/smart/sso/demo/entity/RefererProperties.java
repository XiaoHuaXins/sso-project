package com.smart.sso.demo.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Author xhx
 * @Date 2022/2/14 18:40
 */
@Component
@Data
@ConfigurationProperties(prefix = "referer")
public class RefererProperties {

    Set<String> refererDomain ;

    public Set<String> getRefererDomain() {
        return refererDomain;
    }

    public void setRefererDomain(Set<String> refererDomain) {
        this.refererDomain = refererDomain;
    }
}
