import java.util.HashMap;

public class Parser {
	
	private Scanner scanner;
	private Prog prog;
	private HashMap<String,Integer> idTable = new HashMap<String,Integer>();
	
	public Parser(Scanner scan) throws Exception{
		scanner = scan;
		this.createParseTree();
	}

	private void createParseTree() throws Exception {
		if(scanner.nextToken().equals("PROGRAM")){
			prog = new Prog(scanner,this);
			prog.parse();
		}else{
			throw new Exception("ERROR: Syntax error, expected: Program");
		}
		
	}
	
	public void execute() throws Exception{
		prog.execute();
	}
	
	public void printCode(){
		StringBuilder builder = new StringBuilder();
		prog.printCode(builder, 0);
		System.out.print(builder.toString());
	}
	/**
	 * To be used when reading input data from the scanner. Only Sets value if
	 * the id has already been declared.
	 * 
	 * @param id Stirng ID/variable name
	 * @param val int value associated with the id
	 * @return true if id has been declared.
	 */
	public boolean setIDValue(String id, int val){
		boolean returnFlag = false;
		if(idTable.containsKey(id)){
			returnFlag = true;
			idTable.replace(id, val);
		}
		return returnFlag;
	}
	
	/**
	 * Declares an id.
	 * 
	 * @param id String id/variable name
	 * @return true if id hasn't already been declared, false otherwise.
	 */
	public boolean declareID(String id){
		boolean flag = true;
		if(idTable.containsKey(id)){
			flag = false;
		}
		idTable.put(id, null);
		return flag;
	}
	
	
	public int getIDValue(String id) throws Exception{
		Integer i = idTable.get(id);
		if(i == null)
			throw new Exception("ERROR: "+ id+ " does not have a value");
		return idTable.get(id);
	}
	
	public String getSpaces(int indentation){
		StringBuilder spaces = new StringBuilder();
		for(int i = 0; i < indentation; i++){
			spaces.append("  ");
		}
		return spaces.toString();
	}
}
