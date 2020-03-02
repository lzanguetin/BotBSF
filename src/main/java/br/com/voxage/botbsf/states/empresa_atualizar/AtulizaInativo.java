package br.com.voxage.botbsf.states.empresa_atualizar;

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

public class AtulizaInativo {
	
	@SuppressWarnings({ "serial", "unused" })
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("ATUINATIVO");
			
			setBotStateInteractionType(BotStateInteractionType.NO_INPUT);
			setMaxInputTime(120000);
			
			setPreFunction(botState ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				DadosEmpresa consulta = bot.getDadosEmpresa();
				DadosFluxo dados = bot.getDadosFluxo();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				
				if(consulta.getEmailResponsavel() != null) {
					Message<?> message = null;
					message = SimpleMessage.text(String.format("Localizei seu CPF no CPF informado no início:\nEmpregador: %s\nCPF: %s\n"
							+ "Porém ele ainda está inativo. Para ativá-lo é necessário que o responsável pelo e-mail %s o acesse e clique"
							+ " em \"Autorizar Cadastramento\". Verifique o recebimento também na caixa de lixo eletrônico ou spam. Assim"
							+ " que for autorizado acesse a \"Área do Empregador\" e clique em Solicitar \"Alteração de dados cadastrais\".", 
							consulta.getNomeEmpresa(), dados.getCNPJ(), consulta.getEmailResponsavel()));
					bot.addResponse(message);
				}else {
					Message<?> message = null;
					message = SimpleMessage.text("Não identificamos no cadastro o e-mail do EMPREGADOR. Para solicitar a autorização de "
							+ "acesso de usuário no site é necessário primeiramente que o EMPREGADOR atualize seus dados cadastrais "
							+ "junto ao Benefício Social Famliar através dos DDGs 0800 773 3738 ou 0800 580 3738.");
					bot.addResponse(message);
					
					Date data = new Date();
					SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd'T'HH:mm:ss");
					Integer ret = bot.buildResult(dados.getCPF(), dados.getCNPJ(), "Bot", "Atualizar Dados", "Atualizar Inativo"
							, date.format(data).toString(), bot.getUserSession().get("call_id").toString(), bot);
				}
				return botStateFlow;
			});
			
			setPosFunction((botState, inputResult) ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				DadosEmpresa consulta = bot.getDadosEmpresa();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				
				if(consulta.getEmailResponsavel() != null) {
					bot.insertTransition(11301);
					botStateFlow.navigationKey = BotBSF.STATES.FINALIZAR;
				}else {
					bot.insertTransition(11302);
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
