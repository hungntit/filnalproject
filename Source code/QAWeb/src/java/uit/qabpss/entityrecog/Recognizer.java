
package uit.qabpss.entityrecog;

import java.io.IOException;
import java.util.List;
import uit.qabpss.dbconfig.ColumnInfo;
import uit.qabpss.dbconfig.DBInfo;
import uit.qabpss.dbconfig.Relation;
import uit.qabpss.dbconfig.TableInfo;
import uit.qabpss.dbconfig.XMLReader;
import uit.qabpss.extracttriple.ExtractTriple;
import uit.qabpss.preprocess.SentenseUtil;
import uit.qabpss.preprocess.Token;
import uit.qabpss.preprocess.TripleWord;
import uit.qabpss.util.hibernate.HibernateUtil;

/**
 *
 * @author hoang nguyen
 */
public class Recognizer {
    public static final String CD = "CD";
    public static final String NNP = "NNP";
    private static final String BE = "be";
    private static DBInfo dbInf = null;

    public Recognizer() {
        if (dbInf == null) {
            XMLReader xmlReader = new XMLReader();
            dbInf = xmlReader.loadDBInfo();
        }
    }
    
    public List<TripleWord> tripleRecognize(List<TripleWord> lst){
        if(lst == null){
            return null;
        }
        List<TableInfo> lstTables = dbInf.getTables();
        for (int i = 0; i < lst.size(); i++) {
            TripleWord tripleWord = lst.get(i);
            if (BE.equals(tripleWord.getRelationWord())) {
                continue;
            }
            if (!NNP.equals(tripleWord.getFirstObjPos()) 
                    && !NNP.equals(tripleWord.getSecondObjPos())
                    && !CD.equals(tripleWord.getFirstObjPos())
                    && !CD.equals(tripleWord.getSecondObjPos())
                    ) {
                continue;
            }
            for (int j = 0; j < lstTables.size(); j++) {
                TableInfo tableInfo = lstTables.get(j);
                List<ColumnInfo> columns = tableInfo.getColumns();
                for (int k = 0; k < columns.size(); k++) {
                    ColumnInfo columnInfo = columns.get(k);
                    List<Relation> relations = columnInfo.getRelation();
                    for (int l = 0; l < relations.size(); l++) {
                        Relation relation = relations.get(l);
                        if(relation.getRelationName().equals(tripleWord.getRelationWord())){                            
                            if(tripleWord.getFirstObjPos().equals(NNP)||tripleWord.getFirstObjPos().equals(CD)){
                                if(HibernateUtil.valiateValue(columnInfo, tableInfo, tripleWord.getFirstObject())){
//                                    lst.get(i).setFirstObjHeader(tableInfo.getName());
//                                    lst.get(i).setRelationWordheader(columnInfo.getRelationType());
//                                    lst.get(i).setSecondObjHeader(columnInfo.getName());
                                    System.out.println("First Object");
                                    System.out.println(tableInfo.getName()+" - "+columnInfo.getRelationType()+" - "+columnInfo.getName());
                                }
                            }
                            if(tripleWord.getSecondObjPos().equals(NNP)||tripleWord.getSecondObjPos().equals(CD)){
                                if(HibernateUtil.valiateValue(columnInfo, tableInfo, tripleWord.getSecondObject())){
//                                    lst.get(i).setFirstObjHeader(tableInfo.getName());
//                                    lst.get(i).setRelationWordheader(columnInfo.getRelationType());
//                                    lst.get(i).setSecondObjHeader(columnInfo.getName());
                                    System.out.println("Second Object");
                                    System.out.println(tableInfo.getName()+" - "+columnInfo.getRelationType()+" - "+columnInfo.getName());
                                }
                            }                            
                        }
                    }
                }
            }
        }
        System.out.println("------------------------------------");
        return null;
    }

    public static void main(String[] args) throws IOException{
        String[] questions = new String[]{
            "Who write books in 1999 ?",
            "What publications have resulted from the TREC?",
            "Which books were written by Rafiul Ahad from 1999 to 2010 ?",
            "Which books were published by O'Reilly  in 1999 ?",
            "How many papers were written by Rafiul Ahad ?",
            "Who write books in 1999 ?",
            "Who write books from 1999 to 2010 ?",
         };
        int count =1;
        HibernateUtil.getSessionFactory();
        
        for (String question : questions) {
            List<TripleWord> t = null;
            Token[] tokens = SentenseUtil.formatNerWordInQuestion(question);
            tokens = SentenseUtil.optimizePosTags(tokens);
            System.out.println(count + "/ " + SentenseUtil.tokensToStr(tokens));
            ExtractTriple exTriple = new ExtractTriple();
            t = exTriple.extractTripleWordRel(tokens);
            for (int i = 0; i < t.size(); i++) {
                System.out.println("< " + t.get(i).getFirstObject() + " , " + t.get(i).getRelationWord() + " , " + t.get(i).getSecondObject() + " >");
            }
            count++;
            System.out.println("------------------------------------");
            Recognizer reg = new Recognizer();
            reg.tripleRecognize(t);
        }
        
    }
}
