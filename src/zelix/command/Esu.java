/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 */
package zelix.command;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import zelix.command.Command;
import zelix.utils.hooks.visual.ChatUtils;

public class Esu
extends Command {
    public Esu() {
        super("Esu");
    }

    public static String getJsonData(String jsonString, String targetString) {
        JsonParser parser = new JsonParser();
        JsonElement jsonNode = parser.parse(jsonString);
        if (jsonNode.isJsonObject()) {
            JsonObject jsonObject = jsonNode.getAsJsonObject();
            JsonElement jsonElementId = jsonObject.get(targetString);
            return jsonElementId.toString();
        }
        return null;
    }

    public static String deleteCharInString(String str, char delChar) {
        StringBuilder delStr = new StringBuilder();
        for (int i = 0; i < str.length(); ++i) {
            if (str.charAt(i) == delChar) continue;
            delStr.append(str.charAt(i));
        }
        return delStr.toString();
    }

    public List<String> autocomplete(int arg, String[] args) {
        return new ArrayList<String>();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String line;
            URL realUrl = new URL(url);
            URLConnection connection = realUrl.openConnection();
            connection.setDoOutput(true);
            connection.setReadTimeout(99781);
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            Map<String, List<String>> map = connection.getHeaderFields();
            for (String string : map.keySet()) {
            }
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = in.readLine()) != null) {
                result = String.valueOf(result) + line;
            }
        }
        catch (Exception exception) {
            try {
                if (in != null) {
                    in.close();
                }
            }
            catch (Exception exception2) {
                // empty catch block
            }
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            }
            catch (Exception exception) {}
        }
        return result;
    }

    @Override
    public void runCommand(String s, String[] args) {
        try {
            if (args.length == 0) {
                ChatUtils.error("Usage: " + this.getSyntax());
            }
            String jsonData = Esu.sendGet("https://qb-api.ltd/allcha.php?qq=" + args[0], null);
            jsonData = Esu.deleteCharInString(jsonData, '\n');
            jsonData = Esu.deleteCharInString(jsonData, '\r');
            jsonData = Esu.deleteCharInString(jsonData, ' ');
            try {
                String parsedData = Esu.getJsonData(jsonData, "data");
                String qq = Esu.getJsonData(parsedData, "qq");
                String mobile = Esu.getJsonData(parsedData, "mobile");
                ChatUtils.success("QQ:" + qq + ",mobile:" + mobile);
            }
            catch (Exception e) {
                ChatUtils.message("Can't Find!");
            }
        }
        catch (Exception e) {
            ChatUtils.error("Usage: " + this.getSyntax());
        }
    }

    @Override
    public String getDescription() {
        return "Esu";
    }

    @Override
    public String getSyntax() {
        return "esu <QQNumber>";
    }
}

