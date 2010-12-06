/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uit.qabpss.dbconfig;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import uit.qabpss.core.search.UtimateSearch;

/**
 *
 * @author ThuanHung
 */
public class ColumnInfo implements Serializable{

    public ColumnInfo() {
        isVisible   =   true;
        relation = new ArrayList<Relation>();
    }

    public ColumnInfo(String name, String aliasName, Type type) {
        this.name = name;
        this.aliasName = aliasName;
        this.type = type;
        isVisible   =   true;
    }

    protected String name;
    protected boolean isVisible;
    protected String aliasName;
    protected Type type;
    protected List<Relation> relation;
    protected  List<String> defaultValuesSet  = null;
    protected  String relationType;
    protected TableInfo relatedTable;
    protected MappingTable mappingTable;
    protected boolean isPresentation;
    protected String pattern;

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
//    protected TableInfo mappingTable;

    
    /**
     * Get the value of isPresentation
     *
     * @return the value of isPresentation
     */
    public boolean isIsPresentation() {
        return isPresentation;
    }

    /**
     * Set the value of isPresentation
     *
     * @param isPresentation new value of isPresentation
     */
    public void setIsPresentation(boolean isPresentation) {
        this.isPresentation = isPresentation;
    }

    public  void setDefaultValuesSet(String columnName,String tableName) {
        defaultValuesSet = UtimateSearch.getListDefaultValueFromColumn(tableName, columnName);
    }

    public MappingTable getMappingTable() {
        return mappingTable;
    }

    public void setMappingTable(MappingTable mappingTable) {
        this.mappingTable = mappingTable;
    }

    public TableInfo getRelatedTable() {
        return relatedTable;
    }

    public void setRelatedTable(TableInfo relatedTable) {
        this.relatedTable = relatedTable;
    }
    
    /**
     * Get the value of defaultValue
     *
     * @return the value of defaultValue
     */
    public  List<String> getDefaultValuesSet() {
        return defaultValuesSet;
    }

    public List<Relation> getRelation() {
        return relation;
    }

    public void setRelation(List<Relation> relation) {
        this.relation = relation;
    }

    public void addRelation(Relation relation) {
        this.relation.add(relation);
    }

    /**
     * Get the value of type
     *
     * @return the value of type
     */
    public Type getType() {
        return type;
    }

    /**
     * Set the value of type
     *
     * @param type new value of type
     */
    public void setType(String type) {
        if("String".equals(type)){
            this.type = Type.STRING;
        }
        if("Int".equals(type)){
            this.type = Type.INTEGER;
        }
    }

 

    public boolean isIsVisible() {
        return isVisible;
    }

    public void setIsVisible(String isVisible) {
        if("true".equals(isVisible)){
            this.isVisible = true;
        } else{
            this.isVisible = false;
        }
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

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }
    public boolean isRelatedField()
    {
        if(relationType.equals(RELATED_TABLE))
        {
            return true;

        }
        return false;
    }
    public static final String RELATED_TABLE  =   "related table";
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ColumnInfo other = (ColumnInfo) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if (this.isVisible != other.isVisible) {
            return false;
        }
        if ((this.aliasName == null) ? (other.aliasName != null) : !this.aliasName.equals(other.aliasName)) {
            return false;
        }
        if (this.type != other.type && (this.type == null || !this.type.equals(other.type))) {
            return false;
        }
        if (this.relation != other.relation && (this.relation == null || !this.relation.equals(other.relation))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 29 * hash + (this.aliasName != null ? this.aliasName.hashCode() : 0);
        hash = 29 * hash + (this.type != null ? this.type.hashCode() : 0);
        hash = 29 * hash + (this.relation != null ? this.relation.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return this.name;
    }



}
