package com.instamineabledeepslate;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InstamineableDeepslateMod implements ModInitializer {
    public static final String MOD_ID = "instamineable-deepslate";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Instamineable Deepslate mod initialized! Deepslate can now be instamined with netherite pickaxe + Eff V + Haste II.");
    }
}
