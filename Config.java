package com.xmods.tools.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.xmods.tools.Reference;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Config {
	
	private static Configuration config;

	public static final String CATEGORY_NAME_BLOCKS = "blocks";
	public static final String CATEGORY_NAME_TOOLS = "tools";
	public static final String CATEGORY_NAME_ARMOR = "armor";
	
	public static int obsidianHarvestLevel;
	public static int obsidianToolUses;
	public static int obsidianToolEfficiency;
	public static int obsidianArmorDurability;
	public static int obsidianArmorToughness;
	
	public static void preInit() {
		File configFile = new File(Loader.instance().getConfigDir(), "xmodred.cfg");
		config = new Configuration(configFile);
		syncFromFiles();
	}
	
	public static Configuration getConfig() {
		return config;
	}
	
	public static void clientPreInit() {
		MinecraftForge.EVENT_BUS.register(new ConfigEventHandler());
	}
	
	public static void syncFromFiles() {
		syncConfig(true, true);
	}
	
	public static void syncFromGui() {
		syncConfig(true, true);
	}
	
	public static void syncFromFields() {
		syncConfig(false, false);
	}
	
	private static void syncConfig(boolean loadFromConfigFile, boolean readFieldsFromConfig) {
		if(loadFromConfigFile)
			config.load();
		
		Property propertyObsidianHarvestLevel = config.get(CATEGORY_NAME_BLOCKS, "obsidian_harvest_level", 4);
		propertyObsidianHarvestLevel.setLanguageKey("gui.config.blocks.obsidian_harvest_level.name");
		propertyObsidianHarvestLevel.setComment("gui.config.blocks.obsidian_harvest_level.comment");
		propertyObsidianHarvestLevel.setMinValue(3);
		propertyObsidianHarvestLevel.setMaxValue(4);
		
		Property propertyObsidianToolUses = config.get(CATEGORY_NAME_TOOLS, "obsidian_tool_uses", 1882);
		propertyObsidianToolUses.setLanguageKey("gui.config.tools.obsidian_tool_uses.name");
		propertyObsidianToolUses.setComment("gui.config.tools.obsidian_tool_uses.comment");
		propertyObsidianToolUses.setMinValue(0);
		propertyObsidianToolUses.setMaxValue(2048);
		
		Property propertyObsidianToolEfficiency = config.get(CATEGORY_NAME_TOOLS, "obsidian_tool_efficiency", 14);
		propertyObsidianToolEfficiency.setLanguageKey("gui.config.tools.obsidian_tool_efficiency.name");
		propertyObsidianToolEfficiency.setComment("gui.config.tools.obsidian_tool_efficiency.comment");
		propertyObsidianToolEfficiency.setMinValue(0);
		propertyObsidianToolEfficiency.setMaxValue(18);
		
		Property propertyObsidianArmorDurability = config.get(CATEGORY_NAME_ARMOR, "obsidian_armor_durability", 125);
		propertyObsidianArmorDurability.setLanguageKey("gui.config.tools.obsidian_armor_durability.name");
		propertyObsidianArmorDurability.setComment("gui.config.armor.obsidian_armor_durability.comment");
		propertyObsidianArmorDurability.setMinValue(0);
		propertyObsidianArmorDurability.setMaxValue(156);
		
		Property propertyObsidianArmorToughness = config.get(CATEGORY_NAME_ARMOR, "obsidian_armor_toughness", 2);
		propertyObsidianArmorToughness.setLanguageKey("gui.config.armor.obsidian_armor_toughness.name");
		propertyObsidianArmorToughness.setComment("gui.config.armor.obsidian_armor_toughness.comment");
		propertyObsidianArmorToughness.setMinValue(0);
		propertyObsidianArmorToughness.setMaxValue(3);
		
		List<String> propertyOrderBlocks = new ArrayList<String>();
		propertyOrderBlocks.add(propertyObsidianHarvestLevel.getName());
		config.setCategoryPropertyOrder(CATEGORY_NAME_BLOCKS, propertyOrderBlocks);
		
		List<String> propertyOrderTools = new ArrayList<String>();
		propertyOrderTools.add(propertyObsidianToolUses.getName());
		config.setCategoryPropertyOrder(CATEGORY_NAME_TOOLS, propertyOrderTools);
		propertyOrderTools.add(propertyObsidianToolEfficiency.getName());
		config.setCategoryPropertyOrder(CATEGORY_NAME_TOOLS, propertyOrderTools);
		
		List<String> propertyOrderArmor = new ArrayList<String>();
		propertyOrderArmor.add(propertyObsidianArmorDurability.getName());
		config.setCategoryPropertyOrder(CATEGORY_NAME_ARMOR, propertyOrderArmor);
		propertyOrderArmor.add(propertyObsidianArmorToughness.getName());
		config.setCategoryPropertyOrder(CATEGORY_NAME_ARMOR, propertyOrderArmor);
		
		if(readFieldsFromConfig) {
			obsidianHarvestLevel = propertyObsidianHarvestLevel.getInt();
			obsidianToolUses = propertyObsidianToolUses.getInt();
			obsidianToolEfficiency = propertyObsidianToolEfficiency.getInt();
			obsidianArmorDurability = propertyObsidianArmorDurability.getInt();
			obsidianArmorToughness = propertyObsidianArmorToughness.getInt();
		}
		
		propertyObsidianHarvestLevel.set(obsidianHarvestLevel);
		propertyObsidianToolUses.set(obsidianToolUses);
		propertyObsidianToolEfficiency.set(obsidianToolEfficiency);
		propertyObsidianArmorDurability.set(obsidianArmorDurability);
		propertyObsidianArmorToughness.set(obsidianArmorToughness);
		
		if(config.hasChanged())
			config.save();
	}
	
	public static class ConfigEventHandler {
		
		@SubscribeEvent(priority = EventPriority.LOWEST)
		public void onConfigChanged(OnConfigChangedEvent event) {
			if(event.getModID().equals(Reference.MODID)) {
				syncFromGui();
			}
		}
		
	}

}
