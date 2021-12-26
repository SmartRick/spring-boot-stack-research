package cn.smartrick.config;

import org.springframework.stereotype.Component;

/**
 * @Date: 2021/12/25
 * @Author: SmartRick
 * @Description: TODO
 */

public class DataSourceHolder {
    private static ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<String>();

    public static void setDataSource(String dataSourceBeanName) {
        CONTEXT_HOLDER.set(dataSourceBeanName);
    }

    public static String getDataSource() {
        return CONTEXT_HOLDER.get();
    }

    public void clearDataSource() {
        CONTEXT_HOLDER.remove();
    }
}
