package com.fuatkara.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.codehaus.jackson.JsonParser;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonObject;

public class Main {
	public static void main(String[] args) {      //Bu Projede Diger projeyle baglantili yaptik (RESTful-JSONServer)

		int id=1;
		String name = "Fuat";
		String surname = "Kara";
		//http://localhost:8080/RESTful-JSONClient/user/information/28/Zeynep/Kara
		
		try {
			URL url = new URL("http://localhost:8080/RESTful-JSONClient/rest/user/information"+id+"/"+name+"/"+surname);
			
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");  // Burada diyoruz ki yukaridaki adresle ne yapicaz,bizde diyoruz kim,GET yapicaz, postman'deki gibi
			connection.setRequestProperty("Accept", "application/json"+";charset=utf-8"); //buraya application/json yazsak json gelecek.
			
			if(connection.getResponseCode() != 200) {
				throw new RuntimeException("FUAT HTTP CODE :" + connection.getResponseCode());
			}
			//Birseyi buffer'lamak bellekte onlari biriktiriyorsun sonra kullaniyorsun.
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String result = bufferedReader.readLine();
			System.out.println(result);
			
			connection.disconnect();
			
			
			//========  Bu asagi kisimda bizim normalde printimiz, {} bunlarin icindeydi fakat ayirdik
			// bu sekle soktuk
				// ID : 1
				// Name : Balamir
				// Surname : Black
			// Normalde boyleydi : {"UserId":1, "UserName" : Balamir, "UserLastName"}
			
			JSONObject jsonObject = new JSONObject(result);
			int jsId = jsonObject.getInt("userId");   //jsonObject.getInt(key);   Key'lerimiz id'mizdir, name'dir, surname'dir
			String jsName = jsonObject.getString("userName");
			String jsSurname = jsonObject.getString("userLastName");
			
			System.out.println("ID" + jsId);
			System.out.println("Name" + jsName);     //Bu ve asagidaki
			System.out.println("Surname" + jsSurname);
			
			//======== 
			//Bu yaptigimiz yukaridaki seylerle ayni, fakat google uretti o yuzden yukaridakini
			//kullanicaz yada asagidakini     
			JsonObject objectJson = new JsonParser().parse(result).getAsJsonObject();
			jsId = jsonObject.getAsJsonObject().get("kullaniciId").getAsInt();
			jsName = jsonObject.getAsJsonObject().get("kullaniciAdi").getAsString();
			jsSurname = jsonObject.getAsJsonObject().get("kullaniciSoyadi").getAsInt();
			
			System.out.println("ID" + jsId);
			System.out.println("Name" + jsName);		//Bu ve yukaridaki
			System.out.println("Surname" + jsSurname);
			
			//========
			
			
			}catch (MalformedURLException e) {
				System.err.println("ERRROROROR" + e);    //err.println
				e.printStackTrace(); 					//ve buda tam hatanin aciklamisni gosteriyor
			}catch(IOException e){
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
