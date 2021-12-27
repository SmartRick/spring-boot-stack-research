package cn.smartrick.dao;

import cn.smartrick.domain.ESUserDo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @Date: 2021/12/22 17:24
 * @Author: SmartRick
 * @Description: TODO
 */
//@Repository
public interface EsUserDao extends ElasticsearchRepository<ESUserDo,Long> {
}
