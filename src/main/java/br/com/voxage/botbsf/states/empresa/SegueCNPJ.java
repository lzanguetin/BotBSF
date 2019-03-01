package br.com.voxage.botbsf.states.empresa;

import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.models.ConsultaCNPJ;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;


public class SegueCNPJ {
	private static final String INITIAL_MESSAGE = "Pronto! Já localizei.\nEsse CNPJ já possui %s cadastros. Para cadastrar mais um plano (Adicionar um Sindicato) junto ao BSF acesse www.beneficiosocial.com.br. Insira o CNPJ na área da Empresa e clique em “Solicitar Alteração de Dados Cadastrais” na opção “Adicionar Planos”";

	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("SEGUECNPJ");
			
			setBotStateInteractionType(BotStateInteractionType.NO_INPUT);
			
			setPreFunction(botState ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				ConsultaCNPJ consulta = bot.getConsultaCNPJ();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				
				String output = String.format(INITIAL_MESSAGE, (consulta.getContratos()));
				
				botState.setInitialMessage(output);
				
				return botStateFlow;
			});
			
			setPosFunction((botState, inputResult) ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				botStateFlow.navigationKey = BotBSF.STATES.MENUEMPRESA;
				
				return botStateFlow;
			});
			
			setNextNavigationMap(new HashMap<String, String>(){{
				put(BotBSF.STATES.MENUEMPRESA, "#MENUEMPRESA");
			}});
		}};
	}
}
