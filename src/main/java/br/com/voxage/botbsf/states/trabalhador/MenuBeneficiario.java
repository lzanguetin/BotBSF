package br.com.voxage.botbsf.states.trabalhador;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.models.DadosFluxo;
import br.com.voxage.chat.botintegration.ejb.entitties.SearchedLiveQuestion;
import br.com.voxage.chat.botintegration.message.Message;
import br.com.voxage.chat.botintegration.message.OptionBuilder;
import br.com.voxage.lucenesearchengine.LuceneSearchEngine;
import br.com.voxage.vbot.BotInputResult;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class MenuBeneficiario {	
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("MENUBENEFICIARIO");
			
			setBotStateInteractionType(BotStateInteractionType.DIRECT_INPUT);
			setMaxNoInput(2);
			setMaxInputTime(120000);
			
			setPreFunction(botState ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				
				bot.setMenuType("Menu Beneficiário - ");
				
				Integer aux = bot.getFlagError();
				bot.setFlagError(aux+1);
				
				Message<?> message = null;
				message = OptionBuilder.optionBox("Vou te passar algumas opções e você escolhe aquela que melhor te ajudar, mas se preferir, digite agora em poucas palavras o que você precisa")
						.addOption("1", "1 - Acompanhar o andamento do meu Benefício").addOption("2", "2 - Validar seu Cartão de "
						+ "Benefícios").addOption("3", "3 - Quero saber meu saldo no cartão Agilitas").addOption("4", "4 - Quer saber "
						+ "quando irá receber sua próxima Cesta de Alimentos").addOption("5", "5 - Outros Assuntos").build();
				bot.addResponse(message);
				
				return botStateFlow;
			});
			
			setProcessDirectInputFunction((botState, userInputs) ->{
				BotInputResult botInputResult = new BotInputResult();
				DadosFluxo dadosFluxo = bot.getDadosFluxo();
				botInputResult.setResult(BotInputResult.Result.OK);
				
				String userInput = userInputs.getConcatenatedInputs().toLowerCase();
				dadosFluxo.setFAQ(userInput);
				
				switch(userInput) {
					case "1 - acompanhar o andamento do meu benefício":
						try {
							bot.setOcorrencia("1 - Acompanhar Andamento");
							botInputResult.setIntentName(BotBSF.STATES.ANDAMENTO);
						}catch(Exception e) {
							botInputResult.setResult(BotInputResult.Result.ERROR);
						}
						break;
					case "2 - validar seu cartão de benefícios":
						try {
							bot.setOcorrencia("2 - Validar Cartão");
							botInputResult.setIntentName(BotBSF.STATES.VALIDAR);
						}catch(Exception e) {
							botInputResult.setResult(BotInputResult.Result.ERROR);
						}
						break;
					case "3 - quero saber meu saldo no cartão agilitas":
						try {
							bot.setOcorrencia("3 - Saber Saldo");
							botInputResult.setIntentName(BotBSF.STATES.SALDO);
						}catch(Exception e) {
							botInputResult.setResult(BotInputResult.Result.ERROR);
						}
						break;
					case "4 - quer saber quando irá receber sua próxima cesta de alimentos":
						try {
							bot.setOcorrencia("4 - Status Cesta");
							botInputResult.setIntentName(BotBSF.STATES.CESTA);
						}catch(Exception e) {
							botInputResult.setResult(BotInputResult.Result.ERROR);
						}
						break;
					case "5 - outros assuntos":
						try {
							bot.setOcorrencia("5 - Outros Assuntos");
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
							setBotStateInteractionType(BotStateInteractionType.DIRECT_INPUT);
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
	                        botInputResult.setIntentName(BotBSF.STATES.FAQ);
	                    }
	                }catch(Exception e) {
	                	if(bot.getFlagError() < 3) {
	                		setBotStateInteractionType(BotStateInteractionType.DIRECT_INPUT);
	                		botInputResult.setResult(BotInputResult.Result.ERROR);
	                	}else {
	                		botInputResult.setIntentName("MAX_INPUT_ERROR");
	                	}
	                }
	                
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
				put(BotBSF.STATES.ANDAMENTO, "#ANDAMENTO");
				put(BotBSF.STATES.VALIDAR, "#VALIDAR");	
				put(BotBSF.STATES.SALDO, "#SALDO");
				put(BotBSF.STATES.MENUBENEFICIARIO, "#MENUBENEFICIARIO");
				put(BotBSF.STATES.CESTA, "#CESTA");	
				put(BotBSF.STATES.OUTROS, "#OUTROS");
				put("faq", "#FAQ");
                put("MAX_INPUT_ERROR", "/MAX_INPUT_ERROR");
                put("MAX_NO_INPUT", "/MAX_NO_INPUT");
			}});
		}};
	}
}