/**
 * RAW:
 * login.jspから入力を受け取ってログイン処理するクラス
 * DBを検索してログインする処理まではOK
 */

package kagoyume;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Login_Complete extends HttpServlet {

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
            
            //ユーザ名、パスワード入力を受け取る
            //TODO:入力が空列の場合の処理はどうするか？
            String name     = request.getParameter("name");
            String password = request.getParameter("password");
            
            //out.println(name);out.println(password);
            
            //DBのテーブルに一致するユーザ,passがあるか調べる
            UserDataDTO userResult = UserDataDAO .getInstance().compareNamePass(name, password);
            
            //ログインOKの場合
            if(userResult != null) {
                //ログイン中であることを示す情報をセッションに渡す
                session.setAttribute("Login", userResult);
                request.setAttribute("LoginDialog", "ログインしました");
                String transURL = (String)session.getAttribute("URL");
                request.getRequestDispatcher(transURL).forward(request, response);
                //request.getRequestDispatcher(transURL).forward(request, response);
            }
            
            //ログインNGの場合
            if(userResult == null) {
                //out.println("ログインできません");
                request.setAttribute("LoginFailed", "ログインできません。再度入力してください");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
            
            
                      
//            遷移先はログインページに来る前のページだから、一旦リンクを退避させておかないといけない
//            ただ、そうなるとログインページに飛ぶ前の状態を保持しとかなければいけないか...?
            //request.getRequestDispatcher("/login.jsp").forward(request, response);
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
