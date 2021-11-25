package com.exopteron.exotestmod.recipe;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Consumer;

import com.exopteron.exotestmod.TestMod;
import com.exopteron.exotestmod.items.ItemEnergyCrystal;
import com.exopteron.exotestmod.items.ItemMagicWand;
import com.exopteron.exotestmod.items.ItemSetup;
import com.google.gson.JsonObject;

import net.minecraft.core.NonNullList;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class RecipeWand extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<ShapedRecipe> {

    private static final ResourceLocation NAME = new ResourceLocation("exotestmod", "crafting_shaped");

    public ShapedRecipe fromJson(ResourceLocation p_44236_, JsonObject jsonObject) {
        String type = jsonObject.get("recipe_type").getAsString();
        switch (type) {
            case "1": {
                NonNullList<Ingredient> recipeItems = NonNullList.createWithCapacity(1);
                ItemStack energyCrystal = new ItemStack(ItemSetup.ENERGY_CRYSTAL.get());
                ItemEnergyCrystal.setCrystalDamage(energyCrystal, 0);
                recipeItems.add(Ingredient.of(energyCrystal));
                recipeItems.add(Ingredient.of(TestMod.NETHERITE_STICK.get().getDefaultInstance()));
                ItemStack wand = new ItemStack(ItemSetup.MAGIC_WAND.get());
                ItemEnergyCrystal.setCrystalDamage(wand, 0);
                return new ShapedWandRecipe(p_44236_, "wand1", 1, 2, recipeItems, wand);
            }
/*             case "2": {
                NonNullList<Ingredient> recipeItems = NonNullList.createWithCapacity(1);
                ItemStack energyCrystal = new ItemStack(ItemSetup.ENERGY_CRYSTAL.get());
                ItemEnergyCrystal.setCrystalDamage(energyCrystal, 1);
                recipeItems.add(Ingredient.of(energyCrystal));
                recipeItems.add(Ingredient.of(TestMod.NETHERITE_STICK.get().getDefaultInstance()));
                ItemStack wand = new ItemStack(ItemSetup.MAGIC_WAND.get());
                ItemEnergyCrystal.setCrystalDamage(wand, 1);
                return new ShapedWandRecipe(p_44236_, "wand2", 1, 2, recipeItems, wand);
            } */
        }
        return null;
    }

    public ShapedRecipe fromNetwork(ResourceLocation p_44239_, FriendlyByteBuf p_44240_) {
        int i = p_44240_.readVarInt();
        int j = p_44240_.readVarInt();
        String s = p_44240_.readUtf();
        NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i * j, Ingredient.EMPTY);

        for (int k = 0; k < nonnulllist.size(); ++k) {
            nonnulllist.set(k, Ingredient.fromNetwork(p_44240_));
        }

        ItemStack itemstack = p_44240_.readItem();
        return new ShapedRecipe(p_44239_, s, i, j, nonnulllist, itemstack);
    }

    public void toNetwork(FriendlyByteBuf p_44227_, ShapedRecipe p_44228_) {
        p_44227_.writeVarInt(p_44228_.getWidth());
        p_44227_.writeVarInt(p_44228_.getHeight());
        p_44227_.writeUtf(p_44228_.getGroup());

        for (Ingredient ingredient : p_44228_.getIngredients()) {
            ingredient.toNetwork(p_44227_);
        }

        p_44227_.writeItem(p_44228_.getResultItem());
    }

}
