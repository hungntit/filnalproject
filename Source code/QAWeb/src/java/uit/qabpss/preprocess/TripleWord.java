/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uit.qabpss.preprocess;

import java.util.List;

/**
 *
 * @author ThuanHung
 */
public class TripleWord {

    protected String firstObject;
    protected String relationWord;
    protected String secondObject;
    protected String operator;

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

    public static List<TripleWord> getTripleWordFromQuestion(Token[] tokens)
    {
        String[]  rulesStr  =   new String[]
        {

        };
        return null;
    }
}
