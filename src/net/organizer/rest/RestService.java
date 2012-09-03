package net.organizer.rest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
 
import com.google.gson.Gson;

import android.util.Log;

public class RestService {
//	public static String host = "http://10.0.2.2:8080";
	public static String host = "http://192.168.0.2:8080";
	public static String app = "Organizer";

	public final static int STREAM = 1;
	public final static int STRING = 2;
	public final static int OBJECT = 3;
	public final static int ARRAY = 4;

	public final static int GET = 10;
	public final static int POST = 11;

	public static Object request(String uri, int type, int returnType,
			ArrayList<NameValuePair> params) {

		switch (returnType) {
		case STREAM:
			try {
				return (InputStream) RestService.requestStream(uri, type,
						params);
			} catch (JSONException e) {
				e.printStackTrace();
			}

		case STRING:
			try {
				return (String) RestService.requestString(uri, type,
						returnType, params);
			} catch (JSONException e1) {
				e1.printStackTrace();
			}

		case OBJECT:
			try {
				return (JSONObject) RestService.requestObject(uri, type,
						returnType, params);
			} catch (JSONException e1) {
				e1.printStackTrace();
			}

		case ARRAY:
			try {
				return (JSONArray) RestService.requestArray(uri, type,
						returnType, params);
			} catch (JSONException e) {
				e.printStackTrace();
			}

		default:
			return null;
		}

	}

	private static InputStream requestStream(String uri, int type,
			ArrayList<NameValuePair> params) throws JSONException {
		InputStream is = null;

		switch (type) {
		case RestService.POST:
			return (InputStream) executePost(uri, params);
		case RestService.GET:
			return (InputStream) executeGet(uri);
		default:
			is = null;
			break;
		}

		return is;
	}

	private static InputStream executePost(String uri,
			ArrayList<NameValuePair> params) {
		HttpPost httpRequest = new HttpPost(host + "/" + app + uri);
		httpRequest.addHeader("Accept", "application/json");
		InputStream is = null;
		try {

			if (params != null)
				httpRequest.setEntity(new UrlEncodedFormEntity(params));

			DefaultHttpClient httpClient = new DefaultHttpClient();
			httpClient.getCredentialsProvider().setCredentials(
					new AuthScope(null, -1),
					new UsernamePasswordCredentials(getLogin(), getPassword()));

			HttpResponse response = httpClient.execute(httpRequest);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection (POST request) " + e.toString());
		}

		return is;
	}

	private static InputStream executeGet(String uri) {
		HttpGet httpRequest = new HttpGet(host + "/" + app + uri);
		httpRequest.addHeader("Accept", "application/json");
		InputStream is = null;
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			httpClient.getCredentialsProvider().setCredentials(
					new AuthScope(null, -1),
					new UsernamePasswordCredentials(getLogin(), getPassword()));

			HttpResponse response = httpClient.execute(httpRequest);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection (GET request) " + e.toString());
		}

		return is;
	}

	private static String requestString(String uri, int type, int returnType,
			ArrayList<NameValuePair> params) throws JSONException {
		return convertStreamToString(requestStream(uri, type, params));
	}

	private static JSONObject requestObject(String uri, int type,
			int returnType, ArrayList<NameValuePair> params)
			throws JSONException {
		Gson gson = new Gson();
		JSONObject obj = gson.fromJson(
				requestString(uri, type, returnType, params), JSONObject.class);
		return obj;
	}

	private static JSONArray requestArray(String uri, int type, int returnType,
			ArrayList<NameValuePair> params) throws JSONException {
		return new JSONArray(requestString(uri, type, returnType, params));
	}

	/*
	 * konwertowanie odpowiedzi do lancucha tekstowego w formacie json
	 */
	private static String convertStreamToString(InputStream is)
			throws JSONException {
		String result = "";

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
		} catch (Exception e) {
			Log.e("log_tag", "Error converting result " + e.toString());
		}

		return result;
	}

	private static String getLogin() {
		return Data.User.login;
	}

	private static String getPassword() {
		return Data.User.password;
	}

	public static void resetCredentials() {
		Data.User.login = "";
		Data.User.password = "";
	}

}