package br.com.voxage.botbsf.states.trabalhador;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.models.DadosFluxo;
import br.com.voxage.botbsf.models.TransferType;
import br.com.voxage.chat.botintegration.dao.VosCenterDAO;
import br.com.voxage.chat.botintegration.entities.GroupConfiguration;
import br.com.voxage.chat.botintegration.message.Message;
import br.com.voxage.chat.botintegration.message.SimpleMessage;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class ErroCPF {
	@SuppressWarnings({ "serial", "unused" })
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("ERROCPF");
			
			setBotStateInteractionType(BotStateInteractionType.NO_INPUT);
			
			setPreFunction(botState ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				
				Message<?> message = null;
				message = SimpleMessage.text("Não localizei este CPF");
				bot.addResponse(message);
				
				return botStateFlow;
			});
			
			setPosFunction((botState, inputResult) ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				DadosFluxo dadosFluxo = bot.getDadosFluxo();
				VosCenterDAO con = new VosCenterDAO();
				Calendar horaAtendimento = Calendar.getInstance();
				int hour = horaAtendimento.get(Calendar.HOUR_OF_DAY);
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				
				dadosFluxo.setOption("1");
           	 	bot.transferType();
           	 	TransferType tipo = bot.getType();
           	 	bot.setGroup(tipo.getWeb().getGroups().get(String.format("OPCAO_%s", dadosFluxo.getOption()))); 
           	 
				Map<Integer, GroupConfiguration> groupConfigMap = new HashMap<Integer, GroupConfiguration>();
						
				groupConfigMap = con.getGroupConfigMap();
				
				GroupConfiguration group = groupConfigMap.get(Integer.parseInt(bot.getGroup()));
				
				LocalTime iniTime = group.getInitHour().toLocalDateTime().toLocalTime();
				LocalTime finalTime = group.getEndHour().toLocalDateTime().toLocalTime();
				LocalTime actualTime = LocalTime.now();
			
				
				if(iniTime.isBefore(actualTime) && finalTime.isBefore(actualTime)) {
					Message<?> message = null;
					message = SimpleMessage.text("Para falar com um de nossos atendentes sobre qualquer assunto que não seja Acionamento"
							+ " Funeral será necessário retornar o contato em dias úteis das 8h às 18 h. Em casos de falecimento, afim "
							+ "de providenciarmos o funeral nosso atendimento está disponível todos os dias da semana 24 horas por dia "
							+ "através do 0800 773 3738 ou 0800 580 3738.");
					bot.addResponse(message);
					
					Date data = new Date();
					SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd'T'HH:mm:ss");
					Integer ret = bot.buildResult(dadosFluxo.getCPF(), dadosFluxo.getCNPJ(), "Bot", "Obter CPF Trabalhador", "Fora de Horario"
							+ "ativo", date.format(data).toString(), bot.getUserSession().get("call_id").toString(), bot);
					
					inputResult.setIntentName(BotBSF.STATES.DESPEDE);
				}else {
					inputResult.setIntentName(BotBSF.STATES.ATENDENTE);
				}
				
				botStateFlow.navigationKey = inputResult.getIntentName();
				return botStateFlow;
			});
			
			setNextNavigationMap(new HashMap<String, String>(){{
				put(BotBSF.STATES.DESPEDE, "#DESPEDE");
				put(BotBSF.STATES.ATENDENTE, "#ATENDENTE");
			}});
		}};
	}
}
