package base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import kagoyume.DefineUtil;

public class DBManager {
    public static Connection getConnection(){
        Connection con = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            //RAW:とりあえずパスワードとかは直書き
            con = DriverManager.getConnection("jdbc:mysql://localhost:8889/kagoyume_db","user","user");
            System.out.println("DBConnected!!");
            return con;
        }catch(ClassNotFoundException e){
            throw new IllegalMonitorStateException();
        } catch (SQLException e) {
            throw new IllegalMonitorStateException();
        }
    }
}
