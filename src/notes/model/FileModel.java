package notes.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

class FileModel {
    String FILE = null; //path to file
    private Scanner __scanner; //input object
    private PrintWriter __writer; //output object

    FileModel() {
    	FILE = "data.txt";
    }

    /*
     * Read data from file
     */    
	private ArrayList<String[]> readFile(String file, String delimeter, int col) {	
		if(file == null) file = FILE;
		ArrayList<String[]> data = new ArrayList<String[]>();
		
		try {
			File f = new File(file);
			f.createNewFile();
			__scanner = new Scanner(f);
			__scanner.useDelimiter(delimeter);
	
			while(__scanner.hasNextLine()) {
				String[] record = new String[col];
				for(int i=0; i<col; i++) 
					record[i] = __scanner.next();
				data.add(record);
			}

		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();	
		} finally {
			__scanner.close();
		}
		
		return data;
	}

    /*
     * Write linked list to output file
     */
    private void writeFile(String file, ArrayList<Writable> data) {
    	if(file == null) file = FILE;
    	
        try {
        	__writer = new PrintWriter(file);
        	for(Writable rec : data)
        		__writer.println(rec.toFile());

        } catch(IOException e) {
            e.printStackTrace();
        } finally {
        	__writer.close();
        }
    }
	
    ArrayList<String[]> read(String file, String delimeter, int col) {
    	return readFile(file, delimeter, col);
    }
    
    void write(String file, ArrayList<Writable> data) {
    	writeFile(file, data);
    }
    
    public static void main(String[] args) {
    	LinkedList<Person> persons = new LinkedList<Person>();
    	FileModel fileModel = new FileModel();
    	ArrayList<String[]> data = fileModel.read("data.txt", ";", 5);
    	
    	for(int i=0; i<data.size(); i++) {
    		persons.add(new Person(data.get(i)[0],data.get(i)[1],data.get(i)[2],data.get(i)[3],data.get(i)[4] ));
    	}
    	
    	for(Person per : persons)
    		System.out.println(per);
    	
    	ArrayList<Writable> dataToWrite = new ArrayList<Writable>();
    	dataToWrite.add(new Person("test","test","44", "test@ha.pl", "453245"));
    	fileModel.write("data.txt", dataToWrite);
    }

}
