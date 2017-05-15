<%@page import="kagoyume.UserDataDTO"%>
<!--
    ログイン画面の表示を行いユーザ名、パスワードの入力を受け取る。送信先はLogin_Complete
-->
<%@page import="java.util.Map"%>
<%@page import="javax.servlet.http.HttpSession"
        import="java.text.NumberFormat"
        import="java.util.ArrayList"
        import="kagoyume.DefineUtil"
        import="kagoyume.ScriptUtil"
        import="kagoyume.JsonSearchElements"
        %>
<%
    HttpSession hs = request.getSession();
    ScriptUtil sUtil = new ScriptUtil();
    final UserDataDTO user = (UserDataDTO)hs.getAttribute("Login");
    ArrayList<JsonSearchElements> jseList = (ArrayList<JsonSearchElements>)hs.getAttribute("JseList");
    //out.println(request.getParameter("delete"));
    //カートで削除リンクが押された場合に削除する
    //if(request.getParameter("delete") != null) {
    //    jseList.remove(Integer.parseInt((String)request.getParameter("delete")));
    //}
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
        <h1><a href="top.jsp">(Buy_Confirm)ショッピングデモサイト - 商品を検索する</a></h1>
        <!-- price:合計金額, i:ループのindex -->
        <% int price = 0; int i= 0;%>
        <table class="type01">
            <th class="a">商品名</th>
            <th class="b">価格</th>
        <% for (JsonSearchElements jse : jseList) {%>
            <tr>
            <!--商品名表示 商品名をクリックするとクエリで検索Hitした"i"番目をindexとして渡す-->
                <td class="a"><%= sUtil.h(jse.getName())%></td>
                <!--貨幣表現に変換して表示-->
                <td class="b"><font class="Price"><%= NumberFormat.getCurrencyInstance().format(jse.getPrice())%></font></td>
            </tr>
            <% price += jse.getPrice(); %>
            <% i++; %>
        <% } %>
        </table>
        <h2><% out.println("合計金額：" + NumberFormat.getCurrencyInstance().format(price));%></h2>
        
        <form action="Buy_Complete" method="GET">
            <!--発送方法ラジオボタン。初期値は1つ目のボタン-->
            <% for(int j = 1; j<=3; j++){ %>
            <input type="radio" name="Type" value="<%=j%>"<%if(j == 1){out.print("checked = \"checked\"");}%>><%=sUtil.exTypenum(j)%>
            <% } %>
            <br>
            <input type="hidden" name="Price" value="<%= price %>"/>
            <input type="submit" value="購入する"/>
        </form>
        
        <br>
        <a href="Login?URL=buy_confirm.jsp"><% out.print(user == null ? "ログイン" : "ログアウト"); %></a>
        <!-- Begin Yahoo! JAPAN Web Services Attribution Snippet -->
        <a href="http://developer.yahoo.co.jp/about">
            <img src="http://i.yimg.jp/images/yjdn/yjdn_attbtn2_105_17.gif" width="105" height="17" title="Webサービス by Yahoo! JAPAN" alt="Webサービス by Yahoo! JAPAN" border="0" style="margin:15px 15px 15px 15px"></a>
        <!-- End Yahoo! JAPAN Web Services Attribution Snippet -->
    </body>
</html>