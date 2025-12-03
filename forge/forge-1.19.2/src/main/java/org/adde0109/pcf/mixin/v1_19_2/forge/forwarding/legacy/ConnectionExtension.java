package org.adde0109.pcf.mixin.v1_19_2.forge.forwarding.legacy;

import com.mojang.authlib.properties.Property;

import java.util.UUID;

public interface ConnectionExtension {
    void pcf$setSpoofedUUID(UUID uuid);

    UUID pcf$getSpoofedUUID();

    void pcf$setSpoofedProperties(Property[] properties);

    Property[] pcf$getSpoofedProperties();
}
