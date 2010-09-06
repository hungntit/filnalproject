<%-- 
    Document   : PubsOfAuthor
    Created on : Sep 6, 2010, 8:27:56 PM
    Author     : Hoang-PC
--%>
<%@taglib uri="http://struts.apache.org/tags-html"  prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="l"%>

<td class="bg4">
    <div>
        <c:out value="${authorName}"/>
        <c:forEach var="pub" items="${publications}">
                <li><html:link href="#"><c:out value="${pub.title}"/></html:link></li>
        </c:forEach>
    </div>
</td>
