springboot版本的不同会在配置以及方法的使用上产生不同

### 项目启动的三种方式 ###
1. GirlApplication 启动
2. 根目录命令行执行: $ mvn spring-boot:run
3. 通过执行打包(mvn install)的 jar 文件: $ java -jar girl-0.0.1-SNAPSHOT.jar
通过加参数启动 java -jar girl-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod

### 应用配置（使用yml文件更简便） ###
1. server.port 端口号配置
2. server.servlet.context-path 2.0的访问路径配置（1.0为server.context-path）

### 自定义配置 ###

相当在配置文件中定义变量，通过 @Value("${name}") 注解引用变量，数据类型在定义变量时确定
age: 18

@Value("${age}")
private BigDecimal age;

在配置文件中使用当前配置：直接在另一个属性中通过 ${name} 获得属性值即可

age: 18
content: ${age}

** 避免多次使用@Value **
将属性写成实体类，专门一个类包含自定义的属性，并且加上前缀表示

实体类注解：
@Component
@ConfigurationProperties(prefix = "girl")
使用：直接通过@Autowired注入对应的实体类即可

### 多环境配置(例如考虑生产环境与开发环境) ###
生成不同的 application-name.yml 配置文件
在主配置文件 application.yml 中引用不同的配置文件(spring.profiles.active: dev)

### Controller的使用 ###
@Controller 处理 http 请求，没有此类注解是访问不到后台的
@RestController Spring4之后新加的注解，原来返回 json 需要 @ResponseBody 配合 @Controller (@RestController=@ResponseBody+@Controller)
@RequestMapping 配置 url 映射
@PathVariable 获取 url 中的数据，@RequestMapping中需要写/{id} 例如：http://localhost:8080/hello/say/3
@RequestParam 获取请求参数的值，@RequestMapping中不需要写参数 例如：http://localhost:8080/hello/say?id=3
@GetMapping 组合注解，省略了@RequestMapping中的method，更简洁

@Valid 对请求参数做校验，与实体类中的限制注解配合使用，例如@Min，通过BindingResult来获得验证结果

POST：新增
DELETE：删除
PUT：更新
GET：查询


### 连接数据库 ###
pom文件添加依赖：
spring-boot-starter-data-jpa
mysql-connector-java

配置文件添加数据库连接信息
spring:
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://127.0.0.1:3306/dbgirl
    username: root
    password:
  jpa:
    hibernate:
      ddl-auto: create  # create 自动创建表  
    show-sql: true  # 打印sql语句方便调试

spring.jpa.hibernate.ddl-auto的四个值
- create 每次重新创建表结构，会删除原有的数据
- update 自动更新数据库结构
- create-drop 加载hibernate时创建，应用退出时删除表结构
- validate：每次加载Hibernate时都会验证数据表结构，只会和已经存在的数据表进行比较，根据model修改表结构，但不会创建新表

###持久层###
**注解**
@Entity 表示类对应数据库的表
@Id
@GeneratedValue 表示 id 是自增的

@Min(value = 最小值, message = "不满足条件时的提示")
@NotNull(message = "金额必传") 非空校验

**继承JpaRepository<T, ID>**
实现基本的增删改查，不需要写sql语句

### AOP ###
添加依赖：spring-boot-starter-aop

** 注解 **
@Aspect 声明切面
@Component  声明组件，注入 Spring 容器

注意 execution 表达式的书写
声明切点，其他的@Before、@After只需要引用该切点方法即可，不需要再写 execution 表达式
@Pointcut(execution(public * com.leo.girl.controller.GirlController.*(..)))只需要定义一个空方法
@Before("log()")、@After("log()")声明方法执行时期
@AfterReturning(returning = "object", pointcut = "log()") 获得返回的数据


RequestContextHolder
持有上下文的Request容器

获得 request 请求对象
ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

JoinPoint对象
封装了SpringAop中切面方法的信息,在切面方法中添加JoinPoint参数,就可以获取到封装了该方法信息的JoinPoint对象. 

### 统一异常处理 ###
直接向页面抛出异常的格式不统一，可以通过 Result 来封装异常

@ControllerAdvice 注解一般用作处理系统error，拦截出错信息，返回报错提示界面，防止用户看到一推出错信息！

@ExceptionHandler(value = Exception.class) 声明异常处理的方法以及捕获的异常

** 特别注意自定义异常的问题 **

spring 只对 RuntimeException 进行回滚，不会对 Exception 进行回滚

自定义异常要继承 RuntimeException

错误码注意用枚举

### 单元测试 ###

Service方法测试
@RunWith(SpringRunner.class) 表示现在要在测试运行，底层是junit测试工具
@SpringBootTest 表示将启动整个SpringBoot工程

API测试
@AutoConfigureMockMvc

实体类
MockMvc

调用方法
mockMvc.perform(MockMvcRequestBuilders.get("/girls"))
                .andExpect(MockMvcResultMatchers.status().isOk());

项目打包时会自动进行所有的单元测试

项目打包：mvn clean install
跳过测试打包：mvn clean package -Dmaven.test.skip=true


### 问题汇总 ###
1. @Transactional 注解不生效
通过 ddl-auto 配置自动创建的数据表，springboot2.0 默认使用的引擎是MyISAM，不支持事务。
方法一（不推荐）：修改数据表引擎：alter table table engine=InnoDb;
方法二（一劳永逸）：配置文件修改：spring.jpa.database-platform: org.hibernate.dialect.MySQL5InnoDBDialect

2. getOne 和 findOne 的区别
getOne：当查询一个不存在的id数据时，直接抛出异常，因为它返回的是一个引用，简单点说就是一个代理对象。
findOne：当查询一个不存在的id数据时，返回的值是null。
如果想无论如何都有一个返回，那么就用findOne,否则使用getOne。


