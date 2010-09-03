<%-- 
    Document   : search
    Created on : Sep 3, 2010, 9:21:15 PM
    Author     : Hoang-PC
--%>
<%@taglib uri="http://struts.apache.org/tags-html"  prefix="html"%>
<td class="bg4">
    <div>
        <div>
            <html:form action="/search" method="POST">
                <table>
                    <tr style="margin: 5px;">
                        <td>Search :</td>
                        <td align="left"><html:select property="type">
                                <html:option value="All" />
                                <html:option value="Author" />
                            </html:select>
                        </td>
                    </tr>
                    <tr style="margin: 5px;">
                        <td>Input :</td>
                        <td><html:text property="keyWord" size="60"/></td>
                        <td><html:image property="submit" srcKey="image.submit" altKey="image.submit.alttext"/></td>
                    </tr>
                </table>
            </html:form>
        </div>
    </div
</td>
