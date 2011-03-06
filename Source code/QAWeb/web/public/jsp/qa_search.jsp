<%-- 
    Document   : qa_search
    Created on : Nov 4, 2010, 12:58:35 AM
    Author     : hoang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<script type="text/javascript" src="public/javascript/Qa_script.js"></script>
<table style="width: 100%">
    <tr>
        <td valign="top">
            <div class="box_search" style="text-align: left">
                <span style="color: red;">
                    <html:errors/>
                </span>
                <span style="font-style: italic;color: white"><bean:message key="text.qainput"/></span>
                <br>
                <a href="#" onclick="document.QAForm.sentence.value=''"><bean:message key="text.qaclear"/></a>
                <html:form action="/doQA" focus="keyWord" >
                    <html:textarea property="sentence" cols="120" rows="1"></html:textarea>
                    <html:image property="submitQA" srcKey="image.submit" altKey="image.submit.alttext" onclick="showLoadingPage();"/>
                </html:form>                                
                <br>
                <span style="font-style: italic;color: white">
                    <bean:message key="text.qawarning"/>
                </span>
            </div>                    
        </td>
    </tr>
    <tr>
        <td class="text5">
            <a href="#" onclick="toggleBox('Sample_Question_Box');"><bean:message key="text.qasample"/></a>
            <br>
            <div id="Sample_Question_Box"style="text-align: left;display: none">
                <c:forEach var="q" items="${EXAMPLE_QUESTIONS}" varStatus="st">
                    <c:out value="${st.count}"/><a href="#" style="text-transform: none;font-size: 14px" onclick="document.QAForm.sentence.value='${q.label}'">
                        ${q.label}
                    </a>
                    <br>
                </c:forEach>
            </div>
        </td>
    </tr>
    
    <c:if test="${results != null}">
    <tr>
        <td>
            <div class="box_search" style="text-align: left;background-color: whitesmoke" >
                <c:if test="${isYNQuestion == true}">
                    <bean:message key="text.itstrue" /><br/>
                </c:if>
                <c:if test="${isYNQuestion == false}">
                    <c:if test="${isHowManyQuestion == true}">

                    </c:if>

                </c:if>
                <display:table id="data" name="${results}" requestURI="" pagesize="10" style="width:100%;">
                    <display:column>
                        ${data_rowNum}. ${data}
                    </display:column>
                </display:table>

                            
            </div>

        </td>
    </tr>
    </c:if>
    <c:if test="${notfound == true}">
         <tr>
              <td align="center">
                    <div class="box_search" style="text-align: left;background-color: whitesmoke" >
                    <c:if test="${isYNQuestion == true}">
                            <bean:message key="text.itsfalse" /><br>
                    </c:if>
                    <c:if test="${isYNQuestion == false}">
                        <c:if test="${isHowManyQuestion == true}">

                                    <bean:message key="text.total" />: 0 <br/>

                        </c:if>
                        <c:if test="${isHowManyQuestion == false}">

                               <bean:message key="text.noresult"/>

                        </c:if>

                    </c:if>
                    </div>
           </td>
    </tr>
    </c:if>
    <c:if test="${fail == true}">
        <tr>
            <td align="center"><bean:message key="text.fail"/></td>

        </tr>
    </c:if>

    <c:if test="${replacedObjects != null}">
    <tr>
        <td>
            <div class="box_search" style="text-align: left;background-color: whitesmoke" >

                    <c:forEach var="robj" items="${replacedObjects}" >
                            ${robj.token.value} does not exist.Please replace it by:
                            <br>
                            <c:forEach var="rvalue" items="${robj.list}" varStatus="st">
                                ${st.count}. <a href="doQA.do?replaceStr=${rvalue}&originStr=${robj.token.value}">${rvalue}</a>

                                <hr>
                            </c:forEach>
                        <hr>
                    </c:forEach>

            </div>
        </td>
    </tr>
    </c:if>
</table>
