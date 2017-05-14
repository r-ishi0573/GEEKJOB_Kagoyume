/**
 * RAW:
 * 単独実行での購入情報のデータベース入力は動作確認OK
 * まだ他ファイルとの連携はなし
 * TODO:user_tへtotalの書き込みが必要
 */

package kagoyume;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

public class Buy_Complete extends HttpServlet {

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
            int type = 0, price = 0;
            
            //発送方法の入力受け取りと入力チェック
            final String strType = request.getParameter("Type");
            if(strType  != null) {
                type = Integer.parseInt(strType);
            }
            out.println(type);
            out.print("<br>");
            
            final String strPrice = request.getParameter("Price");
            if(strPrice  != null) {
                price = Integer.parseInt(strPrice);
            }
            out.println(price);
            
            //セッションから登録用DTOを受け取る
            //UserDataDTO userdata = (UserDataDTO)session.getAttribute("udd");
            
//            out.println(userdata.getName());
//            out.println(userdata.getPassword());
//            out.println(userdata.getMail());
//            out.println(userdata.getAddress()); 
            
//            //DTOオブジェクトにマッピング。DB専用のパラメータに変換
//            BuyDataDTO buydata = new BuyDataDTO();
//            //udb.UD2DTOMapping(userdata);
//            buydata.setUserID(1);
//            buydata.setItemCode("222");
//            buydata.setType(1);

            // RAW
            if( (UserDataDTO)session.getAttribute("Login") == null ) {
                request.setAttribute("error", "ログインしていないとこれ以上進めないようにしてる");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
            
            //ログイン用セッション変数を保持
            UserDataDTO userdata = (UserDataDTO)session.getAttribute("Login");
            //ユーザID、カートの中身、発送方法をBuyDataDTOへ変換しDBへ挿入
            ArrayList<JsonSearchElements> jseList = (ArrayList<JsonSearchElements>)session.getAttribute("JseList");
            for(JsonSearchElements jse : jseList) {
                BuyDataDTO buydata = new BuyDataDTO();
                //XXXX 今のとこログインしてなくてもここまで飛べるのでgetUserID呼び出すとヤバイ！！
                //ログインしていないとカートページでログインページに転送されるようにしたので大丈夫なはず...
                buydata.setUserID(userdata.getUserID());
                buydata.setItemCode(jse.getItemCode());
                buydata.setType(type);
                BuyDataDAO .getInstance().insert(buydata);
            }
            
            //合計金額をユーザーのtotalへ入力
            userdata.setTotal(userdata.getTotal() + price);
            UserDataDAO .getInstance().updateTotal(userdata);
            
            //購入が完了したのでカート情報を削除
            session.removeAttribute("JseList"); //NOTICE:これ必要ないかも?
            
            //結果画面での表示用に入力パラメータ―をリクエストパラメータとして保持
            //request.setAttribute("udd", userdata);
            
            request.getRequestDispatcher("/buy_complete.jsp").forward(request, response);
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
