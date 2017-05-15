<%@page import="java.util.Map"%>
<%@page import="javax.servlet.http.HttpSession"
        import="java.util.ArrayList"
        import="kagoyume.DefineUtil"
        import="kagoyume.ScriptUtil"
        import="kagoyume.UserDataDTO"
        import="kagoyume.UserDataDAO"
        import="kagoyume.JsonSearchElements"
        %>
<%
    HttpSession hs = request.getSession();
    ScriptUtil sUtil = new ScriptUtil();
    //ArrayList<JsonSearchElements> jseList = (ArrayList<JsonSearchElements>)session.getAttribute("JseList");
    final UserDataDTO user = (UserDataDTO)hs.getAttribute("Login");
    //URLのクエリからユーザーIDを取得
    //final int userID = Integer.parseInt(request.getParameter("UserID"));
    //IDでDBからデータを抽出
    //UserDataDTO userdata = new UserDataDTO();
    //userdata.setUserID(userID);
    //userdata = UserDataDAO .getInstance().searchByID(userdata);
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
        <h2>ユーザ情報の表示</h2>
        <table class="type01">
            
            <th class="b">ユーザ名</th>
            <th class="b">パスワード</th>
            <th class="b">メールアドレス</th>
            <th class="b">住所</th>
            <th class="b">総購入金額</th>
            <th class="b">登録日時</th>
            
            <tr>
                <td class="b"><%= sUtil.h(user.getName())%></td>
                <td class="b"><%= sUtil.h(user.getPassword())%></td>
                <td class="b"><%= sUtil.h(user.getMail())%></td>
                <td class="b"><%= sUtil.h(user.getAddress())%></td>
                <td class="b"><%= sUtil.formatCurrency(user.getTotal()) %></td>
                <td class="b"><%= sUtil.h(sUtil.formattedTimestamp(user.getNewDate()))%></td> 
            </tr>

        </table>
        <br>
        
        <a href="My_History?UserID=<%= user.getUserID() %>">購入履歴を見る</a><br>
        <a href="My_Update">登録情報を変更する</a><br>
        <a href="My_Delete">登録情報を削除する</a><br>
        
        <a href="Login?URL=my_data.jsp"><% out.print(user == null ? "ログイン" : "ログアウト"); %></a><br>
        <!-- Begin Yahoo! JAPAN Web Services Attribution Snippet -->
        <a href="http://developer.yahoo.co.jp/about">
            <img src="http://i.yimg.jp/images/yjdn/yjdn_attbtn2_105_17.gif" width="105" height="17" title="Webサービス by Yahoo! JAPAN" alt="Webサービス by Yahoo! JAPAN" border="0" style="margin:15px 15px 15px 15px"></a>
        <!-- End Yahoo! JAPAN Web Services Attribution Snippet -->
    </body>
</html>