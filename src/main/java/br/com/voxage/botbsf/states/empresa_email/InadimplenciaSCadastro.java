package br.com.voxage.botbsf.states.empresa_email;

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

public class InadimplenciaSCadastro {
	@SuppressWarnings({ "serial", "unused" })
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
				setId("INADSCADASTRO");
				
				setBotStateInteractionType(BotStateInteractionType.DIRECT_INPUT);
				setMaxNoInput(2);
				setMaxInputTime(120000);
				
				setPreFunction(botState ->{
					BotStateFlow botStateFlow = new BotStateFlow();
					DadosEmpresa consulta = bot.getDadosEmpresa();
					DadosFluxo dados = bot.getDadosFluxo();
					botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
					
					if(consulta.getEmailResponsavel() != null) {
						Message<?> message1 = null;
						message1 = SimpleMessage.text(String.format("Para ter acesso aos boletos em aberto do\nEmpregador: %s\nCNPJ/CPF: %s\nvocê"
								+ " precisa ser um usuário autorizado ao acesso no site, porém seu CPF não consta como autorizado.", 
								consulta.getNomeEmpresa(), dados.getCNPJ()));
						bot.addResponse(message1);
						
						Message<?> message2 = null;
						message2 = OptionBuilder.optionBox(String.format("Deseja solicitar autorização de acesso? (neste caso o responsável pelo e-mail %s"
								+ " receberá sua solicitação e precisará autorizá-lo).", consulta.getEmailResponsavel())).addOption("1", "Sim").addOption("2", "Não").build();
						bot.addResponse(message2);
					}else {
						Message<?> message = null;
						message = SimpleMessage.text("Não identificamos no cadastro o e-mail do EMPREGADOR. Para solicitar a autorização de "
								+ "acesso de usuário no site é necessário primeiramente que o EMPREGADOR atualize seus dados cadastrais "
								+ "junto ao Benefício Social Familiar através dos DDGs 0800 773 3738 ou 0800 580 3738.");
						bot.addResponse(message);
						
						Date data = new Date();
						SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd'T'HH:mm:ss");
						Integer ret = bot.buildResult(dados.getCPF(), dados.getCNPJ(), "Bot", "Inadimplencia", "Inadimplencia Sem Cadastro"
								+ "ativo", date.format(data).toString(), bot.getUserSession().get("call_id").toString(), bot);
					}
					return botStateFlow;
				});
				
				setProcessDirectInputFunction((botState, userInputs) ->{
					BotInputResult botInputResult = new BotInputResult();
					DadosFluxo dados = bot.getDadosFluxo();
					botInputResult.setResult(BotInputResult.Result.OK);
					
					String userInput = userInputs.getConcatenatedInputs().toLowerCase();
					
					switch(userInput) {
						case "sim":
							try {
								bot.insertTransition(12501);
								Message<?> message = null;
								message = SimpleMessage.text("Acesse o site 'www.beneficiosocial.com.br', na área do Empregador, "
										+ "digite o CPF ou CNPJ do empregador, clique em 'Cadastrar Novo Usuário' e siga o passo a"
										+ "passo");
								bot.addResponse(message);
								
								Date data = new Date();
								SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd'T'HH:mm:ss");
								Integer ret = bot.buildResult(dados.getCPF(), dados.getCNPJ(), "Bot", "Inadimplencia Sem Cadastro", "Solicitar Autorizacao"
										+ "ativo", date.format(data).toString(), bot.getUserSession().get("call_id").toString(), bot);
								
								botInputResult.setIntentName(BotBSF.STATES.DESPEDE);
							}catch(Exception e) {
								botInputResult.setResult(BotInputResult.Result.ERROR);
							}
							break;
						case "não":
							try {
								bot.insertTransition(12502);
								Message<?> message = null;
								message = SimpleMessage.text(String.format("Bom, como você não é um usuário cadastrado, não tem como "
										+ "acessar ao Portal da empresa – CPF/CNPJ %s. Para resolver isso, acesse o site "
										+ "'www.beneficiosocial.com.br', digite o CPF ou CNPJ do empregador e se cadastre como "
										+ "usuário. Lembrando que o responsável por essa empresa no site, receberá um e-mail "
										+ "autorizando o seu acesso.", dados.getCNPJ()));
								bot.addResponse(message);
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

