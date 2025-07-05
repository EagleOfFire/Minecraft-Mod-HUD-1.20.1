package ros.eagleoffire.roshud.commands;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ManaValueCommand {
    public ManaValueCommand(CommandDispatcher<CommandSourceStack> Dispatcher) {
        Dispatcher.register(Commands.literal("mana_value")
                .requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                .then(Commands.argument("target", StringArgumentType.string())
                        .suggests(this::suggestTargets)
                        .executes(commandContext ->
                                GetManaValue(commandContext.getSource(),
                                        StringArgumentType.getString(commandContext, "target")))
                ));
    }

    private CompletableFuture<Suggestions> suggestTargets(CommandContext<CommandSourceStack> source, SuggestionsBuilder builder) {
        PlayerList playerList = source.getSource().getServer().getPlayerList();
        for (ServerPlayer player : playerList.getPlayers()) {
            builder.suggest(player.getName().getString());
        }
        return builder.buildFuture();
    }

    private int GetManaValue(CommandSourceStack source, String target) throws CommandSyntaxException {
        PlayerList onlinePlayers = source.getServer().getPlayerList();
        ServerPlayer targetedPlayer = onlinePlayers.getPlayerByName(target);

        if (targetedPlayer == null) {
            source.sendSystemMessage(Component.literal("Joueur '" + target + "' introuvable."));
            return 0;
        }

        File manaFile = new File(System.getProperty("user.dir"), "plugins/MagicBridge/mana.json");
        if (!manaFile.exists()) {
            source.sendSystemMessage(Component.literal("mana.json introuvable !"));
            return 0;
        }

        try (FileReader reader = new FileReader(manaFile)) {
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, Integer>>() {
            }.getType();
            Map<String, Integer> manaMap = gson.fromJson(reader, type);

            String uuid = targetedPlayer.getUUID().toString();
            if (!manaMap.containsKey(uuid)) {
                source.sendSystemMessage(Component.literal("Aucune valeur de mana enregistrée pour ce joueur (" + uuid + ")."));
                return 0;
            }

            int mana = manaMap.get(uuid);
            source.sendSystemMessage(Component.literal(target + " a " + mana + " mana."));
            return 1;

        } catch (Exception e) {
            source.sendSystemMessage(Component.literal("Erreur lors de la lecture de mana.json : " + e.getMessage()));
            e.printStackTrace();
            return 0;
        }
    }
}
