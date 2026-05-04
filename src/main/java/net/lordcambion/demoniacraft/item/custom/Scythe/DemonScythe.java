package net.lordcambion.demoniacraft.item.custom.Scythe;

import net.lordcambion.demoniacraft.Demoniacraft;
import net.lordcambion.demoniacraft.item.custom.Tools.CustomToolItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ToolAction;

import java.util.Properties;
import java.util.Set;

import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.resources.ResourceLocation;

public class DemonScythe extends Item {


    public DemonScythe(Properties properties) {
        super(properties);
    }

    // Static method to create the attribute modifiers for the scythe
    public static ItemAttributeModifiers createScytheAttributes() {
        return ItemAttributeModifiers.builder()
                // +2 attack range
                .add(Attributes.ENTITY_INTERACTION_RANGE,
                        new AttributeModifier(

                                ResourceLocation.fromNamespaceAndPath(Demoniacraft.MOD_ID, "scythe_entity_range"),
                                2.0,
                                AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.MAINHAND)
                // High base damage (adjust values as needed - higher than diamond sword)
                .add(Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(

                                ResourceLocation.fromNamespaceAndPath(Demoniacraft.MOD_ID, "scythe_attack_damage"),
                                10.0, // Higher than diamond sword (which is 7)
                                AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.MAINHAND)
                // Slow attack speed (high cooldown)
                .add(Attributes.ATTACK_SPEED,
                        new AttributeModifier(

                                ResourceLocation.fromNamespaceAndPath(Demoniacraft.MOD_ID, "scythe_attack_speed"),
                                -3.2, // Very slow (diamond sword is -2.4)
                                AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.MAINHAND)
                .build();
    }
}