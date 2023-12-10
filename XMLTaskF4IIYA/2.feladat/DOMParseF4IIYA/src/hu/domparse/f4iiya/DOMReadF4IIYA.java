package hu.domparse.f4iiya;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class DOMReadF4IIYA {
	
	public static void main(String argv[]) throws SAXException, IOException, ParserConfigurationException {

		File xmlFile = new File("XMLF4IIYA.xml");

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = factory.newDocumentBuilder();

		Document doc = dBuilder.parse(xmlFile);

		doc.getDocumentElement().normalize();

		System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

		NodeList zList = doc.getElementsByTagName("zene");

		for (int i = 0; i < zList.getLength(); i++) {

			Node nNode = zList.item(i);
			System.out.println("\nCurrent element: " + nNode.getNodeName());

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element elem = (Element) nNode;
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
		}

		NodeList vhList = doc.getElementsByTagName("versenyhelyszin");

		for (int i = 0; i < vhList.getLength(); i++) {

			Node fNode = vhList.item(i);
			System.out.println("\nCurrent element: " + fNode.getNodeName());

			if (fNode.getNodeType() == Node.ELEMENT_NODE) {

				Element elem = (Element) fNode;
				String id = elem.getAttribute("verseny_id");

				Node node1 = elem.getElementsByTagName("nev").item(0);
				String nev = node1.getTextContent();

				Node node2 = elem.getElementsByTagName("terulet").item(0);
				String terulet = node2.getTextContent();

				Node node3= elem.getElementsByTagName("befogadokepesseg").item(0);
				String befkep = node3.getTextContent();

				System.out.println("A helyszín id-je: " + id);
				System.out.println("A helyszín neve: " + nev);
				System.out.println("A helyszín területe: " + terulet);
				System.out.println("A helyszín befogadóképessége: " + befkep);

				if (vhList.item(i).getChildNodes().getLength() > 4) {
					int db = 0;
					Node node4 = elem.getElementsByTagName("cim").item(0);
					while (node4 != null) {
						node4 = elem.getElementsByTagName("cim").item(db);
						if (node4 != null) {
							Node n = elem.getElementsByTagName("iranyitoszam").item(db);
							String isz = n.getTextContent();
							System.out.println("Telephely irányítószáma: " + isz);

							Node n2 = elem.getElementsByTagName("varos").item(db);
							String v = n2.getTextContent();
							System.out.println("Telephely városa: " + v);

							Node n3 = elem.getElementsByTagName("utca_hazszam").item(db);
							String u = n3.getTextContent();
							System.out.println("Telephely utcája és házszáma: " + u);
						}
						db++;
					}

				}

				

			}
		}
		
		NodeList tList = doc.getElementsByTagName("tanc");

		for (int i = 0; i < tList.getLength(); i++) {

			Node tNode = tList.item(i);
			System.out.println("\nCurrent element: " + tNode.getNodeName());

			if (tNode.getNodeType() == Node.ELEMENT_NODE) {

				Element elem = (Element) tNode;
				String tid = elem.getAttribute("tanc_id");

				Node node1 = elem.getElementsByTagName("t_mufaj").item(0);
				String mufaj = node1.getTextContent();

				Node node2 = elem.getElementsByTagName("nev").item(0);
				String nev = node2.getTextContent();
				
				Node node3 = elem.getElementsByTagName("hossz").item(0);
				String hossz = node3.getTextContent();

				System.out.println("Tánc egyedi azonosítója: " + tid);
				System.out.println("Tánc műfaja: " + mufaj);
				System.out.println("Tánc neve: " + nev);
				System.out.println("Tánc hossza: " + hossz);

			}
		}
		
		NodeList csList = doc.getElementsByTagName("csoport");

		for (int i = 0; i < csList.getLength(); i++) {

			Node vNode = csList.item(i);
			System.out.println("\nCurrent element: " + vNode.getNodeName());

			if (vNode.getNodeType() == Node.ELEMENT_NODE) {

				Element elem = (Element) vNode;
				String cs_id = elem.getAttribute("csoport_id");
				String produkcio = elem.getAttribute("produkcio");

				Node node1 = elem.getElementsByTagName("tagok_szama").item(0);
				String tsz = node1.getTextContent();

				Node node2 = elem.getElementsByTagName("nev").item(0);
				String nev = node2.getTextContent();

				Node node3 = elem.getElementsByTagName("egyesulet").item(0);
				String egyes = node3.getTextContent();
				
				System.out.println("A csoport azonosítója: " + cs_id);
			    System.out.println("A produkció száma: " + produkcio);
				System.out.println("A csoporttagok száma: " + tsz);
				System.out.println("A csoport neve: " + nev);
				System.out.println("Egyesület neve: " + egyes);

				if (csList.item(i).getChildNodes().getLength() > 3) {
					int db = 0;
					Node node4 = elem.getElementsByTagName("korcsoport").item(0);
					while (node4 != null) {
						node4 = elem.getElementsByTagName("korcsoport").item(db);
						if (node4 != null) {
							String kcs = node4.getTextContent();
							System.out.println("A korcsoport, amiben indul a csoport: " + kcs);
						}
						db++;
					}

				}
				
			}
		}
		
		NodeList vList = doc.getElementsByTagName("versenyzo");

		for (int i = 0; i < vList.getLength(); i++) {

			Node gyNode = vList.item(i);
			System.out.println("\nCurrent element: " + gyNode.getNodeName());

			if (gyNode.getNodeType() == Node.ELEMENT_NODE) {

				Element elem = (Element) gyNode;
				String id = elem.getAttribute("versenyzo_id");
				String tag = elem.getAttribute("tag");

				Node node1 = null;
				String eletkor = null;
				if(elem.getElementsByTagName("eletkor").item(0) != null){
					node1 = elem.getElementsByTagName("eletkor").item(0);
					eletkor = node1.getTextContent();
				}

				Node node2 = elem.getElementsByTagName("nev").item(0);
				String nev = node2.getTextContent();
				
				Node node3 = elem.getElementsByTagName("szuletesi_datum").item(0);
				String szul = node3.getTextContent();

				System.out.println("Versenyző egyedi azonosítója: " + id);
				System.out.println("A csoport, ahol tag: " + tag);
				if(node1 != null)
					System.out.println("Életkor: " + eletkor);
				System.out.println("A versenyző neve: " + nev);
				System.out.println("A versenyző születési ideje: " + szul);

				if (vList.item(i).getChildNodes().getLength() > 4) {
					int db = 0;
					Node node4 = elem.getElementsByTagName("lakcim").item(0);
					while (node4 != null) {
						node4 = elem.getElementsByTagName("lakcim").item(db);
						if (node4 != null) {
							Node n = elem.getElementsByTagName("iranyitoszam").item(db);
							String isz = n.getTextContent();
							System.out.println("lakcim irányítószáma: " + isz);

							Node n2 = elem.getElementsByTagName("varos").item(db);
							String v = n2.getTextContent();
							System.out.println("lakcim városa: " + v);

							Node n3 = elem.getElementsByTagName("utca_hazszam").item(db);
							String u = n3.getTextContent();
							System.out.println("lakcim utcája és házszáma: " + u);
						}
						db++;
					}

				}


			}
		}
		
		NodeList thList = doc.getElementsByTagName("tanc_helye");

		for (int i = 0; i < thList.getLength(); i++) {

			Node lNode = thList.item(i);
			System.out.println("\nCurrent element: " + lNode.getNodeName());

			if (lNode.getNodeType() == Node.ELEMENT_NODE) {

				Element elem = (Element) lNode;
				String th_id = elem.getAttribute("th_id");
				String tid = elem.getAttribute("tanc_id");
				String vid = elem.getAttribute("verseny_id");

				Node node1 = elem.getElementsByTagName("pontszam").item(0);
				String psz = node1.getTextContent();

				System.out.println("A tánc helye kapcsolat egyedi azonosítója: " + th_id);
				System.out.println("A tánc azonosítója: " + tid);
				System.out.println("A versenyhely azonosítója: " + vid);
				System.out.println("A kapott pontszám az adott helyszínen: " + psz);

			}
		}
		
		NodeList lzList = doc.getElementsByTagName("lejatszott_zene");

		for (int i = 0; i < lzList.getLength(); i++) {
			
			Node tnNode = lzList.item(i);
			System.out.println("\nCurrent element: " + tnNode.getNodeName());

			if (tnNode.getNodeType() == Node.ELEMENT_NODE) {

				Element elem = (Element) tnNode;
				String tid = elem.getAttribute("tanc_id");
				String zid = elem.getAttribute("zene_id");

				System.out.println("A tánc azonosítója: " + tid);
				System.out.println("A zene azonosítója: " + zid);

			}
		}

	}

}
