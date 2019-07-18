package com.xrf.mongodb;

import com.xrf.mongodb.model.EntityTest;
import com.xrf.mongodb.model.EntityTest1;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongodbApplicationTests {


    @Autowired
    private MongoTemplate mongoTemplate;

    //新增文档
    @Test
    public void insert(){
        //insert方法并不提供级联类的保存，所以级联类需要先自己先保存
        EntityTest1 entityTest1_1 = new EntityTest1(1000L);
        //执行完insert后对象entityTest1将会获得保存后的id
        mongoTemplate.insert(entityTest1_1);
        //再添加一条
        EntityTest1 entityTest1_2 = new EntityTest1(1001L);
        mongoTemplate.insert(entityTest1_2);
        //创建列表并将保存后的关联对象添加进去
        ArrayList<EntityTest1> entityTest1List = new ArrayList<EntityTest1>();
        entityTest1List.add(entityTest1_1);
        entityTest1List.add(entityTest1_2);

        //新增主体对象
        EntityTest entityTest = new EntityTest(100L,"test",new Date(),10,entityTest1List);
        //新增数据的主键已经存在，则会抛DuplicateKeyException异常
        mongoTemplate.insert(entityTest);
    }

    //保存文档
    //保存与新增的主要区别在于，如果主键已经存在，新增抛出异常，保存修改数据
    @Test
    public void save(){
        //查询最后一条数据并更新
        Sort sort = new Sort(Sort.Direction.DESC,"parameter3");
        EntityTest entityTest = mongoTemplate.findOne(Query.query(Criteria.where("")).with(sort),EntityTest.class);
        entityTest.setParameter4(3000);
        //保存数据的主键已经存在，则会对已经存在的数据修改
        mongoTemplate.save(entityTest);
    }

    //更新文档
    @Test
    public void update(){
        //将查询条件符合的全部文档更新
        Query query = new Query();
        Update update = Update.update("parameter2_","update");
        mongoTemplate.updateMulti(query,update,EntityTest.class);
    }

    //删除文档
    @Test
    public void delete(){
        //查询第一条数据并删除
        EntityTest entityTest = mongoTemplate.findOne(Query.query(Criteria.where("")),EntityTest.class);
        //remove方法不支持级联删除所以要单独删除子数据
        List<EntityTest1> entityTest1List = entityTest.getParameter5();
        for(EntityTest1 entityTest1:entityTest1List){
            mongoTemplate.remove(entityTest1);
        }
        //删除主数据
        mongoTemplate.remove(entityTest);
    }

    //查询文档
    @Test
    public void find(){
        //查询小于当前时间的数据，并按时间倒序排列
        Sort sort = new Sort(Sort.Direction.DESC,"parameter3");
        List<EntityTest> findTestList = mongoTemplate.find(Query.query(Criteria.where("parameter3").lt(new Date())).with(sort) ,EntityTest.class);

        //使用findOne查询如果结果极为多条，则返回排序在最上面的一条
        EntityTest findOneTest = mongoTemplate.findOne(Query.query(Criteria.where("parameter3").lt(new Date())).with(sort) ,EntityTest.class);

        //模糊查询
        List<EntityTest> findTestList1 = mongoTemplate.find(Query.query(Criteria.where("parameter3").lt(new Date()).and("parameter2").regex("es")) ,EntityTest.class);

        //分页查询（每页3行第2页）
        Pageable pageable = new PageRequest(1,3,sort);
        List<EntityTest> findTestList2 = mongoTemplate.find(Query.query(Criteria.where("parameter3").lt(new Date())).with(pageable) ,EntityTest.class);
        //共多少条
        Long count = mongoTemplate.count(Query.query(Criteria.where("parameter3").lt(new Date())),EntityTest.class);
        //返回分页对象
        Page<EntityTest> page = new PageImpl<EntityTest>(findTestList2,pageable,count);

        //分页查询（通过起始行和数量也可以自己实现分页逻辑）
        List<EntityTest> findTestList3 = mongoTemplate.find(Query.query(Criteria.where("parameter3").lt(new Date())).with(sort).skip(3).limit(3) ,EntityTest.class);

    }

}
