package br.com.voxage.botbsf.states.empresa_email;

import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class EmailNPago {
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
				setId("EMAILNPAGO");
				
				setPreFunction(botState ->{
					BotStateFlow botStateFlow = new BotStateFlow();
					botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
					
					setInitialMessage("Entendi, identificamos que sua empresa realizou a impressão do boleto vencido no dia 10 deste "
							+ "mês. Porém até a data que enviamos este e-mail, ainda não foi localizado o pagamento. É só seguir o"
							+ " passo a passo que consta no e-mail que recebeu ou acesse o site 'www.beneficiosocial.com.br', na área do"
							+ " Empregador, digite o CNPJ ou CPF do empregador, clique na opção \"Imprimir Boletos de pagamentos\" e siga"
							+ " o passo a passo");
					
					return botStateFlow;
				});
				
				setBotStateInteractionType(BotStateInteractionType.NO_INPUT);setPosFunction((botState, inputResult) ->{
					BotStateFlow botStateFlow = new BotStateFlow();
					botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
					botStateFlow.navigationKey = BotBSF.STATES.FINALIZARINAD;
					
					return botStateFlow;
				});
				
				setNextNavigationMap(new HashMap<String, String>(){{
					put(BotBSF.STATES.FINALIZARINAD, "#FINALIZARINAD");
				}});
		}};
	}
}
