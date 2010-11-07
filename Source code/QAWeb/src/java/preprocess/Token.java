/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package preprocess;

/**
 *
 * @author ThuanHung
 */
public class Token {

    private String value;

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
    private String pos_value;

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
    public Token(Token token)
    {
        this.pos_value  =   token.pos_value;
        this.value      =   token.value;
    }

}
