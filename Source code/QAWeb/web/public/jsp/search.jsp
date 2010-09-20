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
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<table> 
    <tr>
        <td style="width: 25%;margin: 5px;">
            <div style="width: 75%;border: #2C2C2C solid;">
                Quick Search<hr>
                <ul>Find top 10 newest publications</ul>
                <ul>Find top 10 authors</ul>
            </div>
        </td>
        <td style="width: 75%;">
            <div class="box_search">
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
                        <tr>
                            <td>Max Items</td>
                            <td>
                                <html:select property="maxResult" >
                                    <html:option value="5" >5</html:option>
                                    <html:option value="10">10</html:option>
                                    <html:option value="15" >15</html:option>
                                    <html:option value="20" >20</html:option>
                                </html:select>
                            </td>
                        </tr>
                    </table>
                </html:form>
            </div>
        </td>
    </tr>
    <tr>
        <td></td>
        <td>
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
            <div id="box2" style="margin-top: -60px; margin-left: 0px;margin-right: 0px;">
                <display:table id="data" name="${publications}" requestURI="/search.do" pagesize="${pagesize}" >
                    <display:column value="${data_rowNum}" title="No."/>
                    <display:column value="<a href='./showPubDetail.do?id=${data.id}'>${data.title}</a>" title="Title" sortable="true"/>
                    <display:column value="${data.type}" title="Type" sortable="true"/>
                    <display:column value="${data.publisher}" title="Publisher" sortable="true"/>
                    <display:column value="${data.year}" title="Year" sortable="true"/>
                </display:table>                
            </div>
        </td>
    </tr>
</table>

