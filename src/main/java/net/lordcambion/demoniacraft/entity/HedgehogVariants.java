package net.lordcambion.demoniacraft.entity;

import java.util.Arrays;
import java.util.Comparator;

public enum HedgehogVariants {
    GRAY(0),
    BROWN(1);

    private static final HedgehogVariants[] BY_ID = Arrays.stream(values())
            .sorted(Comparator.comparingInt(HedgehogVariants::getId))
            .toArray(HedgehogVariants[]::new);

    private final int id;

    HedgehogVariants(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static HedgehogVariants byId(int id) {
        if (id < 0 || id >= BY_ID.length) {
            return GRAY; // Fallback
        }
        return BY_ID[id];
    }
}