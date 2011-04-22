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
import uit.qabpss.core.search.SearchPublication;
import uit.qabpss.model.Publication;

/**
 *
 * @author aa
 */
public class showPubDetailAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    private static final String WARNING = "There are no references now !";

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
        String id = request.getParameter("id");
        Publication publication = SearchPublication.searchPubByID(id);
        String sourceName = publication.getSource();
        request.setAttribute("publication", publication);
        if (publication.getRefPubs().isEmpty()) {
            request.setAttribute("warning", WARNING);
        }
        if (sourceName != null && !"".equals(sourceName)) {
            List pubs = SearchPublication.searchBySource(sourceName,id);
            request.setAttribute("sameSourcePubs", pubs);
        }
        return mapping.findForward(SUCCESS);
    }
}
