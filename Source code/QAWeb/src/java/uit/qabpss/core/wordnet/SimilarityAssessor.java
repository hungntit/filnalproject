package uit.qabpss.core.wordnet;

import java.io.IOException;
import java.util.LinkedList;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.Hits;

public class SimilarityAssessor {
	/**
	 * Holds a reference to an instance of an Index Broker.
	 */
	private IndexBroker _broker;

	/**
	 * The constructor. Obtains an instance of an Index Broker.
	 */
	public SimilarityAssessor() {
		_broker = IndexBroker.getInstance();
	}

	/**
	 * Calculates the similarity between two specific senses.
	 * 
	 * @param word1
	 *            String
	 * @param senseForWord1
	 *            int The sense number for the first word
	 * @param word2
	 *            String
	 * @param senseForWord2
	 *            int The sense number for the second word
	 * @throws WordNotFoundException
	 *             An exception is thrown if one of the words is not contained
	 *             in the WordNet dictionary.
	 * @return double The degree of similarity between the words; 0 means no
	 *         similarity and 1 means that they may belong to the same synset.
	 */
	public double getSenseSimilarity(String word1, int senseForWord1,
			String word2, int senseForWord2) throws WordNotFoundException {
		Hits synsets1 = _broker.getHits(word1 + "." + senseForWord1);
		Hits synsets2 = _broker.getHits(word2 + "." + senseForWord2);

		if (synsets1.length() == 0) {
			throw new WordNotFoundException("Word " + word1 + "."
					+ senseForWord1 + " is not in the dictionary.");
		}

		if (synsets2.length() == 0) {
			throw new WordNotFoundException("Word " + word2 + "."
					+ senseForWord2 + " is not in the dictionary.");
		}

		try {
			return getSimilarity(synsets1.doc(0), synsets2.doc(0));
		} catch (IOException ex) {
			ex.printStackTrace();
			return 0.0;
		}

	}

	/**
	 * Calculates the similarity between the two words, given as parameters,
	 * according to the referenced paper.
	 * 
	 * @param word1
	 *            String
	 * @param word2
	 *            String
	 * @throws WordNotFoundException
	 *             An exception is thrown if one of the words is not contained
	 *             in the WordNet dictionary.
	 * @return double The degree of similarity between the words; 0 means no
	 *         similarity and 1 means that they may belong to the same synset.
	 */
	public double getSimilarity(String word1, String word2)
			throws WordNotFoundException {
		Hits synsets1 = _broker.getHits(word1 + ".*");
		Hits synsets2 = _broker.getHits(word2 + ".*");

		if (synsets1.length() == 0) {
			throw new WordNotFoundException("Word " + word1
					+ " is not in the dictionary.");
		}

		if (synsets2.length() == 0) {
			throw new WordNotFoundException("Word " + word2
					+ " is not in the dictionary.");
		}

		double current = 0;
		double best = 0;

		try {
			for (int i = 0; i < synsets1.length(); i++) {
				for (int j = 0; j < synsets2.length(); j++) {
					current = getSimilarity(synsets1.doc(i), synsets2.doc(j));

					if (current > best) {
						best = current;
					}
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return best;
	}

	public void printHit(String synsetId) {
		Hits synset = _broker.getHits(IndexBroker.SYNSET + ":" + synsetId);
		try {
			System.out.println("#" + synsetId
					+ synset.doc(0).get(IndexBroker.WORDS));
			System.out.println(":" + synset.doc(0).get(IndexBroker.HYPERNYM));
			System.out.println("IC:"
					+ synset.doc(0).get(IndexBroker.INFORMATION_CONTENT));
		} catch (IOException ex) {
			Logger.getLogger(SimilarityAssessor.class.getName()).log(
					Level.SEVERE, null, ex);
		}
	}

	/**
	 * Does the actual calculation between synsets.
	 * 
	 * @param synset1
	 *            Document
	 * @param synset2
	 *            Document
	 * @return double
	 */
	private double getSimilarity(Document synset1, Document synset2) {
		double msca = getBestMSCAValue(synset1, synset2);
		/*
		 * System.out.println(synset1.get(IndexBroker.WORDS)+":"+synset1.get (
		 * IndexBroker.INFORMATION_CONTENT ));
		 * System.out.println("#"+synset1.get(IndexBroker.HYPERNYM));
		 * System.out.println(synset2.get(IndexBroker.WORDS)+":"+synset2.get (
		 * IndexBroker.INFORMATION_CONTENT ));
		 * System.out.println("#"+synset2.get(IndexBroker.HYPERNYM));
		 */
		if (msca == -1) {
			return 0;
		}

		return 1 - ((Double.parseDouble(synset1
				.get(IndexBroker.INFORMATION_CONTENT))
				+ Double.parseDouble(synset2
						.get(IndexBroker.INFORMATION_CONTENT)) - 2 * msca) / 2);
	}

	/**
	 * Discovers the best Most Specific Common Abstraction (MSCA) value for the
	 * two given Synsets. Note that synsets are represented as Lucene documents.
	 * 
	 * @param doc1
	 *            Document One synset
	 * @param doc2
	 *            Document Another synset
	 * @return double The value of the MSCA with the highest IC value
	 */
	private double getBestMSCAValue(Document doc1, Document doc2) {
		double current = 0;
		double best = 0;
		String offset;

		LinkedList intersection = getIntersection(doc1
				.getValues(IndexBroker.HYPERNYM)[0].split(" "), doc2
				.getValues(IndexBroker.HYPERNYM)[0].split(" "));

		if (intersection.isEmpty()) {
			return -1;
		}

		while (!intersection.isEmpty()) {
			offset = intersection.removeFirst().toString();

			current = getIC(offset);
			if (current > best) {
				best = current;
			}
		}

		return best;
	}

	/**
	 * Obtains the Information Content (IC) value for a given synset offset.
	 * 
	 * @param offset
	 *            String The offset to be queried
	 * @return double The IC value
	 */
	private double getIC(String offset) {
		Hits synset = _broker.getHits(IndexBroker.SYNSET + ":" + offset);
		try {
			return Double.parseDouble(synset.doc(0).get(
					IndexBroker.INFORMATION_CONTENT));
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0.0;
		}
	}

	/**
	 * Gets a list of strings that are contained in both arrays. The strings in
	 * the arrays represent different synsets.
	 * 
	 * @param values1
	 *            String[] An array of synsets
	 * @param values2
	 *            String[] Another array of synsets.
	 * @return LinkedList The list of synsets common to each array
	 */
	private LinkedList getIntersection(String[] values1, String[] values2) {
		LinkedList intersection = new LinkedList();

		for (int i = 0; i < values1.length; i++) {
			for (int j = 0; j < values2.length; j++) {
				if (values1[i].equals(values2[j])) {
					intersection.add(values1[i]);
					break;
				}
			}
		}

		return intersection;
	}

}
