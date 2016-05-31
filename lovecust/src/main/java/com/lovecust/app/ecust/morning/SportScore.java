package com.lovecust.app.ecust.morning;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.CloseableHttpResponse;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.CloseableHttpClient;
import cz.msebera.android.httpclient.impl.client.HttpClients;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

/**
 * Created by YY on 5/7/2016
 */
public class SportScore {
	private String username;
	private String password;
	private String html;
	private boolean ok;
	private final String DEFAULT_URL = "http://59.78.92.38:8888/sportscore/default.aspx";
	private final String SCORE_URL = "http://59.78.92.38:8888/sportscore/stScore.aspx";
	private CloseableHttpClient httpClient;

	public SportScore(String cid, String pwd){
		username = cid;
		password = pwd;
		ok = true;
	}
	private String getContent(String url) throws Exception{
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = httpClient.execute(httpGet);
		HttpEntity entity = response.getEntity();
		Scanner scanner = new Scanner(entity.getContent());
		String s = new String();
		while (scanner.hasNextLine()) {
			s += (scanner.nextLine() + '\n');
		}
		return s;
	}
	public boolean init() throws Exception{
		httpClient = HttpClients.createDefault();
		String s = getContent(DEFAULT_URL);
		Document doc = Jsoup.parse(s);
		Elements es = doc.getElementsByTag("input");
		HttpPost httpPost = new HttpPost(DEFAULT_URL);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		for (Element e : es) {
			String name = e.attr("name");
			String value = e.attr("value");
			if (name.equals("txtuser")) value = username;
			if (name.equals("txtpwd")) value = password;
			if (name.equals("btnok")) continue;
			nvps.add(new BasicNameValuePair(name, value));
		}
		nvps.add(new BasicNameValuePair("dlljs", "st"));
		nvps.add(new BasicNameValuePair("btnok.x", "42"));
		nvps.add(new BasicNameValuePair("btnok.y", "18"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse response = httpClient.execute(httpPost);
		int code = response.getStatusLine().getStatusCode();
		if (code == 200) {
			ok = false;
			return false;
		}
		html = getContent(SCORE_URL);
		return true;
	}
	public String getMorningRun() throws Exception{
		Document document = Jsoup.parse(html);
		Elements elements = document.select("td[class=\"bottom-line\"]");
		return elements.get(1).text();
	}

}
