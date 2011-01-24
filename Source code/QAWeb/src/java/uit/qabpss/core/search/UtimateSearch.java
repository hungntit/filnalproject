/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uit.qabpss.core.search;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import uit.qabpss.dbconfig.ColumnInfo;
import uit.qabpss.dbconfig.DBInfo;
import uit.qabpss.dbconfig.DBInfoUtil;
import uit.qabpss.dbconfig.Param;
import uit.qabpss.dbconfig.TableInfo;
import uit.qabpss.dbconfig.Type;
import uit.qabpss.model.Publication;
import uit.qabpss.util.StringPool;
import uit.qabpss.util.Table;
import uit.qabpss.util.dao.orm.CustomSQLUtil;
import uit.qabpss.util.dao.orm.hibernate.QueryPos;
import uit.qabpss.util.dao.orm.hibernate.QueryUtil;
import uit.qabpss.util.hibernate.HibernateUtil;

/**
 *
 * @author ThuanHung
 */
public class UtimateSearch {

    public static String COUNT_VALUE    =   "count_value";
    public static String generateSelectQuery(TableInfo selectTable,String keyword)
    {
        int size    =   selectTable.getColumns().size();
        if(size<1)
            return null;
        Param   params[]    =   new Param[size];
        for(int i=0;i<size;i++)
        {
            ColumnInfo column   =   selectTable.getColumns().get(i);
             params[i]   =   new Param( selectTable, column);
        }
        return generateSelectQuery(params, false, selectTable, keyword);
    }
    public static String generateSelectQuery(Param[] params,boolean isAndOperator,TableInfo selectTable)
    {
        return generateSelectQuery(params, isAndOperator, selectTable, null);
    }
    public static String generateSelectQuery(Param[] params,boolean isAndOperator)
    {
        return generateSelectQuery(params, isAndOperator, null, null);
    }
    protected static String generateSelectQuery(Param[] params,boolean isAndOperator,TableInfo selectTable,String keyword)
    {

      String AndOroperator;
      if(isAndOperator)
          AndOroperator =   "AND";
      else
          AndOroperator =   "OR";

      String query      =   "SELECT\n";
      int count         =   params.length;
      if(count<1)
          return null;
      //---------------Select---------------------------------//
      if(selectTable ==null)
      {
          for(Param param:params)
          {
              if(param.getColumn().isIsVisible())
                query += param+",";
          }
          query =   query.substring(0, query.length()-1);
       }
      else
      {
            query += selectTable.getName()+".*\n";
      }
      //!---------------End Select----------------------------//

      //---------------From----------------------------------//

      query+="FROM ";
      List<TableInfo> tables    =       new ArrayList<TableInfo>();
      for(Param param:params)
      {
          if(tables.contains(param.getTable()))
          {
              continue;
          }
          if(param.getColumn().isIsVisible())
            tables.add(param.getTable());
          query +=  "\n"+param.getTable()+",";

      }
      query =   query.substring(0, query.length()-1);
      //!---------------End From-----------------------------//

      //---------------Where--------------------------------//
      query+= "\nWHERE";
      String condition   =   "";
      for(Param param:params)
      {
          if(!param.getColumn().isIsVisible())
              continue;
          if(param.getColumn().getType().equals(Type.STRING))
          {
              String keywords[];
              if(keyword == null)
                 keywords =   CustomSQLUtil.keywords(param.getValue());
              else
                  keywords =   CustomSQLUtil.keywords(keyword);
              if(keywords.length>0)
                  condition+= CustomSQLUtil.AND_OR_CONECTOR+" "+ CustomSQLUtil.createOperatorForField(param.toString(), StringPool.LIKE) +"\n";

          }
          else
          {
              if(keyword == null)
                condition+= CustomSQLUtil.AND_OR_CONECTOR + CustomSQLUtil.createOperatorForField(param.toString(),param.getOperator());
          }

      }
      condition =   condition.substring(CustomSQLUtil.AND_OR_CONECTOR.length());
      query+="\n"+ condition;
      for(Param param:params)
      {
          if(param.getColumn().getType().equals(Type.STRING))
          {
              String keywords[];
              if(keyword == null)
                 keywords =   CustomSQLUtil.keywords(param.getValue());
              else
                  keywords =   CustomSQLUtil.keywords(keyword);
              query =   CustomSQLUtil.replaceKeywords(query, param.toString(), StringPool.LIKE, true, keywords);

          }
      }
      query =   CustomSQLUtil.replaceAndOperator(query,isAndOperator);
      //!---------------End Where----------------------------//

      return query;
    }
    public static List searchByParam(Class typeclass, Param[] params,boolean isAndOperator,TableInfo selectTable,int start, int end)
    {
        String queryStr    =   generateSelectQuery(params, isAndOperator, selectTable);
        System.out.println(queryStr);
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session =   null;
        try
        {
        session= sessionFactory.openSession();

        SQLQuery   q   = session.createSQLQuery(queryStr);
        q.addEntity(typeclass);
        QueryPos    qPos    =   QueryPos.getInstance(q);
        q.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        for(Param param:params)
        {
            if(param.getColumn().getType().equals(Type.STRING))
            {
                String keywords[] =   CustomSQLUtil.keywords(param.getValue());
                qPos.add(keywords, 2);
            }
            else
            {
                qPos.add(param.getValue(), param.getColumn().getType());
            }

        }
        return (List)QueryUtil.list(q, start, end);
        }
        catch(Exception ex)
        {

        }
        finally
        {
            
        }
        return null;
    }


    public static List<Object> searchByKeyWords(Class typeclass, String keyword,TableInfo selectTable,int start, int end)
    {
        int size    =   selectTable.getColumns().size();
        if(size<1)
            return null;
        List<Param>   listparam    =   Param.getParamsFromTableInfo(selectTable);
        Param[]         params      =   listparam.toArray(new Param[listparam.size()]);

        String queryStr    =   generateSelectQuery(params, false, selectTable,keyword);
        //System.out.println(queryStr);
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session =   null;
        try
        {
        session= sessionFactory.openSession();

        SQLQuery   q   = session.createSQLQuery(queryStr);
        q.addEntity(typeclass);
        QueryPos    qPos    =   QueryPos.getInstance(q);
        String keywords[] =   CustomSQLUtil.keywords(keyword);
        for(Param param:params)
        {
            if(param.getColumn().getType().equals(Type.STRING))
            {

                qPos.add(keywords, 2);
            }
            

        }
        return (List<Object>)QueryUtil.list(q, start, end);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            
        }
        return null;
    }

/*
 *
 */

    public static String generateCountQuery(TableInfo selectTable,String keyword)
    {
        int size    =   selectTable.getColumns().size();
        if(size<1)
            return null;
        Param   params[]    =   new Param[size];
        for(int i=0;i<size;i++)
        {
            ColumnInfo column   =   selectTable.getColumns().get(i);
             params[i]   =   new Param( selectTable, column);
        }
        return generateCountQuery(params, false, selectTable, keyword);
    }
    public static String generateCountQuery(Param[] params,boolean isAndOperator,TableInfo selectTable)
    {
        return generateCountQuery(params, isAndOperator, selectTable, null);
    }
    public static String generateCountQuery(Param[] params,boolean isAndOperator)
    {
        return generateCountQuery(params, isAndOperator, null, null);
    }
    protected static String generateCountQuery(Param[] params,boolean isAndOperator,TableInfo selectTable,String keyword)
    {

      String AndOroperator;
      if(isAndOperator)
          AndOroperator =   "AND";
      else
          AndOroperator =   "OR";

      String query      =   "SELECT\n";
      int count         =   params.length;
      if(count<1)
          return null;
      //---------------Select---------------------------------//
      if(selectTable ==null)
      {
          query += "count(DISTINCT "+params[0]+") as "+ COUNT_VALUE +"\n";
       }
      else
      {
            query += "count(DISTINCT "+selectTable.getName()+"."+selectTable.getPrimaryKey()+") as "+COUNT_VALUE+"\n";
      }
      //!---------------End Select----------------------------//

      //---------------From----------------------------------//

      query+="FROM ";
      List<TableInfo> tables    =       new ArrayList<TableInfo>();
      for(Param param:params)
      {
          if(tables.contains(param.getTable()))
          {
              continue;
          }
          if(param.getColumn().isIsVisible())
            tables.add(param.getTable());
          query +=  "\n"+param.getTable()+",";

      }
      query =   query.substring(0, query.length()-1);
      //!---------------End From-----------------------------//

      //---------------Where--------------------------------//
      query+= "\nWHERE";
      String condition   =   "";
      for(Param param:params)
      {
          if(!param.getColumn().isIsVisible())
              continue;
          if(param.getColumn().getType().equals(Type.STRING))
          {
              String keywords[];
              if(keyword == null)
                 keywords =   CustomSQLUtil.keywords(param.getValue());
              else
                  keywords =   CustomSQLUtil.keywords(keyword);
              if(keywords.length>0)
                  condition+= CustomSQLUtil.AND_OR_CONECTOR+" "+ CustomSQLUtil.createOperatorForField(param.toString(), StringPool.LIKE) +"\n";

          }
          else
          {
              if(keyword == null)
                condition+= CustomSQLUtil.AND_OR_CONECTOR + CustomSQLUtil.createOperatorForField(param.toString(),param.getOperator());
          }

      }
      condition =   condition.substring(CustomSQLUtil.AND_OR_CONECTOR.length());
      query+="\n"+ condition;
      for(Param param:params)
      {
          if(param.getColumn().getType().equals(Type.STRING))
          {
              String keywords[];
              if(keyword == null)
                 keywords =   CustomSQLUtil.keywords(param.getValue());
              else
                  keywords =   CustomSQLUtil.keywords(keyword);
              query =   CustomSQLUtil.replaceKeywords(query, param.toString(), StringPool.LIKE, true, keywords);

          }
      }
      query =   CustomSQLUtil.replaceAndOperator(query,isAndOperator);
      //!---------------End Where----------------------------//

      return query;
    }
 /*
  *
  */

    public static int countByParam( Param[] params,boolean isAndOperator,TableInfo selectTable)
    {
        String queryStr    =   generateCountQuery(params, isAndOperator, selectTable);
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session =   null;
        try
        {
        session= sessionFactory.openSession();

        SQLQuery   q   = session.createSQLQuery(queryStr);
        //System.out.println(queryStr);
        q.addScalar(COUNT_VALUE, org.hibernate.Hibernate.LONG);
        QueryPos    qPos    =   QueryPos.getInstance(q);

        for(Param param:params)
        {
            if(param.getColumn().getType().equals(Type.STRING))
            {
                String keywords[] =   CustomSQLUtil.keywords(param.getValue());
                qPos.add(keywords, 2);
            }
            else
            {
                qPos.add(param.getValue(), param.getColumn().getType());
            }

        }
         Iterator<Long> itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}


        }
        catch(Exception ex)
        {

        }
        finally
        {

        }
        return 0;
    }


    public static int countByKeyWords( String keyword,TableInfo selectTable)
    {
        int size    =   selectTable.getColumns().size();
        if(size<1)
            return 0;
        List<Param>   listparam    =   Param.getParamsFromTableInfo(selectTable);
        Param[]         params      =   listparam.toArray(new Param[listparam.size()]);

        String queryStr    =   generateSelectQuery(params, false, selectTable,keyword);
        //System.out.println(queryStr);
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session =   null;
        try
        {
        session= sessionFactory.openSession();

        SQLQuery   q   = session.createSQLQuery(queryStr);
        q.addScalar(COUNT_VALUE,org.hibernate.Hibernate.LONG);
        QueryPos    qPos    =   QueryPos.getInstance(q);
        String keywords[] =   CustomSQLUtil.keywords(keyword);
        for(Param param:params)
        {
            if(param.getColumn().getType().equals(Type.STRING))
            {

                qPos.add(keywords, 2);
            }
            

        }
        Iterator<Long> itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}


        }
        catch(Exception ex)
        {

        }
        finally
        {

        }
        return 0;
    }

public static List<String> getListDefaultValueFromColumn(String columnName, String  tableName)
    {

        String column   =   columnName.trim().split(" ")[0];
        String table   =   tableName.trim().split(" ")[0];
        String queryStr    =   "SELECT\n";
               queryStr   +=   "DISTINCT "+column+"\n";
               queryStr   +=   "FROM\n";
               queryStr   +=   table;


        //System.out.println(queryStr);
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session =   null;
        try
        {
        session= sessionFactory.openSession();

        SQLQuery   q   = session.createSQLQuery(queryStr);
        q.addScalar(columnName,org.hibernate.Hibernate.STRING);
        QueryPos    qPos    =   QueryPos.getInstance(q);


        return q.list();



        }
        catch(Exception ex)
        {

        }
        finally
        {

        }
        return null;
}
protected static String generateCountLimitQuery(Param[] params,boolean isAndOperator,TableInfo selectTable,int limit)
    {
    return generateCountLimitQuery(params, isAndOperator, selectTable, null, limit);
    }
protected static String generateCountLimitQuery(Param[] params,boolean isAndOperator,TableInfo selectTable,String keyword,int limit)
    {

      String AndOroperator;
      if(isAndOperator)
          AndOroperator =   "AND";
      else
          AndOroperator =   "OR";

      String query      =   "SELECT count(*) as "+ COUNT_VALUE +"\n FROM (SELECT\n";
      int count         =   params.length;
      if(count<1)
          return null;
      //---------------Select---------------------------------//
      if(selectTable ==null)
      {
          query += "DISTINCT "+params[0]+"\n";
       }
      else
      {
            query += "DISTINCT "+selectTable.getName()+"."+selectTable.getPrimaryKey()+"\n";
      }
      //!---------------End Select----------------------------//

      //---------------From----------------------------------//

      query+="FROM ";
      List<TableInfo> tables    =       new ArrayList<TableInfo>();
      for(Param param:params)
      {
          if(tables.contains(param.getTable()))
          {
              continue;
          }
          if(param.getColumn().isIsVisible())
            tables.add(param.getTable());
          query +=  "\n"+param.getTable()+",";

      }
      query =   query.substring(0, query.length()-1);
      //!---------------End From-----------------------------//

      //---------------Where--------------------------------//
      query+= "\nWHERE";
      String condition   =   "";
      for(Param param:params)
      {
          if(!param.getColumn().isIsVisible())
              continue;
          if(param.getColumn().getType().equals(Type.STRING))
          {
              String keywords[];
              if(keyword == null)
                 keywords =   CustomSQLUtil.keywords(param.getValue());
              else
                  keywords =   CustomSQLUtil.keywords(keyword);
              if(keywords.length>0)
                  condition+= CustomSQLUtil.AND_OR_CONECTOR+" "+ CustomSQLUtil.createOperatorForField(param.toString(), StringPool.LIKE) +"\n";

          }
          else
          {
              if(keyword == null)
                condition+= CustomSQLUtil.AND_OR_CONECTOR + CustomSQLUtil.createOperatorForField(param.toString(),param.getOperator());
          }

      }
      condition =   condition.substring(CustomSQLUtil.AND_OR_CONECTOR.length());
      query+="\n"+ condition;
      for(Param param:params)
      {
          if(param.getColumn().getType().equals(Type.STRING))
          {
              String keywords[];
              if(keyword == null)
                 keywords =   CustomSQLUtil.keywords(param.getValue());
              else
                  keywords =   CustomSQLUtil.keywords(keyword);
              query =   CustomSQLUtil.replaceKeywords(query, param.toString(), StringPool.LIKE, true, keywords);

          }
      }
      query =   CustomSQLUtil.replaceAndOperator(query,isAndOperator);
      //!---------------End Where----------------------------//
      query+=" LIMIT "+limit;
      query+=") temptable";
      return query;
    }
 /*
  *
  */

    public static int countLimitByParam( Param[] params,boolean isAndOperator,TableInfo selectTable,int limit)
    {
        String queryStr    =   generateCountLimitQuery(params, isAndOperator, selectTable,limit);
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session =   null;
        try
        {
        session= sessionFactory.openSession();

        SQLQuery   q   = session.createSQLQuery(queryStr);
        //System.out.println(queryStr);
        q.addScalar(COUNT_VALUE, org.hibernate.Hibernate.LONG);
        QueryPos    qPos    =   QueryPos.getInstance(q);

        for(Param param:params)
        {
            if(param.getColumn().getType().equals(Type.STRING))
            {
                String keywords[] =   CustomSQLUtil.keywords(param.getValue());
                qPos.add(keywords, 2);
            }
            else
            {
                qPos.add(param.getValue(), param.getColumn().getType());
            }

        }
         Iterator<Long> itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}


        }
        catch(Exception ex)
        {

        }
        finally
        {

        }
        return 0;
    }



    public static void main(String args[]){
        DBInfoUtil.initDb();
        TableInfo returnTbl     =       DBInfoUtil.getDBInfo().findTableInfoByName(Table.PUBLICATION);
        List    list            =       searchByKeyWords(returnTbl.getClassTable(), "Life is", returnTbl, 0 , 30);
        Param param =   new Param(new TableInfo("A","a"),new ColumnInfo("B", "b", Type.LONG));
        String query    =   generateCountLimitQuery(new Param[]{param}, true, null, 1);
        int a=0;
    }

}
