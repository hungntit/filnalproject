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

        List objs =   UtimateSearch.searchByParam(table.getClassTable(), params, advanceSForm.isIsAndOperator(),table , 0 , 1);
        request.setAttribute("objs", objs);

        return mapping.findForward(table.getAliasName().trim().toLowerCase());
    }
}
