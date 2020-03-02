package br.com.voxage.botbsf.models;

public class TransferType {
	private TransferProp web;
	private TransferProp telegram;
	private TransferProp whatsapp;
	private TransferProp messenger;
	
	public TransferProp getWeb() {
		return web;
	}
	public void setWeb(TransferProp web) {
		this.web = web;
	}
	public TransferProp getTelegram() {
		return telegram;
	}
	public void setTelegram(TransferProp telegram) {
		this.telegram = telegram;
	}
	public TransferProp getWhatsapp() {
		return whatsapp;
	}
	public void setWhatsapp(TransferProp whatsapp) {
		this.whatsapp = whatsapp;
	}
	public TransferProp getMessenger() {
		return messenger;
	}
	public void setMessenger(TransferProp messenger) {
		this.messenger = messenger;
	}	
}
