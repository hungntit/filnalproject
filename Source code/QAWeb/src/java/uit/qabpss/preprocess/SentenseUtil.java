/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.qabpss.preprocess;

import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.POS;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import uit.qabpss.core.wordnet.Wordnet;
import uit.qabpss.util.StringPool;

/**
 *
 * @author ThuanHung
 */
public class SentenseUtil {

    private static POSTaggerME posTagME;

    static {
        try {
            initPosTagger();
        } catch (IOException ex) {
            Logger.getLogger(SentenseUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void initPosTagger() throws IOException {
        InputStream in = null;
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
            //Use context classloader to read states.properties        
        try {
            in = loader.getResourceAsStream("uit/qabpss/util/resources/model/en-pos-maxent.bin");
            POSModel model = new POSModel(in);
            posTagME = new POSTaggerME(model);
        } catch (IOException e) {
        } finally {
            in.close();
        }
    }

    public static List<String> getNERWords(String question) {



        String tempQuestion = question;
        List<String> results = new ArrayList<String>();
        String[] strsInQuotationMark = question.split("\"");
        for (int i = 1; i < strsInQuotationMark.length; i += 2) {
            String str = strsInQuotationMark[i];
            results.add(str.trim());
            tempQuestion = tempQuestion.replaceAll("\"" + str + "\"", "");

        }
        String[] words = tempQuestion.split(" ");
        boolean isJoinStr = false;
        String joinStr = "";
        for (int i = 1; i < words.length; i++) {
            char firstChar = 'a';
            if (!words[i].trim().equals("")) {
                firstChar = words[i].charAt(0);
            }

            if ((firstChar >= 48 && firstChar <= 57) || (firstChar >= 65 && firstChar < 97))//first char is lower case
            {
                try {
                    Integer.parseInt(words[i]);
                    isJoinStr = false;
                    if (!joinStr.trim().equals("")) {
                        results.add(joinStr);
                    }
                    joinStr = "";
                } catch (NumberFormatException nEx) {
                    isJoinStr = true;
                    joinStr += " " + words[i];
                    continue;
                }
            } else {
                isJoinStr = false;
                if (!joinStr.trim().equals("")) {
                    results.add(joinStr);
                }
                joinStr = "";
            }
        }
        if (isJoinStr) {
            if (!joinStr.trim().equals("")) {
                results.add(joinStr.trim());
            }
        }
        if (results.size() > 0) {
            return results;
        }

        return null;
    }

    public static Token[] formatNerWordInQuestion(String question) throws IOException {
        if (question == null || question.equals("")) {
            return null;
        }


        String tempquestion = question;
        tempquestion = tempquestion.replace("?", "");
        tempquestion = tempquestion.replaceAll("'s", " 's");

        List<String> nerWords = getNERWords(tempquestion);
        tempquestion = tempquestion.replaceAll("\"", "");
        if (nerWords != null) {
            for (int i = 0; i < nerWords.size(); i++) {
                tempquestion = tempquestion.replaceFirst(nerWords.get(i).trim(), "NER" + (i + 1));
            }
        }
        String posTaggersStr = posTagME.tag(tempquestion);
        String[] postaggers = posTaggersStr.split(" ");
        Token[] results = new Token[postaggers.length];

        for (int i = 0; i < results.length; i++) {
            String[] tokenstrArr = postaggers[i].split("/");
            String value = tokenstrArr[0];
            String pos_value = tokenstrArr[1];
            if (value.startsWith("NER")) {
                pos_value = "NNP";
            }
            if (nerWords != null) {
                for (int j = 0; j < nerWords.size(); j++) {
                    value = value.replaceFirst("NER" + (j + 1), nerWords.get(j).trim());
                }
            }

            results[i] = new Token(value, pos_value);
        }

        if (results.length > 0) {
            return results;
        }
        return null;

    }

    public static String tokensToPosTagsStr(Token[] tokens) {
        if (tokens == null) {
            return "";
        }
        String result = "";
        if (tokens.length <= 0) {
            return "";
        }
        for (int i = 0; i < tokens.length; i++) {
            result += tokens[i].getPos_value() + " ";
        }
        return result.trim();
    }

    public static String tokensToStr(Token[] tokens) {
        if (tokens == null) {
            return null;
        }
        String result = "";
        if (tokens.length <= 0) {
            return "";
        }
        for (int i = 0; i < tokens.length; i++) {
            result += tokens[i].getPos_value() + "/" + tokens[i].getValue() + " ";
        }
        return result.trim();
    }

    public static String tokensToValuesStr(Token[] tokens) {
        String result = "";
        if (tokens.length <= 0) {
            return "";
        }
        for (int i = 0; i < tokens.length; i++) {
            result += tokens[i].getValue() + " ";
        }
        return result.trim();
    }

    public static Token[] removeTokenIn(Token[] tokens, int index) {
        if (tokens.length <= 1) {
            return null;
        }
        Token[] result = new Token[tokens.length - 1];
        int pos = 0;
        for (int i = 0; i < tokens.length; i++) {
            if (i == index) {
                continue;
            }

            result[pos] = new Token(tokens[i]);
            pos++;
        }
        return result;
    }

    public static Token[] replaceTokenIn(Token[] tokens, int index, Token replacedToken) {
        if (tokens.length <= 0) {
            return null;
        }
        Token[] result = new Token[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            if (i == index) {
                result[i] = new Token(replacedToken);
            } else {
                result[i] = new Token(tokens[i]);
            }
        }
        return result;
    }

    private static Token[] processForPattern(Token[] tokens, String fomular) {

        String[] inoutStr = fomular.split("-->");
        String inputStr = inoutStr[0];
        String inStrs[] = inputStr.split(" ");
        String outputStr = inoutStr[1];
        String outStrs[] = outputStr.split(" ");
        String patternStr = ".*";
        ReplacedPosTag[] replacedPostagArr = new ReplacedPosTag[inStrs.length];
        for (int i = 0; i < inStrs.length; i++) {
            String tempPosStrs[] = inStrs[i].split("-");
            String posStr = tempPosStrs[0];
            replacedPostagArr[i] = new ReplacedPosTag(posStr);
            if (tempPosStrs.length == 3) {
                replacedPostagArr[i].setValue(tempPosStrs[2]);
            }
            patternStr += "\\s" + posStr;

        }
        patternStr += ".*";
        for (int i = 0; i < outStrs.length; i++) {
            String tempPosStrs[] = outStrs[i].split("-");
            String afterPostagStr = tempPosStrs[0];
            int pos = Integer.parseInt(tempPosStrs[1]) - 1;
            replacedPostagArr[pos].setAfterPostag(afterPostagStr);

        }

        Token[] result = tokens;
        String posStr = tokensToPosTagsStr(tokens);
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(posStr);

        while (matcher.matches()) {

            String mathPostags = "";
            int group = 1;
            for (int i = 0; i < replacedPostagArr.length; i++) {

                if (replacedPostagArr[i].getPosTag().contains("|")) {
                    mathPostags += matcher.group(group) + " ";
                    group++;
                } else {
                    mathPostags += replacedPostagArr[i].getPosTag() + " ";
                }
            }
            mathPostags = mathPostags.trim();

            //mathStr      =     posStr.
            int findTextIdx = posStr.lastIndexOf(mathPostags);
            posStr = posStr.substring(0, findTextIdx);
            int findPos = posStr.split(" ").length;
            boolean notfound = false;
            for (int j = 0; j < replacedPostagArr.length; j++) {
                String value = replacedPostagArr[j].getValue();
                if (!value.equals("")) {
                    String originOfVerb = Wordnet.wnstemmer.findStems(result[findPos + j].getValue(), POS.VERB).get(0);
                    if (!originOfVerb.equalsIgnoreCase(value)) {
                        notfound = true;
                        break;

                    }
                }
            }
            if (notfound) {
                matcher = matcher.reset(posStr);
                continue;
            }
            boolean isWrongVBN = false;
            //replace Token
            for (int j = 0; j < replacedPostagArr.length; j++) {
                String afterPostag = replacedPostagArr[j].getAfterPostag();
                if (!afterPostag.equals("")) {
                    List<String> replaceValues = Wordnet.wnstemmer.findStems(result[findPos + j].getValue(), POS.VERB);
                    if (replaceValues.isEmpty()) {
                        Token replacedToken = new Token(result[findPos + j].getValue(), "NN");
                        result = replaceTokenIn(result, findPos + j, replacedToken);
                        isWrongVBN = true;
                    } else {
                        String replaceValue = replaceValues.get(0);
                        String replacePosTag = afterPostag;
                        Token replacedToken = new Token(replaceValue, replacePosTag);
                        result = replaceTokenIn(result, findPos + j, replacedToken);
                    }
                }
            }
            //remove Token
            for (int j = replacedPostagArr.length - 1; j >= 0; j--) {
                if (isWrongVBN) {
                    break;
                }
                String afterPostag = replacedPostagArr[j].getAfterPostag();
                if (afterPostag.equals("")) {

                    result = removeTokenIn(result, findPos + j);
                }
            }

            matcher = matcher.reset(posStr);



        }
        if (result != null) {
            return result;
        }
        return tokens;
    }
     private static Token[] optimizeNoun(Token[] tokens) {
          if (tokens == null) {
            return null;
        }
           Token[] result = tokens;
        for (int i = 0; i < result.length; i++) {
            if (result[i].getPos_value().equalsIgnoreCase("NNS")) {
                List<String> findStems = Wordnet.wnstemmer.findStems(result[i].getValue(), POS.NOUN);
                if(findStems!=null)
                {
                    result[i].setPos_value("NN");
                    result[i].setValue(findStems.get(0));
                }


            }
         }
        return result;
     }
    private static Token[] optimizeVerb(Token[] tokens) {
        if (tokens == null) {
            return null;
        }
        Token[] result = tokens;
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].getPos_value().equalsIgnoreCase("VB")) {
                int nextTokenIdx = i + 1;
                if (nextTokenIdx < tokens.length) {
                    if (tokens[nextTokenIdx].getPos_value().equalsIgnoreCase("IN") || tokens[nextTokenIdx].getPos_value().equalsIgnoreCase("RP")) {
                        String joinVerb = tokens[i].getValue() + " " + tokens[nextTokenIdx].getValue();
                        IIndexWord idxWord = Wordnet.wndict.getIndexWord(joinVerb, POS.VERB);
                        if (idxWord != null) {
                            result[i].setValue(joinVerb);
                            result = removeTokenIn(result, nextTokenIdx);
                            i--;
                        }

                    }
                }
            }
        }
        return result;
    }

    public static Token[] optimizePosTags(Token[] tokens) {
        //Token[] result= processForPattern1(tokens);
        //VBP/VBZ (1) + VBN (2) + VBG (3)  VBP(3)
        Token[] result = tokens;
        result = processForPattern(result, "MD-1 VBN-2-->VB-2");                      //	MD(1) + VBN(2)  VBP(3)
        result = processForPattern(result, "MD-1 VB-2 VBN-3-->VB-2 VB-3");            //	MD(1) +VB(2) + VBN(2) VBZ(2) + VB(3)
        result = processForPattern(result, "(VBP|VBZ|VB)-1 VBN-2 VBG-3-->VB-3");      //  VBP/VBZ (1) + VBN (2) + VBG (3)  VB(3)
        result = processForPattern(result, "(VBP|VBZ|VB)-1 VBN-2 VBN-3-->VB-2 VB-3"); //  VBP/VBZ (1) + VBN (2) + VBN (3)  VB(2) + VB(3)
        result = processForPattern(result, "(VBP|VBZ|VB)-1-be VBN-2-->VB-1 VB-2");    //	VBP/VBZ(1) + VBN(2)   VB(1) + VB(2) (VBP/VBZ là động từ TOBE)
        result = processForPattern(result, "(VBP|VBZ|VB)-1 VBN-2-->VB-2");            //  VBP/VBZ(1) + VBN(2)    VB(2) với VBP/VBZ/VB không là động từ tobe.
        result = processForPattern(result, "(VBP|VBZ|VB)-1 VBG-2 VBN-3-->VB-1 VB-3"); //  VBP/VBZ(1) + VBG(2) + VBN(3)   VB(1) + VB(3)
        result = processForPattern(result, "(VBP|VBZ|VB)-1 VBG-2-->VB-2");            //  VBP/VBZ(1) + VBG(2)    VB(2).
        result = processForPattern(result, "VBD-1 VBG-2 VBN-3-->VB-1 VB-2");          //	VBD(1) + VBG(2) + VBN(3)   VB(1) + VB(3)
        result = processForPattern(result, "VBD-1 VBG-2-->VB-2");                     //	VBD(1) + VBG(2)    VB(2).
        result = processForPattern(result, "VBD-1 VBN-2-->VB-1 VB-2");                //	VBD(1) + VBN(2)   VB(1) + VB(2)
        result = processForPattern(result, "VBD-1-->VB-1");                           // 	VBD(1)    VB(1).
        result = processForPattern(result, "(VBZ|VBP)-1-->VB-1");                     // 	VBZ/VBP(1)    VB(1).
        // result        = optimizeVerb(result);
         result=   optimizeNoun(result);
        for (int i = 0; i < result.length; i++) {
            Token token = result[i];
            if (token.getPos_value().equals("DT")
                    && (token.getValue().equals("a")
                    || token.getValue().equals("an")
                    || token.getValue().equals("the"))) {
                result = removeToken(result, token);
            }
        }
        return result;
    }

    /*
     * Check sentence have relation and or without equal relation
     * Ex: What books were written by .... and published by ....
     */
    public static List<List<Token>> checkAndCutSen(Token[] l) {
        List<List<Token>> result = new ArrayList<List<Token>>();
        boolean containsAndOr = false;
        for (int i = 0; i < l.length; i++) {
            Token t = l[i];
            if (t.getPos_value().equals(StringPool.POS_CC) && (t.getValue().equals(StringPool.AND) || t.getValue().equals(StringPool.OR))) {
                if (i < l.length - 1
                        && i > 0
                        && !l[i + 1].getPos_value().equals(StringPool.POS_NNP)
                        && !l[i + 1].getPos_value().equals(l[i-1].getPos_value())) {
                    if(l[i+1].getPos_value().equals(StringPool.POS_VB)
                            ||l[i+1].getPos_value().equals(StringPool.POS_VBN)){
                        // Cut List one 
                        Token[] listOne = new Token[i];
                        for (int j = 0; j < i; j++) {
                            Token token = l[j];
                            listOne[j] = token;
                        }                        
                        result.add(Arrays.asList(listOne));
                        // get List two
                        Token[] listSubTwo1 = null;
                        for (int j = 0; j < listOne.length; j++) {
                            Token token = listOne[j];
                            if(token.getPos_value().equals(StringPool.POS_NNS)
                                    || token.getPos_value().equals(StringPool.POS_NN)){
                                listSubTwo1 = new Token[j+1];
                                System.arraycopy(listOne, 0, listSubTwo1, 0, j+1);
                                break;
                            }
                        }
                        if(listSubTwo1[0].getValue().equals("What")                           
                           ||listSubTwo1[0].getValue().equals("Which")){
                            listSubTwo1 = SentenseUtil.removeTokenIn(listSubTwo1, 0);
                        }
                        if (listSubTwo1[0].getValue().equals("How")) {
                            if (listSubTwo1[1].getPos_value().equals("JJ")) {
                                listSubTwo1 = SentenseUtil.removeTokenIn(listSubTwo1, 0);
                                listSubTwo1 = SentenseUtil.removeTokenIn(listSubTwo1, 0);
                            } else {
                                listSubTwo1 = SentenseUtil.removeTokenIn(listSubTwo1, 0);
                            }
                        }
                        Token[] listSubTwo2 = new Token[l.length - i -1 ];
                        int index = 0;
                        for (int j = i +1 ; j < l.length; j++) {
                            listSubTwo2[index] = l[j];
                            index++;
                        }
                        // merger twos array of list two
                        Token[] listTwo = new Token[listSubTwo1.length + listSubTwo2.length];
                        System.arraycopy(listSubTwo1, 0, listTwo, 0, listSubTwo1.length);
                        System.arraycopy(listSubTwo2, 0, listTwo, listSubTwo1.length, listSubTwo2.length);
                        //
                        result.add(Arrays.asList(listTwo));                        
                        containsAndOr = true;
                        return result;
                    }
                }
            }
        }
        if(containsAndOr == false){
            result.add(Arrays.asList(l));
            return result;
        }
        return null;
    }

    public static Token[] removeToken(Token[] tokens, Token tokenRe) {
        Token[] result = new Token[tokens.length - 1];
        int count = -1;
        boolean hasRemove = false;
        for (int i = 0; i < tokens.length; i++) {
            Token token = tokens[i];
            if (!token.getValue().equals(tokenRe.getValue()) || hasRemove == true) {
                count++;
                result[count] = new Token();
                result[count].setValue(token.getValue());
                result[count].setPos_value(token.getPos_value());
            } else {
                hasRemove = true;
            }
        }
        return result;
    }
}
