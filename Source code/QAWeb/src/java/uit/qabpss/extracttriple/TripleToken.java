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
    
    private static String checkSamePosTag(Token tk,String posTagStr)//examlel (token, "NNP|NNS")
    {
        if(tk == null)
            return "";
        posTagStr   =   posTagStr.replace("(", "");
        posTagStr   =   posTagStr.replace(")", "");
        posTagStr   =   posTagStr.replace("|", "-");
        String[] tempStrArr     =   posTagStr.split("-");
        for(int i=0;i<tempStrArr.length;i++)
        {
            if(tk.getPos_value().equalsIgnoreCase(tempStrArr[i]))
                return tempStrArr[i];
        }
        return "";
    }
    private static int countVerbInTokens(Token[] tokens)
    {
        int result  =   0;
        for(int i=0;i< tokens.length;i++)
        {
            if(tokens[i]!= null)
            {
                if(tokens[i].getPos_value().equals("VB"))
                    result++;
            }
        }
        return  result;
    }
public static List<TripleToken> extractTripleWordRelation(Token[] tokens)
    {
    List<TripleToken> result    =   new ArrayList<TripleToken>();
    String[] rules  =   new String[]
    {
        "(NN|NNS|NNP)-1 VB-2 (NN|NNS|NNP)-3-NA VB-4 --> (NN|NNS|NNP)-3-NA,VB-4,(NN|NNS|NNP)-1",
        "(WP|WDT|NNS|NNP|NN)-1 VB-2 VB-3 IN-4 (NN|NNS|NNP)-5-NA --> (WP|WDT|NNS|NNP|NN)-1,VB-2 VB-3,(NN|NNS|NNP)-5-NA ",
        "(WP|WDT|NNS|NNP|NN)-1 VB-2 (NN|NNS|NNP)-3-NA --> (WP|WDT|NNS|NNP|NN)-1,VB-2,(NN|NNS|NNP)-3-NA ",
        "(WP|WDT|NNS|NNP|NN)-1 IN-2 (NN|NNS|NNP|CD)-3-NA --> (WP|WDT|NNS|NNP|NN)-1,VB-2,(NN|NNS|NNP|CD)-3-NA ",
        "(WP|WDT|NNS|NNP|NN)-1 IN-2 (NN|NNS|NNP|CD)-3 TO-4 (NN|NNS|NNP|CD)-5 --> (WP|WDT|NNS|NNP|NN)-1,TO-4,(NN|NNS|NNP|CD)-5 "

        


    };
    Token[] tempTokenArr   =   Token.copyTokens(tokens);
    int start = 0,end   =   0;
    
        for(String rule:rules)
        {
            start   =   0;
            end =   start;
            boolean isAdded =   false;
            String inputRule =   rule.split("-->")[0];
           String outputRule    =   rule.split("-->")[1];
           String posTagStrInputArr[]   =   inputRule.split(" ");
           Token   posTagArr[]   = new Token[posTagStrInputArr.length];


           for(int i = 0;i<posTagStrInputArr.length;i++)
           {
               String   tempStrArr[]    =   posTagStrInputArr[i].split("-");

               posTagArr[i]             =   new Token();
               posTagArr[i].setPos_value(tempStrArr[0]);


               if(tempStrArr.length ==3)
               {
                   posTagArr[i].setValue(tempStrArr[2]);

               }

           }
           int countVerb   =   countVerbInTokens(posTagArr);
           // System.out.println(SentenseUtil.tokensToStr(posTagArr));//----------remove this code
            while(start<tempTokenArr.length)
            {
           
           
           String cachedPosTag  =   "";
           int distance =   0;
           boolean found    =   false;
           List<Token[]> tokensList      =   new ArrayList<Token[]>();
           Token[] foundToken   =   new Token[posTagArr.length];
           tokensList.add(foundToken);
           for(int i=0;i<posTagArr.length;i++)
           {
                
                if(start + i + distance >= tempTokenArr.length )
                {
                    if(!isAdded)
                    {
                        tokensList.remove(tokensList.size()-1);
                    }
                    break;
               }
               Token temptk =   tempTokenArr[start + i + distance];
               end  =   start+i+distance;
               
               if( posTagArr[i].getValue().equalsIgnoreCase("NA") == false)
               {
                   String postag    =   checkSamePosTag(temptk, posTagArr[i].getPos_value());
                   if(! postag.equals(""))
                   {
                       if(i == posTagArr.length -1)
                           found    =   true;
                       for(Token[] tokenArr : tokensList)
                       {
                           tokenArr[i]  =   new Token(temptk);
                           
                       }
                        
                   }
                    else
                   {
                       found    =   false;
                       break;
                    }

               }
                else
               {
                   
                   String postag    =   checkSamePosTag(temptk, posTagArr[i].getPos_value());
                   if(i< posTagArr.length -1 && !cachedPosTag.equals(""))
                   {
                       postag    =   checkSamePosTag(temptk, posTagArr[i+1].getPos_value());
                       if(!postag.equals(""))
                       {

                           i++;
                           if(i== posTagArr.length -1)
                           {
                               found    =   true;
                               for(Token[] tokenArr : tokensList)
                                {
                                   tokenArr[i]  =   new Token(temptk);

                                }
                               break;
                           }
                       }

                   }
                   if( cachedPosTag.equals(""))
                   {
                            for(Token[] tokenArr : tokensList)
                                {
                                   tokenArr[i]  =   new Token(temptk);
                                   
                                }
                            if(!postag.equals(""))
                            {
                               Token[]  coppyTokenArr   =   Token.copyTokens(tokensList.get(0));
                               tokensList.add(coppyTokenArr);
                               cachedPosTag =   postag;
                            }
                           if(i== posTagArr.length -1)
                               found    =   true;

                            i--;
                           distance++;
                           isAdded  =   false;

                       
                       
                   }
                   else
                   {

                       if(temptk.getPos_value().equals(cachedPosTag))
                           {
                                Token[] tokenArr    =   tokensList.get(tokensList.size()-1);
                                tokenArr[i].setValue(temptk.getValue());
                                isAdded =   true;
                                i--;
                                distance++;

                           }
                            else
                           {

                                if(!checkSamePosTag(temptk, "VB|IN|To").equals("") && i== posTagArr.length-1)
                                {
                                        end--;
                                        cachedPosTag  =   "";
                                        if(temptk.getPos_value().equals("VB"))
                                        {
                                            tokensList.remove(tokensList.size()-1);
                                            if(!isAdded)
                                            {
                                            tokensList.remove(tokensList.size()-1);

                                            }

                                        }
                                        if(temptk.getPos_value().equals("IN")||temptk.getPos_value().equals("TO"))
                                        {
                                            if(!isAdded)
                                            {
                                            tokensList.remove(tokensList.size()-1);

                                            }
                                        }
                                }
                                else
                                {
 
                                    i--;
                                    distance++;
                                }
                            }
                   }

                   
               }
               
           }
           if(found== false)
               start++;
           else
           {
               String[] outputStrs          =   outputRule.split(",");
               int firstObjPossition        =   Integer.parseInt(outputStrs[0].split("-")[1].trim())-1;
               int secondObjPossition       =   Integer.parseInt(outputStrs[2].split("-")[1].trim())-1;
               String[] relationStrs        =   outputStrs[1].split(" ");
               int[] relationPosArr            =   new int[relationStrs.length];
               for(int pos=0;pos<relationPosArr.length;pos++ )
               {
                   relationPosArr[pos]  =   Integer.parseInt(relationStrs[pos].split("-")[1].trim()) -1;

               }
               

               for(int index = 0;index< tokensList.size();index++)
               {
                   TripleToken tripleToken;
                   Token[]  tokensInList    = tokensList.get(index);
                   tripleToken   =   new TripleToken();
                   tripleToken.setObj1(tokensInList[firstObjPossition]);
                   tripleToken.setObj2(tokensInList[secondObjPossition]);
                   String relation  =   "";
                   for(int pos=0;pos<relationPosArr.length;pos++ )
                    {
                        int relationPos  =   relationPosArr[pos];
                        relation    +=  tokensInList[relationPos].getValue()+ " ";


                    }
                    relation    =   relation.trim();
                    tripleToken.setRelationName(relation);

                    result.add(tripleToken);

               }
               if(countVerb>1)
               {
                   tempTokenArr =   Token.removeTokens(tempTokenArr, start+1,end);
                   start    =   0;
               }
                else{
                    start   =   start+ posTagArr.length+ 1;
                }


           }

        }
    }
        if(result.size()>0)
            return result;
        return null;
    }

    @Override
    public String toString() {
        return obj1.getValue()+","+relationName+","+obj2.getValue();
    }


}
