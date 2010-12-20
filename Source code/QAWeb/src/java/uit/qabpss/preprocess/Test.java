/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.qabpss.preprocess;

import uit.qabpss.extracttriple.TripleToken;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import org.hibernate.Hibernate;
import uit.qabpss.entityrecog.Recognizer;
import uit.qabpss.extracttriple.ExtractTriple;
import uit.qabpss.generatequery.GenSQLQuery;
import uit.qabpss.util.hibernate.HibernateUtil;

/**
 *
 * @author ThuanHung
 */
public class Test {

    /**    
     * +++++++++++++++++++++++++++++++++
     * + Num of Test : 36
     * + Pass : 31
     * + Fail : 4 (1st,13th,26th,30th)
     * + not solve: 1 (28th)
     * + Warning: TEST GENERATE QUERY
     * => FAIL MEANS QUERY CAN NOT RUN
     * +++++++++++++++++++++++++++++++++
     */
    public static void main(String[] args) throws IOException {
        // List of test questions here
        HibernateUtil.getSessionFactory();
        String[] questions = new String[]{
            "Which books were written by Rafiul Ahad and Amelia Carlson in 2010 ? ",
            "Which books were written by Rafiul Ahad from 1999 to 2010 ?",
            "Which books were published by O'Reilly  in 1999 ?",
            "How many papers were written by Rafiul Ahad ?",
            "Who compose books in 1999 ?",
            "Who write books from 1999 to 2010 ?",
            "How many papers were written by Rafiul Ahad in 2010 ?",
            "Who published books from 1999 to 2000 ?",
            "Who published books in 1999 ?",
            "What are titles of books written by Marcus Thint ?",
            "What papers did Jennifer Widom write ?",
            "What books did Jennifer Widom write ?",
            "Who is the author of  \"Working Models for Uncertain Data\" and \"Active Database Systems.\"",
            "Who is the author of  \"Working Models for Uncertain Data\"",
            "What book did Philip K. Chan write in 1999?",
            "What book did Philip K. Chan write from 1999 to 2000?",
            "What are the titles of the books published by O'reilly in 1999 ?",
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
            "What publications have resulted from TREC in 1999?",
            "Which author write \"Foundations of Databases.\" in 1999",
            "Which books did Philip K. Chan or Marcus Thint write in ACM ?",
            "What book did Philip K. Chan write in 1999 from ACM?",
            "Name some books which Richard L. Muller writes for Springer",
            "List all books were published by Springer in 2010",
            "What year is \"Foundations of Databases.\" written in?",// not solve yet // FAIL TEST REV 234
            "What books refer to \"Foundations of Databases.\"",
            "What books did Richard L. Muller write for Springer",};
        System.out.println("nums test: " + questions.length);
        ExtractTriple extract = new ExtractTriple();
        Recognizer reg = new Recognizer();
        int count = 1;
        for (String question : questions) {
            Date date = new Date();
            long begin = date.getTime();
            System.out.println("----------------------------------------------------------------------------");
            Token[] tokens = SentenseUtil.formatNerWordInQuestion(question);
            tokens = SentenseUtil.optimizePosTags(tokens);
            System.out.println(count + "/");
            count++;
            System.out.println(SentenseUtil.tokensToStr(tokens));
            List<TripleToken> list = extract.extractTripleWordRelation(tokens);
            reg.identifyTripleTokens(list);
            for (TripleToken tripleToken : list) {
                System.out.println(tripleToken);
                if (!tripleToken.isNotIdentified()) {
                    System.out.println(tripleToken.getObj1().toString() + ":" + tripleToken.getObj1().getEntityType().toString() + "," + tripleToken.getObj2().toString() + ":" + tripleToken.getObj2().getEntityType().toString());
                }

            }
            System.out.println();

            EntityType entityTypeOfQuestion = reg.recognizeEntityOfQuestion(tokens);
            String selectandFromQuery = GenSQLQuery.genQuery(list, entityTypeOfQuestion);
            System.out.println(selectandFromQuery);

            date = new Date();
            double end = date.getTime();
            System.out.println("Time: " + (end - begin) / 1000);
        }
    }
}
