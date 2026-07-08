package fr.fitzche.lgmore.clocktower;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.common.collect.Maps;

public class ClockListGenerator {

	
	

	public static ArrayList<ClockRole> getList(int i) {
		switch (i) {
		case 7:
			return ClockRoleSet.getRoles(1, 0, 0, 6);
			
		case 8:
			return ClockRoleSet.getRoles(1, 1, 0, 6);
		case 9:
			return ClockRoleSet.getRoles(1, 1, 0, 7);
		case 10:
			return ClockRoleSet.getRoles(1, 1, 1, 7);
		case 11:
			return ClockRoleSet.getRoles(1, 2, 1, 7);
		case 12:
			return ClockRoleSet.getRoles(1, 2, 1, 8);
		case 13:
			return ClockRoleSet.getRoles(1, 2, 2, 8);
		case 14:
			return ClockRoleSet.getRoles(1, 2, 2, 9);
		}
		return new ArrayList<ClockRole>();
	}

}
