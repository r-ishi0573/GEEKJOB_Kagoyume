/**
 * 商品名、商品画像URL、価格、商品コードを持つbeans
 * 主に画面表示に使う
 */
package kagoyume;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import net.arnx.jsonic.JSON;

public class JsonSearchElements implements Serializable{

    private String name;
    private String imageUrl;
    private int price;
    private String itemCode;
    
    public JsonSearchElements() {};

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
        this.name = name;
    }

    /**
     * @return the uri
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * @param imageUrl the uri to set
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    /**
     * @return the price
     */
    public int getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(int price) {
        this.price = price;
    }
    
    /**
     * @return the itemCode
     */
    public String getItemCode() {
        return itemCode;
    }

    /**
     * @param itemCode the itemCode to set
     */
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }
    
    /**
     * YahooショッピングAPIから引数に指定した商品コードの情報を抽出し、
     * メンバ変数に抽出した値を設定するメソッド
     * @param itemCode YahooAPIで指定する商品コード。ユニークな値
     * @throws Exception 呼び出し元にcatchさせるためにスロー 
     */
    public boolean searchByJanCode(String itemCode) throws Exception {
        try {
            //JsonSearchElements jse = null;
            
            //json取得URL設定
            String itemCode4url = URLEncoder.encode(itemCode, "UTF-8");
            String url_str = "https://shopping.yahooapis.jp/ShoppingWebService/V1/json/itemLookup?appid=" + DefineUtil.appid + "&itemcode=" + itemCode4url;
            //out.println(url_str);
            //out.print("\n\n");

            //URLからjsonを受けとってFileバッファに渡す
            URL url = new URL(url_str);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
            StringBuffer responseBuffer = new StringBuffer();

            //URLのjsonからテキストを読み込む処理
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                responseBuffer.append(line);
            }
            reader.close();
            
            //取得したjsonテキストを文字列に変換
            String jsonText = responseBuffer.toString();
            
            //jsonテキストをjsonic使ってパース
            Map<String, Map<String, Object>> json = JSON.decode(jsonText);
            
            //検索ヒットなしの場合はfalse返して終了
            //if( Integer.valueOf((String) json.get("ResultSet").get("totalResultsReturned")).equals(0) ) {
            //    return false;
            //}
            
            //検索ヒットした結果のうち、返ってきた検索数を格納
            //RAW:課題の仕様だと上位10件みたいなんで値は10にした方がいいかも？
            //int numberOfResult = Integer.parseInt( json.get("ResultSet").get("totalResultsReturned").toString() );
            
            //"Resultset"->"0"->"Result"以降のオブジェクトを保存
            Map<String, Object> result = 
                    ((Map<String, Object>)((Map<String, Map<String, Object>>)json.get("ResultSet").get("0")).get("Result").get("0"));
            
            String name = result.get("Name").toString();
            String imageUrl = ((Map<String, Object>)result.get("Image")).get("Small").toString();
            String price = ((Map<String, Object>) result.get("Price")).get("_value").toString();
            
            //jse = new JsonSearchElements();
            
            //NOTICE:this使うのがいいのかどうか...
            this.name = name;
            this.imageUrl = imageUrl;
            this.price = Integer.parseInt(price);
            
            return true;
            
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new Exception(e);
        }
    }
}
