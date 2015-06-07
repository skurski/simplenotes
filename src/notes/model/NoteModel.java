package notes.model;

public class NoteModel {
	private NoteData noteData = new NoteData();
	
	public NoteModel() {}
	
	public Note getData(String file) {
		return noteData.getRecords(file).get(0);
	}

    public void saveData(String file, String text) {
    	if(noteData.isEmpty())
    		noteData.addNote(new Note(text));
    	else 
    		noteData.setNote(new Note(text));
    	noteData.write(file);
    }
}
