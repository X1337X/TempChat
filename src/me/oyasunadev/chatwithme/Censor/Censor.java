package me.oyasunadev.chatwithme.Censor;

import org.bukkit.util.config.Configuration;

public class Censor {
static String[] censor = null;


public void loadWords(Configuration c){
	String s = c.getString("censor", "");
	censor = s.split(",");
}
public static String censor(String message){
	for(String s : censor){
	if(message.contains(s)){
		int lenght = s.length();
		String hash = "";
		for(int i= 0 ; i != lenght ; i++){
			hash = hash + "*";
		}
		message.replaceAll(s,hash);
	}
	}
	return message;
	
	
}
}
