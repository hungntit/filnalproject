/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uit.qabpss.processanswer;

import java.io.IOException;
import java.util.List;
import uit.qabpss.entityrecog.Recognizer;
import uit.qabpss.extracttriple.ExtractTriple;
import uit.qabpss.extracttriple.TripleToken;
import uit.qabpss.generatequery.GenSQLQuery;
import uit.qabpss.generatequery.RetrieveData;
import uit.qabpss.preprocess.EntityType;
import uit.qabpss.preprocess.SentenseUtil;
import uit.qabpss.preprocess.Token;
import uit.qabpss.util.exception.QuestionNotSolveException;

/**
 *
 * @author aa
 */
public class ProcessAnswer {

    
    protected ExtractTriple extractTriple;
    protected Recognizer recognizer;
    
    public ProcessAnswer() {
        extractTriple = new ExtractTriple();
        recognizer = new Recognizer();
        
    }

    public ProcessAnswer(ExtractTriple extractTriple, Recognizer recognizer) {
        this.extractTriple = extractTriple;
        this.recognizer = recognizer;
    }
    public ResultAnswer answerQuestion(ResultAnswer resultAnswer,String originValue,String replaceValue) throws IOException, QuestionNotSolveException
    {
        String question = resultAnswer.getQuestion();
        
        System.out.println(resultAnswer.getQuestion());
        String query = resultAnswer.getQuery();
        List<List<TripleToken>> groupTripleTokens = resultAnswer.getGroupTripleTokens();
        EntityType entityTypeOfQuestion = resultAnswer.getEntityTypeOfQuestion();

        for(List<TripleToken> tripleTokens:groupTripleTokens)
        {
            for(TripleToken tripleToken:tripleTokens)
            {
                Token obj1 = tripleToken.getObj1();
                Token obj2 = tripleToken.getObj2();
                if(obj1.getValue().equals(originValue))
                    obj1.setValue(replaceValue);
                if(obj2.getValue().equals(originValue))
                    obj2.setValue(replaceValue);
            }
        }
        List retrieveData = RetrieveData.retrieveData(query, groupTripleTokens, entityTypeOfQuestion, 0, 100);
        question          = question.replace(originValue, "\""+replaceValue+"\"");
        question          = question.replace("\"\"", "\"");
        resultAnswer.setQuestion(question);
        resultAnswer.setRetrieveData(retrieveData);
        return resultAnswer;
    }
    public ResultAnswer answerQuestion(String question) throws IOException, QuestionNotSolveException
    {
       
        List retrieveData   =   null;
        ResultAnswer resultAnswer   =   null;
        Token[] tokens = SentenseUtil.formatNerWordInQuestion(question);
        tokens = SentenseUtil.optimizePosTags(tokens);
        try
        {
        /*-------------------------Step 2-------------------------------------------*/
        List<TripleToken> list = extractTriple.extractTripleWordRelation(tokens);
        /*-------------------------Step 3-------------------------------------------*/
        recognizer.identifyTripleTokens(list);
            EntityType entityTypeOfQuestion = recognizer.recognizeEntityOfQuestion(tokens);
            /*-------------------------Step 4-------------------------------------------*/
            List<List<TripleToken>> groupTripleTokens = TripleToken.groupTripleTokens(list);
            String query = GenSQLQuery.genQuery(groupTripleTokens, entityTypeOfQuestion);

        retrieveData = RetrieveData.retrieveData(query,groupTripleTokens, entityTypeOfQuestion, 0, 999999);
        resultAnswer   =   new ResultAnswer(question, query, groupTripleTokens, entityTypeOfQuestion);
        resultAnswer.setRetrieveData(retrieveData);
        resultAnswer.setQuestionType(QuestionType.recognizeTypeOfQuestion(tokens));

        }
        catch(Exception ex)
        {
            throw  new QuestionNotSolveException();
        }
        return resultAnswer;
    }

    

    /**
     * Get the value of countQuery
     *
     * @return the value of countQuery
     */
    
    /**
     * Get the value of recognizer
     *
     * @return the value of recognizer
     */
    public Recognizer getRecognizer() {
        return recognizer;
    }

    /**
     * Set the value of recognizer
     *
     * @param recognizer new value of recognizer
     */
    public void setRecognizer(Recognizer recognizer) {
        this.recognizer = recognizer;
    }

    /**
     * Get the value of extractTriple
     *
     * @return the value of extractTriple
     */
    public ExtractTriple getExtractTriple() {
        return extractTriple;
    }

    /**
     * Set the value of extractTriple
     *
     * @param extractTriple new value of extractTriple
     */
    public void setExtractTriple(ExtractTriple extractTriple) {
        this.extractTriple = extractTriple;
    }

}
