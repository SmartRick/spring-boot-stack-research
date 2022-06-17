package cn.smartrick.config;

import cn.smartrick.anno.DataSourceType;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;

/**
 * @Date: 2021/12/25
 * @Author: SmartRick
 * @Description: TODO
 */
@Configuration
public class DruidConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.first")
    public DataSource firstDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.second")
    public DataSource secondDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @Primary //当需要注入DataSource时，首先注入当前数据源
    public DataSource dynamicDataSource(DataSource firstDataSource, DataSource secondDataSource) {
        HashMap<Object, Object> datasources = new HashMap<>(2);
        datasources.put(DataSourceType.FIRST.name(), firstDataSource);
        datasources.put(DataSourceType.SECOND.name(), secondDataSource);
        return new DynamicDataSource(firstDataSource, datasources);
    }
}
