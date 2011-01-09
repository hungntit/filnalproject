/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uit.qabpss.extracttriple;

import uit.qabpss.dbconfig.Relation;
import uit.qabpss.preprocess.EntityType;

/**
 *
 * @author ThuanHung
 */
public class TripleRelation {

    protected EntityType firstEntity;
    protected Relation relation;
    protected EntityType secondEntity;

    public TripleRelation(EntityType firstEntity, Relation relation, EntityType secondEntity) {
        this.firstEntity = firstEntity;
        this.relation = relation;
        this.secondEntity = secondEntity;
    }


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
     * Get the value of secondEntity
     *
     * @return the value of secondEntity
     */
    public EntityType getSecondEntity() {
        return secondEntity;
    }

    /**
     * Set the value of secondEntity
     *
     * @param secondEntity new value of secondEntity
     */
    public void setSecondEntity(EntityType secondEntity) {
        this.secondEntity = secondEntity;
    }

    /**
     * Get the value of firstEntity
     *
     * @return the value of firstEntity
     */
    public EntityType getFirstEntity() {
        return firstEntity;
    }

    /**
     * Set the value of firstEntity
     *
     * @param firstEntity new value of firstEntity
     */
    public void setFirstEntity(EntityType firstEntity) {
        this.firstEntity = firstEntity;
    }

    @Override
    public boolean equals(Object obj) {
        TripleRelation tr   =   (TripleRelation)obj;
        if(this.firstEntity!=null)
        {
            if(!this.firstEntity.equals(tr.firstEntity))
                return false;
        }
        if(this.secondEntity!=null)
        {
            if(!this.secondEntity.equals(tr.secondEntity))
                return false;
        }
        
        return  true;

    }

    public boolean isSameTable(TripleRelation tr)
    {
        if(this.firstEntity!=null&& tr.firstEntity!=null)
        {
            if(!this.firstEntity.getTableInfo().getName().equals(tr.firstEntity.getTableInfo().getName()))
                return false;
            else
            {
                if(this.secondEntity!=null && tr.secondEntity!=null)
                {
                    if(!this.secondEntity.getColumnInfo().getName().equals(tr.secondEntity.getColumnInfo().getName()))
                        return false;
                }
            }
        }
        

        return  true;
    }

}
