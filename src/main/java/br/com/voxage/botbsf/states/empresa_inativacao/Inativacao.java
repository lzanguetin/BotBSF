package br.com.voxage.botbsf.states.empresa_inativacao;

import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.chat.botintegration.message.Message;
import br.com.voxage.chat.botintegration.message.SimpleMessage;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class Inativacao {	
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("INATIVACAO");
			
			setBotStateInteractionType(BotStateInteractionType.NO_INPUT);
			
			setPreFunction(botState ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				
				Message<?> message = null;
				message = SimpleMessage.text("Para solicitar a inativação do cadastro junto ao Benefício Social Familiar, acesse o "
						+ "site 'www.beneficiosocial.com.br', na área do Empregador, digite o CPF ou CNPJ do Empregador, informe seu CPF"
						+ " e senha de acesso, na tela do Portal do empregador, clique em 'Solicitar Alteração de Dados Cadastrais' e "
						+ "clique em 'Inativar' no plano que corresponde ao que deseja inativar.");
				bot.addResponse(message);
				
				return botStateFlow;
			});
			
			setPosFunction((botState, inputResult) ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				botStateFlow.navigationKey = BotBSF.STATES.FINALIZAR;
				
				return botStateFlow;
			});
			
			setNextNavigationMap(new HashMap<String,String>(){{
				put(BotBSF.STATES.FINALIZAR, "#FINALIZAR");
			}});
		}};
	}
}