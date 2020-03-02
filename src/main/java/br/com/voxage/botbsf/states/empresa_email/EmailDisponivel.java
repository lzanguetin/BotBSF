package br.com.voxage.botbsf.states.empresa_email;

import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class EmailDisponivel {
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
				setId("EMAILDISPONIVEL");
				
				setPreFunction(botState ->{
					BotStateFlow botStateFlow = new BotStateFlow();
					botStateFlow.flow = BotStateFlow.Flow.CONTINUE;

					setInitialMessage("Entendi, esse e-mail é apenas um aviso da Gestora do Benefício Social Familiar para alertar que "
							+ "o próximo boleto a vencer referente ao Benefício Social Familiar já está disponível para impressão. Para "
							+ "imprimi-lo é só seguir o passo a passo que consta no e-mail que recebeu. Ou acesse o site "
							+ "'www.beneficiosocial.com.br', na área do Empregador, digite o CPF ou CNPJ do Empregador, clique na opção "
							+ "'Imprimir Boletos de Pagamento' e siga o passo a passo");
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
