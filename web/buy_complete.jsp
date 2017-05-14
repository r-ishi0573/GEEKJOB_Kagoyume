<%@page import="kagoyume.UserDataDTO"%>
<%@page import="java.util.Map"%>
<%@page import="javax.servlet.http.HttpSession"
        import="java.util.ArrayList"
        import="kagoyume.DefineUtil"
        import="kagoyume.ScriptUtil" 
        import="kagoyume.JsonSearchElements"
        %>
<%
    HttpSession hs = request.getSession();
    ScriptUtil sUtil = new ScriptUtil();
    final UserDataDTO user = (UserDataDTO)hs.getAttribute("Login");
    ArrayList<JsonSearchElements> jseList = (ArrayList<JsonSearchElements>)session.getAttribute("JseList");
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
        <h1><a href="top.jsp">(カート)ショッピングデモサイト - 商品を検索する</a></h1>
        <h2>購入が完了しました</h2>
        <br>
        <a href="search.jsp">検索結果に戻る</a><br>
        
        <a href="Login?URL=buy_complete.jsp">"><% out.print(user == null ? "ログイン" : "ログアウト"); %></a>
        <a href="Registration">会員登録</a><br>
        <!-- Begin Yahoo! JAPAN Web Services Attribution Snippet -->
        <a href="http://developer.yahoo.co.jp/about">
            <img src="http://i.yimg.jp/images/yjdn/yjdn_attbtn2_105_17.gif" width="105" height="17" title="Webサービス by Yahoo! JAPAN" alt="Webサービス by Yahoo! JAPAN" border="0" style="margin:15px 15px 15px 15px"></a>
        <!-- End Yahoo! JAPAN Web Services Attribution Snippet -->
    </body>
</html>