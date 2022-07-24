### 何为Maven

Maven主要服务于基于Java平台的项目构建、依赖管理和项目信息管理

> 编译、运行单元测试、生成文档、打包和部署等，这就是构建。

通过一个坐标系统准确地定位每一个构件（artifact），也就是通过一组坐标Maven能够找到任何一个Java类库（如jar文件）。

Maven还能帮助我们管理原本分散在项目中各个角落的项目信息，包括项目描述、开发者列表、版本控制系统地址、许可证、缺陷管理系统地址等。

#### 为什么需要Maven

和Make一样，Ant也都是过程式的，开发者显式地指定每一个目标，以及完成该目标所需要执行的任务。

测试驱动开发（TDD）。TDD强调测试先行，所有产品都应该由测试用例覆盖

持续集成（CI）。CI强调项目以很短的周期（如15分钟）集成最新的代码。

### 编写POM

POM（Project Object Model，项目对象模型）定义了项目的基本信息，用于描述项目如何构建，声明项目依赖，等等

`元素说明`

modelVersion指定了当前POM模型的版本，对于Maven 2及Maven 3来说，它只能是4.0.0。

artifactId定义了当前Maven项目在组中唯一的ID

#### 编写测试代码

如果不声明依赖范围，那么默认值就是compile，表示该依赖对主代码和测试代码都有效。

在Maven执行测试（test）之前，它会先自动执行项目主资源处理、主代码编译、测试资源处理、测试代码编译等工作，这是Maven生命周期的一个特性

surefire是Maven中负责执行测试的插件

#### 打包和运行

POM中没有指定打包类型，使用默认打包类型jar

它是根据artifact-version.jar规则进行命名的，如有需要，还可以使用finalName来自定义该文件的名称

带有main方法的类信息不会添加到manifest中

为了生成可执行的jar文件，需要借助maven-shade-plugin，配置该插件如下

#### 使用Archetype生成项目骨架

我们称这些基本的目录结构和pom.xml文件内容称为项目的骨架

为此Maven提供了Archetype以帮助我们快速勾勒出项目骨架

### 坐标详解
groupId：定义当前Maven项目隶属的实际项目。
> groupId的表示方式与Java包名的表示方式类似，通常与域名反向一一对应

artifactId：该元素定义实际项目中的一个Maven项目（模块），推荐的做法是使用实际项目名称作为artifactId的前缀。

version：该元素定义Maven项目当前所处的版本

packaging：该元素定义Maven项目的打包方式

>当不定义packaging的时候，Maven会使用默认值jar。

classifier：该元素用来帮助定义构建输出的一些附属构件

> javadoc和sources就是这两个附属构件的classifier。
> 
> 不能直接定义项目的classifier，因为附属构件不是项目直接默认生成的，而是由附加的插件帮助生成。

项目构件的文件名是与坐标相对应的，一般的规则为artifactId-version[-classifier].packaging

> packaging为maven-plugin的构件扩展名为jar。


### 依赖的配置

type：依赖的类型，对应于项目坐标定义的packaging。大部分情况下，该元素不必声明，其默认值为jar。

#### 依赖范围

Maven在编译和执行测试的时候会使用另外一套classpath。

依赖范围就是用来控制依赖与这三种classpath（编译classpath、测试classpath、运行classpath）的关系，Maven有以下几种依赖范围：

compile：编译依赖范围

test：测试依赖范围

provided：已提供依赖范围 对于编译和测试class-path有效

runtime：运行时依赖范围。使用此依赖范围的Maven依赖，对于测试和运行class-path有效

system：系统依赖范围。该依赖与三种classpath的关系，和provided依赖范围完全一致

> 使用system范围的依赖时必须通过systemPath元素显式地指定依赖文件的路径

import（Maven 2.0.9及以上）：导入依赖范围。该依赖范围不会对三种classpath产生实际的影响

#### 传递性依赖
Maven的传递性依赖机制可以很好地解决这一问题

Maven会解析各个直接依赖的POM，将那些必要的间接依赖，以传递性依赖的形式引入到当前的项目中。

依赖范围不仅可以控制依赖与三种classpath的关系，还对传递性依赖产生影响

第一直接依赖的范围和第二直接依赖的范围决定了传递性依赖的范围

当第二直接依赖的范围是compile的时候，传递性依赖的范围与第一直接依赖的范围一致；当第二直接依赖的范围是test的时候，依赖不会得以传递；当第二直接依赖的范围是provided的时候，只传递第一直接依赖范围也为provided的依赖，且传递性依赖的范围同样为provided；当第二直接依赖的范围是runtime的时候，传递性依赖的范围与第一直接依赖的范围一致，但compile例外，此时传递性依赖的范围为runtime

#### 依赖调解
Maven依赖调解（Dependency Mediation）的第一原则是：路径最近者优先。

Maven定义了依赖调解的第二原则：第一声明者优先。在依赖路径长度相等的前提下，在POM中依赖声明的顺序决定了谁会被解析使用，顺序最靠前的那个依赖优胜

#### 可选依赖

然而，由于这里X、Y是可选依赖，依赖将不会得以传递

使用＜optional＞元素表示mysql-connector-java和postgresql这两个依赖为可选依赖，它们只会对当前项目B产生影响，当其他项目依赖于B的时候，这两个依赖不会被传递

> 在理想的情况下，是不应该使用可选依赖的

#### 最佳实践

需要注意的是，声明exclusion的时候只需要groupId和artifactId，而不需要version元素，这是因为只需要groupId和artifactId就能唯一定位依赖图中的某个依赖

首先使用properties元素定义Maven属性，该例中定义了一个springframework.version子元素，其值为2.5.6

命令查看当前项目的已解析依赖：mvn dependency:list

当前项目的依赖树：mvn dependency:tree

dependency:analyze工具可以帮助分析当前项目的依赖。

因此，显式声明任何项目中直接用到的依赖。

> dependency:analyze只会分析编译主代码和测试代码需要用到的依赖，一些执行测试和运行时需要的依赖它就发现不了

### 何为Maven仓库

Maven可以在某个位置统一存储所有Maven项目共享的构件，这个统一的位置就是仓库

#### 仓库的布局

如果构件有classifier，就加上构件分隔符和classifier。

检查构件的extension，若extension存在，则加上句点分隔符和extension

artifactHandler是由项目的packaging决定的。

#### 仓库的分类
对于Maven来说，仓库只分为两类：本地仓库和远程仓库

中央仓库是Maven核心自带的远程仓库，它包含了绝大部分开源的构件

私服是另一种特殊的远程仓库，为了节省带宽和时间，应该在局域网内架设一个私有的仓库服务器，用其代理所有外部的远程仓库

用户目录下都有一个路径名为.m2/repository/的仓库目录

可以编辑文件～/.m2/settings.xml，设置localRepository元素的值为想要的仓库地址

默认情况下，～/.m2/settings.xml文件是不存在的，用户需要从Maven安装目录复制$M2_HOME/conf/settings.xml文件再进行编辑

Install插件的install目标将项目的构建输出文件安装到本地仓库

然后访问路径org/apache/maven/model/

包含这段配置的文件是所有Maven项目都会继承的超级POM

它使用default仓库布局，

snapshots元素，其子元素enabled的值为false，表示不从该中央仓库下载快照版本的构件

当Maven需要下载构件的时候，它从私服请求，如果私服上不存在该构件，则从外部的远程仓库下载，缓存在私服上之后，再为Maven的下载请求提供服务

但是Maven的一些内部机制（如快照更新检查）要求Maven在执行构建的时候不停地检查远程仓库数据。

#### 远程仓库的配置

任何一个仓库声明的id必须是唯一的，尤其需要注意的是，Maven自带的中央仓库使用的id为central，如果其他的仓库声明也使用该id，就会覆盖中央仓库的配置。

`元素说明`

元素updatePolicy用来配置Maven从远程仓库检查更新的频率，默认的值是daily，表示Maven每天检查一次。其他可用的值包括：never—从不检查更新；always—每次构建都检查更新；interval:X—每隔X分钟检查一次更新（X为任意整数）。

元素checksumPolicy用来配置Maven检查检验和文件的策略

认证信息必须配置在settings.xml文件中，servers元素及其server子元素配置仓库认证信息
 
还能将项目生成的构建部署到仓库中，配置distributionManagement元素

#### 快照版本
在发布的过程中，Maven会自动为构件打上时间戳

默认情况下，Maven每天检查一次更新（由仓库配置的updatePolicy控制，见第6.4节），用户也可以使用命令行-U参数强制让Maven检查更新，如mvn clean install-U。

#### 从仓库解析依赖的机制
当本地仓库没有依赖构件的时候，Maven会自动从远程仓库下载；当依赖版本为快照版本的时候，Maven会自动找到最新的快照。

4）如果依赖的版本是RELEASE或者LATEST，则基于更新策略读取所有远程仓库的元数据groupId/artifactId/maven-metadata.xml，将其与本地仓库的对应元数据合并后，计算出RELEASE或者LATEST真实的值，然后基于这个真实的值检查本地和远程仓库，如步骤2）和3）。

如果依赖的版本是SNAPSHOT，则基于更新策略读取所有远程仓库的元数据groupId/artifactId/version/maven-metadata.xml，将其与本地仓库的对应元数据合并后

依赖的版本不明晰的时候，如RELEASE、LATEST和SNAPSHOT,Maven就需要基于更新远程仓库的更新策略来检查更新

用户还可以从命令行加入参数-U，强制检查更新，使用参数后，Maven就会忽略＜updatePolicy＞的配置

RELEASE和LATEST版本，它们分别对应了仓库中存在的该构件的最新发布版本和最新版本（包含快照

Maven通过合并多个远程仓库及本地仓库的元数据，就能计算出基于所有仓库的latest和release分别是什么，然后再解析具体的构件。

Maven 3不再支持在插件配置中使用LATEST和RELEASE。如果不设置插件版本，其效果就和RELEASE一样

仓库元数据并不是永远正确的，有时候当用户发现无法解析某些构件，或者解析得到错误构件的时候，就有可能是出现了仓库元数据错误，这时就需要手工地，或者使用工具（如Nexus）对其进行修复。

#### 镜像
如果仓库X可以提供仓库Y存储的所有内容，那么就可以认为X是Y的一个镜像。


＜mirrorOf＞的值为central，表示该配置为中央仓库的镜像，任何对于中央仓库的请求都会转至该镜像，用户也可以使用同样的方法配置其他仓库的镜像

> 关于镜像的一个更为常见的用法是结合私服。由于私服可以代理任何外部的公共仓库

＜mirrorOf＞的值为星号，表示该配置是所有Maven仓库的镜像

Maven还支持更高级的镜像配置：

#### 仓库搜索服务

Sonatype Nexus仓库搜索服务

### 生命周期和插件

Maven另外两个核心概念是生命周期和插件

#### 何为生命周期

这个生命周期包含了项目的清理、初始化、编译、测试、打包、集成测试、验证、部署和站点生成等几乎所有构建步骤

> 模板方法模式在父类中定义算法的整体结构，子类可以通过实现或者重写父类的方法来控制实际的行为，这样既保证了算法有足够的可扩展性，又能够严格控制算法的整体结构

每个构建步骤都可以绑定一个或者多个插件行为，而且Maven为大多数构建步骤编写并绑定了默认插件

#### 生命周期详解
Maven拥有三套相互独立的生命周期，它们分别为clean、default和site。clean生命周期的目的是清理项目，default生命周期的目的是构建项目，而site生命周期的目的是建立项目站点。

每个生命周期包含一些阶段（phase），这些阶段是有顺序的，并且后面的阶段依赖于前面的阶段，用户和Maven最直接的交互方式就是调用这些生命周期阶段, 而不会对其他生命周期产生任何影响

default生命周期定义了真正构建时所需要执行的所有步骤

site生命周期的目的是建立和发布项目站点

解释其执行的生命周期阶段

实际执行的阶段为clean生命周期的pre-clean和clean阶段。

该命令调用default生命周期的test阶段

> $mvn clean install：该命令调用clean生命周期的clean阶段和default生命周期的install阶段

#### 插件目标
每个功能就是一个插件目标

这是一种通用的写法，冒号前面是插件前缀，冒号后面是该插件的目标。

#### 插件绑定
Maven在核心为一些主要的生命周期阶段绑定了很多插件的目标，当用户通过命令行调用生命周期阶段的时候，对应的插件目标就会执行相应的任务

default生命周期的阶段与插件目标的绑定关系由项目打包类型所决定

default生命周期还有很多其他阶段，默认它们没有绑定任何插件，因此也没有任何实际行为。

除了内置绑定以外，用户还能够自己选择将某个插件目标绑定到生命周期的某个阶段上

executions下每个execution子元素可以用来配置执行一个任务

该例中配置了一个id为attach-sources的任务，通过phrase配置，将其绑定到verify生命周期阶段上，再通过goals配置指定要执行的插件目标。

很多插件的目标在编写时已经定义了默认绑定阶段。可以使用maven-help-plugin查看插件详细信息，了解插件目标的默认绑定阶段。

当多个插件目标绑定到同一个阶段的时候，这些插件声明的先后顺序决定了目标的执行顺序。

#### 插件配置
用户可以在Maven命令中使用-D参数，并伴随一个参数键=参数值的形式，来配置插件目标的参数

用户可以在声明插件的时候，对此插件进行一个全局的配置。也就是说，所有该基于该插件目标的任务，都会使用这些配置

除了为插件配置全局的参数，用户还可以为某个插件任务配置特定的参数

#### 获取插件信息
命令行参数是由该插件参数的表达式（Expression）决定的。

这里值得一提的是目标前缀（Goal Prefix），其作用是方便在命令行直接运行插件

可以使用插件目标前缀替换坐标。

#### 从命令行调用插件

Maven还支持直接从命令行调用插件目标。Maven支持这种方式是因为有些任务不适合绑定在生命周期上，

Maven引入了目标前缀的概念，help是maven-help-plugin的目标前缀，dependency是maven-dependency-plugin的前缀

#### 插件解析机制
可是当Maven需要的插件在本地仓库不存在时，它就不会去这些远程仓库查找。

在POM中配置插件的时候，如果该插件是Maven的官方插件（即如果其groupId为org.apache.maven.plugins），就可以省略groupId配置

Maven在超级POM中为所有核心插件设定了版本，超级POM是所有Maven项目的父POM，所有项目都继承这个超级POM的配置

如果用户使用某个插件时没有设定版本，而这个插件又不属于核心插件的范畴，Maven就会去检查所有仓库中可用的版本，然后做出选择

Maven 3调整了解析机制，当插件没有声明版本的时候，不再解析至latest，而是使用release

插件前缀与groupId:artifactId是一一对应的，这种匹配关系存储在仓库元数据中

这里的仓库元数据为groupId/maven-metadata.xml

Maven在解析插件仓库元数据的时候，会默认使用org.apache.maven.plugins和org.codehaus.mojo两个groupId。

也可以通过配置settings.xml让Maven检查其他groupId上的插件仓库元数据：

### 聚合
Maven聚合（或者称为多模块）这一特性就是为该需求服务的。

> 对于聚合模块来说，其打包方式packaging的值必须为pom，否则就无法构建。

元素modules，这是实现聚合的最核心的配置

聚合模块仅仅是帮助聚合其他模块构建的工具，它本身并无实质的内容

聚合模块与其他模块的目录结构并非一定要是父子关系

Maven会首先解析聚合模块的POM、分析要构建的模块、并计算出一个反应堆构建顺序（Reactor Build Order），然后根据这个顺序依次构建各个模块。反应堆是所有模块组成的一个构建结构

### 继承

需要特别注意的是，它的packaging为pom，这一点与聚合模块一样，作为父模块的POM，其打包类型也必须为pom。

元素relativePath表示父模块POM的相对路径

relativePath的默认值是../pom.xml

子模块没有设置正确的relativePath,Maven将无法找到父POM，这将直接导致构建失败。

在dependencyManagement元素下的依赖声明不会引入实际的依赖，不过它能够约束dependencies下的依赖使用

其主要原因在于在父POM中使用dependencyManagement声明依赖能够统一项目范围中依赖的版本

import的依赖范围，推迟到现在介绍是因为该范围的依赖只在dependencyManagement元素下才有效果，使用该范围的依赖通常指向一个POM，作用是将目标POM中的dependencyManagement配置导入并合并到当前POM的dependencyManagement元素中

#### 聚合与继承的关系
前者主要是为了方便快速构建项目，后者主要是为了消除重复配置。

对于聚合模块来说，它知道有哪些被聚合的模块，但那些被聚合的模块不知道这个聚合模块的存在。

融合使用聚合与继承也没有什么问题

#### 约定优于配置
Maven提倡“约定优于配置”

清除构建目录、创建目录、编译代码、复制依赖至目标目录，最后打包

遵循约定虽然损失了一定的灵活性，用户不能随意安排目录结构，但是却能减少配置。更重要的是，遵循约定能够帮助用户遵守构建标准。

对于Maven 3，超级POM在文件$MAVEN_HOME/lib/maven-model-builder-x.x.x.jar中的org/apache/maven/model/pom-4.0.0.xml路径下。

#### 反应堆
在一个多模块的Maven项目中，反应堆（Reactor）是指所有模块组成的一个构建结构。

实际的构建顺序是这样形成的：Maven按序读取POM，如果该POM没有依赖模块，那么就构建该模块，否则就先构建其依赖模块，如果该依赖还依赖于其他模块，则进一步先构建依赖的依赖

模块间的依赖关系会将反应堆构成一个有向非循环图（Directed Acyclic Graph,DAG）

这个图不允许出现循环，因此，当出现模块A依赖于B，而B又依赖于A的情况时，Maven就会报错。

用户会想要仅仅构建完整反应堆中的某些个模块

Maven提供很多的命令行选项支持裁剪反应堆

灵活应用上述4个参数，可以帮助我们跳过无须构建的模块，从而加速构建。在项目庞大、模块特别多的时候，这种效果就会异常明显。

### Nexus
当用户需要备份Nexus的时候，默认备份sonatype-work/目录，因为该目录包含了用户特定的内容，而nexus-webapp-1.7.2目录下的内容是可以从安装包直接获得的

打开浏览器访问http://localhost:8081/nexus/就能看到Nexus的界面

./nexus start：在后台启动Nexus服务。./nexus stop：停止后台的Nexus服务。./nexus status：查看后台Nexus服务的状态。./nexus restart：重新启动后台的Nexus服务。

则编辑文件nexus-webapp-1.7.2/conf/plexus.properties，找到属性application-port，按需要将默认值8081改成其他端口号

#### Nexus的仓库与仓库组
仓库有四种类型：group（仓库组）、hosted（宿主）、proxy（代理）和virtual（虚拟）

仓库还有一个属性为Policy（策略），表示该仓库为发布（Release）版本仓库还是快照（Snapshot）版本仓库

Public Repositories：该仓库组将上述所有策略为Release的仓库聚合并通过一致的地址提供服务。Public Snapshot Repositories：该仓库组将上述所有策略为Snapshot的仓库聚合并通过一致的地址提供服务

各种类型的Nexus仓库

Deployment Policy用来配置该仓库的部署策略，选项有只读（禁止部署）、关闭重新部署（同一构件只能部署一次）以及允许重新部署

配置中最后的Not Found Cache TTL表示当一个文件没有找到后，缓存这一不存在信息的时间。

多了Artifact Max Age和Metadata Max Age。其中，前者表示构件缓存的最长时间，后者表示仓库元数据文件缓存的最长时间。

，仓库组所包含的仓库的顺序决定了仓库组遍历其所含仓库的次序

#### 配置Maven从Nexus下载构件

Maven还提供了Profile机制，能让用户将仓库配置放到setting.xml中的Profile中

同时配置中又使用activeProfile元素将nexus这个profile激活，这样当执行Maven构建的时候，激活的profile会将仓库配置应用到项目中去

可以创建一个匹配任何仓库的镜像，镜像的地址为私服

#### 部署构件至Nexus
对于另一类Nexus仓库——宿主仓库来说，它们的主要作用是储存组织内部的，或者一些无法从公共仓库中获得的第三方构件，供大家下载使用。

要上传第三方构件，首先选择一个宿主仓库如3rd party，然后在页面的下方选择Artifact Upload选项卡

#### Nexus的权限管理
如要访问Nexus界面，就必须拥有Status-（read）这个权限，而Nexus默认配置的角色UI:Basic UI Privileges就包含了这个权限，

这三个用户对应了三个权限级别

Nexus包含了一个特殊的匿名用户角色（Nexus Anonymous Role），默认配置下没有登录的用户都会拥有该匿名角色的权限

就需要创建基于仓库的增、删、改、查权限。在Nexus中，这样的权限是基于Repository Target建立的

#### Nexus的调度任务

Nexus提供了一系列可配置的调度任务来方便用户管理系统。

10.3 跳过测试

任何改动都要交给测试去验证，测试运行耗时过长应该考虑优化测试，更不要完全依赖持续集成服务来报告错误，测试错误应该尽早在尽小范围内发现，并及时修复。

$mvn package-DskipTests

mvn package-Dmaven.test.skip=true

参数maven.test.skip同时控制了maven-compiler-plugin和maven-surefire-plugin两个插件的行为，测试代码编译跳过了，测试运行也跳过了

＜skip＞true＜/skip＞

＜skip＞true＜/skip＞

10.4 动态指定要运行的测试用例

$mvn test-Dtest=RandomGeneratorTest

星号可以匹配零个或多个字符

除了星号匹配，还可以使用逗号指定多个测试用例：

可以加上-DfailIfNoTests=false，告诉maven-surefire-plugin即使没有任何测试也不要报错：

#### 包含与排除测试用例

*/*Tests.java来匹配所有以Tests结尾的Java类，两个星号**用来匹配任意路径，一个星号*匹配除路径风格符外的0个或者多个字符。

#### 测试报告
Maven通过cobertura-maven-plugin与之集成，用户可以使用简单的命令为Maven项目生成测试覆盖率报告

#### 运行TestNG测试
TestNG允许用户使用一个名为testng.xml的文件来配置想要运行的测试集合

TestNG较JUnit的一大优势在于它支持测试组的概念，如下的注解会将测试方法加入到两个测试组util和medium中：

＜groups＞util,medium＜/groups＞

#### 重用测试代码
这个时候Maven用户就需要通过配置maven-jar-plugin将测试类打包

上述依赖声明中有一个特殊的元素type，所有测试包构件都使用特殊的test-jar打包类型

### 持续集成
持续集成就是快速且高频率地自动构建项目的所有源码，并为项目成员提供丰富的反馈信息

调用自动化构建工具（如Maven）构建项目，该过程包括编译、测试、审查、打包和部署等

一次完整的集成往往会包括以下6个步骤：

好的单元测试必须是自动化的、可重复执行的、不依赖于环境的，并且能够自我检查的。

#### 安装Hudson

用户可以使用——httpPort选项指定Hudson的运行端口

#### Hudson的基本系统设置

包括JDK安装位置和Maven安装等在内的重要信息都必须首先配置正确

### 构建web项目

12.4 使用jetty-maven-plugin进行测试

只有org.apache.maven.plugins和org.codehaus.mojo两个groupId下的插件才支持简化的命令行调用，即可以运行mvn help:system，但mvn jetty:run就不行了。

12.5 使用Cargo实现自动化部署

jetty-maven-plugin主要用来帮助日常的快速开发和测试，而cargo-maven2-plugin主要服务于自动化部署

也可以让Cargo部署应用至远程的正在运行的Web容器中。

### 版本管理
Maven的版本号定义约定是这样的：＜主版本＞.＜次版本＞.＜增量版本＞-＜里程碑版本＞

主版本：表示了项目的重大架构变更

次版本：表示较大范围的功能增加和变化，及Bug修复

增量版本：一般表示重大Bug的修复

里程碑版本：顾名思义，这往往指某一个版本的里程碑

这样的版本与正式的3.0相比，往往表示不是非常稳定，还需要很多测试

> 而对于里程碑版本，Maven则只进行简单的字符串比较，

#### 自动化版本发布
熟悉了版本发布流程之后，就会希望借助工具将这一流程自动化。Maven Release Plugin就提供了这样的功能

Maven Release Plugin主要有三个目标，它们分别为

release:prepare　准备版本发布，

release:rollback　回退release:prepare所执行的操作

release:perform　执行版本发布

一般配置项目的SCM信息如代码清单13-1所示。

＜tagBase＞https://192.168.1.103/app/tags/＜/tagBase＞

maven-release-plugin提供了autoVersionSubmodules参数

是怎样生成-sources.jar和-javadoc.jar的呢？

Profile是指一段在特定情况下被激活并更改Maven行为的配置，本书后续会有专门的章节详细阐述。

在日常的快照开发过程中，往往没有必要每次都生成-source.jar和-javadoc.jar，但是当项目发布的时候，这些文件就显得十分重要

#### GPG签名
PGP（Pretty Good Privacy）就是这样一个用来帮助提高安全性的技术。

在使用GPG之前，先得为自己准备一个密钥对，即一个私钥和一个公钥。之后才可以使用私钥对文件进行签名，并且将公钥分发到公钥服务器供其他用户下载，用户可以使用公钥对签名进行验证。

这里的-a选项告诉GPG创建ASCII格式的输出，而-b选项则告诉GPG创建一个独立的签名文件

为了能让你的用户获取公钥并验证你分发的文件，需要将公钥分发到公钥服务器中

Maven GPG Plugin只需要提供几行简单的配置，它就能够帮我们自动完成签名这一工作

### 灵活的构建

Maven为了支持构建的灵活性，内置了三大特性，即属性、Profile和资源过滤

#### Maven属性

通过＜properties＞元素用户可以自定义一个或多个Maven属性，然后在POM的其他地方使用${属性名称}的方式引用该属性，

事实上这只是6类Maven属性中的一类而已。这6类属性分别为：

POM属性：用户可以使用该类属性引用POM文件中对应元素的值。例如${project.artifactId}就对应了＜project＞＜artifactId＞元素的值

Settings属性：与POM属性同理，用户使用以settings.开头的属性引用settings.xml文件中XML元素的值

Java系统属性：所有Java系统属性都可以使用Maven属性引用，例如${user.home}指向了用户目录。用户可以使用mvn help:system查看所有的Java系统属性。

环境变量属性：所有环境变量都可以使用以env.开头的Maven属性引用

14.2 构建环境的差异

手动往往就意味着低效和错误，因此需要找到一种方法，使它能够自动地应对构建环境的差异。

#### 资源过滤

需要让Maven解析资源文件中的Maven属性。

该插件就能够解析资源文件中的Maven属性，即开启资源过滤。

mvn的-P参数表示在命令行激活一个profile。

#### Maven Profile
如果用户希望某个profile默认一直处于激活状态，就可以配置settings.xml文件的active-Profiles元素，表示其配置的profile对于所有项目都处于激活状态

用户怎么知道哪些profile被激活了呢？maven-help-plugin提供了一个目标帮助用户了解当前激活的profile：$mvn help:active-profiles

可供pom中profile使用的元素非常多，在pom profile中用户可以修改或添加仓库、插件仓库以及部署仓库地址；可以修改或者添加项目依赖；可以修改聚合项目的聚合配置；可以自由添加或修改Maven属性；添加或修改项目报告配置；pom profile还可以添加或修改插件配置、项目资源目录和测试资源目录配置以及项目构件的默认名称。

在pom.xml外部的profile只能够声明如代码清单14-17所示几个元素。

#### Web资源过滤

最后需要配置maven-war-plugin对src/main/webapp/这一web资源目录开启过滤

### 项目站点
Maven 3用户必须使用3.x版本的maven-site-plugin

如果想在本地查看结构正确的站点，则可以maven-site-plugin的stage目标，将站点预发布至某个本地临时目录下。

；site阶段绑定到了maven-site-plugin的site目标

15.2 丰富项目信息

这其实是由一个名为maven-project-info-reports-plugin的插件生成的

依赖收敛（Dependency Convergence）：只针对多模块项目生成，提供一些依赖健康状况分析，如各模块使用的依赖版本是否一致、项目中是否有SNAPSHOT依赖。

这时可以配置maven-project-info-reports-plugin选择性地生成信息项，


