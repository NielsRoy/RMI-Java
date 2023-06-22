package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {

	public static void main(String[] args) {
		System.out.println(chatGPT("who are you?"));
	}
	
	public static String chatGPT(String message) {
		final String URL = "https://api.openai.com/v1/chat/completions";
		final String API_KEY = "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";
		final String MODEL = "gpt-3.5-turbo";
		
		try {
			URL obj = new URL(URL);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Authorization", "Bearer "+API_KEY);
			con.setRequestProperty("Content-Type", "application/json");
		
			//Build the request body
			String body = "{\"model\": \""+MODEL+"\", \"messages\": [{\"role\": \"user\", \"content\": \""+message+"\"}]}";
			con.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
			writer.write(body);
			writer.flush();
			writer.close();
			
			//Get the response
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			return (response.toString().split("\"content\":\"")[1].split("\"")[0]).substring(4);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
