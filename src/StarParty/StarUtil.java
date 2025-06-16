package StarParty;

import StarParty.Role.Chewbaca;
import StarParty.Role.DarkMaul;
import StarParty.Role.DarkVador;
import StarParty.Role.Dooku;
import StarParty.Role.Grievous;
import StarParty.Role.HanSolo;
import StarParty.Role.Impe;
import StarParty.Role.Jango;
import StarParty.Role.Leila;
import StarParty.Role.Luke;
import StarParty.Role.Obiwan;
import StarParty.Role.Palpatine;
import StarParty.Role.QuiGon;
import StarParty.Role.Stormtrooper;
import StarParty.Role.Windu;
import StarParty.Role.Yoda;
import fr.fitzche.lgmore.PlayerData;

public class StarUtil {

	
	public static RoleStar createRole(RolesStar role, PlayerData p) {
		switch (role) {
		case Chewbaca:
			return new Chewbaca(p);
		case DarkMaul:
			return new DarkMaul(p);
		case DarkVador:
			return new DarkVador(p);
		case Dooku:
			return new Dooku(p);
		case Grievou:
			return new Grievous(p);
		case HanSolo:
			return new HanSolo(p);
		case Impe:
			return new Impe(p);
		case Jango:
			return new Jango(p);
		case Leila:
			return new Leila(p);
		case Luke:
			return new Luke(p);
		case ObiWan:
			return new Obiwan(p);
		case Palpa:
			return new Palpatine(p);
		case QuiGon:
			return new QuiGon(p);
		case Stormtrooper:
			return new Stormtrooper();
		case Windu:
			return new Windu(p);
		case Yoda:
			return new Yoda(p);
		default:
			break;
		
		}
		return null;
	} 
}
