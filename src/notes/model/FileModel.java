package notes.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/*
 * Model read data from the file
 * and stored it in linked list also
 * save data to file
 * @author Peter Skurski
 */
class FileModel {
    //path to file with contacts
    private static final String FILE = "data.txt";
    private static Scanner __scanner; //input object
    private static PrintWriter __writer; //output object
    private static List<Person> __persons = new LinkedList<Person>(); //linked list with Persons


    FileModel() {}

    /*
     * Read data from file and insert it into linked list
     */    
	private static void readFile(String file) {	
		if(file == null) file = FILE;
		
		try {
			File f = new File(file);
			f.createNewFile();
			__scanner = new Scanner(f);
			__scanner.useDelimiter(";");
	
			while(__scanner.hasNextLine()) {
				Person person = new Person(__scanner.next(), __scanner.next(), __scanner.next(),
                		__scanner.next(), __scanner.next());
				__persons.add(person);
			}
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();	
		} finally {
			__scanner.close();
		}
	}

    /*
     * Write linked list to output file
     */
    private static void writeFile(String file) {
    	if(file == null) file = FILE;
    	
        try {
        	__writer = new PrintWriter(file);
            for(Person per: __persons) {
            	__writer.println(per.toFile());
            }

        } catch(IOException e) {
            e.printStackTrace();
        } finally {
        	__writer.close();
        }
    }

	static List<Person> getRecords(String file) {
		__persons.clear();
		readFile(file);
		return __persons;
	}
	
    static void write(String file) {
    	writeFile(file);
    }
	
	static void setRecord(Object value, int row, int col) {
		__persons.get(row).setPersonValue(col, value);
	}

	static void removeRecord(int row) {
		__persons.remove(row);
	}
	
	static void addRecord(Person record) {
		__persons.add(record);
	}
}
