<!--
  formで渡す値をvalueからkeyに変更
-->
<%@page import="java.util.Map"%>
<%@page import="javax.servlet.http.HttpSession"
        import="kagoyume.DefineUtil"
        import="kagoyume.ScriptUtil" 
        import="kagoyume.UserDataDTO"
        %>
<%
    HttpSession hs = request.getSession();
    ScriptUtil sUtil = new ScriptUtil();
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
        <h1><a href="top.jsp">ショッピングデモサイト - 商品を検索する</a></h1>
        
        <!--ログインしている場合はユーザ名表示と会員情報へのリンク-->
        <a href="My_Data"><% if(user != null) { out.println("ようこそ"+user.getName()+"さん！");} %></a><br>
        
            <!--Topへページ遷移した時のログイン状況を表示-->
            <% final String ld = (String)request.getAttribute("LoginDialog"); %>
            <font color="#FF0000"><% if(ld != null) out.println(ld); %></font>
            
        <!--フォーム入力-->
        <form action="Search" method="GET" class="Search">
            
            表示順序:
            <select name="sort">
                <% for(Map.Entry<String, String> so : DefineUtil.sortOrder.entrySet()) { %>
                <option value="<%= sUtil.h(so.getKey()) %>" <% if(so.getKey() == "-score") {out.print("selected=\"selected\"");} %>><%= sUtil.h(so.getValue()) %></option>
                <% } %>
            </select>
            
            キーワード検索：
            <select name="category_id">
                <% for(Map.Entry<String, String> ct : DefineUtil.categories.entrySet()) { %>
                <option value="<%= sUtil.h(ct.getKey()) %>" <% if(ct.getKey() == "1") {out.print("selected=\"selected\"");} %>><%= sUtil.h(ct.getValue()) %></option>
                <% } %>  
            </select>
            
            <!--検索キーワード入力フォーム-->
            <input type="text" name="query" value=""/>
            
            <!--送信ボタン-->
            <input type="submit" value="Yahooショッピングで検索"/>
            
        </form>
            
        <!--ログインしてるかしていないかでログインリンクの表示変更-->
        <a href="Login?URL=top.jsp"><% out.print(user == null ? "ログイン" : "ログアウト"); %></a><br>
            
        <!--カートの表示-->
        <a href="Cart"><% out.print("カートを見る"); %></a><br>
        
        <a href="Registration">会員登録</a><br>
            
        <!-- Begin Yahoo! JAPAN Web Services Attribution Snippet -->
        <a href="http://developer.yahoo.co.jp/about">
            <img src="http://i.yimg.jp/images/yjdn/yjdn_attbtn2_105_17.gif" width="105" height="17" title="Webサービス by Yahoo! JAPAN" alt="Webサービス by Yahoo! JAPAN" border="0" style="margin:15px 15px 15px 15px"></a>
        <!-- End Yahoo! JAPAN Web Services Attribution Snippet -->
    </body>
</html>