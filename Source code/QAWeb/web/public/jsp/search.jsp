<%-- 
    Document   : search
    Created on : Sep 3, 2010, 9:21:15 PM
    Author     : Hoang-PC
--%>
<%@taglib uri="http://struts.apache.org/tags-html"  prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="l"%>

<td class="bg4">
    <div>        
        <div>
            <span style="color: red;font-weight: bold">
                <html:errors/>
            </span>
            <html:form action="/search" focus="keyWord" >
                <table>
                    <tr style="margin: 5px;">
                        <td><bean:message key="text.search"/></td>
                        <td align="left">
                            <html:select property="type" >
                                <html:option value="All" />
                                <html:option value="Author" />
                            </html:select>
                        </td>
                    </tr>
                    <tr style="margin: 5px;">
                        <td><bean:message key="text.input"/></td>
                        <td><html:text property="keyWord" size="60"/></td>
                        <td><html:image property="submit" srcKey="image.submit" altKey="image.submit.alttext"/></td>
                    </tr>
                </table>
            </html:form>
        </div>
        <!--Show all authors which you search-->
        <div style="text-align: left;background-color: transparent">
            <span style="color: green"><c:out value="${warning}"/></span>
            <c:forEach var="au" items="${authors}">
                <li><html:link href="showPubsByAuthor.do?authorName=${au}"><c:out value="${au}"/></html:link></li>
            </c:forEach>
        </div>
    </div
</td>
