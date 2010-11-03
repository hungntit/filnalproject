package uit.qabpss.dbconfig;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLReader {
        private static final String RELATION = "relation";
        
	private static final String REVERSED_RELATION = "reversed-relation";

	private static final String FIELD = "field";

	private static final String FIELD_ALIAS = "field-alias";

	private static final String TABLE = "table";

	private static final String TABLE_ALIAS = "table-alias";

	private static final String RELATION_NAME = "relation-name";

	private static final String PATH = "xmlconfig\\rel_config.xml";

	public Document doc = null;

	public XMLReader() {
		try {
			File file = new File(PATH);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String relationWordMapping(String word) {
		String firstObj = null;
		String secondObj = null;
                String rel = null;
		boolean isReverse = false;
		Node temp = null;
		Node n = null;
		doc.getDocumentElement().normalize();
		NodeList nodeLst = doc.getElementsByTagName(RELATION_NAME);
		for (int i = 0; i < nodeLst.getLength(); i++) {
			n = nodeLst.item(i);
			if (word.equals(n.getTextContent())) {
				temp = n.getParentNode();
			}
		}
		if (temp == null) {
			isReverse = true;
			nodeLst = doc.getElementsByTagName(REVERSED_RELATION);
			for (int i = 0; i < nodeLst.getLength(); i++) {
				n = nodeLst.item(i);
				if (word.equals(n.getTextContent())) {
					System.out.println(n.getTextContent());
					temp = n.getParentNode();
				}
			}
		}

		if (temp == null) {
			return null;
		}

		while (temp != null) {
			if (TABLE.equals(temp.getNodeName())
					&& temp.getAttributes().getNamedItem(TABLE_ALIAS) != null) {
				firstObj = temp.getAttributes().getNamedItem(TABLE_ALIAS)
						.getNodeValue();
			}
			if (FIELD.equals(temp.getNodeName())
					&& temp.getAttributes().getNamedItem(FIELD_ALIAS) != null) {
				secondObj = temp.getAttributes().getNamedItem(FIELD_ALIAS)
						.getNodeValue();
                                rel = temp.getAttributes().getNamedItem(RELATION)
						.getNodeValue();
			}
			temp = temp.getParentNode();
		}
		
		if (!isReverse) {
			return firstObj + "-" + rel + "-" + secondObj;
		} else {
			return secondObj + "-" + rel + "-" + firstObj;
		}
	}
}
