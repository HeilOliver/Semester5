package at.fhv.pmp.interfaces;


public interface IOable<in, out> extends Readable<out>, Writeable<in> {

}
