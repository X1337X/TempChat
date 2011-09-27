package me.oyasunadev.chatwithme.Server;

public class CrossServerObject {
	String name = null;
	String ip = null;
	String port = null;
public CrossServerObject(String name){
	this.name = name;
	
}
public void addIP(String ip){
	this.ip = ip;
}
public void addPort(String port){
	this.port = port;
}
}
