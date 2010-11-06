package uit.qabpss.dbconfig;


public class testXMLReader {
	public static void main(String args[]){
		XMLReader rd = new XMLReader();
//		System.out.println(rd.relationWordMapping("be publish"));
//                System.out.println(rd.getValueElement("publication",XMLReader.TABLENAME));
//                System.out.println(rd.getValueElement("publication",XMLReader.PRIMARYKEY));
                DBInfo mytestDBInfo = rd.loadDBInfo();
                System.out.println(mytestDBInfo.getTables().get(0).getColumns().get(0).relation.get(0).getRelationName());
	}
}
