package com.exopteron.exotestmod.items.wand;

import com.exopteron.exotestmod.network.PacketHandler;
import com.exopteron.exotestmod.network.PacketLightningSpell;
import com.exopteron.exotestmod.network.PacketChangeSpell;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public class LightningSpell implements IWandSpell {

    @Override
    public void castClient(Level level, Player player, InteractionHand hand) {
/*         Minecraft mc = Minecraft.getInstance();
        HitResult result = mc.hitResult;
        if (result.getType() == HitResult.Type.BLOCK) {
            BlockPos blockPos = new BlockPos(result.getLocation());
            //System.out.println("Got pos: " + blockPos.getX() + ", " + blockPos.getY() + ", " + blockPos.getZ());
            PacketHandler.INSTANCE.sendToServer(new PacketLightningSpell(blockPos));
        } */
    }
    @Override
    public void rebound(Level level, Player player, InteractionHand hand) {

    }
}
