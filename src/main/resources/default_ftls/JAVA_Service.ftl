KPATH:src/main/java/${groupId }.${artifactId }.service
package ${groupId }.${artifactId }.service;

import java.util.List;

import ${groupId }.${artifactId }.mapper.${table.upperCamelTable }Mapper;
import ${groupId }.${artifactId }.model.${table.upperCamelTable };

public class ${table.upperCamelTable }Service {

	private ${table.upperCamelTable }Mapper ${table.lowerCamelTable }Mapper;
	
	${table.upperCamelTable } queryUiq${table.upperCamelTable }() {
		return ${table.lowerCamelTable }Mapper.queryUiq${table.upperCamelTable }();
	}

	List<${table.upperCamelTable }> query() {
		return ${table.lowerCamelTable }Mapper.query();
	}

	int save(${table.upperCamelTable } entity) {
		${table.lowerCamelTable }Mapper.save(entity);
		return 0;
	}

	void update(${table.upperCamelTable } entity) {
		${table.lowerCamelTable }Mapper.update(entity);
	}

}