package org.adde0109.pcf.mixin.v1_19_2.forge.forwarding.legacy;

import com.mojang.util.UUIDTypeAdapter;

import dev.neuralnexus.taterapi.meta.Mappings;
import dev.neuralnexus.taterapi.meta.enums.MinecraftVersion;
import dev.neuralnexus.taterapi.muxins.annotations.ReqMCVersion;
import dev.neuralnexus.taterapi.muxins.annotations.ReqMappings;

import net.minecraft.network.Connection;
import net.minecraft.network.protocol.handshake.ClientIntentionPacket;
import net.minecraft.server.network.ServerHandshakePacketListenerImpl;

import org.adde0109.pcf.PCF;
import org.adde0109.pcf.forwarding.Mode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.net.InetSocketAddress;

@ReqMappings(Mappings.SEARGE)
@ReqMCVersion(MinecraftVersion.V19)
@Mixin(ServerHandshakePacketListenerImpl.class)
public class ServerHandshakePacketListenerImplMixin {

    @Shadow @Final private Connection connection;

    @Inject(method = "handleIntention", at = @At(value = "HEAD"))
    private void pcf$handleIntention(ClientIntentionPacket packet, CallbackInfo ci) {
        if (PCF.instance().forwarding().mode() != Mode.LEGACY) return;

        String host = packet.getHostName();
        String[] parts = host.split("\0");

        if (parts.length >= 3) {
            String ip = parts[1];
            String uuidString = parts[2];

            // Set address
            ((ConnectionAccessor) connection)
                    .setAddress(
                            new InetSocketAddress(
                                    ip,
                                    ((InetSocketAddress) connection.getRemoteAddress()).getPort()));

            // Set UUID
            if (connection instanceof ConnectionExtension) {
                ((ConnectionExtension) connection)
                        .pcf$setSpoofedUUID(UUIDTypeAdapter.fromString(uuidString));
            }
        }
    }
}
