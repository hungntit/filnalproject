/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.qabpss.analysisandprocess;

import java.io.IOException;
import java.util.regex.Pattern;
import uit.qabpss.preprocess.SentenseUtil;
import uit.qabpss.preprocess.Token;
import uit.qabpss.preprocess.TripleWord;

/**
 *
 * @author aa
 */
public class RelationReg {

    public TripleWord extractTripleWordRel(Token[] tokens) {
        TripleWord result = null;
        result = processFomular(tokens, "NNS VB VB IN NNP --> NNS,VB VB,NNP");
        return result;
    }

    public TripleWord processFomular(Token[] tokens, String formular) {
        String[] inoutStr = formular.split("-->");
        String in = inoutStr[0];
        String out = inoutStr[1];
        //get results from formular
        String obj1 = out.split(",")[0].trim();
        String relWord = out.split(",")[1].trim();
        String obj2 = out.split(",")[2].trim();
        //
        Token[] tempTokens = tokens;
        TripleWord t = new TripleWord("", "", "");
        //
        String posString = SentenseUtil.tokensToPosTagsStr(tempTokens);
        Pattern pattern = Pattern.compile(in);
        // Check the formular is matched
        if (pattern.matcher(posString).find()) {
            //Put the first object to Triple
            for (int i = 0; i < tempTokens.length; i++) {
                Token token = tempTokens[i];
                if (token.getPos_value().equals(obj1)) {
                    t.setFirstObject(token.getValue());
                    break;
                }
            }
            //Put the relationship word to Triple
            for (int i = 0; i < tempTokens.length; i++) {
                Token token = tempTokens[i];
                for (int j = 0; j < relWord.split(" ").length; j++) {
                    if (token.getPos_value().equals(relWord.split(" ")[j])) {
                        t.setRelationWord(t.getRelationWord() +" "+ token.getValue());
                    }
                    t.setRelationWord(t.getRelationWord().trim());
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
        }
        return t;
    }

    public static void main(String[] args) throws IOException {
        Token[] tokens = SentenseUtil.formatNerWordInQuestion("Which books were written by Rafiul Ahad from 1999 to 2010 ?");
        tokens = SentenseUtil.optimizePosTags(tokens);
        System.out.println(SentenseUtil.tokensToStr(tokens));
        RelationReg relReg = new RelationReg();
        TripleWord t = relReg.extractTripleWordRel(tokens);
        System.out.println(t.getFirstObject());
        System.out.println(t.getRelationWord());
        System.out.println(t.getSecondObject());
    }
}
