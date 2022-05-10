package cz.mipemco.umbrel.api.dto;

/**
 * @author Michal Pemčák
 */
public class RLTLoginDto
{

	public String authenticateWith;
	public String authenticationValue;
	public String twoFAToken;

	public RLTLoginDto()
	{
	}

	public RLTLoginDto(String authenticationValue)
	{
		authenticateWith = "PASSWORD";
		twoFAToken = "";
		this.authenticationValue = authenticationValue;
	}
}
