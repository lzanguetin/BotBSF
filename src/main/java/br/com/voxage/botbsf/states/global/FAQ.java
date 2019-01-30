package br.com.voxage.botbsf.states.global;

import java.util.Arrays;
import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.models.DadosFluxo;
import br.com.voxage.chat.botintegration.MessageType;
import br.com.voxage.chat.botintegration.entities.BotMessage;
import br.com.voxage.lucenesearchengine.LuceneSearchEngine;
import br.com.voxage.vbot.BotInputResult;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class FAQ {
	private static final String INITIAL_MESSAGE = "{" + 
	           "   \"message\":\"Esta resposta te ajudou?\"," + 
	           "   \"options\":[" + 
	           "      {" + 
	           "         \"id\":1," + 
	           "         \"text\":\"Sim\"" + 
	           "      }," + 
	           "      {" + 
	           "         \"id\":2," + 
	           "         \"text\":\"Nâo\"" + 
	           "      }" + 
	           "   ]" + 
	           "}";
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("FAQ");
			setMaxInputTime(BotBSF.NO_INPUT_TIMEOUT);
			
			setBotStateInteractionType(BotStateInteractionType.DIRECT_INPUT);
			
			setProcessDirectInputFunction((botState, userInputs) ->{
				try {
					BotInputResult botInputResult = new BotInputResult();
					String userInput = userInputs.getConcatenatedInputs();
					setBotStateInteractionType(BotStateInteractionType.FAQ_SEARCH);
					setNlpSearchEngine(new LuceneSearchEngine());
					
					botState.setInitialMessages(Arrays.asList(new BotMessage(INITIAL_MESSAGE, MessageType.OPTION_BOX)));
					
					switch(userInput) {
					case "1":
						try {
				                botInputResult.setIntentName(BotBSF.STATES.FAQ);
				        }catch(Exception e) {
			                	botInputResult.setResult(BotInputResult.Result.ERROR);
			            }
						break;
					case "2":
						try {
				                botInputResult.setIntentName(BotBSF.STATES.OUTRA);
				        }catch(Exception e) {
			                	botInputResult.setResult(BotInputResult.Result.ERROR);
			            }
						break;
					default:
							botInputResult.setResult(BotInputResult.Result.ERROR);
					}
					
					return BotInputResult.BOT_INPUT_RESULT_RETRY;
				}catch(Exception ex) {
					throw(new RuntimeException(ex));
				}
			});
			
			
			setNextNavigationMap(new HashMap<String, String>(){{
				put("faq", "#FAQ");
				put(BotBSF.STATES.OUTRA, "#OUTRA");
				put(BotBSF.STATES.ATENDENTE, "#ATENDENTE");
			}});
		}};
	}

}
