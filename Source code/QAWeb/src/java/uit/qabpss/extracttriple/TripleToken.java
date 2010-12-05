/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uit.qabpss.extracttriple;

import edu.mit.jwi.item.POS;
import java.util.ArrayList;
import java.util.List;
import uit.qabpss.core.wordnet.Wordnet;
import uit.qabpss.preprocess.Token;
import uit.qabpss.util.StringPool;

/**
 *
 * @author ThuanHung
 */
public class TripleToken {



    protected Token obj1;
    protected Token obj2;
    protected String relationName;
    protected String operator   =   StringPool.EQUAL;
    protected boolean isAndOperator = true;

    /**
     * Get the value of isAndOperator
     *
     * @return the value of isAndOperator
     */
    public boolean isIsAndOperator() {
        return isAndOperator;
    }

    /**
     * Set the value of isAndOperator
     *
     * @param isAndOperator new value of isAndOperator
     */
    public void setIsAndOperator(boolean isAndOperator) {
        this.isAndOperator = isAndOperator;
    }


    /**
     * Get the value of operator
     *
     * @return the value of operator
     */
    public String getOperator() {
        return operator;
    }

    /**
     * Set the value of operator
     *
     * @param operator new value of operator
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    public TripleToken(Token obj1, Token obj2, String relationName) {
        this.obj1 = obj1;
        this.obj2 = obj2;
        this.relationName = relationName;
    }

    public TripleToken() {
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

    /**
     * Get the value of obj2
     *
     * @return the value of obj2
     */
    public Token getObj2() {
        return obj2;
    }

    /**
     * Set the value of obj2
     *
     * @param obj2 new value of obj2
     */
    public void setObj2(Token obj2) {
        this.obj2 = obj2;
    }

    /**
     * Get the value of obj1
     *
     * @return the value of obj1
     */
    public Token getObj1() {
        return obj1;
    }

    /**
     * Set the value of obj1
     *
     * @param obj1 new value of obj1
     */
    public void setObj1(Token obj1) {
        this.obj1 = obj1;
    }
    public void swapTwoObject()
    {
        Token temp  =   obj2;
        obj2        =   obj1;
        obj1        =   temp;

    }
    public boolean isHavingNonNe()
    {
        if(obj1.getPos_value().equals("NN")||obj1.getPos_value().equals("NNS"))
            return true;
        if(obj2.getPos_value().equals("NN")||obj2.getPos_value().equals("NNS"))
            return true;
        return false;
    }
    public boolean isAllNonNe()
    {
        if( (obj1.getPos_value().equals("NN")||obj1.getPos_value().equals("NNS")) && (obj2.getPos_value().equals("NN")||obj2.getPos_value().equals("NNS")) )
            return true;

        return false;
    }

    public boolean isNotIdentified()
    {
        if(obj1.getEntityType()== null || obj2.getEntityType()== null || obj1.getEntityType().isNull() || obj2.getEntityType().isNull())
            return true;
        return false;
    }
    public static List<TripleToken> extractTripleWordRelation(Token[] tokens)
    {
        ExtractTriple   extractTriple   =   new ExtractTriple();
        return extractTriple.extractTripleWordRelation(tokens);
    }
    @Override
    public String toString() {
        return obj1.getValue()+","+relationName+","+obj2.getValue();
    }


}
