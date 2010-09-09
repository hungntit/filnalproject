/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uit.qass.dbconfig;

/**
 *
 * @author ThuanHung
 */
public class ColumnInfo implements Serializable{

    public ColumnInfo() {
        isVisible   =   true;
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
    protected Relation relation;

    /**
     * Get the value of relation
     *
     * @return the value of relation
     */
    public Relation getRelation() {
        return relation;
    }

    /**
     * Set the value of relation
     *
     * @param relation new value of relation
     */
    public void setRelation(Relation relation) {
        this.relation = relation;
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
    public void setType(Type type) {
        this.type = type;
    }

 

    public boolean isIsVisible() {
        return isVisible;
    }

    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
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
