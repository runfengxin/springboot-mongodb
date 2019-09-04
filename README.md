# springboot集成mongodb （目前实现增删查改，分页、分组聚合）
### 概念：MongoDB 是一个基于分布式文件存储的数据库。由 C++ 语言编写。旨在为 WEB 应用提供可扩展的高性能数据存储解决方案。MongoDB 是一个介于关系数据库和非关系数据库之间的产品，是非关系数据库当中功能最丰富，最像关系数据库的。
学习文档:https://www.runoob.com/mongodb/mongodb-tutorial.html
1. 安装mongodb<br>
教程：https://www.runoob.com/mongodb/mongodb-window-install.html<br>
2. 可视化管理工具：RobMongoDB
3. 依赖导入
```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-mongodb</artifactId>
        </dependency>
        <!-- https://mvnrepository.com/artifact/com.alibaba/fastjson -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.58</version>
        </dependency>
```
<br>
4. mongodb地址配置
<br>

```properties
spring.data.mongodb.uri=mongodb://root:root@192.168.10.29:27017/test
```
<br>
5. 使用mongoTemplate操作数据库

