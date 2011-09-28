package me.oyasunadev.chatwithme.Server;

public class CrossServerChatObject {
String message;
String player;
String servername;

public CrossServerChatObject(String message,String player,String servername){
	this.message = message;
	this.player = player;
	this.servername = servername;
}
}
