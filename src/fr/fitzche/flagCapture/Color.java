package fr.fitzche.flagCapture;

public enum Color {

	Blue("Bleu"), Red("Rouge") , Yellow("Jaune"), Green("Vert") ,White("Blanc"), Black("Noir") ;
	
	private String name;
	
	Color(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
}
