package br.com.voxage.botbsf.states.empresa_serasa;

import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.chat.botintegration.message.Message;
import br.com.voxage.chat.botintegration.message.SimpleMessage;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class Acordo {
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
				setId("ACORDO");
				
				setBotStateInteractionType(BotStateInteractionType.NO_INPUT);
				
				setPreFunction(botState ->{
					BotStateFlow botStateFlow = new BotStateFlow();
					botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
					
					Message<?> message1 = null;
					message1 = SimpleMessage.text("Para verificarmos a possibilidade de formalizarmos um acordo é necessário que já tenha impresso o boleto do mês vigente através do nosso site e já esteja providenciando esse pagamento e então nos envie por e-mail a quantidade de trabalhadores referente a cada vencimento que possui em aberto.");
					bot.addResponse(message1);
					
					Message<?> message2 = null;
					message2 = SimpleMessage.text("Todos esses vencimentos você visualiza agora mesmo no site, digitando o CPF ou CNPJ na \"Área do Empregador\", clique em \"Imprimir Boletos de Pagamento\", selecione Benefício Social Familiar - clique em avançar e veja todos os boletos que constam em aberto. Para nos enviar essa relação com a quantidade de trabalhadores mês a mês anote aí: descumprimentocct@beneficiosocial.com.br - em até 24 horas úteis você será respondido - Ah é importante colocar seu telefone no e-mail para que possamos rapidamente retornar seu contato.");
					bot.addResponse(message2);
					
					return botStateFlow;
				});
				
				setPosFunction((botState, inputResult) ->{
					BotStateFlow botStateFlow = new BotStateFlow();
					botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
					botStateFlow.navigationKey = BotBSF.STATES.FINALIZAR;
					
					return botStateFlow;
				});
				
				setNextNavigationMap(new HashMap<String, String>(){{			
                    put(BotBSF.STATES.FINALIZAR, "#FINALIZAR");
				}});
		}};
	}
}