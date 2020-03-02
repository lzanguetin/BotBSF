package br.com.voxage.botbsf.states.empresa_serasa;

import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.models.DadosEmpresa;
import br.com.voxage.chat.botintegration.bean.BotExternalLink;
import br.com.voxage.chat.botintegration.message.Message;
import br.com.voxage.chat.botintegration.message.MessageType.General;
import br.com.voxage.chat.botintegration.message.SimpleMessage;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class CobNaoSegmento {
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
				setId("COBSEGMENTO");
				
				setBotStateInteractionType(BotStateInteractionType.NO_INPUT);
				
				setPreFunction(botState ->{
					BotStateFlow botStateFlow = new BotStateFlow();
					botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
					
					Message<?> message = null;
					message = new SimpleMessage<BotExternalLink>(General.EXTERNAL_LINK, new BotExternalLink("Acesse o link "
							+ "https://www.beneficiosocial.com.br/solicitar-inativacao e preencha o formulário.", "https://www.beneficiosocial"
									+ ".com.br/solicitar-inativacao", "https://www.beneficiosocial.com.br/solicitar-inativacao"));
					bot.addResponse(message);
					
					return botStateFlow;
				});
				
				setPosFunction((botState, inputResult) ->{
					BotStateFlow botStateFlow = new BotStateFlow();
					DadosEmpresa consulta = bot.getDadosEmpresa();
					botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
					
					if(consulta.getRegrasNegocio().getPossuiMaisDeUmCadastroSerasaOuPreSerasa() == true) {
						bot.insertTransition(13601);
						Message<?> message = null;
						message = SimpleMessage.text("Identificamos que para o CPF/CNPJ informados há vários cadastros, portanto podem "
								+ "haver mais casos ocasionando a negativação do empregador.");
						bot.addResponse(message);
						botStateFlow.navigationKey = BotBSF.STATES.FINALIZAR;
					}else {
						bot.insertTransition(13602);
						botStateFlow.navigationKey = BotBSF.STATES.FINALIZAR;
					}

					return botStateFlow;
				});
				
				setNextNavigationMap(new HashMap<String, String>(){{			
                    put(BotBSF.STATES.FINALIZAR, "#FINALIZAR");
				}});
		}};
	}
}