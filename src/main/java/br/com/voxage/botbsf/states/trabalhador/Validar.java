package br.com.voxage.botbsf.states.trabalhador;

import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.chat.botintegration.message.Message;
import br.com.voxage.chat.botintegration.message.SimpleMessage;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class Validar {
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
				setId("VALIDAR");
				
				setBotStateInteractionType(BotStateInteractionType.NO_INPUT);
				
				setPreFunction(botState ->{
					BotStateFlow botStateFlow = new BotStateFlow();
					botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
					
					Message<?> message = null;
					message = SimpleMessage.text("Para efetuar a validação do seu cartão de benefícios tenha em mãos o número do "
							+ "protocolo, a data de nascimento do trabalhador e o CPF do Beneficiário e entre em contato pelo 0800-580"
							+ "-3738 ou baixe o APP BSFOnline em seu celular e conclua a validação do seu cartão.");
					bot.addResponse(message);
					
					return botStateFlow;
				});
				
				setPosFunction((botState, inputResult) ->{
					BotStateFlow botStateFlow = new BotStateFlow();
					botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
					botStateFlow.navigationKey = BotBSF.STATES.FINALIZAR;
					
					return botStateFlow;
				});
				
				setNextNavigationMap(new HashMap<String, String>(){{			
                    put(BotBSF.STATES.FINALIZAR, "#FINALIZAR");
				}});
		}};
	}
}