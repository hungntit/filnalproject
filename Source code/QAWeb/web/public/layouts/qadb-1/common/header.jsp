<%-- 
    Document   : header
    Created on : Sep 2, 2010, 10:20:54 AM
    Author     : ThuanHung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="/WEB-INF/tag-lib/pager-taglib.tld" prefix="pager" %>

<div id="header">
    <div><img src="<bean:message key="image.logo"/>" alt="<bean:message key="image.logo.alttext"/>" style="width: 100px; border-top-width: 1px; margin-left: 110px; margin-bottom: -106px;"/></div>
    <div style="margin-left: 20%;margin-top: 30px;width:  200px;">
        <span class="text1"><bean:message key="text.dbqa"/></span><hr>
        <span class="text3"><bean:message key="text.UIT"/></span>
    </div>
    <div style="margin-right: 3%;text-align: right;color: white;margin-top:-5px;">
        <bean:message key="text.language"/>
        <a href="#">
            <img src="<bean:message key="image.en"/>" alt="<bean:message key="image.en.alttext"/>"/>
        </a>
        <a href="#">
            <img src="<bean:message key="image.vn"/>" alt="<bean:message key="image.vn.alttext"/>"/>
        </a>
    </div>
</div>