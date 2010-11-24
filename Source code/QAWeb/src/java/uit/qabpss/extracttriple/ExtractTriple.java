
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

    private static String[] rules = null;

    public ExtractTriple() {
        if(rules == null){
            rules = RuleReader.loadRules();
        }
    }
/*hoang
    public List<TripleWord> extractTripleWordRel(Token[] tokens) {
        List<TripleWord> result = new ArrayList<TripleWord>();
        Token[] tempTokens = tokens;
        for (int k = 0; k < rules.length; k++) {
            String[] inoutStr = rules[k].split("-->");
            String in = inoutStr[0].trim();
            String out = inoutStr[1].trim();
            //get results from formular
            String[] rulesTriples = out.split(",");
            String obj1 = rulesTriples[0].trim();
            String relWord = rulesTriples[1].trim();
            String obj2 = rulesTriples[2].trim();
            //
            String posString = SentenseUtil.tokensToPosTagsStr(tempTokens);        
            // Check the formular is matched
            Pattern pattern = Pattern.compile(in);
            if (pattern.matcher(posString).find()) {
                TripleWord t = new TripleWord();
                List<String> addValues = new ArrayList<String>();
                //Put the first object to Triple
                for (int i = 0; i < tempTokens.length; i++) {
                    Token token = tempTokens[i];
                    if (token.getPos_value().equals(obj1)&&!addValues.contains(token.getValue())) {
                        t.setFirstObject(token.getValue());
                        t.setFirstObjPos(token.getPos_value());
                        addValues.add(token.getValue());
                        break;
                    }
                }
                //Put the relationship word to Triple
                int numRel = 0;
                String[] relWords = relWord.split(" ");
                for (int i = 0; i < tempTokens.length; i++) {
                    Token token = tempTokens[i];
                    for (int j = 0; j < relWords.length; j++) {                       
                        if (token.getPos_value().equals(relWords[j])&&!addValues.contains(token.getValue())) {
                            if (!token.getValue().equals(DO) || token.getValue().equals(DOES)) {                                
                                t.setRelationWord(t.getRelationWord() + " " + token.getValue());
                                numRel++;                                
                            }
                            t.setRelationWord(t.getRelationWord().trim());
                            addValues.add(token.getValue());
                            break;
                        }
                    }
                    if (numRel == relWords.length) {
                        break;
                    }
                }
                //Put the second object to Triple
                for (int i = 0; i < tempTokens.length; i++) {
                    Token token = tempTokens[i];
                    if (token.getPos_value().equals(obj2)&&!addValues.contains(token.getValue())) {
                        t.setSecondObject(token.getValue());
                        t.setSecondObjPos(token.getPos_value());
                        addValues.add(token.getValue());
                        break;
                    }
                }
                //remove the recognied objects out of array except NN,NNS
                String[] removeObjs = in.split(" ");
                for (int i = 0; i < removeObjs.length; i++) {
                    String pos = removeObjs[i];
                    for (int j = 0; j < tempTokens.length; j++) {
                        Token tk = tempTokens[j];
                        if (pos.equals(NN) || pos.equals(NNS)) {
                            if (j + 1 < tempTokens.length
                                    && !tempTokens[j + 1].getPos_value().equals(NN)
                                    && !tempTokens[j + 1].getPos_value().equals(NNS)) {
                                break;
                            }
                        }
                        if (pos.equals(tk.getPos_value())) {
                            tempTokens = SentenseUtil.removeToken(tempTokens, tk);
                            break;
                        }
                    }
                }
                result.add(t);
            }
        }
        // recognize remain entities which system does not find
        if (tempTokens.length > 0) {
            List<Token> remaintokens = new ArrayList<Token>();            
            for (int i = 0; i < tempTokens.length; i++) {
                Token token = tempTokens[i];
                if (NNP.equals(token.getPos_value()) || CD.equals(token.getPos_value())) {
                    remaintokens.add(token);
                }
            }
            //remove bad triple
            for (int i = 0; i < result.size(); i++) {
                TripleWord triple = result.get(i);
                if(triple.getRelationWord().isEmpty()){
                    if(triple.getFirstObjPos().equals(NNP)||triple.getFirstObjPos().equals(CD)){
                        System.out.println("first expected text:" + triple.getFirstObjPos());
                        remaintokens.add(new Token(triple.getFirstObject(), triple.getFirstObjPos()));
                    }
                    if(triple.getSecondObjPos().equals(NNP)||triple.getSecondObjPos().equals(CD)){
                        System.out.println("second expected text:" + triple.getSecondObjPos());
                        remaintokens.add(new Token(triple.getSecondObject(), triple.getSecondObjPos()));
                    }
                    result.remove(i);
                }
            }
            //map value to database
            if (remaintokens.size() > 0) {
                for (int i = 0; i < remaintokens.size(); i++) {
                    Token token = remaintokens.get(i);
                    TripleWord t = HibernateUtil.getTripleFromValue(token.getValue());
                    if (t != null) {
                        result.add(t);
                    }
                }
            }
        }
        return result;
    }
  */

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
                       if(!postag.equals(cachedPosTag) && postag.equals("") ==false)
                       {
                           if(!isAdded)
                            {
                                     tokensList.remove(tokensList.size() - 1);
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
                        if(tokensInList[relationPos].getPos_value().equals("VBN"))
                        {
                                List<String> findStems = Wordnet.wnstemmer.findStems(tokensInList[relationPos].getValue(), POS.VERB);
                                if(findStems.isEmpty())
                                    relation    +=  tokensInList[relationPos].getValue()+ " ";
                                else
                                    relation    +=  "be "+ findStems.get(0)+ " ";

                        }
                        else
                            relation    +=  tokensInList[relationPos].getValue()+ " ";


                    }
                    relation    =   relation.trim();
                    tripleToken.setRelationName(relation);

                    result.add(tripleToken);

               }
               Token[]  checkRemoveTokens   =   Token.copyTokens(tempTokenArr, start, end);
               int countVerb   =   countVerbInTokens(checkRemoveTokens);

               if(countVerb>1 || hasVBNInTokens(checkRemoveTokens))
               {
                   if(countVerb>1)
                   {
                   tempTokenArr =   Token.removeTokens(tempTokenArr, start+1,end);

                   }
                    else
                   {
                       tempTokenArr =   Token.removeTokens(tempTokenArr, start+1,end);

                    }
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
