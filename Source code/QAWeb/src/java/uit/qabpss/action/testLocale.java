/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.qabpss.action;

/**
 *
 * @author Hoang-PC
 */
public class testLocale {
    private static final String WEB_HTTP = "http://localhost:8080/QAWeb";
    private static final String LOCATE_ACTION_PATTERN = "(/Locale.do).*";
    private static final String DEFAUFT_RETURN = "/Welcome.do";
    private static String preAction = "/Welcome.do";

//    public static void main(String args[]) {
//        String input1 = "http://localhost:8080/QAWeb/Locale.do?method=english&page=http://localhost:8080/QAWeb/Locale.do?method=vietnamese&page=http://localhost:8080/QAWeb/searchPage.do";
//        String input2 = "http://localhost:8080/QAWeb/searchPage.do";
//        String input3 = "http://localhost:8080/QAWeb/";
//        input1 = formatToPath(input1);
//        System.out.println(formatToPath(input1));
//        System.out.println(formatToPath(input2));
//        System.out.println(formatToPath(input3));
//    }

    public static String formatToPath(String url) {
        String result = "";
        result = url.replace(WEB_HTTP,"");
        if ("/".equals(result)) {
            result = DEFAUFT_RETURN;
        }
        if (result.matches(LOCATE_ACTION_PATTERN)) {
            result = preAction;
        }
        return result;
    }
}
