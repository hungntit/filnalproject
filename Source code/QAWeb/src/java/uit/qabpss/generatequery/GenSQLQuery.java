/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uit.qabpss.generatequery;

import com.sun.org.apache.xml.internal.utils.Trie;
import java.util.ArrayList;
import java.util.List;
import uit.qabpss.dbconfig.ColumnInfo;
import uit.qabpss.dbconfig.MappingTable;
import uit.qabpss.dbconfig.Param;
import uit.qabpss.dbconfig.TableInfo;
import uit.qabpss.dbconfig.Type;
import uit.qabpss.extracttriple.TripleToken;
import uit.qabpss.preprocess.EntityType;
import uit.qabpss.preprocess.Token;
import uit.qabpss.util.StringPool;
import uit.qabpss.util.dao.orm.CustomSQLUtil;


/**
 *
 * @author aa
 */
public class GenSQLQuery {
    
//    public static void getSourceForQuery(List<TripleToken> tripleTokens,List<TableInfo> outMTableInfos,List<MappingTable> outMappingTables)
//    {
//
//        for(TripleToken tripleToken:tripleTokens)
//        {
//            EntityType   entityType1    =   tripleToken.getObj1().getEntityType();
//            EntityType   entityType2    =   tripleToken.getObj2().getEntityType();
//            if(entityType1.isColumn())
//            {
//                if(entityType1.getColumnInfo().isMappingField())
//                {
//                    MappingTable mappingTable = entityType1.getColumnInfo().getMappingTable();
//                    if(!outMappingTables.contains(mappingTable))
//                        outMappingTables.add(mappingTable);
//                }
//            }
//            if(entityType2.isColumn())
//            {
//                if(entityType2.getColumnInfo().isMappingField())
//                {
//                    MappingTable mappingTable = entityType2.getColumnInfo().getMappingTable();
//                    if(!outMappingTables.contains(mappingTable))
//                        outMappingTables.add(mappingTable);
//                }
//            }
//            addTableInfoToFromSource(outMTableInfos, entityType1);
//            addTableInfoToFromSource(outMTableInfos, entityType2);
//
//
//
//        }
//
//    }
    private static void addTableInfoToFromSource(List<TableInfo> list,EntityType entityType)
    {

            if(entityType.isTable())
            {
                if( ! list.contains(entityType.getTableInfo()))
                {
                    list.add(entityType.getTableInfo());
                }
            }
            else
            {
                if( entityType.getColumnInfo().isRelatedField())
                {

                    TableInfo   relatedTable   =   entityType.getColumnInfo().getRelatedTable();
                    if( ! list.contains(relatedTable))
                    {
                        if(relatedTable != null)
                            list.add(relatedTable);
                    }

                }
                else
                {
                    if( ! list.contains(entityType.getTableInfo()))
                    {
                        list.add(entityType.getTableInfo());
                    }
                }
            }
    }
    public static void getSourceForQueryInSameTriple(List<TripleToken> tripleTokens,List<TableInfo> outMTableInfos,List<MappingTable> outMappingTables)
    {
       
        
            EntityType   entityType1    =   tripleTokens.get(0).getObj1().getEntityType();
            EntityType   entityType2    =   tripleTokens.get(0).getObj2().getEntityType();
            if(entityType1.isColumn())
            {
                if(entityType1.getColumnInfo().isMappingField())
                {
                    MappingTable mappingTable = entityType1.getColumnInfo().getMappingTable();
                    if(!outMappingTables.contains(mappingTable))
                        outMappingTables.add(mappingTable);
                }
            }
            if(entityType2.isColumn())
            {
                if(entityType2.getColumnInfo().isMappingField())
                {
                    MappingTable mappingTable = entityType2.getColumnInfo().getMappingTable();
                    if(!outMappingTables.contains(mappingTable))
                        outMappingTables.add(mappingTable);
                }
            }
            addTableInfoToFromSource(outMTableInfos, entityType1);
            addTableInfoToFromSource(outMTableInfos, entityType2);



        
       
    }
    private static EntityType getMainEntitypeFromSameTriple(List<TripleToken> tripleTokens)
    {
        EntityType  entityType  =   null;
        TripleToken tripleToken1    =   tripleTokens.get(0);
        TripleToken tripleToken2    =   tripleTokens.get(1);
        String value11              =   tripleToken1.getObj1().getValue();
        String value21              =   tripleToken2.getObj1().getValue();
        if(value11.equals(value21))
            entityType  =    tripleToken1.getObj1().getEntityType();
        else
            entityType  =    tripleToken1.getObj2().getEntityType();
        if(entityType.isColumn())
        {
            if(entityType.getColumnInfo().isRelatedField())
            {
                entityType  =   new EntityType(entityType.getColumnInfo().getRelatedTable(),null);
            }
        }
        return entityType;
    }



    private static String createFromQueryForSameTriple(List<TripleToken> tripleTokens)
    {
        List<List<TripleToken>> list    =   TripleToken.createListsFromInSameTriple(tripleTokens);
        EntityType mainEntitype = getMainEntitypeFromSameTriple(tripleTokens);
        TableInfo tableInfo = mainEntitype.getTableInfo();
        List<TableInfo> tableInfos =   new ArrayList<TableInfo>();
         List<MappingTable> mappingTables   =   new ArrayList<MappingTable>();
       
        getSourceForQueryInSameTriple(tripleTokens, tableInfos, mappingTables);
        
        String fromQuery = genFromQuery(tableInfos, mappingTables);
        String selectQuery = genSelectQuery(mainEntitype);
        
        String query    =   "";



        for(int i=0;i<list.size();i++)
        {
            String OnQuery  =   "";
            List<TripleToken> arrayList = list.get(i);
            String whereQuery = genWhereQueryForNonSameToken(arrayList);
            String childQuery   =   "";
            childQuery   +=  "SELECT "+selectQuery+" \n\r";
            childQuery   +=  "FROM "+fromQuery +"\n\r";
            childQuery   +=  "WHERE \n\r" +whereQuery;
            childQuery   = "("+childQuery+") "+tableInfo.getAliasName();
            if(i>0)
            {
                childQuery+=i;
                
                    OnQuery +=  tableInfo.getAliasName()+"."+tableInfo.getPrimaryKey()+" = " + tableInfo.getAliasName()+i+"."+tableInfo.getPrimaryKey();
               
            }
            if(i>=1 &&i<list.size())
            {
                childQuery = "\n\rINNER JOIN\n\r"+ childQuery +" ON "+ OnQuery;


            }
            query+=childQuery;

        }

        query   =   query.trim();
        if(query.endsWith(","))
            query   =   query.substring(0, query.length()-1);
        return query;
    }
    public static void getSourceForQuery1(List<List<TripleToken>> list,List<TableInfo> outMTableInfos,List<MappingTable> outMappingTables)
    {

       for(List<TripleToken> tripleTokens: list)
       {
           if(tripleTokens.size()<2){
            for(TripleToken tripleToken:tripleTokens)
            {
                EntityType   entityType1    =   tripleToken.getObj1().getEntityType();
                EntityType   entityType2    =   tripleToken.getObj2().getEntityType();
                if(entityType1.isColumn())
                {
                    if(entityType1.getColumnInfo().isMappingField())
                    {
                        MappingTable mappingTable = entityType1.getColumnInfo().getMappingTable();
                        if(!outMappingTables.contains(mappingTable))
                            outMappingTables.add(mappingTable);
                    }
                }
                if(entityType2.isColumn())
                {
                    if(entityType2.getColumnInfo().isMappingField())
                    {
                        MappingTable mappingTable = entityType2.getColumnInfo().getMappingTable();
                        if(!outMappingTables.contains(mappingTable))
                            outMappingTables.add(mappingTable);
                    }
                }
                addTableInfoToFromSource(outMTableInfos, entityType1);
                addTableInfoToFromSource(outMTableInfos, entityType2);



            }
           }

        }
        for(List<TripleToken> tripleTokens: list)
       {
            if(tripleTokens.size()>1){

                EntityType   entityType1    =   tripleTokens.get(0).getObj1().getEntityType();
                EntityType   entityType2    =   tripleTokens.get(0).getObj2().getEntityType();
                if(entityType1.isColumn())
                {
                    if(entityType1.getColumnInfo().isMappingField())
                    {
                        MappingTable mappingTable = entityType1.getColumnInfo().getMappingTable();
                        if(outMappingTables.contains(mappingTable))
                            outMappingTables.remove(mappingTable);
                    }
                }
                if(entityType2.isColumn())
                {
                    if(entityType2.getColumnInfo().isMappingField())
                    {
                        MappingTable mappingTable = entityType2.getColumnInfo().getMappingTable();
                        if(outMappingTables.contains(mappingTable))
                            outMappingTables.remove(mappingTable);
                    }
                }
                removeTableInfoToFromSource(outMTableInfos, entityType1);
                removeTableInfoToFromSource(outMTableInfos, entityType2);




           }
        }
    }
    private static void removeTableInfoToFromSource(List<TableInfo> list,EntityType entityType)
    {

            if(entityType.isTable())
            {
                if(  list.contains(entityType.getTableInfo()))
                {
                    list.remove(entityType.getTableInfo());
                }
            }
            else
            {
                if( entityType.getColumnInfo().isRelatedField())
                {

                    TableInfo   relatedTable   =   entityType.getColumnInfo().getRelatedTable();
                    if(  list.contains(relatedTable))
                    {
                        if(relatedTable != null)
                            list.remove(relatedTable);
                    }

                }
                else
                {
                    if(  list.contains(entityType.getTableInfo()))
                    {
                        list.remove(entityType.getTableInfo());
                    }
                }
            }
    }

    public  static String genSelectQuery(EntityType entityType)
    {
        if(entityType   ==  null)
            return "";
        if(entityType.isNull())
            return "";
        if(entityType.isTable())
            return "`"+entityType.getTableInfo().getAliasName()+"`"+".* ";
        ColumnInfo  columnInfo  =   entityType.getColumnInfo();
        if(columnInfo.isRelatedField())
        {
            TableInfo   tableInfo   =   columnInfo.getRelatedTable();
            if(columnInfo.getName() == null)
                return "`"+tableInfo.getAliasName()+"`"+".* ";
            else
                return tableInfo.getAliasName()+"."+columnInfo.getName();
        }
        else
        {
            TableInfo   tableInfo   =   entityType.getTableInfo();
            return tableInfo.getAliasName()+"."+ columnInfo.getName();
        }

    }
    public  static String genFromQuery(List<TableInfo> tableInfos,List<MappingTable> mappingTables)
    {
        String result   =  "";
        for(MappingTable mappingTable:mappingTables)
        {
           for(TableInfo tableInfo:tableInfos)
           {
               if(mappingTable.getMappingTableName().equals(tableInfo.getName()))
               {
                   mappingTables.remove(mappingTable);
                   break;
               }
           }
            if(mappingTables.isEmpty())
                   break;
        }
        for(TableInfo tableInfo:tableInfos)
        {
            result  +=  "`"+tableInfo.getName()+"`"+" "+"`"+tableInfo.getAliasName()+"`"+",";
        }
        for(MappingTable mappingTable:mappingTables)
        {
            result  += "`"+mappingTable.getMappingTableName()+"`"+",";
        }
        if(result.equals(""))
            return "";
        result  =   result.substring(0, result.length()-1);
        return result;
    }

    public  static String genQuery(List<List<TripleToken>> groupTripleTokens,EntityType entitypeOfQuestion)
    {
        
        
        List<TripleToken>       simpleTripleTokens  =   new ArrayList<TripleToken>();
        String FromQueryForSameTriple   =   "";
        for(List<TripleToken> tripleTokens:groupTripleTokens)
        {
            if(tripleTokens.size()>1)
            {
                
                FromQueryForSameTriple += createFromQueryForSameTriple(tripleTokens)+",\n\r";

            }
            else
            {
                simpleTripleTokens.add(tripleTokens.get(0));
            }
        }
        FromQueryForSameTriple  =   FromQueryForSameTriple.trim();
        if(FromQueryForSameTriple.endsWith(","))
            FromQueryForSameTriple  =   FromQueryForSameTriple.substring(0, FromQueryForSameTriple.length()-1);
        String query    =   "";
         List<TableInfo> tableInfos =   new ArrayList<TableInfo>();
         List<MappingTable> mappingTables   =   new ArrayList<MappingTable>();

        getSourceForQuery1(groupTripleTokens, tableInfos, mappingTables);
        String selectQuery          =   genSelectQuery(entitypeOfQuestion);
        String whereQuery           =   genWhereQueryForNonSameToken(simpleTripleTokens);
        String genFromQuery = genFromQuery(tableInfos, mappingTables);
        genFromQuery    =   genFromQuery.trim();
        if(selectQuery.equals(""))
            return "";
        query   +=  "SELECT DISTINCT "+selectQuery+" \n\r";
        String fromQuery            =   "\n\r"+FromQueryForSameTriple;
        if(!genFromQuery.equals(""))
        {
            if(!FromQueryForSameTriple.equals(""))
                fromQuery+= ",";
            fromQuery+= genFromQuery;
        }
        query   +=  "FROM "+fromQuery +"\n\r";
        if( ! whereQuery.equals(""))
            query   +=  "WHERE \n\r" +whereQuery;

        return query;
    }
    private static String genConditionForParam(Param    param)
    {
        String condition    =   "";
        
        
        if(param.getColumn().getType()!= null)
        {
        if(param.getColumn().getType().equals(Type.STRING)&& param.getOperator().equalsIgnoreCase(StringPool.LIKE) )
          {
              String keywords[];
              
                 keywords =   CustomSQLUtil.keywords(param.getValue());
             
              if(keywords.length>0)
                  condition+= CustomSQLUtil.AND_OR_CONECTOR+" "+ CustomSQLUtil.createOperatorForField(param.toStringWithAlias(), StringPool.LIKE) +"\n";
               condition =   condition.substring(CustomSQLUtil.AND_OR_CONECTOR.length());
              condition =   CustomSQLUtil.replaceKeywords(condition, param.toStringWithAlias(), StringPool.LIKE, true, keywords);

          }

          else
          {
             
                condition+= CustomSQLUtil.createOperatorForField(param.toStringWithAlias(),param.getOperator());
          }
        }
        else
          {

                condition+= CustomSQLUtil.createOperatorForField(param.toStringWithAlias(),param.getOperator());
          }

        condition   =   CustomSQLUtil.replaceAndOperator(condition, true);
        return condition;
        
    }
    private static String genConditionQuery(Token token,String operator)
    {
            EntityType entityType = token.getEntityType();
            ColumnInfo  columnInfo  =   entityType.getColumnInfo();
            TableInfo  tableInfo  =   entityType.getTableInfo();


            String tempConditionQuery  = "";
            if(entityType.isNull())
                return "";
            if(entityType.isTable())
            {
                if(token.isNe())
                {
                    ColumnInfo presentationField = tableInfo.getPresentationField();
                    Param param  =   new Param(tableInfo, presentationField);
                    param.setValue(token.getValue());
                    param.setOperator(operator);
                    tempConditionQuery  =   genConditionForParam(param);

                }
                return tempConditionQuery;
            }

             MappingTable    mappingTable    =   columnInfo.getMappingTable();
            TableInfo       relatedTable    =   columnInfo.getRelatedTable();
            
            if(columnInfo.isRelatedField() && columnInfo.isMappingField())
            {
                String  tempConditionQuery1  =   "";
                String leftSide  = "`"+tableInfo.getAliasName()+"`"+"."+"`"+tableInfo.getPrimaryKey()+"`";
                String rightSide = "`"+mappingTable.getMappingTableName() + "`" + "." + "`"+mappingTable.getTableKey()+"`";
                if(! tableInfo.getName().equalsIgnoreCase(mappingTable.getMappingTableName())  )
                {
                    if(relatedTable.getName().equalsIgnoreCase(mappingTable.getMappingTableName()))
                    {
                         leftSide  = "`"+tableInfo.getAliasName()+"`"+"."+"`"+tableInfo.getPrimaryKey()+"`";
                         rightSide = "`"+relatedTable.getAliasName() + "`" + "." + "`"+mappingTable.getTableKey()+"`";
                     }
                     tempConditionQuery1  =   leftSide+" "+StringPool.EQUAL+" "+rightSide;
                }

                String  tempConditionQuery2  =   "";
                 leftSide  = "`"+relatedTable.getAliasName()+"`"+"."+"`"+relatedTable.getPrimaryKey()+"`";
                 rightSide = "`"+mappingTable.getMappingTableName() + "`" + "." + "`"+mappingTable.getRelatedTableKey()+"`";
                if(! relatedTable.getName().equalsIgnoreCase(mappingTable.getMappingTableName()))
                {
                    if(tableInfo.getName().equalsIgnoreCase(mappingTable.getMappingTableName()))
                    {
                          leftSide  = "`"+relatedTable.getAliasName()+"`"+"."+"`"+relatedTable.getPrimaryKey()+"`";
                          rightSide = "`"+tableInfo.getAliasName() + "`" + "." + "`"+mappingTable.getRelatedTableKey()+"`";
                     }
                    tempConditionQuery2  =   leftSide+" "+StringPool.EQUAL+" "+rightSide;
                }

                  tempConditionQuery       =   tempConditionQuery1 + " AND " +tempConditionQuery2;
                 tempConditionQuery              =   tempConditionQuery.trim();
                 if(tempConditionQuery.startsWith("AND"))
                     tempConditionQuery  =   tempConditionQuery.substring("AND".length());
                 if(tempConditionQuery.endsWith("AND"))
                     tempConditionQuery  =   tempConditionQuery.substring(0, tempConditionQuery.length() - "AND".length());

            }

            if(columnInfo.isRelatedField() && columnInfo.isMappingField() == false)
            {
                  tempConditionQuery  =   "";
                String leftSide  = "`"+tableInfo.getAliasName()+"`"+"."+"`"+columnInfo.getName()+"`";
                String rightSide = "`"+relatedTable.getAliasName() + "`" + "." + "`"+relatedTable.getPrimaryKey()+"`";
                if(! leftSide.equals(rightSide))
                {
                    tempConditionQuery  =   leftSide+" "+StringPool.EQUAL+" "+rightSide;
                }


            }
            if(token.isNe())
            {
                Param   param = null;
                if(columnInfo.isRelatedField())
                {
                    ColumnInfo presentationField = relatedTable.getPresentationField();
                       param   =   new Param(relatedTable, presentationField);
                       tempConditionQuery+=" AND ";
                }
                else
                {
                     param     =   new Param(tableInfo, columnInfo);
                }
                param.setValue(token.getValue());
                param.setOperator(operator);
                tempConditionQuery+= genConditionForParam(param);

            }
            return tempConditionQuery;
    }
    public static String genWhereQueryForNonSameToken(List<TripleToken> list)
    {
        String query    =   "";
        for(TripleToken tripleToken:list)
        {
            Token   obj1    =   tripleToken.getObj1();
            Token   obj2    =   tripleToken.getObj2();

            EntityType  entitype1   =   obj1.getEntityType();
            EntityType  entitype2   =   obj2.getEntityType();

            if(entitype1    ==  entitype2)
            {
                continue;
            }
            String condition1   =   genConditionQuery(obj1,tripleToken.getOperator());
            String condition2   =   genConditionQuery(obj2,tripleToken.getOperator());

            String condition    =   condition1+" AND "+condition2;
            condition   =   condition.trim();
            if(condition.endsWith("AND"))
            {
                condition   =   condition.substring(0, condition.length()- "AND".length());
            }
            if(condition.startsWith("AND"))
            {
                condition   =   condition.substring("AND".length());
            }
            String andOrOperatorStr =   "";
            if(tripleToken.isIsAndOperator())
                andOrOperatorStr    =   " AND";
            else
                andOrOperatorStr    =   " OR";
            if(!condition.equals(""))
                query+= andOrOperatorStr+condition;//replace and operator here
            query   =   query.trim();


        }

            if(query.startsWith("AND"))
            {
                query   =   query.substring("AND".length());
            }
            if(query.endsWith("AND"))
            {
                query   =   query.substring(0, query.length()- "AND".length());
            }
        return query;

    }
        /*
     * Hoang Nguyen
     * Method get Params from List Triples to input to Query
     * input : List Triples
     * output: List Params (Token)
     */
    public static List<Token> getParams(List<TripleToken> triples) {
        if (triples == null || triples.isEmpty()) {
            return null;
        }

        List<Token> resutls = new ArrayList<Token>();
        for (int i = 0; i < triples.size(); i++) {
            TripleToken tripleToken = triples.get(i);
            Token obj1 = tripleToken.getObj1();
            Token obj2 = tripleToken.getObj2();
            if (obj1.getPos_value().equalsIgnoreCase(StringPool.POS_NNP)
                    || obj1.getPos_value().equalsIgnoreCase(StringPool.POS_CD)) {
                resutls.add(obj1);
            }
            if (obj2.getPos_value().equalsIgnoreCase(StringPool.POS_NNP)
                    || obj2.getPos_value().equalsIgnoreCase(StringPool.POS_CD)) {
                resutls.add(obj2);
            }
        }
        return resutls;
    }
}
