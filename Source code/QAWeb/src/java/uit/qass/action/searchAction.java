/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.qass.action;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import uit.qass.core.search.UtimateSearch;
import uit.qass.formBean.searchActionForm;
import uit.qass.core.search.searchAuthor;
import uit.qass.dbconfig.DBInfoUtil;
import uit.qass.dbconfig.TableInfo;
import uit.qass.model.Publication;
import uit.qass.util.Table;

/**
 *
 * @author Hoang-PC
 */
public class searchAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    private static final String WARNING_1 = "Which authors do you want to find ?";
    private static final String WARNING_2 = "Have no persons that you want to find !";
    private static final String WARNING_3 = "Have no publications that you want to find !";

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
        searchActionForm searchForm = (searchActionForm) form;
        HttpSession session = request.getSession(true);
        session.removeAttribute("publications");
        if ("Author".equals(searchForm.getType())) {
            String keyWords = searchForm.getKeyWord();
            List<String> authorNames = new ArrayList<String>();
            authorNames = searchAuthor.searchAuthorByKey(keyWords);
            request.setAttribute("authors", authorNames);
            if (authorNames != null && authorNames.size() > 0) {
                request.setAttribute("warning", WARNING_1);
            } else {
                request.setAttribute("warning", WARNING_2);
            }
            return mapping.findForward(SUCCESS);
        }
        if ("All".equals(searchForm.getType())) {
            String keyWords = searchForm.getKeyWord();
            TableInfo returnTable = DBInfoUtil.getDBInfo().findTableInfoByName(Table.PUBLICATION);
            List<Object> list = UtimateSearch.searchByKeyWords(Publication.class, keyWords, returnTable, 0, 300);
            session.setAttribute("publications", list);
            if (list == null || list.isEmpty()) {
                request.setAttribute("warning", WARNING_3);
            }
            return mapping.findForward(SUCCESS);
        }
        return mapping.findForward(SUCCESS);
    }
}
