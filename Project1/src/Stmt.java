
public class Stmt implements CoreToken{

	private Scanner scanner;
	private Parser parser;
	private CoreToken stmt;
	
	public Stmt(Scanner scan, Parser parse){
		scanner = scan;
		parser = parse;
	}
	
	public void parse() throws Exception {
		if(scanner.currentToken().equals("INPUT")){
			stmt = new CoreInput(scanner,parser);
			stmt.parse();
		}else if(scanner.currentToken().equals("OUTPUT")){
			stmt = new CoreOutput(scanner,parser);
			stmt.parse();
		}else if(scanner.currentToken().equals("WHILE")){
			stmt = new CoreLoop(scanner, parser);
			stmt.parse();
		}else if(scanner.currentToken().equals("IF")){
			stmt = new CoreIf(scanner, parser);
			stmt.parse();
		}else if(scanner.currentToken().equals("CASE")){
			stmt = new CoreCase(scanner, parser);
			stmt.parse();
		}else if(scanner.currentToken().startsWith("ID[")){//assign
			stmt = new CoreAssign(scanner, parser);
			stmt.parse();
		}else{
			throw new Exception("ERROR: expected input, output, "
					+ "while, if, case or id but got \'" + scanner.currentToken() + "\'.");
		}
		
	}

	@Override
	public void printCode(StringBuilder builder, int indentation) {
		stmt.printCode(builder, indentation);
	}
	
	public int execute() throws Exception {
		return stmt.execute();
	}

}
