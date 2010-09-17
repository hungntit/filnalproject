<%-- 
    Document   : content
    Created on : Sep 2, 2010, 10:25:29 AM
    Author     : ThuanHung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<table width="700" border="0" align="center" cellpadding="0" cellspacing="0" style="padding-top: 10px;">
    <tr valign="top">
        <td width="415">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td class="text4">
                        <bean:message key="text.welcome"/>
                        <br>
                        <bean:message key="text.qass.full"/>
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td>
                        <bean:message key="text.qass.des"/>
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                </tr>
            </table>
        </td>
        <td width="35">&nbsp;</td>
        <td width="250">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td><img src="images/spacer.gif" alt="" width="100" height="35" /></td>
                </tr>
                <tr>
                    <td class="text4"><bean:message key="text.UIT"/></td>
                </tr>
            </table>
        </td>
    </tr>
</table>