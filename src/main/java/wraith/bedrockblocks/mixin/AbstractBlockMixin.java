package wraith.bedrockblocks.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import wraith.bedrockblocks.BedrockBlocks;

@Mixin(AbstractBlock.class)
public class AbstractBlockMixin {

    @Inject(method = "calcBlockBreakingDelta", at = @At("HEAD"), cancellable = true)
    public void changeHardness(BlockState state, PlayerEntity player, BlockView world, BlockPos pos, CallbackInfoReturnable<Float> ci) {
        if (BedrockBlocks.DISABLED_BLOCKS.contains(state.getBlock()) && !BedrockBlocks.PLAYER_WHITELIST.contains(player.getName().asString())) {
            ci.setReturnValue(0f);
        }
    }

}
