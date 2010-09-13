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
        <tiles:insert attribute="header"/>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <tiles:insert attribute="menu"/>
            </tr>

            <tr>
                <tiles:insert attribute="content"/>
            </tr>

        </table>

        <div style="text-align: center; font-size: 0.75em;margin-bottom: 1px;">
            <tiles:insert attribute="footer"/>
        </div>
        <!--END LAYOUT CONTENTS-->
    </body>
</html:html>
