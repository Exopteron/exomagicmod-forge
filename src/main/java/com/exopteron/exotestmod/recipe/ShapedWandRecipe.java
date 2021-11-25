package com.exopteron.exotestmod.recipe;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.annotation.Nullable;

import com.exopteron.exotestmod.items.ItemEnergyCrystal;
import com.exopteron.exotestmod.items.ItemMagicWand;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import oshi.util.tuples.Pair;

public class ShapedWandRecipe extends ShapedRecipe {

    public ShapedWandRecipe(ResourceLocation p_44153_, String p_44154_, int p_44155_, int p_44156_,
            NonNullList<Ingredient> p_44157_, ItemStack p_44158_) {
        super(p_44153_, p_44154_, p_44155_, p_44156_, p_44157_, p_44158_);
    }

    @Override
    public boolean matches(CraftingContainer p_44176_, Level p_44177_) {
        for (int i = 0; i <= p_44176_.getWidth() - this.getWidth(); ++i) {
            for (int j = 0; j <= p_44176_.getHeight() - this.getHeight(); ++j) {
                if (this.matches(p_44176_, i, j, true)) {
                    return true;
                }

                if (this.matches(p_44176_, i, j, false)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean matches(CraftingContainer p_44171_, int p_44172_, int p_44173_, boolean p_44174_) {
        for (int i = 0; i < p_44171_.getWidth(); ++i) {
            for (int j = 0; j < p_44171_.getHeight(); ++j) {
                int k = i - p_44172_;
                int l = j - p_44173_;
                Ingredient ingredient = Ingredient.EMPTY;
                if (k >= 0 && l >= 0 && k < this.getWidth() && l < this.getHeight()) {
                    if (p_44174_) {
                        ingredient = this.getIngredients().get(this.getWidth() - k - 1 + l * this.getWidth());
                    } else {
                        ingredient = this.getIngredients().get(k + l * this.getWidth());
                    }
                }
                Pair<Boolean, Integer> pair = test(ingredient, p_44171_.getItem(i + j * p_44171_.getWidth()));
                if (!pair.getA()) {
                    return false;
                }
            }
        }

        return true;
    }

    public Pair<Boolean, Integer> test(Ingredient ing, @Nullable ItemStack p_43914_) {
        if (p_43914_ == null) {
            return new Pair<Boolean, Integer>(false, 0);
        } else {
            try {
                Method m = ing.getClass().getDeclaredMethod("dissolve");
                m.setAccessible(true);
                m.invoke(ing);
                Field f3 = ing.getClass().getDeclaredField("itemStacks");
                f3.setAccessible(true);
                ItemStack[] itemStacks = (ItemStack[]) f3.get(ing);
                if ((int) itemStacks.length == 0) {
                    return new Pair<Boolean, Integer>(p_43914_.isEmpty(), 0);
                } else {
                    int crystalDamage = 0;
                    for (ItemStack itemstack : (ItemStack[]) itemStacks) {
                        if (itemstack.is(p_43914_.getItem())) {
                            if (itemstack.getItem() instanceof ItemEnergyCrystal) {
                                crystalDamage = ItemMagicWand.getCrystalDamage(p_43914_);
                                if (this.getResultItem().getItem() instanceof ItemMagicWand) {
                                    try {
                                        Field f4 = ShapedRecipe.class.getDeclaredField("result");
                                        f4.setAccessible(true);
                                        ItemStack resultStack = this.getResultItem();
                                        //System.out.println("B: " + pair.getB());
                                        ItemEnergyCrystal.setCrystalDamage(resultStack, crystalDamage);
                                        f4.set(this, resultStack);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            return new Pair<Boolean, Integer>(true, crystalDamage);
                        }
                    }

                    return new Pair<Boolean, Integer>(false, 0);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new Pair<Boolean, Integer>(false, 0);
            }
        }
    }
}
