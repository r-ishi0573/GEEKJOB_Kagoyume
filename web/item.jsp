<%@page import="kagoyume.UserDataDTO"%>
<!--
    Item.javaから検索Hitした"i"番目を示すindex(変数:numberOfHit)を受け取り詳細情報を表示
    評価の表示は星表示にすると時間かかるのでとりあえず数値で
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
    int numOfResults = (int)hs.getAttribute("NumOfResults"); //検索結果の数(最大20)
    Map<String, Object> result = (Map<String, Object>)hs.getAttribute("RESULT");
    //RAW: セッションではなくスコープから受け取る
    String numberOfHit = (String)request.getAttribute("NumberOfHit");
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
        <h1><a href="top.jsp">(item)ショッピングデモサイト - 商品を検索する</a></h1>

        <!--検索結果を表示 TODO:検索結果=0の時は「Hitしませんでした」と表示するよう分岐?-->
        <!--中間変数に保存(検索結果で選んだ項目の"Result"->"i"以降のオブジェクト)-->
        <% Map<String, Object> hitOfResults  = ((Map<String, Object>) result.get(numberOfHit)); %>
        <div class="Item">
            <!--商品名表示 商品名をクリックするとクエリで検索Hitした"i"番目をindexとして渡す-->
            <h2><%= sUtil.h(hitOfResults.get("Name").toString()) %></h2>
            <!--サムネイル、価格を表示-->
            <p><img src="<%= sUtil.h(((Map<String, Object>) hitOfResults.get("Image")).get("Medium").toString()) %>" />
            <!--価格を変数に一旦保存し、貨幣表現に変換して表示-->
            <% String price = ((Map<String, Object>) hitOfResults.get("Price")).get("_value").toString();%>
            <font class="Price"><%= NumberFormat.getCurrencyInstance().format(Integer.parseInt(sUtil.h(price)))%></font>
            <!--評価表示-->
            <font color="#000000"><b>&nbsp;評価：<%= sUtil.h(((Map<String, Object>) hitOfResults.get("Review")).get("Rate").toString()) %></b></font>
            <br>
            <!--詳細表示-->
            <%= sUtil.h(hitOfResults.get("Description").toString()) %>
            </p>
        </div>
        <!--カートに入れるフォーム-->
        <form action="Add" method="post">  
            <input type="hidden" name="NumberOfHit" value="<%= numberOfHit %>">
            <input type="submit" value="カートに入れる"/>
        </form>
            
        <a href="Login?URL=item.jsp">"><% out.print(user == null ? "ログイン" : "ログアウト"); %></a><br>
        <!-- Begin Yahoo! JAPAN Web Services Attribution Snippet -->
        <a href="http://developer.yahoo.co.jp/about">
            <img src="http://i.yimg.jp/images/yjdn/yjdn_attbtn2_105_17.gif" width="105" height="17" title="Webサービス by Yahoo! JAPAN" alt="Webサービス by Yahoo! JAPAN" border="0" style="margin:15px 15px 15px 15px"></a>
        <!-- End Yahoo! JAPAN Web Services Attribution Snippet -->
    </body>
</html>