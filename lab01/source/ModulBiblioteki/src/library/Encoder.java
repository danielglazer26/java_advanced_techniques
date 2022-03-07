package library;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

public class Encoder {

    private static String catalogPath;
    private static HashMap<String, String> savedSnapshot;
    private static HashMap<String, String> currentSnapshot;


    public static void main(String... args) {

        if (args.length != 0) {
            choosePath(args[0]);
            findChanges().forEach(strings -> System.out.println(strings.get(0) + " " + strings.get(1)));
        } else
            System.out.println("Brak sciezki");
    }

    private static boolean readMD5() {

        try {
            BufferedReader br = new BufferedReader(new FileReader(catalogPath + "\\.md5"));
            String line;
            savedSnapshot = new HashMap<>();
            while ((line = br.readLine()) != null) {
                String[] fileSnapshot = line.split("=");
                savedSnapshot.put(fileSnapshot[0], fileSnapshot[1]);
            }
            br.close();
        } catch (IOException e) {
            return false;
        }
        return true;

    }

    private static void generateMD5() {

        try {
            currentSnapshot = new HashMap<>();
            MessageDigest md = MessageDigest.getInstance("MD5");

            InputStream is = null;
            DigestInputStream dis = null;
            for (String filePath : makeListOfFiles()) {
                is = Files.newInputStream(Path.of(filePath));
                dis = new DigestInputStream(is, md);
                currentSnapshot.put(filePath, Arrays.toString(md.digest(dis.readAllBytes())));
            }
            if (is != null)
                is.close();
            if (dis != null)
                dis.close();

        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        saveToMD5();
    }

    private static void saveToMD5() {

        Path pathMD5 = Path.of(catalogPath + "\\.md5");
        try (OutputStream out = new BufferedOutputStream(
                Files.newOutputStream(pathMD5, CREATE, TRUNCATE_EXISTING))) {

            for (Map.Entry<String, String> entry : currentSnapshot.entrySet()) {
                out.write((entry.toString()).getBytes(), 0, entry.toString().getBytes().length);
                out.write("\n".getBytes(), 0, "\n".getBytes().length);
            }
            out.close();

            Files.setAttribute(pathMD5, "dos:hidden", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> makeListOfFiles() throws IOException {
        return Files.list(Paths.get(catalogPath))
                .filter(Files::isRegularFile)
                .filter(e -> !e.endsWith(".md5"))
                .map(Path::toString)
                .collect(Collectors.toList());
    }

    public static void choosePath(String path) {
        catalogPath = path;
    }

    public static ArrayList<ArrayList<String>> findChanges() {
        ArrayList<ArrayList<String>> localizationOfChangedFiles = new ArrayList<>();

        if (readMD5()) {
            generateMD5();

            boolean fileDeleted;

            // sprawdzenie zmian
            for (Map.Entry<String, String> saved : savedSnapshot.entrySet()) {
                fileDeleted = true;
                for (Map.Entry<String, String> current : currentSnapshot.entrySet()) {

                    if (Objects.equals(saved.getKey(), current.getKey())) {
                        if (!Objects.equals(saved.getValue(), current.getValue()))
                            localizationOfChangedFiles.add(new ArrayList<>(Arrays.asList(saved.getKey(), "Zmiana")));
                        currentSnapshot.remove(current.getKey());
                        fileDeleted = false;
                        break;
                    }

                }
                if (fileDeleted)
                    localizationOfChangedFiles.add(new ArrayList<>(Arrays.asList(saved.getKey(), "Usunięto")));
                else if (localizationOfChangedFiles.size() == 0)
                    localizationOfChangedFiles.add(new ArrayList<>(Arrays.asList(saved.getKey(), "Brak zmian")));
                else if (!localizationOfChangedFiles
                        .get(localizationOfChangedFiles.size() - 1)
                        .get(0)
                        .equals(saved.getKey()))
                    localizationOfChangedFiles.add(new ArrayList<>(Arrays.asList(saved.getKey(), "Brak zmian")));
            }

        } else
            generateMD5();

        if (currentSnapshot.size() > 0)
            currentSnapshot.forEach((s, s2) -> localizationOfChangedFiles.add(new ArrayList<>(Arrays.asList(s, "Dodano"))));

        return localizationOfChangedFiles;
    }
}
