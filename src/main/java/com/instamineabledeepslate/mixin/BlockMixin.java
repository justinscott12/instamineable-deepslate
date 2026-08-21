package com.instamineabledeepslate.mixin;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(Player.class)
public class BlockMixin {

    @Inject(method = "getDestroySpeed", at = @At("HEAD"), cancellable = true)
    private void makeDeepslateInstamineable(BlockState state, CallbackInfoReturnable<Float> cir) {
        Player player = (Player) (Object) this;

        // Check if the block is deepslate
        if (state.getBlock() == Blocks.DEEPSLATE) {
            ItemStack heldItem = player.getMainHandItem();

            // Check if player is holding a netherite pickaxe
            if (heldItem.getItem() == Items.NETHERITE_PICKAXE) {
                // Check if the pickaxe has Efficiency V
                ItemEnchantments enchantments = heldItem.getEnchantments();
                Holder<Enchantment> efficiency = player.registryAccess()
                        .lookupOrThrow(Registries.ENCHANTMENT)
                        .getOrThrow(Enchantments.EFFICIENCY);
                int efficiencyLevel = enchantments.getLevel(efficiency);
                if (efficiencyLevel >= 5) {
                    // Check if player has Haste II effect
                    if (player.hasEffect(MobEffects.HASTE) &&
                        Objects.requireNonNull(player.getEffect(MobEffects.HASTE)).getAmplifier() >= 1) {
                        // Return a speed that instamines deepslate
                        cir.setReturnValue(100.0f);
                    }
                }
            }
        }
    }
}
