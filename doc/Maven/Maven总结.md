### Maven 是什么

Maven主要服务于基于Java平台的项目构建、依赖管理和项目信息管理

> 编译、运行单元测试、生成文档、打包和部署等，这就是构建。

`Maven 遵循 约定由于配置`

#### Maven 好处

- 统一项目构建
- 不用编写构建脚本，比如Make和Ant需要自己写构建脚本
- 支持单元测试和持续集成

### Maven安装

conf 目录包含settings.xml, 全局地定制Maven的行为；~/.m2/目录下settings.xml，用户范围定制Maven的行为。

lib目录：存在Maven内置的超级POM

> setttings文件支持配置代理，有proxies 和 proxy 元素
> 
> MAVEN_OPTS 设置maven的Java内存大小

### Maven的使用

#### POM文件
POM（Project Object Model，项目对象模型）定义了项目的基本信息，用于描述项目如何构建，声明项目依赖

`元素说明`

modelVersion指定了当前POM模型的版本

groupId定义了项目属于哪个组

artifactId定义了当前Maven项目在组中唯一的ID

version 指定项目的版本

name元素定义项目名称

> groupId、artifactId和version的三行。这三个元素定义了一个项目基本的坐标

`代码位置`

项目主代码位于src/main/java目录

测试代码目录是src/test/java

`打包/运行`

默认打包类型jar，jar文件名：artifact-version.jar规则进行命名的

带有main方法的类信息不会添加到jar里manifest文件中，需要使用maven-shade-plugin插件

> 使用finalName来自定义jar文件的名称