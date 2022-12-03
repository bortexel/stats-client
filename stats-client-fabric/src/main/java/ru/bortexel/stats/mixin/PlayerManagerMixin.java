package ru.bortexel.stats.mixin;

import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.bortexel.stats.StatsClientFabric;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
    @Inject(method = "remove", at = @At("TAIL"))
    public void onDisconnect(ServerPlayerEntity player, CallbackInfo ci) {
        StatsClientFabric.getInstance().ifPresent((client) -> client.updatePlayer(player));
    }
}
