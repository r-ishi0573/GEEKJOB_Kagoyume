<%@page import="javax.servlet.http.HttpSession"
        import="kagoyume.UserDataDTO"
        %>
<%
    //JumsHelper jh = JumsHelper.getInstance();
    HttpSession hs = request.getSession();
    UserDataDTO udd = null;
    boolean reinput = false;
    UserDataDTO user = (UserDataDTO)hs.getAttribute("Login");
    //if(request.getParameter("mode") != null && request.getParameter("mode").equals("REINPUT")){
    //    reinput = true;
    //    udd = (UserDataDTO)hs.getAttribute("udd");
    //}
    
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JUMS登録画面</title>
    </head>
    <body>
    <form action="My_Update_Result" method="POST">
        名前:
        <input type="text" name="name" value="<% out.print(user.getName()); %>">
        <br><br>
        パスワード:
        <input type="text" name="password" value="<% out.print(user.getPassword()); %>">
        <br><br>

        メールアドレス:
        <input type="text" name="mail" value="<% out.print(user.getMail()); %>">
        <br><br>
        
        住所
        <br>
        <textarea name="address" rows=10 cols=50 style="resize:none" wrap="hard"><% out.print(user.getAddress()); %></textarea><br><br>
    </form>
    マジで削除しますか？<br>
    <a href="My_Delete_Result?UserID=<%= user.getUserID() %>">はい</a><br>
    <a href="top.jsp">いいえ</a><br>
    <br>
 
    <a href="Login?URL=my_delete.jsp"><% out.print(user == null ? "ログイン" : "ログアウト"); %></a><br>
    </body>
</html>
