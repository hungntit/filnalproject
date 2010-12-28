/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uit.qabpss.generatequery;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import uit.qabpss.dbconfig.ColumnInfo;
import uit.qabpss.dbconfig.TableInfo;
import uit.qabpss.dbconfig.Type;
import uit.qabpss.entityrecog.Recognizer;
import uit.qabpss.extracttriple.ExtractTriple;
import uit.qabpss.extracttriple.TripleToken;
import uit.qabpss.model.Author;
import uit.qabpss.model.Publication;
import uit.qabpss.preprocess.EntityType;
import uit.qabpss.preprocess.SentenseUtil;
import uit.qabpss.preprocess.Token;
import uit.qabpss.util.StringPool;
import uit.qabpss.util.dao.orm.CustomSQLUtil;
import uit.qabpss.util.dao.orm.hibernate.QueryPos;
import uit.qabpss.util.dao.orm.hibernate.QueryUtil;
import uit.qabpss.util.hibernate.HibernateUtil;

/**
 *
 * @author ThuanHung
 */
public class RetrieveData {

    public static void main(String[] args) throws IOException {
        // List of test questions here
        HibernateUtil.getSessionFactory();
        String[] questions = new String[]{
           "Which books were written by Rafiul Ahad and Amelia Carlson in 2010 ? ",
            "Which books were written by Rafiul Ahad from 1999 to 2010 ?",
            "Which books were published by O'Reilly  in 1999 ?",
            "How many papers were written by Rafiul Ahad ?",
            "Who compose books in 1999 ?",
            "Who write books from 1999 to 2010 ?",
            "How many papers were written by Rafiul Ahad in 2010 ?",
            "Who published books from 1999 to 2000 ?",
            "Who published books in 1999 ?",
            "What are titles of books written by Marcus Thint ?",
            "What papers did Jennifer Widom write ?",
            "What books did Jennifer Widom write ?",
            "Who is the author of  \"Working Models for Uncertain Data\" and \"Active Database Systems.\"",
            "Who is the author of  \"Working Models for Uncertain Data\"",
            "What book did Philip K. Chan write in 1999?",
            "What book did Philip K. Chan write from 1999 to 2000?",
            "What are the titles of the books published by O'reilly in 1999 ?",
            "What composer wrote \" Java 2D Graphics\"",
            "What books has isbn 1-56592-484-3",
            "What books has doi 10.1145/360271.360274",
            "What composer wrote books from 1999 in ACM?",
            "Who is the author of the paper \"Question Classification using Head Words and their Hypernyms.\"?",
            "Who wrote \"Question Classification using Head Words and their Hypernyms.\"?",
            "What books were written by \"Philip K. Chan\" from ACM?",
            "How many publisher did \"Richard L. Muller\" cooperate with?",
            "What books were published by ACM or Springer in 2010?",
            "What publications have resulted from TREC?",
            "What publications have resulted from TREC in 1999?",
            "Which author write \"Foundations of Databases.\" in 1999",
            "Which books did Philip K. Chan or Marcus Thint write in ACM ?",
            "What book did Philip K. Chan write in 1999 from ACM?",
            "Name some books which Richard L. Muller writes for Springer",
            "List all books were published by Springer in 2010",
            "What year is \"Foundations of Databases.\" written in?",// not solve yet // FAIL TEST REV 234
            "What books refer to \"Foundations of Databases.\"",
            "What books did Richard L. Muller write for Springer",
        };
        System.out.println("nums test: " + questions.length);
        ExtractTriple extract = new ExtractTriple();
        Recognizer  reg     =   new Recognizer();
        int count = 1;
        for (String question : questions) {
            Date    date    =   new Date();
            long begin   =   date.getTime();
            System.out.println("----------------------------------------------------------------------------");
            Token[] tokens = SentenseUtil.formatNerWordInQuestion(question);
            tokens = SentenseUtil.optimizePosTags(tokens);
            System.out.println(count+"/");
            count++;
            System.out.println(SentenseUtil.tokensToStr(tokens));
            List<TripleToken> list = extract.extractTripleWordRelation(tokens);
            reg.identifyTripleTokens(list);
            for(TripleToken tripleToken:list)
            {
                System.out.println(tripleToken);
                //reg.identifyTripleToken(tripleToken);
                if(!tripleToken.isNotIdentified())
                {
                    System.out.println(tripleToken.getObj1().toString()+":"+tripleToken.getObj1().getEntityType().toString() +","+tripleToken.getObj2().toString()+":"+tripleToken.getObj2().getEntityType().toString());
                }

            }
             System.out.println();

             EntityType entityTypeOfQuestion    =   reg.recognizeEntityOfQuestion(tokens);
             String selectandFromQuery  =   GenSQLQuery.genQuery(list, entityTypeOfQuestion);
             System.out.println(selectandFromQuery);
            List retrieveData = retrieveData(list, entityTypeOfQuestion, 0, 100);
            for(Object object: retrieveData)
            {
                if(object!= null)
                    System.out.println(object.toString());
            }
            date    =   new Date();
            double end    =   date.getTime();
            System.out.println("Time: "+(end - begin)/1000);
        }
    }

    public static  Class getTypeOfEntity(EntityType typeOfQuestion)
    {
        ColumnInfo  columnInfo  =   typeOfQuestion.getColumnInfo();
        TableInfo   tableInfo   =   typeOfQuestion.getTableInfo();
        EntityType entityType   =   new EntityType(tableInfo, columnInfo);

        if(typeOfQuestion.isColumn())
        {
            if(typeOfQuestion.getColumnInfo().isRelatedField())
            {
                 tableInfo = columnInfo.getRelatedTable();
                ColumnInfo  relatedColumnInfo  =   null;
                if(columnInfo.getName()!= null)
                {
                    relatedColumnInfo  =   tableInfo.findColumnByName(columnInfo.getName());
                }

                entityType   =   new EntityType(tableInfo, relatedColumnInfo);
            }
        }
        if(entityType.isTable())
        {
            if(entityType.getTableInfo().getAliasName().equals("Author"))
            {
                return Author.class;
            }
            if(entityType.getTableInfo().getAliasName().equals("Publication"))
            {
                return Publication.class;
            }
            if(entityType.getTableInfo().getAliasName().equals("Reference"))
            {
                return Publication.class;
            }


        }
        else
        {

            if(entityType.getColumnInfo().getType().equals(Type.STRING))
            {
                return String.class;
            }
            if(entityType.getColumnInfo().getType().equals(Type.INTEGER))
            {
                return int.class;
            }
            if(entityType.getColumnInfo().getType().equals(Type.LONG))
            {
                return long.class;
            }
        }
        return Object.class;
    }

    public static List  retrieveData(List<TripleToken> tripleTokens,EntityType typeOfQuestion,int start,int end)
    {
        Class typeclass =   getTypeOfEntity(typeOfQuestion);
        String queryStr    =   GenSQLQuery.genQuery(tripleTokens, typeOfQuestion);
        String genSelectQuery = GenSQLQuery.genSelectQuery(typeOfQuestion);
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session =   null;
        try
        {
        session= sessionFactory.openSession();

        SQLQuery   q   = session.createSQLQuery(queryStr);
        //System.out.println(queryStr);
        if(typeclass.equals(int.class) || typeclass.equals(long.class))
        {
            q.addScalar(genSelectQuery, org.hibernate.Hibernate.LONG);
            
        }
        else
        {
            if(typeclass.equals(String.class))
                q.addScalar(genSelectQuery, org.hibernate.Hibernate.STRING);
            else
            {
                q.addEntity(typeclass);
            }
        }



        QueryPos    qPos    =   QueryPos.getInstance(q);


        for(TripleToken tripleToken:tripleTokens)
        {
            Token obj1  =   tripleToken.getObj1();
            Token obj2  =   tripleToken.getObj2();
            
            if(obj1.isNe())
            {
                ColumnInfo presentationField = obj1.getEntityType().getTableInfo().getPresentationField();
                ColumnInfo columnInfo   =   presentationField;

                if(tripleToken.getOperator().equalsIgnoreCase(StringPool.LIKE))
                {
                     String keywords[] =   CustomSQLUtil.keywords(obj1.getValue());
                     qPos.add(keywords, 2);
                }
                else
                {
                    if(!columnInfo.getType().equals(Type.STRING))
                        qPos.add(obj1.getValue().toString(), columnInfo.getType());
                     else
                         qPos.add(obj1.getValue().toString());
                }
            }

            if(obj2.isNe())
            {
                TableInfo tableInfo  =   obj2.getEntityType().getTableInfo();
                ColumnInfo columnInfo = obj2.getEntityType().getColumnInfo();
                ColumnInfo presentationField = tableInfo.getPresentationField();
                if(columnInfo.isRelatedField())
                {
                    tableInfo   =   columnInfo.getRelatedTable();
                    presentationField = tableInfo.getPresentationField();
                    if(columnInfo.getName()!=null)

                        columnInfo      =   tableInfo.findColumnByName(columnInfo.getName());
                    else
                        columnInfo  =   presentationField;
                }
                

                
                if(columnInfo   ==  null)
                {
                    columnInfo  =   presentationField;
                }
                if(tripleToken.getOperator().equalsIgnoreCase(StringPool.LIKE))
                {
                     String keywords[] =   CustomSQLUtil.keywords(obj2.getValue());
                     qPos.add(keywords, 2);
                }
                else
                {
                     if(!columnInfo.getType().equals(Type.STRING))
                        qPos.add(obj2.getValue().toString(), columnInfo.getType());
                     else
                         qPos.add(obj2.getValue().toString());
                }
            }
        }
        return (List<Object>)QueryUtil.list(q,start,end);
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
}
