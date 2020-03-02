package br.com.voxage.botbsf.states.empresa_atualizar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.models.DadosFluxo;
import br.com.voxage.chat.botintegration.message.Message;
import br.com.voxage.chat.botintegration.message.SimpleMessage;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class AtulizaAtivo {
	@SuppressWarnings({ "serial", "unused" })
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("ATUATIVO");
			
			setBotStateInteractionType(BotStateInteractionType.NO_INPUT);
			
			setPreFunction(botState ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				DadosFluxo dadosFluxo = bot.getDadosFluxo();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				
				Message<?> message = null;
				message = SimpleMessage.text("Para atualizar o cadastro acesse o site 'www.beneficiosocial.com.br', na área do Empregador"
						+ ", digite o CPF ou CNPJ do Empregador, informe seu CPF e senha de acesso, na tela do Portal do empregador, "
						+ "clique em 'Solicitar' > 'Alteração de Dados Cadastrais' e siga o passo a passo.");
				bot.addResponse(message);
				
				Date data = new Date();
				SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd'T'HH:mm:ss");
				Integer ret = bot.buildResult(dadosFluxo.getCPF(), dadosFluxo.getCNPJ(), "Bot", "Atualizar Ativo", "Atualizar Dados - Alteração de Dados Cadastrais"
						, date.format(data).toString(), bot.getUserSession().get("call_id").toString(), bot);
				
				return botStateFlow;
			});
			
			setPosFunction((botState, inputResult) ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				botStateFlow.navigationKey = BotBSF.STATES.DESPEDE;
				
				return botStateFlow;
			});
			
			setNextNavigationMap(new HashMap<String, String>(){{
				put(BotBSF.STATES.DESPEDE, "/DESPEDE");
			}});
		}};
	}
}
