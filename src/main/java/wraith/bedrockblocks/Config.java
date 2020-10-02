package wraith.bedrockblocks;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

public class Config {

    public static HashSet<Block> loadDisabledBlocks() {
        HashSet<Block> set = new HashSet<>();
        String defaultConfig =
                        "{\n" +
                        "  \"disabled_blocks\": [\n" +
                        "    \"minecraft:end_portal_frame\",\n" +
                        "    \"minecraft:bedrock\"\n" +
                        "  ]\n" +
                        "}";
        String path = "config/bedrockblocks/disabled_blocks.json";
        Config.createFile(path, defaultConfig, false);
        File file = new File(path);
        JsonObject json = getJsonObject(readFile(file));
        for (JsonElement disabled : json.get("disabled_blocks").getAsJsonArray()) {
            System.out.println(Registry.BLOCK.get(new Identifier(disabled.getAsString())));
            set.add(Registry.BLOCK.get(new Identifier(disabled.getAsString())));
        }
        return set;
    }

    public static HashSet<String> loadPlayerWhitelist() {
        HashSet<String> set = new HashSet<>();
        String defaultConfig =
                        "{\n" +
                        "  \"whitelisted_players\": [\n" +
                        "    \"LordDeatHunter\",\n" +
                        "    \"Notch\"\n" +
                        "  ]\n" +
                        "}";
        String path = "config/bedrockblocks/whitelisted_players.json";
        Config.createFile(path, defaultConfig, false);
        File file = new File(path);
        JsonObject json = getJsonObject(readFile(file));
        for (JsonElement player : json.get("whitelisted_players").getAsJsonArray()) {
            set.add(player.getAsString());
        }
        return set;
    }

    public static void createFile(String path, String contents, boolean overwrite) {
        File file = new File(path);
        if (file.exists() && !overwrite) return;
        file.getParentFile().mkdirs();
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        file.setReadable(true);
        file.setWritable(true);
        file.setExecutable(true);
        if (contents == null || contents.equals(""))return;
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(contents);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFile(File file) {
        String output = "";
        try {
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter("\\Z");
            output = scanner.next();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return output;
    }

    public static JsonObject getJsonObject(String json) {
        return new JsonParser().parse(json).getAsJsonObject();
    }

}
