# 目录

* [KUTILS](#kutils)
  * [TemplateBuilder](#templatebuilder)
    * [模板示例](#generateTemplate)
  * [Dates](#dates)
  * [Excels](#excels)
  * [Files](#files)
  * [Numerics](#numerics)
  * [Strings](#strings)



# KUTILS 
 此工具包的目的为致力于减少开发工作中的重复性工作，比如一些格式化和校验类的代码，当然随着JDK的更新一些工具化的操作已经有一部分集成进JDK中，已经非常遍历，此工具包希望作为一种补充，为开发人员减少些许劳动量。

## TemplateBuilder
基于Freemarker 的代码生成器
### <p id="generateTemplate">模板</p>
#### 模板文件命名

  文件命名格式为 `文件类型名称+下划线+文件后缀+.ftl`
  
  eg. JAVA_Service.ftl 将被转化为 `XXXService.java`
  
#### 模板文件内容

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
## Dates
 日期处理工具
## Excels
 Excel 工具
## Files
 文件处理工具
## Numerics
数值处理工具
## Strings
字符串处理工具

