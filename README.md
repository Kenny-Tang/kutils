# 目录

* [KUTILS](#kutils)
  * [TemplateBuilder](#templatebuilder)
    * [模板](#generateTemplate)
      * [模板命名](#generateTemplate)
      * [模板内容](#generateTemplate)
      * [使用](#TemplateBuilderUse)
  * [Dates](#dates)
  * [Excels](#excels)
  * [Files](#files)
  * [Numerics](#numerics)
  * [Strings](#strings)



# KUTILS 
 此工具包的目的为致力于减少开发工作中的重复性工作，比如一些格式化和校验类的代码，当然随着JDK的更新一些工具化的操作已经有一部分集成进JDK中，已经非常遍历，此工具包希望作为一种补充，为开发人员减少些许劳动量。

## TemplateBuilder
基于Freemarker 的代码生成器
### <span id="generateTemplate">模板</span>
#### 模板命名

  文件命名格式为 `文件类型名称+下划线+文件后缀+.ftl`
  
  eg. JAVA_Service.ftl 将被转化为 `XXXService.java`
  
#### 模板内容

模板文件需要使用 `freemarker` 的语法规范进行编写，示例如下：

```freemarker
package com.github.group.model;

import java.io.Serializable;
<#list table.imports as import>
import ${import };
</#list>

public class ${table.upperCamelTable } implements Serializable {

	private static final long serialVersionUID = 1L;
	
	<#list table.columns as field>
	private ${field.javaTypeSimpleName } ${field.lowerCamelColumn} ;
	</#list>
	
	<#list table.columns as field>
	public void set${field.upperCamelColumn}(${field.javaTypeSimpleName } ${field.lowerCamelColumn}) {
		this.${field.lowerCamelColumn} = ${field.lowerCamelColumn};
	}
	
	public ${field.javaTypeSimpleName } get${field.upperCamelColumn}() {
		return ${field.lowerCamelColumn};
	}
	
	</#list>
}
```
#### <span id="TemplateBuilderUse">使用</span>
一般我们可以用单元测试的形式在项目中使用, 这样生成的文件就可以直接在项目中使用了

```java
  @Test
  public void testGenerate() {
    TemplateBuilder builder = new TemplateBuilderFactory().build() ;
    TableInfoLoader dataLoader = (TableInfoLoader) builder.getDataLoader() ;
    dataLoader.setTable("user");
    builder.generate();
  }
```
## Dates
 日期处理工具
## Excels
 该工具主要是在导出数据事使用，一般来说 excel 类型文件的导出是比较常见的功能，而且格式也是很固定的，比较适合做成工具使用。
这里主要有两种数据导出格式，一种是数据量较少时，使用通用常见的 `xlsx` 类型的文件导出，但是如果导出的文件数据量较大，使用xlsx类型文件的话可能会消耗过多的内存，这种情况下我们可以选择 xml 类型的文件的导出。
xlsx 文件底层实现是使用的xml实现的，因此我们导出的xml格式的文件可以直接使用Office Excel 软件直接打开操作即可，我们在拼接xml 字符串时不会像使用POI框架一样产生大量的对象，减少了内存的支出因此在同样的内存下可以导出更多的数据。
## Files
 文件处理工具
## Numerics
数值处理工具
## Strings
字符串处理工具

