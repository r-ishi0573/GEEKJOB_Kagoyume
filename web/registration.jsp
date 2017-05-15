<!--
    会員情報入力画面
    TODO:confrmから戻ってきた時の表示
-->

<%@page import="javax.servlet.http.HttpSession"
        import="kagoyume.UserDataDTO"
        %>
<%
    //JumsHelper jh = JumsHelper.getInstance();
    HttpSession hs = request.getSession();
    UserDataDTO udd = null;
    boolean reinput = false;
    if(request.getParameter("mode") != null && request.getParameter("mode").equals("REINPUT")){
        reinput = true;
        udd = (UserDataDTO)hs.getAttribute("udd");
    }
    final UserDataDTO user = (UserDataDTO)hs.getAttribute("Login");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JUMS登録画面</title>
    </head>
    <body>
    <form action="Registration_Confirm" method="POST">
        名前:
        <input type="text" name="name" value="<% if(reinput){out.print(udd.getName());}%>">
        <br><br>
        パスワード:
        <input type="text" name="password" value="<% if(reinput){out.print(udd.getPassword());}%>">
        <br><br>

        メールアドレス:
        <input type="text" name="mail" value="<% if(reinput){out.print(udd.getMail());}%>">
        <br><br>
        
        住所
        <br>
        <textarea name="address" rows=10 cols=50 style="resize:none" wrap="hard"><% if(reinput){out.print(udd.getAddress());}%></textarea><br><br>
        
        <input type="submit" name="btnSubmit" value="確認画面へ">
    </form>
        <br>
        <a href="Login?URL=registration.jsp"><% out.print(user == null ? "ログイン" : "ログアウト"); %></a><br>
    </body>
</html>
