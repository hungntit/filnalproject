
package uit.qabpss.entityrecog;

import edu.mit.jwi.item.POS;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import uit.qabpss.core.search.UtimateSearch;
import uit.qabpss.core.wordnet.Wordnet;
import uit.qabpss.dbconfig.ColumnInfo;
import uit.qabpss.dbconfig.DBInfo;
import uit.qabpss.dbconfig.DBInfoUtil;
import uit.qabpss.dbconfig.Param;
import uit.qabpss.dbconfig.Relation;
import uit.qabpss.dbconfig.TableInfo;
import uit.qabpss.dbconfig.Type;
import uit.qabpss.dbconfig.XMLReader;
import uit.qabpss.extracttriple.TripleRelation;
import uit.qabpss.extracttriple.TripleToken;
import uit.qabpss.preprocess.EntityType;
import uit.qabpss.preprocess.Token;
import uit.qabpss.preprocess.TripleWord;
import uit.qabpss.util.dao.orm.CustomSQLUtil;
import uit.qabpss.util.dao.orm.hibernate.QueryPos;
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
    /*
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
*/
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
       // EntityType tripleFromValue = getTripleFromValue("database");
        //EntityType tripleFromValue = getEntityTypeFromValue("0-201-59098-0");
        //EntityType tripleFromValue = getTripleFromValue("database");
        //EntityType tripleFromValue = getTripleFromValue("database");
        //EntityType tripleFromValue = getTripleFromValue("database");
        Recognizer reg = new Recognizer();
       // List<TripleRelation> tripleRelationFromRelationStr = reg.getTripleRelationFromRelationStr("write");
       // tripleRelationFromRelationStr   =   reg.getTripleRelationsFromNonNER(tripleRelationFromRelationStr, new Token("author", "NN"));
        int a=0;

    }
    private  List<TripleRelation> getTripleRelationFromObj(List<TripleRelation> existTripleRelations,Token obj,int index)
    {
        if(obj == null)
            return existTripleRelations;
        EntityType entityType   =   obj.getEntityType();
        if(entityType.isNull())
            return existTripleRelations;
        List<TripleRelation> result     =   new ArrayList<TripleRelation>();
        if(index    ==  1)
        {
            for(TripleRelation tripleRelation:existTripleRelations)
            {
                EntityType  firstEntityType =   tripleRelation.getFirstEntity();
                if(firstEntityType.equals(entityType))
                {
                    result.add(tripleRelation);
                }
            }
            if(result.size()>0)
                return result;
        }
        else
        {
            for(TripleRelation tripleRelation:existTripleRelations)
            {
                EntityType  secondEntityType =   tripleRelation.getSecondEntity();
                if(secondEntityType.equals(entityType))
                {
                    result.add(tripleRelation);
                }
            }
            if(result.size()>0)
                return result;
        }
        return existTripleRelations;
    }
    public List<TripleRelation> getTripleRelationsFromFirstObj(List<TripleRelation> existTripleRelations,Token obj)
    {
        return getTripleRelationFromObj(existTripleRelations, obj, 1);
    }
    public List<TripleRelation> getTripleRelationsFromSecondObj(List<TripleRelation> existTripleRelations,Token obj)
    {
        return getTripleRelationFromObj(existTripleRelations, obj, 2);
    }

    public  EntityType getEntityTypeFromValue(String value) {
            if (value.isEmpty()) {
                return null;
            }
            String temp = value;
            EntityType t = null;
            // check value is number : year
            if(Type.isDouble(value)|| Type.isNumber(value))
            {
                t = checkValueFromDB(value, Type.DOUBLE);
                if(t!= null)
                    return t;
            }
            // check value is CODE : isbn, doi
            if ((value + " ").matches("[0-9].*")) {
                t = checkValueFromDB(value, Type.CODE);
                if(t!= null)
                    return t;
            }
            //check to another fields
            t = checkValueFromDB(value, Type.STRING);
            if (t != null) {
                return t;
            }
            return null;
        }

    private  EntityType checkValueFromDB(String value,Type type,List<TripleRelation> tripleRelations){
            
            List<EntityType> invisibleEntityTypes   =   new ArrayList<EntityType>();
            try {
                
                    for (int j = 0; j < tripleRelations.size(); j++) {
                        EntityType entityType   =  tripleRelations.get(j).getSecondEntity();
                        ColumnInfo columnInfo = entityType.getColumnInfo();
                        TableInfo tableInfo = entityType.getTableInfo();
                        if(!columnInfo.isIsVisible())
                        {
                            invisibleEntityTypes.add(entityType);
                            continue;
                        }
                            
                        if (type.equals(columnInfo.getType()) ||(Type.isDouble(value) && columnInfo.getType().getIsNumber())) { 

                           Param param  =   new Param(tableInfo, columnInfo);
                           param.setValue(value);
                           int count    =   UtimateSearch.countLimitByParam(new Param[]{param}, true, null , 1);
                           if(count>0)
                               return new EntityType(tableInfo, columnInfo);
                        }
                    
                }
            } catch (Exception e) {
                e.printStackTrace();
                }
            if(invisibleEntityTypes.size() == 1)
                return invisibleEntityTypes.get(0);
            return null;
        }
    public  EntityType getEntityTypeFromValue(String value,List<TripleRelation> tripleRelations) {
            if (value.isEmpty()) {
                return null;
            }
            String temp = value;
            EntityType t = null;
            // check value is number : year
            if(Type.isDouble(value)|| Type.isNumber(value))
            {
                t = checkValueFromDB(value, Type.DOUBLE,tripleRelations);
                if(t!= null)
                    return t;
            }
            // check value is CODE : isbn, doi
            if ((value + " ").matches("[0-9].*")) {
                t = checkValueFromDB(value, Type.CODE,tripleRelations);
                if(t!= null)
                    return t;
            }
            //check to another fields
            t = checkValueFromDB(value, Type.STRING,tripleRelations);
            if (t != null) {
                return t;
            }
            return null;
        }

    private  EntityType checkValueFromDB(String value,Type type){
            TripleWord t = null;
            String inputValue = "";
            if(DBInfoUtil.getDBInfo() == null)
                DBInfoUtil.initDb();
            List<TableInfo> tables = DBInfoUtil.getDBInfo().getTables();
            try {
                for (int i = 0; i < tables.size(); i++) {
                    TableInfo tableInfo = tables.get(i);
                    List<ColumnInfo> columns = tableInfo.getColumns();
                    for (int j = 0; j < columns.size(); j++) {
                        ColumnInfo columnInfo = columns.get(j);
                        if(!columnInfo.isIsVisible())
                            continue;
                        if (type.equals(columnInfo.getType()) ||(type.isDouble(value) && columnInfo.getType().getIsNumber())) {

                           Param param  =   new Param(tableInfo, columnInfo);
                           param.setValue(value);
                           int count    =   UtimateSearch.countLimitByParam(new Param[]{param}, true, null , 1);
                           if(count>0)
                               return new EntityType(tableInfo, columnInfo);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                }
            return null;
        }
    public List<TripleRelation> getTripleRelationsFromNonNER(List<TripleRelation> existTripleRelations,Token token)
    {
        if(token.getPos_value().equalsIgnoreCase("NNS"))
        {
            String stemNoun =   Wordnet.wnstemmer.findStems(token.getValue(), POS.NOUN).get(0);
            token.setPos_value("NN");
            token.setValue(stemNoun);
        }
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
                           if(relationStr.equalsIgnoreCase("be"))
                           {
                               continue;
                           }
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

     public  void identifyTripleToken(TripleToken tripleToken)
    {
        String relationStr =   tripleToken.getRelationName();
        List<TripleRelation> tripleRelationList = getTripleRelationFromRelationStr(relationStr);

        if(tripleRelationList.size() == 1)
        {
            TripleRelation  tripleRelation  =   tripleRelationList.get(0);
            //Kiem tra quan he nguoc
            if(tripleRelation.getRelation().isReversedRelation())
                tripleToken.swapTwoObject();
            tripleToken.getObj1().setEntityType(tripleRelation.getFirstEntity());
            tripleToken.getObj2().setEntityType(tripleRelation.getSecondEntity());
        }
        else
        {
            // check ob2 is identified
            if(tripleToken.getObj2().getEntityType().isNull() == false)
            {

                if(tripleToken.getObj2().getEntityType().isTable())
                {
                    tripleToken.swapTwoObject();
                    //Now ob1 is obj2
                    tripleRelationList  =   getTripleRelationsFromFirstObj(tripleRelationList, tripleToken.getObj1());
                    if(tripleRelationList.size() == 1)
                    {
                        tripleToken.getObj2().setEntityType(tripleRelationList.get(0).getSecondEntity());
                        return;
                    }
                }
                else
                {
                    tripleRelationList  =   getTripleRelationsFromSecondObj(tripleRelationList, tripleToken.getObj2());
                    if(tripleRelationList.size() == 1)
                    {
                        tripleToken.getObj1().setEntityType(tripleRelationList.get(0).getFirstEntity());
                        return;
                    }
                }


            }
            //end  check ob2 is identified
            // check ob1 is identified
            if(tripleToken.getObj1().getEntityType().isNull() == false)
            {

                if(tripleToken.getObj1().getEntityType().isColumn())
                {
                    tripleToken.swapTwoObject();
                    //Now ob1 is obj2
                    tripleRelationList  =   getTripleRelationsFromSecondObj(tripleRelationList, tripleToken.getObj2());
                    if(tripleRelationList.size() == 1)
                    {
                        tripleToken.getObj1().setEntityType(tripleRelationList.get(0).getFirstEntity());
                        return;
                    }
                }
                else
                {
                    tripleRelationList  =   getTripleRelationsFromSecondObj(tripleRelationList, tripleToken.getObj1());
                    if(tripleRelationList.size() == 1)
                    {
                        tripleToken.getObj2().setEntityType(tripleRelationList.get(0).getSecondEntity());
                        return;
                    }
                }


            }
            //end  check ob1 is identified
            if(tripleToken.isHavingNonNe())
            {
                // check obj2 is non name entity
                Token   obj2    =   tripleToken.getObj2();
                if(obj2.getPos_value().equalsIgnoreCase("NN")|| obj2.getPos_value().equalsIgnoreCase("NNS"))
                {
                    tripleRelationList  =   getTripleRelationsFromNonNER(tripleRelationList, obj2);
                    if(tripleRelationList.size() == 1)
                    {
                        if(obj2.getEntityType().isTable())
                        {
                            tripleToken.swapTwoObject();
                            tripleToken.getObj2().setEntityType(tripleRelationList.get(0).getSecondEntity());
                        }
                        else
                        {
                            tripleToken.getObj1().setEntityType(tripleRelationList.get(0).getFirstEntity());
                        }
                        return;
                    }

                }
                // end check obj2 is non name entity
                // check obj1 is non name entity
                Token   obj1    =   tripleToken.getObj1();
                if(obj1.getPos_value().equalsIgnoreCase("NN")||obj1.getPos_value().equalsIgnoreCase("NNS"))
                {
                    tripleRelationList  =   getTripleRelationsFromNonNER(tripleRelationList, obj1);
                    if(tripleRelationList.size() == 1)
                    {
                        if(obj1.getEntityType().isColumn())
                        {
                            tripleToken.swapTwoObject();
                            tripleToken.getObj1().setEntityType(tripleRelationList.get(0).getFirstEntity());
                        }
                        else
                        {
                            tripleToken.getObj2().setEntityType(tripleRelationList.get(0).getSecondEntity());
                        }
                        return;
                    }

                }

                // end check obj2 is non name entity



            }

                //check obj2 is name entity
                if(tripleToken.getObj2().getPos_value().equalsIgnoreCase("NNP")|| tripleToken.getObj2().getPos_value().equalsIgnoreCase("CD"))
                {
                    EntityType tempEntityType   =   tripleToken.getObj2().getEntityType();
                    EntityType entityType = getEntityTypeFromValue(tripleToken.getObj2().getValue(),tripleRelationList);
                    tripleToken.getObj2().setEntityType(entityType);
                    if(tripleToken.getObj2().getEntityType()!= null&&tripleToken.getObj2().getEntityType().isNull() == false)
                    {
                        if(tripleToken.getObj2().getEntityType().isTable())
                        {
                            tripleToken.swapTwoObject();
                            //Now ob1 is obj2
                            int lastSize    =   tripleRelationList.size();
                            tripleRelationList  =   getTripleRelationsFromFirstObj(tripleRelationList, tripleToken.getObj1());
                            if(tripleRelationList.size() == 1)
                            {
                                tripleToken.getObj2().setEntityType(tripleRelationList.get(0).getSecondEntity());
                                return;
                            }
                            else
                            {
                                if(tripleRelationList.size() == lastSize)// unvalueable
                                {
                                    tripleToken.swapTwoObject();
                                    tripleToken.getObj2().setEntityType(tempEntityType);
                                }
                            }
                        }
                        else
                        {
                            int lastSize        =   tripleRelationList.size();
                            tripleRelationList  =   getTripleRelationsFromSecondObj(tripleRelationList, tripleToken.getObj2());
                            if(tripleRelationList.size() == 1)
                            {
                                tripleToken.getObj1().setEntityType(tripleRelationList.get(0).getFirstEntity());
                                return;
                            }
                            else
                            {
                                if(tripleRelationList.size() == lastSize)// unvalueable
                                {
                                    tripleToken.getObj2().setEntityType(tempEntityType);
                                }
                            }
                        }
                    }
                }
                //end check obj2 is name entity
                //check obj1 is name entity
                if(tripleToken.getObj1().getPos_value().equalsIgnoreCase("NNP")|| tripleToken.getObj1().getPos_value().equalsIgnoreCase("CD"))
                {
                    EntityType tempEntityType   =   tripleToken.getObj1().getEntityType();
                    EntityType entityType = getEntityTypeFromValue(tripleToken.getObj1().getValue(),tripleRelationList);
                    tripleToken.getObj1().setEntityType(entityType);
                    if(tripleToken.getObj1().getEntityType().isNull() == false)
                    {
                        int lastSize    =   tripleRelationList.size();
                        if(tripleToken.getObj1().getEntityType().isColumn())
                        {
                            tripleToken.swapTwoObject();
                            //Now ob1 is obj2

                            tripleRelationList  =   getTripleRelationsFromSecondObj(tripleRelationList, tripleToken.getObj2());
                            if(tripleRelationList.size() == 1)
                            {
                                tripleToken.getObj1().setEntityType(tripleRelationList.get(0).getFirstEntity());
                                return;
                            }
                            else
                            {
                                if(tripleRelationList.size()    ==  lastSize)
                                {
                                    tripleToken.swapTwoObject();
                                    tripleToken.getObj1().setEntityType(tempEntityType);
                                }
                            }
                        }
                        else
                        {
                            tripleRelationList  =   getTripleRelationsFromSecondObj(tripleRelationList, tripleToken.getObj1());
                            if(tripleRelationList.size() == 1)
                            {
                                tripleToken.getObj2().setEntityType(tripleRelationList.get(0).getSecondEntity());
                                return;
                            }
                            else
                            {
                                if(tripleRelationList.size()    ==  lastSize)
                                {
                                    tripleToken.getObj1().setEntityType(tempEntityType);
                                }
                            }
                        }


                    }
                }

                //end check obj1 is name entity
            
        }
    }
}
