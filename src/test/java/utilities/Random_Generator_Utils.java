package utilities;

import org.apache.commons.lang3.RandomStringUtils;

public class Random_Generator_Utils {

	public static String subdomain() 
	{
		String generatedString = RandomStringUtils.randomAlphabetic(10);
		return (generatedString);
	}
}
