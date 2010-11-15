
package uit.qabpss.extracttriple;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import uit.qabpss.preprocess.SentenseUtil;
import uit.qabpss.preprocess.Token;
import uit.qabpss.preprocess.TripleWord;
import uit.qabpss.util.hibernate.HibernateUtil;

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
  
    public static void main(String[] args) throws IOException {
        String[] questions = new String[]{
            "Who is the author of the book, \"Question Classification using Head Words and their Hypernyms.\"?",
            "What publications have resulted from the TREC?",
            "Which books were written by Rafiul Ahad and Amelia Carlson in 2010 ? ", //need to fix
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
            "What books did Jennifer Widom write ?", //test fail
            "Who is the author of  \"Working Models for Uncertain Data\"",
            "What book did Philip K. Chan write in 1999 ?", //test fail
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
        HibernateUtil.getSessionFactory();
        for (String question : questions) {
            Token[] tokens = SentenseUtil.formatNerWordInQuestion(question);
            tokens = SentenseUtil.optimizePosTags(tokens);
            System.out.println(count+"/ "+SentenseUtil.tokensToStr(tokens));
            ExtractTriple exTriple = new ExtractTriple();
            List<TripleWord> t = exTriple.extractTripleWordRel(tokens);
            for (int i = 0; i < t.size(); i++) {
                System.out.println("< " + t.get(i).getFirstObject() + " , " + t.get(i).getRelationWord() + " , " + t.get(i).getSecondObject() + " >");
            }
            count++;
            System.out.println("------------------------------------");
        }
    }
}
