package br.com.voxage.botbsf;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.models.ConsultaCNPJ;
import br.com.voxage.botbsf.models.ConsultaOperador;
import br.com.voxage.botbsf.models.Authorization;
import static br.com.voxage.chat.botintegration.utils.AppLogger.log;
import br.com.voxage.chat.botintegration.utils.AsyncHttpUtils;
import br.com.voxage.chat.botintegration.utils.JsonUtils;

import com.google.gson.JsonSyntaxException;
import java.util.HashMap;

public class BotBSFIntegration {
	private static final String BASE_URL = "http://localhost:3001/";
	
	public static ConsultaCNPJ dadosempresa(BotBSF bot,  String cnpj) {
		String url = String.format("%s%s/%s?%s", BASE_URL, "v1", "dadosEmpresa", "cnpj=" + cnpj);
		
		HashMap<String, String> headers = new HashMap<String, String>();
		
		headers.putAll(Authorization.getHeaderMap(bot));
		
		try {
			AsyncHttpUtils asyncHttpUtils = new AsyncHttpUtils();
			ConsultaCNPJ trab = asyncHttpUtils.get(url, headers)
					.exceptionally(t->{
						throw(new RuntimeException(t));
					})
					.thenApply(resp-> {
						try {
	                        ConsultaCNPJ customerInfo = null;
	                        
	                        switch(resp.getStatusCode()) {
	                        	case 200:
	                        		String json = resp.getResponseBody();
	                        		customerInfo = JsonUtils.parseJson(json, ConsultaCNPJ.class);
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
	
	public static ConsultaOperador dadosOperador(BotBSF bot, String cnpj, String cpf) {
		String url = String.format("%s%s/%s?%s&%s", BASE_URL, "v1", "dadosOperador", "cnpj=" + cnpj, "cpf=" + cpf);
		
		HashMap<String, String> headers = new HashMap<String, String>();
		
		headers.putAll(Authorization.getHeaderMap(bot));
		
		try {
			AsyncHttpUtils asyncHttpUtils = new AsyncHttpUtils();
			ConsultaOperador oper = asyncHttpUtils.get(url, headers)
					.exceptionally(t->{
						throw(new RuntimeException(t));
					})
					.thenApply(resp-> {
						try {
							ConsultaOperador customerInfo = null;
	                        
	                        switch(resp.getStatusCode()) {
	                        	case 200:
	                        		String json = resp.getResponseBody();
	                        		customerInfo = JsonUtils.parseJson(json, ConsultaOperador.class);
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

	        	return oper;
	        }catch(Exception e) {
	            throw( new RuntimeException(e) );
	        }
	    }
}