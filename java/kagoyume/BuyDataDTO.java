package kagoyume;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 * ユーザー情報を持ちまわるJavaBeans
 * データベースのカラムと型に対応させている(DTO)。データの挿入、取り出しどちらにも便利
 * NOTICE:BuyData.javaは作らずDTOだけで十分かどうかは現在検討中
 *        int型メンバ変数のアクセサの引数がStringになる可能性は？
 */
public class BuyDataDTO {
    private int buyID;
    private int userID;
    private String itemCode;
    private int type;
    private Timestamp buyDate;

    public BuyDataDTO() {
        buyID = 0;
        userID = 0;
        itemCode = "";
        type = 0;
        //buyDate = null;
    }
    
    /**
     * @return the buyID
     */
    public int getBuyID() {
        return buyID;
    }

    /**
     * @param buyID the userID to set
     */
    public void setBuyID(int buyID) {
        this.buyID = buyID;
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
     * @return the itemCode
     */
    public String getItemCode() {
        return itemCode;
    }

    /**
     * @param itemCode the name to set
     */
    public void setItemCode(String itemCode) {
        //空文字(未入力)の場合空文字をセット
        if(itemCode.trim().length()==0){
            this.itemCode = "";
        }else{
            this.itemCode = itemCode;
        }
    }
    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the userID to set
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @return the buyDate
     */
    public Timestamp getBuyDate() {
        return buyDate;
    }

    /**
     * @param buyDate the newDate to set
     */
    public void setBuyDate(Timestamp buyDate) {
        this.buyDate = buyDate;
    }
    
    //前の課題の空列判定を移植
    public ArrayList<String> chkproperties(){
        ArrayList<String> chkList = new ArrayList<String>();
        if(this.itemCode.equals("")){
            chkList.add("name");
        }
        
        if(this.type == 0){
            chkList.add("type");
        }
        
        return chkList;
    }
}
