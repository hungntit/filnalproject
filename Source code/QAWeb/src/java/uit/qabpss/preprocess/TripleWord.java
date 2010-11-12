/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uit.qabpss.preprocess;

import java.util.List;
import uit.qabpss.extracttriple.ExtractTriple;

/**
 *
 * @author ThuanHung
 */
public final class TripleWord {

    protected String firstObject;
    protected String firstObjPos;
    protected String relationWord;
    protected String secondObject;
    protected String secondObjPos;
    protected String operator;

    public TripleWord() {
        setFirstObject("");
        setSecondObject("");
        setRelationWord("");
        setOperator("=");
    }

    public TripleWord(String firstObject, String relationWord, String secondObject) {
        this.firstObject = firstObject;
        this.relationWord = relationWord;
        this.secondObject = secondObject;
    }

    /**
     * Get the value of firstObject
     *
     * @return the value of firstObject
     */

    public String getFirstObject() {
        return firstObject;
    }

    /**
     * Set the value of firstObject
     *
     * @param firstObject new value of firstObject
     */
    public void setFirstObject(String firstObject) {
        this.firstObject = firstObject;
    }
    

    /**
     * Get the value of secondObject
     *
     * @return the value of secondObject
     */
    public String getSecondObject() {
        return secondObject;
    }

    /**
     * Set the value of secondObject
     *
     * @param secondObject new value of secondObject
     */
    public void setSecondObject(String secondObject) {
        this.secondObject = secondObject;
    }
    

    /**
     * Get the value of relationWord
     *
     * @return the value of relationWord
     */
    public String getRelationWord() {
        return relationWord;
    }

    /**
     * Set the value of relationWord
     *
     * @param relationWord new value of relationWord
     */
    public void setRelationWord(String relationWord) {
        this.relationWord = relationWord;
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

    public String getFirstObjPos() {
        return firstObjPos;
    }

    public void setFirstObjPos(String firstObjPos) {
        this.firstObjPos = firstObjPos;
    }

    public String getSecondObjPos() {
        return secondObjPos;
    }

    public void setSecondObjPos(String secondObjPos) {
        this.secondObjPos = secondObjPos;
    }

    public static List<TripleWord> getTripleWordFromQuestion(Token[] tokens)
    {
        if(tokens.length==0){
            return null;
        }
        ExtractTriple extractTriple = new ExtractTriple();
        return extractTriple.extractTripleWordRel(tokens);
    }
}
