package br.com.voxage.botbsf.states.global;


import java.util.HashMap;
import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.lucenesearchengine.LuceneSearchEngine;
import br.com.voxage.vbot.BotInputResult;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class FAQ {
	@SuppressWarnings({ "serial", "unused" })
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("FAQ");
			setMaxInputTime(BotBSF.NO_INPUT_TIMEOUT);
			
			setBotStateInteractionType(BotStateInteractionType.DIRECT_INPUT);
			
			setProcessDirectInputFunction((botState, userInputs) ->{
				try {
					String userInput = userInputs.getConcatenatedInputs();
					
					setBotStateInteractionType(BotStateInteractionType.FAQ_SEARCH);
					setNlpSearchEngine(new LuceneSearchEngine());
					
					return BotInputResult.BOT_INPUT_RESULT_RETRY;
				}catch(Exception ex) {
					throw(new RuntimeException(ex));
				}
			});
			
			setPosFunction((botState, inputResult) ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				botStateFlow.navigationKey = "REDIRECT";
				
				return botStateFlow;
			});

			setNextNavigationMap(new HashMap<String, String>(){{
				put("faq", "#FAQ");
				put("REDIRECT", "#REDIRECT");
			}});
		}};
	}
}
