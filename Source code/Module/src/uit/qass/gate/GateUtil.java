package uit.qass.gate;

import gate.Annotation;
import gate.AnnotationSet;
import gate.Corpus;
import gate.Document;
import gate.DocumentContent;
import gate.Factory;
import gate.Gate;
import gate.creole.ResourceInstantiationException;
import gate.util.GateException;
import gate.util.Out;
import gate.util.web.WebCrimeReportAnalyser.SortedAnnotationList;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class GateUtil {

	private static final String LOOKUP = "Lookup";

	private static final String TOKEN = "Token";

	private static final String ANNIE = "ANNIE";

	private static final String D_DIR = "plugins";

	private static final String GATE_CONFIG_XML = "gate.xml";

	private static Document doc = null;

	private static Corpus corpus = null;

	private static StandAloneAnnie annie = null;

	public static void RunGate() {
		Out.prln("Initiation GATE API");
		if(!Gate.isInitialised()){
			gateInit();
			Out.prln("Load ANNIE PLUGIN");
			loadAnniePlugin();
			Out.prln("FINISH !");
			return;
		}
		Out.prln("Gate already loaded !");
	}

	public static void gateInit() {
		// Init Gate : set Gate Home ,Plugin Home and config file .
		Gate.setGateHome(new File(D_DIR));
		Gate.setPluginsHome(new File(D_DIR));
		Gate.setSiteConfigFile(new File(GATE_CONFIG_XML));
		try {
			Gate.init();
		} catch (GateException e) {
			e.printStackTrace();
		}
	}

	public static void loadAnniePlugin() {
		// Load ANNIE plugin
		File gateHome = Gate.getGateHome();
		File pluginsHome = new File(gateHome, "");
		Out.prln("...GATE initialised");
		try {
			Gate.getCreoleRegister().registerDirectories(
					new File(pluginsHome, ANNIE).toURL());
			annie = new StandAloneAnnie();
			// initialise ANNIE (this may take several minutes)
			annie.initAnnie();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (GateException e) {
			e.printStackTrace();
		}
	}


	@SuppressWarnings("unchecked")
	public static void setupDocumentAndCorpus(String sentence) {
		try {
			corpus = (Corpus) Factory.createResource("gate.corpora.CorpusImpl");
			doc = (Document) Factory.newDocument(sentence);
			if (doc != null && annie != null) {
				corpus.add(doc);
				annie.setCorpus(corpus);
				// Get content and print it into console
				DocumentContent docContent = doc.getContent();
			}
			annie.execute();
		} catch (ResourceInstantiationException e) {
			e.printStackTrace();
		} catch (GateException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static String getGazeteerClass(String pharse) {
		Document tempDoc = null;
		Corpus tempCorpus = null;
		// set up documnet and corpus
		try {
			tempCorpus = (Corpus) Factory
					.createResource("gate.corpora.CorpusImpl");
			tempDoc = (Document) Factory.newDocument(pharse);
			if (tempDoc != null && annie != null) {
				tempCorpus.add(tempDoc);
				annie.setCorpus(tempCorpus);
			}
			annie.execute();
		} catch (ResourceInstantiationException e) {
			e.printStackTrace();
		} catch (GateException e) {
			e.printStackTrace();
		}
		
		Iterator<Document> iter = tempCorpus.iterator();

		while (iter.hasNext()) {
			Document doc = iter.next();
			AnnotationSet defaultAnnotSet = doc.getAnnotations();
			Set<String> annotTypesRequired = new HashSet<String>();
			annotTypesRequired.add(LOOKUP);
			Annotation currAnnot;
			StringBuffer editableContent = new StringBuffer(doc.getContent()
					.toString());

			Out.prln("Get all Annotation Set");
			Set<Annotation> token = new HashSet<Annotation>(defaultAnnotSet
					.get(annotTypesRequired));

			SortedAnnotationList sortedAnnotations = new SortedAnnotationList();

			Iterator<Annotation> it = token.iterator();
			while (it.hasNext()) {
				currAnnot = it.next();
				sortedAnnotations.addSortedExclusive(currAnnot);
			}

			Out.prln("Unsorted annotations count: " + token.size());
			Out.prln("Sorted annotations count: " + sortedAnnotations.size());
			Out.prln("=============================");
			long insertPositionEnd;
			long insertPositionStart;
			for (int i = sortedAnnotations.size() - 1; i >= 0; --i) {
				currAnnot = (Annotation) sortedAnnotations.get(i);
				insertPositionStart = currAnnot.getStartNode().getOffset()
						.longValue();
				insertPositionEnd = currAnnot.getEndNode().getOffset()
						.longValue();
				if (insertPositionEnd != -1 && insertPositionStart != -1) {
					String tmpAno = editableContent.substring(
							(int) insertPositionStart, (int) insertPositionEnd);
					Out.prln(tmpAno + " - "
							+ currAnnot.getFeatures().get("majorType"));
					return (String) currAnnot.getFeatures().get("majorType");
				}
			}
			Out.prln("=============================");
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	static public List<String> getTagPOS(String sentence) {
		Document tempDoc = null;
		Corpus tempCorpus = null;
		// set up documnet and corpus
		try {
			tempCorpus = (Corpus) Factory
					.createResource("gate.corpora.CorpusImpl");
			tempDoc = (Document) Factory.newDocument(sentence);
			if (tempDoc != null && annie != null) {
				tempCorpus.add(tempDoc);
				annie.setCorpus(tempCorpus);
			}
			annie.execute();
		} catch (ResourceInstantiationException e) {
			e.printStackTrace();
		} catch (GateException e) {
			e.printStackTrace();
		}
		// get pos tags anotation
		List<String> result = new ArrayList<String>();
		Iterator<Document> iter = tempCorpus.iterator();

		while (iter.hasNext()) {
			Document doc = iter.next();
			AnnotationSet defaultAnnotSet = doc.getAnnotations();
			Set<String> annotTypesRequired = new HashSet<String>();
			annotTypesRequired.add(TOKEN);
			Annotation currAnnot;

			Out.prln("Get all Annotation Set");
			Set<Annotation> token = new HashSet<Annotation>(defaultAnnotSet
					.get(annotTypesRequired));

			SortedAnnotationList sortedAnnotations = new SortedAnnotationList();

			Iterator<Annotation> it = token.iterator();
			while (it.hasNext()) {
				currAnnot = it.next();
				sortedAnnotations.addSortedExclusive(currAnnot);
			}

			Out.prln("Unsorted annotations count: " + token.size());
			Out.prln("Sorted annotations count: " + sortedAnnotations.size());
			Out.prln("=============================");
			long insertPositionEnd;
			long insertPositionStart;
			for (int i = sortedAnnotations.size() - 1; i >= 0; --i) {
				currAnnot = (Annotation) sortedAnnotations.get(i);
				insertPositionStart = currAnnot.getStartNode().getOffset()
						.longValue();
				insertPositionEnd = currAnnot.getEndNode().getOffset()
						.longValue();
				if (insertPositionEnd != -1 && insertPositionStart != -1) {
					result
							.add((String) currAnnot.getFeatures().get(
									"category"));
				}
			}
			Out.prln("=============================");
		}
		return result;
	}
}
