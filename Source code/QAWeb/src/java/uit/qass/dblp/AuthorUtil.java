/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uit.qass.dblp;

import java.util.ArrayList;
import java.util.List;
import uit.qass.model.Author;

/**
 *
 * @author ThuanHung
 */
public class AuthorUtil {
public static List<String> getListAuthorFromListObj(List<Author> list)
    {
    List<String> result =   new ArrayList<String>();
    for(Author au:list)
    {
        String author   =   au.getAuthor();
        if(!result.contains(author))
        {
            result.add(author);
        }
    }
    return result;
}
}
