package br.com.voxage.botbsf.models;

import br.com.voxage.botbsf.BotBSF;
import java.util.HashMap;
import java.util.Map;

public class Authorization {
    
    private static final HashMap<String, String> MAP = new HashMap<String, String>();
    public final static String HASHCODE = "ABCD";
    
    static {
        MAP.put("Authorization", "Bearer " + HASHCODE);
    }
      
    public static Map<String, String> getHeaderMap(BotBSF bot){
        return MAP;
    }
}
