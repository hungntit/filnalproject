/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uit.qabpss.extracttriple;

import java.util.ArrayList;
import java.util.List;
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
        if(obj1.getPos_value().equals("NN"))
            return true;
        if(obj2.getPos_value().equals("NN"))
            return true;
        return false;
    }
    public boolean isAllNonNe()
    {
        if( obj1.getPos_value().equals("NN") && obj2.getPos_value().equals("NN") )
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
    public boolean isSameWith(TripleToken tripleToken)
    {
        if(this.getRelationName().equals(tripleToken.getRelationName()) && (this.obj1.getEntityType() == tripleToken.obj1.getEntityType() && this.obj2.getEntityType() == tripleToken.obj2.getEntityType()))
        {
            return true;
        }
        return false;
    }
    public static List<List<TripleToken>> createListsFromInSameTriple(List<TripleToken> tripleTokens)
    {
        List<List<TripleToken>> complexTripleLists =   new ArrayList<List<TripleToken>>();
        List<TripleToken> andList   =   new ArrayList<TripleToken>();
        List<TripleToken> orList   =   new ArrayList<TripleToken>();
                             for(TripleToken tripleToken:tripleTokens)
                                {
                                    if(tripleToken.isIsAndOperator())
                                    {
                                        andList.add(tripleToken);
                                    }
                                    else
                                    {
                                        orList.add(tripleToken);
                                    }
                                }


                                for(TripleToken tripleToken:andList)
                                {
                                    List<TripleToken> arrayList = new ArrayList<TripleToken>();
                                    arrayList.add(tripleToken);
                                    arrayList.addAll(orList);
                                    complexTripleLists.add(arrayList);
                                }
                                return complexTripleLists;
    }
    public static List<TripleToken> createListFromInSameTripleLists(List<List<TripleToken>> list)
            {
                    List<TripleToken> result    =   new ArrayList<TripleToken>();
                    List<TripleToken> simpleTriples =   new ArrayList<TripleToken>();
                    List<List<TripleToken>> complexTripleLists =   new ArrayList<List<TripleToken>>();
                    for(List<TripleToken> tripleTokens: list)
                    {
                        if(tripleTokens.size() <=1)
                        {
                            simpleTriples.add(tripleTokens.get(0));
                        }
                        else
                        {
                            complexTripleLists  =   createListsFromInSameTriple(tripleTokens);
                        }
                    }
                    for(List<TripleToken> arrayList : complexTripleLists)    
                    {
                        result.addAll(arrayList);
                    }
                    result.addAll(simpleTriples);
                    return result;
            }

    public static List<List<TripleToken>> groupTripleTokens(List<TripleToken> tripleTokens)
    {
        List<List<TripleToken>> list    =   new ArrayList<List<TripleToken>>();


        for(int i = 0;i< tripleTokens.size();i++)
        {
            TripleToken firstTripleToken =   tripleTokens.get(i);
            List<TripleToken>   childTripleTokens   =   new ArrayList<TripleToken>();
            childTripleTokens.add(firstTripleToken);
            list.add(childTripleTokens);
            for(int j   = i+1;j<tripleTokens.size();j++)
            {
                TripleToken tripleToken = tripleTokens.get(j);
                if(firstTripleToken.isSameWith(tripleToken))
                {
                    childTripleTokens.add(tripleToken);
                    i++;
                }
                else
                {
                    i=j-1;
                    break;
                }
            }
        }
        return list;
    }

}
