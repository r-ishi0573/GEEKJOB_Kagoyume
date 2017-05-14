package kagoyume;

import base.DBManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * ユーザー情報を格納するテーブルに対しての操作処理を包括する
 * DB接続系はDBManagerクラスに一任
 * 基本的にはやりたい1種類の動作に対して1メソッド
 * 前の課題のマネジメントシステムから流用。アクセサメソッドの使い方を一部の変更
 * ・compareNamePass()を追加
 */
public class UserDataDAO {
    
    //インスタンスオブジェクトを返却させてコードの簡略化
    public static UserDataDAO getInstance(){
        return new UserDataDAO();
    }
    
    /**
     * データの挿入処理を行う。現在時刻は挿入直前に生成
     * @param ud 対応したデータを保持しているJavaBeans
     * @throws SQLException 呼び出し元にcatchさせるためにスロー 
     */
    public void insert(UserDataDTO ud) throws SQLException{
        Connection con = null;
        PreparedStatement st = null;
        try{
            con = DBManager.getConnection();
            st =  con.prepareStatement("INSERT INTO user_t(name,password,mail,address,total,newDate) VALUES(?,?,?,?,?,?)");
            st.setString(1, ud.getName());
            st.setString(2, ud.getPassword());
            st.setString(3, ud.getMail());
            st.setString(4, ud.getAddress());
            st.setInt(5, 0);
            st.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            st.executeUpdate();
            System.out.println("insert completed");
        }catch(SQLException e){
            System.out.println(e.getMessage());
            throw new SQLException(e);
        }finally{
            if(con != null){
                con.close();
            }
        }

    }
    
    /**
     * データの更新処理を行う。現在時刻は挿入直前に生成
     * @param ud 対応したデータを保持しているJavaBeans
     * @throws SQLException 呼び出し元にcatchさせるためにスロー 
     */
    public void update(UserDataDTO ud) throws SQLException{
        Connection con = null;
        PreparedStatement st = null;
        try{
            con = DBManager.getConnection();
            st =  con.prepareStatement("UPDATE user_t SET name=?,password=?,mail=?,address=? WHERE userID=? && deleteFlg=0");
            st.setString(1, ud.getName());
            st.setString(2, ud.getPassword());
            st.setString(3, ud.getMail());
            st.setString(4, ud.getAddress());
            st.setInt(5, ud.getUserID());
            st.executeUpdate();
            System.out.println("update completed");
        }catch(SQLException e){
            System.out.println(e.getMessage());
            throw new SQLException(e);
        }finally{
            if(con != null){
                con.close();
            }
        }

    }
    
    /**
     * データの削除処理を行う。実際はdeleteFlgを1にするのみ
     * @param id 削除対象のuserID
     * @throws SQLException 呼び出し元にcatchさせるためにスロー 
     */
    public void delete(int id) throws SQLException{
        Connection con = null;
        PreparedStatement st = null;
        try{
            con = DBManager.getConnection();
            st =  con.prepareStatement("UPDATE user_t SET deleteFlg=1 WHERE userID=? && deleteFlg=0");
            st.setInt(1, id);
            st.executeUpdate();
            System.out.println("update completed");
        }catch(SQLException e){
            System.out.println(e.getMessage());
            throw new SQLException(e);
        }finally{
            if(con != null){
                con.close();
            }
        }

    }
    
//    /**
//     * データの検索処理を行う。
//     * @param ud 対応したデータを保持しているJavaBeans
//     * @throws SQLException 呼び出し元にcatchさせるためにスロー 
//     * @return 検索結果
//     */
//    
//    //修正点
//    //返り値をDTOの配列にした
//    //public UserDataDTO search(UserDataDTO ud) throws SQLException{
//    public ArrayList<UserDataDTO> search(UserDataDTO ud) throws SQLException{
//        Connection con = null;
//        PreparedStatement st = null;
//        try{
//            con = DBManager.getConnection();
//            
//            String sql = "SELECT * FROM user_t";
//            
//            /** 
//             * SQL文の変更によってプレースホルダへ代入する値を制御する処理を追加
//             * stateOf~変数によって制御する
//             */ 
//            int stateOfName = 0, stateOfBirth = 0, stateOfType = 0;
//            boolean flag = false;
//            if (!ud.getName().equals("")) {
//                sql += " WHERE name like ?";
//                flag = true;
//                //プレースホルダ用
//                stateOfName = 1;
//            }
//            if (ud.getBirthday()!=null) {
//                if(!flag){
//                    sql += " WHERE birthday like ?";
//                    flag = true;
//                    //プレースホルダ用
//                    stateOfBirth = 1;
//                }else{
//                    sql += " AND birthday like ?";
//                    //プレースホルダ用
//                    stateOfBirth = 2;
//                }
//            }
//            if (ud.getType()!=0) {
//                if(!flag){
//                    sql += " WHERE type like ?";
//                    //プレースホルダ用
//                    stateOfType = 1;
//                }else{
//                    sql += " AND type like ?";
//                    //プレースホルダの順番を調整
//                    //Name,Birth両方値がある場合はTypeが3番目になる
//                    if(stateOfBirth == 2) {
//                        stateOfType = 3;
//                    }
//                    else {
//                        stateOfType = 2;
//                    }
//                }
//
//            }
//
//            st =  con.prepareStatement(sql);
//            
//            /*
//            st.setString(1, "%"+ud.getName()+"%");
//            st.setString(2, "%"+ new SimpleDateFormat("yyyy").format(ud.getBirthday())+"%");
//            st.setInt(3, ud.getType());
//            */
//            //プレースホルダの調整
//            if(stateOfName != 0) {
//                st.setString(stateOfName, "%"+ud.getName()+"%");
//            }
//            if(stateOfBirth != 0) {
//                st.setString(stateOfBirth, "%"+ new SimpleDateFormat("yyyy").format(ud.getBirthday())+"%");
//            }
//            if(stateOfType != 0) {
//                st.setInt(stateOfType, ud.getType());
//            }
//            
//            
//            ResultSet rs = st.executeQuery();
//            //UserDataDTO resultUd = new UserDataDTO();
//            ArrayList<UserDataDTO> resultUdList = new ArrayList<UserDataDTO>();
//
//            /*
//            rs.next();
//            UserDataDTO resultUd = new UserDataDTO();
//            resultUd.setUserID(rs.getInt(1));
//            resultUd.setName(rs.getString(2));
//            resultUd.setBirthday(rs.getDate(3));
//            resultUd.setTell(rs.getString(4));
//            resultUd.setType(rs.getInt(5));
//            resultUd.setComment(rs.getString(6));
//            resultUd.setNewDate(rs.getTimestamp(7));
//            */
//            //UserDTOの配列にDTOを保持させる
//            while(rs.next()) {
//                UserDataDTO resultUd = new UserDataDTO();
//                resultUd.setUserID(rs.getInt(1));
//                resultUd.setName(rs.getString(2));
//                resultUd.setBirthday(rs.getDate(3));
//                resultUd.setTell(rs.getString(4));
//                resultUd.setType(rs.getInt(5));
//                resultUd.setComment(rs.getString(6));
//                resultUd.setNewDate(rs.getTimestamp(7));
//                resultUdList.add(resultUd);
//            }
//            System.out.println("search completed");
//
//            //return resultUd;
//            //返り値変更に伴い変数変更
//            return resultUdList;
//        }catch(SQLException e){
//            System.out.println(e.getMessage());
//            throw new SQLException(e);
//        }finally{
//            if(con != null){
//                con.close();
//            }
//        }
//
//    }
   
    /**
     * ユーザーIDによる1件のデータの検索処理を行う。
     * @param ud 対応したデータを保持しているJavaBeans
     * @throws SQLException 呼び出し元にcatchさせるためにスロー 
     * @return 検索結果
     */
    public UserDataDTO searchByID(UserDataDTO ud) throws SQLException{
        Connection con = null;
        PreparedStatement st = null;
        try{
            con = DBManager.getConnection();
            
            String sql = "SELECT * FROM user_t WHERE userID = ?";
            
            st =  con.prepareStatement(sql);
            st.setInt(1, ud.getUserID());
            
            ResultSet rs = st.executeQuery();
            rs.next();
            UserDataDTO resultUd = new UserDataDTO();
            resultUd.setUserID(rs.getInt(1));
            resultUd.setName(rs.getString(2));
            resultUd.setPassword(rs.getString(3));
            resultUd.setMail(rs.getString(4));
            resultUd.setAddress(rs.getString(5));
            resultUd.setTotal(rs.getInt(6));
            resultUd.setNewDate(rs.getTimestamp(7));
            resultUd.setDeleteFlg(rs.getInt(8));
            
            System.out.println("searchByID completed");

            return resultUd;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            throw new SQLException(e);
        }finally{
            if(con != null){
                con.close();
            }
        }
   }
    
    /**
     * 入力されたユーザ情報があるかチェック。主にログイン時に使う。メソッド名が微妙...
     * @param name
     * @param password ログインページでフォームから受け取る入力
     * @throws SQLException 呼び出し元にcatchさせるためにスロー 
     * @return 一致:true 一致しない:false
     */
    public UserDataDTO compareNamePass(String name, String password) throws SQLException{
        Connection con = null;
        PreparedStatement st = null;
        try{
            con = DBManager.getConnection();
            
            //deleteFlagのチェックを追加
            String sql = "SELECT * FROM user_t WHERE name = ? && password = ? && deleteFlg = 0";
            
            st =  con.prepareStatement(sql);
            st.setString(1, name);
            st.setString(2, password);
            
            ResultSet rs = st.executeQuery();
            
            UserDataDTO resultUd = new UserDataDTO();

            //検索でHitした場合(該当するユーザ&passがあった)
            if(rs.next() == true) {

            resultUd.setUserID(rs.getInt(1));
            resultUd.setName(rs.getString(2));
            resultUd.setPassword(rs.getString(3));
            resultUd.setMail(rs.getString(4));
            resultUd.setAddress(rs.getString(5));
            resultUd.setTotal(rs.getInt(6));
            resultUd.setNewDate(rs.getTimestamp(7));
            resultUd.setDeleteFlg(rs.getInt(8));

            
            //return true;
            }
            //検索でhitしなかった場合(該当ユーザ&passなし)
            else {
                resultUd = null;
                //return false;
            }
            
            return resultUd;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            throw new SQLException(e);
        }finally{
            if(con != null){
                con.close();
            }
       }

    }
    
    /**
     * 合計金額の更新処理を行う。現在時刻は更新しない
     * @param ud 対応したデータを保持しているJavaBeans
     * @throws SQLException 呼び出し元にcatchさせるためにスロー 
     */
    public void updateTotal(UserDataDTO ud) throws SQLException{
        Connection con = null;
        PreparedStatement st = null;
        try{
            con = DBManager.getConnection();
            st =  con.prepareStatement("UPDATE user_t SET total=? WHERE userID=?");
            st.setInt(1, ud.getTotal());
            st.setInt(2, ud.getUserID());
            st.executeUpdate();
            System.out.println("update completed");
        }catch(SQLException e){
            System.out.println(e.getMessage());
            throw new SQLException(e);
        }finally{
            if(con != null){
                con.close();
            }
        }

    }
}
