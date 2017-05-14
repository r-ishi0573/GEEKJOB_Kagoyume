/**
 * RAW:今のとこmy_history.jspに飛ぶだけのクラス
 * TODO:JanCodeからYahooAPIを用いて商品情報を検索
 * 
 */

package kagoyume;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

public class My_History extends HttpServlet {

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

            //UserDataDTO loginUser = (UserDataDTO)session.getAttribute("Login");
            
            //int userID = loginUser.getUserID();
            
            //TODO:合計金額も渡した方がいいかも？
            
            //userIDでbuy_tを検索し、該当userIDが購入した商品の商品コードを配列へ追加
            int userID = Integer.parseInt(request.getParameter("UserID"));
            ArrayList<String> itemCodeList = new ArrayList<String>();
            itemCodeList = BuyDataDAO .getInstance().searchItemCodeByID(userID);
                        
            //商品コードをJSEにセットし、そのJSEをJSEリストへ追加
            ArrayList<JsonSearchElements> jseList = new ArrayList<JsonSearchElements>();
            for(String itemCode : itemCodeList) {
                JsonSearchElements jse = new JsonSearchElements();
                jse.setItemCode(itemCode);
                jseList.add(jse);
                //out.print(itemCode);
                //out.print("<br>");
            }

            //JSEリストの各JSEの商品コードを参照し、APIから情報を取得して値をセット
            for(JsonSearchElements jse : jseList) {
                jse.searchByJanCode(jse.getItemCode());
                //out.println(jse.getName() + "\n" + jse.getImageUrl() +  "\n"+ jse.getPrice() + "\n");  
            }
            
            request.setAttribute("HistoryJseList", jseList);
            
            request.getRequestDispatcher("/my_history.jsp").forward(request, response);  
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
