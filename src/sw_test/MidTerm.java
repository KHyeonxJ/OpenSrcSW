package sw_test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;

public class MidTerm {
	String fpath, query;

	public MidTerm(String fpath, String query) {
		this.fpath = fpath;
		this.query = query;
	}
	
	public void showSnippet() throws IOException {
		File fxml=new File(this.fpath);
		org.jsoup.nodes.Document xmlf = Jsoup.parse(fxml, "UTF-8", "", Parser.xmlParser());
		Elements titlelist = xmlf.select("title");
		Elements bodylist= xmlf.select("body");
		//kkma
		KeywordExtractor key = new KeywordExtractor();
		KeywordList keyL = key.extractKeyword(this.query, true);
		
		ArrayList<String> wordL = new ArrayList<>();
		
		for (int i = 0; i < keyL.size(); i++) {
			Keyword kword = keyL.get(i);
			String word = kword.getString();
			wordL.add(word);
		}
		String[] body= new String[5];
		ArrayList<String> split_body=new ArrayList<>();
		for(int i=0;i<5;i++) {
			body[i]=bodylist.get(i).text();
			split_body.add(body[i]);
		}
	}
}
