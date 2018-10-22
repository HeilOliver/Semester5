package at.fhv.ohe.impla.implement;

import at.fhv.pmp.filter.DataTransformationFilter2;
import at.fhv.pmp.interfaces.Writeable;
import at.fhv.pmp.interfaces.Readable;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CircularShift extends DataTransformationFilter2<String[], String[]> {
    private String[] _frequentWords;
    private int countLine = 0;

    public CircularShift(Readable<String[]> input, String[] frequentWords) throws InvalidParameterException {
        super(input);
        _frequentWords = frequentWords;
        frequentWordsToLowerCase();

    }

    public CircularShift(Writeable<String[]> output, String[] frequentWords) throws InvalidParameterException {
        super(output);
        _frequentWords = frequentWords;
        frequentWordsToLowerCase();
    }

    @Override
    protected String[] process(String[] lineOfWords) {
        countLine++;

        if(lineOfWords.length > 0 && lineOfWords[0].equals("\0")){
            return lineOfWords;
        }

        if(String.join(" ", lineOfWords).trim().length() <= 0) {
            return new String[0];
        }

        List<String> outputStrings = new LinkedList<>();
        int i = 0;
        while ( i < lineOfWords.length){

            if(!Arrays.asList(_frequentWords).contains(lineOfWords[0].toLowerCase())) {
                String line = String.join(" ", lineOfWords) + " : " + countLine;
                outputStrings.add(line);
            }

            String firstWordOfLine = lineOfWords[0];
            shift(firstWordOfLine, lineOfWords);

            i++;
        }

        String[] result = new String[outputStrings.size()];
        outputStrings.toArray(result);

        return result;
    }

    private void frequentWordsToLowerCase(){
        for(int index = 0; index < _frequentWords.length; index++) {
            this._frequentWords[index] = _frequentWords[index].toLowerCase();
        }
    }

    private void shift(String firstWordOfLine, String[] line){
        System.arraycopy(line, 1, line, 0, line.length - 1);
        line[line.length - 1] = firstWordOfLine;

    }
}
