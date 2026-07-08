package fr.fitzche.lgmore.bedwars;

import org.bukkit.metadata.FixedMetadataValue;

import fr.fitzche.lgmore.Main;

public enum BedLocType {
	Spawn, CartPosition, MultiGenerator, DiamondGenerator, EmeraldGenerator, DiamondTrader, BaseTrader, UpgradingTrader;

	public final static FixedMetadataValue DTraderMeta = new FixedMetadataValue(Main.plug, "DTraderMeta");
	public final static FixedMetadataValue ETraderMeta = new FixedMetadataValue(Main.plug, "ETraderMeta");
	public final static FixedMetadataValue BTraderMeta = new FixedMetadataValue(Main.plug, "BTraderMeta");
	public final static FixedMetadataValue UTraderMeta = new FixedMetadataValue(Main.plug, "UTraderMeta");
}
