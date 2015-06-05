package notes.calculator;

import java.util.Stack;

/*
 * ONP
 */
class Mathematic {
	//stack used to calculate the result
	private Stack<Double> calculate = new Stack<Double>();
	//stack used to translate infix notation to postfix notation (onp)
	private Stack<String> operand = new Stack<String>();
	
	Mathematic() {}
	
	/*
	 * Format entry string
	 */
	private String formatEntryString(String operation) {
		char[] oper = operation.toCharArray();
		StringBuilder formatBuilder = new StringBuilder();
		
		if(oper[0]=='+' || oper[0]=='-' || Character.isDigit(oper[0])) formatBuilder.append(oper[0]);
		
		for(int i=1; i<oper.length; i++) {
			if(Character.isDigit(oper[i])) formatBuilder.append(oper[i]);
			else if(oper[i] == '.') formatBuilder.append(oper[i]);
			else if(oper[i]=='+' || oper[i]=='-' || oper[i]=='*' || oper[i]=='/') {
				formatBuilder.append(" " + oper[i] + " ");
				if(oper[i+1]=='-') {
					formatBuilder.append(oper[i+1]);
					i++;
				}
			}
		}
		
		return formatBuilder.toString();
	}
	
	/*
	 * Use format String to create String with operation in postfix notation (ONP)
	 */
	private String createONP(String operation) {
		String entry = formatEntryString(operation);
		String[] onpArr = entry.split(" ");
		StringBuilder onpBuilder = new StringBuilder();
		
		for(int i=0; i<onpArr.length; i++) {
			if(onpArr[i].matches("[-]*[0-9.]+")) onpBuilder.append(onpArr[i] + " ");
			if(onpArr[i].matches("\\+|-|\\*|/")) {
				if(onpArr[i].matches("\\+|-")) {
					if(!operand.isEmpty()) onpBuilder.append(operand.pop()+" ");
					operand.push(onpArr[i]);
				} else if(onpArr[i].matches("\\*|/")) {
					operand.push(onpArr[i]);
				}
			}
		}
		
		while(!operand.isEmpty()) onpBuilder.append(operand.pop()+" ");

		return onpBuilder.toString();
	}
	
	private boolean notAllowedChar(String operation) {
		String condition = "+-/*.";
		Character lastChar = operation.charAt(operation.length()-1);
		String endChar = lastChar.toString();
		if(condition.contains(endChar)) 
			return true;
		else 
			return false;
	}
	

	/*
	 * Calculate ONP and return the result
	 */
	Double run(String operation) {
		//if the last char is +-/* don't calculate
		if(notAllowedChar(operation)) return null;		
			
		String[] onp = createONP(operation).split(" ");
		
		for(int i=0; i<onp.length; i++) {
			if(onp[i].matches("[-]*[0-9.]+")) {
				calculate.push(Double.parseDouble(onp[i]));
			}
			if(onp[i].matches("\\*|/|\\+|-")) {
				Double b = calculate.pop();
				Double a = calculate.pop();
				if(onp[i].equals("+")) calculate.push(a+b);
				else if(onp[i].equals("-")) calculate.push(a-b);
				else if(onp[i].equals("*")) calculate.push(a*b);
				else if(onp[i].equals("/")) calculate.push(a/b);
			}
		}
		
		return calculate.pop();
	}
	
	Double radic(String operation) {
		String condition = "+-/*.";
		if(condition.contains(operation))
			return null;

		return Math.sqrt(Double.parseDouble(operation));
	}
	
	
	public static void main(String[] args) {
		Mathematic math = new Mathematic();
		System.out.println(math.run("-3*22+5-"));
	}
}

