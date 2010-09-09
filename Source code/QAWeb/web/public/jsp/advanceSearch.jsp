<%-- 
    Document   : advanceSearch
    Created on : Sep 9, 2010, 3:32:36 PM
    Author     : ThuanHung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib uri="http://struts.apache.org/tags-html"  prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<td class="bg4">
    <div>
        <div>
            <span style="color: red;font-weight: bold">
                <html:errors/>
            </span>
            <html:form action="/advanceSearch" focus="keyWord" >
                <table>
                    <logic:iterate id="param" name="AdvanceSearchForm" property="params">

                        <tr style="margin: 5px;">
                            <td><bean:write name="param" property="column.aliasName" /></td>
                            <td><html:text name="param" property="value"/></td>

                        </tr>
                    </logic:iterate>
                    <tr>
                        <td><html:image property="submit" srcKey="image.submit" altKey="image.submit.alttext"/></td>
                    </tr>
                </table>
                
            </html:form>
        </div>
        <!--Show all authors which you search-->
        <div style="text-align: left;background-color: transparent;">
            <span style="color: green"><c:out value="${warning}"/></span>
            <ul>
            <c:forEach var="au" items="${authors}">
                <li style="display: inline-block;width: 200px;">
                    <html:link href="showPubsByAuthor.do?authorName=${au}">
                        <c:out value="${au}"/>
                    </html:link>
                </li>
            </c:forEach>
            </ul>
        </div>
    </div
</td>
