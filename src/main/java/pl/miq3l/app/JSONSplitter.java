package pl.miq3l.app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONSplitter {

    public static void main(String[] args) {
        String inputFilePath = "src/main/resources/RefusalOrder.json";
        String outputFolderPath = "src/main/resources/Result.json";
        int chunkSize = 4; // Liczba rekordów w każdej części

        try {
            // Odczytaj zawartość pliku JSON
            String jsonData = readJSONFile(inputFilePath);

            // Konwertuj dane JSON na obiekt JSONArray
            JSONArray jsonArray = new JSONArray(jsonData);

            int totalRecords = jsonArray.length();
            int chunkNumber = 1;

            // Podziel dane na części i zapisz jako osobne pliki
            for (int i = 0; i < totalRecords; i += chunkSize) {
                JSONArray chunkArray = new JSONArray();

                for (int j = i; j < i + chunkSize && j < totalRecords; j++) {
                    JSONObject record = jsonArray.getJSONObject(j);
                    chunkArray.put(record);
                }

                // Zapisz część jako osobny plik JSON
                String outputFilePath = outputFolderPath + "czesc_" + chunkNumber + ".json";
                writeJSONFile(chunkArray.toString(), outputFilePath);

                chunkNumber++;
            }

            System.out.println("Podzielono plik na " + (chunkNumber - 1) + " części.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readJSONFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        StringBuilder stringBuilder = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }

        reader.close();

        return stringBuilder.toString();
    }

    private static void writeJSONFile(String jsonData, String filePath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        writer.write(jsonData);
        writer.close();
    }
}
