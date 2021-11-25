package com.exopteron.exotestmod.items.wand;

import java.util.HashMap;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class MagicWandSpells {
    public static String rotate(String in) {
        String[] spells = new String[SpellRegistry.INSTANCE.spellRegistry.keySet().size()];
        SpellRegistry.INSTANCE.spellRegistry.keySet().toArray(spells);
        int i = 0;
        int finalInt = 0;
        for (String spell : spells) {
            if (spell == in) {
                finalInt = i;
            }
            i++;
        }
        finalInt += 1;
        finalInt %= spells.length;
        return spells[finalInt];
    } 
    public class SpellRegistry {
        public static SpellRegistry INSTANCE = new MagicWandSpells().new SpellRegistry();
        public HashMap<String, IWandSpell> spellRegistry;
        public HashMap<String, IWandSpell> clientSpellRegistry;
        public SpellRegistry() {
            this.spellRegistry = new HashMap<String, IWandSpell>();
            this.registerSpell("FIREBALL", new FireballSpell());
            this.registerSpell("ICEBALL", new IceballSpell());
            this.registerSpell("POISONCLOUD", new PoisonCloudSpell());
        }
        public void registerSpell(String spellName, IWandSpell spell) {
            this.spellRegistry.put(spellName, spell);
        }
        public static void setItemStack(ItemStack i, String spell) {
            CompoundTag tag = i.getOrCreateTag();
            tag.putString("CurrentSpell", spell);
        }
    }
    public static String spellFromItemStack(ItemStack in) {
        CompoundTag tag = in.getOrCreateTag();
        String s = tag.getString("CurrentSpell");
        if (s == null || s == "") {
            tag.putString("CurrentSpell", "FIREBALL");
        }
        s = tag.getString("CurrentSpell");
        return s;
    }
}
