package br.com.voxage.botbfs.state.empresa_operador;

import java.util.HashMap;

import br.com.voxage.basicvalidators.CPFValidator;
import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.models.DadosFluxo;
import br.com.voxage.vbot.BotInputResult;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class Operador {
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("OPERADOR");
			
			setBotStateInteractionType(BotStateInteractionType.DIRECT_INPUT);
			setMaxInputTime(BotBSF.NO_INPUT_TIMEOUT); 
			setMaxInputError(3);
			setMaxNoInput(3);
			
			setProcessDirectInputFunction((botState, userInputs) -> {
				BotInputResult botInputResult = new BotInputResult();
				DadosFluxo dadosFluxo = bot.getDadosFluxo();
				botInputResult.setResult(BotInputResult.Result.OK);			
				String userInput = userInputs.getConcatenatedInputs().replaceAll("\\D+", "");;
				
				if((CPFValidator.isValidCPF(userInput)) == false) {
					botInputResult.setResult(BotInputResult.Result.ERROR);
				}else {
					dadosFluxo.setCPF(userInput);
					botInputResult.setResult(BotInputResult.Result.OK);
				}
				
				return botInputResult;
			});
			
			setPosFunction((botState, inputResult)-> {
				BotStateFlow botStateFlow = new BotStateFlow();
				botStateFlow.flow = botStateFlow.flow.CONTINUE;
				botStateFlow.navigationKey = inputResult.getIntentName();
				
				return botStateFlow;
			});
			
			setNextNavigationMap(new HashMap<String, String>(){{
				put(BotBSF.STATES.ATIVO, "#ATIVO");
				put(BotBSF.STATES.INATIVO, "#INATIVO");
				put(BotBSF.STATES.SCADASTRO, "#SCADASTRO");
                put("MAX_INPUT_ERROR", "/TERMINATE");
                put("MAX_NO_INPUT", "/TERMINATE");
			}});
		}};
	}
}
