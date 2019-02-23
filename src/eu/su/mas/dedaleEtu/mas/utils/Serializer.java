package eu.su.mas.dedaleEtu.mas.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Base64;

public class Serializer 
{
	public static <type> String serialize(type input)
	{
		try (ByteArrayOutputStream bo = new ByteArrayOutputStream();
				ObjectOutputStream so = new ObjectOutputStream(bo)) 
		{
	          so.writeObject(input);
	          so.flush();
	          
	          return Base64.getEncoder().encodeToString(bo.toByteArray());
	    }
	    catch (IOException e) 
		{
	    	
		}
		
	    return null;
	}
}
