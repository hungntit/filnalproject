/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uit.qabpss.formbean;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.ImageButtonBean;
import uit.qabpss.processanswer.ResultAnswer;

/**
 *
 * @author Admin
 */
public class QAForm extends org.apache.struts.action.ActionForm {
    
    private String sentence;
    private ImageButtonBean submitQA;
    private ResultAnswer    resultAnswer;


    public ResultAnswer getResultAnswer() {
        return resultAnswer;
    }

    public void setResultAnswer(ResultAnswer resultAnswer) {
        this.resultAnswer = resultAnswer;
    }

    public ImageButtonBean getSubmitQA() {
        return submitQA;
    }

    public void setSubmitQA(ImageButtonBean submitQA) {
        this.submitQA = submitQA;
    }
    public ImageButtonBean getSubmit() {
        return submitQA;
    }

    public void setSubmit(ImageButtonBean submit) {
        this.submitQA = submit;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    /**
     *
     */
    public QAForm() {
        sentence="";
        submitQA = new ImageButtonBean();
        resultAnswer    =   null;
    }

    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if (getSentence() == null || getSentence().length() < 1 || getSentence().isEmpty()) {
            errors.add(null, new ActionMessage("errors.input"));
        }
        if (getSentence().length() < 25) {
            errors.add(null, new ActionMessage("errors.minsentence"));
        }
        return errors;
    }
}
