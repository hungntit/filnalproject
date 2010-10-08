package test;

import org.junit.Test;

import uit.qass.util.similarity.WNSimilarity;

public class SimilarityTest {
	
	@SuppressWarnings("unused")
	@Test
	public void SimilarityTest() {
		// TODO Auto-generated method stub
		System.out.println("compare car and automobile: " + WNSimilarity.compareSense("car", "automobile"));
		System.out.println("compare film and audience: " + WNSimilarity.compareSense("film", "audience"));
		System.out.println("compare person and author: " + WNSimilarity.compareSense("person", "author"));
		System.out.println("compare organization and author: " + WNSimilarity.compareSense("author", "organization"));
	}
}
