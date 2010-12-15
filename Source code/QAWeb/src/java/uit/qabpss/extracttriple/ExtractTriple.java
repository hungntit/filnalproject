
package uit.qabpss.extracttriple;

import edu.mit.jwi.item.POS;
import java.util.ArrayList;
import java.util.List;
import uit.qabpss.core.wordnet.Wordnet;
import uit.qabpss.preprocess.Token;

/**
 *
 * @author hoang nguyen
 */
public class ExtractTriple {
    public static final String CD = "CD";
    public static final String DO = "do";
    public static final String DOES = "does";
    public static final String NN = "NN";
    public static final String NNP = "NNP";
    public static final String NNS = "NNS";
    public static final String CC = "CC";

    private static String[] rules = null;

    public ExtractTriple() {
        if(rules == null){
            rules = RuleReader.loadRules();
        }
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

    private static boolean hasVBNInTokens(Token[] tokens)
    {

        for(int i=0;i< tokens.length;i++)
        {
            if(tokens[i]!= null)
            {

                if(tokens[i].getPos_value().equals("VBN"))
                    return true;
            }
        }
        return  false;
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



    public  List<TripleToken> extractTripleWordRelation(Token[] tokens)
    {
    List<TripleToken> result    =   new ArrayList<TripleToken>();
    Token[] tempTokenArr   =   Token.copyTokens(tokens);
    int start = 0,end   =   0;

        for(String rule:rules)
        {
            start   =   0;
            end =   start;
            boolean isAdded =   false;
            boolean isRemove    =   false;
            String rulesStr[]     =   rule.split("-->");
            String inputRule =   rulesStr[0];
           String outputRule    =   rulesStr[1];
           if(rulesStr.length ==3)
               isRemove =   true;
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

           // System.out.println(SentenseUtil.tokensToStr(posTagArr));//----------remove this code
            while(start<tempTokenArr.length)
            {


           String cachedPosTag  =   "";
           int distance =   0;
           boolean found    =   false;
           List<Token[]> tokensList      =   new ArrayList<Token[]>();
           List<TripleToken> tripleTokens   =   new ArrayList<TripleToken>();
           Token[] foundToken   =   new Token[posTagArr.length];
           tokensList.add(foundToken);
           tripleTokens.add(new TripleToken());
           for(int i=0;i<posTagArr.length;i++)
           {

                if(start + i + distance >= tempTokenArr.length )
                {
                    if(!isAdded)
                    {
                        tokensList.remove(tokensList.size()-1);
                        tripleTokens.remove(tripleTokens.size()-1);
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
                       if(!postag.equals(cachedPosTag) && postag.equals("") ==false)
                       {
                           if(!isAdded)
                            {
                                     tokensList.remove(tokensList.size() - 1);
                                     tripleTokens.remove(tripleTokens.size()-1);
                            }
                       }
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
                               tripleTokens.add(new TripleToken());
                               cachedPosTag =   postag;

                            }
                           if(i== posTagArr.length -1)
                           {
                               if(!postag.equals(""))
                                    found    =   true;
                               else
                               {
                                   found = false;
                                   break;
                               }

                           }


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
                                if(temptk.getPos_value().equals(CC))
                                {
                                    TripleToken tripleToken = tripleTokens.get(tripleTokens.size() - 1);
                                    if(temptk.getValue().equalsIgnoreCase("AND"))
                                        tripleToken.setIsAndOperator(true);
                                    else
                                        tripleToken.setIsAndOperator(false);
                                }
                                if(!checkSamePosTag(temptk, "VBN|VB|IN|To").equals("") && i== posTagArr.length-1)
                                {
                                        end--;
                                        cachedPosTag  =   "";
                                        if(!checkSamePosTag(temptk, "VBN|VB").equals(""))
                                        {
                                            tokensList.remove(tokensList.size()-1);
                                            tripleTokens.remove(tripleTokens.size()-1);
                                            if(!isAdded)
                                            {
                                            tokensList.remove(tokensList.size()-1);
                                            tripleTokens.remove(tripleTokens.size()-1);

                                            }

                                        }
                                        if(temptk.getPos_value().equals("IN")||temptk.getPos_value().equals("TO"))
                                        {
                                            if(!isAdded)
                                            {
                                            tokensList.remove(tokensList.size()-1);
                                            tripleTokens.remove(tripleTokens.size()-1);

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
                   try
                   {
                       String relationPosStr[]  =   relationStrs[pos].split("-");
                        if(relationPosStr.length>1)
                            relationPosArr[pos]  =   Integer.parseInt(relationPosStr[1].trim()) -1;
                        else
                            relationPosArr[pos]  =   -1;
                   }
                   catch(NumberFormatException nEx)
                   {
                       relationPosArr[pos]  =   -1;
                   }

               }


               for(int index = 0;index< tokensList.size();index++)
               {
                   TripleToken tripleToken;
                   Token[]  tokensInList    = tokensList.get(index);
                   tripleToken   =   tripleTokens.get(index);
                   tripleToken.setObj1(tokensInList[firstObjPossition]);
                   tripleToken.setObj2(tokensInList[secondObjPossition]);
                   if(secondObjPossition>0)
                   {
                       if (!checkSamePosTag(tokensInList[secondObjPossition - 1], "NNS|NN").equals(""))
                       {
                            tripleToken.getObj2().setEntityOfToken(tokensInList[secondObjPossition - 1]);
                       }
                   }
                   String relation  =   "";
                   if(relationPosArr[0] == -1)
                       relation  =   relationStrs[0] ;
                   else
                   {
                   for(int pos=0;pos<relationPosArr.length;pos++ )
                    {
                        int relationPos  =   relationPosArr[pos];

                        if(tokensInList[relationPos].getPos_value().equals("VBN"))
                        {
                                List<String> findStems = Wordnet.wnstemmer.findStems(tokensInList[relationPos].getValue(), POS.VERB);
                                if(findStems.isEmpty())
                                    relation    +=  tokensInList[relationPos].getValue()+ " ";
                                else
                                    relation    +=  "be "+ findStems.get(0)+ " ";

                        }
                        else
                        {
                            relation    +=  tokensInList[relationPos].getValue()+ " ";
                        }

                    }
                   }
                    relation    =   relation.trim();
                    tripleToken.setRelationName(relation);
                    if(relation.equalsIgnoreCase("be"))
                    {
                        tripleToken.getObj1().setEntityOfToken(tripleToken.getObj2());
                    }
                    result.add(tripleToken);

               }
               //Token[]  checkRemoveTokens   =   Token.copyTokens(tempTokenArr, start, end);
               //int countVerb   =   countVerbInTokens(checkRemoveTokens);

               if(isRemove)
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

    
}
