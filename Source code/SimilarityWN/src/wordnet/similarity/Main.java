package wordnet.similarity ;

import wordnet.similarity.SimilarityAssessor ;




/**
 *
 * <p>Title: Java WordNet Similarity</p>
 * <p>Description: Assesses the semantic similarity between a pair of words
 * as described in Seco, N., Veale, T., Hayes, J. (2004) "An Intrinsic Information
 * Content Metric for Semantic Similarity in WordNet". In Proceedings of the
 * European Conference of Artificial Intelligence </p>
 * <p>This Class is an example of how the similarity function
 * can be called.</p>
 * <p>Copyright: Nuno Seco Copyright (c) 2004</p>
 * @author Nuno Seco
 * @version 1.0
 */

public class Main
{
  /**
   * Holds a reference to an instance of a Similarity Assessor.
   * The Similarity Assessor calculates the semantic similarity
   * between two words.
   */
  private SimilarityAssessor _assessor ;
  
  
  
  
  /**
   * The constructor. Creates an instance of the Simialrity
   * Assessor and calls the exmaples method.
   */
  public Main ()
  {
    _assessor = new SimilarityAssessor () ;
    doExamples () ;
  }
  
  
  
  
  /**
   * This method executes semantic similarity assessments of 30
   * pairs of words. These  are the same pairs used by George Miller
   * and W.G. Charles in  �Contextual correlates of semantic
   * similarity�, Language and Cognitive Processes, 6, 1�28, (1991).
   */
  private void doExamples ()
  {
    try
    {
       
      System.out.println ( "similarity between participant and game:  " +
          _assessor.getSimilarity ( "paper" , "book" ) ) ;
     System.out.println ( "similarity between car and automobile:  " +
          _assessor.getSimilarity ( "car" , "automobile" ) ) ;
      System.out.println ( "similarity between gem and jewel:  " +
          _assessor.getSimilarity ( "gem" , "jewel" ) ) ;
      System.out.println ( "similarity between journey and voyage:  " +
          _assessor.getSimilarity ( "journey" , "voyage" ) ) ;
      System.out.println ( "similarity between boy and lad:  " +
          _assessor.getSimilarity ( "boy" , "lad" ) ) ;
      System.out.println ( "similarity between coast and shore:  " +
          _assessor.getSimilarity ( "coast" , "shore" ) ) ;
      System.out.println ( "similarity between asylum and madhouse:  " +
          _assessor.getSimilarity ( "asylum" , "madhouse" ) ) ;
      System.out.println ( "similarity between magician and wizard:  " +
          _assessor.getSimilarity ( "magician" , "wizard" ) ) ;
      System.out.println ( "similarity between midday and noon:  " +
          _assessor.getSimilarity ( "midday" , "noon" ) ) ;
      System.out.println ( "similarity between furance and stove:  " +
          _assessor.getSimilarity ( "furnace" , "stove" ) ) ;
      System.out.println ( "similarity between food and fruit:  " +
          _assessor.getSimilarity ( "food" , "fruit" ) ) ;
      System.out.println ( "similarity between bird and cock:  " +
          _assessor.getSimilarity ( "bird" , "cock" ) ) ;
      System.out.println ( "similarity between bird and crane:  " +
          _assessor.getSimilarity ( "bird" , "crane" ) ) ;
      System.out.println ( "similarity between tool and implement:  " +
          _assessor.getSimilarity ( "tool" , "implement" ) ) ;
      System.out.println ( "similarity between brother and monk:  " +
          _assessor.getSimilarity ( "brother" , "monk" ) ) ;
      System.out.println ( "similarity between crane and implement:  " +
          _assessor.getSimilarity ( "crane" , "implement" ) ) ;
      System.out.println ( "similarity between lad and brother:  " +
          _assessor.getSimilarity ( "lad" , "brother" ) ) ;
      System.out.println ( "similarity between journey and car:  " +
          _assessor.getSimilarity ( "journey" , "car" ) ) ;
      System.out.println ( "similarity between monk and oracle:  " +
          _assessor.getSimilarity ( "monk" , "oracle" ) ) ;
      System.out.println ( "similarity between cemtery and woodland:  " +
          _assessor.getSimilarity ( "cemetery" , "woodland" ) ) ;
      System.out.println ( "similarity between food and rooster:  " +
          _assessor.getSimilarity ( "food" , "rooster" ) ) ;
      System.out.println ( "similarity between coast and hill:  " +
          _assessor.getSimilarity ( "coast" , "hill" ) ) ;
      System.out.println ( "similarity between forest and graveyard:  " +
          _assessor.getSimilarity ( "forest" , "graveyard" ) ) ;
      System.out.println ( "similarity between shore and woodland:  " +
          _assessor.getSimilarity ( "shore" , "woodland" ) ) ;
      System.out.println ( "similarity between monk and slave:  " +
          _assessor.getSimilarity ( "monk" , "slave" ) ) ;
      System.out.println ( "similarity between coast and forest:  " +
          _assessor.getSimilarity ( "coast" , "forest" ) ) ;
      System.out.println ( "similarity between lad and wizard:  " +
          _assessor.getSimilarity ( "lad" , "wizard" ) ) ;
      System.out.println ( "similarity between chord and smile:  " +
          _assessor.getSimilarity ( "chord" , "smile" ) ) ;
      System.out.println ( "similarity between glass and magician:  " +
          _assessor.getSimilarity ( "glass" , "magician" ) ) ;
      System.out.println ( "similarity between noon and string:  " +
          _assessor.getSimilarity ( "noon" , "string" ) ) ;
      System.out.println ( "similarity between rooster and voyage:  " +
          _assessor.getSimilarity ( "rooster" , "voyage" ) ) ;
      
      System.out.println ( "similarity between art and creation:  " +
          _assessor.getSimilarity ( "art" , "creation" )                   ) ;
      
      System.out.println ( "similarity between honey and sugar:  " +
          _assessor.getSimilarity ( "honey" , "sugar" ) ) ;
      System.out.println ( "similarity between film and audience:  " +
          _assessor.getSimilarity ( "film" , "audience" ) ) ;
      
        _assessor.printHit("1740");
      System.out.println ("");
      System.out.println ("Using specific senses....");
      System.out.println ( "similarity between sense 2 of game and sense 2 of participant:  " +
          _assessor.getSenseSimilarity ( "athlete" , 1, "car" ,1) ) ;
    }
    catch ( Exception ex )
    {
      ex.printStackTrace () ;
    }
    
  }
  
  
  
  
  /**
   * The program execution entry point.
   * @param args String[] Contains command line arguments
   */
  public static void main ( String args[] )
  {
    new Main () ;
  }
}
