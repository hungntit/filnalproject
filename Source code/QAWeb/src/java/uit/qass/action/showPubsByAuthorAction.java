/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uit.qass.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import uit.qass.core.search.searchAuthor;
import uit.qass.model.Author;
import uit.qass.model.Publication;

/**
 *
 * @author Hoang-PC
 */
public class showPubsByAuthorAction extends org.apache.struts.action.Action {
     
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
        String authorName = (String) request.getParameter("authorName");        
        List<Publication> publications = searchAuthor.searchPubsByAuthorName(authorName);
        request.setAttribute("authorName", authorName);
        request.setAttribute("publications", publications);
        return mapping.findForward(SUCCESS);
    }
}
