package com.kodility.service.configuration;

import com.kodility.dao.configuration.ConfigurationDao;
import com.kodility.domain.configuration.Configuration;
import com.kodility.utils.EnvironmentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class ConfigurationService {

    private static final Map<String, String> CONFIGURATIONS = new ConcurrentHashMap<>();

    @Autowired
    private ConfigurationDao configurationDao;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Value("${environment}")
    private String environment;

    @PostConstruct
    public void init() {
        populateConfigurationsMap();
    }

    public String getValue(String key) {
        return CONFIGURATIONS.get(key);
    }

    public String getGoogleAnalyticsScript() {
        return getValue("google.analytics.script");
    }

    public boolean isDevelopment() {
        return EnvironmentUtils.isDevelopment(environment);
    }

    private void populateConfigurationsMap() {
        new TransactionTemplate(transactionManager).execute(status -> {
            CONFIGURATIONS.putAll(configurationDao.findAll().stream().collect(Collectors.toMap(Configuration::getName, Configuration::getValue)));
            return null;
        });
    }

}