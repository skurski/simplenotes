package notes.model;

import java.util.LinkedList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class CsvTableModel extends AbstractTableModel {
	private String[] columnNames = {"Imie", "Nazwisko", "Wiek", "Telefon", "Email"};
	private List<Person> data = new LinkedList<Person>();
	private PersonData perData = new PersonData();
	
	public CsvTableModel() {
		data = perData.getRecords(null);
	}
	
	public void getData(String file) {
		for(int i=0; i<data.size()-1; i++) fireTableRowsDeleted(i,i);
		data.clear();
		data = perData.getRecords(file);
		fireTableDataChanged();
	}
	
	public int getColumnCount() {
		return columnNames.length;
	}
	
	public int getRowCount() {
		return data.size();
	}
	
	public String getColumnName(int col) {
		return columnNames[col];
	}
	
	public Object getValueAt(int row, int col) {
		return data.get(row).getPersonValue(col);
	}
	
    public void setValueAt(Object value, int row, int col) {
    	Person toChange = data.get(row);
    	toChange.setPersonValue(col, value);
    	data.set(row, toChange);
    	perData.setRecord(value, row, col);
        fireTableCellUpdated(row, col);
    }
	
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    public boolean isCellEditable(int row, int col) {
        return true;
    }
    
    public void removeRow(int row) {
    	if(row >= 0 ) {
	    	data.remove(row);
	        fireTableRowsDeleted(row,row);
    	}
    }
    
    public void addRow(Person record) {
    	data.add(record);
        fireTableDataChanged();
//    	fireTableRowsInserted(data.size(), data.size());
    }
    
    public void saveData(String file) {
    	perData.write(file);
    }
}
