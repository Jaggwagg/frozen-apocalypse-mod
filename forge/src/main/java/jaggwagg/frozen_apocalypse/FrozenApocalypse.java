package jaggwagg.frozen_apocalypse;

import com.mojang.logging.LogUtils;
import jaggwagg.frozen_apocalypse.config.FrozenApocalypseConfig;
import jaggwagg.frozen_apocalypse.entity.effect.FrozenApocalypseStatusEffects;
import jaggwagg.frozen_apocalypse.item.FrozenApocalypseItems;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import org.slf4j.Logger;

import java.lang.reflect.Method;
import java.util.Objects;

@Mod("frozen_apocalypse")
public class FrozenApocalypse {
    public static final String MOD_ID = "frozen_apocalypse";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final FrozenApocalypseConfig CONFIG = FrozenApocalypseConfig.getConfig();

    public static int timeOfDay = 0;

    @SubscribeEvent
    public void buildContents(CreativeModeTabEvent.Register event) {
        /*event.registerCreativeModeTab(new ResourceLocation(MOD_ID, "general"), builder ->
                builder.title(Component.translatable("itemGroup." + MOD_ID + ".general"))
                        .icon(() -> new ItemStack(FrozenApocalypseItems.Armor.THERMAL_CHESTPLATE.item))
                        .displayItems((enabledFlags, populator, hasPermissions) -> {
                            populator.accept(FrozenApocalypseItems.ITEMS.get);
                            populator.accept(BLOCK.get());
                        })
        );*/
    }

    public FrozenApocalypse()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        FrozenApocalypseItems.init(modEventBus);
        FrozenApocalypseStatusEffects.init(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

        //modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("Successfully initialized!");
    }
}
