package fr.fitzche.lgmore.InfinityStones;

public enum StonesType {
	SPACE("Espace"), SOUL("Ame"), POWER("Pouvoir"), TIME("Temps"), REALITY("Réalité"), MIND("Esprit");
	
	
	private String name;
	private StonesType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
}
