<!--
    ログイン画面の表示を行いユーザ名、パスワードの入力を受け取る。送信先はLogin_Complete
-->
<%@page import="java.util.Map"%>
<%@page import="javax.servlet.http.HttpSession"
        import="kagoyume.DefineUtil"
        import="kagoyume.ScriptUtil" %>
<%
    HttpSession hs = request.getSession();
    ScriptUtil sUtil = new ScriptUtil();
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
        <title>ショッピングデモサイト - 商品を検索する</title>
        <link rel="stylesheet" type="text/css" href="./css/prototype.css"/>
    </head>
    <body>
        <h1><a href="top.jsp">(ログイン)ショッピングデモサイト - 商品を検索する</a></h1>
        <!--ログイン失敗した場合のダイアログ-->
        <p><font color="#FF0000"><% if(request.getAttribute("LoginFailed") != null) out.println(request.getAttribute("LoginFailed")); %></font></p>
        <form action="Login_Complete" method="POST">
            ユーザーID:<input type="text" name="name" value=""/>
            パスワード:<input type="text" name="password" value=""/>
            <input type="submit" value="送信"/>
        </form>
        <a href="Login?URL=login.jsp">ログイン</a><br>
        <a href="Registration">会員登録</a><br>
        <!-- Begin Yahoo! JAPAN Web Services Attribution Snippet -->
        <a href="http://developer.yahoo.co.jp/about">
            <img src="http://i.yimg.jp/images/yjdn/yjdn_attbtn2_105_17.gif" width="105" height="17" title="Webサービス by Yahoo! JAPAN" alt="Webサービス by Yahoo! JAPAN" border="0" style="margin:15px 15px 15px 15px"></a>
        <!-- End Yahoo! JAPAN Web Services Attribution Snippet -->
    </body>
</html>