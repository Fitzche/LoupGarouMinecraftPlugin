package fr.fitzche.lgmore.Util;

import java.util.ArrayList;

public class MyStringUtil {
	public static ArrayList<String> sortByCorres(ArrayList<String> liste, String corres){
		ArrayList<String> withOne = new ArrayList<String>();
		ArrayList<String> withTwo = new ArrayList<String>();
		ArrayList<String> withThree = new ArrayList<String>();
		ArrayList<String> withFour = new ArrayList<String>();
		ArrayList<String> withOut = new ArrayList<String>();
		for (String str: liste) {
			if (str.charAt(0)==(corres.charAt(0))) {
				if (str.charAt(1)==corres.charAt(1)) {
					if (str.charAt(2)==corres.charAt(2)) {
						if (str.charAt(3)==corres.charAt(3) ) {
							withFour.add(str);
						}
						else {
							withThree.add(str);
						}
					} else {
						withTwo.add(str);
					}
				}else {
					withOne.add(str);
				}
			} else {
				withOut.add(str);
			}
		}
		ArrayList<String> returned = new ArrayList<String>();
		returned.addAll(withFour);
		returned.addAll(withThree);
		returned.addAll(withTwo);
		returned.addAll(withOne);
		returned.addAll(withOut);
		
		return returned;
	}
}
