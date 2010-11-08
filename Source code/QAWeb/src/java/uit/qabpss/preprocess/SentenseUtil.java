/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uit.qadpss.preprocess;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.morph.WordnetStemmer;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import opennlp.tools.lang.english.ParserTagger;


/**
 *
 * @author ThuanHung
 */
public class SentenseUtil {

    private static Dictionary  wndict;
    private static ParserTagger    parserTagger ;
    static {
        try {
            initWordnetDictionary("C:\\Program Files\\WordNet\\2.1");
            initPosTagger();
        } catch (IOException ex) {
            Logger.getLogger(SentenseUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public static void initPosTagger() throws IOException
    {
        parserTagger    =   new ParserTagger("model\\english\\parser\\tag.bin.gz", null);
    }
    public static void initWordnetDictionary(String WNdir) throws MalformedURLException
    {
        String path     =   WNdir+"\\" + "dict";
        URL url =   new URL("file", null, path);
        wndict  =   new Dictionary(url);
        wndict.open();
    }

    public static List<String>  getNERWords(String question)
    {



        String    tempQuestion            =   question;
        List<String> results    =   new ArrayList<String>();
        String[]    strsInQuotationMark  =   question.split("\"");
        for(int i   =   1;i< strsInQuotationMark.length; i+=2)
        {
            String str  =   strsInQuotationMark[i];
            results.add(str.trim());
            tempQuestion    =   tempQuestion.replaceAll("\""+str+"\"", "");

        }
        String[] words    = tempQuestion.split(" ");
        boolean isJoinStr   =   false;
        String  joinStr     =   "";
        for(int i   =   1;i< words.length;i++)
        {
            char firstChar  =   'a'  ;
            if( ! words[i].trim().equals(""))
                firstChar   =   words[i].charAt(0);

            if((firstChar>=48&&firstChar<=57)||(firstChar>=65 &&firstChar<97))//first char is lower case
            {
                try
                {
                    Integer.parseInt(words[i]);
                    isJoinStr   =   false;
                    if( ! joinStr.trim().equals("") )
                        results.add(joinStr);
                    joinStr     =   "";
                }
                catch(NumberFormatException nEx)
                {
                isJoinStr   =   true;
                joinStr     +=  " "+words[i];
                continue;
                }
            }
            else
            {
                isJoinStr   =   false;
                if( ! joinStr.trim().equals("") )
                    results.add(joinStr);
                joinStr     =   "";
            }
        }
        if(isJoinStr)
        {
            if( ! joinStr.trim().equals("") )
                    results.add(joinStr.trim());
        }
        if(results.size()>0)
            return results;

        return null;
    }
    public static Token[] formatNerWordInQuestion(String question) throws IOException
    {
        if(question ==null ||question.equals(""))
            return null;


        String tempquestion        =    question;
        tempquestion        =   tempquestion.replace("?", "");
        tempquestion               =    tempquestion.replaceAll("'s", " 's");

        List<String> nerWords   =   getNERWords(tempquestion);
        tempquestion =  tempquestion.replaceAll("\"", "");
        if(nerWords !=null)
            for(int i=0;i<nerWords.size();i++)
            {
                tempquestion    =   tempquestion.replaceFirst(nerWords.get(i).trim(), "NER"+(i+1));
            }
        String posTaggersStr    =   parserTagger.tag(tempquestion);
        String[] postaggers       =   posTaggersStr.split(" ");
        Token[] results          =   new Token[postaggers.length];

        for(int i=0;i< results.length;i++)
        {
            String[]    tokenstrArr    =   postaggers[i].split("/");
            String value            =   tokenstrArr[0];
            String pos_value            =   tokenstrArr[1];
            if( value.startsWith("NER"))
                pos_value   =   "NNP";
            if(nerWords!=null)
                for(int j=0;j< nerWords.size();j++)
                {
                    value   =   value.replaceFirst("NER"+(j+1), nerWords.get(j).trim());
                }

            results[i]  =   new Token(value, pos_value);
        }

        if(results.length>0)
            return results;
        return null;

    }
    public static String tokensToPosTagsStr(Token[] tokens)
    {
        if(tokens == null) return "";
        String result   =   "";
        if(tokens.length<=0) return "";
        for(int i=0;i<tokens.length;i++)
        {
            result+=tokens[i].getPos_value()+" ";
        }
        return result.trim();
    }
    public static String tokensToStr(Token[] tokens)
    {
        if(tokens == null) return null;
        String result   =   "";
        if(tokens.length<=0) return "";
        for(int i=0;i<tokens.length;i++)
        {
            result+=tokens[i].getPos_value()+"/"+tokens[i].getValue()+" ";
        }
        return result.trim();
    }
    public static String tokensToValuesStr(Token[] tokens)
    {
        String result   =   "";
        if(tokens.length<=0) return "";
        for(int i=0;i<tokens.length;i++)
        {
            result+=tokens[i].getValue()+" ";
        }
        return result.trim();
    }
    public static Token[]   removeTokenIn(Token[] tokens,int index)
    {
        if(tokens.length<=1)
        {
            return null;
        }
        Token[] result= new Token[tokens.length -1] ;
        int pos =   0;
        for(int i=0;i<tokens.length;i++)
        {
            if(i == index)  continue;

            result[pos] =   new Token(tokens[i]);
            pos++;
        }
        return result;
    }
    public static Token[]   replaceTokenIn(Token[] tokens,int index,Token replacedToken)
    {
        if(tokens.length <=  0)
            return null;
        Token[] result  =   new Token[tokens.length];
        for(int i=0;i<tokens.length;i++)
        {
            if(i == index)
            {
                result[i]   =   new Token(replacedToken);
            }
            else
            {
                result[i]   =   new Token(tokens[i]);
            }
        }
        return result;
    }

    private static Token[] processForPattern(Token[] tokens,String fomular)
    {
       WordnetStemmer   wnstemmer   =   new WordnetStemmer(wndict);
       String[] inoutStr     =   fomular.split("-->");
       String inputStr      =   inoutStr[0];
       String inStrs[]      =   inputStr.split(" ");
       String outputStr      =   inoutStr[1];
       String outStrs[]      =   outputStr.split(" ");
       String patternStr       =   ".*";
       ReplacedPosTag[] replacedPostagArr =  new ReplacedPosTag[inStrs.length];
       for(int i=0;i<inStrs.length;i++)
       {
           String tempPosStrs[]    =   inStrs[i].split("-");
           String posStr    =   tempPosStrs[0];
           replacedPostagArr[i] =   new ReplacedPosTag(posStr);
           if(tempPosStrs.length == 3)
           {
               replacedPostagArr[i].setValue(tempPosStrs[2]);
           }
           patternStr+=    "\\s"+posStr;

       }
       patternStr+=".*";
       for(int i    =   0;i<outStrs.length;i++)
       {
           String tempPosStrs[]    =   outStrs[i].split("-");
           String afterPostagStr    =   tempPosStrs[0];
           int pos          =   Integer.parseInt(tempPosStrs[1])-1;
            replacedPostagArr[pos].setAfterPostag(afterPostagStr);

       }

       Token[]  result      =   tokens;
       String posStr    =   tokensToPosTagsStr(tokens);
       Pattern pattern =   Pattern.compile(patternStr);
       Matcher  matcher    =   pattern.matcher(posStr);

       while(matcher.matches())
       {

           String mathPostags   =   "";
           int group    =   1;
           for(int i=0;i<replacedPostagArr.length;i++)
            {

                   if(replacedPostagArr[i].getPosTag().contains("|"))
                   {
                       mathPostags  +=  matcher.group(group)+" ";
                       group++;
                    }
                    else
                   {
                       mathPostags+=    replacedPostagArr[i].getPosTag()+" ";
                    }
           }
           mathPostags  =   mathPostags.trim();

           //mathStr      =     posStr.
            int findTextIdx   = posStr.lastIndexOf(mathPostags);
            posStr   = posStr.substring(0, findTextIdx);
            int findPos       =   posStr.split(" ").length;
            boolean notfound    =   false;
            for(int j=0 ;j<replacedPostagArr.length;j++)
            {
                String value  = replacedPostagArr[j].getValue();
                if(!value.equals(""))
                {
                    String originOfVerb =   wnstemmer.findStems(result[findPos+j].getValue(), POS.VERB).get(0);
                    if (!originOfVerb.equalsIgnoreCase(value))
                    {
                        notfound    =   true;
                        break;

                    }
                }
            }
            if(notfound)
            {
                matcher =   matcher.reset(posStr);
                continue;
           }
            boolean isWrongVBN  =   false;
            //replace Token
            for(int j=0 ;j<replacedPostagArr.length;j++)
            {
                String afterPostag =   replacedPostagArr[j].getAfterPostag();
                if(  !afterPostag.equals(""))
                {
                    List<String> replaceValues          =   wnstemmer.findStems(result[findPos+j].getValue(), POS.VERB);
                    if(replaceValues.size()==0)
                    {
                               Token replacedToken =   new Token(result[findPos+j].getValue(), "NN");
                               result                       =  replaceTokenIn(result, findPos+j, replacedToken);
                               isWrongVBN       =   true;
                    }
                    else
                    {
                        String replaceValue         =   replaceValues.get(0);
                        String replacePosTag         =   afterPostag;
                        Token replacedToken =   new Token(replaceValue, replacePosTag);
                       result                       =  replaceTokenIn(result, findPos+j, replacedToken);
                    }
                }
           }
            //remove Token
            for(int j=replacedPostagArr.length-1 ;j>=0;j--)
            {
                if(isWrongVBN)
                    break;
                String afterPostag =   replacedPostagArr[j].getAfterPostag();
                if( afterPostag.equals(""))
                {

                   result                       =  removeTokenIn(result, findPos+j);
                }
           }

           matcher =   matcher.reset(posStr);



       }
       if(result!= null) return result;
       return tokens;
    }

    private static Token[] optimizeVerb(Token[] tokens)
    {
        if(tokens == null) return null;
        Token[] result  =   tokens;
        for(int i=0;i<tokens.length;i++)
        {
            if(tokens[i].getPos_value().equalsIgnoreCase("VB"))
            {
                int nextTokenIdx    =   i+1;
                if(nextTokenIdx<tokens.length)
                {
                    if(tokens[nextTokenIdx].getPos_value().equalsIgnoreCase("IN")||tokens[nextTokenIdx].getPos_value().equalsIgnoreCase("RP"))
                    {
                        String joinVerb =   tokens[i].getValue() +" "+ tokens[nextTokenIdx].getValue();
                        IIndexWord idxWord  =   wndict.getIndexWord(joinVerb, POS.VERB);
                        if(idxWord != null)
                        {
                            result[i].setValue(joinVerb);
                            result  =   removeTokenIn(result,nextTokenIdx);
                            i--;
                        }

                    }
                }
            }
        }
        return result;
    }

    public static Token[] optimizePosTags(Token[] tokens)
    {
        //Token[] result= processForPattern1(tokens);
        //VBP/VBZ (1) + VBN (2) + VBG (3)  VBP(3)
        Token[] result  =   tokens;
        result        = processForPattern(result,"MD-1 VBN-2-->VB-2");                      //	MD(1) + VBN(2)  VBP(3)
        result        = processForPattern(result,"MD-1 VB-2 VBN-3-->VB-2 VB-3");            //	MD(1) +VB(2) + VBN(2) VBZ(2) + VB(3)
        result        = processForPattern(result,"(VBP|VBZ|VB)-1 VBN-2 VBG-3-->VB-3");      //  VBP/VBZ (1) + VBN (2) + VBG (3)  VB(3)
        result        = processForPattern(result,"(VBP|VBZ|VB)-1 VBN-2 VBN-3-->VB-2 VB-3"); //  VBP/VBZ (1) + VBN (2) + VBN (3)  VB(2) + VB(3)
        result        = processForPattern(result,"(VBP|VBZ|VB)-1-be VBN-2-->VB-1 VB-2");    //	VBP/VBZ(1) + VBN(2)   VB(1) + VB(2) (VBP/VBZ là động từ TOBE)
        result        = processForPattern(result,"(VBP|VBZ|VB)-1 VBN-2-->VB-2");            //  VBP/VBZ(1) + VBN(2)    VB(2) với VBP/VBZ/VB không là động từ tobe.
        result        = processForPattern(result,"(VBP|VBZ|VB)-1 VBG-2 VBN-3-->VB-1 VB-3"); //  VBP/VBZ(1) + VBG(2) + VBN(3)   VB(1) + VB(3)
        result        = processForPattern(result,"(VBP|VBZ|VB)-1 VBG-2-->VB-2");            //  VBP/VBZ(1) + VBG(2)    VB(2).
        result        = processForPattern(result,"VBD-1 VBG-2 VBN-3-->VB-1 VB-2");          //	VBD(1) + VBG(2) + VBN(3)   VB(1) + VB(3)
        result        = processForPattern(result,"VBD-1 VBG-2-->VB-2");                     //	VBD(1) + VBG(2)    VB(2).
        result        = processForPattern(result,"VBD-1 VBN-2-->VB-1 VB-2");                //	VBD(1) + VBN(2)   VB(1) + VB(2)
        result        = processForPattern(result,"VBD-1-->VB-1");                           // 	VBD(1)    VB(1).
        result        = processForPattern(result,"(VBZ|VBP)-1-->VB-1");                     // 	VBZ/VBP(1)    VB(1).
        result        = optimizeVerb(result);

        return result;
    }
}

