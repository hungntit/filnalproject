/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uit.qabpss.dblp;

import java.util.List;
import uit.qabpss.preprocess.Token;

/**
 *
 * @author ThuanHung
 */
public class ObjectsAndToken {

    protected Token  token;
    protected List list;

    public ObjectsAndToken(Token token, List list) {
        this.token = token;
        this.list = list;
    }

    /**
     * Get the value of list
     *
     * @return the value of list
     */
    public List getList() {
        return list;
    }

    /**
     * Set the value of list
     *
     * @param list new value of list
     */
    public void setList(List list) {
        this.list = list;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if(obj.getClass() == Token.class)
            {
            if(token.equals(obj))
                return true;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ObjectsAndToken other = (ObjectsAndToken) obj;
        if (this.token != other.token && (this.token == null || !this.token.equals(other.token))) {
            return false;
        }
        if (this.list != other.list && (this.list == null || !this.list.equals(other.list))) {
            return false;
        }
        return true;
    }


        
}
