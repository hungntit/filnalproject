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
    public static List<TableInfo> getSourceForQuery(List<TripleToken> tripleTokens)
    {
        List<TableInfo> result  =   new ArrayList<TableInfo>();
        for(TripleToken tripleToken:tripleTokens)
        {
            EntityType   entityType1    =   tripleToken.getObj1().getEntityType();
            EntityType   entityType2    =   tripleToken.getObj2().getEntityType();
            addTableInfoToFromSource(result, entityType1);
            addTableInfoToFromSource(result, entityType2);



        }
        return result;
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
            return entityType.getTableInfo().getName()+".* ";
        ColumnInfo  columnInfo  =   entityType.getColumnInfo();
        if(columnInfo.isRelatedField())
        {
            TableInfo   tableInfo   =   columnInfo.getRelatedTable();
            return tableInfo.getName()+".* ";
        }
        else
        {
            TableInfo   tableInfo   =   entityType.getTableInfo();
            return tableInfo.getName()+"."+ columnInfo.getName();
        }

    }
    public  static String genFromQuery(List<TableInfo> tableInfos)
    {
        String result   =  "";
        for(TableInfo tableInfo:tableInfos)
        {
            result  +=  tableInfo.getName()+",";
        }
        if(result.equals(""))
            return "";
        result  =   result.substring(0, result.length()-1);
        return result;
    }
    public  static String genQuery(List<TripleToken> list,EntityType entitypeOfQuestion)
    {
        String query    =   "";
        List<TableInfo> tableInfos  =   getSourceForQuery(list);
        String selectQuery          =   genSelectQuery(entitypeOfQuestion);
        String whereQuery           =   genWhereQuery(list);

        if(selectQuery.equals(""))
            return "";
        query   +=  "SELECT "+selectQuery+" \n\r";
        String fromQuery            =   genFromQuery(tableInfos);
        query   +=  "FROM "+fromQuery +"\n\r";
        query   +=  "WHERE \n\r" +whereQuery;

        return query;
    }
    private static String genConditionForParam(Param    param)
    {
        String condition    =   "";
        
        
        if(param.getColumn().getType()!= null)
        {
        if(param.getColumn().getType().equals(Type.STRING) )
          {
              String keywords[];
              
                 keywords =   CustomSQLUtil.keywords(param.getValue());
             
              if(keywords.length>0)
                  condition+= CustomSQLUtil.AND_OR_CONECTOR+" "+ CustomSQLUtil.createOperatorForField(param.toString(), StringPool.LIKE) +"\n";
               condition =   condition.substring(CustomSQLUtil.AND_OR_CONECTOR.length());
              condition =   CustomSQLUtil.replaceKeywords(condition, param.toString(), StringPool.LIKE, true, keywords);   

          }

          else
          {
             
                condition+= CustomSQLUtil.createOperatorForField(param.toString(),param.getOperator());
          }
        }
        else
          {

                condition+= CustomSQLUtil.createOperatorForField(param.toString(),param.getOperator());
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
                String leftSide  = "`"+tableInfo.getName()+"`"+"."+"`"+tableInfo.getPrimaryKey()+"`";
                String rightSide = "`"+mappingTable.getMappingTableName() + "`" + "." + "`"+mappingTable.getTableKey()+"`";
                if(! leftSide.equals(rightSide))
                {
                    tempConditionQuery1  =   leftSide+" "+StringPool.EQUAL+" "+rightSide;
                }

                String  tempConditionQuery2  =   "";
                 leftSide  = "`"+relatedTable.getName()+"`"+"."+"`"+relatedTable.getPrimaryKey()+"`";
                 rightSide = "`"+mappingTable.getMappingTableName() + "`" + "." + "`"+mappingTable.getRelatedTableKey()+"`";
                if(! leftSide.equals(rightSide))
                {
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
                String leftSide  = "`"+tableInfo.getName()+"`"+"."+"`"+columnInfo.getName()+"`";
                String rightSide = "`"+relatedTable.getName() + "`" + "." + "`"+relatedTable.getPrimaryKey()+"`";
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
