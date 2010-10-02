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
<table >
    <tr>
        <td style="width: 25%;margin: 5px;" rowspan="2" valign="top">
            <div style="width: 75%;border: #2C2C2C solid;">
                <h3>Quick Search</h3><hr>
                <ol>
                    <li><a href="./quicksearch.do?p=topPubs" onclick="showLoadingPage();"><bean:message key="text.top.newestPublications"/></a></li>
                    <li><a href="./quicksearch.do?p=topAus" onclick="showLoadingPage();"><bean:message key="text.top.topAuthors"/></a></li>
                    <li><a href="./quicksearch.do?p=book" onclick="showLoadingPage();"><bean:message key="text.top.book"/></a></li>
                    <li><a href="./quicksearch.do?p=www" onclick="showLoadingPage();"><bean:message key="text.top.www"/></a></li>
                    <li><a href="./quicksearch.do?p=article" onclick="showLoadingPage();"><bean:message key="text.top.article"/></a></li>
                    <li><a href="./quicksearch.do?p=incollection" onclick="showLoadingPage();"><bean:message key="text.top.incollection"/></a></li>
                    <li><a href="./quicksearch.do?p=inproceedings" onclick="showLoadingPage();"><bean:message key="text.top.inproceeding"/></a></li>
                    <li><a href="./quicksearch.do?p=mastersthesis" onclick="showLoadingPage();"><bean:message key="text.top.masterthesis"/></a></li>
                    <li><a href="./quicksearch.do?p=phdthesis" onclick="showLoadingPage();"><bean:message key="text.top.phdthesis"/></a></li>
                    <li><a href="./quicksearch.do?p=proceedings" onclick="showLoadingPage();"><bean:message key="text.top.proceeding"/></a></li>
                </ol>
            </div>
        </td>
        <td style="width: 75%;" valign="top">
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
            <br>
            <div id="box2" style="margin-top: -60px; margin-left: 0px;margin-right: 0px;">
                <c:if test="${publications != null}">
                    <display:table id="data" name="${publications}" requestURI="" pagesize="${pagesize}" >                       
                        <display:column title="Title" sortable="true">
                            <a href='./showPubDetail.do?id=${data.id}' style="font-size: 20px;line-height: 22px;">${data_rowNum}. ${data.title}</a><br>
                            <table  style="width: 100%;font-size: 8px;">
                                <tr>
                                    <td class="left_col"><bean:message key="text.authors"/></td>
                                    <td>
                                        <c:forEach var="au" items="${data.authors}">
                                            <a href="showPubsByAuthor.do?authorName=${au.author}">
                                                <c:out value="${au.author}"/>
                                            </a> ,
                                        </c:forEach>
                                    </td>
                                </tr>                                                             
                                <tr>
                                    <td class="left_col"><bean:message key="text.publisher"/></td>
                                    <td><c:out value="${data.publisher}" default="N/A"/></td>
                                </tr>
                            </table>
                        </display:column>
                        <display:column value="${data.year}" title="Year" sortable="true"/>
                    </display:table>
                </c:if>
            </div>
        </td>
    </tr>
</table>

