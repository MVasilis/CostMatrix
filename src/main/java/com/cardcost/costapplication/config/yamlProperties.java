package com.cardcost.costapplication.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = "classpath:application.yml", factory= YamlPropertySourceFactory.class)
@Getter
public class yamlProperties {

    @Value("${clients.binList.url}")
    private String binListUrl;
}
