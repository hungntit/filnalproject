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
     * @param args the command line arguments
     *
     * Tester: Hoang Nguyen (revison 234)
     * +++++++++++++++++++++++++++++++++
     * + Num of Test : 18
     * + Pass : 14
     * + Fail : 4 (7th, 14th, 16th, 17th)
     * + Warning: DO NOT USE 'LIKE' OPERATOR IN QUERY SENTENCES, REPLACE IT TO '='
     * +++++++++++++++++++++++++++++++++
     * * Tester: Hoang Nguyen (revison 241)
     * +++++++++++++++++++++++++++++++++
     * + Num of Test : 18
     * + Pass : 17
     * + Fail : 1 (16th)
     * + Warning: 
     * +++++++++++++++++++++++++++++++++
     *
     */
    public static void main(String[] args) throws IOException {
        // List of test questions here
        HibernateUtil.getSessionFactory();
        String[] questions = new String[]{
        /*   "Which books were written by Rafiul Ahad or Amelia Carlson in 2010 ? ",
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
            "Who is the author of  \"Working Models for Uncertain Data\" and ACM 1999",
            "Who is the author of  \"Working Models for Uncertain Data\" and ACM",
            "What book did Philip K. Chan write in 1999?",
            "What book did Philip K. Chan write from 1999 to 2000?",
            "What are the titles of the books published by O’reilly in 1999 ?",
            "What composer wrote \" Java 2D Graphics\"",*/
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
            "What books did Richard L. Muller write for Springer",
        };
        System.out.println("nums test: " + questions.length);
        ExtractTriple extract = new ExtractTriple();
        Recognizer  reg     =   new Recognizer();
        for (String question : questions) {
            Date    date    =   new Date();
            long begin   =   date.getTime();
            System.out.println("----------------------------------------------------------------------------");
            Token[] tokens = SentenseUtil.formatNerWordInQuestion(question);
            tokens = SentenseUtil.optimizePosTags(tokens);
            System.out.println(SentenseUtil.tokensToStr(tokens));
            List<TripleToken> list = extract.extractTripleWordRelation(tokens);
            reg.identifyTripleTokens(list);
            for(TripleToken tripleToken:list)
            {
                System.out.println(tripleToken);
                //reg.identifyTripleToken(tripleToken);
                if(!tripleToken.isNotIdentified())
                {
                    System.out.println(tripleToken.getObj1().toString()+":"+tripleToken.getObj1().getEntityType().toString() +","+tripleToken.getObj2().toString()+":"+tripleToken.getObj2().getEntityType().toString());
                }

            }
            /*
            for(Token token: tokens)
            {

                String s    =   token.toString()+"|"+ token.getEntityType().toString()+ " ";
                if(token.getEntityType().isNull())
                {
                    s   =   token.toString()+ " ";
                }
                System.out.print(s);
            }*/
             System.out.println();

             EntityType entityTypeOfQuestion    =   reg.recognizeEntityOfQuestion(tokens);
             String selectandFromQuery  =   GenSQLQuery.genQuery(list, entityTypeOfQuestion);
             System.out.println(selectandFromQuery);

            date    =   new Date();
            double end    =   date.getTime();
            System.out.println("Time: "+(end - begin)/1000);
        }
    }
}