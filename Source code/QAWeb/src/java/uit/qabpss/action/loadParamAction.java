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
import uit.qabpss.dbconfig.DBInfoUtil;
import uit.qabpss.dbconfig.Param;
import uit.qabpss.dbconfig.TableInfo;
import uit.qabpss.formbean.AdvanceSearchForm;

/**
 *
 * @author ThuanHung
 */
public class loadParamAction extends org.apache.struts.action.Action {
    
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
        String tableAliasName    =   (String)request.getParameter("tbl");
        AdvanceSearchForm   advanceSearchForm   =   (AdvanceSearchForm)form;
        TableInfo table;
        if(tableAliasName == null)
        {
            table   =   advanceSearchForm.getTableInfo();
            if(table == null)
                table =   DBInfoUtil.getDBInfo().getTables().get(1);
        }
        else
        {
            table =   DBInfoUtil.getDBInfo().findTableInfoByAliasName(tableAliasName);
        }

        List<Param> params  =   Param.getParamsFromTableInfo(table);
        advanceSearchForm.setParams(params);
        advanceSearchForm.setTableInfo(table);
        response.setContentType("text/html");
        if(tableAliasName == null)
            return mapping.findForward(SUCCESS);
        else
            return mapping.findForward("load");
    }
}
