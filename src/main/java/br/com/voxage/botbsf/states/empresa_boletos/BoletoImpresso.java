package br.com.voxage.botbsf.states.empresa_boletos;

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

public class BoletoImpresso {
	
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("BOLETOIMPRESSO");
			
			setBotStateInteractionType(BotStateInteractionType.NO_INPUT);
			
			setPreFunction(botState ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				DadosEmpresa consulta = bot.getDadosEmpresa();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				
		    	DateFormat dia = new SimpleDateFormat("dd-MM-yyyy");
		    	DateFormat hora = new SimpleDateFormat("HH:mm:ss"); 
		    	Date date = new Date();
		    	Date vencimento = new Date();
		    	date = consulta.getRegrasNegocio().getUltimaImpressao().getDataHora();
		    	vencimento = consulta.getRegrasNegocio().getUltimaImpressao().getDataVencimento();
		    	Integer qtdHoras = consulta.getRegrasNegocio().getUltimaImpressao().getQtdHorasDePagamento();
				
				Message<?> message = null;
				message = SimpleMessage.text(String.format("Sua última impressão foi realizada no dia %s as %s referente ao vencimento "
						+ "de %s. Você deverá efetuar o pagamento após %s hora(s) da impressão.", dia.format(date), hora.format(date), 
						dia.format(vencimento), qtdHoras));
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
