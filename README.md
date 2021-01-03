# Table of Contents

* [KUTILS](#kutils)
  * [TemplateBuilder](#templatebuilder)
    * [模板示例](#模板示例)
  * [Dates](#dates)
  * [Excels](#excels)
  * [Files](#files)
  * [Numerics](#numerics)
  * [Strings](#strings)



@[TOC](KUTILS工具集GUID)
 此工具包的目的为致力于减少开发工作中的重复性工作，比如一些格式化和校验类的代码，当然随着JDK的更新一些工具化的操作已经有一部分集成进JDK中，已经非常遍历，此工具包希望作为一种补充，为开发人员减少些许劳动量。
# KUTILS
## TemplateBuilder
基于Freemarker 的代码生成器
### 模板示例
```
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

