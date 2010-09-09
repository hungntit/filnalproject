/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.qass.action;

import java.util.ArrayList;
import java.util.Collections;
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
        Collections.sort(publications);
        request.setAttribute("totalNums", publications.size());
        request.setAttribute("authorName", authorName);
        request.setAttribute("publications", publications);
        List<List<Publication>> newlist = groupByYear(publications);
        request.setAttribute("publicationGrouped", newlist);
        return mapping.findForward(SUCCESS);
    }

    private List<List<Publication>> groupByYear(List<Publication> list) {
        int tempYear = list.get(0).getYear();
        List<List<Publication>> result = new ArrayList<List<Publication>>();
        List<Publication> l = new ArrayList<Publication>();

        for (int i = 0; i < list.size(); i++) {
            Publication publication = list.get(i);
            if (publication.getYear() == tempYear) {
                l.add(publication);
            } else {
                tempYear = publication.getYear();
                result.add(l);
                l = new ArrayList<Publication>();
                l.add(publication);
            }
            if (i == list.size() - 1) {
                result.add(l);
            }
        }
        return result;
    }
}
