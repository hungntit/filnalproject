/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.qabpss.extracttriple;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import uit.qabpss.preprocess.SentenseUtil;
import uit.qabpss.preprocess.Token;
import uit.qabpss.preprocess.TripleWord;

/**
 *
 * @author aa
 */
public class RelationReg {

    private static String[] rules = new String[]{
        "WP VB DT NNS --> WP,VB,NNS",
        "WP VB DT NN --> WP,VB,NN",
        "WP VB NNS --> WP,VB,NNS",
        "WP VB NNP --> WP,VB,NNP",
        "WP NNS VB NN NNP --> NNS,VB,NNP",
        "NNS VB VB IN NNP --> NNS,VB VB,NNP",
        "WP NN VB NNP VB --> NN,VB,NNP",
        "NNS IN DT NNS --> NNS,IN,NNS",
        "NNS VBN IN NNP --> NNS,VBN,NNP",
        "NNS IN CD --> NNS,IN,CD",
        "NNS TO CD --> NNS,TO,CD",
        "NNS TO CD --> NNS,TO,CD",
        "NNS IN NNP --> NNS,IN,NNP",
        "NN VB NNS --> NN,VB,NNS",
        "NN IN NNP --> NN,IN,NNP",
        "NN IN DT NN NNP--> NN,IN,NNP",
        "NN VB NNP --> NN,VB,NNP",
        "NN IN CD --> NN,IN,CD",};

    public List<TripleWord> extractTripleWordRel(Token[] tokens) {
        List<TripleWord> result = new ArrayList<TripleWord>();
        Token[] tempTokens = tokens;
        for (int k = 0; k < rules.length; k++) {
            String[] inoutStr = rules[k].split("-->");
            String in = inoutStr[0].trim();
            String out = inoutStr[1].trim();
            //get results from formular
            String obj1 = out.split(",")[0].trim();
            String relWord = out.split(",")[1].trim();
            String obj2 = out.split(",")[2].trim();
            //
            String posString = SentenseUtil.tokensToPosTagsStr(tempTokens);
            System.out.println(posString);
            Pattern pattern = Pattern.compile(in);
            // Check the formular is matched
            if (pattern.matcher(posString).find()) {
                TripleWord t = new TripleWord("", "", "");
                //Put the first object to Triple
                for (int i = 0; i < tempTokens.length; i++) {
                    Token token = tempTokens[i];
                    if (token.getPos_value().equals(obj1)) {
                        t.setFirstObject(token.getValue());
                        break;
                    }
                }
                //Put the relationship word to Triple
                int numRel = 0;
                for (int i = 0; i < tempTokens.length; i++) {
                    Token token = tempTokens[i];
                    for (int j = 0; j < relWord.split(" ").length; j++) {
                        if (token.getPos_value().equals(relWord.split(" ")[j])) {
                            if (!token.getValue().equals("do") || token.getValue().equals("does")) {
                                t.setRelationWord(t.getRelationWord() + " " + token.getValue());
                                numRel++;
                            }
                        }
                        t.setRelationWord(t.getRelationWord().trim());
                        break;
                    }
                    if (numRel == relWord.split(" ").length) {
                        break;
                    }
                }
                //Put the second object to Triple
                for (int i = 0; i < tempTokens.length; i++) {
                    Token token = tempTokens[i];
                    if (token.getPos_value().equals(obj2)) {
                        t.setSecondObject(token.getValue());
                        break;
                    }
                }
                for (int i = 0; i < in.split(" ").length; i++) {
                    String pos = in.split(" ")[i];
                    for (int j = 0; j < tempTokens.length; j++) {
                        Token tk = tempTokens[j];
                        if (pos.equals("NN") || pos.equals("NNS")) {
                            if (j + 1 < tempTokens.length
                                    && !tempTokens[j + 1].getPos_value().equals("NN")
                                    && !tempTokens[j + 1].getPos_value().equals("NNS")) {
                                break;
                            }
                        }
                        if (pos.equals(tk.getPos_value())) {
                            tempTokens = removeToken(tempTokens, tk);
                            break;
                        }
                    }
                }

                result.add(t);
            }
        }
        return result;
    }

    public Token[] removeToken(Token[] tokens, Token tokenRe) {
        Token[] result = new Token[tokens.length - 1];
        int count = -1;
        boolean hasRemove = false;
        for (int i = 0; i < tokens.length; i++) {
            Token token = tokens[i];
            if (!token.getValue().equals(tokenRe.getValue()) || hasRemove == true) {
                count++;
                result[count] = new Token("", "");
                result[count].setValue(token.getValue());
                result[count].setPos_value(token.getPos_value());
            } else {
                hasRemove = true;
            }
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        //Token[] tokens = SentenseUtil.formatNerWordInQuestion("Who is the author of the paper \"Question Classification using Head Words and their Hypernyms.\"?");
        //Token[] tokens = SentenseUtil.formatNerWordInQuestion("Who published books from 1999 to 2000 ?");
        String[] questions = new String[]{
                       "Which books were written by Rafiul Ahad and Amelia Carlson in 2010 ? ",        //need to fix
                        "Which books were written by Rafiul Ahad from 1999 to 2010 ?",
                        "Which books were published by O'Reilly  in 1999 ?",
                        "How many papers were written by Rafiul Ahad ?",
                        "Who write books in 1999 ?",
                        "Who write books from 1999 to 2010 ?",
                        "How many papers were written by Rafiul Ahad in 2010 ?",
                        "Who published books from 1999 to 2000 ?",
                        "Who published books in 1999 ?",
                        "What are titles of books written by Marcus Thint ?",
                        "What book did Jennifer Widom write ?",
                        "What books did Jennifer Widom write ?",                                          //test fail
                        "Who is the author of  \"Working Models for Uncertain Data\"",
                        "What book did Philip K. Chan write in 1999 ?",                                   //test fail
                        "What book did Philip K. Chan write from 1999 to 2000?",
                        "What are the titles of the books published by O’reilly in 1999 ?",
                        "What composer wrote \" Java 2D Graphics\"",
                        "What books has isbn 1-56592-484-3",
                        "What books has doi 10.1145/360271.360274",
                        "What composer wrote books from 1999 in ACM?",
                        "Who is the author of the paper \"Question Classification using Head Words and their Hypernyms.\"?",
                        "Who wrote \"Question Classification using Head Words and their Hypernyms.\"?",
                        "What books were written by \"Philip K. Chan\" from ACM?",
                        "How many publisher did \"Philip K. Chan\" works with?"
        };
        int count =1;
        for (String question : questions) {
            Token[] tokens = SentenseUtil.formatNerWordInQuestion(question);
            tokens = SentenseUtil.optimizePosTags(tokens);
            System.out.println(count+"/ "+SentenseUtil.tokensToStr(tokens));
            RelationReg relReg = new RelationReg();
            List<TripleWord> t = relReg.extractTripleWordRel(tokens);
            for (int i = 0; i < t.size(); i++) {
                System.out.println("<" + t.get(i).getFirstObject() + "," + t.get(i).getRelationWord() + "," + t.get(i).getSecondObject() + ">");
            }
            count++;
            System.out.println("------------------------------------");
        }
    }
}
