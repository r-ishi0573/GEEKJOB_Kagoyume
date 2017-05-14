/**
 *
 */

package kagoyume;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Registration_Complete extends HttpServlet {

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
        
        //セッションスタート
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");//リクエストパラメータの文字コードをUTF-8に変更
        response.setContentType("text/html; charset=UTF-8"); 
        PrintWriter out = response.getWriter();
        
        try{
            
//            //アクセスルートチェック
//            String accesschk = request.getParameter("ac");
//            if(accesschk ==null || (Integer)session.getAttribute("ac")!=Integer.parseInt(accesschk)){
//                throw new Exception("不正なアクセスです");
//            }
            
            //セッションから登録用DTOを受け取る
            UserDataDTO userdata = (UserDataDTO)session.getAttribute("udd");
            
//            out.println(userdata.getName());
//            out.println(userdata.getPassword());
//            out.println(userdata.getMail());
//            out.println(userdata.getAddress()); 
            
            //DTOオブジェクトにマッピング。DB専用のパラメータに変換
            //UserDataDTO userdata = new UserDataDTO();
            //udb.UD2DTOMapping(userdata);
//            userdata.setName("bbb");
//            userdata.setPassword("222");
//            userdata.setMail("yyy");
//            userdata.setAddress("888");            
            
            //DBへデータの挿入
            UserDataDAO .getInstance().insert(userdata);
            
            //成功したのでセッションの値を削除
            //session.invalidate();
            
            //結果画面での表示用に入力パラメータ―をリクエストパラメータとして保持
            request.setAttribute("udd", userdata);
            
            request.getRequestDispatcher("/registration_complete.jsp").forward(request, response);
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
