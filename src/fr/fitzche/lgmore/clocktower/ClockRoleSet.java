package fr.fitzche.lgmore.clocktower;

import java.util.ArrayList;
import java.util.Arrays;

import fr.fitzche.lgmore.Util.MathUtil;

public class ClockRoleSet {

	public static ArrayList<ClockRole> getRoles(int evil, int sbire, int etranger, int citadin) {
		ArrayList<ClockRole> returned = new ArrayList<ClockRole>();
		ArrayList<ClockRole> sbires = new ArrayList<ClockRole>(/*Arrays.asList(ClockRole.Espion, ClockRole.Poisonner, ClockRole.Ecarlate)*/);
		
		ArrayList<ClockRole> etrangers = new ArrayList<ClockRole>(/*Arrays.asList(ClockRole.Saint, ClockRole.Reclus)*/);
		
		ArrayList<ClockRole> citadins = new ArrayList<ClockRole>(/*Arrays.asList(ClockRole.Croque, ClockRole.Cuistot, ClockRole.Empathe, ClockRole.Lavandière, ClockRole.Moine, ClockRole.Pourfendeur, ClockRole.Vierge, ClockRole.Voyante)*/);
		
		returned.add(ClockRole.Evil);
		for (int i = 0; i<sbire;i++) {
			int x = MathUtil.generateAlInt(0, sbires.size()-1);
			returned.add(sbires.get(x));
			sbires.remove(x);
		}
		for (int i = 0; i<etranger;i++) {
			int x = MathUtil.generateAlInt(0, etrangers.size()- 1);
			returned.add(etrangers.get(x));
			etrangers.remove(x);
		}
		for (int i = 0; i<citadin;i++) {
			int x = MathUtil.generateAlInt(0, citadins.size() - 1);
			returned.add(citadins.get(x));
			citadins.remove(x);
		}
		
		
		
		return returned;
	}
}
