package DOMPARSEF4IIYA;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DOMModifyF4IIYA {

	public static void main(String argv[]) throws SAXException, IOException, ParserConfigurationException {

		try {

			File inputFile = new File("XML_F4IIYA2.xml");

			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

			Document doc = documentBuilder.parse(inputFile);

			// csoport attribútumának módosítása
			Node csoport = doc.getElementsByTagName("csoport").item(0);

			NamedNodeMap attr = csoport.getAttributes();
			Node nodeAttr = attr.getNamedItem("csoport_id");
			nodeAttr.setTextContent("6");

			// csoportnév módosítása
			NodeList list = csoport.getChildNodes();

			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) node;

					if ("nev".equals(eElement.getNodeName())) {
						if ("Arany Csillagok".equals(eElement.getTextContent())) {
							eElement.setTextContent("Phoenix Fusion");
						}
					}
				}
			}

			// Versenyzőknél való módosítás
			NodeList vsList = doc.getElementsByTagName("versenyzo");

			for (int i = 0; i < vsList.getLength(); i++) {
				Node node = vsList.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					
					Element eElement = (Element) node;
					String tagValue = eElement.getAttribute("tag");

					if ("1".equals(tagValue)) {
						eElement.setAttribute("tag", "6");
					}

				}
			}

			// Tartalom konzolra, illetve fájlba való írása
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();

			DOMSource source = new DOMSource(doc);

			System.out.println("----Módosított fájl----");

			StreamResult consoleResult = new StreamResult(System.out);
			StreamResult file = new StreamResult(inputFile);

			transformer.transform(source, consoleResult);
			transformer.transform(source, file);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}