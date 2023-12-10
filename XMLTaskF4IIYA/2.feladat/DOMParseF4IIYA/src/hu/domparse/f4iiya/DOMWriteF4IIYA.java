package hu.domparse.f4iiya;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class DOMWriteF4IIYA {

	public static void main(String argv[]) throws Exception {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = factory.newDocumentBuilder();

		Document doc = dBuilder.newDocument();

		Element root = doc.createElementNS("XMLF4IIYA", "F4IIYA_Tancverseny");
		doc.appendChild(root);

		// zenek

		root.appendChild(createZene(doc, "1", "Cim 1", "3:45", "Pop", "Kovács Anna"));
        root.appendChild(createZene(doc, "2", "Cim 2", "2:45", "Elektronikus", "Tiesto"));
        root.appendChild(createZene(doc, "3", "Cim 3", "3:29", "Folk", "Szabó István"));

		Element element = (Element) doc.getElementsByTagName("zene").item(0);
		Comment comment = doc.createComment("Zenék");
		element.getParentNode().insertBefore(comment, element);

		// versenyhelyszin

        root.appendChild(createVersenyhelyszin(doc, "1", "Park Stadion", "800 nm^2", "5000", "1024", "Budapest", "Kossuth utca 10"));
        root.appendChild(createVersenyhelyszin(doc, "2", "Sportaréna", "1200 nm^2", "10000", "1132", "Budapest", "Rákospatak utca 5"));
        root.appendChild(createVersenyhelyszin(doc, "3", "Sportközpont", "600 nm^2", "3000", "3521", "Debrecen", "Sport utca 8"));

		element = (Element) doc.getElementsByTagName("versenyhelyszin").item(0);
		comment = doc.createComment("Versenyhelyszínek");
		element.getParentNode().insertBefore(comment, element);

		// tancok

		root.appendChild(createTanc(doc, "1", "Modern tánc", "Fények játéka", "4:30"));
		root.appendChild(createTanc(doc, "2", "Latin tánc", "Forró ritmusok", "3:15"));
		root.appendChild(createTanc(doc, "3", "Balett", "Hamupipőke álom", "5:00"));

		element = (Element) doc.getElementsByTagName("tanc").item(0);
		comment = doc.createComment("Táncok");
		element.getParentNode().insertBefore(comment, element);

		// Csoportok

        String[] kcs = {"25-30"};
		root.appendChild(createCsoport(doc, "1", "1", "15", "Arany Csillagok", "Táncművészeti Egyesület", kcs));

        String[] kcs2 = {"25-30", "22-25"};
		root.appendChild(createCsoport(doc, "2", "2", "10", "Latin Ritmusok", "Tánciskola", kcs2));
		
        String[] kcs3 = {"12-14"};
		root.appendChild(createCsoport(doc, "3", "3", "14", "Kis Angyalok", "Balett Stúdió", kcs3));
		
		element = (Element) doc.getElementsByTagName("csoport").item(0);
		comment = doc.createComment("Csoportok");
		element.getParentNode().insertBefore(comment, element);

		// Versenyzők

        root.appendChild(createVersenyzo(doc, "3", "1", "Kovács Anna", "2009-04-10", "1024", "Budapest", "Kossuth utca 10", "14"));
        root.appendChild(createVersenyzo(doc, "2", "2", "Szabó Bence", "1992-08-22", "1132", "Budapest", "Rákospatak utca 5", ""));
        root.appendChild(createVersenyzo(doc, "1", "3", "Nagy Ferenc", "1998-04-10", "4023", "Debrecen", "Sport tér 15", "25"));

		element = (Element) doc.getElementsByTagName("versenyzo").item(0);
		comment = doc.createComment("Versenyzők");
		element.getParentNode().insertBefore(comment, element);

		// tanc_helye

		root.appendChild(createTancHelye(doc, "2", "1", "3", "78"));
        root.appendChild(createTancHelye(doc, "1", "2", "1", "82"));
		root.appendChild(createTancHelye(doc, "3", "3", "2", "76"));

		element = (Element) doc.getElementsByTagName("tanc_helye").item(0);
		comment = doc.createComment("A tánc helye több-több kapcsolat");
		element.getParentNode().insertBefore(comment, element);

		// lejatszott_zene

        root.appendChild(createLejatszottZene(doc, "1", "1"));
        root.appendChild(createLejatszottZene(doc, "2", "2"));
        root.appendChild(createLejatszottZene(doc, "3", "3"));
		
		element = (Element) doc.getElementsByTagName("lejatszott_zene").item(0);
		comment = doc.createComment("Lejátszott zene több-több kapcsolat");
		element.getParentNode().insertBefore(comment, element);

		// Transform

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transf = transformerFactory.newTransformer();

		transf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transf.setOutputProperty(OutputKeys.INDENT, "yes");
		transf.setOutputProperty("{https://xml.apache.org/xslt}indent-amount", "2");

		// File letrehozas

		DOMSource source = new DOMSource(doc);
		File myFile = new File("XMLF4IIYA_write.xml");

		// Konzolra valo kiiras

		//StreamResult console = new StreamResult(System.out);
		StreamResult file = new StreamResult(myFile);

		//transf.transform(source, console);
		transf.transform(source, file);

	}

	private static Node createZene(Document doc, String zene_id, String cim, String hossz, String mufaj,
			String eloado) {

		Element z = doc.createElement("zene");

		z.setAttribute("zene_id", zene_id);
		z.appendChild(createElement(doc, "cim", cim));
		z.appendChild(createElement(doc, "hossz", hossz));
		z.appendChild(createElement(doc, "mufaj", mufaj));
		z.appendChild(createElement(doc, "eloado", eloado));

		return z;

	}

	private static Node createVersenyhelyszin(Document doc, String verseny_id, String nev, String terulet,
			String befogadokepesseg, String isz, String varos, String utcahazszam) {

		Element vh = doc.createElement("versenyhelyszin");

		vh.setAttribute("verseny_id", verseny_id);
		vh.appendChild(createElement(doc, "nev", nev));
		vh.appendChild(createElement(doc, "terulet", terulet));
        vh.appendChild(createElement(doc, "befogadokepesseg", befogadokepesseg));

		Element cim = doc.createElement("cim");
		cim.appendChild(createElement(doc, "iranyitoszam", isz));
        cim.appendChild(createElement(doc, "varos", varos));
		cim.appendChild(createElement(doc, "utca_hazszam", utcahazszam));

        vh.appendChild(cim);

		return vh;

	}

	private static Node createTanc(Document doc, String tanc_id, String t_mufaj, String nev,
			String hossz) {

		Element tr = doc.createElement("tanc");

		tr.setAttribute("tanc_id", tanc_id);
		tr.appendChild(createElement(doc, "t_mufaj", t_mufaj));
		tr.appendChild(createElement(doc, "nev", nev));
		tr.appendChild(createElement(doc, "hossz", hossz));

		return tr;

	}

	private static Node createCsoport(Document doc, String csoport_id, String produkcio, String tagok_szama, 
        String nev, String egyesulet, String[] korcsoport) {

		Element csop = doc.createElement("csoport");

		csop.setAttribute("csoport_id", csoport_id);
        csop.setAttribute("produkcio", produkcio);
        csop.appendChild(createElement(doc, "tagok_szama", tagok_szama));
		csop.appendChild(createElement(doc, "nev", nev));
		csop.appendChild(createElement(doc, "egyesulet", egyesulet));

		Node[] node1 = appendArray(doc, "korcsoport", korcsoport);

		for (int i = 0; i < korcsoport.length; i++) {
			csop.appendChild(node1[i]);
		}

		return csop;

	}

	private static Node createVersenyzo(Document doc, String tag, String versenyzo_id, String nev, String szuletesi_datum,
			String isz, String varos, String utcahazszam, String eletkor) {

		Element v = doc.createElement("versenyzo");

		v.setAttribute("tag", tag);
		v.setAttribute("versenyzo_id", versenyzo_id);
        if(!(eletkor.equals(""))){
            v.appendChild(createElement(doc, "eletkor", eletkor));
        }
		v.appendChild(createElement(doc, "nev", nev));
		v.appendChild(createElement(doc, "szuletesi_datum", szuletesi_datum));
		
		Element cim = doc.createElement("lakcim");
		cim.appendChild(createElement(doc, "iranyitoszam", isz));
        cim.appendChild(createElement(doc, "varos", varos));
		cim.appendChild(createElement(doc, "utca_hazszam", utcahazszam));

        v.appendChild(cim);

		return v;

	}

	private static Node createTancHelye(Document doc, String tanc_id, String th_id, String verseny_id,
			String pontszam) {

		Element th = doc.createElement("tanc_helye");

		th.setAttribute("tanc_id", tanc_id);
		th.setAttribute("th_id", th_id);
        th.setAttribute("verseny_id", verseny_id);
		th.appendChild(createElement(doc, "pontszam", pontszam));

		return th;

	}

	private static Node createLejatszottZene(Document doc, String tanc_id, String zene_id) {

		Element lz = doc.createElement("lejatszott_zene");

		lz.setAttribute("tanc_id", tanc_id);
		lz.setAttribute("zene_id", zene_id);

		return lz;

	}

	private static Node createElement(Document doc, String name, String value) {

		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));

		return node;

	}

	private static Node[] appendArray(Document doc, String name, String[] value) {

		Element nodes[] = new Element[value.length];

		for (int i = 0; i < value.length; i++) {

			nodes[i] = doc.createElement(name);
			nodes[i].appendChild(doc.createTextNode(value[i]));

		}

		return nodes;

	}
}
