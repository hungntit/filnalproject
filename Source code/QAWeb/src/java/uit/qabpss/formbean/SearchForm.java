/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.qabpss.formbean;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.ImageButtonBean;

/**
 *
 * @author Hoang-PC
 */
public class SearchForm extends org.apache.struts.action.ActionForm {

    private String type;
    private String keyWord;
    private ImageButtonBean submit;
    private String maxResult;

    public String getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(String maxResult) {
        this.maxResult = maxResult;
    }

    public SearchForm() {
        type = "";
        keyWord = "";
        submit = new ImageButtonBean();
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public ImageButtonBean getSubmit() {
        return submit;
    }

    public void setSubmit(ImageButtonBean submit) {
        this.submit = submit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        if (getKeyWord() == null || "".equals(getKeyWord())) {
            errors.add("key word", new ActionMessage("errors.required",new String[]{"key word"}));
            return errors;
        }
        if (getKeyWord().length() < 3) {
            errors.add("key word", new ActionMessage("errors.minlength",new String[]{"key word","3"}));
            return errors;
        }
        return errors;
    }
}
