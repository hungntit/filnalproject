/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uit.qabpss.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ThuanHung
 */
public class ListUtil {
    public static List distinctList(List list)
    {
        List result     =   new ArrayList();
        for(Object object:list)
        {
            if(!result.contains(object))
                result.add(object);
        }
        return result;
    }
}
