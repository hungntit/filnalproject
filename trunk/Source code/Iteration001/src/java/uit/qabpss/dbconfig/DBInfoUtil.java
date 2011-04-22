/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.qabpss.dbconfig;

import uit.qabpss.model.Author;
import uit.qabpss.model.Publication;

/**
 *
 * @author ThuanHung
 */
public class DBInfoUtil {

    private static DBInfo dbinfo;
    private static DBInfo dbinfoXML = null;

     public static void initDb()
    {
        TableInfo dblp_author_ref_new   =   new TableInfo("dblp_author_ref_new", "Author");
        dblp_author_ref_new.setPrimaryKey("author");
        dblp_author_ref_new.setClassTable(Author.class);
        //clolumn at table Author
        ColumnInfo author               =   new ColumnInfo("author", "Author", Type.STRING);
        //clolumn at table publication        
        TableInfo dblp_pub_new          =   new TableInfo("dblp_pub_new", "Publication");
        ColumnInfo source               =   new ColumnInfo("source", "Source", Type.STRING);
        ColumnInfo titleSignature       =   new ColumnInfo("titleSignature", "Title", Type.STRING);
        ColumnInfo publisher            =   new ColumnInfo("publisher", "Publisher", Type.STRING);
        ColumnInfo year                 =   new ColumnInfo("year", "Year", Type.INTEGER);
        ColumnInfo type                 =   new ColumnInfo("type", "Type", Type.STRING);
//        ColumnInfo isbn                 =   new ColumnInfo("isbn", "Isbn", Type.CODE);
//        ColumnInfo doi                  =   new ColumnInfo("doi", "Doi", Type.CODE);
        
        type.setDefaultValuesSet(dblp_pub_new.getName(), type.name);
        
        dblp_pub_new.setClassTable(Publication.class);
        dblp_pub_new.setPrimaryKey("id");

        //add column to table
        dblp_author_ref_new.addColumn(author);
        
        //add column to table
        dblp_pub_new.addColumn(titleSignature);
        dblp_pub_new.addColumn(year);        
        dblp_pub_new.addColumn(publisher);
        dblp_pub_new.addColumn(source);
        dblp_pub_new.addColumn(type);
//        dblp_pub_new.addColumn(isbn);
//        dblp_pub_new.addColumn(doi);
        dbinfo    =   new DBInfo("dblp");
        dbinfo.addTable(dblp_author_ref_new);
        dbinfo.addTable(dblp_pub_new);
    }
   public static void initDbXML() {
        if (dbinfoXML == null) {
            XMLReader xMLReader = new XMLReader();
            dbinfoXML = xMLReader.loadDBInfo();
        }
    }

    public static DBInfo getDBInfo() {
        return dbinfo;
    }

    public static DBInfo getDBInfoXML() {
        return dbinfoXML;
    }
}
