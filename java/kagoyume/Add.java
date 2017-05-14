/**
 * RAW:
 * 検索Hitした"i"番目を示すindex(変数:numberOfHit)を受け取り、
 * 必要な情報を抽出してセッションへ挿入するクラス
 */

package kagoyume;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

public class Add extends HttpServlet {

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
            response.setContentType("text/html; charset=UTF-8"); 
        try{
 
            HttpSession session = request.getSession();
            request.setCharacterEncoding("UTF-8");//リクエストパラメータの文字コードをUTF-8に変更
            PrintWriter out = response.getWriter();

            //
            //入力を取得する処理
            //
            //検索Hitした"i"番目を示すindex取得。index空でないかチェック
            String numberOfHit = !(request.getParameter("NumberOfHit").isEmpty()) ? request.getParameter("NumberOfHit") : "";
            //out.println(numberOfHit);
         
            //"Resultset"->"0"->"Result"以降のオブジェクトを保存
            Map<String, Object> result = (Map<String, Object>)session.getAttribute("RESULT");
            //"Result"->"{i}"以降のオブジェクトを保存
            Map<String, Object> hitOfResults  = ((Map<String, Object>) result.get(numberOfHit));
            
            //TODO:検索結果のオブジェクトから必要な要素を(JSEへ?)抽出する操作
            JsonSearchElements jse = new JsonSearchElements();
            
            jse.setName(hitOfResults.get("Name").toString());
            jse.setImageUrl(((Map<String, Object>) hitOfResults.get("Image")).get("Medium").toString());
            String price = ((Map<String, Object>) hitOfResults.get("Price")).get("_value").toString();
            jse.setPrice(Integer.parseInt(price));
            jse.setItemCode(hitOfResults.get("Code").toString());
            
            //out.println(jse.getName()+"<br>"+jse.getImageUrl()+"<br>"+jse.getPrice()+"<br>"+jse.getItemCode());
            
            //TODO:抽出した要素をセッションorクッキーへ挿入する操作
//                   カート用の配列作ってそこに保存するか？
//                   JSE型の配列用意してJSE宣言->Resultから値抽出 みたいな?
            ArrayList <JsonSearchElements> jseList = (ArrayList<JsonSearchElements>)session.getAttribute("JseList");
            //カートに何もない場合インスタンス作成
            if(jseList == null) {
                jseList = new ArrayList<JsonSearchElements>();
            }
            
            jseList.add(jse);
            
            session.setAttribute("JseList", jseList);
            
            request.getRequestDispatcher("/add.jsp").forward(request, response);  
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
