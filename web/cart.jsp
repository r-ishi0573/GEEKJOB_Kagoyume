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
    ArrayList<JsonSearchElements> jseList = (ArrayList<JsonSearchElements>)hs.getAttribute("JseList");
    //カートで削除リンクが押された場合にその商品をカート情報から削除する
    if(request.getParameter("delete") != null) {
        jseList.remove(Integer.parseInt(request.getParameter("delete")));
    }
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
        
        <!--カートの内容表示。カートが空の場合は商品表示なし-->
        
        <!-- price:合計金額, i:ループのindex -->
        <% int price = 0; int i= 0;%>
        
        <!--カートが空の時-->
        <% if(jseList == null || jseList.isEmpty()) { %>カートに商品はありません<% } %>
        
        <!--カートに商品がある場合-->
        <% if(jseList != null && !jseList.isEmpty()) { %>
        
            <% for (JsonSearchElements jse : jseList) {%>
            <div class="Item">
                <!--商品名表示 商品名をクリックするとクエリで検索Hitした"i"番目をindexとして渡す-->
                <h2><%= sUtil.h(jse.getName())%></h2>
                <!--サムネイル、価格を表示-->
                <p><img src="<%= sUtil.h(jse.getImageUrl())%>" />
                    <!--価格を変数に一旦保存し、貨幣表現に変換して表示-->
                    <font class="Price"><%= NumberFormat.getCurrencyInstance().format(jse.getPrice())%></font><br>
                    <!-- 削除リンク。クエリで削除したいjseListのindexを渡す -->
                    <a href="cart.jsp?delete=<%=i%>">削除:<%= i%></a><br>
                </p>
                <% price += jse.getPrice(); %>
                <% i++; %>
            </div>
            <% } %>
            
            <!--合計金額表示-->
            <h2><% out.println("合計金額：" + NumberFormat.getCurrencyInstance().format(price));%></h2>
            
            <!--購入ページへの送信フォーム-->
            <form action="Buy_Confirm">
                <input type="submit" value="購入する"/>
            </form>
            
        <% } %>
        
        <!--カート表示ここまで-->
        
        <br>
        <a href="Login?URL=cart.jsp">"><% out.print(user == null ? "ログイン" : "ログアウト"); %></a>
        <!-- Begin Yahoo! JAPAN Web Services Attribution Snippet -->
        <a href="http://developer.yahoo.co.jp/about">
            <img src="http://i.yimg.jp/images/yjdn/yjdn_attbtn2_105_17.gif" width="105" height="17" title="Webサービス by Yahoo! JAPAN" alt="Webサービス by Yahoo! JAPAN" border="0" style="margin:15px 15px 15px 15px"></a>
        <!-- End Yahoo! JAPAN Web Services Attribution Snippet -->
    </body>
</html>