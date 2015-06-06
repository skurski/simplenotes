package notes.model;

/*
 * Objects which want to use FileModel class
 * to write themselves to text file
 * have to implement this interface
 */
public interface Writable {
	String toFile();
}
