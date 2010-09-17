<%-- 
    Document   : PubsOfAuthor
    Created on : Sep 6, 2010, 8:27:56 PM
    Author     : Hoang-PC
--%>
<%@page import="uit.qass.dblp.PublicationUtil"%>
<%@page import="uit.qass.model.Publication"%>
<%@page import="java.util.List"%>
<%@taglib uri="http://struts.apache.org/tags-html"  prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="l"%>
<%@taglib uri="/WEB-INF/tlds/pager-taglib.tld" prefix="pg" %>

<script type="text/javascript" language="javascript">
    function contextMenuClick(event,id) {
        ev = window.event || event;
        var objContextMenu = document.getElementById("contextmenu");

        objContextMenu.style.left = (ev.pageX + 4) + "px";
        objContextMenu.style.top = (ev.pageY + 4) + "px";
        objContextMenu.style.visibility = "visible";
        objContextMenu.innerHTML = document.getElementById(id).innerHTML;
    }
</script>
<%
List<Publication> objs  =(List<Publication>)   request.getAttribute("objs");
List<List<Publication>> publicationGrouped  =   PublicationUtil.groupByYear(objs);
request.setAttribute("publicationGrouped", publicationGrouped);
%>


    <div id="contextmenu">publication informations
    </div>                
    <div>
        
       <% int i = 1;%>
       <pg:pager url="advanceSearch.do" maxPageItems="10" items="${totalRowsCount}" isOffset="true" maxIndexPages="10">
        <div style="text-align: left;background-color: whitesmoke;">
            <c:forEach var="p" items="${publicationGrouped}">
                <table border="1" style="width: 650px;">
                    <c:forEach var="pub" items="${p}" varStatus="status">
                        <pg:item>
                        <span id="pub<%=i%>" style="visibility: hidden;position: fixed">
                            <h4><bean:message key="text.authors"/></h4>
                            <c:forEach var="au" items="${pub.authors}">
                                <c:out value="${au.author}"/>,
                            </c:forEach>
                            <li>
                                <bean:message key="text.source"/>:
                                <c:out value="${pub.source}" default="NA"/>
                            </li>
                            <li>
                                <bean:message key="text.series"/>:
                                <c:out value="${pub.series}" default="NA"/>
                            </li>
                            <li>
                                <bean:message key="text.type"/>:
                                <c:out value="${pub.type}" default="NA"/>
                            </li>
                            <li>
                                <bean:message key="text.volume"/>:
                                <c:out value="${pub.volume}" default="NA"/>
                            </li>
                            <li>
                                <bean:message key="text.pages"/>:
                                <c:out value="${pub.pages}" default="NA"/>
                            </li>
                            <li>
                                <bean:message key="text.ee"/>:
                                <c:out value="${pub.ee}" default="NA"/>
                            </li>
                            <li>
                                <bean:message key="text.ee_PDF"/>:
                                <c:out value="${pub.ee_PDF}" default="NA"/>
                            </li>
                            <li>
                                <bean:message key="text.url"/>:
                                <c:out value="${pub.url}" default="NA"/>
                            </li>
                        </span>
                        <c:if test="${status.count == 1}">
                            <tr style="text-align: center">
                                <th><c:out value="${pub.year}"/></th>
                            </tr>
                        </c:if>
                        <tr>
                            <td>
                                <a href="./showPubDetail.do?id=${pub.id}" style="text-decoration: none;"
                                   onmouseover="contextMenuClick(event,'pub<%=i%>');"
                                   onmouseout="document.getElementById('contextmenu').style.visibility = 'hidden'">
                                    <%=i%>. <c:out value="${pub.title}"/>
                                </a>
                            </td>
                        </tr>
                        </pg:item>
                        <%i++;%>
                    </c:forEach>
                    <br>
                </table>
            </c:forEach>
        </div>
        <pg:index>

   

            <pg:prev>
              <a href="<%= pageUrl %>">[  &lt;&lt; Previous ]</a>
            </pg:prev>

            <pg:pages>
               <a href="<%= pageUrl %>"><%= pageNumber %></a>
            </pg:pages>

            <pg:next>
              <a href="<%= pageUrl %>">[ Next &gt;&gt; ]</a>
            </pg:next>


          </pg:index>
    </pg:pager>
    </div>