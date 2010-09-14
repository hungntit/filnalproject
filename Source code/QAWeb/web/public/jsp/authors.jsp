<%--
    Document   : search
    Created on : Sep 3, 2010, 9:21:15 PM
    Author     : Hoang-PC
--%>
<%@page import="uit.qass.model.Author"%>
<%@page import="uit.qass.dblp.AuthorUtil"%>
<%@page import="java.util.List"%>
<%@taglib uri="http://struts.apache.org/tags-html"  prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="l"%>
<%@taglib uri="/WEB-INF/tlds/pager-taglib.tld" prefix="pg" %>
<%
List<String> authors    =   AuthorUtil.getListAuthorFromListObj((List<Author>) request.getAttribute("objs"));
request.setAttribute("authors", authors);
%>
<td class="bg4">
    <div>
       
        <!--Show all authors which you search-->
        <div style="text-align: left;background-color: transparent;">
            <span style="color: green"><c:out value="${warning}"/></span>
            <pg:pager url="advanceSearch.do" maxPageItems="10" items="${totalRowsCount}" isOffset="true" maxIndexPages="10">
            <ul>
            <c:forEach var="au" items="${authors}">
                <pg:item>
                <li style="display: inline-block;width: 200px;">
                    <html:link href="showPubsByAuthor.do?authorName=${au}">
                        <c:out value="${au}"/>
                    </html:link>
                </li>
                </pg:item>
            </c:forEach>
            </ul>
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
    </div
</td>
