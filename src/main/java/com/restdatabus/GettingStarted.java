package com.restdatabus;

import com.hazelcast.config.Config;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.config.InterfacesConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.util.Map;
import java.util.Queue;

public class GettingStarted {

    public static void main(String[] args) {

        Config cfg = new Config();

        cfg.setInstanceName("toto");
        cfg.setGroupConfig(new GroupConfig("thegroup"));
        NetworkConfig netcfg = new NetworkConfig();

        cfg.getNetworkConfig().setPublicAddress("127.0.0.1");

        HazelcastInstance instance = Hazelcast.newHazelcastInstance(cfg);
        Map<Long, String> mapCustomers = instance.getMap("test");
        mapCustomers.put(2L, "Ali");
        mapCustomers.put(3L, "Avi");

        System.out.println("Customer with key 1: "+ mapCustomers.get(1L));
        System.out.println("Map Size:" + mapCustomers.size());
    }

}
