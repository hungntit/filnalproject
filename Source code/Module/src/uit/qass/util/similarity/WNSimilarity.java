package uit.qass.util.similarity;

public class WNSimilarity {

	public static double compareSense(String sense1, String sense2) {
		SimilarityAssessor _assessor = new SimilarityAssessor();
		try {
			return _assessor.getSimilarity(sense1, sense2);
		} catch (WordNotFoundException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
