/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uit.qabpss.processanswer;

import java.io.Serializable;
import java.util.List;
import uit.qabpss.extracttriple.TripleToken;
import uit.qabpss.preprocess.EntityType;

/**
 *
 * @author ThuanHung
 */
public class ResultAnswer implements  Serializable{
    
    protected String question;
    protected String query;
     protected String countQuery;
    protected List<List<TripleToken>> groupTripleTokens;
    protected EntityType entityTypeOfQuestion;
    protected QuestionType questionType;

    /**
     * Get the value of questionType
     *
     * @return the value of questionType
     */
    public QuestionType getQuestionType() {
        return questionType;
    }

    /**
     * Set the value of questionType
     *
     * @param questionType new value of questionType
     */
    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    protected List retrieveData   ;

    public List getRetrieveData() {
        return retrieveData;
    }

    public void setRetrieveData(List retrieveData) {
        this.retrieveData = retrieveData;
    }

    public EntityType getEntityTypeOfQuestion() {
        return entityTypeOfQuestion;
    }

    public void setEntityTypeOfQuestion(EntityType entityTypeOfQuestion) {
        this.entityTypeOfQuestion = entityTypeOfQuestion;
    }

    public List<List<TripleToken>> getGroupTripleTokens() {
        return groupTripleTokens;
    }

    public void setGroupTripleTokens(List<List<TripleToken>> groupTripleTokens) {
        this.groupTripleTokens = groupTripleTokens;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ResultAnswer(String question, String query, List<List<TripleToken>> groupTripleTokens, EntityType entityTypeOfQuestion) {
        this.question = question;
        this.query = query;
        this.groupTripleTokens = groupTripleTokens;
        this.entityTypeOfQuestion = entityTypeOfQuestion;
    }

    public ResultAnswer() {
        this.question = "";
        this.query = "";
        this.groupTripleTokens = null;
        this.entityTypeOfQuestion = null;
    }



}
