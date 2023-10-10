maven-surefire-plugin

#### configuration配置

＜include＞**/*Tests.java＜/include＞
> 两个星号**用来匹配任意路径，一个星号*匹配除路径风格符外的0个或者多个字符。

＜exclude＞**/*Test.java＜/exclude＞

### 测试覆盖率

Maven通过cobertura-maven-plugin与之集成，用户可以使用简单的命令为Maven项目生成测试覆盖率报告

> $mvn cobertura:cobertura

### TestNG
TestNG允许用户使用一个名为testng.xml的文件来配置想要运行的测试集合
> ＜suiteXmlFile＞testng.xml＜/suiteXmlFile＞
### 测试插件
插件就是maven-surefire-plugin，可以称之为测试运行器（Test Runner）

test阶段正是与maven-surefire-plugin的test目标相绑定了

maven-surefire-plugin的test目标会自动执行测试源码路径（默认为src/test/java/）

#### 跳过测试
$mvn package-DskipTests

```xml
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-surefire-plugin</artifactId>
        <configuration>
          <skipTests>true</skipTests>
        </configuration>
      </plugin>
```

$mvn package-Dmaven.test.skip=true
> 参数maven.test.skip同时控制了maven-compiler-plugin和maven-surefire-plugin两个插件的行为，测试代码编译跳过了，测试运行也跳过了

跳过测试编译
```xml
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <configuration>
          <skip>true</skip>
        </configuration>
      </plugin>
```

### 测试类执行
$mvn test-Dtest=XXXTest 
> test参数的值是测试用例的类名

`批量执行`
1. 星号可以匹配零个或多个字符
2. 使用逗号指定多个测试用例

> 加上-DfailIfNoTests=false，告诉maven-surefire-plugin即使没有任何测试也不要报错


### 测试类打包

Maven用户就需要通过配置maven-jar-plugin将测试类打包

```xml
            <plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-jar-plugin</artifactId>
				<executions>
					<execution>
						<goals>
							<goal>test-jar</goal>
						</goals>
					</execution>
				</executions>
			</plugin>
```

maven-jar-plugin有两个目标，分别是jar和test-jar，前者通过Maven的内置绑定在default生命周期的package阶段运行，其行为就是对项目主代码进行打包，而后者并没有内置绑定

test-jar的默认绑定生命周期阶段为package

上述依赖声明中有一个特殊的元素type，所有测试包构件都使用特殊的test-jar打包类型

```xml
        <dependency>
			<groupId>xxx</groupId>
			<artifactId>xxx</artifactId>
			<version>xxx</version>
			<type>test-jar</type>
			<scope>test</scope>
		</dependency>
```


TestNG较JUnit的一大优势在于它支持测试组的概念，如下的注解会将测试方法加入到两个测试组util和medium中：

> @Test（groups={"a"，"b"}）
> ＜groups＞a,b＜/groups＞