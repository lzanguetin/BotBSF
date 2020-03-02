package br.com.voxage.botbsf.states.empresa_serasa;

import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.chat.botintegration.message.Message;
import br.com.voxage.chat.botintegration.message.SimpleMessage;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class Negativada {
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
				setId("NEGATIVADA");
				
				setBotStateInteractionType(BotStateInteractionType.NO_INPUT);
				
				setPreFunction(botState ->{
					BotStateFlow botStateFlow = new BotStateFlow();
					botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
					
					Message<?> message = null;
					message = SimpleMessage.text("Entendi, bom, o Benefício Social Familiar consta em muitas Convenções Coletivas de Trabalho de diversas regiões do nosso País. São vários benefícios interessantes para os trabalhadores e também para os empregadores. Se você quiser saber mais detalhes, sugiro que você entre aqui mesmo pelo site em \"Saiba Mais\", entenda o que é o Benefício Social Familiar e assista aos vídeos disponíveis ou, em \"Consultar manual de orientação e regras\", localize sua Convenção Coletiva de trabalho e leia na íntegra.");
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
