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
<script type="text/javascript" src="public/javascript/Qa_script.js"></script>
<table style="width: 100%">
    <tr>
        <td valign="top">
            <div class="box_search" style="text-align: left">
                <span style="color: red;">
                    <html:errors/>
                </span>
                Enter your question here:
                <br>
                <a href="#" onclick="document.QAForm.sentence.value=''">Clear field</a>
                <html:form action="/doQA" focus="keyWord" >
                    <html:textarea property="sentence" cols="120" rows="1"></html:textarea>
                    <html:image property="submitQA" srcKey="image.submit" altKey="image.submit.alttext" onclick="showLoadingPage();"/>
                </html:form>                                
                <br>
                <span style="font-style: italic">
                    Warning:<br>
                    - You should wrap your value such as title, author names in double-quote.<br>
                    - You should write name or heading titles in Upper<br>
                    - System only supports for English questions.
                </span>
            </div>                    
        </td>
    </tr>
    <tr>
        <td class="text5">
            <a href="#" onclick="toggleBox('Sample_Question_Box');">Some example questions:</a>
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
    <tr>
        <td>
            <div>
                <c:if test="${results != null}">
                    <c:forEach var="q" items="${results}" varStatus="st">
                        ${q}
                        <br>
                    </c:forEach>
                </c:if>
            </div>
        </td>
    </tr>
</table>
