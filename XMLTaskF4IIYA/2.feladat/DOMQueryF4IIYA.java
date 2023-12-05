package DOMPARSEF4IIYA;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DOMQueryF4IIYA {

	public static void main(String[] args) {

		try {

			// DocumentBuilder létrehozása
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

			Document document = documentBuilder.parse("XML_F4IIYA.xml");

			document.getDocumentElement().normalize();

			// az XPath készítése
			XPath xPath = XPathFactory.newInstance().newXPath();
			
			// meg kell adni az elérési út kifejezést és a csomópont listát
			
			// a F4IIYA_Tancverseny root elem zene gyerekelemeinek lekérdezése
			String expression = "F4IIYA_Tancverseny / zene";

			// 2-es azonositoju helyszin lekerdezese
			// String expression = "//versenyhelyszin[@verseny_id='2']";

			// a masodik tanc kivalasztasa
			// String expression = "F4IIYA_Tancverseny/tanc[2]";

			// csoportok, akik indulnak a 25-30as kategoriaban
			// String expression = "//csoport[korcsoport='25-30']";

			//csoportok, akik indulnak a 25-30as kategoriaban, azoknak az egyesulete
			// String expression = "//csoport[korcsoport='25-30']/egyesulet";

			// versenyzok szuletesi datumanak es eletkoranak kiiratasa
			// String expression = "//szuletesi_datum | //eletkor";

			// tanc_helye kapcsolat elso ket eleme
			// String expression = "//tanc_helye[position()<3]";

			// versenyzok, akik tobb mint 23 evesek
			// String expression = "//versenyzo[eletkor>23]/nev";
			
			// tancok, amik rendelkeznek barmilyen attributummal
			// String expression = "//tanc[@*]";
			
			// keszitunk egy listat, majd az xPath kifejezest le kell forditani es ki kell ertekelni
			NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);

			// A for ciklus segitsegevel a NodeList csomopontjain vegig kell iteralni
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);

				System.out.println("\nAktualis elem: " + node.getNodeName());

				// Meg kell vizsgalni a csomopontot, tesztelni kell a subelementet, jelen esetben a zene elemet
				if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("zene")) {

					Element elem = (Element) node;

                        String id = elem.getAttribute("zene_id");
        
                        Node node1 = elem.getElementsByTagName("cim").item(0);
                        String cim = node1.getTextContent();
        
                        Node node2 = elem.getElementsByTagName("hossz").item(0);
                        String hossz = node2.getTextContent();
        
                        Node node3 = elem.getElementsByTagName("mufaj").item(0);
                        String mufaj = node3.getTextContent();
        
                        Node node4 = elem.getElementsByTagName("eloado").item(0);
                        String eloado = node4.getTextContent();
        
                        System.out.println("Zene id-je: " + id);
                        System.out.println("Cím: " + cim);
                        System.out.println("Hossz: " + hossz);
                        System.out.println("Műfaj: " + mufaj);
                        System.out.println("Előadó: " + eloado);

				}
				
				// Csoport egyesuletenek kiiratasa
				if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("egyesulet")) {

					Element element = (Element) node;

					System.out.println("Egyesulet: " + element.getTextContent());

				}

                // Csoport kiiratasa
                if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("csoport")) {

					Element element = (Element) node;

					System.out.println("ID: " + element.getAttribute("csoport_id"));

					System.out.println(
							"Helyszin neve: " + element.getElementsByTagName("nev").item(0).getTextContent());

					System.out.println(
							"Terulete: " + element.getElementsByTagName("tagok_szama").item(0).getTextContent());

					System.out.println(
							"Befogadokepesseg: " + element.getElementsByTagName("egyesulet").item(0).getTextContent());
                      
                    System.out.println(
							"Korcsoport: " + element.getElementsByTagName("korcsoport").item(0).getTextContent());
                              
				}

				// Versenyzo szuletesi idejenek kiiratasa
				if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("szuletesi_datum")) {

					Element element = (Element) node;

					System.out.println("Szuletesi datum: " + element.getTextContent());

				}

                // Versenyzo eletkoranak kiiratasa
				if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("eletkor")) {

					Element element = (Element) node;

					System.out.println("Eletkor: " + element.getTextContent());

				}

				// Versenyzo nevenek kiiratasa
				if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("nev")) {

					Element element = (Element) node;

					System.out.println("Nev: " + element.getTextContent());

				}

				//Versenyhelyszin kiiratasa
				if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("versenyhelyszin")) {

					Element element = (Element) node;

					System.out.println("ID: " + element.getAttribute("verseny_id"));

					System.out.println(
							"Helyszin neve: " + element.getElementsByTagName("nev").item(0).getTextContent());

					System.out.println(
							"Terulete: " + element.getElementsByTagName("terulet").item(0).getTextContent());

					System.out.println(
							"Befogadokepesseg: " + element.getElementsByTagName("befogadokepesseg").item(0).getTextContent());
                            
                    if (nodeList.item(i).getChildNodes().getLength() > 4) {
						int db = 0;
						Node node3 = element.getElementsByTagName("cim").item(0);
						while (node3 != null) {
							node3 = element.getElementsByTagName("cim").item(db);
							if (node3 != null) {
								Node n = element.getElementsByTagName("iranyitoszam").item(db);
								String isz = n.getTextContent();
								System.out.println("Telephely iranyitoszama: " + isz);
								Node n2 = element.getElementsByTagName("varos").item(db);
								String v = n2.getTextContent();
								System.out.println("Telephely varosa: " + v);
                                Node n3 = element.getElementsByTagName("utca_hazszam").item(db);
								String u = n3.getTextContent();
								System.out.println("Telephely utca,hazszama: " + u);
							}
							db++;
						}

					}

				}

				//tanc kiiratasa
				if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("tanc")) {

					Element element = (Element) node;

					System.out.println("ID: " + element.getAttribute("tanc_id"));

					System.out.println("Nev: " + element.getElementsByTagName("nev").item(0).getTextContent());

					System.out.println(
							"Mufaj: " + element.getElementsByTagName("t_mufaj").item(0).getTextContent());

                    System.out.println(
							"hossz: " + element.getElementsByTagName("hossz").item(0).getTextContent());        
                    

				}

				//tanc_hely kiiratas
				if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("tanc_helye")) {

					Element element = (Element) node;

					System.out.println("Tanc id: " + element.getAttribute("tanc_id"));

					System.out.println("Elem id: " + element.getAttribute("th_id"));

					System.out.println("Versenyhelyszin id: " + element.getAttribute("verseny_id"));

					System.out
							.println("Pontszam: " + element.getElementsByTagName("pontszam").item(0).getTextContent());

				}

			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

	}

}