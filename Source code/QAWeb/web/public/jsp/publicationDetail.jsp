<%-- 
    Document   : publicationDetail
    Created on : Sep 7, 2010, 10:01:48 AM
    Author     : aa
--%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="l" %>

<td class="bg4">
    <div>
        <h2><c:out value="${publication.title}" /></h2>
        <div style="background: transparent">
            <table>
                <tr>
                    <th></th>
                    <th><bean:message key="text.informations"/></th>
                </tr>
                <tr>
                    <td><bean:message key="text.authors"/></td>
                    <td>
                        <c:forEach var="au" items="${publication.authors}">
                            <a href="showPubsByAuthor.do?authorName=${au.author}">
                                <c:out value="${au.author}"/>
                            </a> ,
                        </c:forEach>
                    </td>
                </tr>
                <tr>
                    <td><bean:message key="text.source"/></td>
                    <td><c:out value="${publication.source}" default="NA"/></td>
                </tr>
                <tr>
                    <td><bean:message key="text.year"/></td>
                    <td><c:out value="${publication.year}" default="NA"/></td>
                </tr>
                <tr>
                    <td><bean:message key="text.type"/></td>
                    <td><c:out value="${publication.type}" default="NA"/></td>
                </tr>
                <tr>
                    <td><bean:message key="text.volume"/></td>
                    <td><c:out value="${publication.volume}" default="NA"/></td>
                </tr>
                <tr>
                    <td><bean:message key="text.pages"/></td>
                    <td><c:out value="${publication.pages}" default="NA"/></td>
                </tr>
                <tr>
                    <td><bean:message key="text.ee"/></td>
                    <td>
                        <a href="${publication.ee}"><c:out value="${publication.ee}" default="NA"/></a>
                    </td>
                </tr>
                <tr>
                    <td><bean:message key="text.ee_PDF"/></td>
                    <td>
                        <a href="${publication.ee_PDF}"><c:out value="${publication.ee_PDF}" default="NA"/></a>
                    </td>
                </tr>
            </table>
            <% int i =1; %>
            <h3><bean:message key="text.references"/></h3><br>
            <c:out value="${warning}"/><br>
            <table>
            <c:forEach var="ref" items="${publication.refPubs}">
                <tr>
                    <td>[<%=i%>]</td>
                    <td><a href="showPubDetail.do?id=${ref.id}"><c:out value="${ref.title}"/></a></td>
                    <td><c:out value="${ref.year}"/></td>
                    <%i++;%>
                </tr>
            </c:forEach>
            </table>
        </div>
    </div>
</td>
