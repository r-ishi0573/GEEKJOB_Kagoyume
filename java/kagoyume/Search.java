/**
 * top.jspからフォーム入力を受け取る
 * ⇨YahooショッピングAPIで検索をかけ、結果のjsonファイルを受け取る
 * ⇨seach.jspへ検索結果を渡して遷移
 */
package kagoyume;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import javafx.beans.binding.Bindings;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.net.*;
import java.io.*;
import net.arnx.jsonic.JSON;

public class Search extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            
            request.setCharacterEncoding("UTF-8");//リクエストパラメータの文字コードをUTF-8に変更

            response.setContentType("text/html; charset=UTF-8"); 
            response.setContentType("application/json; charset=UTF-8");
            PrintWriter out = response.getWriter();

            //out.println(request.getParameter("sort"));
            //out.println(URLDecoder.decode(request.getParameter("category_id"), "UTF-8"));
            //out.println(request.getParameter("query"));
            
            //
            //入力を取得する処理
            //
            //検索文字列。空でないかチェック
            String query = !(request.getParameter("query").isEmpty()) ? request.getParameter("query") : "";
            //out.println(query);
            //検索文字列がない場合はtop.jspへ戻る
            if(query.equals("")) {
                //RAW 「検索する文字を入力してください」みたいなの入れた方がいい？
                request.getRequestDispatcher("/top.jsp").forward(request, response); 
            }
            String query4url = URLEncoder.encode(query, "UTF-8");
            
            
            //検索条件。空でないかチェックし、該当検索条件があるかチェック
            String sort = !(request.getParameter("sort").isEmpty()) &&
                    DefineUtil.sortOrder.containsKey(request.getParameter("sort"))
                    ? request.getParameter("sort") : "";
            //out.println(sort);
            String sort4url = URLEncoder.encode(sort, "UTF-8");
            
            //検索カテゴリ。空でないかチェックし、該当カテゴリがあるかチェック
            //数値どうかのチェックはしてない
            String category_id = !(request.getParameter("category_id").isEmpty()) &&
                    DefineUtil.categories.containsKey(request.getParameter("category_id"))
                    ? request.getParameter("category_id") : "1";
            //out.println(category_id);
            
            //json取得URL設定
            String url_str = "http://shopping.yahooapis.jp/ShoppingWebService/V1/json/itemSearch?appid="+DefineUtil.appid+"&query="+query4url+"&category_id="+category_id+"&sort="+sort4url;
            //out.println(url_str);
            //out.print("\n\n");

            //URLからjsonを受けとってFileバッファに渡す
            URL url = new URL(url_str);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
            StringBuffer responseBuffer = new StringBuffer();
            
            //URLのjsonからテキストを読み込む処理
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                responseBuffer.append(line);
            }
            reader.close();
            
            //取得したjsonテキストを文字列に変換
            String jsonText = responseBuffer.toString();
            //out.println(jsonText);
            
            //jsonテキストをjsonic使ってパース
            Map<String, Map<String, Object>> json = JSON.decode(jsonText);
            
            //検索ヒットした結果のうち、返ってきた検索数を格納
            //RAW:課題の仕様だと上位10件みたいなんで値は10にした方がいいかも？
            int numberOfResult = Integer.parseInt( json.get("ResultSet").get("totalResultsReturned").toString() );
            //out.println(numberOfResult);
            
            //"Resultset"->"0"->"Result"以降のオブジェクトを保存
            Map<String, Object> result = 
                            ((Map<String, Object>) ((Map<String, Object>) json.get("ResultSet").get("0")).get("Result"));
//            String name = ((Map<String, Object>) result.get(String.valueOf(0))).get("Name").toString();
            //out.println(name);
            //検索結果を変数:jsonへ格納
//            List<JsonSearchElements> jseList = new LinkedList<JsonSearchElements>();
//            if (numberOfResult == 0) {
//                //
//                //検索結果なしと表示させてtopに戻る予定
//            }
//            if (numberOfResult != 0) {
//                
//                for (int i = 0; i < numberOfResult; i++) {
//                    //ResultSet->0->Result->i配下を格納
//                    Map<String, Object> result = 
//                            ((Map<String, Object>) ((Map<String, Map<String, Object>>) json.get("ResultSet").get("0")).get("Result").get(String.valueOf(i)));
//
//                    String name = result.get("Name").toString();
//                    String imageUrl = ((Map<String, Object>) result.get("Image")).get("Medium").toString();
//                    String price = ((Map<String, Object>) result.get("Price")).get("_value").toString();
//
//                    JsonSearchElements jse = new JsonSearchElements();
//                    jse.setName(name);
//                    jse.setImageUrl(imageUrl);
//                    jse.setPrice(price);
//                    jseList.add(jse);
//
//                    //out.print("\n");
//                    //out.println(jse.getName());
//                    //out.println(jse.getUri());
//                    //out.println(jse.getPrice());
//                }
////                for(JsonSearchElements jse : jseList) {
////                    out.println(jse.getName());
////                    out.println(jse.getImageUrl());
////                    out.println(jse.getPrice());
////                    out.print("\n");
////                }
//            }
            
            
            //[RAW] セッションに検索条件を設定
            HttpSession session = request.getSession();
            session.setAttribute("query", query);
            session.setAttribute("sort", sort);
            session.setAttribute("category_id", category_id);
            session.setAttribute("RESULT", result);
            session.setAttribute("NumOfResults", numberOfResult);
            
            request.getRequestDispatcher("/search.jsp").forward(request, response);  
        }catch(Exception e){
            //何らかの理由で失敗したらエラーページにエラー文を渡して表示。想定は不正なアクセスとDBエラー
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
        
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
