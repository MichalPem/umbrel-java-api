package cz.mipemco.umbrel.api.dto;

/**
 * @author Michal Pemčák
 */
public class LoginDto
{
	public LoginDto(String password)
	{
		this.password = password;
	}

	public LoginDto()
	{
	}

	public String password;
}
