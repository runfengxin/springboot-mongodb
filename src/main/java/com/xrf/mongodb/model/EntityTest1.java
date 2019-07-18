package com.xrf.mongodb.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author xinrunfeng
 * @description:
 */
//注释此类将被映射到数据库的一个集合（collection为集合名称）
@Document(collection = "ex_entity_test1")
@Data
public class EntityTest1 implements Serializable {
    //标记id字段
    @Id
    private ObjectId id;

    //如果实体类没有为任何字段创建索引将不会自动创建集合
    @Indexed
    private Long parameter1;

    public EntityTest1(){

    }

    public EntityTest1(Long parameter1) {
        this.parameter1 = parameter1;
    }


}
