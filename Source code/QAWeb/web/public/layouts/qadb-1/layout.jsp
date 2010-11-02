<%-- 
    Document   : layout
    Created on : Sep 2, 2010, 9:48:27 AM
    Author     : ThuanHung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://struts.apache.org/tags-html"  prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-tiles"  prefix="tiles"%>
<html:html xhtml="true">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="keywords" content="<tiles:getAsString name="keywords"/>" >
        <meta name="description" content="<tiles:getAsString name="description"/>" />
        <title><tiles:getAsString name="title"/></title>
        <link href="<html:rewrite page='/public/layouts/qadb-1/css/default.css'/>" rel="stylesheet" type="text/css" />
    </head>
    <body class="bg1">
        <!--LAYOUT CONTENTS-->
        <div class="wrapper">
            <tiles:insert attribute="header"/>
            <tiles:insert attribute="menu"/>
            <div class="content">
                <tiles:insert attribute="content"/>
            </div>
        </div>
        <div class ="footer">
            <tiles:insert attribute="footer"/>
        </div>
        <!--END LAYOUT CONTENTS-->
    </body>
</html:html>
