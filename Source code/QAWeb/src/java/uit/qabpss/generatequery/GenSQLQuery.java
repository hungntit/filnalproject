/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uit.qabpss.generatequery;

import java.util.ArrayList;
import java.util.List;
import uit.qabpss.dbconfig.ColumnInfo;
import uit.qabpss.dbconfig.TableInfo;
import uit.qabpss.extracttriple.TripleToken;
import uit.qabpss.preprocess.EntityType;


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
            return null;
        if(entityType.isNull())
            return null;
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
        if(selectQuery.equals(""))
            return "";
        query   +=  "SELECT "+selectQuery+" \n\r";
        String fromQuery            =   genFromQuery(tableInfos);
        query   +=  "FROM "+fromQuery;
        return query;
    }
}
