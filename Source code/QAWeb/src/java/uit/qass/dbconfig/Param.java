/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uit.qass.dbconfig;

/**
 *
 * @author ThuanHung
 */
public class Param {

    protected TableInfo table;
    protected ColumnInfo column;
    protected String value;

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
    }

    public Param(TableInfo table, ColumnInfo column) {
        this.table = table;
        this.column = column;
        this.value  =   "";
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



}
