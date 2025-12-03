package org.adde0109.pcf.mixin.v1_19_2.forge.forwarding.legacy;

import dev.neuralnexus.taterapi.meta.Mappings;
import dev.neuralnexus.taterapi.meta.enums.MinecraftVersion;
import dev.neuralnexus.taterapi.muxins.annotations.ReqMCVersion;
import dev.neuralnexus.taterapi.muxins.annotations.ReqMappings;

import net.minecraft.network.Connection;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.net.SocketAddress;

@ReqMappings(Mappings.SEARGE)
@ReqMCVersion(MinecraftVersion.V19)
@Mixin(Connection.class)
public interface ConnectionAccessor {
    @Accessor("f_129468_") // address
    void setAddress(SocketAddress address);
}
