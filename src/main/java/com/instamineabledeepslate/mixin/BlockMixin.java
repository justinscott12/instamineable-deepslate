package com.instamineabledeepslate.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(PlayerEntity.class)
public class BlockMixin {

    @Inject(method = "getBlockBreakingSpeed", at = @At("HEAD"), cancellable = true)
    private void makeDeepslateInstamineable(BlockState state, CallbackInfoReturnable<Float> cir) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        // Check if the block is deepslate
        if (state.getBlock() == Blocks.DEEPSLATE) {
            ItemStack heldItem = player.getMainHandStack();

            // Check if player is holding a netherite pickaxe
            if (heldItem.getItem() == Items.NETHERITE_PICKAXE) {
                // Check if the pickaxe has Efficiency V
                ItemEnchantmentsComponent enchantments = heldItem.getEnchantments();
                RegistryEntryLookup<Enchantment> enchantmentLookup = player.getRegistryManager()
                        .getOrThrow(RegistryKeys.ENCHANTMENT);
                int efficiencyLevel = enchantments.getLevel(enchantmentLookup.getOrThrow(Enchantments.EFFICIENCY));
                if (efficiencyLevel >= 5) {
                    // Check if player has Haste II effect
                    if (player.hasStatusEffect(StatusEffects.HASTE) &&
                        Objects.requireNonNull(player.getStatusEffect(StatusEffects.HASTE)).getAmplifier() >= 1) {
                        // Return a speed that instamines deepslate
                        cir.setReturnValue(100.0f);
                    }
                }
            }
        }
    }
}
