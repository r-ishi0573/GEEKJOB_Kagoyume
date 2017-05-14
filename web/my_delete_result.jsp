<!--
    NOTICE:パスワード表示しちゃまずくない？
-->

<%@page import="javax.servlet.http.HttpSession"
        import="kagoyume.ScriptUtil"
        import="kagoyume.UserDataDTO" %>
<%
    HttpSession hs = request.getSession();
    ScriptUtil sUtil = new ScriptUtil();
    UserDataDTO user = (UserDataDTO)hs.getAttribute("Login");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ユーザー登録画面</title>
    </head>
    <body>
        <h1>ユーザーを削除しました</h1><br>
    </body>
    <a href="Login?URL=my_delete_result.jsp">"><% out.print(user == null ? "ログイン" : "ログアウト"); %></a><br>
     <%=sUtil.home()%>  
</html>
