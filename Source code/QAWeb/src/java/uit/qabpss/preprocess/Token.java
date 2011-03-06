/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.qabpss.preprocess;

import java.util.List;

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
        entityType  =   new EntityType();
    }
    public boolean isNe()
    {
        if(pos_value.equals("NNP") || pos_value.equals("CD"))
            return true;
        return false;
    }
    public boolean isNonNe()
    {
        if(pos_value.equals("NN") )
            return true;
        return false;
    }
    public boolean isWP()
    {
        if(pos_value.equals("WP"))
            return true;
        return false;
    }
    public boolean isWDT()
    {
        if(pos_value.equals("WDT"))
            return true;
        return false;
    }
    public boolean isVB()
    {
        if(pos_value.equals("VB"))
            return true;
        return false;
    }
     public boolean isMD()
    {
        if(pos_value.equals("MD"))
            return true;
        return false;
    }
    public boolean isAuxiliaryVerb()
    {
        if(pos_value.equals("VB"))
        {
            if(value.equalsIgnoreCase("be")|| value.equalsIgnoreCase("have")||value.equalsIgnoreCase("do"))
                return true;
        }
        return false;
    }


    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        if(entityType == null|| this.entityType == null)
            this.entityType = entityType;
        else
        {
            this.entityType.tableInfo   =   entityType.tableInfo;
            this.entityType.columnInfo  =   entityType.columnInfo;
        }
    }
    public void setEntityOfToken(Token token) {
        this.entityType = token.getEntityType();
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
        this.entityType =   new EntityType();
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
    public static Token[] copyTokens(List<Token> tokens)
    {

        if(tokens == null)
            return null;
        return copyTokens((Token[])tokens.toArray());

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
    public static boolean contains(Token[] tokens,Token token)
    {
        for(Token t: tokens)
        {
            if(t.getValue().equals(token.value) && t.getPos_value().equals(token.pos_value))
            {
                return true;
            }

        }
        return false;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Token other = (Token) obj;
        if ((this.value == null) ? (other.value != null) : !this.value.equals(other.value)) {
            return false;
        }
        if ((this.pos_value == null) ? (other.pos_value != null) : !this.pos_value.equals(other.pos_value)) {
            return false;
        }
        if (this.entityType != other.entityType && (this.entityType == null || !this.entityType.equals(other.entityType))) {
            return false;
        }
        return true;
    }

   

}
