
package uit.qabpss.entityrecog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import uit.qabpss.core.wordnet.Wordnet;
import uit.qabpss.dbconfig.ColumnInfo;
import uit.qabpss.dbconfig.DBInfo;
import uit.qabpss.dbconfig.Relation;
import uit.qabpss.dbconfig.TableInfo;
import uit.qabpss.dbconfig.XMLReader;
import uit.qabpss.extracttriple.ExtractTriple;
import uit.qabpss.extracttriple.TripleRelation;
import uit.qabpss.preprocess.EntityType;
import uit.qabpss.preprocess.SentenseUtil;
import uit.qabpss.preprocess.Token;
import uit.qabpss.preprocess.TripleWord;
import uit.qabpss.util.hibernate.HibernateUtil;
import wordnet.similarity.WordNotFoundException;

/**
 *
 * @author hoang nguyen
 */
public class Recognizer {
    public static final String CD = "CD";
    public static final String NNP = "NNP";
    private static final String BE = "be";
    private static DBInfo dbInf = null;

    public static final double SIMILARITY_LIMIT  =   0.7f;
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
        /* String[] questions = new String[]{
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
         */
        Recognizer reg = new Recognizer();
        List<TripleRelation> tripleRelationFromRelationStr = reg.getTripleRelationFromRelationStr("write");
        tripleRelationFromRelationStr   =   reg.getTripleRelationsFromNonNER(tripleRelationFromRelationStr, new Token("author", "NN"));
        int a=0;

    }

    public List<TripleRelation> getTripleRelationsFromNonNER(List<TripleRelation> existTripleRelations,Token token)
    {
        
        List<TripleRelation> tableresult =   new ArrayList<TripleRelation>();
        List<TripleRelation> columnresult =   new ArrayList<TripleRelation>();
        double maxSimilarityNumber    =   0;
        for(TripleRelation tr:existTripleRelations)
        {
            EntityType tableType    =   tr.getFirstEntity();
            String tableName        =   tableType.getTableInfo().getAliasName();
            double similarityWithTable =    0;
            try {

                similarityWithTable = Wordnet.similarityWN.getSimilarity(tableName, token.getValue())+ 0.01F;

            } catch (WordNotFoundException ex) {
                //Logger.getLogger(Recognizer.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(tableName.equalsIgnoreCase(token.getValue()))
                    similarityWithTable = 1+ 0.01F;
                if(similarityWithTable > SIMILARITY_LIMIT && similarityWithTable >= maxSimilarityNumber)
                {
                    if(similarityWithTable > maxSimilarityNumber)
                    {
                        tableresult.clear();
                        tableresult.add(tr);
                        maxSimilarityNumber =   similarityWithTable;
                    }
                    else
                    {
                        tableresult.add(tr);
                    }

                }

        }
        if(tableresult.size() > 0)
        {
            if(tableresult.size() ==1)
            {
                token.setEntityType(tableresult.get(0).getFirstEntity());
            }
            return tableresult;
        }
        else
        {
            for(TripleRelation tr:existTripleRelations)
            {

                EntityType columnType   =   tr.getSecondEntity();
                String columnName        =   columnType.getColumnInfo().getAliasName();
                double similarityWithColumn =   0;
                try {
                    similarityWithColumn = Wordnet.similarityWN.getSimilarity(columnName, token.getValue());

                } catch (WordNotFoundException ex) {
                  //  Logger.getLogger(Recognizer.class.getName()).log(Level.SEVERE, null, ex);
                }
                    if(columnName.equalsIgnoreCase(token.getValue()))
                        similarityWithColumn = 1.0F;
                    if(similarityWithColumn > SIMILARITY_LIMIT && similarityWithColumn >= maxSimilarityNumber)
                    {
                        if(similarityWithColumn > maxSimilarityNumber)
                        {
                            columnresult.clear();
                            columnresult.add(tr);
                            maxSimilarityNumber =   similarityWithColumn;
                        }
                        else
                        {
                            columnresult.add(tr);
                        }

                    }
                if(columnresult.size() > 0)
                {
                        if(columnresult.size() ==1)
                        {
                            token.setEntityType(columnresult.get(0).getSecondEntity());
                        }

                        return columnresult;
                }
            }
        }
        return existTripleRelations;

    }
     public  List<TripleRelation> getTripleRelationFromRelationStr(String relationStr)
    {
        List<TripleRelation> result   =   new ArrayList<TripleRelation>();
        List<TripleRelation> total   =   new ArrayList<TripleRelation>();
        List<TableInfo> tableInfos    =   dbInf.getTables();
        for(TableInfo tableInfo:tableInfos)
        {
            List<ColumnInfo> columnInfos    =   tableInfo.getColumns();
            for(ColumnInfo columnInfo:columnInfos)
            {
                List<Relation> relations    =   columnInfo.getRelation();
                for(Relation relation:relations)
                {
                    EntityType   firstEntity =   new EntityType(tableInfo, null);
                    EntityType   secondEntity =   new EntityType(tableInfo, columnInfo);
                    TripleRelation   tripleRelation  =   new TripleRelation(firstEntity,relation,secondEntity);
                    total.add(tripleRelation);
                   if(relation.getRelationName().equalsIgnoreCase(relationStr))
                   {
                       
                       if(!result.contains(tripleRelation))
                            result.add(tripleRelation);
                   }
                   else
                   {
                       if(relationStr.startsWith(BE))
                       {
                           if(relation.getRelationName().startsWith(BE))
                           {
                                String verb1 =   relation.getRelationName().substring(3);//remove BE in Str
                                String verb2 =   relationStr.substring(3);//remove BE in Str

                                if(Wordnet.checkSimilarityVerb(verb1, verb2))
                                {
                    
                                    if(!result.contains(tripleRelation))
                                        result.add(tripleRelation);
                                }
                           }
                       }
                        else
                       {
                                String verb1 =   relation.getRelationName();
                                String verb2 =   relationStr;

                                if(Wordnet.checkSimilarityVerb(verb1, verb2))
                                {
                                    if(!result.contains(tripleRelation))
                                        result.add(tripleRelation);
                                }
                        }
                   }
                }
            }
        }
        if(result.size()>0)
            return result;
        return total;
    }
}
