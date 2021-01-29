<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${table.upperCamelTable }">
	
	<resultMap id="${table.upperCamelTable }ResultMap" type="${table.upperCamelTable }">
		<#list table.columns as field>
		<result column="${field.columnName }" property="${field.lowerCamelColumn }" jdbcType="${field.jdbcType }" />
		</#list>
	</resultMap>
	   
	<sql id="${table.upperCamelTable }Columns">
		<#list table.columns as field>${field.columnName?lower_case }<#if field_has_next>,</#if> </#list>
	</sql>
   
  <select id="queryUiq${table.upperCamelTable }" resultMap="${table.upperCamelTable }ResultMap">
    select <include refid="${table.upperCamelTable}Columns" />
    from ${table.tableName?lower_case } t
    where 1=1
  </select>
  
  <select id="query" resultMap="${table.upperCamelTable }ResultMap">
    select <include refid="${table.upperCamelTable}Columns" />
    from ${table.tableName?lower_case } t
    where 1=1
  </select>
  
  <insert id="save" parameterType="${table.upperCamelTable }">
 		insert into ${table.tableName?lower_case } (<include refid="${table.upperCamelTable}Columns" />) 
 		values(<#list table.columns as field>${r"#{"}${field.lowerCamelColumn }${r"}"}<#if field_has_next>,</#if> </#list>)
  </insert>
  
  <update id="update" parameterType="${table.upperCamelTable }">
  	update ${table.tableName?lower_case } t 
  	set 
  		<#list table.columns as field>
  		${field.columnName?lower_case } = ${r"#{"}${field.lowerCamelColumn } ${r"}"}<#if field_has_next>,</#if> 
  		</#list>
  	where id = ${r"#{"}id ${r"}"}
  </update>
</mapper>