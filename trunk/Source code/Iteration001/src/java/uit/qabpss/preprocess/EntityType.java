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
    public boolean isNull()
    {
        if(tableInfo == null)
            return true;
        return false;
    }
    public EntityType(TableInfo tableInfo, ColumnInfo columnInfo) {
        this.tableInfo = tableInfo;
        this.columnInfo = columnInfo;
    }

    public EntityType() {
        this.tableInfo = null;
        this.columnInfo = null;
    }
   

    

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        EntityType entityType   =   (EntityType)obj;

        if(tableInfo == entityType.tableInfo && columnInfo  ==  entityType.columnInfo )
        {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        if(isNull())
            return "Not indentified :"+ super.toString();
        else
        {
            if(isColumn())
            {
                return tableInfo.getAliasName()+"."+ columnInfo.getAliasName();
            }
            return  tableInfo.getAliasName();
        }
    }
    public EntityType getMainEntityType()
    {
        if(this.isColumn())
        {

            if(this.getColumnInfo().isRelatedField())
            {
                TableInfo   tableInfo   =   this.getColumnInfo().getRelatedTable();
                ColumnInfo  columnInfo  =   null;
                if(this.getColumnInfo().getName() != null)
                {
                      columnInfo  =   tableInfo.findColumnByName(this.getColumnInfo().getName());
                }
                return new EntityType(tableInfo, columnInfo);
            }
        }
        return this;

    }
}
