package br.com.voxage.botbsf;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.models.ConsultaCPF;
import br.com.voxage.botbsf.models.Authorization;
import static br.com.voxage.chat.botintegration.utils.AppLogger.log;
import br.com.voxage.chat.botintegration.utils.AsyncHttpUtils;
import br.com.voxage.chat.botintegration.utils.JsonUtils;

import com.google.gson.JsonSyntaxException;
import java.util.HashMap;

public class BotBSFIntegration {
	private static final String BASE_URL = "http://localhost:3001/";
	
	public static ConsultaCPF dadostrabalhador(BotBSF bot,  String cpf) {
		String url = String.format("%s%s/%s?%s&%s", BASE_URL, "v1", "dadosOperador", "cnpj=11222333000102", "cpf=" + cpf);
		
		HashMap<String, String> headers = new HashMap<String, String>();
		
		headers.putAll(Authorization.getHeaderMap(bot));
		
		try {
			AsyncHttpUtils asyncHttpUtils = new AsyncHttpUtils();
			ConsultaCPF trab = asyncHttpUtils.get(url, headers)
					.exceptionally(t->{
						throw(new RuntimeException(t));
					})
					.thenApply(resp-> {
						try {
	                        ConsultaCPF customerInfo = null;
	                        
	                        switch(resp.getStatusCode()) {
	                        	case 200:
	                        		String json = resp.getResponseBody();
	                        		customerInfo = JsonUtils.parseJson(json, ConsultaCPF.class);
	                                break;
	                        	case 500:
	                            	throw(new RuntimeException(resp.getResponseBody()));
	                        }
	                        return( customerInfo );
	                    } catch( JsonSyntaxException e ) {
	                        log.error(resp.getResponseBody(), bot.getSessionId());
	                        log.error("Erro ao fazer parse do json", e, bot.getSessionId());
	                        throw( new JsonSyntaxException(e) );
	                    } catch(Exception e) {
	                        log.error(resp.getResponseBody(), bot.getSessionId());
	                        log.error("Erro ao fazer parse do json", e, bot.getSessionId());
	                        throw( new RuntimeException(e) );
	                    }
	                }).get();

	        	return trab;
	        }catch(Exception e) {
	            throw( new RuntimeException(e) );
	        }
	    }
}
	                        