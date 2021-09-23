package com.example.examplemod;

import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.AchievementEvent;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentText;

@Mod(modid = TestMod.MODID, version = TestMod.VERSION)
public class TestMod {
	public static final String MODID = "testmod";
	public static final String VERSION = "1.1";
	public static String lastAchievement = "";

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		FMLCommonHandler.instance().bus().register(this);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void render(RenderGameOverlayEvent event) {
		if (event.isCancelable() || event.type != ElementType.EXPERIENCE) {
			return;
		}

		EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
		BlockPos pos = player.getPosition();
		ItemStack item = player.getHeldItem();
		String item_name = "";
		if (item != null)
			item_name = item.getDisplayName();
		FontRenderer font_renderer = Minecraft.getMinecraft().fontRendererObj;
		font_renderer.drawString(EnumChatFormatting.GREEN + "This is a test", 5, 5, 0);
		font_renderer.drawString(
			EnumChatFormatting.BLUE + "Player Pos: { " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + " }",
			5, 5 + font_renderer.FONT_HEIGHT, 0
		);
		font_renderer.drawString(
			EnumChatFormatting.RED + "Held Item: " + item_name,
			5, 5 + font_renderer.FONT_HEIGHT * 2, 0
		);
		font_renderer.drawString(
			EnumChatFormatting.YELLOW + "Last Achievement: " + this.lastAchievement,
			5, 5 + font_renderer.FONT_HEIGHT * 3, 0
		);
	}

	@SubscribeEvent
	public void onAchievement(AchievementEvent event)
	{
		this.lastAchievement = event.achievement.statId;
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("You earned an achievement"));
	}
}
