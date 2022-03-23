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
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class collection {

	public static void main(String[] args) {
		
	}

	public static File[] makeFileList(String path) {// 폴더 path안에 있는 파일들을 파일 리스트로 반환
		File dir = new File(path);
		return dir.listFiles();
	}
	public static void oneweek() {
		try {
			File files[] = makeFileList("C:/Users/user/SimpleIR/htmltest");
			
			// doucument생성
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			Document doc = docBuilder.newDocument();// document객체 생성
			// element 추출
			Element docs = doc.createElement("docs");
			doc.appendChild(docs);
			for (int i = 0; i < files.length; i++) {
				// jsoup 라이브러리
				org.jsoup.nodes.Document html = Jsoup.parse(files[i], "UTF-8");
				String titleData = html.title();
				String bodyData = html.body().text();
				
				Element docid = doc.createElement("doc");
				docs.appendChild(docid);
				String idx=Integer.toString(i);
				docid.setAttribute("id", idx);
				
				Element title= doc.createElement("title");
				title.appendChild(doc.createTextNode(titleData));
				docid.appendChild(title);
					
				Element body=doc.createElement("body");
				body.appendChild(doc.createTextNode(bodyData));
				docid.appendChild(body);
			}
			// xml파일로 쓰기
				TransformerFactory transformerFactory = TransformerFactory.newInstance();

				Transformer transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new FileOutputStream(new File("C:/Users/user/SimpleIR/docs.xml")));

				transformer.transform(source, result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static File[] makeXMLList(String path) {// 폴더 path안에 있는 파일들을 
		
	}
}
