package at.fhv.ohe.impla.implement;

import at.fhv.pmp.filter.DataTransformationFilter2;
import at.fhv.pmp.interfaces.Writeable;
import at.fhv.pmp.interfaces.Readable;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.LinkedList;

public class SortAlphabetically extends DataTransformationFilter2<String[], String[]> {
    private LinkedList<String> allLines = new LinkedList<>();

    public SortAlphabetically(Readable<String[]> input) throws InvalidParameterException {
        super(input);
    }

    public SortAlphabetically(Writeable<String[]> output) throws InvalidParameterException {
        super(output);
    }

    @Override
    protected String[] process(String[] lineArray) {


        allLines.addAll(Arrays.asList(lineArray));
        int i = 0;

        while(lineArray.length > i){
            if(lineArray[i]== "\0") {
                allLines.sort(String.CASE_INSENSITIVE_ORDER);
                String[] outputArray = new String[allLines.size()];
                i++;
                return allLines.toArray(outputArray);
            } else {
                i++;
            }

        }

        return null;
    }
}
