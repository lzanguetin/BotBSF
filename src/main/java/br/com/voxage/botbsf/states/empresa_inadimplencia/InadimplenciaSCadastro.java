package br.com.voxage.botbsf.states.empresa_inadimplencia;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateInteractionType;

public class InadimplenciaSCadastro {
	private static final String INITIAL_MESSAGE = "{" + 
	           "   \"message\":\"Olá, você está no atendimento online da Gestora do Benefício Social Familiar!\nEu sou a Benê, assitente gigital e estou aqui para te ajudar. Você deseja falar sobre assuntos da Empresa ou do seu Benefício?\"," + 
	           "   \"options\":[" + 
	           "      {" + 
	           "         \"id\":1," + 
	           "         \"text\":\"Empresa\"" + 
	           "      }," + 
	           "      {" + 
	           "         \"id\":2," + 
	           "         \"text\":\"Beneficiário\"" + 
	           "      }" + 
	           "   ]" + 
	           "}";
	
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
				setId("INADSCADASTRO");
				
				setBotStateInteractionType(BotStateInteractionType.DIRECT_INPUT);
}
