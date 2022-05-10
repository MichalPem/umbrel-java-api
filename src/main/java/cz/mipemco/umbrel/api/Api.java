package cz.mipemco.umbrel.api;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * @author Michal Pemčák
 */
public class Api
{

	protected String call(String url, String method, String login, String body) throws IOException
	{

		URL u = new URL(url);
		HttpURLConnection httpCon = (HttpURLConnection) u.openConnection();
		httpCon.setRequestMethod(method);
		httpCon.setInstanceFollowRedirects(true);
		setHeader(httpCon);
		if (login != null)
		{
			httpCon.setRequestProperty("Authorization", "JWT " + login);
		}
		if (body != null)
		{
			httpCon.setDoOutput(true);
			OutputStream os = httpCon.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);
			osw.write(body);
			osw.flush();
			osw.close();
			os.close();  //don't forget to close the OutputStream
		}
		httpCon.connect();

		//read the inputstream and print it
		String result;
		BufferedInputStream bis = new BufferedInputStream(httpCon.getInputStream());
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		int result2 = bis.read();
		while (result2 != -1)
		{
			buf.write((byte) result2);
			result2 = bis.read();
		}
		result = buf.toString();
		return result;

	}

	protected void setHeader(HttpURLConnection httpCon)
	{
		httpCon.setRequestProperty("Accept", "application/json, text/plain, */*");
		httpCon.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
		httpCon.setRequestProperty("Origin", "http://umbrel.local");
		httpCon.setRequestProperty("DNT", "1");
	}
}
