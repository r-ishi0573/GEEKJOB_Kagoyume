package kagoyume;

import base.DBManager;
import com.sun.org.apache.bcel.internal.generic.AALOAD;
import java.sql.Array;
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
 * INFO:DAOを複数作るのが適当かは不明
 */
public class BuyDataDAO {
    
    //インスタンスオブジェクトを返却させてコードの簡略化
    public static BuyDataDAO getInstance(){
        return new BuyDataDAO();
    }
    
    /**
     * データの挿入処理を行う。現在時刻は挿入直前に生成
     * @param bd 対応したデータを保持しているJavaBeans
     * @throws SQLException 呼び出し元にcatchさせるためにスロー 
     */
    public void insert(BuyDataDTO bd) throws SQLException{
        Connection con = null;
        PreparedStatement st = null;
        try{
            con = DBManager.getConnection();
            st =  con.prepareStatement("INSERT INTO buy_t(userID,itemCode,type,buyDate) VALUES(?,?,?,?)");
            st.setInt(1, bd.getUserID());
            st.setString(2, bd.getItemCode());
            st.setInt(3, bd.getType());
            st.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
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
     * ユーザーIDによる1件のデータの検索処理を行う。
     * @param bd 対応したデータを保持しているJavaBeans
     * @throws SQLException 呼び出し元にcatchさせるためにスロー 
     * @return 検索結果
     */
    public ArrayList<String> searchItemCodeByID(int userID) throws SQLException{
        Connection con = null;
        PreparedStatement st = null;
        try{
            con = DBManager.getConnection();
            
            String sql = "SELECT itemCode FROM buy_t WHERE userID = ?";
            
            st =  con.prepareStatement(sql);
            st.setInt(1, userID);
            
            ResultSet rs = st.executeQuery();
            ArrayList<String> itemCodeArray = new ArrayList<String>();
            while(rs.next()){
                itemCodeArray.add(rs.getString(1));
            }
            
            System.out.println("searchByID completed");

            return itemCodeArray;
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
//     * データの更新処理を行う。現在時刻は挿入直前に生成
//     * @param bd 対応したデータを保持しているJavaBeans
//     * @throws SQLException 呼び出し元にcatchさせるためにスロー 
//     */
//    public void update(BuyDataDTO bd) throws SQLException{
//        Connection con = null;
//        PreparedStatement st = null;
//        try{
//            con = DBManager.getConnection();
//            st =  con.prepareStatement("UPDATE user_t SET name=?,birthday=?,tell=?,type=?,comment=?,newDate=? WHERE userID=?");
//            st.setString(1, bd.getName());
//            st.setDate(2, new java.sql.Date(bd.getBirthday().getTime()));//指定のタイムスタンプ値からSQL格納用のDATE型に変更
//            st.setString(3, bd.getTell());
//            st.setInt(4, bd.getType());
//            st.setString(5, bd.getComment());
//            st.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
//            st.setInt(7, bd.getUserID());
//            st.executeUpdate();
//            System.out.println("update completed");
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
//    /**
//     * データの削除処理を行う。現在時刻は挿入直前に生成
//     * @param id 削除対象のuserID
//     * @throws SQLException 呼び出し元にcatchさせるためにスロー 
//     */
//    public void delete(int id) throws SQLException{
//        Connection con = null;
//        PreparedStatement st = null;
//        try{
//            con = DBManager.getConnection();
//            st =  con.prepareStatement("DELETE FROM user_t WHERE userID=?");
//            st.setInt(1, id);
//            st.executeUpdate();
//            System.out.println("update completed");
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
//    
//    /**
//     * データの検索処理を行う。
//     * @param bd 対応したデータを保持しているJavaBeans
//     * @throws SQLException 呼び出し元にcatchさせるためにスロー 
//     * @return 検索結果
//     */
//    
//    //修正点
//    //返り値をDTOの配列にした
//    //public BuyDataDTO search(BuyDataDTO bd) throws SQLException{
//    public ArrayList<BuyDataDTO> search(BuyDataDTO bd) throws SQLException{
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
//            if (!bd.getName().equals("")) {
//                sql += " WHERE name like ?";
//                flag = true;
//                //プレースホルダ用
//                stateOfName = 1;
//            }
//            if (bd.getBirthday()!=null) {
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
//            if (bd.getType()!=0) {
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
//            st.setString(1, "%"+bd.getName()+"%");
//            st.setString(2, "%"+ new SimpleDateFormat("yyyy").format(bd.getBirthday())+"%");
//            st.setInt(3, bd.getType());
//            */
//            //プレースホルダの調整
//            if(stateOfName != 0) {
//                st.setString(stateOfName, "%"+bd.getName()+"%");
//            }
//            if(stateOfBirth != 0) {
//                st.setString(stateOfBirth, "%"+ new SimpleDateFormat("yyyy").format(bd.getBirthday())+"%");
//            }
//            if(stateOfType != 0) {
//                st.setInt(stateOfType, bd.getType());
//            }
//            
//            
//            ResultSet rs = st.executeQuery();
//            //BuyDataDTO resultUd = new BuyDataDTO();
//            ArrayList<BuyDataDTO> resultUdList = new ArrayList<BuyDataDTO>();
//
//            /*
//            rs.next();
//            BuyDataDTO resultUd = new BuyDataDTO();
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
//                BuyDataDTO resultUd = new BuyDataDTO();
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
//    
//    /**
//     * ユーザーIDによる1件のデータの検索処理を行う。
//     * @param bd 対応したデータを保持しているJavaBeans
//     * @throws SQLException 呼び出し元にcatchさせるためにスロー 
//     * @return 検索結果
//     */
//    public BuyDataDTO searchByID(BuyDataDTO bd) throws SQLException{
//        Connection con = null;
//        PreparedStatement st = null;
//        try{
//            con = DBManager.getConnection();
//            
//            String sql = "SELECT * FROM user_t WHERE userID = ?";
//            
//            st =  con.prepareStatement(sql);
//            st.setInt(1, bd.getUserID());
//            
//            ResultSet rs = st.executeQuery();
//            rs.next();
//            BuyDataDTO resultUd = new BuyDataDTO();
//            resultUd.setUserID(rs.getInt(1));
//            resultUd.setName(rs.getString(2));
//            resultUd.setBirthday(rs.getDate(3));
//            resultUd.setTell(rs.getString(4));
//            resultUd.setType(rs.getInt(5));
//            resultUd.setComment(rs.getString(6));
//            resultUd.setNewDate(rs.getTimestamp(7));
//            
//            System.out.println("searchByID completed");
//
//            return resultUd;
//        }catch(SQLException e){
//            System.out.println(e.getMessage());
//            throw new SQLException(e);
//        }finally{
//            if(con != null){
//                con.close();
//            }
//        }

//    }
    
    /**
     * 入力されたユーザ情報があるかチェック。主にログイン時に使う。メソッド名が微妙...
     * @param name
     * @param password ログインページでフォームから受け取る入力
     * @throws SQLException 呼び出し元にcatchさせるためにスロー 
     * @return 一致:true 一致しない:false
     */
//    public boolean compareNamePass(String name, String password) throws SQLException{
//        Connection con = null;
//        PreparedStatement st = null;
//        try{
//            con = DBManager.getConnection();
//            
//            String sql = "SELECT * FROM user_t WHERE name = ? && password = ?";
//            
//            st =  con.prepareStatement(sql);
//            st.setString(1, name);
//            st.setString(2, password);
//            
//            ResultSet rs = st.executeQuery();
//            
//            //検索でHitした場合(該当するユーザ&passがあった)
//            if(rs.next() == true) {
//                return true;
//            }
//            //検索でhitしなかった場合(該当ユーザ&passなし)
//            else {
//                return false;
//            }
//            
//            
//        }catch(SQLException e){
//            System.out.println(e.getMessage());
//            throw new SQLException(e);
//        }finally{
//            if(con != null){
//                con.close();
//            }
//       }
//
//    }
}
