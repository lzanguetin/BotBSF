package br.com.voxage.botbsf.states.empresa_atualizar;

import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.models.ConsultaCNPJ;
import br.com.voxage.botbsf.models.DadosFluxo;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class AtulizaInativo {
	private static final String INITIAL_MESSAGE = "Localizei seu CPF neste CNPJ que você informou:\nEmpresa: %s\n CNPJ: %s\nPorém ele ainda está inativo. Para ativá-lo é necessário que o responsável pelo email %s o acesse e clique em 'Autorizar Cadastramento'. Verifique o recebimento na caixa de lixo eletrônico ou spam. Assim que for autorizado acesse a Área da Empresa e clique em 'Solicitar Alteração de Dados Cadastrais.'";
	
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("ATUINATIVO");
			
			setBotStateInteractionType(BotStateInteractionType.NO_INPUT);
			
			setPreFunction(botState ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				DadosFluxo dadosFluxo = bot.getDadosFluxo();
				ConsultaCNPJ consulta = bot.getConsultaCNPJ();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				
				String output = String.format(INITIAL_MESSAGE, consulta.getNome(), dadosFluxo.getCNPJ(), consulta.getEmail());
				
				botState.setInitialMessage(output);
				
				return botStateFlow;
			});
			
			setPosFunction((botState, inputResult) ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				botStateFlow.navigationKey = "FINALIZAR";
				
				return botStateFlow;
			});
			
			setNextNavigationMap(new HashMap<String, String>(){{
				put("FINALIZAR", "/FINALIZAR");
			}});
		}};
	}
}
