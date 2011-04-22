/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uit.qabpss.dblp;

import uit.qabpss.model.Author;
import uit.qabpss.model.Publication;

/**
 *
 * @author ThuanHung
 */
public class HtmlUtil {
public static String toHtml(Object obj)
    {
        if(obj == null)
            return "";
        if(obj.getClass() == Author.class)
        {
            Author author   =   (Author)obj;
             return author.toHtmlStr();

        }
        if(obj.getClass() == Publication.class)
        {
            Publication pub   =   (Publication)obj;
            return pub.toHtmlStr();

        }
        return obj.toString();

    }
}
