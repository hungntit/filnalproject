/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uit.qabpss.preprocess;

import uit.qabpss.dbconfig.ColumnInfo;
import uit.qabpss.dbconfig.TableInfo;

/**
 *
 * @author ThuanHung
 */
public class EntityType {

    protected String entityName;
    protected TableInfo tableInfo;
    protected ColumnInfo columnInfo;

    /**
     * Get the value of columnInfo
     *
     * @return the value of columnInfo
     */
    public ColumnInfo getColumnInfo() {
        return columnInfo;
    }

    /**
     * Set the value of columnInfo
     *
     * @param columnInfo new value of columnInfo
     */
    public void setColumnInfo(ColumnInfo columnInfo) {
        this.columnInfo = columnInfo;
    }

    /**
     * Get the value of tableInfo
     *
     * @return the value of tableInfo
     */
    public TableInfo getTableInfo() {
        return tableInfo;
    }

    /**
     * Set the value of tableInfo
     *
     * @param tableInfo new value of tableInfo
     */
    public void setTableInfo(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
    }

    /**
     * Get the value of entityName
     *
     * @return the value of entityName
     */
    public String getEntityName() {
        return entityName;
    }

    /**
     * Set the value of entityName
     *
     * @param entityName new value of entityName
     */
    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }
    public  boolean isTable()
    {
        if(columnInfo == null)
        {
            return true;
        }
        return false;
    }
    public  boolean isColumn()
    {
        if(columnInfo != null)
        {
            return true;
        }
        return false;
    }

    public EntityType(TableInfo tableInfo, ColumnInfo columnInfo) {
        this.tableInfo = tableInfo;
        this.columnInfo = columnInfo;
    }
   

    public EntityType() {
        entityName  =   "";
    }

}
