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
    private static final String FAIL = "fail";

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

        /*Initiation QA Process*/
        ExtractTriple extract = null;
        Recognizer reg = null;
        Token[] tokens = null;
        try {
            extract = new ExtractTriple();
            reg = new Recognizer();
        } catch (Exception e) {
            request.setAttribute("error", "can not load Extractor and Recognizer !");
            return mapping.findForward(FAIL);
        }
        
        /*-------Step 1: Tokenizer and Pos Tagger-------*/
        try{
        tokens = SentenseUtil.formatNerWordInQuestion(f.getSentence());
        tokens = SentenseUtil.optimizePosTags(tokens);
        }catch(Exception e){
            request.setAttribute("error", "Sentence tokenize fial !");
            return mapping.findForward(FAIL);
        }
        System.out.println(SentenseUtil.tokensToStr(tokens));

        /*-------Step 2: extract Triples in sentences and recognize-------*/
        List<TripleToken> list = extract.extractTripleWordRelation(tokens);        
        reg.identifyTripleTokens(list);
        for (TripleToken tripleToken : list) {
            System.out.println(tripleToken);
            if (!tripleToken.isNotIdentified()) {
                System.out.println(tripleToken.getObj1().toString() + ":" + tripleToken.getObj1().getEntityType().toString() + "," + tripleToken.getObj2().toString() + ":" + tripleToken.getObj2().getEntityType().toString());
            }

        }
                
        EntityType entityTypeOfQuestion = reg.recognizeEntityOfQuestion(tokens);
        List<List<TripleToken>> groupTripleTokens = TripleToken.groupTripleTokens(list);
        String selectandFromQuery = GenSQLQuery.genQuery(groupTripleTokens, entityTypeOfQuestion);
        System.out.println(selectandFromQuery);
        List retrieveData = RetrieveData.retrieveData(groupTripleTokens, entityTypeOfQuestion, 0, 100);
        request.setAttribute("results", retrieveData);
        return mapping.findForward(SUCCESS);
    }
}
