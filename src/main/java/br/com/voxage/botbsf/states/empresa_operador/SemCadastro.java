package br.com.voxage.botbsf.states.empresa_operador;

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

public class SemCadastro {
	@SuppressWarnings({ "serial", "unused" })
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("SCADASTRO");
			
			setBotStateInteractionType(BotStateInteractionType.DIRECT_INPUT);
			setMaxNoInput(2);
			setMaxInputTime(120000);
			
			setPreFunction(botState ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				
				Message<?> message = null;
				message = OptionBuilder.optionBox("O CPF informado não possui cadastro para acesso.\nVocê deseja "
						+ "realizar o cadastro agora?").addOption("1", "Sim").addOption("2", "Não").build();
				bot.addResponse(message);
				
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
							bot.insertTransition(10902);
							Message<?> message = null;
							message = SimpleMessage.text("Acesse o site 'www.beneficiosocial.com.br', na área do Empregador, digite o "
									+ "CPF ou CNPJ do Empregador, clique em 'Cadastrar Novo usuário' e siga o passo a passo.");
							bot.addResponse(message);
							
							Date data = new Date();
							SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd'T'HH:mm:ss");
							Integer ret = bot.buildResult(dadosFluxo.getCPF(), dadosFluxo.getCNPJ(), "Bot", "Operador Sem Cadastro", "Acesso do Operador - Quer Cadastrar"
									, date.format(data).toString(), bot.getUserSession().get("call_id").toString(), bot);
							
							botInputResult.setIntentName(BotBSF.STATES.DESPEDE);
						}catch(Exception e) {
							botInputResult.setResult(BotInputResult.Result.ERROR);
						}
						break;
					case "não":
						try {
							bot.insertTransition(10901);
							DadosFluxo dados = bot.getDadosFluxo();
							DadosEmpresa dadosEmpresa = bot.getDadosEmpresa();
							Message<?> message = null;
							message = SimpleMessage.text(String.format("Bom, como você não é um usuário cadastrado, não tem como acessar"
									+ " ao Portal – CPF/CNPJ %s - Empregador %s aqui no site do Benefício Social Familiar. Desta forma "
									+ "será necessário, assim que possível, acessar a \"Área do Empregador\", informar o CPF ou CNPJ "
									+ "para acesso, se cadastrar como um usuário e ser autorizado pelo responsável desta empresa para "
									+ "então prosseguir.", dados.getCNPJ(), dadosEmpresa.getNomeEmpresa()));
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
				put(BotBSF.STATES.DESPEDE, "/DESPEDE");
				put(BotBSF.STATES.FINALIZAR, "/FINALIZAR");
				put("MAX_INPUT_ERROR", "/MAX_INPUT_ERROR");
				put("MAX_NO_INPUT", "/MAX_NO_INPUT");
			}});
		}};
	}
}