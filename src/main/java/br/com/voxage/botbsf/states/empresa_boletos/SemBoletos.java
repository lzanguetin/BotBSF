package br.com.voxage.botbsf.states.empresa_boletos;

import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.models.DadosEmpresa;
import br.com.voxage.chat.botintegration.bean.BotExternalLink;
import br.com.voxage.chat.botintegration.message.Message;
import br.com.voxage.chat.botintegration.message.MessageType.General;
import br.com.voxage.chat.botintegration.message.OptionBuilder;
import br.com.voxage.chat.botintegration.message.SimpleMessage;
import br.com.voxage.vbot.BotInputResult;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class SemBoletos {
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("SEMBOLETOS");
			
			setBotStateInteractionType(BotStateInteractionType.DIRECT_INPUT);
			setMaxNoInput(2);
			setMaxInputTime(120000);
			
			setPreFunction(botState ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				
				Message<?> message = null;
				message = OptionBuilder.optionBox("Você ainda não possui impressão de boletos neste mês. Está com dúvidas para "
						+ "imprimir?").addOption("1", "Sim").addOption("2", "Não").build();
				bot.addResponse(message);
				
				return botStateFlow;
			});
			
			setProcessDirectInputFunction((botState, userInputs) ->{
				BotInputResult botInputResult = new BotInputResult();
				DadosEmpresa dados = bot.getDadosEmpresa();
				botInputResult.setResult(BotInputResult.Result.OK);
				
				String userInput = userInputs.getConcatenatedInputs().toLowerCase();
				
				switch(userInput) {
					case "sim":
						try {
							bot.insertTransition(11802);
							Message<?> link = new SimpleMessage<BotExternalLink>(General.EXTERNAL_LINK, 
									new BotExternalLink("Acesse aqui um vídeo tutorial que pode lhe ajudar", dados.getVideoImpressao(), "aqui"));
							bot.addResponse(link);
							botInputResult.setIntentName(BotBSF.STATES.FINALIZAR);
						}catch(Exception e) {
							botInputResult.setResult(BotInputResult.Result.ERROR);
						}
						break;
					case "não":
						try {
							bot.insertTransition(11801);
							botInputResult.setIntentName(BotBSF.STATES.ATENDENTE);
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
				put(BotBSF.STATES.ATENDENTE, "#ATENDENTE");
				put(BotBSF.STATES.FINALIZAR, "#FINALIZAR");
				put("MAX_INPUT_ERROR", "/MAX_INPUT_ERROR");
				put("MAX_NO_INPUT", "/MAX_NO_INPUT");
			}});
		}};
	}
}