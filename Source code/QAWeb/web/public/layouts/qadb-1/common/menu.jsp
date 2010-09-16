<%-- 
    Document   : menu
    Created on : Sep 2, 2010, 10:25:38 AM
    Author     : ThuanHung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>

<div class="bg3" id ="menu_bg3" >
    <ul>
        <li><a href="Welcome.do" class="link1"><bean:message  key="text.home"/></a></li>
        <li><a href="searchPage.do" class="link1"><bean:message  key="text.search"/></a></li>
        <li><a href="loadParams.do" class="link1"><bean:message  key="text.advanceSearch"/></a></li>
        <li><a href="#" class="link1"><bean:message  key="text.contactUs"/></a></li>
    </ul>
</div>
