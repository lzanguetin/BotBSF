package br.com.voxage.botbsf;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import br.com.voxage.botbsf.models.DadosBeneficiario;
import br.com.voxage.botbsf.models.DadosEmpresa;
import br.com.voxage.botbsf.models.DadosFluxo;
import br.com.voxage.botbsf.models.DadosOperador;
import br.com.voxage.botbsf.models.Ocorrencia;
import br.com.voxage.botbsf.models.Ocorrencias;
import br.com.voxage.botbsf.models.Token;
import br.com.voxage.botbsf.models.TransferType;
import br.com.voxage.chat.botintegration.BotInfo;
import br.com.voxage.chat.botintegration.ISearchEngine;
import br.com.voxage.chat.botintegration.ISearchEngineCredentials;
import br.com.voxage.chat.botintegration.TextSearchEngine;
import br.com.voxage.chat.botintegration.annotation.Bot;
import br.com.voxage.chat.botintegration.entities.AttendantClientInfo;
import br.com.voxage.chat.botintegration.entities.GenericFieldUpdate;
import br.com.voxage.chat.botintegration.message.Message;
import br.com.voxage.chat.botintegration.message.SimpleMessage;
import br.com.voxage.chat.botintegration.tracker.NavigationTracker;
import br.com.voxage.vbot.BotContext;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;
import br.com.voxage.vbot.VBot;
import br.com.voxage.vbot.utils.ClassFinder;

@SuppressWarnings("serial")
@Bot(name="BotBSF")
public class BotBSF extends VBot {

	public static int NO_INPUT_TIMEOUT = 300000;
	
	private String lastState;
	private DadosFluxo dadosFluxo;
	private DadosEmpresa dadosEmpresa;
	private Token token;
	private DadosOperador dadosOperador;
	private DadosBeneficiario dadosBeneficiario;
	private Integer erro;
	private Integer posBeneficio;
	private Integer firstTime;
	private TransferType type;
	private String option;
	private String group;
	private Ocorrencias ocorrencias; 
	private Integer flagError;
	private String ocorrencia;
	private String menuType;
	private AttendantClientInfo aInfo;
	private List<AttendantClientInfo> cInfo;

	public interface STATES{
	    	String START = "start";
	    	
	    	String CNPJ = "cnpj";
	    	String CNPJ_VALIDO = "cnpj_valido";
	    	String CNPJ_INVALIDO = "cnpj_invalido";
	    	String MENUEMPRESA = "menuempresa";
	    	
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
	    	
	    	String EMAIL = "email";
	    	String EMAILDISPONIVEL = "emaildisponivel";
	    	String EMAILNPAGO = "emailnpago";
	    		    	
	    	String INADIMPLENCIA = "inadimplencia";
	    	String INADINATIVO = "inadinativo";
	    	String INADSCADASTRO = "inadscadastro";	    	
	    	String INADATIVO = "inadativo";
	    	String INADVENCER = "inadvencer";
	    	String SEMINAD = "seminad";
	    	String FINALIZARINAD = "finalizarinad";
	    	String INADPAGO = "inadpago";
	    	String INADEBITO = "inadebito";
	    	String NAUTRIZADO = "nautorizado";

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
	    	String PRESERASA = "preserasa";
	    	
	    	String OUTROS = "outros";
	    	
	    	String CPF = "cpf";
	    	String SEGUECPF = "seguecpf";
	    	String ERROCPF = "errocpf";
	    	String FORAEXPEDIENTE = "foraexpediente";
	    	String MENUTRABALHADOR = "menutrabalhador";
	    	String MENUBENEFICIARIO = "menubeneficiario";

	    	String CARTAO = "cartao";
	    	
	    	String ANDAMENTO = "andamento";
	    	String PROTOCOLO = "protocolo";
	    	String NPROTOCOLO = "nprotocolo";
	    	
	    	String VALIDAR = "validar";
	    	String SALDO = "saldo";
	    	
	    	String CESTA = "cesta";
	    	String POSSUICESTA = "possuicesta";
	    	String NPOSSUICESTA = "npossuicesta";
	    	String NPROTOCOLOCESTA = "nprotocolocesta";
	    	
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

	@Override
	public BotInfo getBotInfo() {
	   return BotInfo.create("Benefício Social Familiar", "bsf-logo.png");
	}

	public boolean recordDialog() {
		return true;
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

		m.put("MAX_NO_INPUT", new BotState("/") {
			{
				setId("MAX_NO_INPUT");

				setPosFunction((botState, inputResult) -> {
					Date data = new Date();
					SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd'T'HH:mm:ss");
					@SuppressWarnings("unused")
					Integer ret = buildResult(dadosFluxo.getCPF(), dadosFluxo.getCNPJ(), "Bot", "Silêncio Máximo", 
							String.format("Silêncio Máximo - %s%s", getMenuType(), getOcorrencia())
							, date.format(data).toString(), getUserSession().get("call_id").toString(), BotBSF.this);
					return (new BotStateFlow(BotStateFlow.Flow.CONTINUE, "/TERMINATE"));

				});
				
				setNextNavigationMap(new HashMap<String, String>() {
					{
						put("", "/TERMINATE");
					}
				});
			}
		});

		m.put("MAX_INPUT_ERROR", new BotState("/") {
			{

				setId("MAX_INPUT_ERROR");
				setPosFunction((botState, inputResult) -> {
					Date data = new Date();
					SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd'T'HH:mm:ss");
					@SuppressWarnings("unused")
					Integer ret = buildResult(dadosFluxo.getCPF(), dadosFluxo.getCNPJ(), "Bot", "Erro Máximo", 
							String.format("Erro Máximo - %s%s", getMenuType(), getOcorrencia())
							, date.format(data).toString(), getUserSession().get("call_id").toString(), BotBSF.this);
					return (new BotStateFlow(BotStateFlow.Flow.CONTINUE, "#ATENDENTE"));

				});
				
				setNextNavigationMap(new HashMap<String, String>() {
					{
						put("", "#ATENDENTE");
					}
				});
			}
		});
		
		m.put("FORA_HORARIO", new BotState("/") {
			{

				setId("FORA_HORARIO");
				setPosFunction((botState, inputResult) -> {
					Message<?> message = null;
					message = SimpleMessage.text("No momento não temos atendentes disponíveis\n"
							+ "Por favor entre em contato novamente durante nosso horário de atendimento!");
					addResponse(message);
					return (new BotStateFlow(BotStateFlow.Flow.CONTINUE, "/TERMINATE"));

				});
				
				setNextNavigationMap(new HashMap<String, String>() {
					{
						put("", "/TERMINATE");
					}
				});
			}
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
	
    public void writeGenericField(String value) {
        GenericFieldUpdate genericField = new GenericFieldUpdate();
        
        genericField.setCallId(Long.parseLong(getUserSession().get("call_id").toString()));
        genericField.setIdForm(4);
        genericField.setIdCallResult(1266);
        genericField.setFieldId(35);
        genericField.setValue(value);
        
        this.insertGenericData(genericField);
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
	
	public TransferType getType() {
		return type;
	}

	public void setType(TransferType type) {
		this.type = type;
	}

	public void transferType() {
		Gson gson = new Gson();
		TransferType type = gson.fromJson(jsonCustomParams, TransferType.class);

		setType(type);
	}
	
	public void insertTransition(Integer idNextVavigation) {
		NavigationTracker  navigationTracker = (NavigationTracker) userSession.get("navigationTracker");
		navigationTracker.trackNavigationDetail(idNextVavigation);
	}
	
	@SuppressWarnings("unused")
	public Integer writeResult(BotBSF bot, Ocorrencia oc) {
		Ocorrencias customerInfo;
		try {
            customerInfo = BotBSFIntegration.registrarOcorrencia(bot, oc);
            return 0;
        }catch(Exception e) {
    	   return 1;
        }
	}
	
	public Integer buildResult(String cpf, String cnpj, String canal, String menu, String desc, String data, String id, BotBSF bot) {
		Ocorrencia oc = new Ocorrencia();
		
		oc.setCpf(cpf);
		oc.setCnpj(cnpj);
		oc.setCanal(canal);
		oc.setMenu(menu);
		oc.setDescricao(desc);
		oc.setDataHora(data);
		oc.setCallId(Integer.parseInt(id));
		
		Integer i = writeResult(bot, oc);
		
		return i;
	}
	
	 public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	public DadosEmpresa getDadosEmpresa() {
		return dadosEmpresa;
	}

	public void setDadosEmpresa(DadosEmpresa dadosEmpresa) {
		this.dadosEmpresa = dadosEmpresa;
	}
	
	public DadosOperador getDadosOperador() {
		return dadosOperador;
	}

	public void setDadosOperador(DadosOperador dadosOperador) {
		this.dadosOperador = dadosOperador;
	}

	public DadosBeneficiario getDadosBeneficiario() {
		return dadosBeneficiario;
	}

	public void setDadosBeneficiario(DadosBeneficiario dadosBeneficiario) {
		this.dadosBeneficiario = dadosBeneficiario;
	}

	public Integer getErro() {
		return erro;
	}

	public void setErro(Integer erro) {
		this.erro = erro;
	}

	public Integer getPosBeneficio() {
		return posBeneficio;
	}

	public void setPosBeneficio(Integer posBeneficio) {
		this.posBeneficio = posBeneficio;
	}

	public Integer getFirstTime() {
		return firstTime;
	}

	public void setFirstTime(Integer firstTime) {
		this.firstTime = firstTime;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public Ocorrencias getOcorrencias() {
		return ocorrencias;
	}

	public void setOcorrencias(Ocorrencias ocorrencias) {
		this.ocorrencias = ocorrencias;
	}

	public Integer getFlagError() {
		return flagError;
	}

	public void setFlagError(Integer flagError) {
		this.flagError = flagError;
	}

	public String getOcorrencia() {
		return ocorrencia;
	}

	public void setOcorrencia(String ocorrencia) {
		this.ocorrencia = ocorrencia;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public AttendantClientInfo getaInfo() {
		return aInfo;
	}

	public void setaInfo(AttendantClientInfo aInfo) {
		this.aInfo = aInfo;
	}

	public List<AttendantClientInfo> getcInfo() {
		return cInfo;
	}

	public void setcInfo(List<AttendantClientInfo> cInfo) {
		this.cInfo = cInfo;
	}
}