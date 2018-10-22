package at.fhv.ohe.impla;

import at.fhv.ohe.impla.implement.*;
import at.fhv.pmp.interfaces.Readable;
import at.fhv.pmp.pipes.SimplePipe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Assignment_2_PipeFilter
 * at.fhv.ohe.impla
 * null.java
 * 22/10/2018 OliverHeil
 * <p>
 * Enter Description here
 */
public class RunMe {
    private static final Logger logger = LogManager.getLogger("RunMe");
    private static final String bookTextFile = "\\aliceInWonderland.txt";
    private static final String wordTextFile = "frequentEnglishWords.txt";
    private static final String outputTextFile = "outputPath.txt";

    public static void main(String[] args) throws IOException {
        logger.info("Start");

        var input = new Input(bookTextFile);



        var simplePipe1 = new SimplePipe<>(input);
        WordSeparator wordSeparator = new WordSeparator(simplePipe1);
        SimplePipe<String[]> simplePipe2 = new SimplePipe<>((Readable<String[]>) wordSeparator);
        FrequentWordProcessor frequentWordProcessor = new FrequentWordProcessor();
        String[] frequentWords = frequentWordProcessor.getFrequentWords(wordTextFile, 104);
        CircularShift circularShift = new CircularShift((Readable<String[]>) simplePipe2, frequentWords);
        SimplePipe<String[]> simplePipe3 = new SimplePipe<>((Readable<String[]>) circularShift);
        SortAlphabetically sortAlphabetically = new SortAlphabetically((Readable<String[]>) simplePipe3);
        SimplePipe<String[]> simplePipe4 = new SimplePipe<>((Readable<String[]>) sortAlphabetically);
        Output output = new Output(simplePipe4, outputTextFile);

        //close input
        if (input != null) {
            output.run();
            input.closeStream();
        }

    }
}
