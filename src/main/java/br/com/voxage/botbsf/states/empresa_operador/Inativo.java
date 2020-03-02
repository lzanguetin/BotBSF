package br.com.voxage.botbsf.states.empresa_operador;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.models.DadosEmpresa;
import br.com.voxage.botbsf.models.DadosFluxo;
import br.com.voxage.chat.botintegration.message.Message;
import br.com.voxage.chat.botintegration.message.SimpleMessage;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class Inativo {
	
	@SuppressWarnings({ "serial", "unused" })
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("INATIVO");
			
			setBotStateInteractionType(BotStateInteractionType.NO_INPUT);
			
			setPreFunction(botState ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				DadosEmpresa consulta = bot.getDadosEmpresa();
				DadosFluxo dadosFluxo = bot.getDadosFluxo();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				
				if(consulta.getEmailResponsavel() != null) {
					bot.insertTransition(10801);
					Message<?> message = null;
					message = SimpleMessage.text(String.format("Localizei seu CPF como inativo no CPF/CNPJ informado:\nEmpregador: %s\n"
							+ "CPF: %s\nPara ativá-lo é necessário que o responsável pelo email %s, o acesse e clique em \"Autorizar Cadastramento\"."
							+ "Ah, verifique também na caixa de lixo eletrônico ou spam, pois o e-mail pode estar lá.", consulta.getNomeEmpresa(),dadosFluxo.getCNPJ(), consulta.getEmailResponsavel()));
					bot.addResponse(message);
				}else {
					bot.insertTransition(10802);
					Message<?> message = null;
					message = SimpleMessage.text("Não identificamos no cadastro o e-mail do EMPREGADOR. Para solicitar a autorização de "
							+ "acesso de usuário no site é necessário primeiramente que o EMPREGADOR atualize seus dados cadastrais "
							+ "junto ao Benefício Social Familiar através dos DDGs 0800 773 3738 ou 0800 580 3738");
					bot.addResponse(message);
					
					Date data = new Date();
					SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd'T'HH:mm:ss");
					Integer ret = bot.buildResult(dadosFluxo.getCPF(), dadosFluxo.getCNPJ(), "Bot", "Acesso do Operador", "Operador Inativo"
							, date.format(data).toString(), bot.getUserSession().get("call_id").toString(), bot);
				}
				return botStateFlow;
			});
			
			setPosFunction((botState, inputResult) ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				DadosEmpresa consulta = bot.getDadosEmpresa();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				
				if(consulta.getEmailResponsavel() != null) {
					botStateFlow.navigationKey = BotBSF.STATES.FINALIZAR;
				}else {
					botStateFlow.navigationKey = BotBSF.STATES.DESPEDE;
				}
				return botStateFlow;
			});
			
			setNextNavigationMap(new HashMap<String, String>(){{
				put(BotBSF.STATES.FINALIZAR, "/FINALIZAR");
				put(BotBSF.STATES.DESPEDE, "/DESPEDE");
			}});
		}};
	}
}