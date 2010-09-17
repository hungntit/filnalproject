<%-- 
    Document   : search
    Created on : Sep 3, 2010, 9:21:15 PM
    Author     : Hoang-PC
--%>
<%@taglib uri="http://struts.apache.org/tags-html"  prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="l"%>
<%@taglib uri="/WEB-INF/tlds/pager-taglib.tld" prefix="pg"%>

<div>
    <div>
        <span style="color: red;">
            <html:errors/>
        </span>
        <html:form action="/search" focus="keyWord" >
            <table >
                <tr style="margin: 5px;">
                    <td><bean:message key="text.search"/></td>
                    <td align="left">
                        <html:select property="type" >
                            <html:option value="All" ><bean:message key="text.all"/></html:option>
                            <html:option value="Author"><bean:message key="text.author"/></html:option>
                        </html:select>
                    </td>
                </tr>
                <tr style="margin: 5px;">
                    <td><bean:message key="text.input"/></td>
                    <td><html:text property="keyWord" size="60"/></td>
                    <td><html:image property="submit" srcKey="image.submit" altKey="image.submit.alttext" onclick="showLoadingPage();"/></td>
                </tr>
            </table>
        </html:form>
    </div>
    <!--Show all authors which you search-->
    <c:if test="${warning != null}">
        <span style="color: green;">
            <bean:message key="${warning}"/>
        </span>
    </c:if>
    <div id="box2">
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
    <div id="box2" style="margin-top: -60px;">
        <pg:pager url="publicationList.do" maxIndexPages="10" maxPageItems="5">
            <TABLE id ="header_table" width="80%" border="0"></TABLE>
            <c:forEach var="pub" items="${publications}" varStatus="i">
                <pg:item>
                    <div id="box3">
                        <a href="./showPubDetail.do?id=${pub.id}" style="font-size: 20px;" >
                            <c:out value="${i.count}"/>.
                            <c:out value="${pub.title}"/>
                        </a> -
                        <c:forEach var="au" items="${pub.authors}">
                            <c:out value="${au.author}" default="N/A"/>,
                        </c:forEach>
                        <hr>
                        <table border="0.5" bgcolor="whitesmoke" style="width: 100%;">
                            <tr>
                                <td class="left_col"><bean:message key="text.source"/></td>
                                <td><c:out value="${pub.source}" default="N/A"/></td>
                            </tr>
                            <tr>
                                <td class="left_col"><bean:message key="text.year"/></td>
                                <td><c:out value="${pub.year}" default="N/A"/></td>
                            </tr>
                            <tr>
                                <td class="left_col"><bean:message key="text.publisher"/></td>
                                <td><c:out value="${pub.publisher}" default="N/A"/></td>
                            </tr>
                            <tr>
                                <td class="left_col"><bean:message key="text.type"/></td>
                                <td><c:out value="${pub.type}" default="N/A"/></td>
                            </tr>
                        </table>
                    </div>
                </pg:item>                
            </c:forEach>
            <TABLE id="footer_table"width="80%" border="0">
                <TR><TD>&nbsp;</TD></TR>
                <TR align="center">
                    <TD>
                        <pg:index>
                            <pg:first><a href="<%= pageUrl%>">[&lt;&lt;First]</a></pg:first>
                            <pg:prev><a href="<%= pageUrl%>">[&lt;&lt;Prev]</a></pg:prev>
                            <pg:pages><a href="<%= pageUrl %>"><%= pageNumber %></a> </pg:pages>
                            <pg:next><a href="<%= pageUrl%>">[Next&gt;&gt;]</a></pg:next>
                            <pg:last><a href="<%= pageUrl%>">[Last&gt;&gt;]</a></pg:last>
                        </pg:index>
                    </TD>
                </TR>
                <TR><TD>&nbsp;</TD></TR>
            </TABLE>
            <script type="text/javascript" language="javascript">
                document.getElementById("header_table").innerHTML = document.getElementById("footer_table").innerHTML;
            </script>
        </pg:pager>
    </div>
</div>
