package br.com.voxage.botbsf.states.global;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import br.com.voxage.basicvalidators.CNPJValidator;
import br.com.voxage.basicvalidators.CPFValidator;
import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.BotBSFIntegration;
import br.com.voxage.botbsf.models.DadosEmpresa;
import br.com.voxage.botbsf.models.DadosFluxo;
import br.com.voxage.chat.botintegration.entities.AttendantClientInfo;
import br.com.voxage.chat.botintegration.message.Message;
import br.com.voxage.chat.botintegration.message.SimpleMessage;
import br.com.voxage.vbot.BotInputResult;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class ObterCNPJ {
	@SuppressWarnings({ "serial", "unused" })
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("CNPJ");
			
			setBotStateInteractionType(BotStateInteractionType.DIRECT_INPUT);
			setMaxNoInput(2);
			setMaxInputTime(120000);
			
			setPreFunction(botState ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				
				setInitialMessage("Ok. Por favor digite agora o CNPJ da empresa. Ah! se você não possui um CNPJ e é um empregador digite seu CPF");
	
				return botStateFlow;
			});
			
			setProcessDirectInputFunction((botState, userInputs) ->{
				BotInputResult botInputResult = new BotInputResult();
				DadosFluxo dadosFluxo = bot.getDadosFluxo();
				botInputResult.setResult(BotInputResult.Result.OK);
				String userInput = userInputs.getConcatenatedInputs().replaceAll("\\D+", "");;
				
				dadosFluxo.setCNPJ(userInput);
				
				if(((CPFValidator.isValidCPF(userInput)) == true) || ((CNPJValidator.isValidCNPJ(userInput)) == true)){
					AttendantClientInfo cInfo = new AttendantClientInfo();
					List<AttendantClientInfo> att;
					att = new ArrayList<AttendantClientInfo>();	
					
					cInfo.setName("CPF-CNPJ");
					cInfo.setValue(userInput);
					bot.setaInfo(cInfo);
					att.add(bot.getaInfo());
					bot.setcInfo(att);
					
					bot.getUserSession().put("CLIENT_INFO", bot.getcInfo());
					
					Date data = new Date();
					SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd'T'HH:mm:ss");
					Integer ret = bot.buildResult(dadosFluxo.getCPF(), dadosFluxo.getCNPJ(), "Bot", "Empregador", "Empregador - Informou CPF/CNPJ válido"
							, date.format(data).toString(), bot.getUserSession().get("call_id").toString(), bot);
					bot.writeGenericField(userInput);
					botInputResult.setResult(BotInputResult.Result.OK);
				}else {
					botInputResult.setResult(BotInputResult.Result.ERROR);
				}
				
				return botInputResult;
			});
			
			setAsyncPosFunction((botState, inputResult)-> CompletableFuture.supplyAsync(() ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				DadosFluxo dadosFluxo = bot.getDadosFluxo();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				
				DadosEmpresa customerInfo = null;
				
				Message<?> message = null;
				message = SimpleMessage.text("Aguarde enquanto eu consulto seu cadastro.");
				bot.addResponse(message);
				
				try {
					customerInfo = BotBSFIntegration.dadosEmpresa(bot, dadosFluxo.getCNPJ());
					bot.setDadosEmpresa(customerInfo);
					
					DadosEmpresa consulta = bot.getDadosEmpresa();
					
					Message<?> message1 = null;
					message1 = SimpleMessage.text("Pronto! Já localizei.");
					bot.addResponse(message1);
					
					Message<?> message2 = null;
					if(consulta.getRegrasNegocio().getQtdPlanos() == 1) {
						message2 = SimpleMessage.text(String.format("Esse CNPJ já possui %s cadastro(s). Para cadastrar mais um plano (Adicionar um Sindicato) "
								+ "junto ao Benefício Social Familiar acesse a Área do Empregador, insira o CNPJ ou CPF e clique em Solicitar "
								+ "alteração de dados cadastrais, na opção Adicionar Planos. Para outras opções permaneça "
								+ "conectado.", consulta.getRegrasNegocio().getQtdPlanos()));
					}else {
						message2 = SimpleMessage.text(String.format("Esse CNPJ já possui %s cadastro(s). Para cadastrar mais um plano (Adicionar um Sindicato) "
								+ "junto ao Benefício Social Familiar acesse a Área do Empregador, insira o CNPJ ou CPF e clique em Solicitar "
								+ "alteração de dados cadastrais, na opção Adicionar Planos. Para outras opções permaneça "
								+ "conectado.", consulta.getRegrasNegocio().getQtdPlanos()));
					}

					bot.addResponse(message2);
					
					botStateFlow.navigationKey = BotBSF.STATES.MENUEMPRESA;
					
					bot.insertTransition(10102);
					
					Date data = new Date();
					SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd'T'HH:mm:ss");
					Integer ret = bot.buildResult(dadosFluxo.getCPF(), dadosFluxo.getCNPJ(), "Bot", "Obter CNPJ", "Localizou cadastro "
							+ "ativo", date.format(data).toString(), bot.getUserSession().get("call_id").toString(), bot);
				}catch(Exception e) {
					if(bot.getErro() != 500) {
						Message<?> message3 = null;
						message3 = SimpleMessage.text("Para se cadastrar junto ao Benefício Social Familiar - acesse a \"Área do "
								+ "Empregador\", clique em \"Cadastre o empregador\" e preencha o formulário até concluí-lo. Para outras"
								+ " opções permaneça conectado.");
						bot.addResponse(message3);
						
						bot.insertTransition(10101);
					}else{
						bot.insertTransition(9997);
					}
					Date data = new Date();
					SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd'T'HH:mm:ss");
					Integer ret = bot.buildResult(dadosFluxo.getCPF(), dadosFluxo.getCNPJ(), "Bot", "Obter CNPJ", "Não localizou cadastro "
							+ "ativo", date.format(data).toString(), bot.getUserSession().get("call_id").toString(), bot);
					
					botStateFlow.navigationKey = BotBSF.STATES.ATENDENTE;
				}
				
				return botStateFlow;
			}));
			
			setNextNavigationMap(new HashMap<String, String>(){{
				put(BotBSF.STATES.MENUEMPRESA, "#MENUEMPRESA");
				put(BotBSF.STATES.ATENDENTE, "#ATENDENTE");
                put("MAX_INPUT_ERROR", "/MAX_INPUT_ERROR");
                put("MAX_NO_INPUT", "/MAX_NO_INPUT");
			}});
		}};
	}
}
