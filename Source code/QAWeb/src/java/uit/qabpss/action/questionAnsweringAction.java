/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.qabpss.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import uit.qabpss.entityrecog.Recognizer;
import uit.qabpss.extracttriple.ExtractTriple;
import uit.qabpss.extracttriple.TripleToken;
import uit.qabpss.formbean.QAForm;
import uit.qabpss.generatequery.GenSQLQuery;
import uit.qabpss.generatequery.RetrieveData;
import uit.qabpss.preprocess.EntityType;
import uit.qabpss.preprocess.SentenseUtil;
import uit.qabpss.preprocess.Token;

/**
 *
 * @author Admin
 */
public class questionAnsweringAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @throws java.lang.Exception
     * @return
     */
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        QAForm f = (QAForm) form;
        ExtractTriple extract = new ExtractTriple();
        Recognizer reg = new Recognizer();
        System.out.println("----------------------------------------------------------------------------");
        Token[] tokens = SentenseUtil.formatNerWordInQuestion(f.getSentence());
        tokens = SentenseUtil.optimizePosTags(tokens);
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
        List retrieveData = RetrieveData.retrieveData(list, entityTypeOfQuestion, 0, 100);
        request.setAttribute("results", retrieveData);
        return mapping.findForward(SUCCESS);
    }
}
