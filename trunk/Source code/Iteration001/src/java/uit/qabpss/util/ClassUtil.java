/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uit.qabpss.util;

import java.util.Date;
import uit.qabpss.dbconfig.Type;

/**
 *
 * @author ThuanHung
 */
public class ClassUtil {
public static  Class changeTypeToClass(Type    type)
    {
    if(type.getIsDateTime())
        return Date.class;
    if(type.getIsDouble())
        return double.class;
    if(type.getIsNumber())
        return int.class;
    if(type.getIsString())
        return String.class;
    return null;
}
}
