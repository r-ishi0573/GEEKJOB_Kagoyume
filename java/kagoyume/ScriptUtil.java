/**
 *
 * よく使うユーザ定義関数のクラス
 *
 *
 */
package kagoyume;

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

}
