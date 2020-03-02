package br.com.voxage.botbsf.states.empresa_atualizar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.models.DadosEmpresa;
import br.com.voxage.botbsf.models.DadosFluxo;
import br.com.voxage.chat.botintegration.message.Message;
import br.com.voxage.chat.botintegration.message.OptionBuilder;
import br.com.voxage.chat.botintegration.message.SimpleMessage;
import br.com.voxage.vbot.BotInputResult;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class AtulizaSCadastro {
	@SuppressWarnings({ "serial", "unused" })
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("ATUSCADASTRO");
			
			setBotStateInteractionType(BotStateInteractionType.DIRECT_INPUT);
			setMaxInputTime(120000);
			
			setPreFunction(botState ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				DadosEmpresa consulta = bot.getDadosEmpresa();
				DadosFluxo dados = bot.getDadosFluxo();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				
				if(consulta.getEmailResponsavel() != null) {
					Message<?> message1 = null;
					Message<?> message2 = null;
					
					message1 = SimpleMessage.text("Acesse o site 'www.beneficiosocial.com.br', na área do Empregador, digite o CPF ou "
							+ "CNPJ do Empregador, clique em 'Cadastrar Novo usuário' e siga o passo a passo.");
					
					message2 = OptionBuilder.optionBox(String.format("Você deseja solicitar autorização de acesso? (neste caso o "
							+ "responsável pelo e-mail %s receberá sua solicitação e precisará autorizá-lo).", consulta.getEmailResponsavel()))
							.addOption("1", "Sim").addOption("2", "Não").build();

					bot.addResponse(message1);
					bot.addResponse(message2);
				}else {
					Message<?> message = null;
					message = SimpleMessage.text("Não identificamos no cadastro o e-mail do EMPREGADOR. Para solicitar a autorização de "
							+ "acesso de usuário no site é necessário primeiramente que o EMPREGADOR atualize seus dados cadastrais "
							+ "junto ao Benefício Social Famliar através dos DDGs 0800 773 3738 ou 0800 580 3738.");
					bot.addResponse(message);
					
					Date data = new Date();
					SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd'T'HH:mm:ss");
					Integer ret = bot.buildResult(dados.getCPF(), dados.getCNPJ(), "Bot", "Atualizar Dados", "Atualizar Sem Cadastro"
							, date.format(data).toString(), bot.getUserSession().get("call_id").toString(), bot);
				}
				return botStateFlow;
			});
			
			setProcessDirectInputFunction((botState, userInputs) ->{
				BotInputResult botInputResult = new BotInputResult();
				DadosFluxo dadosFluxo = bot.getDadosFluxo();
				botInputResult.setResult(BotInputResult.Result.OK);
				
				String userInput = userInputs.getConcatenatedInputs().toLowerCase();
				
				switch(userInput) {
					case "sim":
						try {
							Message<?> message = null;
							message = SimpleMessage.text("Você será direcionado para a \"Área do Empregador\". Digite o CPF ou CNPJ e Clique em Cadastrar novo usuário. O Benefício Social Familiar agradece seu contato.");
							bot.addResponse(message);
							bot.insertTransition(11401);
							Date data = new Date();
							SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd'T'HH:mm:ss");
							Integer ret = bot.buildResult(dadosFluxo.getCPF(), dadosFluxo.getCNPJ(), "Bot", "Atualizar Sem Cadastro", "Atualizar Dados - Solicitar Autorização"
									, date.format(data).toString(), bot.getUserSession().get("call_id").toString(), bot);
							botInputResult.setIntentName(BotBSF.STATES.DESPEDE);
						}catch(Exception e) {
							botInputResult.setResult(BotInputResult.Result.ERROR);
						}
						break;
					case "não":
						try {
							Message<?> message = null;
							message = SimpleMessage.text(String.format("Bom, como você não é um usuário cadastrado, não tem como acessar ao Portal "
									+ "– CPF %s no site do Benefício Social Familiar. Desta forma será necessário, assim que possível, "
									+ "acessar ao site www.beneficiosocial.com.br e ao digitar o CPF ou CNPJ do empregador no acesso "
									+ "à mesma, se cadastrar como usuário e deverá ser autorizado pelo responsável da empresa para que "
									+ "possa prosseguir.", dadosFluxo.getCPF()));
							bot.addResponse(message);
							bot.insertTransition(11402);
							botInputResult.setIntentName(BotBSF.STATES.FINALIZAR);
						}catch(Exception e) {
							botInputResult.setResult(BotInputResult.Result.ERROR);
						}
						break;
					default:
						botInputResult.setResult(BotInputResult.Result.ERROR);
				}
				return botInputResult;
			});
			
			setPosFunction((botState, inputResult) ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				botStateFlow.navigationKey = inputResult.getIntentName();
				
				return botStateFlow;
			});
			
			setNextNavigationMap(new HashMap<String, String>(){{
				put(BotBSF.STATES.DESPEDE, "#DESPEDE");
				put(BotBSF.STATES.FINALIZAR, "#FINALIZAR");
				put("MAX_INPUT_ERROR", "/MAX_INPUT_ERROR");
				put("MAX_NO_INPUT", "/MAX_NO_INPUT");
			}});
		}};
	}
}