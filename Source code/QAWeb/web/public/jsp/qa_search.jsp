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
                Input your question here:
                <br>
                <textarea cols="120" style="height: 20px;"></textarea>
                <input type="submit" value="Submit">
            </div>                    
        </td>
    </tr>
</table>
