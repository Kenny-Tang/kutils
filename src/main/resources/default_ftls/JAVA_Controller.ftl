KPATH:src/main/java/${groupId }.${artifactId }.controller
package ${groupId }.${artifactId }.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import ${groupId }.${artifactId }.service.${table.upperCamelTable }Service;

@RestController
public class ${table.upperCamelTable }Controller {

    @Autowired
    private ${table.upperCamelTable }Service ${table.lowerCamelTable }Service ;
}