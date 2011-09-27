package me.oyasunadev.chatwithme.Server;

import me.oyasunadev.chatwithme.ChatWithMe;

import org.bukkit.util.config.Configuration;


public class CrossServerChat {
Configuration f = null;
	public void load(Configuration config,ChatWithMe plugin){
		this.f = config;
		String names = config.getString("Servernames","");
		String[] namessplit = names.split(",");
		for(String i : namessplit){
			CrossServerObject ob = new CrossServerObject(i);
			ob.addIP(getIP(i));
			ob.addPort(getPort(i));
		}
	}
	
	public String getIP(String name){
		String all = f.getString(name);
		String[] ips = all.split(":");
		String ip = ips[0];
		return ip;
	}
	public String getPort(String name){
		String all = f.getString(name);
		String[] ports = all.split(":");
		String port = ports[1];
		return port;
	}
}
