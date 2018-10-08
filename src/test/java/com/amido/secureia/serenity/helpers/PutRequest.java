package com.amido.secureia.serenity.helpers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import net.thucydides.core.ThucydidesSystemProperty;
import net.thucydides.core.util.SystemEnvironmentVariables;

public class PutRequest {

	public static final String accessToken = "05ca714f-9745-49da-8505-283a39d4edd3";

	public static String updateUserParams(String username, String id, Map<String, String> parameters) {
		HttpClient httpClient = null;
		HttpResponse response = null;
		StringBuilder result = new StringBuilder();
		try {
			SSLContextBuilder builder = new SSLContextBuilder();
			builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build());
			httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			HttpPut request = new HttpPut(SystemEnvironmentVariables.createEnvironmentVariables()
					.getProperty(ThucydidesSystemProperty.WEBDRIVER_BASE_URL)
					+ "/identity/seam/resource/restv1/scim/v2/Users/" + id + "?access_token=" + accessToken);
			request.addHeader("Content-Type", "application/json");
			request.setEntity(buildParamsFromJSON(username, id, parameters));
			response = httpClient.execute(request);
			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
			String output;
			while ((output = br.readLine()) != null) {
				result.append(output);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {

		}
		return result.toString();
	}

	private static StringEntity buildParamsFromJSON(String userName, String id, Map<String, String> parameters)
			throws UnsupportedEncodingException {
		JSONObject params = new JSONObject();
		params.put("id", id);

		JSONArray schemas = new JSONArray();
		schemas.put("urn:ietf:params:scim:schemas:core:2.0:User");
		schemas.put("urn:ietf:params:scim:schemas:extension:gluu:2.0:User");

		params.put("schemas", schemas);
		params.put("displayName", userName);

		JSONObject extension = new JSONObject();

		for (Map.Entry<String, String> entry : parameters.entrySet()) {
			extension.put(entry.getKey(), entry.getValue());
		}

		params.put("urn:ietf:params:scim:schemas:extension:gluu:2.0:User", extension);
		return new StringEntity(params.toString());
	}

}
