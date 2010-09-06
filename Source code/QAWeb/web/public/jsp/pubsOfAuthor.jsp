<%-- 
    Document   : PubsOfAuthor
    Created on : Sep 6, 2010, 8:27:56 PM
    Author     : Hoang-PC
--%>
<%@taglib uri="http://struts.apache.org/tags-html"  prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="l"%>
<script type="text/javascript" language="javascript">
    function contextMenuClick(event) {
        ev = window.event || event;
        var objContextMenu = document.getElementById("contextmenu");

        objContextMenu.style.left = (ev.pageX + 4) + "px";
        objContextMenu.style.top = (ev.pageY + 4) + "px";
        objContextMenu.style.visibility = "visible";
    }
</script>
<td class="bg4">
    <div id="contextmenu">publication informations
    </div>                
    <div>
        <div style="text-align: left;background-color: transparent;">
            <h2 style="color: green"><c:out value="${authorName}"/></h2>
            <bean:message key="text.total"/>:
            <c:out value="${totalNums}"/><hr>
            <% int i = 1;%>
            <c:forEach var="pub" items="${publications}">
                <div style="visibility: hidden;margin: 0xp;position: fixed"></div>
                <li style="list-style: none">
                    <a href="#" style="text-decoration: none;" onmouseover="contextMenuClick(event);" onmouseout="document.getElementById('contextmenu').style.visibility = 'hidden'">
                        <%=i%>. <c:out value="${pub.title}"/> 
                    </a>
                    - <c:out value="${pub.year}"/>                    
                </li>
                <%i++;%>
            </c:forEach>
        </div>
    </div>
</td>
