<%-- 
    Document   : advanceSearch
    Created on : Sep 9, 2010, 3:32:36 PM
    Author     : ThuanHung
--%>

<%@page import="uit.qabpss.dblp.PublicationUtil"%>
<%@page import="uit.qabpss.model.Publication"%>
<%@page import="java.util.List"%>
<%@page import="uit.qabpss.dbconfig.DBInfoUtil"%>
<%@page import="uit.qabpss.dbconfig.TableInfo"%>
<%@page import="uit.qabpss.util.StringPool"%>
<%@page import="uit.qabpss.dbconfig.Type"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://struts.apache.org/tags-html"  prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="/WEB-INF/tlds/pager-taglib.tld" prefix="pg" %>
<script type="text/javascript" src="public/javascript/Ajax.js"></script>
<div>
        <div class="box_search">
            <span style="color: red;font-weight: bold">
                <html:errors/>
            </span>
            <div id ="advanceSearchForm" >
            <html:form action="/advanceSearch"  >
                <table>
                    <bean:size id="paramsSize" name="AdvanceSearchForm" property="params" />
                    <logic:iterate id="param" name="AdvanceSearchForm" property="params" indexId="id">

                        <tr style="margin: 5px;">
                            <td>
                                <bean:define name="param" property="column.aliasName" id="column_aliasName" />
                                <%
                               String aliasName = (String)org.apache.struts.taglib.tiles.util.TagUtils.findAttribute("column_aliasName", pageContext);
                               aliasName =   aliasName.toLowerCase();

                                %>
                                <bean:define id="alias" value='<%=aliasName%>'/>
                                <bean:message  key="text.dblp.${alias}"  />
                            </td>

                            <td>
                                <logic:equal value="true"  property="column.type.isNumber" name="param">

                                    <html:select property="param[${id}].operator" name="AdvanceSearchForm">
                                        <html:option value="<%=StringPool.EQUAL %>"><bean:message key="operator.equal"/></html:option>
                                        <html:option value="<%=StringPool.GREATER_THAN_OR_EQUAL %>"><bean:message key="operator.greater_than_or_equal"/></html:option>
                                        <html:option value="<%=StringPool.LESS_THAN_OR_EQUAL %>"><bean:message key="operator.less_than_or_equal"/></html:option>
                                        <html:option value="<%=StringPool.GREATER_THAN %>"><bean:message key="operator.greater_than"/></html:option>
                                        <html:option value="<%=StringPool.LESS_THAN %>"><bean:message key="operator.less_than"/></html:option>
                                    </html:select>

                                </logic:equal>


                                <logic:equal value="true"  property="column.type.isBoolean" name="param">
                                    <html:select property="param[${id}].value" name="AdvanceSearchForm">
                                        <html:option value="1"><bean:message key="text.true"/></html:option>
                                        <html:option value="0"><bean:message key="text.false"/></html:option>
                                    </html:select>
                                </logic:equal>
                                <logic:notEqual value="true"  property="column.type.isBoolean" name="param">
                                    <logic:notEmpty property="column.defaultValuesSet" name="param">
                                        <html:select property="param[${id}].value" name="AdvanceSearchForm" >
                                            <html:option value=""><c:out value=""/></html:option>
                                            <logic:iterate id="value_s" property="column.defaultValuesSet" name="param">
                                                <html:option value="${value_s}"><c:out value="${value_s}"/></html:option>
                                            </logic:iterate>

                                        </html:select>

                                    </logic:notEmpty>
                                    <logic:empty property="column.defaultValuesSet" name="param">
                                        <html:text name="AdvanceSearchForm" property="param[${id}].value" size="50"/>
                                    </logic:empty>
                                </logic:notEqual>
                            </td>
                        </tr>
                    </logic:iterate>
                    <tr>
                        <td><html:image property="submit" srcKey="image.submit" altKey="image.submit.alttext" onclick="showLoadingPage();"/></td>
                    </tr>
                </table>
                
            </html:form>
            </div>
        </div>
            <c:if test="${warning != null}">
                <span style="color: green;">
                    <bean:message key="${warning}"/>
                </span>
            </c:if>
<%--        Show all authors which you search
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
        </div>            --%>
</div>

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


    
    <div>

        <c:if test="${totalRowsCount > 0}">
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
     </c:if>
    </div>