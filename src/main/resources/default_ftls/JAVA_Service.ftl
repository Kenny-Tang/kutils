KPATH:src/main/java/${groupId }.${artifactId }.service
package ${groupId }.${artifactId }.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ${groupId }.${artifactId }.mapper.${table.upperCamelTable }Mapper;
import ${groupId }.${artifactId }.model.${table.upperCamelTable };

@Service
public class ${table.upperCamelTable }Service {

    @Autowired
	private ${table.upperCamelTable }Mapper ${table.lowerCamelTable }Mapper;
	
	public ${table.upperCamelTable } queryUiq${table.upperCamelTable }() {
		return ${table.lowerCamelTable }Mapper.queryUiq${table.upperCamelTable }();
	}

	public List<${table.upperCamelTable }> query() {
		return ${table.lowerCamelTable }Mapper.query();
	}

	public int save(${table.upperCamelTable } entity) {
		${table.lowerCamelTable }Mapper.save(entity);
		return 0;
	}

	public void update(${table.upperCamelTable } entity) {
		${table.lowerCamelTable }Mapper.update(entity);
	}

}