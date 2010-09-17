
<%@page import="java.util.List"%>
<%@page import="uit.qass.dbconfig.DBInfoUtil"%>
<%@page import="uit.qass.dbconfig.TableInfo"%>
<%@page import="uit.qass.util.StringPool"%>
<%@page import="uit.qass.dbconfig.Type"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib uri="http://struts.apache.org/tags-html"  prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
                            <td><bean:write name="param" property="column.aliasName" /></td>
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
                                        <html:option value="true"><bean:message key="text.true"/></html:option>
                                        <html:option value="false"><bean:message key="text.false"/></html:option>
                                    </html:select>
                                </logic:equal>
                                <logic:notEqual value="true"  property="column.type.isBoolean" name="param">
                                    <logic:notEmpty property="column.defaultValuesSet" name="param">
                                        <html:select property="param[${id}].value" name="AdvanceSearchForm">
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
                        <td><html:image property="submit" srcKey="image.submit" altKey="image.submit.alttext"/></td>
                    </tr>
                </table>

            </html:form>