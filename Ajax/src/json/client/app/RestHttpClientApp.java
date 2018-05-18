package json.client.app;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import json.spring.domain.User;

public class RestHttpClientApp {

	public static void main(String[] args) throws Exception {

		// System.out.println("\n==========================================\n");
		// // 1.1 Http Get ��� Requeest : JsonSimple lib ���
//		RestHttpClientApp.RequestHttpGet_UseJsonSimple();

		// System.out.println("\n==========================================\n");
		// // 1.2 Http Get ��� Requeest : CodeHaus lib ���
//		 RestHttpClientApp.RequestHttpGet_UseCodeHaus();

		// System.out.println("\n==========================================\n");
		// // 2.1 Http Protocol POST ��� Request
		// // : Form Data���� (JSON �̿�) / JsonSimple lib ���
//		 RestHttpClientApp.RequestHttpPostData_UseJsonSimple();

		// System.out.println("\n==========================================\n");
		// // 2.2 Http Protocol POST ��� Request
		// // : Form Data���� (JSON �̿�) / CodeHaus lib ���
		RestHttpClientApp.RequestHttpPostData_UseCodeHaus();

	}

	//=====================================================================================//
	// 1.1 Http Protocol Get Request : JsonSimple 3rd party lib ���
	public static void RequestHttpGet_UseJsonSimple() throws Exception {
		
		// HttpClient : Http Protocol�� Client �߻�ȭ Bean
		HttpClient httpClient = new DefaultHttpClient();
		
		String url = "http://127.0.0.1:8080/Spring14/user/json/user01"
					 + "?name=user02&age=10";
		
		// HttpGet : Http Protocol GET ��� Request Header ����
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("Content-Type", "application/json");
		
		// Request ���� �� Response �ޱ�
		// HttpResponse : Http Protocol ���� Message �߻�ȭ Bean
		HttpResponse httpResponse = httpClient.execute(httpGet);
		
		//==> Response ��� Ȯ��
		System.out.println(httpResponse);
		System.out.println();
		
		//==> Response Header/Body �� Body �ޱ�
		// httpEntity : Http Protocol Body �߻�ȭ Bean
		HttpEntity responseHttpEntity = httpResponse.getEntity();
		
		//==> Server���� ���� Data �б� ���� HttpEntity�� ���� ImputStream ����
		InputStream is = responseHttpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		
		System.out.println("[ server���� ���� data Ȯ�� ]");
		String serverData = br.readLine();
		System.out.println(serverData);
		
		//==> Server���� ���� JSONData => JSONObject ��ü ����
		JSONObject jsonobj = (JSONObject)JSONValue.parse(serverData);
		System.out.println(jsonobj);
		
	}
	
	// 1.2 Http Protocol Get Request : JsonSimple + codehaus 3rd party lib ���
	public static void RequestHttpGet_UseCodeHaus() throws Exception {
		
		// HttpClient : Http Protocol�� Client �߻�ȭ Bean
		HttpClient httpClient = new DefaultHttpClient();
		
		String url = "http://127.0.0.1:8080/Spring14/user/json/user01"
				+ "?name=user02&age=10";
		
		// HttpGet : Http Protocol GET ��� Request Header ����
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("Content-Type", "application/json");
		
		// Request ���� �� Response �ޱ�
		// HttpResponse : Http Protocol ���� Message �߻�ȭ Bean
		HttpResponse httpResponse = httpClient.execute(httpGet);
		
		//==> Response ��� Ȯ��
		System.out.println(httpResponse);
		System.out.println();
		
		//==> Response Header/Body �� Body �ޱ�
		// httpEntity : Http Protocol Body �߻�ȭ Bean
		HttpEntity responseHttpEntity = httpResponse.getEntity();
		
		//==> Server���� ���� Data �б� ���� HttpEntity�� ���� ImputStream ����
		InputStream is = responseHttpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		
		System.out.println("[ server���� ���� data Ȯ�� ]");
		String serverData = br.readLine();
		System.out.println(serverData);
		
		//==> Server���� ���� JSONData => JSONObject ��ü ����
		JSONObject jsonobj = (JSONObject)JSONValue.parse(serverData);
		//==> Server ���� ���� JSONData => Domain Object �� Binding
		ObjectMapper objectMapper = new ObjectMapper();
		User user = objectMapper.readValue(jsonobj.get("user").toString(), User.class);
		System.out.println(user);
		
	}
	
	//=====================================================================================//
	// 2.1 Http protocol POST Request : FromData ���� / JsonSimple 3rd party lib ���
	public static void RequestHttpPostData_UseJsonSimple() throws Exception {
		
		// HttpClient : Http Protocol�� Client �߻�ȭ Bean
		HttpClient httpClient = new DefaultHttpClient();
		
		String url = "http://127.0.0.1:8080/Spring14/user/json/getUser/user01";
		
		// HttpGet : Http Protocol GET ��� Request Header ����
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");
		
		//==> POST ����� Body�� Data ����
		//==> QueryString (name = value)���� �������� �ʰ�
		//==> JSONData ���� ���� Data Make
		
		// [��� 1 : String ��� ]
//		String data = "{\"userId\":\"teset\",\"userName\":\"ȫ�浿\"}";
		
		// [��� 2 : JSONObject ��� ]
		JSONObject json = new JSONObject();
		json.put("userId", "test");
		json.put("userName", "ȫ�浿");
		
		//==> Request Header/Body�� Body �����
		// HttpEntity : Http Protocol Body �߻�ȭ Bean
		HttpEntity requestHttpEntity = new StringEntity(json.toString(), "UTF-8");
		httpPost.setEntity(requestHttpEntity);
		
		// Request ���� �� Response �ޱ�
		// HttpResponse : Http Protocol ���� Message �߻�ȭ Bean
		HttpResponse httpResponse = httpClient.execute(httpPost);
		
		//==> Response ��� Ȯ��
		System.out.println(httpResponse);
		System.out.println();
		
		//==> Response Header/Body �� Body �ޱ�
		// httpEntity : Http Protocol Body �߻�ȭ Bean
		HttpEntity responseHttpEntity = httpResponse.getEntity();
		
		//==> Server���� ���� Data �б� ���� HttpEntity�� ���� ImputStream ����
		InputStream is = responseHttpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		
		System.out.println("[ server���� ���� data Ȯ�� ]");
		String serverData = br.readLine();
		System.out.println(serverData);
		
		//==> Server���� ���� JSONData => JSONObject ��ü ����
		JSONObject jsonobj = (JSONObject)JSONValue.parse(serverData);
		System.out.println(jsonobj);
		
	}
	
	// 2.2 Http protocol POST Request : FromData ���� 
	// JsonSimple + Codehaus 3rd party lib ���
	public static void RequestHttpPostData_UseCodeHaus() throws Exception {
		
		// HttpClient : Http Protocol�� Client �߻�ȭ Bean
		HttpClient httpClient = new DefaultHttpClient();
		
		String url = "http://127.0.0.1:8080/Spring14/user/json/getUser/user01";
		
		// HttpGet : Http Protocol GET ��� Request Header ����
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");
		
		//==> POST ����� Body�� Data ����
		//==> QueryString (name = value)���� �������� �ʰ�
		//==> JSONData ���� ���� Data Make
		
		// [��� 1 : String ��� ]
//		String data = "{\"userId\":\"teset\",\"userName\":\"ȫ�浿\"}";
		
		// [��� 2 : JSONObject ��� ]
//		JSONObject json = new JSONObject();
//		json.put("userId", "test");
//		json.put("userName", "ȫ�浿");
		
		// [��� 3 : codehaus ��� ]
		User user = new User("test", "ȫ�浿", "1111", null, 10);
		ObjectMapper objectMapper01 = new ObjectMapper();
		// Domain Object ==> JSON Value ��ȯ
		String jsonValue = objectMapper01.writeValueAsString(user);
		
		//==> Request Header/Body�� Body �����
		// HttpEntity : Http Protocol Body �߻�ȭ Bean
		HttpEntity requestHttpEntity = new StringEntity(jsonValue, "UTF-8");
		httpPost.setEntity(requestHttpEntity);
		
		// Request ���� �� Response �ޱ�
		// HttpResponse : Http Protocol ���� Message �߻�ȭ Bean
		HttpResponse httpResponse = httpClient.execute(httpPost);
		
		//==> Response ��� Ȯ��
		System.out.println(httpResponse);
		System.out.println();
		
		//==> Response Header/Body �� Body �ޱ�
		// httpEntity : Http Protocol Body �߻�ȭ Bean
		HttpEntity responseHttpEntity = httpResponse.getEntity();
		
		//==> Server���� ���� Data �б� ���� HttpEntity�� ���� ImputStream ����
		InputStream is = responseHttpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		
		System.out.println("[ server���� ���� data Ȯ�� ]");
		String serverData = br.readLine();
		System.out.println(serverData);
		
		//==> Server���� ���� JSONData => JSONObject ��ü ����
		JSONObject jsonobj = (JSONObject)JSONValue.parse(serverData);
		// Server���� ���� JSONData => Domain Object�� Binding
		ObjectMapper objectMapper = new ObjectMapper();
		User returnUser = objectMapper.readValue(jsonobj.get("user").toString(), User.class);
		System.out.println(returnUser);
		
	}
	
}
