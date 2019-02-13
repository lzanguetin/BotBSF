package br.com.voxage.botbsf.states.empresa_inadimplencia;

import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.models.ConsultaCNPJ;
import br.com.voxage.botbsf.models.DadosFluxo;
import br.com.voxage.vbot.BotInputResult;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class InadimplenciaSCadastro {
	private static final String INITIAL_MESSAGE = "{" + 
	           "   \"message\":\"Para ter acesso a quais boletos a\nEmpresa: %s\nCNPJ: %s\nPossui em aberto você precisa ser um operador autorizado ao acesso desta empresa no site, porém seu CPF não consta como autorizado.\nDeseja solicitar autorização de acesso? Neste caso o responsável pelo email %s receberá sua solicitação e precisará autorizá-lo.\"," + 
	           "   \"options\":[" + 
	           "      {" + 
	           "         \"id\":1," + 
	           "         \"text\":\"Sim\"" + 
	           "      }," + 
	           "      {" + 
	           "         \"id\":2," + 
	           "         \"text\":\"Não\"" + 
	           "      }" +
	           "   ]" + 
	           "}";

	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
				setId("INADSCADASTRO");
				
				setBotStateInteractionType(BotStateInteractionType.DIRECT_INPUT);
				
				setPreFunction(botState ->{
					BotStateFlow botStateFlow = new BotStateFlow();
					DadosFluxo dadosFluxo = bot.getDadosFluxo();
					ConsultaCNPJ consulta = bot.getConsultaCNPJ();
					botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
					
					String output = String.format(INITIAL_MESSAGE, consulta.getNome(), dadosFluxo.getCNPJ(), consulta.getEmail());
					
					botState.setInitialMessage(output);
					
					return botStateFlow;
				});
				
				setProcessDirectInputFunction((botState, userInputs) ->{
					BotInputResult botInputResult = new BotInputResult();
					botInputResult.setResult(BotInputResult.Result.OK);
					
					String userInput = userInputs.getConcatenatedInputs();
					
					switch(userInput) {
						case "1":
							try {
								botInputResult.setIntentName(BotBSF.STATES.ATENDENTE);
							}catch(Exception e) {
								botInputResult.setResult(BotInputResult.Result.ERROR);
							}
							break;
						case "2":
							try {
								botInputResult.setIntentName(BotBSF.STATES.FINALIZAR);
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
					put(BotBSF.STATES.FINALIZAR, "#FINALIZAR");
					put(BotBSF.STATES.ATENDENTE, "#ATENDENTE");				
					put("MAX_INPUT_ERROR", "/TERMINATE");
					put("MAX_NO_INPUT", "/TERMINATE");
				}});
		}};
	}
}

