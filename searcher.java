package sw_test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;

public class searcher {
	String fpath, query;
	String[] title = new String[5];

	public searcher(String postRoute, String query) throws IOException {
		this.fpath = postRoute;
		this.query = query;
		File docsfile = new File("./SimpleIR/docs.xml");
		org.jsoup.nodes.Document xmlf = Jsoup.parse(docsfile, "UTF-8", "", Parser.xmlParser());
		Elements titlelist = xmlf.select("title");
		for (int i = 0; i < 5; i++) {
			title[i] = titlelist.get(i).text();
		}
	}

	public void forthweek() throws ClassNotFoundException, IOException {
		rank();
	}

	public HashMap getHash() throws IOException, ClassNotFoundException {
		FileInputStream fileStream = new FileInputStream(this.fpath);
		ObjectInputStream objectInputStream = new ObjectInputStream(fileStream);

		Object object = objectInputStream.readObject();
		objectInputStream.close();

		HashMap hashMap = (HashMap) object;
		return hashMap;
	}

	public ArrayList<Double> Sim() throws ClassNotFoundException, IOException {
		// kkma
		KeywordExtractor key = new KeywordExtractor();
		KeywordList keyL = key.extractKeyword(this.query, true);
		ArrayList<String> wordL = new ArrayList<>();
		ArrayList<Integer> wordcnt = new ArrayList<>();
		for (int i = 0; i < keyL.size(); i++) {
			Keyword kword = keyL.get(i);
			String word = kword.getString();
			int cnt = kword.getCnt();
			wordL.add(word);
			wordcnt.add(cnt);
		}
		HashMap map = getHash();
		ArrayList<Double> inner_result = new ArrayList<>();
		double add = 0;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < wordL.size(); j++) {
				ArrayList<String> tmp = (ArrayList<String>) map.get(wordL.get(j));// 가중치
				// System.out.println(tmp);
				String[] splitTmp = tmp.get(i).split(" ");
				add += Double.parseDouble(splitTmp[1]) * (wordcnt.get(j));
				// (j번째 단어의 문서id i의 가중치)*(query안에 j번째 단어의 횟수) 더한값
			}
			inner_result.add(add);
			add = 0;
		}
		// System.out.println(inner_result);
		return inner_result;
	}

	public void rank() throws ClassNotFoundException, IOException {
		ArrayList<Double> result = Sim();
		int[] ranking = { 1, 1, 1, 1, 1 };
		int cnt = 0;
		for (int i = 0; i < result.size(); i++) {
			if (result.get(i) != 0)
				cnt++;
		}
		if (cnt > 0) {
			for (int i = 0; i < ranking.length; i++) {
				ranking[i] = 1;
				for (int j = 0; j < ranking.length; j++) {
					if (result.get(i) < result.get(j))
						ranking[i]++;
					else if(result.get(i)==result.get(j)) {
						if(i<j)
							ranking[j]++;
					}
				}
			}
			for(int i=0;i<ranking.length;i++) {
				System.out.println("문서 제목: "+title[i]+", 유사도:"+result.get(i));
			}
		}
		else
			System.out.println("검색된 문서가 없습니다.");
	}
}
