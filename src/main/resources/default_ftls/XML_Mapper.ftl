<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${table.upperCamelTable }">
	
	<resultMap id="${table.upperCamelTable }ResultMap" type="${table.upperCamelTable }">
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
  
  <insert id="save" resultMap="${table.upperCamelTable }ResultMap">
 		insert into ${table.tableName?lower_case } (id , optimistic, customer_no, customer_name, fee_type, cust_buss_brand_code, order_no, external_id, fee, trans_time, order_status , create_time, update_time) 
 		values(sys_guid(), 0, ${r"#{"}customerNo ${r"}"} ${r"#{"}customerName ${r"}"} ${r"#{"}feeType ${r"}"} ${r"#{"}custBussBrandCode ${r"}"} ${r"#{"}orderNo ${r"}"} ${r"#{"}externalId ${r"}"} ${r"#{"}fee ${r"}"} ${r"#{"}transTime } , ${r"#{"}orderStatus ${r"}"} sysdate, ${r"#{"}updateTime} )
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