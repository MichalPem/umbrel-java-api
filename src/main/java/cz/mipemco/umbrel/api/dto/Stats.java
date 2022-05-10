package cz.mipemco.umbrel.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

/**
 * @author Michal Pemčák
 * ]
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Stats
{
	public Long connections;
	public Long difficulty;
	public Long mempool;
	public BigDecimal networkhashps;
	public Long size;
}
