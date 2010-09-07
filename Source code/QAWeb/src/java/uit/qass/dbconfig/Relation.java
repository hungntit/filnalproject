/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uit.qass.dbconfig;

/**
 *
 * @author ThuanHung
 */
public class Relation {

    protected String relationName;
    protected String relatedTable;
    protected String mappingTable;

    /**
     * Get the value of mappingTable
     *
     * @return the value of mappingTable
     */
    public String getMappingTable() {
        return mappingTable;
    }

    /**
     * Set the value of mappingTable
     *
     * @param mappingTable new value of mappingTable
     */
    public void setMappingTable(String mappingTable) {
        this.mappingTable = mappingTable;
    }

    /**
     * Get the value of relatedTable
     *
     * @return the value of relatedTable
     */
    public String getRelatedTable() {
        return relatedTable;
    }

    /**
     * Set the value of relatedTable
     *
     * @param relatedTable new value of relatedTable
     */
    public void setRelatedTable(String relatedTable) {
        this.relatedTable = relatedTable;
    }

    /**
     * Get the value of relationName
     *
     * @return the value of relationName
     */
    public String getRelationName() {
        return relationName;
    }

    /**
     * Set the value of relationName
     *
     * @param relationName new value of relationName
     */
    public void setRelationName(String relationName) {
        this.relationName = relationName;
    }

}
