/**
 *
 * 定数定義をするクラス
 * 
 */

/**
 * セッション用のパラメータ(このファイルに書く留めるべきかは微妙)
 * "Login", UserDataDTO              ログインしているユーザ情報を示す
 * "LoginDialog", "ログインしました"    ログインした際に表示。top.jspで使う?       
 * "LoginFailed", "ログインできません。再度入力してください"
 *                                   ログイン失敗の際に表示。login.jspで使う? 
 */

package kagoyume;

import java.util.*;

public class DefineUtil {
    
    //トップへのリンクを定数として設定
    public static final String homeURL = "top.jsp";

    //アプリケーションIDを設定
    public static final String appid = "dj0zaiZpPWRvdmFlcFBLbnk4QyZzPWNvbnN1bWVyc2VjcmV0Jng9M2Y-";

    /**
     * @brief カテゴリーID一覧
     *
     * 商品カテゴリの一覧です。 キーにカテゴリID、値にカテゴリ名が入っています。
     * @var array
     */
    public static final LinkedHashMap<String, String> categories;
    static {
        categories = new LinkedHashMap<String, String>();
        categories.put("1", "すべてのカテゴリから");
        categories.put("13457", "ファッション");
        categories.put("2498", "食品");
        categories.put("2500", "ダイエット、健康");
        categories.put("2501", "コスメ、香水");
        categories.put("2502", "パソコン、周辺機器");
        categories.put("2504", "AV機器、カメラ");
        categories.put("2505", "家電");
        categories.put("2506", "家具、インテリア");
        categories.put("2507", "花、ガーデニング");
        categories.put("2508", "キッチン、生活雑貨、日用品");
        categories.put("2503", "DIY、工具、文具");
        categories.put("2509", "ペット用品、生き物");
        categories.put("2510", "楽器、趣味、学習");
        categories.put("2511", "ゲーム、おもちゃ");
        categories.put("2497", "ベビー、キッズ、マタニティ");
        categories.put("2512", "スポーツ");
        categories.put("2513", "レジャー、アウトドア");
        categories.put("2514", "自転車、車、バイク用品");
        categories.put("2516", "CD、音楽ソフト");
        categories.put("2517", "DVD、映像ソフト");
        categories.put("10002", "本、雑誌、コミック");
    }

    /**
     * @brief ソート方法一覧
     *
     * 検索結果のソート方法の一覧です。 キーに検索用パラメータ、値にソート方法が入っています。
     * @access private
     * @var array
     *
     */
    public static final LinkedHashMap<String, String> sortOrder;
    static {
        sortOrder = new LinkedHashMap<String, String>();
        sortOrder.put("-score", "おすすめ順");
        sortOrder.put("+price", "商品価格が安い順");
        sortOrder.put("-price", "商品価格が高い順");
        sortOrder.put("+name", "ストア名昇順");
        sortOrder.put("-name", "ストア名降順");
        sortOrder.put("-sold", "売れ筋順");
    }
}
