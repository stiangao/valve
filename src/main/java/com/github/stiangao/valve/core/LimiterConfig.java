package com.github.stiangao.valve.core;

/**
 * Created by ttgg on 2017/8/5.
 */
public interface LimiterConfig {

    int getQps(LimiterType type, String key);

    boolean enableLimit(LimiterType type);

}
