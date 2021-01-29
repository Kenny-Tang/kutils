/**
 * @author  ${author} 
 * @ClassName: ${table.upperCamelTable }Mapper   
 * 
 * @date ${ curentTime}
 */
public interface ${table.upperCamelTable }Mapper {

	 ${table.upperCamelTable } queryUiq${table.upperCamelTable }();
	
	List< ${table.upperCamelTable }> query();
	
	int save( ${table.upperCamelTable } order) ;

	void update( ${table.upperCamelTable } order);

}
