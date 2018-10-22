package at.fhv.ohe.impla.implement;

import at.fhv.pmp.filter.DataTransformationFilter2;
import at.fhv.pmp.filter.Sink;
import at.fhv.pmp.interfaces.Writeable;
import at.fhv.pmp.interfaces.Readable;

import java.io.*;
import java.security.InvalidParameterException;

public class Output extends Sink<String[]> {

    private Readable<String[]> input;
    private BufferedWriter buffer;

    public Output(Readable<String[]> input, String path) throws InvalidParameterException {
        super(input);
        this.input = input;
        try {
            FileOutputStream fos = new FileOutputStream(path);
            DataOutputStream dos = new DataOutputStream(fos);
            buffer = new BufferedWriter(new OutputStreamWriter(dos));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Output(String path) throws InvalidParameterException {
        super();
        try {
            FileOutputStream fos = new FileOutputStream(path);
            DataOutputStream dos = new DataOutputStream(fos);
            buffer = new BufferedWriter(new OutputStreamWriter(dos));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(String[] lines) throws StreamCorruptedException {
        if(buffer == null) {
            return;
        }
        if(lines != null) {
            for (String line : lines) {
                try {
                    buffer.write(line);
                    buffer.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                buffer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        String[] currentInput;
        try {
            do {
                currentInput  = this.input.read();
            } while(currentInput == null);
            write(currentInput);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
