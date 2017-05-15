<!--
    セッションに渡された登録の入力情報を表示
-->

<%@page import="java.util.ArrayList"
        import="javax.servlet.http.HttpSession"
        import="kagoyume.ScriptUtil"
        import="kagoyume.UserDataDTO" %>
<%
    ScriptUtil sUtil = new ScriptUtil();
    HttpSession hs = request.getSession();
    UserDataDTO udd = (UserDataDTO)hs.getAttribute("udd");
    ArrayList<String> chkList = udd.chkproperties();
    final UserDataDTO user = (UserDataDTO)hs.getAttribute("Login"); 
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JUMS登録確認画面</title>
        <% if(request.getAttribute("mode") != null) out.println(request.getAttribute("mode")); %>
    </head>
    <body>
    <% if(chkList.size()==0){ %>
        <h1>登録確認</h1>
        名前:<%= udd.getName()%><br>
        パスワード:<%= udd.getPassword()%><br>
        メールアドレス:<%= udd.getMail()%><br>
        住所:<%= udd.getAddress()%><br>
        上記の内容で登録します。よろしいですか？
        <form action="Registration_Complete" method="POST">
<!--            <input type="hidden" name="ac"  value="<//%= hs.getAttribute("ac")%>">-->
            <input type="submit" name="yes" value="はい">
        </form>
    <% }else{ %>
        <h1>入力が不完全です</h1>
        <%=sUtil.chkinput(chkList) %>
    <% } %>
        <!--登録画面に戻るボタン-->
        <form action="Registration" method="POST">
            <input type="submit" name="no" value="登録画面に戻る">
            <input type="hidden" name="mode" value="REINPUT">
        </form>
        <a href="Login?URL=registration_confirm.jsp"><% out.print(user == null ? "ログイン" : "ログアウト"); %></a><br>
    </body>
</html>
