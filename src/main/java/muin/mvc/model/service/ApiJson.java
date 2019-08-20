package muin.mvc.model.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;
import muin.mvc.model.dto.MovieDTO;

@Component
public class ApiJson {



	public String jsonMovieApi() throws IOException {

		String secretKey = "KADA8JG2N7IJF41LZK16";

		StringBuilder sb = null;
		String result = null;

		try {

			String apiURL = "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/"
					+ "search_json.jsp?collection=kmdb_new&ServiceKey="+secretKey+"&startCount=0&listCount=150"; // json 寃곌낵

			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();

			BufferedReader br;
			if (responseCode == 200) { 
				br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			} else { 
				br = new BufferedReader(new InputStreamReader(con.getErrorStream(), "UTF-8"));
			}

			sb = new StringBuilder();
			String line;

			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}

			br.close();
			con.disconnect();
			result = sb.toString();
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
		
	}//////////////////////////////////////////////////////////////////////////// jsonMovieApi_end

	static JSONArray array;
	static JSONObject obj;
////////////////////////////////////////////////////////////////////////////////////////////////////////

	
	public List<MovieDTO> insertAll() {
		
		List<MovieDTO> list = new ArrayList<MovieDTO>();
		
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonMovieApi());
			
			MovieDTO mdto = new MovieDTO();

			
			String plot; // movie_story
			String type; // movie_genre
			
			
			JSONArray data = (JSONArray) jsonObject.get("Data");// data 
			
			JSONObject json2 = (JSONObject)data.get(0);
            JSONArray json3 =(JSONArray)json2.get("Result");
            
            System.out.println("-----------------------------------json3.size() : " + json3.size());
            for(int i=0; i < json3.size() ; i++) {
            JSONObject json4 = (JSONObject)json3.get(i);
            String movieName=(String)json4.get("title");
            mdto.setMovieName(movieName);
				
			JSONArray json5 =(JSONArray)json4.get("actor");
	        JSONObject json6 = (JSONObject)json5.get(0);
	        String actor=(String)json6.get("actorNm");
	        mdto.setMovieActor(actor);
	         
	         
	        JSONArray json7 =(JSONArray)json4.get("director");
	        JSONObject json8 = (JSONObject)json7.get(0);
	        
	        
	        String director=(String)json8.get("directorNm");
	        mdto.setMovieDirector(director);
	        
				plot = (String) json4.get("plot"); // movie_story
				mdto.setMovieStory(plot);
				type = (String) json4.get("genre"); // movie_genre
				
				
				if(!type.equals("에로")) { 
				mdto.setMovieGenre(type);
				}
				String posters =(String)json4.get("posters");
				mdto.setMoviePoster(posters);
				
				
				
				MovieDTO dto = new MovieDTO(0, mdto.getMovieName(), mdto.getMovieGenre(), mdto.getMovieStory(), 
						mdto.getMovieActor(), mdto.getMovieDirector(),mdto.getMoviePoster());

				list.add(dto);
            }
				System.out.println("list = "+list.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;

	}// insertAll
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	
}// class
