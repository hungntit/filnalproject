package uit.qabpss.dbconfig;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLReader {
    public static final String DATABASE = "database";
    public static final String DATABASE_NAME = "database-name";
    public static final String DEFAUFT_OPERATOR = "=";
    public static final String FIELD_NAME = "field-name";
    public static final String PRIMARYKEY = "primary-key";
    public static final String RELATED_TABLE = "related-table";
    public static final String REVERSED_RELATION = "reversed-relation";
    public static final String TABLE = "table";
    public static final String TABLENAME = "table-name";
    public static final String RELATION = "relation";
    public static final String FIELD = "field";
    public static final String FIELD_ALIAS = "field-alias";
    public static final String TABLE_ALIAS = "table-alias";
    public static final String RELATION_NAME = "relation-name";
    public static final String TYPE = "type";
    public static final String VALUE = "value";
    public static final String VISIBLE = "visible";
    private static final String PATH = "xmlconfig\\rel_config.xml";
    public Document doc = null;

    public XMLReader() {
        try {
            File file = new File(PATH);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DBInfo loadDBInfo(){
        DBInfo dbInfo = new DBInfo();
        NodeList nodeLst = doc.getElementsByTagName(DATABASE).item(0).getChildNodes();
        for (int i = 0; i < nodeLst.getLength(); i++) {
            Node n = nodeLst.item(i);
            if(DATABASE_NAME.equals(n.getNodeName())){
                dbInfo.setName(n.getTextContent());
            }
            if(TABLE.equals(n.getNodeName())){                
                NodeList tbChilds = n.getChildNodes();
                TableInfo tbInf = new TableInfo();
                tbInf.setAliasName(n.getAttributes().getNamedItem(TABLE_ALIAS).getNodeValue());
                tbInf.setClassTable(null);
                for (int j = 0; j < tbChilds.getLength(); j++) {
                    Node temp = tbChilds.item(j);
                    if(TABLENAME.equals(temp.getNodeName())){
                        tbInf.setName(temp.getTextContent());
                    }
                    if(PRIMARYKEY.equals(temp.getNodeName())){
                        tbInf.setPrimaryKey(temp.getTextContent());
                    }
                    if(FIELD.equals(temp.getNodeName())){
                        ColumnInfo colInf = new ColumnInfo();
                        NodeList childFields = temp.getChildNodes();
                        colInf.setAliasName(temp.getAttributes().getNamedItem(FIELD_ALIAS).getNodeValue());
                        colInf.setIsVisible(temp.getAttributes().getNamedItem(VISIBLE).getNodeValue());
//                        colInf.setName(temp.getAttributes().getNamedItem(FIELD_NAME).getNodeValue());
                        colInf.setType(temp.getAttributes().getNamedItem(TYPE).getNodeValue());
                        colInf.setRelationType(temp.getAttributes().getNamedItem(RELATION).getNodeValue());
                        for (int k = 0; k < childFields.getLength(); k++) {
                            Node childField = childFields.item(k);
                            if(FIELD_NAME.equals(childField.getNodeName())){
                                colInf.setName(childField.getTextContent());
                            }
                            if(RELATION.equals(childField.getNodeName())){
                                NodeList relList = childField.getChildNodes();                                
                                for (int l = 0; l < relList.getLength(); l++) {
                                    Node rel = relList.item(l);                                    
                                    if(RELATION_NAME.equals(rel.getNodeName())){
                                        Relation fieldRel = new Relation();
                                        try{                                                                                    
                                            fieldRel.setOperator(rel.getAttributes().getNamedItem(VALUE).getNodeValue());                                           
                                        }catch(Exception e){
                                            fieldRel.setOperator(DEFAUFT_OPERATOR);
                                        }
                                        fieldRel.setRelationName(rel.getTextContent());
                                        fieldRel.setType(RELATION);
                                        colInf.addRelation(fieldRel);
                                    }
                                    if(REVERSED_RELATION.equals(rel.getNodeName())){
                                        Relation fieldRel = new Relation();
                                        try{
                                            fieldRel.setOperator(rel.getAttributes().getNamedItem(VALUE).getNodeValue());                                            
                                        }catch(Exception e){
                                            fieldRel.setOperator(DEFAUFT_OPERATOR);
                                        }
                                        fieldRel.setRelationName(rel.getTextContent());
                                        fieldRel.setType(REVERSED_RELATION);
                                        colInf.addRelation(fieldRel);
                                    }
                                    if(RELATED_TABLE.equals(rel.getNodeName())){
                                        colInf.setRelatedTable(rel.getTextContent());
                                    }                                                                      
                                }
                            }
                        }
                        tbInf.addColumn(colInf);
                    }
                }
                dbInfo.addTable(tbInf);
            }
        }
        return dbInfo;
    }
    
//    public String relationWordMapping(String word) {
//        String firstObj = null;
//        String secondObj = null;
//        String rel = null;
//        boolean isReverse = false;
//        Node temp = null;
//        Node n = null;
//        doc.getDocumentElement().normalize();
//        // get relation from tags relation name
//        NodeList nodeLst = doc.getElementsByTagName(RELATION_NAME);
//        for (int i = 0; i < nodeLst.getLength(); i++) {
//            n = nodeLst.item(i);
//            if (word.equals(n.getTextContent())) {
//                temp = n.getParentNode();
//            }
//        }
//        // get reversed relation from tags relation name
//        if (temp == null) {
//            isReverse = true;
//            nodeLst = doc.getElementsByTagName(REVERSED_RELATION);
//            for (int i = 0; i < nodeLst.getLength(); i++) {
//                n = nodeLst.item(i);
//                if (word.equals(n.getTextContent())) {
//                    System.out.println(n.getTextContent());
//                    temp = n.getParentNode();
//                }
//            }
//        }
//        // not found any relations
//        if (temp == null) {
//            return null;
//        }
//        // get first object, second object and their relationship
//        while (temp != null) {
//            if (TABLE.equals(temp.getNodeName())
//                    && temp.getAttributes().getNamedItem(TABLE_ALIAS) != null) {
//                firstObj = temp.getAttributes().getNamedItem(TABLE_ALIAS).getNodeValue();
//            }
//            if (FIELD.equals(temp.getNodeName())
//                    && temp.getAttributes().getNamedItem(FIELD_ALIAS) != null) {
//                secondObj = temp.getAttributes().getNamedItem(FIELD_ALIAS).getNodeValue();
//                rel = temp.getAttributes().getNamedItem(RELATION).getNodeValue();
//            }
//            temp = temp.getParentNode();
//        }
//
//        if (!isReverse) {
//            return firstObj + "-" + rel + "-" + secondObj;
//        } else {
//            return secondObj + "-" + rel + "-" + firstObj;
//        }
//    }
//
//    public String getValueElement(String aliasTableName,String tagName){
//        String result = null;
//        if(aliasTableName.isEmpty() || tagName.isEmpty()){
//            return null;
//        }
//
//        try{
//        doc.getDocumentElement().normalize();
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//
//        Node n = null;
//        // get relation from tags relation name
//        NodeList nodeLst = doc.getElementsByTagName(TABLE);
//        for (int i = 0; i < nodeLst.getLength(); i++) {
//            n = nodeLst.item(i);
//            if (aliasTableName.equals(n.getAttributes().getNamedItem(TABLE_ALIAS).getNodeValue())) {
//                result = n.getOwnerDocument().getElementsByTagName(tagName).item(0).getTextContent();
//            }
//        }
//        return result;
//    }

    
}
