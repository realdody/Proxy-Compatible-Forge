package org.adde0109.pcf.mixin.v1_19_2.forge.forwarding.legacy;

import com.mojang.authlib.properties.Property;

import dev.neuralnexus.taterapi.meta.Mappings;
import dev.neuralnexus.taterapi.meta.enums.MinecraftVersion;
import dev.neuralnexus.taterapi.muxins.annotations.ReqMCVersion;
import dev.neuralnexus.taterapi.muxins.annotations.ReqMappings;

import net.minecraft.network.Connection;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.UUID;

@ReqMappings(Mappings.SEARGE)
@ReqMCVersion(MinecraftVersion.V19)
@Mixin(Connection.class)
public class ConnectionMixin implements ConnectionExtension {
    @Unique private UUID pcf$spoofedUUID;
    @Unique private Property[] pcf$spoofedProperties;

    @Override
    public void pcf$setSpoofedUUID(UUID uuid) {
        this.pcf$spoofedUUID = uuid;
    }

    @Override
    public UUID pcf$getSpoofedUUID() {
        return this.pcf$spoofedUUID;
    }

    @Override
    public void pcf$setSpoofedProperties(Property[] properties) {
        this.pcf$spoofedProperties = properties;
    }

    @Override
    public Property[] pcf$getSpoofedProperties() {
        return this.pcf$spoofedProperties;
    }
}
