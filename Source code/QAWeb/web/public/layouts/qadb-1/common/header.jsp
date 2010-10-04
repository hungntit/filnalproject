<%-- 
    Document   : header
    Created on : Sep 2, 2010, 10:20:54 AM
    Author     : ThuanHung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<script language="JavaScript" type="text/javascript">
    <!--
    function hideLoadingPage() {
        if (document.getElementById) { // DOM3 = IE5, NS6
            document.getElementById('hidepage').style.visibility = 'hidden';
        }
        else {
            if (document.layers) { // Netscape 4
                document.hidepage.visibility = 'hidden';
            }
            else { // IE 4
                document.all.hidepage.style.visibility = 'hidden';
            }
        }
    }
    function showLoadingPage() {
        if (document.getElementById) { // DOM3 = IE5, NS6
            document.getElementById('hidepage').style.visibility = 'visible';
        }
        else {
            if (document.layers) { // Netscape 4
                document.hidepage.visibility = 'show';
            }
            else { // IE 4
                document.all.hidepage.style.visibility = 'visible';
            }
        }      
    }
    // End -->
</script>
<div id="header">
    <img src="<bean:message key="image.background"/>" alt="<bean:message key="image.logo.alttext"/>" style="position: fixed;z-index: -1;width: 100%; height: 110px; top: 0px;visibility: inherit"/>
    <table style="width: 100%" >
        <tr>
            <td style="width: 50%;height: 80px;" align="center">
                <table>
                    <tr>
                        <td><img src="<bean:message key="image.logo"/>" alt="<bean:message key="image.logo.alttext"/>" style="width: 80px;"/></td>
                        <td valign="middle">
                            <div style="width:  200px;height: 80px;">
                                <span class="text1" style="color: #FFA414"><bean:message key="text.dbqa"/></span><hr>
                                <span class="text3" style="color: #FFA414"><bean:message key="text.UIT"/></span>
                            </div>
                        </td>
                    </tr>
                </table>
            </td>
            <td style="width: 50%;height: 100px;text-align: right" valign="bottom">
                <bean:message key="text.language"/>
                <script language="javscript" type="text/javascript">
                    document.write('<a href="./Locale.do?method=english&page='+ document.location.href +'">');
                </script>
                <img src="<bean:message key="image.en"/>" alt="<bean:message key="image.en.alttext"/>"/>
                <script language="javscript" type="text/javascript">
                    document.write('</a>');
                </script>
                <script language="javscript" type="text/javascript">
                    document.write('<a href="./Locale.do?method=vietnamese&page='+ document.location.href +'">');
                </script>
                <img src="<bean:message key="image.vn"/>" alt="<bean:message key="image.vn.alttext"/>"/>
                <script language="javscript" type="text/javascript">
                    document.write('</a>');
                </script>
            </td>
        </tr>
    </table>
</div>
<div id="hidepage">
    <b><bean:message key="text.process"/></b><br>
    <img src="<bean:message key="image.wait"/>" alt="<bean:message key="image.wait.alttext"/>"><br>
    <a style="text-align: right" href="#" onclick="hideLoadingPage();">Close</a>
</div>