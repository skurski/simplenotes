package notes.model;

/*
 * Every instance of class Person
 * represents one contact in phone book
 * @author Peter Skurski
 */
public class Person implements Comparable<Person>, Writable {
    private String _firstName;
    private String _lastName;
    private String _age;
    private String _email;
    private String _phone;

    public Person(String firstName, String lastName, String age, String phone, String email) {
    	_firstName = firstName.trim();
    	_lastName = lastName.trim();
    	_age = age.trim();
    	_phone = phone.trim();
    	_email = email.trim();
    }
    
    /*
     * Method use in CsvTableModel to map Person
     * object attribute to column number
     */
    String getPersonValue(int col) {
    	String value = null;
    	switch(col) {
    		case 0:
    			value = _firstName;
    			break;
    		case 1: 
    			value = _lastName;
    			break;
    		case 2: 
    			value = _age;
    			break;
    		case 3:
    			value = _email;
    			break;
    		case 4: 
    			value = _phone;
    			break;
    	}
    	return value;
    }
    
    void setPersonValue(int col, Object obj) {
    	switch(col) {
	    	case 0:
				_firstName = (String) obj;
				break;
			case 1: 
				_lastName = (String) obj;
				break;
			case 2: 
				_age = (String) obj;
				break;
			case 3:
				_email = (String) obj;
				break;
			case 4: 
				_phone = (String) obj;
				break;
    	}
    }
    
    public String toFile() {
    	return String.format(";"+_firstName+";"+_lastName+";"+_age+";"+
    				_phone+";"+_email);
    }

    public String toString() {
        return String.format(_firstName + " " + _lastName + " " + _age +" " +
                _phone + " " + _email);
    }
    
    public int compareTo(Person per) {
    	if(per == null) throw new NullPointerException();
        int ret = _lastName.compareTo(per._lastName);
        if (ret == 0) ret = _firstName.compareTo(per._firstName);
        if (ret == 0) ret = _email.compareTo(per._email);       
        return ret;
    }
}

