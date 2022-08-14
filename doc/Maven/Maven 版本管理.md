### 版本号定义

`＜主版本＞.＜次版本＞.＜增量版本＞-＜里程碑版本＞`

> 1.3.4-beta-2

主版本和次版本之间，以及次版本和增量版本之间用点号分隔，里程碑版本之前用连字号分隔

- 主版本：表示了项目的重大架构变更
- 次版本：表示较大范围的功能增加和变化，及Bug修复
- 增量版本：一般表示重大Bug的修复
- 里程碑版本：顾名思义，这往往指某一个版本的里程碑, 这样的版本与正式的版本相比，往往表示不是非常稳定，还需要很多测试

#### 版本比较

对于主版本、次版本和增量版本来说，比较是基于数字的，因此1.5＞1.4＞1.3.11＞1.3.9

对于里程碑版本，Maven则只进行简单的字符串比较，1.2-beta-3＞1.2-beta-11的结果

### 自动化版本发布

Maven Release Plugin 插件

`目标`
1. release:prepare　准备版本发布
2. release:rollback　回退release:prepare所执行的操作
3. release:perform　执行版本发布

#### 准备版本发布
- 检查项目是否有未提交的代码
- 检查项目是否有快照版本依赖 
- 根据用户的输入将快照版本升级为发布版 
- 将POM中的SCM信息更新为标签地址 
- 基于修改后的POM执行Maven构建 
- 提交POM变更 
- 基于用户输入为代码打标签 
- 将代码从发布版升级为新的快照版 
- 提交POM变更

#### 配置版本控制系统信息
需要知道版本控制系统的主干、标签等地址信息后才能执行相关的操作，在pom文件需要配置scm信息

`插件配置`

代码管理系统的tag路径
> ＜tagBase＞**/tags/＜/tagBase＞ 

autoVersionSubmodules参数
> 所有模块的发布版本以及新的SNAPSHOT开发版本都保持一致

`生成-sources.jar和-javadoc.jar`

超级pom文件：activate元素下有一个名为performRelease、值为true的属性配置,激活下面插件

- maven-sources-plugin的jar目标会为项目生成-source.jar文件，
- maven-javadoc-plugin的jar目标会为项目生成-javadoc.jar文件，
- maven-deploy-plugin的update-release-info配置，则会在部署的时候更新仓库中的元数据，告诉仓库该版本是最新的发布版。

> 每个插件配置中值为true的inherited元素则表示该插件配置可以被子POM继承

#### 自动创建分支

Maven Release Plugin 插件branch目标实现

`插件配置`

代码管理系统的branch路径
> ＜branchBase＞**/branches/＜/branchBase＞

`命令`

$mvn release:branch-DbranchName=1.1.x\-DupdateBranchVersions=true-DupdateWorkingCopyVersions=false
- -DbranchName=1.1.x用来配置所要创建的分支的名称，
- -DupdateBranchVersions=true表示为分支使用新的版本，
- -DupdateWorkingCopyVersions=false表示不更新本地代码

### GPG签名
Maven GPG Plugin够帮自动完成签名