package ros.eagleoffire.roshud.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ModClientConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> HP_BAR_X;
    public static final ForgeConfigSpec.ConfigValue<Integer> HP_BAR_Y;
    public static final ForgeConfigSpec.ConfigValue<Integer> MANA_BAR_X;
    public static final ForgeConfigSpec.ConfigValue<Integer> MANA_BAR_Y;
    public static final ForgeConfigSpec.ConfigValue<Integer> HUNGER_BAR_X;
    public static final ForgeConfigSpec.ConfigValue<Integer> HUNGER_BAR_Y;

    static {
        BUILDER.push("Configs for ROS hud Mod");

        HP_BAR_X = BUILDER.comment("L'alignement horizontal de la bar d'HP").define("HP bar x",10);
        HP_BAR_Y = BUILDER.comment("L'alignement vertical de la bar d'HP").define("HP bar y",2);
        MANA_BAR_X = BUILDER.comment("L'alignement horizontal de la bar de mana").define("mana bar x",2);
        MANA_BAR_Y = BUILDER.comment("L'alignement vertical de la bar de mana").define("mana bar y",30);
        HUNGER_BAR_X = BUILDER.comment("L'alignement horizontal de la bar de nourriture").define("hunger bar x",3);
        HUNGER_BAR_Y = BUILDER.comment("L'alignement vertical de la bar de nourriture").define("hunger bar y",16);

        BUILDER.pop();

        SPEC=BUILDER.build();
    }
}
