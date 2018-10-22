package at.fhv.ohe.impla.implement;

import at.fhv.pmp.filter.DataTransformationFilter2;
import at.fhv.pmp.interfaces.Writeable;
import at.fhv.pmp.interfaces.Readable;

import java.security.InvalidParameterException;

public class WordSeparator extends DataTransformationFilter2<String, String[]> {


    public WordSeparator(Readable<String> input) throws InvalidParameterException {
        super(input);
    }

    public WordSeparator(Writeable<String[]> output) throws InvalidParameterException {
        super(output);
    }

    @Override
    protected String[] process(String entity) {

        entity = entity.replaceAll("_", "").replaceAll("[^\\w\\s\0]", "");
        String[] line = entity.split("\\s+");
        int i = 0;

        while(line.length > i){
            line[i] = line[i].replaceAll("\\s+","");
            i++;
        }

        return line;

    }
}
