<%-- 
    Document   : publicationDetail
    Created on : Sep 7, 2010, 10:01:48 AM
    Author     : aa
--%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="l" %>
<%@taglib  uri="http://ditchnet.org/jsp-tabs-taglib" prefix="tab" %>
<tab:tabConfig />

<td class="bg4">
    <div>
        <h2><c:out value="${publication.title}" /></h2>
        <%--<div style="background: transparent">
            <table  style="-moz-border-radius: 10px;-webkit-border-radius: 10px;background-color: whitesmoke;width: 100%;">
                <tr>
                    <th class="left_col"></th>
                    <th><bean:message key="text.informations"/></th>
                </tr>
                <tr>
                    <td class="left_col"><bean:message key="text.authors"/></td>
                    <td>
                        <c:forEach var="au" items="${publication.authors}">
                            <a href="showPubsByAuthor.do?authorName=${au.author}">
                                <c:out value="${au.author}"/>
                            </a> ,
                        </c:forEach>
                    </td>
                </tr>
                <tr>
                    <td class="left_col"><bean:message key="text.source"/></td>
                    <td><c:out value="${publication.source}" default="NA"/></td>
                </tr>
                <tr>
                    <td class="left_col"><bean:message key="text.year"/></td>
                    <td>
                        <c:out value="${publication.month}" default=""/>
                        <c:out value="${publication.year}" default="NA"/>
                    </td>
                </tr>
                <tr>
                    <td class="left_col"><bean:message key="text.type"/></td>
                    <td><c:out value="${publication.type}" default="NA"/></td>
                </tr>
                <tr>
                    <td class="left_col"><bean:message key="text.volume"/></td>
                    <td><c:out value="${publication.volume}" default="NA"/></td>
                </tr>
                <tr>
                    <td class="left_col"><bean:message key="text.pages"/></td>
                    <td><c:out value="${publication.pages}" default="NA"/></td>
                </tr>
                <tr>
                    <td class="left_col"><bean:message key="text.ee"/></td>
                    <td>
                        <a href="${publication.ee}"><c:out value="${publication.ee}" default="NA"/></a>
                    </td>
                </tr>
                <tr>
                    <td class="left_col"><bean:message key="text.ee_PDF"/></td>
                    <td>
                        <a href="${publication.ee_PDF}"><c:out value="${publication.ee_PDF}" default="NA"/></a>
                    </td>
                </tr>
                <tr>
                    <td class="left_col"><bean:message key="text.url"/></td>
                    <td>
                        <a href="<bean:message key="link.dblp"/>${publication.url}"><c:out value="${publication.url}" default="NA"/></a>
                    </td>
                </tr>
                <tr>
                    <td class="left_col"><bean:message key="text.doi"/></td>
                    <td>
                        <a href="<bean:message key="link.doi"/>${publication.doi}"><c:out value="${publication.doi}" default="NA"/></a>
                    </td>
                </tr>
                <tr>
                    <td class="left_col"><bean:message key="text.publisher"/></td>
                    <td><c:out value="${publication.publisher}" default="NA"/></td>
                </tr>
                <tr>
                    <td class="left_col">Try to another links </td>
                    <td>
                        <a target="_blank"  href="http://scholar.google.com/scholar?q=${publication.title.replace(" ","+")}"><img src="public/images/scholar.jpg" width="30" height="30"/></a>
                        <a target="_blank" href="http://citeseerx.ist.psu.edu/search?q=${publication.title.replace(" ","+")}"><img src="public/images/citexeer.jpg" width="30" height="30"/></a>
                    </td>
                </tr>
            </table>
        </div>
        <% int i =1; %>
--%>

</div>

<style type="text/css">
.ditch-tab-wrap{
    width: 100%;
    padding-bottom: 0px
}
.ditch-tab-pane-wrap{
    width: 100%;
    padding-top: 0px
}

.ditch-tab-skin-default{
    width: 70%;
    margin-left: 10px;
}
#pub-details-container {
    margin-left: 10px; 
}
</style>    
    <tab:tabContainer id="pub-details-container" >

        <tab:tabPane id="foo" tabTitle="Authors">
            <c:forEach var="au" items="${publication.authors}">
                <a href="showPubsByAuthor.do?authorName=${au.author}">
                    <c:out value="${au.author}"/>
                </a> ,
            </c:forEach>
        </tab:tabPane>

        <tab:tabPane id="foo" tabTitle="Info">
            <table  style="-moz-border-radius: 10px;-webkit-border-radius: 10px;background-color: whitesmoke;width: 100%;">
                <tr>
                    <td class="left_col"><bean:message key="text.source"/></td>
                    <td><c:out value="${publication.source}" default="NA"/></td>
                </tr>
                <tr>
                    <td class="left_col"><bean:message key="text.year"/></td>
                    <td>
                        <c:out value="${publication.month}" default=""/>
                        <c:out value="${publication.year}" default="NA"/>
                    </td>
                </tr>
                 <tr>
                    <td class="left_col"><bean:message key="text.type"/></td>
                    <td><c:out value="${publication.type}" default="NA"/></td>
                </tr>
                <tr>
                    <td class="left_col"><bean:message key="text.volume"/></td>
                    <td><c:out value="${publication.volume}" default="NA"/></td>
                </tr>
                <tr>
                    <td class="left_col"><bean:message key="text.pages"/></td>
                    <td><c:out value="${publication.pages}" default="NA"/></td>
                </tr>
                <tr>
                    <td class="left_col"><bean:message key="text.publisher"/></td>
                    <td><c:out value="${publication.publisher}" default="NA"/></td>
                </tr>
            </table>
        </tab:tabPane>

         <tab:tabPane id="foo" tabTitle="Links">
            <table  style="-moz-border-radius: 10px;-webkit-border-radius: 10px;background-color: whitesmoke;width: 100%;">                                
                <tr>
                    <td class="left_col"><bean:message key="text.ee"/></td>
                    <td>
                        <a href="${publication.ee}"><c:out value="${publication.ee}" default="NA"/></a>
                    </td>
                </tr>
                <tr>
                    <td class="left_col"><bean:message key="text.ee_PDF"/></td>
                    <td>
                        <a href="${publication.ee_PDF}"><c:out value="${publication.ee_PDF}" default="NA"/></a>
                    </td>
                </tr>
                <tr>
                    <td class="left_col"><bean:message key="text.url"/></td>
                    <td>
                        <a href="<bean:message key="link.dblp"/>${publication.url}"><c:out value="${publication.url}" default="NA"/></a>
                    </td>
                </tr>
                <tr>
                    <td class="left_col"><bean:message key="text.doi"/></td>
                    <td>
                        <a href="<bean:message key="link.doi"/>${publication.doi}"><c:out value="${publication.doi}" default="NA"/></a>
                    </td>
                </tr>
                <tr>
                    <td class="left_col">Try to another links </td>
                    <td>
                        <a target="_blank"  href="http://scholar.google.com/scholar?q=${publication.title.replace(" ","+")}"><img src="public/images/scholar.jpg" width="30" height="30"/></a>
                        <a target="_blank" href="http://citeseerx.ist.psu.edu/search?q=${publication.title.replace(" ","+")}"><img src="public/images/citexeer.jpg" width="30" height="30"/></a>
                    </td>
                </tr>
            </table>
        </tab:tabPane>

        <tab:tabPane id="bar" tabTitle="References">
            <h3><bean:message key="text.references"/></h3><br>
            <c:out value="${warning}"/><br>
            <table>
                <c:forEach var="ref" items="${publication.refPubs}" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td><a href="showPubDetail.do?id=${ref.id}"><c:out value="${ref.title}"/></a></td>
                        <td><c:out value="${ref.year}"/></td>

                    </tr>
                </c:forEach>
            </table>
            <br>
        </tab:tabPane>

        <tab:tabPane id="bar" tabTitle="Cited by">

        </tab:tabPane>
            
        <tab:tabPane id="bar" tabTitle="Has same Source">
            <c:out value="${publication.source}" default="NA"/>
            <c:if test="${sameSourcePubs != null}">
                <h3>Publications have the same source</h3><br>
                <table>
                    <c:forEach var="ref" items="${sameSourcePubs}" varStatus="status">
                        <tr>
                            <td>${status.count}</td>
                            <td><a href="showPubDetail.do?id=${ref.id}"><c:out value="${ref.title}"/></a></td>
                            <td><c:out value="${ref.year}"/></td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
        </tab:tabPane>
    </tab:tabContainer>
</td>
