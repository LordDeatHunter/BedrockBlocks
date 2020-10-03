package wraith.bedrockblocks;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.block.Block;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

import java.util.HashSet;

public class BedrockBlocks implements ModInitializer {

    public static HashSet<Block> DISABLED_BLOCKS;
    public static HashSet<String> PLAYER_WHITELIST;

    @Override
    public void onInitialize() {
        DISABLED_BLOCKS = Config.loadDisabledBlocks();
        PLAYER_WHITELIST = Config.loadPlayerWhitelist();

        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            dispatcher.register(CommandManager.literal("reloadbedrockblocks")
                    .requires(source -> source.hasPermissionLevel(1))
                    .executes(context -> {
                        DISABLED_BLOCKS = Config.loadDisabledBlocks();
                        PLAYER_WHITELIST = Config.loadPlayerWhitelist();
                        ServerPlayerEntity player = context.getSource().getPlayer();
                        if (player != null) {
                            player.sendMessage(new LiteralText("§6[§eBedrockBlocks§6] §3has successfully been reloaded"), false);
                        }
                        
                        return 1;
                    })
                );
            }
        );
    }

}
