package fr.fitzche.lgmore.Util;

import fr.fitzche.lgmore.Lg.RegisterType;

public class Registre {

	RegisterType type;
	int taux; 
	
	public Registre(int taux, RegisterType type) {
		this.taux = taux;
		this.type = type;
	}
	
	public RegisterType getType() {
		return this.type;
	}
	public int getTaux() {
		return this.taux;
	}
	
}
