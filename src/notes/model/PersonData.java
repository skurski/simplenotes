package notes.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/*
 * Model read data from the file
 * and stored it in linked list also
 * save data to file
 * @author Peter Skurski
 */
class PersonData extends FileModel {
    private List<Person> __persons = new LinkedList<Person>(); //linked list with Persons

    PersonData() {}

	List<Person> getRecords(String file) {
		__persons.clear();
		ArrayList<String[]> data = read(file,";",5);
		for(String[] person : data)
			__persons.add(new Person(person[0], person[1], person[2], person[3], person[4]));
		return __persons;
	}
	
	void setRecord(Object value, int row, int col) {
		__persons.get(row).setPersonValue(col, value);
	}

	void removeRecord(int row) {
		__persons.remove(row);
	}
	
	void addRecord(Person record) {
		__persons.add(record);
	}
	
	void write(String file) {
		ArrayList<Writable> data = new ArrayList<Writable>();
		for(Person per : __persons)
			data.add(per);
		super.write(file, data);
	}
}
