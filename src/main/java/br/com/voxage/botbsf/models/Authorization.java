package br.com.voxage.botbsf.models;

import br.com.voxage.botbsf.BotBSF;
import java.util.HashMap;
import java.util.Map;

public class Authorization {
    
    private static final HashMap<String, String> MAP = new HashMap<String, String>();
    private static final HashMap<String, String> MAP_TOKEN = new HashMap<String, String>();
    public final static String HASHCODE = "ABCD";
    
    static {
        MAP.put("Authorization", "Bearer " + HASHCODE);
    }
      
    public static Map<String, String> getHeaderMap(BotBSF bot){
    	if (bot.getToken() != null){
    		final String HASHCODE_TOKEN = bot.getToken().getToken();
        	
            MAP_TOKEN.put("Authorization", "Bearer " + HASHCODE_TOKEN);
            
        	return MAP_TOKEN;
    	}else {
    		return MAP;    		
    	}
    }
}
