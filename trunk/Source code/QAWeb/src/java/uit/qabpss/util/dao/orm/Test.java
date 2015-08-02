/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uit.qabpss.util.dao.orm;

import uit.qabpss.util.StringPool;

/**
 *
 * @author aa
 */
public class Test {
    public static void main(String args[]){
        String[] keys = CustomSQLUtil.keywords("Philip K. Chan");
        for(String s:keys){
            System.out.println(s);
        }
        String sql = "select distinct author from dblp_author_ref_new where (dblp_author_ref_new.author LIKE ? [$AND_OR_NULL_CHECK$])";
        String result = CustomSQLUtil.replaceKeywords(sql, "dblp_author_ref_new.author", StringPool.LIKE, true, keys);
        String sql2     =   CustomSQLUtil.replaceAndOperator(result, true);
        System.out.println(result);
        System.out.println(sql2);
    }
}
