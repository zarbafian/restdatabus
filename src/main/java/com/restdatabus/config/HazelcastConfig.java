package com.restdatabus.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.config.InterfacesConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfig {

    @Value("${hazelcast.public-address}")
    private String publidAddress;

    @Bean
    public Config getConfig() {
        Config cfg = new Config();

        cfg.setInstanceName("toto");
        cfg.setGroupConfig(new GroupConfig("thegroup"));

        cfg.getNetworkConfig().setPublicAddress(publidAddress);

        return cfg;
    }

}
