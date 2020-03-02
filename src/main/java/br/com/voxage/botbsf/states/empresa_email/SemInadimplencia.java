package br.com.voxage.botbsf.states.empresa_email;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.models.DadosEmpresa;
import br.com.voxage.botbsf.models.DadosFluxo;
import br.com.voxage.chat.botintegration.message.Message;
import br.com.voxage.chat.botintegration.message.SimpleMessage;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class SemInadimplencia {	
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("SEMINAD");
			
			setBotStateInteractionType(BotStateInteractionType.NO_INPUT);

			setPreFunction(botState ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				DadosEmpresa consulta = bot.getDadosEmpresa();
				DadosFluxo dados = bot.getDadosFluxo();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				
				Message<?> message = null;
				
				DateFormat actualDate = new SimpleDateFormat("dd/MM/yyyy");
				Date date1 = new Date();
				date1 = consulta.getRegrasNegocio().getDataUltimoPagamento();
				
				message = SimpleMessage.text(String.format("Não localizamos nenhum boleto em aberto para o\n Empregador: %s\n CPF/CNPJ: "
						+ "%s\n O último pagamento ocorreu em: %s\n", consulta.getNomeEmpresa(), dados.getCNPJ(), actualDate.format(date1)));
				bot.addResponse(message);
				
				return botStateFlow;
			});
			
			setPosFunction((botState, inputResult) ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				botStateFlow.navigationKey = BotBSF.STATES.FINALIZARINAD;
				
				return botStateFlow;
			});
			
			setNextNavigationMap(new HashMap<String, String>() {{
				put(BotBSF.STATES.FINALIZARINAD, "#FINALIZARINAD");
			}});
		}};
	}
}
