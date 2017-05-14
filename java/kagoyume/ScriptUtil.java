/**
 *
 * よく使うユーザ定義関数のクラス
 *
 *
 */
package kagoyume;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class ScriptUtil {

    

    
    /**
     * java用のhtmlspecialchars
     * @param str
     * @return 
     * 最初はstatic呼び出しで作ってた⇨後でやめた
     */
    public String h(String str) {
        String ret_val = new String(str);

        String[] escape = {"&", "<", ">", "\"", "\'", "\n", "\t"};
        String[] replace = {"&amp;", "&lt;", "&gt;", "&quot;", "&#39;", "<br>", "&#x0009;"};

        for (int i = 0; i < escape.length; i++) {
            ret_val = ret_val.replace(escape[i], replace[i]);
        }

        return ret_val;
    }
    
    //Timestamp→String型への変換メソッド
    public String formattedTimestamp(Timestamp timestamp) {
        return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(timestamp);
    }

    //貨幣表示変換の記述が長すぎるので書いたメソッド。あまり変わらないような...?
    public String formatCurrency(int price) {
        return NumberFormat.getCurrencyInstance().format(price);
    }
    
    //トップへのリンクを返却
    public String home(){
        return "<a href=\""+DefineUtil.homeURL+"\">トップへ戻る</a>";
    }
    
    /**
     * 入力されたデータのうち未入力項目がある場合、チェックリストにしたがいどの項目が
     * 未入力なのかのhtml文を返却する
     * @param chkList　UserDataBeansで生成されるリスト。未入力要素の名前が格納されている
     * @return 未入力の項目に対応する文字列
     */
    public String chkinput(ArrayList<String> chkList){
        String output = "";
        for(String val : chkList){
                if(val.equals("name")){
                    output += "名前";
                }
                if(val.equals("password")){
                    output +="パスワード";
                }
                if(val.equals("mail")){
                    output +="メールアドレス";
                }
                if(val.equals("address")){
                    output +="住所";
                }
                output +="が未記入です<br>";
            }
        return output;
    }
    
    /**
     * 種別は数字で取り扱っているので画面に表示するときは日本語に変換
     * @param i
     * @return 
     */
    public String exTypenum(int i){
        switch(i){
            case 1:
                return "発送方法1";
            case 2:
                return "発送方法2";
            case 3:
                return "発送方法3";
        }
        return "";
    }
    
}
