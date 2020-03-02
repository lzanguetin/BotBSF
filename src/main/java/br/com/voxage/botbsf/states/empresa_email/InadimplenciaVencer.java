package br.com.voxage.botbsf.states.empresa_email;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.models.DadosEmpresa;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class InadimplenciaVencer {	
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("INADVENCER");
			
			setBotStateInteractionType(BotStateInteractionType.NO_INPUT);

			setPreFunction(botState ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				DadosEmpresa consulta = bot.getDadosEmpresa();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				
				DateFormat actualDate = new SimpleDateFormat("dd/MM/yyyy");
				Date date1 = new Date();
				date1 = consulta.getRegrasNegocio().getDataVencimentoUnicoBoletoAVencer();
				
				setInitialMessage(String.format("O único boleto disponível para pagamento irá vencer em %s, portanto deve ter recebido "
						+ "um e-mail de aviso de boleto disponível e não de inadimplência. Para imprimi-lo é só seguir o passo a passo "
						+ "que consta no e-mail que recebeu ou acesse o site 'www.beneficiosocial.com.br', na área do Empregador, "
						+ "digiteo o CNPJ ou CPF do empregador, e clique na opção \"Imprimir Boletos de pagamentos\" e siga o passo a "
						+ "passo.", actualDate.format(date1)));
				
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
				put("MAX_INPUT_ERROR", "/MAX_INPUT_ERROR");
				put("MAX_NO_INPUT", "/MAX_NO_INPUT");
			}});
		}};
	}
}
