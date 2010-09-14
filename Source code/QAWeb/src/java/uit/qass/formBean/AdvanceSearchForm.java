/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uit.qass.formBean;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.ImageButtonBean;
import uit.qass.dbconfig.DBInfoUtil;
import uit.qass.dbconfig.Param;
import uit.qass.dbconfig.TableInfo;
import uit.qass.dbconfig.Type;

/**
 *
 * @author ThuanHung
 */
public class AdvanceSearchForm extends org.apache.struts.action.ActionForm {
    

    private List<Param> params;
    private ImageButtonBean submit;
    private TableInfo   tableInfo;
    private boolean     isAndOperator;

    public boolean isIsAndOperator() {
        return isAndOperator;
    }

    public void setIsAndOperator(boolean isAndOperator) {
        this.isAndOperator = isAndOperator;
    }

    public TableInfo getTableInfo() {
        return tableInfo;
    }

    public void setTableInfo(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
    }
    
    public ImageButtonBean getSubmit() {
        return submit;
    }

    public void setSubmit(ImageButtonBean submit) {
        this.submit = submit;
    }

    public List<Param> getParams() {
        return params;
    }

    public void setParams(List<Param> params) {
        this.params = params;
    }
    public void setParam(int index,Param param)
    {
        if(index> params.size())
        {
            params.set(index, param);
        }
    }

    public Param getParam(int index)
    {
        return params.get(index);
    }
    /**
     *
     */
    public AdvanceSearchForm() {
        submit = new ImageButtonBean();
        
        params  =   new ArrayList<Param>();
        isAndOperator   =   true;
        // TODO Auto-generated constructor stub
    }

    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        
        for(Param param:params)
        {
            if(param.getColumn().getType().equals(Type.INTEGER)||param.getColumn().getType().equals(Type.LONG))
            {
                System.out.println(param.getValue());
                try
                {
                    int number  =   Integer.parseInt(param.getValue());
                }
                catch(Exception ex)
                {
                    errors.add(param.getColumn().getAliasName(), new ActionMessage("errors.isnumber",new String[]{param.getColumn().getAliasName()}));
                }
                
            }
        }
        
        return errors;
    }
}
