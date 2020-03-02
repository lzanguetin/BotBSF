package br.com.voxage.botbsf.states.empresa_serasa;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.models.DadosEmpresa;
import br.com.voxage.chat.botintegration.message.Message;
import br.com.voxage.chat.botintegration.message.SimpleMessage;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class SemSerasa {
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("SEMSERASA");
			
			setBotStateInteractionType(BotStateInteractionType.NO_INPUT);
			
			setPosFunction((botState, inputResult) ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				DadosEmpresa consulta = bot.getDadosEmpresa();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;

				if(consulta.getRegrasNegocio().getDataRetiradaSerasa() != null) {
					bot.insertTransition(13101);
					Message<?> message = null;
					message = SimpleMessage.text("Este CPF/CNPJ não consta no SERASA");
					bot.addResponse(message);
					botStateFlow.navigationKey = BotBSF.STATES.FINALIZAR;
				}else {
					bot.insertTransition(13102);
					Message<?> message = null;
					
					DateFormat actualDate = new SimpleDateFormat("dd/MM/yyyy");
					Date date1 = new Date();
					date1 = consulta.getRegrasNegocio().getDataRetiradaSerasa();
					
					message = SimpleMessage.text(String.format("Este CPF/CNPJ já consta regularizado e já foi retirado do SERASA em %s"
							, actualDate.format(date1)));
					bot.addResponse(message);
					botStateFlow.navigationKey = BotBSF.STATES.FINALIZAR;
				}
				
				return botStateFlow;
			});
			
			setNextNavigationMap(new HashMap<String, String>(){{
				put(BotBSF.STATES.FINALIZAR, "#FINALIZAR");				
			}});
		}};
	}
}
