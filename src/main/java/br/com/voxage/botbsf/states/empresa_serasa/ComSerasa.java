package br.com.voxage.botbsf.states.empresa_serasa;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.models.DadosEmpresa;
import br.com.voxage.chat.botintegration.message.Message;
import br.com.voxage.chat.botintegration.message.OptionBuilder;
import br.com.voxage.chat.botintegration.message.SimpleMessage;
import br.com.voxage.vbot.BotInputResult;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class ComSerasa {	
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("COMSERASA");
			
			setBotStateInteractionType(BotStateInteractionType.DIRECT_INPUT);
			setMaxNoInput(2);
			setMaxInputTime(120000);
			
			setPreFunction(botState ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				DadosEmpresa consulta = bot.getDadosEmpresa();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				
				if(consulta.getRegrasNegocio().getPossuiCadastroSerasa() == true) {
					Message<?> message = null;
					message = SimpleMessage.text("Este CNPJ/CPF está Negativado devido descumprimento de Convenção Coletiva de Trabalho");
					bot.addResponse(message);
				}else{
					Message<?> message = null;
					
					DateFormat actualDate = new SimpleDateFormat("dd/MM/yyyy");
					Date date1 = new Date();
					date1 = consulta.getRegrasNegocio().getDataParaRegularizacaoPreSerasa();
					
					message = SimpleMessage.text(String.format("Você tem até o dia %s para regularizar o cadastro junto ao Benefício "
							+ "Social Familiar e evitar que esse empregador seja negativado", actualDate.format(date1)));
					bot.addResponse(message);
				}
				
				Message<?> message = null;
				message = OptionBuilder.optionBox("").addOption("1", "1 - Deseja entender por que foi negativada")
						.addOption("2", "2 - Entende que essa cobrança é indevida").addOption("3", "3 - Deseja fazer um acordo para a "
								+ "regularização dos débitos").build();
				bot.addResponse(message);
				
				return botStateFlow;
			});
			
			setProcessDirectInputFunction((botState, userInputs) ->{
				BotInputResult botInputResult = new BotInputResult();
				botInputResult.setResult(BotInputResult.Result.OK);
				
				String userInput = userInputs.getConcatenatedInputs().toLowerCase();
				
				switch(userInput) {
					case "1 - deseja entender por que foi negativada":
						try {
							botInputResult.setIntentName(BotBSF.STATES.NEGATIVADA);
						}catch(Exception e) {
							botInputResult.setResult(BotInputResult.Result.ERROR);
						}
						break;
					case "2 - entende que essa cobrança é indevida":
						try {
							botInputResult.setIntentName(BotBSF.STATES.INDEVIDA);
						}catch(Exception e) {
							botInputResult.setResult(BotInputResult.Result.ERROR);
						}
						break;
					case "3 - deseja fazer um acordo para a regularização dos débitos":
						try {
							botInputResult.setIntentName(BotBSF.STATES.ACORDO);
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
				put(BotBSF.STATES.NEGATIVADA, "#NEGATIVADA");
				put(BotBSF.STATES.INDEVIDA, "#INDEVIDA");
				put(BotBSF.STATES.ACORDO, "#ACORDO");
				put("MAX_INPUT_ERROR", "/MAX_INPUT_ERROR");
				put("MAX_NO_INPUT", "/MAX_NO_INPUT");
			}});
		}};
	}
}

		