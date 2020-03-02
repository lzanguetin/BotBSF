package br.com.voxage.botbsf.states.empresa;

import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class Sobre {
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("SOBRE");
			
			setBotStateInteractionType(BotStateInteractionType.NO_INPUT);
			
			setPreFunction(botState ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				
				setInitialMessage("Entendi, bom, o Benefício Social Familiar consta em muitas Convenções Coletivas de Trabalho de "
						+ "diversas regiões do nosso País. São vários benefícios interessantes para os trabalhadores e também para os "
						+ "empregadores. Se você quiser saber mais detalhes, sugiro que você entre aqui mesmo no site, em \"Saiba "
						+ "mais\", entenda o que é o Benefício Social Familiar e assista aos vídeos disponíveis, ou, em \"Consultar "
						+ "Manual de Orientação e Regras\", localize sua Convenção Coletiva de trabalho e leia na íntegra");
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
