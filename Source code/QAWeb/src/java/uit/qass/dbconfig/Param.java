/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uit.qass.dbconfig;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import uit.qass.util.StringPool;

/**
 *
 * @author ThuanHung
 */
public class Param implements Serializable{

    protected TableInfo table;
    protected ColumnInfo column;
    protected String value;
    protected String operator;

    /**
     * Get the value of operator
     *
     * @return the value of operator
     */
    public String getOperator() {
        return operator;
    }

    /**
     * Set the value of operator
     *
     * @param operator new value of operator
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * Get the value of value
     *
     * @return the value of value
     */
    public String getValue() {
        return value;
    }

    /**
     * Set the value of value
     *
     * @param value new value of value
     */
    public void setValue(String value) {
        this.value = value;
    }


    public Param(DBInfo database,String tableName,String coumnName) {

        if(database != null)
            table   =   database.findTableInfoByName(tableName);
        if(table != null)
            column  =   this.table.findColumnByName(coumnName);
        this.value  =   "";
        this.operator   =   StringPool.EQUAL;
    }

    public Param(TableInfo table, ColumnInfo column) {
        this.table = table;
        this.column = column;
        this.value  =   "";
        this.operator   =   StringPool.EQUAL;
    }


    /**
     * Get the value of column
     *
     * @return the value of column
     */
    public ColumnInfo getColumn() {
        return column;
    }

    /**
     * Set the value of column
     *
     * @param column new value of column
     */
    public void setColumn(ColumnInfo column) {
        this.column = column;
    }

    /**
     * Get the value of table
     *
     * @return the value of table
     */
    public TableInfo getTable() {
        return table;
    }

    /**
     * Set the value of table
     *
     * @param table new value of table
     */
    public void setTable(TableInfo table) {
        this.table = table;
    }

    @Override
    public String toString() {
        return "`"+table+"`"    +"."+   "`"+column+"`";
    }

    public static List<Param> getParamsFromTableInfo(TableInfo table)
    {
        List<Param> result  =   new ArrayList<Param>();
         int size    =   table.getColumns().size();
        if(size<1)
            return null;

        for(int i=0;i<size;i++)
        {
            ColumnInfo column   =   table.getColumns().get(i);
            if(column.isVisible)
            {
             Param param   =   new Param( table, column);
             result.add(param);
            }
        }
         if(result.size()==0)
             return null;
         return result;

    }


}
