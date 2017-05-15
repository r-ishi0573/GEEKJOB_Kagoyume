<!--
    NOTICE:パスワード表示しちゃまずくない？
-->

<%@page import="javax.servlet.http.HttpSession"
        import="kagoyume.ScriptUtil"
        import="kagoyume.UserDataDTO" %>
<%
    HttpSession hs = request.getSession();
    ScriptUtil sUtil = new ScriptUtil();
    UserDataDTO udd = (UserDataDTO)request.getAttribute("udd");
    final UserDataDTO user = (UserDataDTO)hs.getAttribute("Login");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ユーザー登録画面</title>
    </head>
    <body>
        <h1>登録結果</h1><br>
        名前:<%= udd.getName()%><br>
        パスワード:<%= udd.getPassword()%><br>
        メールアドレス:<%= udd.getMail()%><br>
        自己紹介:<%= udd.getAddress()%><br>
        以上の内容で登録しました。<br>
    </body>
    <a href="Login?URL=registration_complete.jsp"><% out.print(user == null ? "ログイン" : "ログアウト"); %></a><br>
     <%=sUtil.home()%>  
</html>
