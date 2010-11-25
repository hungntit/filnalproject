/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.qabpss.preprocess;

import uit.qabpss.extracttriple.TripleToken;
import java.io.IOException;
import java.util.List;
import uit.qabpss.extracttriple.ExtractTriple;

/**
 *
 * @author ThuanHung
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        String[] questions = new String[]{
           "Which books were written by Rafiul Ahad and Amelia Carlson in 2010 ? ",
           "Which books were written by Rafiul Ahad from 1999 to 2010 ?",
            "Which books were published by O'Reilly  in 1999 ?",
            "How many papers were written by Rafiul Ahad ?",
            "Who write books in 1999 ?",
            "Who write books from 1999 to 2010 ?",
            "How many papers were written by Rafiul Ahad in 2010 ?",
           "Who published books from 1999 to 2000 ?",
            "Who published books in 1999 ?",
            "What are titles of books written by Marcus Thint ?",
            "What papers did Jennifer Widom write ?",
            "What books did Jennifer Widom write ?",
            "Who is the author of  \"Working Models for Uncertain Data and ACM\" 1999" ,
            "Who is the author of  \"Working Models for Uncertain Data and ACM\"" ,
            "What book did Philip K. Chan write in 1999?",
            "What book did Philip K. Chan write from 1999 to 2000?",
            "What are the titles of the books published by O’reilly in 1999 ?",
           "What composer wrote \" Java 2D Graphics\"",
            "What books has isbn 1-56592-484-3",
            "What books has doi 10.1145/360271.360274",
            "What composer wrote books from 1999 in ACM?",
            "Who is the author of the paper \"Question Classification using Head Words and their Hypernyms.\"?",
            "Who wrote \"Question Classification using Head Words and their Hypernyms.\"?",
            "What books were written by \"Philip K. Chan\" from ACM?",
            "How many publisher did \"Philip K. Chan\" cooperate with?",
            "What books were published by ACM or Springer in 2010?",
            "What publications have resulted from TREC?",
             "What publications have resulted from TREC in 1999?",//fail test
             "Which author write \"database\" in 1999",
        };
        System.out.println("nums test: "+questions.length);
        ExtractTriple extract   =   new ExtractTriple();
        for (String question : questions) {
            System.out.println("-----------------------------------------------------------");
            Token[] tokens = SentenseUtil.formatNerWordInQuestion(question);
            tokens = SentenseUtil.optimizePosTags(tokens);
            System.out.println(SentenseUtil.tokensToStr(tokens));
            List<TripleToken> list  =   extract.extractTripleWordRelation(tokens);
            for(TripleToken tripleToken:list)
            {
                
                System.out.println(tripleToken);

            }

        }
    }
}
