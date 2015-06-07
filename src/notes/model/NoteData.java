package notes.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class NoteData extends FileModel {
    private List<Note> _notes = new LinkedList<Note>(); //linked list with notes

    NoteData() {
    	FILE = "note.txt";
    }
    
    boolean isEmpty() {
    	return _notes.isEmpty();
    }

	List<Note> getRecords(String file) {
		_notes.clear();
		ArrayList<String[]> data = read(file,";",1);
		for(String[] note : data)
			_notes.add(new Note(note[0]));
		return _notes;
	}
	void setNote(Note note) {
		_notes.set(0, note);
	}
	
	void addNote(Note note) {
		_notes.add(note);
	}
	
	void write(String file) {
		ArrayList<Writable> data = new ArrayList<Writable>();
		for(Note not : _notes)
			data.add(not);
		super.write(file, data);
	}
	
	public static void main(String[] args) {
    	LinkedList<Note> notes = new LinkedList<Note>();
    	FileModel fileModel = new FileModel();
    	ArrayList<String[]> data = fileModel.read("note.txt", ";", 1);
    	
    	for(int i=0; i<data.size(); i++) {
    		notes.add(new Note(data.get(i)[0]));
    	}
    	
    	for(Note not : notes)
    		System.out.println(not);
    	
    	ArrayList<Writable> dataToWrite = new ArrayList<Writable>();
    	dataToWrite.add(new Note("to jest tekst notatki"));
    	fileModel.write("note.txt", dataToWrite);
	}
}
