package com.exopteron.exotestmod.gui;

import com.exopteron.exotestmod.items.ItemMagicWand;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;
import java.awt.Color;

@OnlyIn(value = Dist.CLIENT)
public class GuiMagicWand implements IIngameOverlay {

    @Override
    public void render(ForgeIngameGui gui, PoseStack mStack, float partialTicks, int width, int height) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        ItemStack heldItem = player.getInventory().getItem(player.getInventory().selected);
        if (heldItem.getItem() instanceof ItemMagicWand) {
            // 0, ((mc.getWindow().getHeight() / 4) * 2), 0, ((mc.getWindow().getHeight() /
            // 4) * 2) - 25
            Gui.fill(mStack, 0, ((mc.getWindow().getHeight() / 4) * 2), 100, ((mc.getWindow().getHeight() / 4) * 2) - 45,
                    new Color(0, 0, 0, 140).getRGB());
            Gui.drawString(mStack, mc.font, "Durability: " + (150 - heldItem.getDamageValue()), 5,
                    ((mc.getWindow().getHeight() / 4) * 2) - 40, 0xD3D3D3);
            CompoundTag tag = heldItem.getOrCreateTag();
            String spell = tag.getString("CurrentSpell");
            if (spell != null) {
                Gui.drawString(mStack, mc.font, "Spell: " + spell, 5, ((mc.getWindow().getHeight() / 4) * 2) - 25,
                        0xD3D3D3);
            }
            int crystalDamage = tag.getInt("CrystalDamage");
            Gui.drawString(mStack, mc.font, "Crystal Damage: " + crystalDamage, 5,
                    ((mc.getWindow().getHeight() / 4) * 2) - 15, 0xD3D3D3);
        }
    }

}
