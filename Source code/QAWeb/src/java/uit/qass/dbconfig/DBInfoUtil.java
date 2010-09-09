/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uit.qass.dbconfig;

/**
 *
 * @author ThuanHung
 */
public class DBInfoUtil {
    private static DBInfo   dbinfo;
static
    {
        TableInfo dblp_author_ref_new   =   new TableInfo("dblp_author_ref_new", "Author");
        ColumnInfo author               =   new ColumnInfo("author", "Author", Type.STRING);
        ColumnInfo editor               =   new ColumnInfo("editor", "Editor", Type.INTEGER);
        TableInfo dblp_pub_new          =   new TableInfo("dblp_pub_new", "Publisher");
        ColumnInfo title                =   new ColumnInfo("title", "Title", Type.STRING);
        ColumnInfo publisher            =   new ColumnInfo("publisher", "Publisher", Type.STRING);
        ColumnInfo year                 =   new ColumnInfo("year", "Year", Type.INTEGER);

        editor.setIsVisible(true);
        dblp_author_ref_new.addColumn(editor);
        dblp_author_ref_new.addColumn(author);

        dblp_pub_new.addColumn(year);
        dblp_pub_new.addColumn(title);
        dblp_pub_new.addColumn(publisher);





        dbinfo    =   new DBInfo();
        dbinfo.addTable(dblp_author_ref_new);
        dbinfo.addTable(dblp_pub_new);


    }
    public static DBInfo getDBInfo()
    {
    return dbinfo;
    }
}
