package kagoyume;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 * ユーザー情報を持ちまわるJavaBeans
 * データベースのカラムと型に対応させている(DTO)。データの挿入、取り出しどちらにも便利
 * NOTICE:UserData.javaは作らずDTOだけで十分かどうかは現在検討中
 */
public class UserDataDTO {
    private int userID;
    private String name;
    private String password;
    private String mail;
    private String address;
    private int total;
    private Timestamp newDate;
    private int deleteFlg;

    public UserDataDTO() {
        userID = 0;
        name = "";
        password = "";
        mail = "";
        address = "";
        total = 0;
        //newDate = null;
        deleteFlg = 0;
    }
    
    /**
     * @return the userID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        //空文字(未入力)の場合空文字をセット
        if(name.trim().length()==0){
            this.name = "";
        }else{
            this.name = name;
        }
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        //空文字(未入力)の場合空文字をセット
        if(password.trim().length()==0){
            this.password = "";
        }else{
            this.password = password;
        }
    }

    /**
     * @return the mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * @param mail the mail to set
     */
    public void setMail(String mail) {
        //空文字(未入力)の場合空文字をセット
        if(mail.trim().length()==0){
            this.mail = "";
        }else{
            this.mail = mail;
        }
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        //空文字(未入力)の場合空文字をセット
        if(address.trim().length()==0){
            this.address = "";
        }else{
            this.address = address;
        }
    }

    /**
     * @return the total
     */
    public int getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    //NOTICE:引数がStringになる可能性は？
    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * @return the newDate
     */
    public Timestamp getNewDate() {
        return newDate;
    }

    /**
     * @param newDate the newDate to set
     */
    public void setNewDate(Timestamp newDate) {
        this.newDate = newDate;
    }

    /**
     * @return the deleteFlg
     */
    public int getDeleteFlg() {
        return deleteFlg;
    }

    /**
     * @param deleteFlg the deleteFlg to set
     */
    public void setDeleteFlg(int deleteFlg) {
        this.deleteFlg = deleteFlg;
    }
    
    //前の課題の空列判定を移植
    public ArrayList<String> chkproperties(){
        ArrayList<String> chkList = new ArrayList<String>();
        if(this.name.equals("")){
            chkList.add("name");
        }
        if(this.password.equals("")){
            chkList.add("password");
        }
        if(this.mail.equals("")){
            chkList.add("mail");
        }
        if(this.address.equals("")){
            chkList.add("address");
        }
        
        return chkList;
    }
}
