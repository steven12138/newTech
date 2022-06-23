# newTech 技术学考报名系统 server

- 2022年重构
- Designed by StevenKerman SubIT
- Powered By Springboot 2.7.0
# Requirement 项目依赖

```
1. Java (18.0.1) & maven
2. MySQL or mariaDB
3. Redis
```

# Deploy 部署

1. 配置
   - 请根据数据库类型，配置驱动类
2. 打包！

```shell
mvn package
```

3. 在jar同级目录下新建```application.yaml```并配置

```yaml
server:
   port: [Listen Port]
spring:
  datasource:
    url: jdbc:mariadb://xxx.xxx.xxx.xxx:3306/NewTech
    driver-class-name: org.mariadb.jdbc.Driver
    username: [UserName]
    password: [Password]
  jpa:
    show-sql: false
    hibernate:
      ddl-auto: update #First time please set to `create` to let it create the database
  application:
    name: NewTech
  redis:
    host: [redis HOST]
    port: 6379
    database: 0
    jedis:
      pool:
        max-active: 20
        max-wait: -1ms
        max-idle: 10
        min-idle: 0
    timeout: 1000
token: "[JWT Secret]"
```

4. 部署

```shell
nohup java -jar xxx.jar > /path &
```
