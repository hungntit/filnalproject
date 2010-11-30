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
    private static DBInfo   dbinfo;
    public static void initDb()


    {
        XMLReader xMLReader =   new XMLReader();
        dbinfo  =   xMLReader.loadDBInfo();

    }
    public static DBInfo getDBInfo()
    {
    return dbinfo;
    }
}
