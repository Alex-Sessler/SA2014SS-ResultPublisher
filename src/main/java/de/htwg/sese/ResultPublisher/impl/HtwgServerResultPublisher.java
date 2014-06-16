package de.htwg.sese.ResultPublisher.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import de.htwg.sese.ResultPublisher.IResultPublisher;

public class HtwgServerResultPublisher implements IResultPublisher {

	@Override
	public void publishResult(String game, String player, Long score)
			throws Exception {

		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost postRequest = new HttpPost(
				"http://de-htwg-sa-highscores.herokuapp.com/");

		StringBuilder sb = new StringBuilder().append("{\"game\":\"")
				.append(game).append("\",\"player\":\"").append(player)
				.append("\",\"score\":").append(score).append("}");

		StringEntity input = new StringEntity(sb.toString());
		input.setContentType("application/json");
		postRequest.setEntity(input);

		HttpResponse response = httpClient.execute(postRequest);

		if (response.getStatusLine().getStatusCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatusLine().getStatusCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
				(response.getEntity().getContent())));

		String output;
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}

		httpClient.getConnectionManager().shutdown();

	}

	@Override
	public void configurePublisher(Map<String, String> conf) {
		// TODO Auto-generated method stub

	}

}
