package cn.smartrick.config;

import org.springframework.stereotype.Component;

/**
 * @Date: 2021/12/25
 * @Author: SmartRick
 * @Description: 数据源持有者，通过改变其当前线程数据源beanName结合AbstractRoutingDatasource实现动态数据源切换
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
