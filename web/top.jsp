<%@page import="java.util.Map"%>
<%@page import="javax.servlet.http.HttpSession"
        import="kagoyume.DefineUtil"
        import="kagoyume.ScriptUtil" %>
<%
    HttpSession hs = request.getSession();
    ScriptUtil su = new ScriptUtil();
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
        <h1><a href="/top.jsp">ショッピングデモサイト - 商品を検索する</a></h1>
        <form action="/Search" class="Search">
            表示順序:
            <select name="sort">
                <% for(Map.Entry<String, String> so : DefineUtil.sortOrder.entrySet()) { %>
                <option value="<%= su.h(so.getValue()) %>" <% if(so.getKey() == "-score") {out.print("selected=\"selected\"");} %>><%= su.h(so.getValue()) %></option>
                <% } %>
            </select>
            キーワード検索：
            <select name="category_id">
                <% for(Map.Entry<String, String> ct : DefineUtil.categories.entrySet()) { %>
                <option value="<%= su.h(ct.getValue()) %>" <% if(ct.getKey() == "1") {out.print("selected=\"selected\"");} %>><%= su.h(ct.getValue()) %></option>
                <% } %>
            </select>
            <input type="text" name="query" value=""/>
            <input type="submit" value="Yahooショッピングで検索"/>
        </form>
        <!-- Begin Yahoo! JAPAN Web Services Attribution Snippet -->
        <a href="http://developer.yahoo.co.jp/about">
            <img src="http://i.yimg.jp/images/yjdn/yjdn_attbtn2_105_17.gif" width="105" height="17" title="Webサービス by Yahoo! JAPAN" alt="Webサービス by Yahoo! JAPAN" border="0" style="margin:15px 15px 15px 15px"></a>
        <!-- End Yahoo! JAPAN Web Services Attribution Snippet -->
    </body>
</html>