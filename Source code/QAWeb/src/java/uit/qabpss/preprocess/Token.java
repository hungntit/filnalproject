/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.qabpss.preprocess;

/**
 *
 * @author ThuanHung
 */
public final class Token {

    private String value;
    private String pos_value;
    private EntityType  entityType;
    public Token() {
        setPos_value("");
        setValue("");
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    /**
     * Get the value of value
     *
     * @return the value of value
     */
    public String getValue() {
        return value;
    }

    /**
     * Set the value of value
     *
     * @param value new value of value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Get the value of pos_value
     *
     * @return the value of pos_value
     */
    public String getPos_value() {
        return pos_value;
    }

    /**
     * Set the value of pos_value
     *
     * @param pos_value new value of pos_value
     */
    public void setPos_value(String pos_value) {
        this.pos_value = pos_value;
    }

    public Token(String value, String pos_value) {
        this.value = value;
        this.pos_value = pos_value;
    }

    public Token(Token token) {


        this.pos_value = token.pos_value;
        this.value = token.value;
        this.entityType =   token.entityType;
    }
    public static Token[] copyTokens(Token[] tokens)
    {

        if(tokens   ==  null)
            return null;
        else
        {
            int length  =   tokens.length;
            Token[] results=    new Token[length];
            for(int i=0;i<length;i++)
            {
                if(tokens[i]!= null)
                    results[i]  =   new Token(tokens[i]);

            }
            return results;
        }

    }
    public static Token[] copyTokens(Token[] tokens,int start,int end)
    {

        if(tokens   ==  null)
            return null;

        else
        {
            int length  =   tokens.length;
            if(start>length)
                start   =   length-1;
            if(end>length)
                end   =   length-1;
            Token[] results=    new Token[end-start+1];
            for(int i=start;i<=end;i++)
            {
                if(tokens[i]!= null)
                    results[i-start]  =   new Token(tokens[i]);

            }
            return results;
        }

    }
    public static Token[] removeTokens(Token[] tokens, int start, int end)
    {
        Token[] result  ;
        if(tokens == null)
            return null;

        if(end> tokens.length)
            end =   tokens.length;
        if(start>end)
            return null;
        result  =   new Token[tokens.length-(end-start)-1];
        int pos =   0;
        for(int i=0;i<tokens.length;i++)
        {
            if(i <start || i>end)
            {
                result[pos]   =   new Token(tokens[i]);
                pos++;

            }
        }
        return result;
    }

    @Override
    public String toString() {
        return this.pos_value+"/"+this.value;
    }

}
