<%-- 
    Document   : publicationDetail
    Created on : Sep 7, 2010, 10:01:48 AM
    Author     : aa
--%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="l" %>
<%@taglib uri="/WEB-INF/tlds/struts-layout.tld" prefix="layout" %>
<script type="text/javascript" src="public/javascript/javascript.js"></script>
<style type="text/css">
.ongletMain {
	
	BACKGROUND-COLOR: #FFFFFF;/*dce8f4*/
}
.ongletTextEna {
	BORDER-RIGHT: #336699 1px solid;
	BORDER-TOP: #336699 1px solid;
	FONT-WEIGHT: bold;
	FONT-SIZE: 10px;
	BORDER-LEFT: #336699 1px solid;
	COLOR: #FFFFFF; /*#005386; */
	BORDER-BOTTOM: medium none;
	FONT-FAMILY: verdana;
	BACKGROUND-COLOR: #336699;
	TEXT-ALIGN: center;
}

.ongletTextDis {
    background-color: #FFFFFF;
    border: 1px solid #336699;
    color: #336699;
    font-family: verdana;
    font-size: 10px;
    font-weight: bold;
    text-align: center;
}
.ongletTextErr {
    background-color: #ED4F50;
    border-color: #C5C5C5 #C5C5C5 #80ADD6;
    border-right: 1px solid #C5C5C5;
    border-style: solid;
    border-width: 1px 1px 2px;
    color: #FFFFFF;
    font-family: verdana;
    font-size: 10px;
    font-weight: bold;
    text-align: center;
}
.ongletMiddle {
    background-color: #FFFFFF;
    font-size: 1px;
}
.ongletSpace {
    
}
</style>
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

    <layout:html>
    <layout:tabs  width="400">
        <layout:tab key="text.dblp.author" width="50">
             <table  style="-moz-border-radius: 10px;-webkit-border-radius: 10px;background-color: whitesmoke;width: 100%; ">
                <c:forEach var="au" items="${publication.authors}">
                   <tr>
                       <td>
                           <a href="showPubsByAuthor.do?authorName=${au.author}">
                            <c:out value="${au.author}"/>
                            </a>
                       </td>
                    <tr>
                </c:forEach>
             </table>
        </layout:tab>
         <layout:tab key="text.info" width="50">
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
          </layout:tab>
         <layout:tab key="text.links" width="50">
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
          </layout:tab>
         <layout:tab key="text.references" width="50">
              <h3><bean:message key="text.references"/></h3><br>
                <c:out value="${warning}"/><br>
                <table  style="-moz-border-radius: 10px;-webkit-border-radius: 10px;background-color: whitesmoke;width: 100%;">
                    <c:forEach var="ref" items="${publication.refPubs}" varStatus="status">
                        <tr>
                            <td>${status.count}</td>
                            <td><a href="showPubDetail.do?id=${ref.id}"><c:out value="${ref.title}"/></a></td>
                            <td><c:out value="${ref.year}"/></td>

                        </tr>
                    </c:forEach>
                </table>
                <br>
         </layout:tab>
         <layout:tab key="text.dblp.samesource" width="100">
              <c:out value="${publication.source}" default="NA"/>
                <c:if test="${sameSourcePubs != null}">
                    <h3>Publications have the same source</h3><br>
                     <table  style="-moz-border-radius: 10px;-webkit-border-radius: 10px;background-color: whitesmoke;width: 100%;">
                        <c:forEach var="ref" items="${sameSourcePubs}" varStatus="status">
                            <tr>
                                <td>${status.count}</td>
                                <td><a href="showPubDetail.do?id=${ref.id}"><c:out value="${ref.title}"/></a></td>
                                <td><c:out value="${ref.year}"/></td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:if>
         </layout:tab>
    </layout:tabs>
  </layout:html>
</td>