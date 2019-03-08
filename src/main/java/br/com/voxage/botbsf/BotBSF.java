package br.com.voxage.botbsf;

import java.util.HashMap;
import java.util.Map;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.models.DadosFluxo;
import br.com.voxage.botbsf.models.ConsultaCNPJ;
import br.com.voxage.botbsf.models.ConsultaCPF;
import br.com.voxage.botbsf.models.ConsultaOperador;
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

	public static int NO_INPUT_TIMEOUT = 300000;
	
	private String lastState;
	private DadosFluxo dadosFluxo;
	private ConsultaCNPJ consulta;
	private ConsultaCPF trab;
	private ConsultaOperador op;
	
	 public interface STATES{
	    	String START = "start";
	    	
	    	String CNPJ = "cnpj";
	    	String SEGUECNPJ = "seguecnpj";
	    	String MENUEMPRESA = "menuempresa";
	    	String ERROCNPJ = "errocnpj";
	    	
	    	String FUNERAL = "funeral";
	    	String PLANTAO = "plantao";
	    	String LOCAL = "local";
	    	String CONTATO = "contato";
	    	String CPFUNERAL = "cpfuneral";
	    	
	    	String OPERADOR = "operador";
	    	String ATIVO = "ativo";
	    	String INATIVO = "inativo";
	    	String SCADASTRO = "scadastro";
	    	String NCADASTROSENHA = "ncadastrosenha";
	    	String ESQUECEU = "esqueceu";
	    	
	    	String ATUALIZAR = "atualizar";
	    	String ATUATIVO = "atuativo";
	    	String ATUINATIVO = "atuinativo";
	    	String ATUSCADASTRO = "atuscadastro";
	    	String ATUSCADASTROSIM = "atuscadastrosim";
	    	String ATUSCADASTRONAO = "atuscadastronao";
	    	
	    	String SOBRE = "sobre";
	    	
	    	String BOLETOS = "boletos";
	    	String SEMBOLETOS = "semboleto";
	    	String BOLETOIMPRESSO = "boletoimpresso";
	    	
	    	String INATIVACAO = "inativacao";
	    	String NSEGMENTO = "nsegmento";
	    	String NTRABALHADOR = "ntrabalhador";
	    	String NREGISTRO = "nregistro";
	    	
	    	String INADIMPLENCIA = "inadimplencia";
	    	String INADINATIVO = "inadinativo";
	    	String INADSCADASTRO = "inadscadastro";	    	
	    	String INADATIVO = "inadativo";
	    	String INADVENCER = "inadvencer";
	    	String SEMINAD = "seminad";
	    	String FINALIZARINAD = "finalizarinad";
	    	String INADPAGO = "inadpago";

	    	String SERASA = "sarasa";
	    	String SEMSERASA = "semsarasa";
	    	String NDCONSTA = "ndconsta";
	    	String REGULARIZADO = "regularizado";
	    	String COMSERASA = "comsarasa";
	    	String NEGATIVADA = "negativada";
	    	String ACORDO = "acordo";
	    	String INDEVIDA = "indevida";
	    	String COBSEGMENTO = "cobsegmento";
	    	String COBSEMTRAB = "cobsemtrab";
	    	
	    	String OUTROS = "outros";
	    	
	    	String CPF = "cpf";
	    	String SEGUECPF = "seguecpf";
	    	String ERROCPF = "errocpf";
	    	String MENUTRABALHADOR = "menutrabalhador";

	    	String CARTAO = "cartao";
	    	String ANDAMENTO = "andamento";
	    	String VALIDAR = "validar";
	    	String SALDO = "saldo";
	    	
	    	String CESTA = "cesta";
	    	String POSSUICESTA = "possuicesta";
	    	String NPOSSUICESTA = "npossuicesta";
	    	
	    	String FAQ = "faq";
	    	String ATENDENTE = "atendente";
	    	String FINALIZAR = "finalizar";
	    	String DESPEDE = "despede";
	    	String REDIRECT = "redirect";
	    	
	    	String TRANSFERIR = "transfer_to_attendant(1)";
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
	
	public void setConsultaCNPJ(ConsultaCNPJ consulta) {
		this.consulta = consulta;
	}
	
	public ConsultaCNPJ getConsultaCNPJ() {
		return this.consulta;
	}
	public void setConsultaCPF(ConsultaCPF trab) {
		this.trab = trab;
	}
	
	public ConsultaCPF getConsultaCPF() {
		return this.trab;
	}
	
	public void setConsultaOperador(ConsultaOperador op) {
		this.op = op;
	}
	
	public ConsultaOperador getConsultaOperador() {
		return this.op;
	}
}