package muin.mvc.model.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
					+ "search_json.jsp?collection=kmdb_new&ServiceKey=" + secretKey + "&releaseDts=2019&listCount=200"; // json 결과

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

			JSONObject json2 = (JSONObject) data.get(0);
			JSONArray json3 = (JSONArray) json2.get("Result");

			System.out.println("-----------------------------------json3.size() : " + json3.size());
			for (int i = 0; i < json3.size(); i++) {
				JSONObject json4 = (JSONObject) json3.get(i);
				// System.out.println("json4 : "+json4);
				JSONArray ratingarr = (JSONArray) json4.get("rating");
				// System.out.println("ratingarr : "+ratingarr);
				JSONObject ratingobj = (JSONObject) ratingarr.get(0);
				String rating = (String) ratingobj.get("ratingGrade");
			//	 System.out.println("��� : "+rating);

				
				String movieName = (String) json4.get("title");
			//	System.out.println("movieName   " + movieName);
				mdto.setMovieName(movieName);
				
				JSONArray json5 = (JSONArray) json4.get("actor");
				JSONObject json6 = (JSONObject) json5.get(0);
				String actor = (String) json6.get("actorNm");
				mdto.setMovieActor(actor);
	
				JSONArray json7 = (JSONArray) json4.get("director");
				JSONObject json8 = (JSONObject) json7.get(0);

				String director = (String) json8.get("directorNm");
				mdto.setMovieDirector(director);

				plot = (String) json4.get("plot"); // movie_story
				mdto.setMovieStory(plot);
				type = (String) json4.get("genre"); // movie_genre

				 
				mdto.setMovieGenre(type);
			
				String posters = (String) json4.get("posters");
				mdto.setMoviePoster(posters);

		//		System.out.println("--------------------------" + mdto.getMovieName());
				
				if(!rating.isEmpty() && !rating.equals("18��������(û�ҳ�����Ұ�)")
						&& !rating.equals(" 18��������(û�ҳ�����Ұ�)")&& !plot.isEmpty()&& !type.isEmpty()&& !posters.isEmpty()){
				MovieDTO dto = new MovieDTO(0, mdto.getMovieName(), mdto.getMovieGenre(), mdto.getMovieStory(),
						mdto.getMovieActor(), mdto.getMovieDirector(), mdto.getMoviePoster());

		//		System.out.println("dto�������" + dto.getMovieName());
				list.add(dto);
			}//if_end
			}//for_end
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;

	}// insertAll

	///////////////////////////////////////////////////////////////////////////////////////////////
	public List<Map<String, String>> actorDetail() {
		
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> actorMap = new HashMap<String, String>();
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonMovieApi());

			MovieDTO mdto = new MovieDTO();


			JSONArray data = (JSONArray) jsonObject.get("Data");// data

			JSONObject json2 = (JSONObject) data.get(0);
			JSONArray json3 = (JSONArray) json2.get("Result");

				for (int i = 0; i < json3.size(); i++) {
					JSONObject json4 = (JSONObject) json3.get(i);
					String nation = (String) json4.get("nation");
					JSONArray json5 = (JSONArray) json4.get("actor");
					JSONObject json6 = (JSONObject) json5.get(0);
					
					String actorId = (String) json6.get("actorId");
					
					String prodYear = (String) json4.get("prodYear");
			
					if(!nation.isEmpty()&&!actorId.isEmpty()&& !prodYear.isEmpty()) {
					actorMap.put("nation", nation);
					actorMap.put("actorId", actorId);
				//	System.out.println("actorid: "+actorId);
					actorMap.put("prodYear", prodYear);
					//System.out.println("prodYear : "+prodYear);
					
					list.add(actorMap);
					}//if_end
				}//for_end
			//	System.out.println("������"+list.size());
				}//try_end
				catch (Exception e) {
					e.printStackTrace();
				}
					return list;
	}//actor
//////////////////////////////////////////////////////////////////////////////////////		
	public List<Map<String, String>> directorDetail() {
		
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> directorMap = new HashMap<String, String>();
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonMovieApi());

			MovieDTO mdto = new MovieDTO();


			JSONArray data = (JSONArray) jsonObject.get("Data");// data

			JSONObject json2 = (JSONObject) data.get(0);
			JSONArray json3 = (JSONArray) json2.get("Result");

				for (int i = 0; i < json3.size(); i++) {
					JSONObject json4 = (JSONObject) json3.get(i);
					String nation = (String) json4.get("nation");
					JSONArray json5 = (JSONArray) json4.get("staff");
					JSONObject staff = (JSONObject) json5.get(0);
					
					String role = (String) staff.get("staffRoleGroup");
					
					String direcId = (String) staff.get("staffId");
			
					if(!nation.isEmpty()&&!role.isEmpty()&& !direcId.isEmpty()) {
						directorMap.put("nation", nation);
						directorMap.put("role", role);
						directorMap.put("directorId", direcId);
					
					list.add(directorMap);
					}//if_end
				}//for_end
				}//try_end
				catch (Exception e) {
					e.printStackTrace();
				}
					return list;
	}//director
	
}// class
