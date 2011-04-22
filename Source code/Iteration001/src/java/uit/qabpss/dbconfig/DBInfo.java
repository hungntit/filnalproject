/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uit.qabpss.dbconfig;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ThuanHung
 */
public class DBInfo implements Serializable{

    protected String name;
    protected List<TableInfo>   tables;

    public DBInfo() {
        tables      =  new ArrayList<TableInfo>();
    }

    public DBInfo(String name) {
        this.name = name;
        tables      =  new ArrayList<TableInfo>();
    }

    public List<TableInfo> getTables() {
        return tables;
    }

    public void setTables(List<TableInfo> tables) {
        this.tables = tables;
    }

    public void addTable(TableInfo table)
    {
        tables.add(table);
    }

    public  TableInfo findTableInfoByName(String tableName)
    {
        if(tableName == null) return null;
        if(tableName.trim().equals("")) return null;
        for(TableInfo table:tables)
        {
            if(table.getName().equalsIgnoreCase(tableName))
                return table;
        }
        return null;
    }
    public  TableInfo findTableInfoByAliasName(String tableAliasName)
    {
        if(tableAliasName == null) return null;
        if(tableAliasName.trim().equals("")) return null;
        for(TableInfo table:tables)
        {
            if(table.getAliasName().equalsIgnoreCase(tableAliasName))
                return table;
        }
        return null;
    }
    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    public void setName(String name) {
        this.name = name;
    }

}
