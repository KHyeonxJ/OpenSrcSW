package sw_test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

@SuppressWarnings({ "rawtypes", "unchecked", "nls" })
public class indexer {
	ArrayList <ArrayList<String>> totaldata=new ArrayList();// Ű����, �󵵼� //ArrayList�ȿ� ArrayList<String>�ֱ�
	ArrayList <ArrayList<String>> finaldata=new ArrayList();// Ű����, ����ġ

	public void thirdweek() throws ClassNotFoundException {
		try {
			File index = new File("C:/Users/user/SimpleIR/index.xml");
			org.jsoup.nodes.Document xmlf = Jsoup.parse(index, "UTF-8", "", Parser.xmlParser());
			Elements bodylist = xmlf.select("body");

			String[][] splitStr = new String[5][];// '#'�� �������� split�� ��
			String[] indatasave = new String[2];

			for (int i = 0; i < 5; i++) {
				splitStr[i] = bodylist.get(i).text().split("#");
			}
			for (int i = 0; i < splitStr.length; i++) {
				ArrayList indata = new ArrayList<String>();// ':'�� �������� split�� ��
				for (int j = 0; j < splitStr[i].length; j++) {
					indatasave = splitStr[i][j].split(":");
					indata.add(indatasave[0]);
					indata.add(indatasave[1]);
				}
				totaldata.add(indata);
			}
			for(int i=0;i<totaldata.size();i++) {
				for(int j=0;j<totaldata.get(i).size();j++) {
					if(j%2==0){//keyword�κ�
						if(!finaldata.contains(totaldata.get(i).get(j))) {//keyword�ߺ�
							finaldata.add(weight(totaldata.get(i).get(j)));
						}
					}
				}
			}
			HashSet<ArrayList<String>> set=new HashSet<>(finaldata);
			ArrayList <ArrayList<String>> finalKeyandTF=new ArrayList(set);
			
			File map = new File("C:/Users/user/SimpleIR/index.post");
			FileOutputStream fileStream = new FileOutputStream(map);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileStream);

			HashMap htmlMap = new HashMap();
			for(int i=0;i<finalKeyandTF.size();i++) {
				ArrayList<String> totalFrequency=new ArrayList<>();
				for(int j=0;j<5;j++) {
					totalFrequency.add(finaldata.get(i).get(j+1));
				}
				//System.out.println(totalFrequency);
				htmlMap.put(finalKeyandTF.get(i).get(0), totalFrequency);
			}
			objectOutputStream.writeObject(htmlMap);
			objectOutputStream.close();
			
			this.printHash("C:/Users/user/SimpleIR/index.post");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<String> weight(String x) {//x=�ܾ�
		ArrayList<String> mapdata=new ArrayList<>();
		mapdata.add(x);//keyword����
		int count=0;//��� ������ �����ߴ��� count
		int[] frequency=new int[5];//index�� ���� id�� ���� ���� �ȿ����� �󵵼�
		double result;
		
		for(int i=0;i<5;i++) {
			if(totaldata.get(i).contains(x)) {
				for(int j=0;j<totaldata.get(i).size();j++) {
					if(j%2==0) {
						if(totaldata.get(i).get(j).equals(x)) {
							count++;
							frequency[i]=Integer.parseInt(totaldata.get(i).get(j+1));
						}
					}
				}
			}
		}
		for(int i=0;i<5;i++) {
			if(count!=0) {
				result=Math.round(frequency[i]*Math.log(5/(double)count)*100)/100.0;//�Ҽ��� 3��° �ڸ����� �ݿø�
				mapdata.add(i+" "+result);
			}
		}
		return mapdata;
	}

	public void printHash(String path) throws IOException, ClassNotFoundException {
		FileInputStream fileStream = new FileInputStream(path);
		ObjectInputStream objectInputStream = new ObjectInputStream(fileStream);

		Object object = objectInputStream.readObject();
		objectInputStream.close();

		HashMap hashMap = (HashMap) object;
		Iterator<String> it = hashMap.keySet().iterator();

		while (it.hasNext()) {
			String key = it.next();
			ArrayList value = (ArrayList) hashMap.get(key);
			System.out.println(key + " �� " + value);
		}
	}
}
