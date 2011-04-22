<%-- 
    Document   : contact
    Created on : Jan 6, 2011, 8:53:03 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>

<div>
    <table border="1">
        <tr>
            <td><img src="<bean:message key="image.author1"/>" style="padding: 10px;" alt="" width="150" height="200"/></td>
            <td align="center" style="padding: 10px;"><bean:message key="author1.info"/></td>
        </tr>
    </table>
</div>

<div>
    <table border="1">
        <tr>
            <td><img src="<bean:message key="image.author2"/>" style="padding: 10px;" alt="" width="150" height="200"/></td>
            <td align="center" style="padding: 10px;"><bean:message key="author2.info"/></td>
        </tr>
    </table>
</div>
