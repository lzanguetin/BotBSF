package br.com.voxage.botbsf.states.trabalhador;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.models.DadosFluxo;
import br.com.voxage.chat.botintegration.MessageType;
import br.com.voxage.chat.botintegration.ejb.entitties.SearchedLiveQuestion;
import br.com.voxage.chat.botintegration.entities.BotMessage;
import br.com.voxage.lucenesearchengine.LuceneSearchEngine;
import br.com.voxage.vbot.BotInputResult;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MenuTrabalhador {
	private static final String INITIAL_MESSAGE = "{" + 
	           "   \"message\":\"Sobre qual asssunto deseja falar?\"," + 
	           "   \"options\":[" + 
	           "      {" + 
	           "         \"id\":1," + 
	           "         \"text\":\"Acionar agora o Serviço Funeral\"" + 
	           "      }," + 
	           "      {" + 
	           "         \"id\":2," + 
	           "         \"text\":\"Recebi esse cartão e quero saber o que é\"" + 
	           "      }," + 
	           "      {" + 
	           "         \"id\":3," + 
	           "         \"text\":\"Acompanhar o andamento do meu benefício\"" + 
	           "      }," +
	           "      {" + 
	           "         \"id\":4," + 
	           "         \"text\":\"Validar seu cartão de benefícios\"" + 
	           "      }," +
	           "      {" + 
	           "         \"id\":5," + 
	           "         \"text\":\"Quero saber meu saldo no cartão Agilitas\"" + 
	           "      }," +
	           "      {" + 
	           "         \"id\":6," + 
	           "         \"text\":\"Quer saber quando irá receber sua próxima Cesta de Alimentos\"" + 
	           "      }," +
	           "      {" + 
	           "         \"id\":7," + 
	           "         \"text\":\"Outros Assuntos\"" + 
	           "      }" +
	           "   ]" + 
	           "}";
	
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("MENUTRABALHADOR");
			
			setBotStateInteractionType(BotStateInteractionType.DIRECT_INPUT);
			
			setPreFunction(botState ->{
				bot.setLastState(BotBSF.STATES.MENUTRABALHADOR);
				BotStateFlow botStateFlow = new BotStateFlow();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				
				 botState.setInitialMessages(Arrays.asList(new BotMessage(INITIAL_MESSAGE, MessageType.OPTION_BOX)));
				return botStateFlow;
			});
			
			setProcessDirectInputFunction((botState, userInputs) ->{
				BotInputResult botInputResult = new BotInputResult();
				DadosFluxo dadosFluxo = bot.getDadosFluxo();
				botInputResult.setResult(BotInputResult.Result.OK);
				
				String userInput = userInputs.getConcatenatedInputs();
				dadosFluxo.setFAQ(userInput);
				
				switch(userInput) {
					case "1":
						try {
							botInputResult.setIntentName(BotBSF.STATES.FUNERAL);
						}catch(Exception e) {
							botInputResult.setResult(BotInputResult.Result.ERROR);
						}
						break;
					case "Acionar agora o Serviço Funeral":
						try {
							botInputResult.setIntentName(BotBSF.STATES.FUNERAL);
						}catch(Exception e) {
							botInputResult.setResult(BotInputResult.Result.ERROR);
						}
						break;
					case "2":
						try {
							botInputResult.setIntentName(BotBSF.STATES.CARTAO);
						}catch(Exception e) {
							botInputResult.setResult(BotInputResult.Result.ERROR);
						}
						break;
					case "Recebi esse cartão e quero saber o que é":
						try {
							botInputResult.setIntentName(BotBSF.STATES.CARTAO);
						}catch(Exception e) {
							botInputResult.setResult(BotInputResult.Result.ERROR);
						}
						break;
					case "3":
						try {
							botInputResult.setIntentName(BotBSF.STATES.ANDAMENTO);
						}catch(Exception e) {
							botInputResult.setResult(BotInputResult.Result.ERROR);
						}
						break;
					case "Acompanhar o andamento do meu benefício":
						try {
							botInputResult.setIntentName(BotBSF.STATES.ANDAMENTO);
						}catch(Exception e) {
							botInputResult.setResult(BotInputResult.Result.ERROR);
						}
						break;
					case "4":
						try {
							botInputResult.setIntentName(BotBSF.STATES.VALIDAR);
						}catch(Exception e) {
							botInputResult.setResult(BotInputResult.Result.ERROR);
						}
						break;
					case "Validar seu cartão de benefícios":
						try {
							botInputResult.setIntentName(BotBSF.STATES.VALIDAR);
						}catch(Exception e) {
							botInputResult.setResult(BotInputResult.Result.ERROR);
						}
						break;
					case "5":
						try {
							botInputResult.setIntentName(BotBSF.STATES.SALDO);
						}catch(Exception e) {
							botInputResult.setResult(BotInputResult.Result.ERROR);
						}
						break;
					case "Quero saber meu saldo no cartão Agilitas":
						try {
							botInputResult.setIntentName(BotBSF.STATES.SALDO);
						}catch(Exception e) {
							botInputResult.setResult(BotInputResult.Result.ERROR);
						}
						break;
					case "6":
						try {
							botInputResult.setIntentName(BotBSF.STATES.CESTA);
						}catch(Exception e) {
							botInputResult.setResult(BotInputResult.Result.ERROR);
						}
						break;
					case "Quer saber quando irá receber sua próxima Cesta de Alimentos":
						try {
							botInputResult.setIntentName(BotBSF.STATES.CESTA);
						}catch(Exception e) {
							botInputResult.setResult(BotInputResult.Result.ERROR);
						}
						break;
					case "7":
						try {
							botInputResult.setIntentName(BotBSF.STATES.OUTROS);
						}catch(Exception e) {
							botInputResult.setResult(BotInputResult.Result.ERROR);
						}
						break;
					case "Outros Assuntos":
						try {
							botInputResult.setIntentName(BotBSF.STATES.OUTROS);
						}catch(Exception e) {
							botInputResult.setResult(BotInputResult.Result.ERROR);
						}
						break;
					default:
						try {
							userInput = dadosFluxo.getFAQ();						
							setBotStateInteractionType(BotStateInteractionType.FAQ_SEARCH);
							setNlpSearchEngine(new LuceneSearchEngine());
			                 
							return BotInputResult.BOT_INPUT_RESULT_RETRY;
						}catch(Exception ex) {
							botInputResult.setResult(BotInputResult.Result.ERROR);
						}	
					}
				
					return botInputResult;
				});
				
				setProcessFAQResultFunction((botState, input)->{
	                BotInputResult botInputResult = new BotInputResult();
	                botInputResult.setResult(br.com.voxage.vbot.BotInputResult.Result.OK);
	                Type listType = new TypeToken<List<SearchedLiveQuestion>>(){}.getType();
	                try {
	                    List<SearchedLiveQuestion> results = new Gson().fromJson(input.getAnswer(), listType);
	                    if(!results.isEmpty()) {
	                        botInputResult.setAnswer(results.get(0).getChAnswer());
	                    }
	                }catch(Exception e) {
	                    botInputResult.setResult(br.com.voxage.vbot.BotInputResult.Result.ERROR);
	                }
	                botInputResult.setIntentName(BotBSF.STATES.FAQ);
	                
	                return botInputResult;
	            });	
			
			setPosFunction((botState, inputResult) ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				botStateFlow.navigationKey = inputResult.getIntentName();
					
				return botStateFlow;
			});
		
			setNextNavigationMap(new HashMap<String, String>(){{
				put(BotBSF.STATES.FUNERAL, "#FUNERAL");
				put(BotBSF.STATES.CARTAO, "#CARTAO");
				put(BotBSF.STATES.ANDAMENTO, "#ANDAMENTO");
				put(BotBSF.STATES.VALIDAR, "#VALIDAR");	
				put(BotBSF.STATES.SALDO, "#SALDO");
				put(BotBSF.STATES.CESTA, "#CESTA");	
				put(BotBSF.STATES.OUTROS, "#OUTROS");
				put("faq", "#FAQ");
                put("MAX_INPUT_ERROR", "/TERMINATE");
                put("MAX_NO_INPUT", "/TERMINATE");
			}});
		}};
	}
}