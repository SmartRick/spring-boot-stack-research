package cn.smartrick;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import javax.annotation.Resource;

/**
 * @Date: 2021/12/22 17:19
 * @Author: SmartRick
 * @Description: TODO
 */
@SpringBootApplication
@EnableElasticsearchRepositories("cn.smartrick.dao")
public class EsApplication {
    public static void main(String[] args) {
        SpringApplication.run(EsApplication.class);
    }
}
