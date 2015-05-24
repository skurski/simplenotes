package notes.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class CsvTableModel extends AbstractTableModel {
	private String[] columnNames = {"Imie", "Nazwisko", "Wiek", "Email", "Telefon"};
	private List<Object[]> data = new ArrayList<Object[]>();
	FileModel _fileModel = new FileModel();
	
	public CsvTableModel() {
		data = _fileModel.getRecords(null);
	}
	
	public void getData(String file) {
		for(int i=0; i<data.size()-1; i++) fireTableRowsDeleted(i,i);
		data.clear();
		data = _fileModel.getRecords(file);
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
		return data.get(row)[col];
	}
	
    public void setValueAt(Object value, int row, int col) {
    	Object[] toChange = data.get(row);
    	toChange[col] = value;
    	data.set(row, toChange);
        _fileModel.setRecord(value, row, col);
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
    
    public void addRow(Object[] record) {
    	data.add(record);
        fireTableDataChanged();
//    	fireTableRowsInserted(data.size(), data.size());
    }
    
    public void saveData(String file) {
    	_fileModel.writeFile(file);
    }
}
