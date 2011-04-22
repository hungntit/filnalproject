/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uit.qabpss.dbconfig;

/**
 *
 * @author ThuanHung
 */
public class MappingTable {
    protected String name;
    protected String tableKey;
    protected String relatedTableKey;
    protected String mappingTableName;

    public String getMappingTableName() {
        return mappingTableName;
    }

    public void setMappingTableName(String mappingTableName) {
        this.mappingTableName = mappingTableName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelatedTableKey() {
        return relatedTableKey;
    }

    public void setRelatedTableKey(String relatedTableKey) {
        this.relatedTableKey = relatedTableKey;
    }

    public String getTableKey() {
        return tableKey;
    }

    public void setTableKey(String tableKey) {
        this.tableKey = tableKey;
    }
    
}
