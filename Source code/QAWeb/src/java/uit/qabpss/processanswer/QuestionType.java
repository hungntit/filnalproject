/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uit.qabpss.processanswer;

import uit.qabpss.preprocess.Token;

/**
 *
 * @author ThuanHung
 */
public class QuestionType {
    int type;
    private static int WP   =   0;
    private static int YES_NO   =   1;
    private static int HOW_MANY   =   2;


    private  QuestionType(int type) {
        this.type = type;
    }
    public final static  QuestionType WP_Q    =  new QuestionType(QuestionType.WP);
    public final static  QuestionType Y_N_Q    =  new QuestionType(QuestionType.YES_NO);
    public final static  QuestionType HOW_MANY_Q    =  new QuestionType(QuestionType.HOW_MANY);

    public static QuestionType recognizeTypeOfQuestion(Token[] tokens)
    {
       if(tokens[0].getPos_value().equals("WP") || tokens[0].getPos_value().equals("WDT"))
           return  QuestionType.WP_Q;
       if(tokens[0].getValue().equalsIgnoreCase("how") && tokens[1].getValue().equalsIgnoreCase("many"))
           return  QuestionType.HOW_MANY_Q;
       return Y_N_Q;
    }
    public boolean isWPQuestion()
    {
        if(type == WP)
            return true;
        return false;
    }
    public boolean isYesNoQuestion()
    {
        if(type == YES_NO)
            return true;
        return false;
    }
    public boolean isHowManyQuestion()
    {
        if(type == HOW_MANY)
            return true;
        return false;
    }

    @Override
    public String toString() {
        if(type == 0)
            return "WH-Question";
        if(type == 2)
            return "HOW-Many_Question";
         return "YN-Question";
         }

}
