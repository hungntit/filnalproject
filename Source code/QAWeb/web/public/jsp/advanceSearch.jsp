<%-- 
    Document   : advanceSearch
    Created on : Sep 9, 2010, 3:32:36 PM
    Author     : ThuanHung
--%>

<%@page import="java.util.List"%>
<%@page import="uit.qass.dbconfig.DBInfoUtil"%>
<%@page import="uit.qass.dbconfig.TableInfo"%>
<%@page import="uit.qass.util.StringPool"%>
<%@page import="uit.qass.dbconfig.Type"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://struts.apache.org/tags-html"  prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<script type="text/javascript" src="public/javascript/Ajax.js"></script>
<%
List<TableInfo> tables  =   DBInfoUtil.getDBInfo().getTables();
request.setAttribute("tables", tables);
%>

    	<html:form action="loadParams.do">
            <div id ="selectTable"
		<select onchange="retrieveURL(' loadParams.do?tbl=' + this.value);">
                    <c:forEach items="${tables}" var="tbl">

                        <logic:equal value="${tbl.aliasName}" name="AdvanceSearchForm" property="tableInfo.aliasName">
                            <option value="${tbl.aliasName}"  selected="true">
                                <bean:message key="text.dblp.${fn:toLowerCase(tbl.aliasName)}" />
                            </option>
                        </logic:equal>

                        <logic:notEqual value="${tbl.aliasName}" name="AdvanceSearchForm" property="tableInfo.aliasName">
                            <option value="${tbl.aliasName}"  >
                                <bean:message key="text.dblp.${fn:toLowerCase(tbl.aliasName)}" />
                            </option>
                        </logic:notEqual>

                    </c:forEach>
		</select>
        </div>
		<br>
		
	</html:form>
    <div>
        <div>
            <span style="color: red;font-weight: bold">
                <html:errors/>
            </span>
            <div id ="advanceSearchForm" >
            <html:form action="/advanceSearch"  >
                <table>
                    <bean:size id="paramsSize" name="AdvanceSearchForm" property="params" />
                    <logic:greaterThan value="1" name="paramsSize"  >
                    <tr style="margin: 5px;">
                        <td>
                        <bean:message key="text.operator" />
                        </td>
                        
                        <td>

                            <html:select property="isAndOperator" name="AdvanceSearchForm">
                                <html:option value="true">AND</html:option>
                                <html:option value="false">OR</html:option>
                            </html:select>

                        </td>
                        
                    </tr>
                    </logic:greaterThan>
                    <logic:iterate id="param" name="AdvanceSearchForm" property="params" indexId="id">

                        <tr style="margin: 5px;">
                            <td>
                                <bean:define name="param" property="column.aliasName" id="column_aliasName" />
                                <bean:message  key="text.dblp.${fn:toLowerCase(column_aliasName)}"  />
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
                                        <html:text name="AdvanceSearchForm" property="param[${id}].value" />
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

