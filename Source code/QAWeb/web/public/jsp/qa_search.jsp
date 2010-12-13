<%-- 
    Document   : qa_search
    Created on : Nov 4, 2010, 12:58:35 AM
    Author     : hoang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<table style="width: 100%">
    <tr>
        <td valign="top">
            <div class="box_search" style="text-align: left">
                <span style="color: red;">
                    <html:errors/>
                </span>
                Enter your question here:
                <br>
                <html:form action="/doQA" focus="keyWord" >
                    <html:textarea property="sentence" cols="120" rows="1"></html:textarea>
                    <html:image property="submitQA" srcKey="image.submit" altKey="image.submit.alttext" onclick="showLoadingPage();"/>
                </html:form>
                <br>
                <span style="font-style: italic">
                    Warning:<br>
                    - You should wrap your value such as title, author names in double-quote.<br>
                    - You should write name or heading titles in Upper case
                </span>
            </div>                    
        </td>
    </tr>
</table>
