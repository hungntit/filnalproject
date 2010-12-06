package uit.qabpss.dbconfig;


public class testXMLReader {
	public static void main(String args[]){
		XMLReader rd = new XMLReader();
//		System.out.println(rd.relationWordMapping("be publish"));
//                System.out.println(rd.getValueElement("publication",XMLReader.TABLENAME));
//                System.out.println(rd.getValueElement("publication",XMLReader.PRIMARYKEY));
                DBInfo mytestDBInfo = rd.loadDBInfo();
                System.out.println(mytestDBInfo.getTables().get(0).getColumns().get(0).relation.get(0).getRelationName());

                TableInfo tbTest = rd.getRelatedTable("author");
                System.out.println(tbTest.getAliasName());
                System.out.println(tbTest.getName());
                System.out.println(tbTest.getPrimaryKey());
                System.out.println("-------------------");
                MappingTable mapTest = rd.getMappingTable("pub_and_pub");
                System.out.println(mapTest.getName());
                System.out.println(mapTest.getMappingTableName());
                System.out.println(mapTest.getRelatedTableKey());
                System.out.println(mapTest.getTableKey());
	}
}
