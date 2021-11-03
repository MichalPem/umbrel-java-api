package cz.mipemco.umbrel.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.mipemco.umbrel.api.dto.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author Michal Pemčák
 */
public class UmbrelApi
{

	private static final String LOGIN_URL = "/manager-api/v1/account/login";
	private static final String SYNC_URL = "/api/v1/bitcoind/info/sync";
	private static final String BLOCK_DETAILS_URL = "/api/v1/bitcoind/info/blocks";
	private static final String TEMPERATURE = "/manager-api/v1/system/temperature";
	private static final String UPTIME = "/manager-api/v1/system/uptime";
	private static final String REBOOT = "/manager-api/v1/system/reboot";

	private final ObjectMapper mapper = new ObjectMapper();
	private final String host;

	public UmbrelApi(String host)
	{
		this.host = host;
	}

	public List<Block> getBlockDetails(LoginResponseDto loginResponseDto, Integer from, Integer to) throws IOException
	{
		String fromto = "?from=" + from + "&to=" + to;
		return mapper.readValue(call(host + BLOCK_DETAILS_URL + fromto, "GET", loginResponseDto.jwt, null), Blocks.class).blocks;
	}

	public Float getTemperature(LoginResponseDto loginResponseDto) throws IOException
	{
		return Float.parseFloat(call(host + TEMPERATURE,"GET", loginResponseDto.jwt, null));
	}

	public Integer getUptime(LoginResponseDto loginResponseDto) throws IOException
	{
		return Integer.parseInt(call(host + UPTIME,"GET", loginResponseDto.jwt, null));
	}

	public Integer rebootUmbrel(LoginResponseDto loginResponseDto) throws IOException
	{
		return Integer.parseInt(call(host + REBOOT,"POST", loginResponseDto.jwt, null));
	}

	public LoginResponseDto login(String password) throws IOException
	{
		return mapper
				.readValue(call(host + LOGIN_URL, "POST", null, mapper.writeValueAsString(new LoginDto(password))), LoginResponseDto.class);
	}

	public SyncResponseDto getSync(LoginResponseDto login) throws IOException
	{
		return mapper.readValue(call(host + SYNC_URL, "GET", login.jwt, null), SyncResponseDto.class);
	}

	private String call(String url, String method, String login, String body) throws IOException
	{

		URL u = new URL(url);
		HttpURLConnection httpCon = (HttpURLConnection) u.openConnection();
		httpCon.setRequestMethod(method);
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

	private void setHeader(HttpURLConnection httpCon)
	{
		httpCon.setRequestProperty("Accept", "application/json, text/plain, */*");
		httpCon.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
		httpCon.setRequestProperty("Origin", "http://umbrel.local");
		httpCon.setRequestProperty("DNT", "1");
	}
}
