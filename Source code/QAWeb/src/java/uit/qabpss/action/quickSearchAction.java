/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.qabpss.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import uit.qabpss.core.search.searchAuthor;
import uit.qabpss.core.search.searchPublication;

/**
 *
 * @author Hoang-PC
 */
public class quickSearchAction extends org.apache.struts.action.Action {

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
        String quickString = request.getParameter("p");
        HttpSession session = request.getSession(true);
        if ("topPubs".equals(quickString)) {
            List pubs = searchPublication.searchTop100();
            if (pubs != null) {
                session.setAttribute("publications", pubs);
                session.setAttribute("pagesize", "10");
                return mapping.findForward(SUCCESS);
            }
        }
        else if("topAus".equals(quickString)) {
            List Aus = searchAuthor.searchtopAuthor();
            if (Aus != null) {
                request.setAttribute("authors", Aus);
                return mapping.findForward(SUCCESS);
            }
        } else{
            List pubs = searchPublication.searchByType(quickString);
            if (pubs != null) {
                session.setAttribute("publications", pubs);
                session.setAttribute("pagesize", "10");
                return mapping.findForward(SUCCESS);
            }
        }
        return mapping.findForward(SUCCESS);
    }
}
