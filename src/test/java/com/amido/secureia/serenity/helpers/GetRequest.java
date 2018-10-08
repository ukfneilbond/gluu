package com.amido.secureia.serenity.helpers;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.json.JSONObject;

import net.thucydides.core.ThucydidesSystemProperty;
import net.thucydides.core.util.SystemEnvironmentVariables;

public class GetRequest {

	public static final String accessToken = "05ca714f-9745-49da-8505-283a39d4edd3";

	private static String getUser(String id) {
		HttpClient httpClient = null;
		HttpResponse response = null;
		StringBuilder result = new StringBuilder();
		try {
			SSLContextBuilder builder = new SSLContextBuilder();
			builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build());
			httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			HttpGet request = new HttpGet(SystemEnvironmentVariables.createEnvironmentVariables()
					.getProperty(ThucydidesSystemProperty.WEBDRIVER_BASE_URL)
					+ "/identity/seam/resource/restv1/scim/v2/Users/" + id + "?access_token=" + accessToken);
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

	public static String getSecurityCode(String id) {
		return new JSONObject(getUser(id)).getJSONObject("urn:ietf:params:scim:schemas:extension:gluu:2.0:User")
				.get("securityCode").toString();
	}
}
