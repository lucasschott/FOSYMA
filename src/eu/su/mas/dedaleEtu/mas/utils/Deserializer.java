package eu.su.mas.dedaleEtu.mas.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;

public class Deserializer 
{
	@SuppressWarnings("unchecked")
	public static <type> type deserialize(String input)
	{
		try (ByteArrayOutputStream bo = new ByteArrayOutputStream();
				ObjectOutputStream so = new ObjectOutputStream(bo)) 
		{
	          so.writeObject(input);
	          so.flush();
	          
	          byte[] decoded = Base64.getDecoder().decode(input);
	          
	          InputStream in = new ByteArrayInputStream(decoded);
	          ObjectInputStream obin = new ObjectInputStream(in);
	          
	          return (type) obin.readObject();
	    }
	    catch (IOException e) {
	    	
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
