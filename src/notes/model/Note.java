package notes.model;

/*
 * Notes 
 * @author Peter Skurski
 */
public class Note implements Writable {
    private String _text;

    public Note(String text) {
    	_text = text.trim();
    }
    
    public void setNote(String text) {
    	_text = text.trim();
    }
    
    public String toFile() {
    	return String.format(";" + _text);
    }

    public String toString() {
        return String.format(_text);
    }
}

