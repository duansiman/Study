### Maven属性
通过＜properties＞元素用户可以自定义一个或多个Maven属性，然后在POM的其他地方使用${属性名称}的方式引用该属性

`内置属性`

${basedir}表示项目根目录，即包含pom.xml文件的目录；${version}表示项目版本

`POM属性`

该类属性引用POM文件中对应元素的值, ${project.artifactId}就对应了＜project＞＜artifactId＞元素的值

`Settings属性`

引用settings.xml文件中XML元素的值，${settings.localRepository}指向用户本地仓库的地址

`Java系统属性`

${user.home}指向了用户目录。用户可以使用mvn help:system查看所有的Java系统属性

`环境变量属性`

env.开头的Maven属性引用。${env.JAVA_HOME}指代了JAVA_HOME环境变量的值

### 资源过滤

maven-resources-plugin该插件就能够解析资源文件中的Maven属性，即开启资源过滤

`配置资源目录开启过滤`

默认的主资源目录和测试资源目录开启了过滤

```xml
<resource>
    <directory>${project.basedir}/src/main/resources</directory>
    <filtering>true</filtering>
</resource>
```

> mvn的-P参数表示在命令行激活一个profile


### Maven Profile

不同环境的构建很可能是不同的, profile能够在构建的时候修改POM的一个子集，或者添加额外的配置元素

`命令激活profile`

mvn命令行参数-P加上profile的id来激活profile，多个id之间以逗号分隔

`settings激活profile`

配置settings.xml文件的active-Profiles元素，表示其配置的profile对于所有项目都处于激活状态

`属性激活profile`

配置当某系统属性存在的时候，自动激活profile

```xml
<activation>
    <property>
        <name>test</name>
    </property>
</activation>
```

`操作系统环境激活`

Profile还可以自动根据操作系统环境激活
```xml
<activation>
    <os>
        <name>Windows XP</name>
        <family></family>
        <arch></arch>
        <version></version>
    </os>
</activation>
```

`文件存在与否激活`

文件存在与否来决定是否激活profile
```xml
<activation>
    <file>
        <missing>文件</missing>
        <exists></exists>
    </file>
</activation>
```
`默认激活`

＜activeByDefault＞true＜/activeByDefault＞

#### 查询 profile

maven-help-plugin提供了一个目标帮助用户了解当前激活的profile：

help:all-profiles 列出当前所有的profile

#### profile的种类

在下面文件里可以定义

- pom.xml
- 用户settings.xml
- 全局settings.xml

`profile 功能`

- 可以修改或添加仓库、插件仓库以及部署仓库地址；
- 可以修改或者添加项目依赖；可以修改聚合项目的聚合配置；
- 可以自由添加或修改Maven属性；添加或修改项目报告配置；
- 可以添加或修改插件配置、项目资源目录和测试资源目录配置以及项目构件的默认名称
