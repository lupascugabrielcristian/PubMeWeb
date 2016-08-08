package pubme.services;



import pubme.interfaces.IResourceReader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ResourceReader implements IResourceReader {

    @Override
    public List<String> getNames() {

        List<String> allNames = new ArrayList<>();
        String csvFile = "/static/names.csv";
        BufferedReader br;
        String line;

        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                allNames.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return allNames;
    }

    @Override
    public String getRandomName() {
        List<String> allNames = getNames();
        Random rand = new Random();
        int chosen = rand.nextInt(allNames.size());
        return allNames.get(chosen);
    }
}
