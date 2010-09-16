package uit.qass.action;

import java.util.Locale;
import javax.ejb.SessionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class LanguageSelectAction extends DispatchAction {

    private ActionForward myAction;

    public ActionForward vietnamese(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        System.out.println(mapping.getInputForward());
        //String path = (String) request.getSession().getAttribute("LAST_ACTION");
        request.getSession().setAttribute(
                Globals.LOCALE_KEY, new Locale("vn"));
//        if (path != null && !path.isEmpty()) {
//            myAction = new ActionForward(path);
//        } else {
        myAction = new ActionForward("/Welcome.do");
        //}
        return myAction;
    }

    public ActionForward english(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        request.getSession().setAttribute(
                Globals.LOCALE_KEY, Locale.ENGLISH);
        myAction = new ActionForward("/Welcome.do");
        return myAction;
    }
}
