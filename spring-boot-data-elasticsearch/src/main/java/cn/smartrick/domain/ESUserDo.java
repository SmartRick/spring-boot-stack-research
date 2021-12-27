package cn.smartrick.domain;

import cn.smartrick.constant.AnalyzerType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.stereotype.Indexed;

/**
 * @Date: 2021/12/22 17:22
 * @Author: SmartRick
 * @Description: TODO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "sys_user")
public class ESUserDo {
    @Id
    private Long id;
    @Field(type = FieldType.Text, analyzer = AnalyzerType.SMART_WORD)
    private String username;
    @Field(type = FieldType.Integer)
    private Integer age;
    @Field(type = FieldType.Text, analyzer = AnalyzerType.MAX_WORD)
    private String address;
    @Field(type = FieldType.Text, analyzer = AnalyzerType.MAX_WORD)
    private String phone;
}

