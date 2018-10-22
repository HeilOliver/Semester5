package at.fhv.ohe.impla.implement;

import at.fhv.pmp.filter.DataTransformationFilter2;
import at.fhv.pmp.interfaces.Writeable;
import at.fhv.pmp.interfaces.Readable;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class FrequentWordProcessor {

    public String[] getFrequentWords(String path, int numberOfWordsToReadFromFrequentWords){
        List<String> frequentWords = new LinkedList<>();

        try {
            FileInputStream fis = new FileInputStream(path);
            DataInputStream dis = new DataInputStream(fis);
            BufferedReader bReader = new BufferedReader(new InputStreamReader(dis));
            String line;
            bReader.readLine();

            for(int i = 0; i < numberOfWordsToReadFromFrequentWords; i++){
                if((line = bReader.readLine())!= null){
                    String word = line.split("\t")[1];
                    frequentWords.add(word);
                }
            }

            dis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] words = new String[frequentWords.size()];
        return frequentWords.toArray(words);
    }
}
