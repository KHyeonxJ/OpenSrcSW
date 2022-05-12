package sw_test;

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class makeKeyword {
	String fpath;
	
	public makeKeyword(String fpath){
		this.fpath=fpath;
	}
	public void secondweek() {// �� xml ������ kkma�� �̿��� index.xml�����
		try {
			File collection = new File(fpath);

			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document doc = builder.newDocument();

			Element docs = doc.createElement("docs");
			doc.appendChild(docs);
			
			org.jsoup.nodes.Document xmlf = Jsoup.parse(collection, "UTF-8", "", Parser.xmlParser());
			Elements doclist = xmlf.select("doc");
			Elements titlelist = xmlf.select("title");
			Elements bodylist = xmlf.select("body");
			// xml���Ͽ��� doc, title, body �±� ����� ���� ��󳻼� list�� ����
			int docnum = doclist.size();// doc�±�(id ����)�� �󸶳� �ִ��� �˾Ƴ�

			for (int i = 0; i < docnum; i++) {
				Element docid = doc.createElement("doc");
				docs.appendChild(docid);
				String idx = Integer.toString(i);
				docid.setAttribute("id", idx);

				Element title = doc.createElement("title");
				title.appendChild(doc.createTextNode(titlelist.get(i).text()));
				// get�Լ��� titlelist�� �ִ� element�޾ƿͼ� text�Լ��� �̿��� string���� ��ȯ �� ����
				docid.appendChild(title);

				String bodydata = bodylist.get(i).text();
				// init keywordExtractor
				KeywordExtractor key = new KeywordExtractor();
				// extract keywords
				KeywordList keyL = key.extractKeyword(bodydata, true);
				bodydata="";//bodydata�ʱ�ȭ
				for (int j = 0; j < keyL.size(); j++) {
					Keyword keyW = keyL.get(j);
					bodydata += keyW.getString() + ":" + keyW.getCnt() + "#";
				}
				Element body = doc.createElement("body");
				body.appendChild(doc.createTextNode(bodydata));
				docid.appendChild(body);

				// xml���Ϸ� ����
				TransformerFactory transformerFactory = TransformerFactory.newInstance();

				Transformer transformer;

				transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(
						new FileOutputStream(new File("./SimpleIR/index.xml")));

				transformer.transform(source, result);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
