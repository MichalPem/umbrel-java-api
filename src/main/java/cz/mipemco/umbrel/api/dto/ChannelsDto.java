package cz.mipemco.umbrel.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Michal Pemčák
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChannelsDto
{
	public Boolean active;
	public String capacity;
	public String chanId;
	public String localBalance;
	public String remoteAlias;
	public String remoteBalance;
	public String totalSatoshisReceived;
	public String totalSatoshisSent;
	public String unsettledBalance;
}
