package fr.fitzche.lgmore;

import org.bukkit.Bukkit;

public class Timer {
	public int temps = 0;
	public Timer() {
		Bukkit.getWorld("world").setFullTime(0);
	}
	public int getEpisode() {
		int ep = 0;
		for (long i = temps;i >= 1200 ; i =i- 1200) {
			ep++;
		//	System.out.println("+1");
		}
		
		return ep+1;
	}
	
	public void addOne() {
		temps ++;
	}
	public String getStringTime() {
		int hours = 0;
		int min= 0;
		int sec = 0;
		long ex = this.temps;
		for (long i = ex; i >= 1; i= i - 1) {
			sec++;
		}
		for (long i = ex; i >= 60; i=i-60) {
			min++;
		}
		for (long i = ex; i >= 3600; i=i-3600) {
			hours++;
		}
		return ("temps: " + String.valueOf(hours) + "heures " + String.valueOf(min - 60*hours) + " minutes " + String.valueOf((sec-60*min) + " secondes"));
	}
}
