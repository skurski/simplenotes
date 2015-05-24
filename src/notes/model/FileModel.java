package notes.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class FileModel {
	private final static String FILE = "data.txt";
	private Scanner __scanner = null;
	private PrintWriter __writer = null;
	private List<Object[]> _records = new ArrayList<Object[]>();

	private void readFile(String file) {	
		if(file == null) file = FILE;
		
		try {
			File f = new File(file);
			f.createNewFile();
			__scanner = new Scanner(f);
			__scanner.useDelimiter(";");
	
			while(__scanner.hasNextLine()) {
				_records.add(new Object[] {__scanner.next(),
											__scanner.next(),
											__scanner.next(),
											__scanner.next(),
											__scanner.next()});
			}
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();	
		} finally {
			__scanner.close();
		}
	}
	
    void writeFile(String file) {
    	if(file == null) file = FILE;
    	
        try {
        	__writer = new PrintWriter(file);
            for(Object[] rec: _records) {
            	for(Object obj: rec)
            		__writer.print(";" + obj);
//            	__writer.println();
            }

        } catch(IOException e) {
            e.printStackTrace();
        } finally {
        	__writer.close();
        }
    }
	
	public List<Object[]> getRecords(String file) {
		_records.clear();
		readFile(file);
		return _records;
	}
	
	public void setRecord(Object value, int row, int col) {
		_records.get(row)[col] = value;
	}
	
	public void removeRecord(int row) {
		_records.remove(row);
	}
	
	public void addRecord(Object[] record) {
		_records.add(record);
	}
}
