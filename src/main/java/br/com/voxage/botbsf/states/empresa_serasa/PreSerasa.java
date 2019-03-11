package br.com.voxage.botbsf.states.empresa_serasa;

import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.models.ConsultaCNPJ;
import br.com.voxage.vbot.BotInputResult;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class PreSerasa {
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("PRESERASA");
			
			setBotStateInteractionType(BotStateInteractionType.DIRECT_INPUT);

			setPreFunction(botState ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				ConsultaCNPJ consulta = bot.getConsultaCNPJ();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
		
				botState.setCustomField("data", consulta.getSerasa().getdataPreSerasa());
		
				return botStateFlow;
			});
			
			setProcessDirectInputFunction((botState, userInputs) ->{
				BotInputResult botInputResult = new BotInputResult();
				botInputResult.setResult(BotInputResult.Result.OK);
				
				String userInput = userInputs.getConcatenatedInputs();
				
				switch(userInput) {
					case "Deseja entender por que a Empresa foi negativada":
						try {
							botInputResult.setIntentName(BotBSF.STATES.NEGATIVADA);
						}catch(Exception e) {
							botInputResult.setResult(BotInputResult.Result.ERROR);
						}
						break;
					case "Entende que essa cobrança é indevida":
						try {
							botInputResult.setIntentName(BotBSF.STATES.INDEVIDA);
						}catch(Exception e) {
							botInputResult.setResult(BotInputResult.Result.ERROR);
						}
						break;
					case "Deseja fazer um acordo para a regularização dos débitos":
						try {
							botInputResult.setIntentName(BotBSF.STATES.ACORDO);
						}catch(Exception e) {
							botInputResult.setResult(BotInputResult.Result.ERROR);
						}
						break;
					default:
						botInputResult.setResult(BotInputResult.Result.ERROR);
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
				put(BotBSF.STATES.NEGATIVADA, "#NEGATIVADA");
				put(BotBSF.STATES.INDEVIDA, "#INDEVIDA");
				put(BotBSF.STATES.ACORDO, "#ACORDO");
				put("MAX_INPUT_ERROR", "/TERMINATE");
				put("MAX_NO_INPUT", "/TERMINATE");
			}});
		}};
	}
}
