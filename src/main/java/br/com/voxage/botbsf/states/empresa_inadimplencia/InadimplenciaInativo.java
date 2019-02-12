package br.com.voxage.botbsf.states.empresa_inadimplencia;

import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.models.ConsultaCNPJ;
import br.com.voxage.botbsf.models.DadosFluxo;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class InadimplenciaInativo {
	private static final String INITIAL_MESSAGE = "Localizei seu CPF neste CNPJ que você informou:\nEmpresa: %s\nCNPJ:%s\nPorém ele ainda está inativo. Para ativá-lo é necessário que o responsável pelo email %s o acesse e clique em 'Autorizar Cadastramento'. Verifique o recebimento também na caixa de lixo eletrônico ou spam. Assim que for autorizado acesse a Área da Empresa e clique em Imprimir > Boletos de Pagamento onde visualizará todos os boleto em aberto.";
	
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("INADINATIVO");
			
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
				botStateFlow.navigationKey = "FINALIZARINAD";
				
				return botStateFlow;
			});
			
			setNextNavigationMap(new HashMap<String, String>() {{
				put("FINALIZARINAD", "#FINALIZARINAD");
			}});
		}};
	}
}