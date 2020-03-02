package br.com.voxage.botbsf.states.trabalhador;

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
import br.com.voxage.botbsf.models.DadosBeneficiario;
import br.com.voxage.botbsf.models.DadosFluxo;
import br.com.voxage.chat.botintegration.entities.AttendantClientInfo;
import br.com.voxage.chat.botintegration.message.Message;
import br.com.voxage.chat.botintegration.message.SimpleMessage;
import br.com.voxage.vbot.BotInputResult;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class CPF {
	@SuppressWarnings({ "serial", "unused" })
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("CPF");
			setBotStateInteractionType(BotStateInteractionType.DIRECT_INPUT);
			setMaxNoInput(2);
			setMaxInputTime(120000);
			
			setPreFunction(botState ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				
				Message<?> message = null;
				message = SimpleMessage.text("Ok, agora por favor digite o seu CPF");
				bot.addResponse(message);
				
				return botStateFlow;
			});
			
			setProcessDirectInputFunction((botState, userInputs) ->{
				BotInputResult botInputResult = new BotInputResult();
				DadosFluxo dadosFluxo = bot.getDadosFluxo();
				botInputResult.setResult(BotInputResult.Result.OK);
				String userInput = userInputs.getConcatenatedInputs().replaceAll("\\D+", "");;
				
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
					
					dadosFluxo.setCPF(userInput);
					bot.writeGenericField(userInput); 
					Date data = new Date();
					SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd'T'HH:mm:ss");
					Integer ret = bot.buildResult(dadosFluxo.getCPF(), dadosFluxo.getCNPJ(), "Bot", "Trabalhador/Beneficiario", "Trabalhador/Beneficiario - Informou CPF/CNPJ válido"
							, date.format(data).toString(), bot.getUserSession().get("call_id").toString(), bot);
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
				
				DadosBeneficiario customerInfo = null;
				
				try {
					customerInfo = BotBSFIntegration.dadosBeneficiario(bot, dadosFluxo.getCPF());
					bot.setDadosBeneficiario(customerInfo);
					botStateFlow.navigationKey = BotBSF.STATES.SEGUECPF;
				}catch(Exception e) {
					botStateFlow.navigationKey = BotBSF.STATES.ERROCPF;
				}
				
				return botStateFlow;
			}));
			
			setNextNavigationMap(new HashMap<String, String>(){{
				put(BotBSF.STATES.SEGUECPF, "#SEGUECPF");
				put(BotBSF.STATES.ERROCPF, "#ERROCPF");
                put("MAX_INPUT_ERROR", "/MAX_INPUT_ERROR");
                put("MAX_NO_INPUT", "/MAX_NO_INPUT");
			}});
		}};
	}
}
