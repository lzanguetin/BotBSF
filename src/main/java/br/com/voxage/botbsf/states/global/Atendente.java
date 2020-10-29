package br.com.voxage.botbsf.states.global;

import static br.com.voxage.chat.botintegration.utils.AppLogger.log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.models.DadosFluxo;
import br.com.voxage.botbsf.models.TransferType;
import br.com.voxage.chat.botintegration.message.SimpleMessage;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class Atendente {
    @SuppressWarnings({ "serial", "rawtypes", "unused" })
	public static BotState load(BotBSF bot) {
        return new BotState("/") {{
            setId("ATENDENTE");
            setMaxInputError(2);
            setMaxNoInput(2);
            setBotStateInteractionType(BotStateInteractionType.NO_INPUT);       
            
            setPreFunction(botState -> {
                log.info("=================== RESUMO PARA ATENDENTE =================== ", bot.getSessionId());   
                	if (bot.getUserSession().containsKey("CLIENT_INFO"))
                		log.info("Transf: " + bot.getUserSession().get("CLIENT_INFO"), bot.getSessionId());
                log.info("============================================================= ", bot.getSessionId());
                
                DadosFluxo dadosFluxo = bot.getDadosFluxo();
				Date data = new Date();
				SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd'T'HH:mm:ss");
				Integer ret = bot.buildResult(dadosFluxo.getCPF(), dadosFluxo.getCNPJ(), "Bot", "Atendente", 
						String.format("Atendente - %s%s", bot.getMenuType(), bot.getOcorrencia())
						, date.format(data).toString(), bot.getUserSession().get("call_id").toString(), bot);

                BotStateFlow state = new BotStateFlow();
				SimpleMessage message = SimpleMessage.text("Aguarde que um dos nossos atendentes irá te ajudar.");
				bot.addResponse(message);
                state.flow = BotStateFlow.Flow.CONTINUE;
                return state;
            });

            setPosFunction((botState, inputResult) -> {
            	 bot.transferType();
            	 TransferType tipo = bot.getType();
            	 DadosFluxo dadosFluxo = bot.getDadosFluxo();
            	 dadosFluxo.setOption("1");
            	 
            	 if(bot.getSessionId().contains("whatsapp")) {
                	 bot.setOption("transfer_to_attendant(" + tipo.getWhatsapp().getSubjectIds().get(String.format("OPCAO_%s", dadosFluxo.getOption())) + ")");
                	 bot.setGroup(tipo.getWhatsapp().getGroups().get(String.format("OPCAO_%s", dadosFluxo.getOption()))); 
            	 }else if(bot.getSessionId().contains("telegram")) {
                	 bot.setOption("transfer_to_attendant(" + tipo.getTelegram().getSubjectIds().get(String.format("OPCAO_%s", dadosFluxo.getOption())) + ")");
                	 bot.setGroup(tipo.getTelegram().getGroups().get(String.format("OPCAO_%s", dadosFluxo.getOption())));
            	 }else if(bot.getSessionId().contains("messenger")) {
                	 bot.setOption("transfer_to_attendant(" + tipo.getMessenger().getSubjectIds().get(String.format("OPCAO_%s", dadosFluxo.getOption())) + ")");
                	 bot.setGroup(tipo.getMessenger().getGroups().get(String.format("OPCAO_%s", dadosFluxo.getOption())));
            	 }else {
                	 bot.setOption("transfer_to_attendant(" + tipo.getWeb().getSubjectIds().get(String.format("OPCAO_%s", dadosFluxo.getOption())) + ")");
                	 bot.setGroup(tipo.getWeb().getGroups().get(String.format("OPCAO_%s", dadosFluxo.getOption())));
            	 }
            	      	
            	 String CHAT_TRANSFER_WHATSAPP = bot.getOption();
                 String CHAT_TRANSFER_TELEGRAM = bot.getOption();
                 String CHAT_TRANSFER_WEB = bot.getOption();
                 int GROUP_ID_CHAT_TRANSFER_WHATSAPP = Integer.parseInt(bot.getGroup());
                 int GROUP_ID_CHAT_TRANSFER_TELEGRAM = Integer.parseInt(bot.getGroup());
                 int GROUP_ID_CHAT_TRANSFER_WEB = Integer.parseInt(bot.getGroup());
            	
                 log.info("CHAT_TRANSFER_WHATSAPP=" + CHAT_TRANSFER_WHATSAPP, bot.getSessionId());
                 log.info("CHAT_TRANSFER_TELEGRAM=" + CHAT_TRANSFER_TELEGRAM, bot.getSessionId());
                 log.info("CHAT_TRANSFER_WEB=" + CHAT_TRANSFER_WEB, bot.getSessionId());
                 
                 log.info("GROUP_ID_CHAT_TRANSFER_WHATSAPP=" + GROUP_ID_CHAT_TRANSFER_WHATSAPP, bot.getSessionId());
                 log.info("GROUP_ID_CHAT_TRANSFER_TELEGRAM=" + GROUP_ID_CHAT_TRANSFER_TELEGRAM, bot.getSessionId());
                 log.info("GROUP_ID_CHAT_TRANSFER_WEB=" + GROUP_ID_CHAT_TRANSFER_WEB, bot.getSessionId());
            	
                BotStateFlow state = new BotStateFlow();

                try {
                    if (bot.getSessionId().contains("telegram")) {
                        log.info("============================================================= ", bot.getSessionId());
                        log.info("======          TELEGRAM DETECTADO                    ======= ", bot.getSessionId());
                        log.info("============================================================= ", bot.getSessionId());
                        if (bot.canTransferToAttendant(GROUP_ID_CHAT_TRANSFER_TELEGRAM)) {
                            state.flow = BotStateFlow.Flow.TRANSFER;
                            bot.setBotNameToTransfer(CHAT_TRANSFER_TELEGRAM);
                            log.info("BotNameToTransfer=" + CHAT_TRANSFER_TELEGRAM, bot.getSessionId());
                        } else {
                            state.flow = BotStateFlow.Flow.END_CURRENT_STATE;
                            state.navigationKey = "#FORA_HORARIO";
                            log.info("FORA_HORARIO", bot.getSessionId());
                        }
                    } else if (bot.getSessionId().contains("whatsapp")) {
                        log.info("============================================================= ", bot.getSessionId());
                        log.info("======          WHATSAPP DETECTADO                    ======= ", bot.getSessionId());
                        log.info("============================================================= ", bot.getSessionId());
                        if (bot.canTransferToAttendant(GROUP_ID_CHAT_TRANSFER_WHATSAPP)) {
                            state.flow = BotStateFlow.Flow.TRANSFER;
                            bot.setBotNameToTransfer(CHAT_TRANSFER_WHATSAPP);
                            log.info("BotNameToTransfer=" + CHAT_TRANSFER_WHATSAPP, bot.getSessionId());
                        } else {
                            state.flow = BotStateFlow.Flow.END_CURRENT_STATE;
                            state.navigationKey = "#FORA_HORARIO";
                            log.info("FORA_HORARIO", bot.getSessionId());
                        }
                    } else {
                        log.info("============================================================= ", bot.getSessionId());
                        log.info("======          WEB DETECTADO                    ======= ", bot.getSessionId());
                        log.info("============================================================= ", bot.getSessionId());
                        if (bot.canTransferToAttendant(GROUP_ID_CHAT_TRANSFER_WEB)) {
                            state.flow = BotStateFlow.Flow.TRANSFER;
                            bot.setBotNameToTransfer(CHAT_TRANSFER_WEB);
                            log.info("BotNameToTransfer=" + bot.getBotNameToTransfer(), bot.getSessionId());
                        } else {
                            state.flow = BotStateFlow.Flow.END_CURRENT_STATE;
                            state.navigationKey = "#FORA_HORARIO";
                            log.info("FORA_HORARIO", bot.getSessionId());
                        }
                    }
                }catch(Exception e){
                    log.error("Erro ao tentar transferir para etendente", e, bot.getSessionId());
                    state.flow = BotStateFlow.Flow.END_CURRENT_STATE;
                    state.navigationKey = "#FORA_HORARIO";
                }
                return state;
            });
            
            setNextNavigationMap(new HashMap<String, String>() {
                {
                    put("#FORA_HORARIO", "#FORA_HORARIO");
                }
            });
        }};
    }
}
