/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uit.qass.dbconfig;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ThuanHung
 */
public class TableInfo {
    protected List<ColumnInfo>  columns;
    protected String name;
    protected String aliasName;

    public TableInfo() {
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

}
