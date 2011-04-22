/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.qabpss.preprocess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class TestSentenseUtil {

    public static void main(String[] args) throws IOException {
        String[] questions = new String[]{
            "Which books were written by Rafiul Ahad and Amelia Carlson in 2010 ? ",
            "Which books were written by Rafiul Ahad or Amelia Carlson in 2010 ? ",
            "Which books are published in 1999 and written by \"Ken Arnold\"?",
            "Which books are written by \"Ken Arnold\" and published by IEEE ?",
            "How many books are written by \"Ken Arnold\" and published by IEEE ?",
            "Which books are written by \"Ken Arnold\" and have source from DBPL ?",
            "Who wrote books in 2010 and had source from DBPL",
            "Which papers are composed by \"Ken Arnold\" and have source from DBPL ?",
            "Who is the author of  \"Working Models for Uncertain Data\" and \"Active Database Systems.\"",};

        for (String question : questions) {
            System.out.println("--------");
            Token[] tokens = SentenseUtil.formatNerWordInQuestion(question);
            tokens = SentenseUtil.optimizePosTags(tokens);
            List<List<Token>> result = new ArrayList<List<Token>>();
            result = SentenseUtil.checkAndCutSen(tokens);
            if (result != null) {
                for (int i = 0; i < result.size(); i++) {
                    List<Token> list = result.get(i);
                    System.out.println(SentenseUtil.tokensToStr((Token[]) list.toArray()));

                }
            }
        }
    }
}
