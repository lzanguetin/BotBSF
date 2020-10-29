package br.com.voxage.botbsf.states.trabalhador_cesta;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import br.com.voxage.basicvalidators.CurrencyValidator;
import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.models.Cestas;
import br.com.voxage.botbsf.models.DadosBeneficiario;
import br.com.voxage.botbsf.models.DadosFluxo;
import br.com.voxage.chat.botintegration.message.Message;
import br.com.voxage.chat.botintegration.message.SimpleMessage;
import br.com.voxage.vbot.BotInputResult;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class Cesta {
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {
			{
				setId("CESTA");

				setBotStateInteractionType(BotStateInteractionType.DIRECT_INPUT);
				setMaxNoInput(2);
				setMaxInputTime(120000);

				setPreFunction(botState -> {
					BotStateFlow botStateFlow = new BotStateFlow();
					botStateFlow.flow = BotStateFlow.Flow.CONTINUE;

					Message<?> message = null;
					message = SimpleMessage.text("Ok, digite o número do protocolo.");
					bot.addResponse(message);

					return botStateFlow;
				});

				setProcessDirectInputFunction((botState, userInputs) -> {
					BotInputResult botInputResult = new BotInputResult();
					DadosFluxo dadosFluxo = bot.getDadosFluxo();
					DadosBeneficiario consulta = bot.getDadosBeneficiario();
					botInputResult.setResult(BotInputResult.Result.OK);

					String str = userInputs.getConcatenatedInputs();
					String userInput = CurrencyValidator.getNumberDigitsOnly(str);
					dadosFluxo.setProt(userInput);

					List<Cestas> cestas = new ArrayList<Cestas>();
					cestas = consulta.getCestas();
					Integer aux = null;
					Integer count = 0;

					Message<?> message = null;
					message = SimpleMessage.text("Aguarde enquanto eu consulto seu cadastro");
					bot.addResponse(message);

					if(cestas != null && !cestas.isEmpty()) {
						for (Cestas cesta : cestas) {
							int parseInt = 0;

							try {
								parseInt = Integer.parseInt(userInput);

								if (cesta.getProtocolo() == parseInt) {
									aux = 1;
									bot.setPosBeneficio(count);
									count = count + 1;
								} else { 
									aux = 0;
									bot.setPosBeneficio(count);
									count = count + 1;
								}

								if (aux != null && aux == 1) {
									if ("ENTREGUE".equals(cesta.getSituacao())) {
										bot.insertTransition(14501);

										DateFormat dia = new SimpleDateFormat("dd-MM-yyyy");
										Date date = new Date();
										date = cesta.getDataEntrega();

										Message<?> messages = null;
										messages = SimpleMessage.text(String.format(
												"Você já recebeu sua cesta de número %s em %s.\n"
														+ "Todas as cestas que você tinha como benefício já foram entregues.",
												cesta.getNumero().toString(), dia.format(date)));
										bot.addResponse(messages);
										botInputResult.setIntentName(BotBSF.STATES.FINALIZAR);
									} else if ("NAO ENTREGUE".equals(cesta.getSituacao())) {
										bot.insertTransition(14502);

										DateFormat dia = new SimpleDateFormat("dd-MM-yyyy");
										Date date = new Date();
										date = cesta.getDataPrevista();

										Message<?> messages = null;
										messages = SimpleMessage
												.text(String.format("Você receberá sua cesta número %s até %s",
														cesta.getNumero().toString(), dia.format(date)));
										bot.addResponse(messages);
										botInputResult.setIntentName(BotBSF.STATES.FINALIZAR);
									}
									break;
								} else {
									bot.insertTransition(14503);
									Message<?> messages = null;
									messages = SimpleMessage.text(
											"Você não possui cestas de alimentos a serem entregues por este protocolo");
									bot.addResponse(messages);
									botInputResult.setIntentName(BotBSF.STATES.FINALIZAR);
									break;
								}
							} catch (NumberFormatException e) {
								Message<?> msg = SimpleMessage
										.text("O campo informado não é um número, por favor, me informe o protocolo");
								bot.addResponse(msg);

								if (bot.getFirstTime() == 0) {
									bot.setFirstTime(1);
									botInputResult.setResult(BotInputResult.Result.ERROR);
								} else {
									botInputResult.setIntentName(BotBSF.STATES.ATENDENTE);
								}
							}
						}
					}else {
						Message<?> msg = SimpleMessage
								.text("Não encontrei nenhuma cesta para o CPF informado");
						bot.addResponse(msg);
						botInputResult.setIntentName(BotBSF.STATES.ATENDENTE);
					}
					

					return botInputResult;
				});

				setPosFunction((botState, inputResult) -> {
					BotStateFlow botStateFlow = new BotStateFlow();
					botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
					botStateFlow.navigationKey = inputResult.getIntentName();

					return botStateFlow;
				});

				setNextNavigationMap(new HashMap<String, String>() {
					{
						put(BotBSF.STATES.FINALIZAR, "/FINALIZAR");
						put(BotBSF.STATES.ATENDENTE, "/ATENDENTE");
						put("MAX_INPUT_ERROR", "/MAX_INPUT_ERROR");
						put("MAX_NO_INPUT", "/MAX_NO_INPUT");
					}
				});
			}
		};
	}
}