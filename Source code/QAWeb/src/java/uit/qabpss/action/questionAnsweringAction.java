/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.qabpss.action;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import uit.qabpss.core.search.UtimateSearch;
import uit.qabpss.dbconfig.Param;
import uit.qabpss.dblp.HtmlUtil;
import uit.qabpss.dblp.ObjectsAndToken;
import uit.qabpss.extracttriple.TripleToken;
import uit.qabpss.formbean.QAForm;
import uit.qabpss.model.Author;
import uit.qabpss.model.Publication;
import uit.qabpss.preprocess.EntityType;
import uit.qabpss.preprocess.Token;
import uit.qabpss.processanswer.ProcessAnswer;
import uit.qabpss.processanswer.ResultAnswer;
import uit.qabpss.util.ClassUtil;
import uit.qabpss.util.ListUtil;
import uit.qabpss.util.exception.QuestionNotSolveException;

/**
 *
 * @author Admin
 */
public class questionAnsweringAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    private static final String FAIL = "success";
    private static final String NOTFOUND    = "success";
    ProcessAnswer   processAnswer       =   null;
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
        QAForm f = (QAForm) form;
        List results =  null;
         ResultAnswer resultAnswer    =   f.getResultAnswer();
        String replaceStr = request.getParameter("replaceStr");
        String originStr  = request.getParameter("originStr");
        if(processAnswer    ==  null)
            processAnswer   =   new ProcessAnswer();
        
        try{
          if(replaceStr != null)
        {
             resultAnswer = processAnswer.answerQuestion(resultAnswer, originStr, replaceStr);
             f.setSentence(resultAnswer.getQuestion());

        }
        else
            resultAnswer = processAnswer.answerQuestion(f.getSentence());
         results    =   resultAnswer.getRetrieveData();
         results    =   toHtmlsList(results);
                  if(resultAnswer.getQuestionType().isWPQuestion())
             request.setAttribute("isWPQuestion", true);
         else
         {
             request.setAttribute("isWPQuestion", false);
         }
         if(resultAnswer.getQuestionType().isYesNoQuestion())
             request.setAttribute("isYNQuestion", true);
         else
         {
             request.setAttribute("isYNQuestion", false);
         }
         if(resultAnswer.getQuestionType().isHowManyQuestion())
             request.setAttribute("isHowManyQuestion", true);
         else
         {
             request.setAttribute("isHowManyQuestion", false);
         }
         
        resultAnswer.setRetrieveData(null);
        }
        catch(QuestionNotSolveException qnse)
        {
            qnse.printStackTrace();
            request.setAttribute("fail", true);
            return mapping.findForward(FAIL);
        }
        if(results !=null){
            if(results.isEmpty())
            {
                List<ObjectsAndToken> replacedObjects = getObjectsAndReplacedValueList(resultAnswer.getGroupTripleTokens());
                if(replacedObjects.isEmpty())
                {
                    request.setAttribute("notfound", true);
                    return mapping.findForward(NOTFOUND);
                }
                f.setResultAnswer(resultAnswer);
                request.setAttribute("replacedObjects", replacedObjects);
                results =null;
                request.setAttribute("results", results);
                return mapping.findForward(SUCCESS);
            }
            else
            {
                request.setAttribute("results", results);
                return mapping.findForward(SUCCESS);
            }
        }
        else
                return mapping.findForward(SUCCESS);

    }

    private static List searchFromToken(Token token,Class classtype)
    {
        if(token.getEntityType() == null)
            return new ArrayList();
        EntityType  mainEntityType  =   token.getEntityType().getMainEntityType();

        if(mainEntityType.isTable())
        {
           return UtimateSearch.searchByKeyWords(classtype, token.getValue(), mainEntityType.getTableInfo(), 0, 100);
        }
        else
        {
            Param   param   =   new Param(mainEntityType.getTableInfo(), mainEntityType.getColumnInfo());
            param.setValue(token.getValue());
            return UtimateSearch.searchByParam(classtype, new Param[]{param}, true, null, 0, 100);

        }
    }
    public static List<ObjectsAndToken> getObjectsAndReplacedValueList(List<List<TripleToken>> groupTripleTokens)
    {
            List<ObjectsAndToken>   list    =   new ArrayList<ObjectsAndToken>();
            for(List<TripleToken> tripleTokens:groupTripleTokens)
            {
                for(TripleToken tripleToken:tripleTokens)
                {
                    Token token1    =   tripleToken.getObj1();
                    Token token2    =   tripleToken.getObj2();
                    if(!list.contains(token1) && token1.isNe())
                    {
                        EntityType mainEntityType = token1.getEntityType().getMainEntityType();
                        Class clss  =   null;
                        if(mainEntityType.isTable())
                        {

                            if(mainEntityType.getTableInfo().getAliasName().equalsIgnoreCase("Author"))
                                clss    =   Author.class;
                            else
                            {
                                if(mainEntityType.getTableInfo().getAliasName().equalsIgnoreCase("Publication"))
                                    clss    =   Publication.class;
                                else
                                    if(mainEntityType.getTableInfo().getAliasName().equalsIgnoreCase("Reference")
                                        ||mainEntityType.getTableInfo().getAliasName().equalsIgnoreCase("Citation"))
                                        clss    =   Publication.class;
                            }

                        }
                        else
                        {
                            clss    =   ClassUtil.changeTypeToClass(mainEntityType.getColumnInfo().getType());
                        }
                        if(clss == int.class || clss == long.class)
                            continue;
                        List searchFromToken = searchFromToken(token1, clss);
                        if(!ListUtil.containAndEquals(searchFromToken, token1.getValue()))
                        {
                            searchFromToken =   ListUtil.distinctList(searchFromToken);
                            ObjectsAndToken objectsAndToken =   new ObjectsAndToken(token1, searchFromToken);
                            list.add(objectsAndToken);
                        }

                    }
                    if(!list.contains(token2) && token2.isNe())
                    {
                        EntityType mainEntityType = token2.getEntityType().getMainEntityType();
                        Class clss  =   null;
                        if(mainEntityType.isTable())
                        {

                            if(mainEntityType.getTableInfo().getAliasName().equalsIgnoreCase("Author"))
                                clss    =   Author.class;
                            else
                            {
                                if(mainEntityType.getTableInfo().getAliasName().equalsIgnoreCase("Publication"))
                                    clss    =   Publication.class;
                                else
                                    if(mainEntityType.getTableInfo().getAliasName().equalsIgnoreCase("Reference")
                                     ||mainEntityType.getTableInfo().getAliasName().equalsIgnoreCase("Citation"))
                                        clss    =   Publication.class;
                            }

                        }
                        else
                        {
                            clss    =   ClassUtil.changeTypeToClass(mainEntityType.getColumnInfo().getType());
                        }
                        if(clss == int.class || clss == long.class)
                            continue;
                        List searchFromToken = searchFromToken(token2, clss);
                        if(!ListUtil.containAndEquals(searchFromToken, token2.getValue()))
                        {
                             searchFromToken =   ListUtil.distinctList(searchFromToken);
                            ObjectsAndToken objectsAndToken =   new ObjectsAndToken(token2, searchFromToken);
                            list.add(objectsAndToken);

                        }
                    }
                }
            }
            return list;
    }
    private static List<String> toHtmlsList(List list)
    {
        List<String>    resutls     =   new ArrayList<String>();
        for(Object obj: list)
        {
            String htmlStr  =   HtmlUtil.toHtml(obj);
            if(!resutls.contains(htmlStr))
                resutls.add(htmlStr);
        }
        return resutls;
    }

}
