package br.com.voxage.botbsf.states.empresa_email;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.models.DadosEmpresa;
import br.com.voxage.chat.botintegration.message.Message;
import br.com.voxage.chat.botintegration.message.SimpleMessage;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class InadimplenciaDebito {
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("INADEBITO");
			
			setBotStateInteractionType(BotStateInteractionType.NO_INPUT);

			setPreFunction(botState ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				DadosEmpresa consulta = bot.getDadosEmpresa();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				
				Integer size = consulta.getPlanos().size();
				
				DateFormat actualDate = new SimpleDateFormat("dd/MM/yyyy");
				
				for(Integer i = 0; i<size; i++) {
					Integer sizeDeb = consulta.getPlanos().get(i).getDebitos().size();
					for(Integer j = 0; j<sizeDeb; j++) {
						String sit = consulta.getPlanos().get(i).getDebitos().get(j).getSituacao();
						if("ABERTO".equals(sit) && consulta.getPlanos().get(i).getDebitos().get(j).getPossuiBoletoImpresso() == false) {
							Message<?> message1 = null;
							message1 = SimpleMessage.text(String.format("Plano: %s", consulta.getPlanos().get(i).getNome()));
							bot.addResponse(message1);
						}else if ("ABERTO".equals(sit) && consulta.getPlanos().get(i).getDebitos().get(j).getPossuiBoletoImpresso() == true) {
							Message<?> message1 = null;
							message1 = SimpleMessage.text(String.format("Plano: %s\nVencimento:%s", consulta.getPlanos().get(i).getNome(), 
									actualDate.format(consulta.getPlanos().get(i).getDebitos().get(j).getImpressao().getDataVencimento())));
							bot.addResponse(message1);
						}
					}
				}
				
				Message<?> message2 = null;
				message2 = SimpleMessage.text("São esses os boletos que constam em aberto para o CNPJ informado. Acessa o site "
						+ "'www.beneficiosocial.com.br', na área do empregador, digite o CPF ou CNPJ do empregador e clique na opção \"Imprimir Boletos de Pagamento\""
						+ "e siga o passo a passo.");
				bot.addResponse(message2);
				
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