/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.qabpss.dbconfig;

import java.io.Serializable;

/**
 *
 * @author ThuanHung
 */
public class Relation implements Serializable {

    protected String relationName;
    protected String type;

    public Relation() {
        relationName = "";
        type = "";
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
