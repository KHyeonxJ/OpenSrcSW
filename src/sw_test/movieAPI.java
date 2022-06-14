package reSW;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

public class movieAPI {

    public static void main(String [] args) throws IOException, ParseException {
        String title;

        System.out.print("검색할 제목을 입력하세요 : ");
        Scanner sc = new Scanner(System.in);
        title = sc.nextLine();

        String text = URLEncoder.encode(title, "UTF-8");
        String apiURL = "https://openapi.naver.com/v1/search/movie?query=" + text;

        String clientId = "naverAPI ID";
        String clientSecret = "naverAPI password";

        URL url = new URL(apiURL);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("X-Naver-Client-Id", clientId);
        con.setRequestProperty("X-Naver-Client-Secret", clientSecret);

        int responseCode = con.getResponseCode();
        BufferedReader br;
        if(responseCode == 200){
            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        }else{
            br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
        }
        String inputLine;
        StringBuffer response = new StringBuffer();
        while((inputLine = br.readLine()) != null){
            response.append(inputLine);
        }
        br.close();

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(String.valueOf(response));
        JSONArray infoArray = (JSONArray) jsonObject.get("items");

        for(int i = 0; i < infoArray.size(); i++){
            System.out.println("=item_" + i + "===========================================");
            JSONObject itemObject = (JSONObject) infoArray.get(i);
            System.out.println("title:\t" + itemObject.get("title"));
            System.out.println("subTitle:\t" + itemObject.get("subtitle"));
            System.out.println("director:\t" + itemObject.get("director"));
            System.out.println("actor:\t" + itemObject.get("actor"));
            System.out.println("userRating:\t" + itemObject.get("userRating"));
        }

    }
}
