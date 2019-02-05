package br.com.voxage.botbsf;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.models.ConsultaCNPJ;
import br.com.voxage.chat.botintegration.entities.VAServiceJsonParams;
import br.com.voxage.chat.botintegration.entities.VAServiceModel;
import br.com.voxage.chat.botintegration.entities.VAServiceResponseModel;
import br.com.voxage.chat.botintegration.entities.VoscenterMailingInfo;
import br.com.voxage.chat.botintegration.tracker.GenericSQLWorkerThread;
import static br.com.voxage.chat.botintegration.utils.AppLogger.log;
import br.com.voxage.chat.botintegration.utils.AsyncHttpUtils;
import br.com.voxage.chat.botintegration.utils.JsonUtils;
import com.google.gson.JsonParseException;

import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class BotBSFIntegration {
	private static final String BASE_URL = "http://localhost:3000/";
	
	public static ConsultaCNPJ ConsultaCNPJ(BotBSF bot,  String cpf) {
		return null;
		
	}
}
