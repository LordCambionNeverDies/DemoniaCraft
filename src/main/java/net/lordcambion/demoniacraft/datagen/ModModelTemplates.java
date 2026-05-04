package net.lordcambion.demoniacraft.datagen;


import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public class ModModelTemplates extends ModelTemplates {
    public static final ModModelTemplate DOOR_BOTTOM_LEFT = create("door_bottom_left", "_bottom_left", TextureSlot.TOP, TextureSlot.BOTTOM);
    public static final ModModelTemplate DOOR_BOTTOM_LEFT_OPEN = create("door_bottom_left_open", "_bottom_left_open", TextureSlot.TOP, TextureSlot.BOTTOM);
    public static final ModModelTemplate DOOR_BOTTOM_RIGHT = create("door_bottom_right", "_bottom_right", TextureSlot.TOP, TextureSlot.BOTTOM);
    public static final ModModelTemplate DOOR_BOTTOM_RIGHT_OPEN = create("door_bottom_right_open", "_bottom_right_open", TextureSlot.TOP, TextureSlot.BOTTOM);
    public static final ModModelTemplate DOOR_TOP_LEFT = create("door_top_left", "_top_left", TextureSlot.TOP, TextureSlot.BOTTOM);
    public static final ModModelTemplate DOOR_TOP_LEFT_OPEN = create("door_top_left_open", "_top_left_open", TextureSlot.TOP, TextureSlot.BOTTOM);
    public static final ModModelTemplate DOOR_TOP_RIGHT = create("door_top_right", "_top_right", TextureSlot.TOP, TextureSlot.BOTTOM);
    public static final ModModelTemplate DOOR_TOP_RIGHT_OPEN = create("door_top_right_open", "_top_right_open", TextureSlot.TOP, TextureSlot.BOTTOM);

    // Template per trapdoor
    public static final ModModelTemplate TRAPDOOR_TOP = create("template_trapdoor_top", "_top", TextureSlot.TEXTURE);
    public static final ModModelTemplate TRAPDOOR_BOTTOM = create("template_trapdoor_bottom", "_bottom", TextureSlot.TEXTURE);
    public static final ModModelTemplate TRAPDOOR_OPEN = create("template_trapdoor_open", "_open", TextureSlot.TEXTURE);
    public static final ModModelTemplate ORIENTABLE_TRAPDOOR_TOP = create("template_orientable_trapdoor_top", "_top", TextureSlot.TEXTURE);
    public static final ModModelTemplate ORIENTABLE_TRAPDOOR_BOTTOM = create("template_orientable_trapdoor_bottom", "_bottom", TextureSlot.TEXTURE);
    public static final ModModelTemplate ORIENTABLE_TRAPDOOR_OPEN = create("template_orientable_trapdoor_open", "_open", TextureSlot.TEXTURE);

    // Template per scale - AGGIUNGI QUESTI
    public static final ModModelTemplate STAIRS_INNER = create("inner_stairs", "", TextureSlot.ALL);
    public static final ModModelTemplate STAIRS_STRAIGHT = create("stairs", "", TextureSlot.ALL);
    public static final ModModelTemplate STAIRS_OUTER = create("outer_stairs", "", TextureSlot.ALL);

    // Template per slab - AGGIUNGI QUESTI
    public static final ModModelTemplate SLAB_BOTTOM = create("slab", "", TextureSlot.ALL);
    public static final ModModelTemplate SLAB_TOP = create("slab_top", "", TextureSlot.ALL);
    public static final ModModelTemplate SLAB_DOUBLE = create("cube", "", TextureSlot.ALL);

    // Template per altri blocchi
    public static final ModModelTemplate BUTTON = create("button", "", TextureSlot.TEXTURE);
    public static final ModModelTemplate BUTTON_PRESSED = create("button_pressed", "", TextureSlot.TEXTURE);
    public static final ModModelTemplate BUTTON_INVENTORY = create("button_inventory", "_inventory", TextureSlot.TEXTURE);
    public static final ModModelTemplate WALL_POST = create("template_wall_post", "_post", TextureSlot.WALL);
    public static final ModModelTemplate WALL_LOW_SIDE = create("template_wall_side", "_side", TextureSlot.WALL);
    public static final ModModelTemplate WALL_TALL_SIDE = create("template_wall_side_tall", "_side_tall", TextureSlot.WALL);
    public static final ModModelTemplate WALL_INVENTORY = create("wall_inventory", "_inventory", TextureSlot.WALL);
    public static final ModModelTemplate FENCE_POST = create("fence_post", "_post", TextureSlot.TEXTURE);
    public static final ModModelTemplate FENCE_SIDE = create("fence_side", "_side", TextureSlot.TEXTURE);
    public static final ModModelTemplate FENCE_INVENTORY = create("fence_inventory", "_inventory", TextureSlot.TEXTURE);
    public static final ModModelTemplate CUSTOM_FENCE_POST = create("custom_fence_post", "_post", TextureSlot.TEXTURE);
    public static final ModModelTemplate CUSTOM_FENCE_SIDE_NORTH = create("custom_fence_side_north", "_side_north", TextureSlot.TEXTURE);
    public static final ModModelTemplate CUSTOM_FENCE_SIDE_EAST = create("custom_fence_side_east", "_side_east", TextureSlot.TEXTURE);
    public static final ModModelTemplate CUSTOM_FENCE_SIDE_SOUTH = create("custom_fence_side_south", "_side_south", TextureSlot.TEXTURE);
    public static final ModModelTemplate CUSTOM_FENCE_SIDE_WEST = create("custom_fence_side_west", "_side_west", TextureSlot.TEXTURE);
    public static final ModModelTemplate CUSTOM_FENCE_INVENTORY = create("custom_fence_inventory", "_inventory", TextureSlot.TEXTURE);
    public static final ModModelTemplate FENCE_GATE_OPEN = create("template_fence_gate_open", "_open", TextureSlot.TEXTURE);
    public static final ModModelTemplate FENCE_GATE_CLOSED = create("template_fence_gate", "", TextureSlot.TEXTURE);
    public static final ModModelTemplate FENCE_GATE_WALL_OPEN = create("template_fence_gate_wall_open", "_wall_open", TextureSlot.TEXTURE);
    public static final ModModelTemplate FENCE_GATE_WALL_CLOSED = create("template_fence_gate_wall", "_wall", TextureSlot.TEXTURE);
    public static final ModModelTemplate CUSTOM_FENCE_GATE_OPEN = create("custom_fence_gate_open", "_open", TextureSlot.TEXTURE);
    public static final ModModelTemplate CUSTOM_FENCE_GATE_CLOSED = create("custom_fence_gate", "", TextureSlot.TEXTURE);
    public static final ModModelTemplate CUSTOM_FENCE_GATE_WALL_OPEN = create("custom_fence_gate_wall_open", "_wall_open", TextureSlot.TEXTURE);
    public static final ModModelTemplate CUSTOM_FENCE_GATE_WALL_CLOSED = create("custom_fence_gate_wall", "_wall", TextureSlot.TEXTURE);
    public static final ModModelTemplate PRESSURE_PLATE_UP = create("pressure_plate_up", "", TextureSlot.TEXTURE);
    public static final ModModelTemplate PRESSURE_PLATE_DOWN = create("pressure_plate_down", "", TextureSlot.TEXTURE);
    public static final ModModelTemplate PARTICLE_ONLY = create("particle_only", "", TextureSlot.PARTICLE);

    private static ModModelTemplate create(String pName, String pSuffix, TextureSlot... pRequiredSlots) {
        return new ModModelTemplate(Optional.of(ResourceLocation.withDefaultNamespace("block/" + pName)), Optional.of(pSuffix), pRequiredSlots);
    }
}


