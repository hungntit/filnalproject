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
public class TableInfo implements Serializable{
    protected List<ColumnInfo>  columns;
    protected String name;
    protected String aliasName;
    protected Class  classTable;
    protected String primaryKey;
    protected ColumnInfo presentationField  =   null;

    public ColumnInfo getPresentationField() {
        return presentationField;
    }

    public void setPresentationField(ColumnInfo presentationField) {
        this.presentationField = presentationField;
    }

    /**
     * Get the value of primaryKey
     *
     * @return the value of primaryKey
     */
    public String getPrimaryKey() {
        return primaryKey;
    }

    /**
     * Set the value of primaryKey
     *
     * @param primaryKey new value of primaryKey
     */
    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public Class getClassTable() {
        return classTable;
    }

    public void setClassTable(Class classTable) {
        this.classTable = classTable;
    }

    public TableInfo() {
        this.columns    =   new ArrayList<ColumnInfo>();
    
    }

    public List<ColumnInfo> getColumns() {
        return columns;
    }

    public TableInfo(String name, String aliasName) {
        this.name = name;
        this.aliasName = aliasName;
        this.columns    =   new ArrayList<ColumnInfo>();
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


    /**
     * Get the value of aliasName
     *
     * @return the value of aliasName
     */
    public String getAliasName() {
        return aliasName;
    }

    /**
     * Set the value of aliasName
     *
     * @param aliasName new value of aliasName
     */
    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }
    public void addColumn(ColumnInfo column)
    {
        if(!columns.contains(column))
            columns.add(column);
        if(column.isPresentation)
        {
            presentationField   =   column;
        }
    }
    public ColumnInfo findColumnByName(String columnName)
    {
        if(columnName == null)  return null;
        if(columnName.trim().equals(""))  return null;
        for(ColumnInfo column: columns)
        {
            if(column.getName().equalsIgnoreCase(columnName))
                return column;
        }
        return null;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TableInfo other = (TableInfo) obj;
        
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if ((this.aliasName == null) ? (other.aliasName != null) : !this.aliasName.equals(other.aliasName)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.columns != null ? this.columns.hashCode() : 0);
        hash = 67 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 67 * hash + (this.aliasName != null ? this.aliasName.hashCode() : 0);
        return hash;
    }

}
