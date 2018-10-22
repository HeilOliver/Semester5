package at.fhv.ohe.impla.implement;

import at.fhv.pmp.filter.Source;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

public class Input extends Source<String> {

    private static final Logger logger = LogManager.getLogger("Input");

    private BufferedReader bufferedReader;
    private String line;

    public Input(String path) throws IOException {
        try (var inputStream = new FileInputStream(path)) {
            this.bufferedReader =
                    new BufferedReader(new InputStreamReader(inputStream));
            line = "";
        } catch (IOException e) {
            logger.error("Input Error - ",e);
            throw e;
        }
    }

    @Override
    public String read() throws StreamCorruptedException {

        try{
            return readNextLine();
        } catch (IOException e) {
            logger.warn("Error while reading - ", e);
        }
        return null;
    }

    private String readNextLine() throws IOException {
        if((line = this.bufferedReader.readLine()) != null) {
            return line;
        }

        return "\0";
    }

    public void closeStream(){
        try {
            if (bufferedReader != null)
                bufferedReader.close();
        } catch (IOException e) {
            // Ignore
        }
    }
}
