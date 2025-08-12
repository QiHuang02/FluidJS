package cn.qihuang02.fluidjs.config;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

public class FluidJSConfig {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.ConfigValue<List<? extends String>> FLUID_MODIFICATIONS;

    static {
        BUILDER.push("Fluid Modifications");

        FLUID_MODIFICATIONS = BUILDER
                .comment("""
                        A list of rules to modify fluid properties.
                        Each rule is a comma-separated string with 3 parts:
                        1. Fluid ID (e.g., "minecraft:water")
                        2. Property Name (e.g., "luminosity", "density", "rarity")
                        3. Value (e.g., "15", "1500", "RARE")
                        
                        Valid Property Names:
                        - For FluidBlock: luminosity (0-15)
                        - For FluidType:
                          - Integer: density (>0), viscosity (>0), temperature (any)
                          - Float: fallDistanceModifier (0.0-1.0)
                          - Double: motionScale (0.0-1.0)
                          - Boolean: canPush, canSwim, canDrown, canExtinguish, canConvertToSource, supportsBoating, canHydrate
                          - String: rarity (Values: COMMON, UNCOMMON, RARE, EPIC)
                        """)
                .defineListAllowEmpty("modifications", () -> List.of(
                        "minecraft:water,luminosity,5", // Example: Make water glow slightly
                        "minecraft:lava,temperature,1000", // Example: Make lava a bit cooler
                        "minecraft:water,rarity,UNCOMMON", // Example: Make water's name blue
                        "minecraft:lava,supportsBoating,true" // Example: Allow boating on lava
                ), obj -> obj instanceof String && validateRule((String) obj));

        BUILDER.pop();
        SPEC = BUILDER.build();
    }

    private static boolean validateRule(String rule) {
        if (rule == null || rule.isBlank()) {
            return false;
        }
        
        String[] parts = rule.split(",", 3);
        if (parts.length != 3) {
            return false;
        }
        
        String fluidId = parts[0].trim();
        String property = parts[1].trim().toLowerCase();
        String value = parts[2].trim();
        
        if (fluidId.isEmpty() || property.isEmpty() || value.isEmpty()) {
            return false;
        }
        
        return switch (property) {
            case "luminosity" -> isValidInteger(value, 0, 15);
            case "density", "viscosity" -> isValidInteger(value, 1, Integer.MAX_VALUE);
            case "temperature" -> isValidInteger(value);
            case "falldistancemodifier", "motionscale" -> isValidDouble(value, 0.0, 1.0);
            case "canpush", "canswim", "candrown", "canextinguish", "canconverttosource", 
                 "supportsboating", "canhydrate" -> isValidBoolean(value);
            case "rarity" -> isValidRarity(value);
            default -> true;
        };
    }
    
    private static boolean isValidInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    private static boolean isValidInteger(String value, int min, int max) {
        try {
            int intValue = Integer.parseInt(value);
            return intValue >= min && intValue <= max;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    private static boolean isValidDouble(String value, double min, double max) {
        try {
            double doubleValue = Double.parseDouble(value);
            return doubleValue >= min && doubleValue <= max;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    private static boolean isValidBoolean(String value) {
        return "true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value);
    }
    
    private static boolean isValidRarity(String value) {
        try {
            net.minecraft.world.item.Rarity.valueOf(value.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
