package jaggwagg.frozen_apocalypse.registry;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import jaggwagg.frozen_apocalypse.world.ModBooleanGameRules;
import jaggwagg.frozen_apocalypse.world.ModIntegerGameRules;
import net.fabricmc.fabric.api.gamerule.v1.CustomGameRuleCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.Arrays;

public class RegisterGameRules {
    public static final CustomGameRuleCategory FROZEN_APOCALYPSE_GAMERULE_CATEGORY = new CustomGameRuleCategory(new Identifier(FrozenApocalypse.MOD_ID, FrozenApocalypse.MOD_ID), Text.translatable("gamerule.category." + FrozenApocalypse.MOD_ID).formatted(Formatting.AQUA, Formatting.BOLD));

    public static void init() {
        Arrays.stream(ModBooleanGameRules.values()).forEach(Enum::describeConstable);
        Arrays.stream(ModIntegerGameRules.values()).forEach(Enum::describeConstable);
    }
}
