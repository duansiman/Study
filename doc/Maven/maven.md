### Maven 
是解析XML文档，管理生命周期和插件，它的强大来源插件，这些插件可以编译源代码、打包二进制代码、发布站点等

#### 命令格式

> mvn <plugin-prefix>:<goal> -D属性名=属性值 ...
>
> **plugin-prefix** 有效插件前缀
>
> **goal** 插件所包含的目标
>
> 也可以这样执行：mvn <plugin-group-id>:<plugin-artifact-id>:<plugin-version>:<goal>

#### 插件

##### archetype插件

创建项目用的

###### goal目标

- archetype:create
- archetype:generate
- archetype:create-from-project
- archetype:crawl 从仓库中搜索原型

##### compile插件

编译项目的

##### exec 插件

运行Java类

> mvn exec:java -Dexec.mainClass=com.epdc.maven.App

##### help 插件

###### goal目标

- help:effective-pom 查看项目起作用的pom，参考核心概念pom文件

##### dependency 插件

分析依赖

###### goal目标

- dependency:analyze
- dependency:resolve
- dependency:tree 列出直接和传递性依赖，以树形式打印

#### 核心概念

Maven 采用了"约定由于配置"的原则，比如文件的放置位置

预定义了一个固定的生命周期，以及一组用于构建和装配软件的通用插件

##### pom文件

每个项目的pom.xml，都有一个上级pom.xml，当前项目的pom配置信息合并到上级的pom, Maven的默认上级pom文件定义项目大量的默认设置

##### 生命周期（lifecycle）

构建项目包含多个有序的阶段（phase），Maven有默认一套生命周期

> 生命周期中元素称为阶段（phase），由多个阶段组成，各阶段按顺序、依次执行
>
> mvn <phase1> <phase2> ...

mvn命令执行生命周期里某阶段时，会自动从生命周期的第一个阶段开始执行，直至mvn命令指定的阶段

##### 三个基本的生命周期

###### clean 生命周期

构建项目之前进行一些清理工作，包括三个核心阶段

- pre-clean 预清理
- clean 执行清理
- post-clean 最后清理

###### defaut 生命周期

包括的核心阶段

- compile 编译项目
- test 单元测试
- package 项目打包
- install 安装到本地仓库
- deploy 部署到远程仓库

实际上有这么多阶段，上面只列了核心阶段

> validate, initialize, generate-sources, process-sources, generate-resources, process-resources, compile, process-classes, generate-test-sources, process-test-sources, generate-test-resources, process-test-resources, test-compile, process-test-classes, test, prepare-package, package, pre-integration-test, integration-test, post-integration-test, verify, install, deploy

- process-resources 复制资源文件至目标目录
- process-test-classes 复制测试资源文件至测试目标目录

###### site 生命周期

生成项目报告站点、发布站点，包括核心阶段

- pre-site 生成站点前校验
- site 生成站点
- post-site 生成站点后校验
- site-deploy 发布站点到远程服务器

##### 插件和目标

阶段所完成工作有插件实现，每个阶段可以绑定多个目标

- compile阶段是 compile:compile
- test阶段是surefire:test
- package阶段是jar:jar
- install阶段是install:install
- deploy阶段是deploy:deploy

```xml
<plugin>
	<groupId>org.codehaus.mojo</groupId>
    <artifactId>exec-maven-plugin</artifactId>
    <version>1.3.1</version>
    <executions>
    	<execution>
            <!-- 绑定到compile阶段 -->
        	<phase>compile</phase>
            <!-- 运行的目标 -->
            <goals>
            	<goal>java</goal>
            </goals>
            <!-- 插件配置 -->
            <configuration>
            	<mainClass></mainClass>
            </configuration>
        </execution>
    </executions>
</plugin>
```

##### Maven的资源库（repository）

保存各种第三方的包、Maven插件，分三种资源库：

- **本地资源库：**用的插件或jar都会下载到本地库，当本地找不到才去远程下载
- **远程资源库：** 由公司集中维护，让全公司项目使用项目JAR包系统
- **中央资源库：**由Maven官方维护

##### 依赖管理

<dependency/> 定义了依赖的框架或类库，由以下子元素：

- scope 依赖库起作用的范围
- type 依赖框架或类库的类型，默认jar
- optional 依赖库是否为可选: 使用<optional> 声明当前依赖是可选的, 默认情况下也不会被其他项目继承 [参考文章](https://juejin.cn/post/6844903987322290189)
- classifier JDK版本号

###### scope 的值

- compile 默认的范围，编译、测试、打包都需要
- provided 容器在runtime是提供
- runtine 编译时不需要，测试和运行时需要
- test 只用于测试阶段
- system 和provided 类似，要求JAR是系统自带的
- import 继承父POM中用dependencyMangement配置的依赖，只能在dependencyMangement中使用（为了解决多继承）

##### POM文件中元素

- <dependencyMangement/> 定义依赖管理
- <repositories/> 定义远程资源库的位置
- <pluginRepositories/> 定义插件资源库的位置
- <distrbutionMangement/> 部署管理
- <profiles/> 指定根据环境调整构建配置
- <scm/> 指定项目源代码管理工具
- <build/> 定义构建信息
- <properties/> 定义全局属性

###### 环境属性引用

- env.x 会返回一个shell环境变量
- project.x 指代了POM中对应的元素值
- settings.x 指代了settings.xml中对应元素的值
- ${java.home} 可通过java.lang.System.getProperties()访问的属性都能在POM中使用该形式访问
- x 在<properties/>元素中，或者外部文件中设置，以${x}的形式使用

##### settings.xml文件

在Maven安装目录的conf子目录下，用户的.m2目录下settings.xml配置文件会覆盖Maven安装目录

- <localRepository/> 构建系统本地仓库的路径
- <interactiveMode/> 是否需要和用户交互以获得输入，默认为true
- <usePluginRegistry/> 是否需要使用plugin-registry.xml文件来管理插件版本（~/.m2/plugin-registry.xml来管理插件版本）
- <offline/> 是否允许Maven进行联网来下载所需要的信息，默认为false
- <pluginGroups/> 当通过plugin的前缀来解析plugin的时候到哪里寻找
- <servers/> 仓库的用户名、密码信息配置（里面id与distributionManagement中repository元素的id相匹配）
- <mirrors/> 把远程仓库的请求转换到对其镜像地址的请求，每个远程仓库都会有一个id，mirror来关联到该ID仓库
- <proxies/> 配置不同的代理
- <profiles/> 根据环境参数来调整构建配置的列表
- <activeProfiles/> 手动激活profiles的列表，不论环境设置如何，其对应的 profile都会被激活

###### profiles

当profile被激活，它的值会覆盖任何其它定义在pom.xml中带有相同id的profile

- id 唯一标识
- activation 自动触发profile的条件逻辑
- repositories 用于定义远程仓库的, 激活时，作为当前pom的远程仓库
- pluginRepositories 
- properties 用于定义属性键值对的, 激活时，pom.xml中可以使用

#### 参考文章

https://www.cnblogs.com/hongmoshui/p/10762272.html