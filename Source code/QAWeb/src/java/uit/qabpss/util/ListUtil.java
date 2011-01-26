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
    public static boolean containAndEquals(List list,Object value)
    {
        if(value == null)
            return false;
        List result     =   new ArrayList();
        for(Object object:list)
        {
            if(object == null)
                continue;
            else
            {
                if(object.toString().equals(value.toString()))
                    return true;
            }
        }
        return false;
    }
}
