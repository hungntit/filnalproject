
package uit.qabpss.dbconfig;

import java.io.Serializable;
import uit.qabpss.util.StringPool;

/**
 * <a href="Type.java.html"><b><i>View Source</i></b></a>
 *
 * @author Hung Nguyen
 *
 */
public class Type implements Serializable {

	public static final Type INTEGER = new Type(StringPool.INTEGER);
        public static final Type STRING = new Type(StringPool.STRING);
        public static final Type DATETIME = new Type(StringPool.DATETIME);
        public static final Type LONG = new Type(StringPool.LONG);
        public static final Type DOUBLE = new Type(StringPool.DOUBLE);
        public static final Type BOOLEAN = new Type(StringPool.BOOLEAN);
        public static final Type CHARACTER = new Type(StringPool.CHARACTER);
        public static final Type CODE = new Type(StringPool.CODE);
        
        private Type(String type)
        {
            this.type   =   type;
        }
        private String type;

    @Override
    public boolean equals(Object obj) {
        return this.toString().equals(obj.toString());
    }

    @Override
    public String toString() {
        return this.type;
    }
    public boolean getIsNumber()
    {
        if(type.equals(StringPool.LONG) ||type.equals(StringPool.INTEGER) )
            return true;
        return false;

    }
    public boolean getIsString()
    {
        if(type.equals(StringPool.STRING) )
            return true;
        return false;

    }
    public boolean getIsDouble()
    {
        if(type.equals(StringPool.DOUBLE) )
            return true;
        return false;

    }
    public boolean getIsDateTime()
    {
        if(type.equals(StringPool.DATETIME) )
            return true;
        return false;

    }
    public boolean getIsBoolean()
    {
        if(type.equals(StringPool.BOOLEAN) )
            return true;
        return false;

    }
    public static boolean isNumber(String value)
    {
        try
        {

            Long.parseLong(value);
            return true;
        }
        catch(NumberFormatException nEx)
        {
            //do nothing
        }
        return false;
    }
    public static boolean isDouble(String value)
    {
        try
        {

            Double.parseDouble(value);
            return true;
        }
        catch(NumberFormatException nEx)
        {
            //do nothing
        }
        return false;
    }
}