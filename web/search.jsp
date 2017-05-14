<%@page import="kagoyume.UserDataDTO"%>
<!--
   search.javaから検索結果の
   1."Resultset"->"0"->"Result"以降のオブジェクトを受けるように変更
   2."Resultset"->"totalResultsReturned"をint化した値を受けるように変更
   受けたオブジェクトの"i"番目の要素の抽出して表示
   TODO:検索結果=0の場合の処理の記述
        "i"番目のリンク踏んだらGETで"i"を渡す感じ?
-->

<%@page import="java.util.*"%>
<%@page import="javax.servlet.http.HttpSession"
        import="java.text.NumberFormat"
        import="kagoyume.DefineUtil"
        import="kagoyume.ScriptUtil"
        import="kagoyume.JsonSearchElements" %>
<%
    HttpSession hs = request.getSession();
    ScriptUtil sUtil = new ScriptUtil();
    //List<JsonSearchElements> jseList = (List<JsonSearchElements>)hs.getAttribute("jselist");
    int numOfResults = (int)hs.getAttribute("NumOfResults");
    Map<String, Object> result = (Map<String, Object>)hs.getAttribute("RESULT");
    final UserDataDTO user = (UserDataDTO)hs.getAttribute("Login");
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
        <!--< out.println(hs.getAttribute("query"));out.println(hs.getAttribute("sort"));out.println(hs.getAttribute("category_id")); >-->
        <h1><a href="top.jsp">(search)ショッピングデモサイト - 商品を検索する</a></h1>
        <form action="Search" class="Search">
            <!--ソート選択フォームを表示-->
            表示順序:
            <select name="sort">
                <% for(Map.Entry<String, String> so : DefineUtil.sortOrder.entrySet()) { %>
                <option value="<%= sUtil.h(so.getKey()) %>" <% if(so.getKey().equals(hs.getAttribute("sort"))) {out.print("selected=\"selected\"");} %>><%= sUtil.h(so.getValue()) %></option>
                <% } %>
            </select>
            <!--検索カテゴリ選択フォームを表示-->
            キーワード検索：
            <select name="category_id">
                <% for(Map.Entry<String, String> ct : DefineUtil.categories.entrySet()) { %>
                <option value="<%= sUtil.h(ct.getKey()) %>" <% if(ct.getKey().equals(hs.getAttribute("category_id"))) {out.print("selected=\"selected\"");} %>><%= sUtil.h(ct.getValue()) %></option>
                <% } %>
            </select>
            <!--検索キーワード入力フォームを表示-->
            <input type="text" name="query" value=""/>
            <input type="submit" value="Yahooショッピングで検索"/>
        </form>
        <!--検索結果を表示 TODO:検索結果=0の時は「Hitしませんでした」と表示するよう分岐?-->
        <% for(int i=0; i<numOfResults; i++) {//for (JsonSearchElements jse : jseList) {
//          中間変数に保存(Hitした検索結果のi番目)
            Map<String, Object> hitOfResults  = ((Map<String, Object>) result.get(String.valueOf(i))); %>
        <div class="Item">
            <!--商品名表示 商品名をクリックするとクエリで検索Hitした"i"番目をindexとして渡す-->
            <h2><a href="Item?NumberOfHit=<%= i %>" ><%= sUtil.h(hitOfResults.get("Name").toString()) %></a></h2>
            <!--サムネイル、価格を表示-->
            <p><img src="<%= sUtil.h(((Map<String, Object>) hitOfResults.get("Image")).get("Medium").toString()) %>" />
                <!--価格を変数に一旦保存し、貨幣表現に変換して表示-->
                <% String price = ((Map<String, Object>) hitOfResults.get("Price")).get("_value").toString(); %>
                <font class="Price"><%= NumberFormat.getCurrencyInstance().format(Integer.parseInt(sUtil.h(price))) %></font>
            </p>
        </div>
        <% } %>
        <!-- Begin Yahoo! JAPAN Web Services Attribution Snippet -->
        <a href="Login?URL=search.jsp"><% out.print(user == null ? "ログイン" : "ログアウト"); %></a><br>
        <a href="http://developer.yahoo.co.jp/about">
            <img src="http://i.yimg.jp/images/yjdn/yjdn_attbtn2_105_17.gif" width="105" height="17" title="Webサービス by Yahoo! JAPAN" alt="Webサービス by Yahoo! JAPAN" border="0" style="margin:15px 15px 15px 15px"></a>
        <!-- End Yahoo! JAPAN Web Services Attribution Snippet -->
    </body>
</html>