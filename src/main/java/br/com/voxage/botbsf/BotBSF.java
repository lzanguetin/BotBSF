package br.com.voxage.botbsf;

import java.util.HashMap;
import java.util.Map;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.models.DadosFluxo;
import br.com.voxage.chat.botintegration.ISearchEngine;
import br.com.voxage.chat.botintegration.ISearchEngineCredentials;
import br.com.voxage.chat.botintegration.TextSearchEngine;
import br.com.voxage.chat.botintegration.annotation.Bot;
import br.com.voxage.chat.botintegration.entities.BotImageType;
import br.com.voxage.vbot.BotContext;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateInteractionType;
import br.com.voxage.vbot.VBot;
import br.com.voxage.vbot.utils.ClassFinder;

@SuppressWarnings("serial")
@Bot(name="BotBSF")
public class BotBSF extends VBot {
	
	public static int NO_INPUT_TIMEOUT = 180000;
	
	private String lastState;
	private DadosFluxo dadosFluxo;
	
	 public interface STATES{
	    	String START = "start";
	    	String TRABALHADOR = "trabalhador";
	    	String REPRESENTANTE = "representante";
	    	
	    	String BOLINDI = "bolindi";
	    	String RELTRAB = "reltrab";
	    	String IMPRBOL = "imprbol";
	    	String PAGBOL = "pagbol"; 
	    	String ACNFUN = "acnfun";
	      	String CADEMP = "cademp";
	    	String COMENV = "comenv";
	    	String ANDABENF = "andabenf";
	    	String VALCARD = "valcard";
	    	
	    	String DESBCRT = "desbcrt";
	    	String DESCFOL = "descfol";
	    	String CRTID = "crtid";
	    	String BNFPREST = "bnfprest";
	    	String SOLBENEF = "solbenef";
	    	String ACOMBENEF = "acombenef";
	    	String COMEV = "comev";
	    	String VEREV = "verev";
	    	String BSF = "bsf";
	    	
	    	//String MOTIVOIMP = "motivoimp";
	    	//String MOTIVOREAG = "motivoreag";
	    	//String ATENDENTE = "atendente";
	    	
			//String CEABS_OS = "transfer_to_attendant(1)";
		    //String CEABS_IMPRODUTIVA = "transfer_to_attendant(2)";
			//String CEABS_REAGENDAMENTO = "transfer_to_attendant(3)";
		    //String CEABS_APOIO = "transfer_to_attendant(4)";
			//String CEABS_OUTROS = "transfer_to_attendant(5)";
	}
	 
	public BotBSF() {
		this.dadosFluxo = new DadosFluxo();
	}
	 
	public Map<String, Object> getDefaultParameters() {
		return null;
	}

	public String getCustomBotName() {
		return "Bot BSF";
	}

	public String getCustomImageName() {
		return "bsf-logo.png";
	}

	public BotImageType getBotImageType() {
		return BotImageType.CUSTOM;
	}

	public boolean recordDialog() {
		return false;
	}

	public void writeTimeoutResult() {
	}

	@Override
	public String getDebugCommand(String arg0) {
		return null;
	}

	@Override
	protected TextSearchEngine getDefaultAutoCompleteSearchEngine() {
		return null;
	}

	@Override
	protected ISearchEngineCredentials getDefaultEngineCredentials() {
		return null;
	}

	@Override
	protected ISearchEngine getDefaultNLPSearchEngine() {
		return null;
	}

	@Override
	protected String getPackageName() {
		return "br.com.voxage.botbsf";
	}

	@Override
	public void loadStates() {
		BotContext botContext = new BotContext();
		Map<String, BotState> m = new HashMap<>();
		m.putAll(ClassFinder.loadAllStates("br.com.voxage.botbsf.states", BotBSF.class, BotBSF.this));
		botContext.setId("/");
		botContext.setContextNavigationMap(new HashMap<String, String>() {			
        });
		m.put("END", new BotState("/") {
            {
                setId("END");
                setBotStateInteractionType(BotStateInteractionType.TERMINATE);
            }
        });
		
		m.put("TERMINATE", new BotState("/") {
            {
                setId("TERMINATE");
                setBotStateInteractionType(BotStateInteractionType.TERMINATE);
            }
        });
		
		botContext.setStatesMap(m);
		super.contexts.put("/", botContext);
	}
	
    public void setLastState(String lastState) {
        this.lastState = lastState;
    }
    
    public String getLastState() {
        return lastState;
    }

	public DadosFluxo getDadosFluxo() {
		return this.dadosFluxo;
	}
}