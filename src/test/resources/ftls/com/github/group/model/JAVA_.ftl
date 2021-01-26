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