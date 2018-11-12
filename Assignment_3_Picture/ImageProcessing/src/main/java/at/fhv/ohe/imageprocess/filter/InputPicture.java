package at.fhv.ohe.imageprocess.filter;

import at.fhv.pmp.filter.Source;
import at.fhv.pmp.interfaces.Writeable;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import java.io.StreamCorruptedException;

public class InputPicture extends Source<PlanarImage> {
    private String picturePath;
    private boolean isPictureLoaded = true;

    public InputPicture(String path) {
        picturePath = path;
    }

    public InputPicture(Writeable<PlanarImage> out, String path) {
        super(out);
        picturePath = path;
    }

    @Override
    public void run() {
        PlanarImage output;
        try {
            if (m_Output == null)
                throw new StreamCorruptedException("output filter is null");

            output = read();
            m_Output.write(output);

            epilogue();
        } catch (StreamCorruptedException e) {
            //Ignore
        }
    }

    @Override
    public PlanarImage read() throws StreamCorruptedException {
        if (picturePath == null && !isPictureLoaded) {
            return null;
        }

        PlanarImage image = JAI.create("fileload", picturePath);
        isPictureLoaded = false;

        return image;
    }
}
