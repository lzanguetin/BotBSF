package br.com.voxage.botbsf.states.global;


import java.util.List;
import java.util.HashMap;
import java.util.stream.Collectors;
import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;
import br.com.voxage.chat.botintegration.entities.BotMessage;

public class Atendente {
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("ATENDENTE");
			
			setBotStateInteractionType(BotStateInteractionType.NO_INPUT);
			
			setPreFunction(botState ->{
				List<BotMessage> initialMessages = botState.getCustomMessageById("state_transferir").get(0)
						.getMessages().stream().map(rm -> rm.clone()).collect(Collectors.toList());
				botState.setInitialMessages(initialMessages);
				
				BotStateFlow botStateFlow = new BotStateFlow();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				return botStateFlow;
			});
			
			setPosFunction((botState, inputResult) ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				botStateFlow.flow = BotStateFlow.Flow.TRANSFER;
				
				bot.setBotNameToTransfer(BotBSF.STATES.TRANSFERIR);
				botStateFlow.navigationKey = "TRANSFER";
				
				return botStateFlow;
			});
			
			setNextNavigationMap(new HashMap<String, String>(){{
				put("TRANSFER", "/TRANSFER");
			}});
		}};
	}
}