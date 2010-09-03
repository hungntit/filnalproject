<%-- 
    Document   : search
    Created on : Sep 3, 2010, 9:21:15 PM
    Author     : Hoang-PC
--%>
<%@taglib uri="http://struts.apache.org/tags-html"  prefix="html"%>
<td class="bg4">
    <div>
        <div align="center">
            <html:form action="/search" method="POST">
                <ul style="list-style-type: none;display: block;">
                    <li>Search :
                        <html:select property="type">
                            <html:option value="All" />
                            <html:option value="Author" />
                        </html:select>
                    </li>
                    <li>Input : <html:text property="keyWord" size="60"/></li>
                    <li><html:image property="submit" srcKey="image.submit" altKey="image.submit.alttext"/></li>
                </ul>
            </html:form>
        </div>
    </div
</td>
