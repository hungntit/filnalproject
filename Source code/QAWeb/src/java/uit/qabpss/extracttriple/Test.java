/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.qabpss.extracttriple;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import uit.qabpss.entityrecog.Recognizer;
import uit.qabpss.generatequery.GenSQLQuery;
import uit.qabpss.preprocess.EntityType;
import uit.qabpss.preprocess.SentenseUtil;
import uit.qabpss.preprocess.Token;
import uit.qabpss.util.hibernate.HibernateUtil;

/**
 *
 * @author hoang
 */
public class Test {

    public static final String PATH = "src\\java\\uit\\qabpss\\util\\resources\\test\\test_question_29112010.txt";

    public static String[] loadQuestions() {
        List<String> lst = new ArrayList<String>();
        int i = 0;
        BufferedReader input = null;
        try {
            //use buffering, reading one line at a time
            input = new BufferedReader(new FileReader(new File(PATH)));
            String line = null;
            while ((line = input.readLine()) != null) {
                lst.add(line);
                i++;
            }
        } catch (IOException ex) {
            Logger.getLogger(RuleReader.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(RuleReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lst.toArray(new String[i]);
    }

    public static void main(String[] args) {
        // List of test questions here
        List<Integer> errors = new ArrayList<Integer>();
        HibernateUtil.getSessionFactory();
        String[] questions = loadQuestions();
        System.out.println("nums test: " + questions.length);
        ExtractTriple extract = new ExtractTriple();
        Recognizer reg = new Recognizer();
        int count = 1;
        for (String question : questions) {
            try {
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
                List<List<TripleToken>> groupTripleTokens = TripleToken.groupTripleTokens(list);
                String selectandFromQuery = GenSQLQuery.genQuery(groupTripleTokens, entityTypeOfQuestion);
                System.out.println(selectandFromQuery);

                date = new Date();
                double end = date.getTime();
                System.out.println("Time: " + (end - begin) / 1000);
            } catch (Exception e) {
                errors.add(count - 1);
                continue;
            }
        }
        String e = "";
        for (int i = 0; i < errors.size(); i++) {
            Integer integer = errors.get(i);
            e += " " + integer.toString();
        }
        System.out.println(e);
        System.out.println("Rate :" + ((float) ((questions.length - errors.size()) * 100) / (float) (questions.length)));
    }
}
