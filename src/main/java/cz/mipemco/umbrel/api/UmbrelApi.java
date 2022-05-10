package cz.mipemco.umbrel.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.mipemco.umbrel.api.dto.*;

import java.io.*;
import java.util.List;

/**
 * @author Michal Pemčák
 */
public class UmbrelApi extends Api
{

	private static final String LOGIN_URL = "/manager-api/v1/account/login";
	private static final String SYNC_URL = "/api/v1/bitcoind/info/sync";
	private static final String BLOCK_DETAILS_URL = "/api/v1/bitcoind/info/blocks";
	private static final String TEMPERATURE = "/manager-api/v1/system/temperature";
	private static final String UPTIME = "/manager-api/v1/system/uptime";
	private static final String REBOOT = "/manager-api/v1/system/reboot";
	private static final String CHANNELS = "/api/v1/lnd/channel";
	private static final String STATS = "/api/v1/bitcoind/info/stats";

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

	public List<ChannelsDto> getChannels(LoginResponseDto loginResponseDto) throws IOException
	{
		return mapper.readValue(call(host + CHANNELS,"GET",loginResponseDto.jwt,null),new TypeReference<List<ChannelsDto>>(){});
	}

	public Stats getStats(LoginResponseDto loginResponseDto) throws IOException
	{
		return mapper.readValue(call(host + STATS,"GET",loginResponseDto.jwt,null),Stats.class);
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


}
