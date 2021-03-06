package com.github.stiangao.valve.local;

import com.github.stiangao.valve.core.LimiterConfig;
import com.github.stiangao.valve.core.LimiterType;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ttgg on 2017/8/4.
 */
public class LocalLimiterConfig implements LimiterConfig {

    private int globalLimitQps = 99999;
    private boolean enableLimit = false;

    private int defaultClientLimitQps = 9999;
    private int defaultAddressLimitQps = 9999;
    private int defaultTargetLimitQps = 9999;

    private boolean enableClientLimit = false;
    private Map<String, Integer> clientConfigMap = new HashMap<>();
    private boolean enableAddressLimit = false;
    private Map<String, Integer> addressConfigMap = new HashMap<>();
    private boolean enableTargetLimit = false;
    private Map<String, Integer> targetConfigMap = new HashMap<>();

    //TODO read valve.yml file, parse to config
    public void load() {
        InputStream fileStream = getClass().getClassLoader().getResourceAsStream("valve.yml");
        Yaml yaml = new Yaml();
        Map<String, Object> map = (Map<String, Object>) yaml.load(fileStream);
        Object limit = map.get("limit");
    }

    public int getQps(LimiterType type, String key) {
        switch (type) {
            case ALL:
                return globalLimitQps;
            case ADDRESS:
                return getAddressQps(key);
            case TARGET:
                return getTargetQps(key);
            case CLIENT:
                return getClientQps(key);
        }
        return 0;
    }

    private int getClientQps(String key) {
        Integer v = clientConfigMap.get(key);
        return v == null ? defaultClientLimitQps : v;
    }

    private int getTargetQps(String key) {
        Integer v = targetConfigMap.get(key);
        return v == null ? defaultTargetLimitQps : v;
    }

    private int getAddressQps(String key) {
        Integer v = addressConfigMap.get(key);
        return v == null ? defaultAddressLimitQps : v;
    }

    public boolean enableLimit(LimiterType type) {
        switch (type) {
            case ALL:
                return enableLimit;
            case ADDRESS:
                return enableAddressLimit;
            case TARGET:
                return enableTargetLimit;
            case CLIENT:
                return enableClientLimit;
        }
        return false;
    }
}
