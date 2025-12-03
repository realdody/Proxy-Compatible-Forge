package org.adde0109.pcf.mixin.v1_19_2.forge.forwarding.legacy;

import com.mojang.authlib.GameProfile;

import dev.neuralnexus.taterapi.meta.Mappings;
import dev.neuralnexus.taterapi.meta.enums.MinecraftVersion;
import dev.neuralnexus.taterapi.muxins.annotations.ReqMCVersion;
import dev.neuralnexus.taterapi.muxins.annotations.ReqMappings;

import net.minecraft.network.Connection;
import net.minecraft.server.network.ServerLoginPacketListenerImpl;

import org.adde0109.pcf.PCF;
import org.adde0109.pcf.forwarding.Mode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@ReqMappings(Mappings.SEARGE)
@ReqMCVersion(MinecraftVersion.V19)
@Mixin(ServerLoginPacketListenerImpl.class)
public class ServerLoginPacketListenerImplMixin {

    @Shadow @Final public Connection connection;

    @ModifyVariable(method = "startClientVerification", at = @At("HEAD"), argsOnly = true)
    private GameProfile pcf$modifyGameProfile(GameProfile profile) {
        if (PCF.instance().forwarding().mode() != Mode.LEGACY) return profile;

        if (connection instanceof ConnectionExtension) {
            java.util.UUID spoofed = ((ConnectionExtension) connection).pcf$getSpoofedUUID();
            if (spoofed != null && !spoofed.equals(profile.getId())) {
                return new GameProfile(spoofed, profile.getName());
            }
        }
        return profile;
    }
}
