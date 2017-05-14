/**
 * 会員登録確認画面
 * 入力をDB用の変数UserDataDTOに格納する。
 * 格納処理まではOK
 */
package kagoyume;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class My_Update_Result extends HttpServlet {

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
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try{
            
//            //アクセスルートチェック
//            String accesschk = request.getParameter("ac");
//            if(accesschk ==null || (Integer)session.getAttribute("ac")!=Integer.parseInt(accesschk)){
//                throw new Exception("不正なアクセスです");
//            }
            
            //フォームからの入力を取得して、JavaBeansに格納
            UserDataDTO udd = new UserDataDTO();
            udd.setName(request.getParameter("name"));
            udd.setPassword(request.getParameter("password"));
            udd.setMail(request.getParameter("mail"));
            udd.setAddress(request.getParameter("address"));
            
            out.println(udd.getName());out.println(udd.getPassword());
            out.println(udd.getMail());out.println(udd.getAddress());
            
            //フォームからhiddenでユーザーIDを受け取りbeansへ
            int userID = Integer.parseInt(request.getParameter("UserID"));
            udd.setUserID(userID);
            //out.print(userID);

            //DBへデータの挿入
            UserDataDAO .getInstance().update(udd);
            
            //DBへのデータ挿入がうまくいけば今ログインしているユーザ情報も変更
            UserDataDTO loginUserData = (UserDataDTO)session.getAttribute("Login");
            loginUserData.setName(udd.getName());
            loginUserData.setPassword(udd.getPassword());
            loginUserData.setMail(udd.getMail());
            loginUserData.setAddress(udd.getAddress());
            
            //ユーザー情報群をセッションに格納
            session.setAttribute("Login", loginUserData);
            //System.out.println("Session updated!!");
            
            request.getRequestDispatcher("/my_update_result.jsp").forward(request, response);
        }catch(Exception e){
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
