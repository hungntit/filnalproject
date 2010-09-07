/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uit.qass.core.search;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.DialectFactory;
import uit.qass.dbconfig.ColumnInfo;
import uit.qass.dbconfig.DBInfo;
import uit.qass.dbconfig.Param;
import uit.qass.dbconfig.TableInfo;
import uit.qass.dbconfig.Type;
import uit.qass.model.Author;
import uit.qass.model.Publication;
import uit.qass.util.StringPool;
import uit.qass.util.dao.orm.CustomSQLUtil;
import uit.qass.util.dao.orm.hibernate.QueryPos;
import uit.qass.util.dao.orm.hibernate.QueryUtil;
import uit.qass.util.hibernate.HibernateUtil;

/**
 *
 * @author ThuanHung
 */
public class UtimateSearch {

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
              condition+= CustomSQLUtil.AND_OR_CONECTOR+" "+ CustomSQLUtil.createOperatorForField(param.toString(), StringPool.LIKE) +"\n";

          }
          else
          {
              if(keyword == null)
                condition+= CustomSQLUtil.AND_OR_CONECTOR + CustomSQLUtil.createOperatorForField(param.toString(), StringPool.EQUAL);
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



    public static void main(String args[]){
        TableInfo dblp_author_ref_new   =   new TableInfo("dblp_author_ref_new", "Author");
        ColumnInfo author               =   new ColumnInfo("author", "Author", Type.STRING);
        ColumnInfo editor               =   new ColumnInfo("editor", "Editor", Type.INTEGER);
        TableInfo dblp_pub_new   =   new TableInfo("dblp_pub_new", "Publisher");
        ColumnInfo title               =   new ColumnInfo("title", "Title", Type.STRING);
        ColumnInfo publisher               =   new ColumnInfo("publisher", "Publisher", Type.STRING);
        ColumnInfo year               =   new ColumnInfo("year", "Year", Type.INTEGER);

        editor.setIsVisible(true);
        dblp_author_ref_new.addColumn(editor);
        dblp_author_ref_new.addColumn(author);

        dblp_pub_new.addColumn(year);
        dblp_pub_new.addColumn(title);
        dblp_pub_new.addColumn(publisher);





        DBInfo  database    =   new DBInfo();
        database.addTable(dblp_author_ref_new);
        database.addTable(dblp_pub_new);
        Param param1 =   new Param(database, dblp_author_ref_new.getName(), author.getName());
        param1.setValue("Philip K.");
        Param param2 =   new Param(database, dblp_author_ref_new.getName(), editor.getName());
        String query1;
 //       query1   =   generateSelectQuery(dblp_author_ref_new,"Philip K.");
 //       String query2   =   generateSelectQuery(new Param[]{param1,param2}, true, dblp_author_ref_new);
  //      System.out.println("Query1 with key:");
  //      System.out.println(query1);
  //      System.out.println("Query2 with params:");
  //      System.out.println(query2);

        Param pub1 =   new Param(database, dblp_pub_new.getName(), title.getName());
        Param pub2 =   new Param(database, dblp_pub_new.getName(), publisher.getName());
        Param pub3 =   new Param(database, dblp_pub_new.getName(), year.getName());
        pub3.setValue("1999");

  //      String pubqr   =   generateSelectQuery(new Param[]{pub3}, true, dblp_pub_new);
   //     System.out.println(pubqr);
        List list   =   searchByParam(Publication.class,new Param[]{pub3}, true, dblp_pub_new, 1, 5);
        System.out.println("END");
    }
    public static List<Object> searchByParam(Class typeclass, Param[] params,boolean isAndOperator,TableInfo selectTable,int start, int end)
    {
        String queryStr    =   generateSelectQuery(params, isAndOperator, selectTable);
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session =   null;
        try
        {
        session= sessionFactory.openSession();

        SQLQuery   q   = session.createSQLQuery(queryStr);
        q.addEntity(typeclass);
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
        return (List<Object>)QueryUtil.list(q, start, end);
        }
        catch(Exception ex)
        {

        }
        finally
        {
            session.close();
        }
        return null;
    }

}
