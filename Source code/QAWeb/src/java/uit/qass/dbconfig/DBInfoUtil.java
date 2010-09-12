/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uit.qass.dbconfig;

import uit.qass.model.Author;
import uit.qass.model.Publication;

/**
 *
 * @author ThuanHung
 */
public class DBInfoUtil {
    private static DBInfo   dbinfo;
static
    {
        TableInfo dblp_author_ref_new   =   new TableInfo("dblp_author_ref_new", "Author");
        dblp_author_ref_new.setClassTable(Author.class);
        ColumnInfo author               =   new ColumnInfo("author", "Author", Type.STRING);
        ColumnInfo editor               =   new ColumnInfo("editor", "Editor", Type.BOOLEAN);
        TableInfo dblp_pub_new          =   new TableInfo("dblp_pub_new", "Publication");
        ColumnInfo title                =   new ColumnInfo("title", "Title", Type.STRING);
        ColumnInfo publisher            =   new ColumnInfo("publisher", "Publisher", Type.STRING);
        ColumnInfo year                 =   new ColumnInfo("year", "Year", Type.INTEGER);
        dblp_pub_new.setClassTable(Publication.class);
        editor.setIsVisible(false);
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
