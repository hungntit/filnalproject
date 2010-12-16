/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uit.qabpss.core.wordnet;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.ISynsetID;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.item.Pointer;
import edu.mit.jwi.item.Synset;
import edu.mit.jwi.morph.WordnetStemmer;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import wordnet.similarity.SimilarityAssessor;
import wordnet.similarity.WordNotFoundException;

/**
 *
 * @author ThuanHung
 */
public class Wordnet {
    public static Dictionary  wndict;
    public static SimilarityAssessor similarityWN;
    public static   WordnetStemmer   wnstemmer   ;
static {
        try {
            initWordnetDictionary("WordNet");
            initSimilarityWordnet();
        } catch (IOException ex) {
            Logger.getLogger(Wordnet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void initWordnetDictionary(String WNdir) throws MalformedURLException
    {
        String path     =   WNdir+"\\" + "dict";
        URL url =   new URL("file", null, path);
        wndict  =   new Dictionary(url);
        wndict.open();
         wnstemmer   =   new WordnetStemmer(Wordnet.wndict);
    }
    public static void initSimilarityWordnet()
    {
        similarityWN  =   new SimilarityAssessor();

    }
    public static float getSimilarityVerb(String verb1,String verb2)
    {
        IIndexWord indexWord1 = wndict.getIndexWord(verb1, POS.VERB);
        IIndexWord indexWord2 = wndict.getIndexWord(verb2, POS.VERB);
        if(indexWord1 == null)
            return 0;
        if(indexWord2 == null)
            return 0;

        List<IWordID> wordIDs1 = indexWord1.getWordIDs();
        List<IWordID> wordIDs2 = indexWord2.getWordIDs();

        for(IWordID wordId1: wordIDs1)
        {
            IWord   word1    =   wndict.getWord(wordId1);
            ISynset synset1 = word1.getSynset();
            for(IWordID wordId2:wordIDs2)
            {
                ISynsetID synsetID2 = wordId2.getSynsetID();
                if(synset1.getOffset() == synsetID2.getOffset())
                {
                    return 1;
                }
            }

            List<ISynsetID> hypernymSynsets = synset1.getRelatedSynsets(Pointer.HYPERNYM);

            for(ISynsetID hypernymsynsetID:hypernymSynsets)
            {

                    for(IWordID wordID2:wordIDs2)
                    {
                        ISynsetID synsetID2 = wordID2.getSynsetID();
                        if(hypernymsynsetID.getOffset() == synsetID2.getOffset())
                            return 0.5f;

                    }

            }

            List<ISynsetID> troponymSynsets = synset1.getRelatedSynsets(Pointer.HYPONYM);

            for(ISynsetID troponymSynsetID:troponymSynsets)
            {

                    for(IWordID wordID2:wordIDs2)
                    {
                        ISynsetID synsetID2 = wordID2.getSynsetID();
                        if(troponymSynsetID.getOffset() == synsetID2.getOffset())
                            return 0.5f;

                    }

            }

        }

        return 0;
    }
    public static double getValueSimilarityNoun(String noun1,String noun2)
    {
        double result = 0.0;
        try {
            result = similarityWN.getSimilarity(noun1, noun2);
        } catch (WordNotFoundException ex) {
            ex.printStackTrace();
        }
        return result;
    }

//    public static void main(String[] args){
//        Wordnet.initSimilarityWordnet();
//        System.out.println(Wordnet.getValueSimilarityNoun("author", "composer"));//0.6
//        System.out.println(Wordnet.getValueSimilarityNoun("author", "publisher"));
//        System.out.println(Wordnet.getValueSimilarityNoun("author", "writer"));
//        System.out.println(Wordnet.getValueSimilarityNoun("author", "creator"));
//        System.out.println("----------------------");
//        System.out.println(Wordnet.getValueSimilarityNoun("publisher", "writer"));
//        System.out.println(Wordnet.getValueSimilarityNoun("publisher", "composer"));
//        System.out.println(Wordnet.getValueSimilarityNoun("publisher", "creator"));
//        System.out.println(Wordnet.getValueSimilarityNoun("publisher", "distributor"));
//        System.out.println(Wordnet.getValueSimilarityNoun("publisher", "author"));//0.6
//        System.out.println("----------------------");
//        System.out.println(Wordnet.getValueSimilarityNoun("publication", "book"));//0.6
//        System.out.println(Wordnet.getValueSimilarityNoun("publication", "paper"));
//        System.out.println("----------------------");
//        System.out.println(Wordnet.getValueSimilarityNoun("title", "heading"));
//    }

}
