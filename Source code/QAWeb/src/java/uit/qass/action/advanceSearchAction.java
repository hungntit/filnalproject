/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uit.qass.action;

import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import uit.qass.core.search.UtimateSearch;
import uit.qass.dbconfig.Param;
import uit.qass.dbconfig.TableInfo;
import uit.qass.formBean.AdvanceSearchForm;
import uit.qass.model.Publication;

/**
 *
 * @author ThuanHung
 */
public class advanceSearchAction extends org.apache.struts.action.Action {
    
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
        AdvanceSearchForm advanceSForm  =   (AdvanceSearchForm)form;
        TableInfo   table               =   advanceSForm.getTableInfo();
        List<Param> paramslist          =   advanceSForm.getParams();
        Param[] params                   =   (Param[])paramslist.toArray(new Param[paramslist.size()]);
        
        int startRow =0;
        String offset   =   request.getParameter("pager.offset");
        if(offset == null)
        {
            int totalRowsCount  =   UtimateSearch.countByParam(params, advanceSForm.isIsAndOperator(), table);
            request.getSession().setAttribute("totalRowsCount", totalRowsCount);

        }
        else
        {
            startRow    =   Integer.parseInt(offset);
        }
        int range   =   20;
        if(request.getParameter("range")!= null)
        {
            range   =   Integer.parseInt(request.getParameter("range"));
        }
        List objs =   UtimateSearch.searchByParam(table.getClassTable(), params, advanceSForm.isIsAndOperator(),table , startRow , startRow+range);
        if(objs.size()>0)
        {
            if(objs.get(0) instanceof Comparable)
            {
                Collections.sort(objs);
            }
        }
        request.setAttribute("objs", objs);
        request.setAttribute("startRow", startRow);
        request.setAttribute("range", range);
        return mapping.findForward(table.getAliasName().trim().toLowerCase());
    }
}
