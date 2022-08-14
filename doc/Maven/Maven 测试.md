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

TestNG较JUnit的一大优势在于它支持测试组的概念，如下的注解会将测试方法加入到两个测试组util和medium中：

> @Test（groups={"a"，"b"}）
> ＜groups＞a,b＜/groups＞