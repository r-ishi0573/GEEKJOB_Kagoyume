<%@page import="kagoyume.UserDataDTO"%>
<%@page import="java.util.ArrayList"%>
<!--
    ログイン画面の表示を行いユーザ名、パスワードの入力を受け取る。送信先はLogin_Complete
-->
<%@page import="java.util.Map"%>
<%@page import="javax.servlet.http.HttpSession"
        import="java.text.NumberFormat"
        import="kagoyume.DefineUtil"
        import="kagoyume.ScriptUtil" 
        import="kagoyume.JsonSearchElements"
        %>
<%
    HttpSession hs = request.getSession();
    ScriptUtil sUtil = new ScriptUtil();
    final UserDataDTO user = (UserDataDTO)hs.getAttribute("Login");
    //リクエストスコープから取得
    final ArrayList<JsonSearchElements> jseList = (ArrayList<JsonSearchElements>)request.getAttribute("HistoryJseList");
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
        <h1><a href="top.jsp">(履歴)ショッピングデモサイト - 商品を検索する</a></h1>
        <!-- price:合計金額, i:ループのindex -->
        <% int price = 0; int i= 0;%>
        <% for (JsonSearchElements jse : jseList) {%>
        <div class="Item">
            <!--商品名表示 商品名をクリックするとクエリで検索Hitした"i"番目をindexとして渡す-->
            <h2><%= sUtil.h(jse.getName())%></h2>
            <!--サムネイル、価格を表示-->
            <p><img src="<%= sUtil.h(jse.getImageUrl())%>" />
                <!--価格を変数に一旦保存し、貨幣表現に変換して表示-->
                <font class="Price"><%= NumberFormat.getCurrencyInstance().format(jse.getPrice())%></font><br>
            </p>
            <% price += jse.getPrice(); i++; %>
        </div>
        <% } %>
        
        <!--合計金額表示-->
        <h2><% out.println("合計金額：" + NumberFormat.getCurrencyInstance().format(price));%></h2>
        
        <br>
        <a href="Login?URL=history.jsp"><% out.print(user == null ? "ログイン" : "ログアウト"); %></a><br>
        <!-- Begin Yahoo! JAPAN Web Services Attribution Snippet -->
        <a href="http://developer.yahoo.co.jp/about">
            <img src="http://i.yimg.jp/images/yjdn/yjdn_attbtn2_105_17.gif" width="105" height="17" title="Webサービス by Yahoo! JAPAN" alt="Webサービス by Yahoo! JAPAN" border="0" style="margin:15px 15px 15px 15px"></a>
        <!-- End Yahoo! JAPAN Web Services Attribution Snippet -->
    </body>
</html>