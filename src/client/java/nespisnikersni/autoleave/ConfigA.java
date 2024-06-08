package nespisnikersni.autoleave;

import net.fabricmc.loader.api.FabricLoader;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class ConfigA {
    public void createConfig(String fileName) {
        try {
            Path configDir = FabricLoader.getInstance().getConfigDir();
            Path folderPath = configDir.resolve("AutoLeave");
            Files.createDirectories(folderPath);
            Path filePath = folderPath.resolve(fileName + ".properties");
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addString(String fileName, String key, String value) {
        if(getString(fileName,key)==null) {
            try {
                Path configDir = FabricLoader.getInstance().getConfigDir();
                Path folderPath = configDir.resolve("AutoLeave");
                Files.createDirectories(folderPath);
                Path filePath = folderPath.resolve(fileName + ".properties");
                try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
                    writer.write(key + "=" + value);
                    writer.newLine();
                }
                saveConfig(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void saveConfig(Path filePath) {
        try {
            Files.setAttribute(filePath, "dos:hidden", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getString(String fileName, String key) {
        try {
            Path configDir = FabricLoader.getInstance().getConfigDir();
            Path folderPath = configDir.resolve("AutoLeave");
            Files.createDirectories(folderPath);
            Path filePath = folderPath.resolve(fileName + ".properties");
            List<String> lines = Files.readAllLines(filePath);
            for (String line : lines) {
                if (line.startsWith(key + "=")&&key!=null) {
                    return line.substring(key.length() + 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Map<String, String> readConfigFile(Path filePath) {
        Map<String, String> configMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split each line by '=' to get the key and value
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    configMap.put(key, value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return configMap;
    }
    public void setValue(String fileName, String key, String value) {
        try {
            Path configDir = FabricLoader.getInstance().getConfigDir();
            Path folderPath = configDir.resolve("AutoLeave");
            Files.createDirectories(folderPath);
            Path filePath = folderPath.resolve(fileName + ".properties");
            List<String> lines = Files.readAllLines(filePath);
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                if (line.startsWith(key + "=")) {
                    lines.set(i, key + "=" + value);
                    break;
                }
            }
            Files.write(filePath, lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean isEmpty(String fileName) {
        try {
            Path configDir = FabricLoader.getInstance().getConfigDir();
            Path folderPath = configDir.resolve("AutoLeave");
            Path filePath = folderPath.resolve(fileName + ".properties");

            if (Files.exists(filePath)) {
                List<String> lines = Files.readAllLines(filePath);
                return lines.isEmpty();
            } else {
                return true;
            }
        } catch (IOException e) {
            return true;
        }
    }
}
