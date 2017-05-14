/**
 * serch.jspから受け取った値をitem.jspへ渡すだけでいいかな?
 * もしかすると別ページから戻ってくる時のためにセッションへ値を渡すかは不明
 * 検索Hitした"i"番目を示すindex(変数:numberOfHit)をitem.jspへ渡すだけ
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

public class Item extends HttpServlet {

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
 
            HttpSession session = request.getSession();
            request.setCharacterEncoding("UTF-8");//リクエストパラメータの文字コードをUTF-8に変更

            response.setContentType("text/html; charset=UTF-8"); 
            response.setContentType("application/json; charset=UTF-8");
            PrintWriter out = response.getWriter();

            //
            //入力を取得する処理
            //
            //検索Hitした"i"番目を示す。index空でないかチェック
            String numberOfHit = !(request.getParameter("NumberOfHit").isEmpty()) ? request.getParameter("NumberOfHit") : "";
            //out.println(numberOfHit);
         
            //"Resultset"->"0"->"Result"以降のオブジェクトを保存
            Map<String, Object> result = (Map<String, Object>)session.getAttribute("RESULT");
            Map<String, Object> hitOfResults  = ((Map<String, Object>) result.get(numberOfHit));
            
            //RAW: セッションではなくスコープへ渡す
            request.setAttribute("NumberOfHit", numberOfHit);
            
            request.getRequestDispatcher("/item.jsp").forward(request, response);  
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
