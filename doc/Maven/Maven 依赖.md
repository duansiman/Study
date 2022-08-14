### 坐标
Maven定义了这样一组规则：世界上任何一个构件都可以使用Maven坐标唯一标识，

Maven坐标的元素包括groupId、artifactId、version、packaging、classifier。

只要我们提供正确的坐标元素，Maven就能找到对应的构件

`坐标详解`

- groupId：定义当前Maven项目隶属的实际项目, Maven项目和实际项目不一定是一对一的关系
- artifactId：该元素定义实际项目中的一个Maven项目（模块），推荐的做法是使用实际项目名称作为artifactId的前缀。
- packaging：该元素定义Maven项目的打包方式
- classifier：该元素用来帮助定义构建输出的一些附属构件
> javadoc和sources就是这两个附属构件的classifier。
> 
> 不能直接定义项目的classifier，因为附属构件不是项目直接默认生成的，而是由附加的插件帮助生成。