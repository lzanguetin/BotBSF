package br.com.voxage.botbsf;

import static br.com.voxage.chat.botintegration.utils.AppLogger.log;

import java.util.HashMap;
import java.util.concurrent.CancellationException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonSyntaxException;

import br.com.voxage.botbsf.models.Authorization;
import br.com.voxage.botbsf.models.DadosBeneficiario;
import br.com.voxage.botbsf.models.DadosEmpresa;
import br.com.voxage.botbsf.models.DadosOperador;
import br.com.voxage.botbsf.models.Ocorrencia;
import br.com.voxage.botbsf.models.Ocorrencias;
import br.com.voxage.botbsf.models.Token;
import br.com.voxage.chat.botintegration.utils.AsyncHttpUtils;
import br.com.voxage.chat.botintegration.utils.JsonUtils;

public class BotBSFIntegration {
	private static final String BASE_URL = "https://api.bsfonline.com.br/vox-age/";
	
	public static Token validarUsuario(BotBSF bot, String user, String pass) {
		String url = String.format("%s%s?%s&%s", BASE_URL, "acesso", "usuario=" + user, "senha=" + pass);

		HashMap<String, String> headers = new HashMap<String, String>();

		headers.putAll(Authorization.getHeaderMap(bot));

		try {
			AsyncHttpUtils asyncHttpUtils = new AsyncHttpUtils();
			Token trab = asyncHttpUtils.get(url, headers).exceptionally(t -> {
				throw (new RuntimeException(t));
			}).thenApply(resp -> {
				try {
					Token customerInfo = null;

					switch (resp.getStatusCode()) {
					case 200:
						String json = resp.getResponseBody();
						customerInfo = JsonUtils.parseJson(json, Token.class);
						break;
					case 500:
						bot.setErro(500);
						throw (new RuntimeException(resp.getResponseBody()));
					case 404:
						bot.setErro(404);
						throw (new RuntimeException(resp.getResponseBody()));
					case 400:
						bot.setErro(400);
						throw (new RuntimeException(resp.getResponseBody()));
					case 401:
						bot.setErro(401);
						throw (new RuntimeException(resp.getResponseBody()));
					}
					return (customerInfo);
				} catch (JsonSyntaxException e) {
					log.error(resp.getResponseBody(), bot.getSessionId());
					log.error("Erro ao fazer parse do json", e, bot.getSessionId());
					throw (new JsonSyntaxException(e));
				} catch (CancellationException e) {
					log.error("500 - Chamada assÃ­ncrona demorando muito para completar, encerrando por erro !", e,
							bot.getSessionId());
					throw (new RuntimeException(e));
				} catch (Exception e) {
					log.error(resp.getResponseBody(), bot.getSessionId());
					log.error("Erro ao fazer parse do json", e, bot.getSessionId());
					throw (new RuntimeException(e));
				}
			}).get();

			return trab;
		} catch (Exception e) {
			throw (new RuntimeException(e));
		}
	}
	
	public static DadosEmpresa dadosEmpresa(BotBSF bot,  String cnpj) {
		String url = String.format("%s%s?%s", BASE_URL, "dadosEmpresa", "cpfcnpj=" + cnpj);
		
		HashMap<String, String> headers = new HashMap<String, String>();
		
		headers.putAll(Authorization.getHeaderMap(bot));
		
		try {
			AsyncHttpUtils asyncHttpUtils = new AsyncHttpUtils();
			DadosEmpresa trab = asyncHttpUtils.get(url, headers)
					.exceptionally(t->{
						throw(new RuntimeException(t));
					})
					.thenApply(resp-> {
						try {
							DadosEmpresa customerInfo = null;
	                        
	                        switch(resp.getStatusCode()) {
	                        	case 200:
	                        		String json = resp.getResponseBody();
	                        		customerInfo = JsonUtils.parseJson(json, DadosEmpresa.class);
	                                break;
	                        	case 500:
	                        		bot.setErro(500);
	                            	throw(new RuntimeException(resp.getResponseBody()));
	        					case 404:
	        						bot.setErro(404);
	        						throw (new RuntimeException(resp.getResponseBody()));
	        					case 400:
	        						bot.setErro(400);
	        						throw (new RuntimeException(resp.getResponseBody()));
	        					case 401:
	        						bot.setErro(401);
	        						throw (new RuntimeException(resp.getResponseBody()));
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
	
	public static DadosOperador dadosOperador(BotBSF bot, String cnpj, String cpf) {
		String url = String.format("%s%s?%s&%s", BASE_URL, "dadosOperador", "cnpj=" + cnpj, "cpf=" + cpf);
		
		HashMap<String, String> headers = new HashMap<String, String>();
		
		headers.putAll(Authorization.getHeaderMap(bot));
		
		try {
			AsyncHttpUtils asyncHttpUtils = new AsyncHttpUtils();
			DadosOperador oper = asyncHttpUtils.get(url, headers)
					.exceptionally(t->{
						throw(new RuntimeException(t));
					})
					.thenApply(resp-> {
						try {
							DadosOperador customerInfo = null;
	                        
	                        switch(resp.getStatusCode()) {
	                        	case 200:
	                        		String json = resp.getResponseBody();
	                        		customerInfo = JsonUtils.parseJson(json, DadosOperador.class);
	                                break;
	                        	case 500:
	                        		bot.setErro(500);
	                            	throw(new RuntimeException(resp.getResponseBody()));
	        					case 404:
	        						bot.setErro(404);
	        						throw (new RuntimeException(resp.getResponseBody()));
	        					case 400:
	        						bot.setErro(400);
	        						throw (new RuntimeException(resp.getResponseBody()));
	        					case 401:
	        						bot.setErro(401);
	        						throw (new RuntimeException(resp.getResponseBody()));
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
	
	public static DadosBeneficiario dadosBeneficiario(BotBSF bot, String cpf) {
		String url = String.format("%s%s?%s", BASE_URL, "dadosBeneficiario", "cpf=" + cpf);
		
		HashMap<String, String> headers = new HashMap<String, String>();
		
		headers.putAll(Authorization.getHeaderMap(bot));
		
		try {
			AsyncHttpUtils asyncHttpUtils = new AsyncHttpUtils();
			DadosBeneficiario oper = asyncHttpUtils.get(url, headers)
					.exceptionally(t->{
						throw(new RuntimeException(t));
					})
					.thenApply(resp-> {
						try {
							DadosBeneficiario customerInfo = null;
	                        
	                        switch(resp.getStatusCode()) {
	                        	case 200:
	                        		String json = resp.getResponseBody();
	                        		customerInfo = JsonUtils.parseJson(json, DadosBeneficiario.class);
	                                break;
	                        	case 500:
	                            	throw(new RuntimeException(resp.getResponseBody()));
	        					case 404:
	        						bot.setErro(404);
	        						throw (new RuntimeException(resp.getResponseBody()));
	        					case 400:
	        						bot.setErro(400);
	        						throw (new RuntimeException(resp.getResponseBody()));
	        					case 401:
	        						bot.setErro(401);
	        						throw (new RuntimeException(resp.getResponseBody()));
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

	public static Ocorrencias registrarOcorrencia(BotBSF bot, Ocorrencia env) {
		String url = String.format("%s%s", BASE_URL, "registrarOcorrencia");

		HashMap<String, String> headers = new HashMap<String, String>();

		headers.putAll(Authorization.getHeaderMap(bot));

		final ObjectMapper mapper = new ObjectMapper();

		try {
			AsyncHttpUtils asyncHttpUtils = new AsyncHttpUtils();
			Ocorrencias trab = asyncHttpUtils
					.post(url, headers, "application/json;charset=utf-8", mapper.writeValueAsString(env))
					.exceptionally(t -> {
						throw (new RuntimeException(t));
					}).thenApply(resp -> {
						try {
							Ocorrencias customerInfo = null;

							switch (resp.getStatusCode()) {
							case 200:
								String json = resp.getResponseBody();
								customerInfo = JsonUtils.parseJson(json, Ocorrencias.class);
								break;
							case 500:
								bot.setErro(500);
								throw (new RuntimeException(resp.getResponseBody()));
							case 404:
								bot.setErro(404);
								throw (new RuntimeException(resp.getResponseBody()));
							case 400:
								bot.setErro(400);
								throw (new RuntimeException(resp.getResponseBody()));
							}
							return (customerInfo);
						} catch (JsonSyntaxException e) {
							log.error(resp.getResponseBody(), bot.getSessionId());
							log.error("Erro ao fazer parse do json", e, bot.getSessionId());
							throw (new JsonSyntaxException(e));
						} catch (CancellationException e) {
							bot.setErro(500);
							log.error("500 - Chamada assíncrona demorando muito para completar, encerrando por erro !",
									e, bot.getSessionId());
							throw (new RuntimeException(resp.getResponseBody()));
						} catch (Exception e) {
							log.error(resp.getResponseBody(), bot.getSessionId());
							log.error("Erro ao fazer parse do json", e, bot.getSessionId());
							throw (new RuntimeException(e));
						}
					}).get();

			return trab;
		} catch (Exception e) {
			throw (new RuntimeException(e));
		}
	}
	
}