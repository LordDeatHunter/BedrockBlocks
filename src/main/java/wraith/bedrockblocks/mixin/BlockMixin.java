package wraith.bedrockblocks.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wraith.bedrockblocks.BedrockBlocks;

@Mixin(Block.class)
public class BlockMixin {

    @Inject(at = @At("HEAD"), method = "onBreak", cancellable = true)
    public void cancelBreak(World world, BlockPos pos, BlockState state, PlayerEntity player, CallbackInfo ci) {
        if (BedrockBlocks.DISABLED_BLOCKS.contains(state.getBlock()) && !BedrockBlocks.PLAYER_WHITELIST.contains(player.getName().asString())) {
            ci.cancel();
        }
    }

}
