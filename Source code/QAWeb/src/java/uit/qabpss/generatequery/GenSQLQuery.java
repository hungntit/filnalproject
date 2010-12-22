/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uit.qabpss.generatequery;

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
    public static List<List<TripleToken>> groupTripleTokens(List<TripleToken> tripleTokens)
    {
        List<List<TripleToken>> list    =   new ArrayList<List<TripleToken>>();


        for(int i = 0;i< tripleTokens.size();i++)
        {
            TripleToken firstTripleToken =   tripleTokens.get(i);
            List<TripleToken>   childTripleTokens   =   new ArrayList<TripleToken>();
            childTripleTokens.add(firstTripleToken);
            list.add(childTripleTokens);
            for(int j   = i+1;j<tripleTokens.size();j++)
            {
                TripleToken tripleToken = tripleTokens.get(j);
                if(firstTripleToken.isSameWith(tripleToken))
                {
                    childTripleTokens.add(tripleToken);
                }
                else
                {
                    i=j-1;
                    break;
                }
            }
        }
        return list;
    }
    public static void getSourceForQuery(List<TripleToken> tripleTokens,List<TableInfo> outMTableInfos,List<MappingTable> outMappingTables)
    {
       
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
    public  static String genQuery(List<TripleToken> list,EntityType entitypeOfQuestion)
    {
        String query    =   "";
         List<TableInfo> tableInfos =   new ArrayList<TableInfo>();
         List<MappingTable> mappingTables   =   new ArrayList<MappingTable>();
        getSourceForQuery(list,tableInfos, mappingTables);
        String selectQuery          =   genSelectQuery(entitypeOfQuestion);
        String whereQuery           =   genWhereQuery(list);

        if(selectQuery.equals(""))
            return "";
        query   +=  "SELECT "+selectQuery+" \n\r";
        String fromQuery            =   genFromQuery(tableInfos,mappingTables);
        query   +=  "FROM "+fromQuery +"\n\r";
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
    public static String genWhereQuery(List<TripleToken> list)
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
}
