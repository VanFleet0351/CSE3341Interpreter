
public class CoreAssign implements CoreToken {

	private Scanner scanner;
	private Parser parser;
	private String id;
	private Expr expr;
	
	public CoreAssign(Scanner scan, Parser parse){
		scanner = scan;
		parser = parse;
	}
	
	@Override
	public void parse() throws Exception {
		id = scanner.currentToken();
		if(parser.declareID(id)){
			throw new Exception("ERROR: id \'"+id+"\' hasn't been declared.");
		}
		scanner.nextToken();//consume id
		if(!scanner.currentToken().equals("ASSIGN")){
			throw new Exception("ERROR: syntax error: expected := but got \'" + 
		scanner.currentToken() + "\'.");
		}
		scanner.nextToken();//Consume ASSIGNs
		expr = new Expr(scanner, parser);
		expr.parse();
		if(!scanner.currentToken().equals("SEMICOLON")){
			throw new Exception("ERROR: syntax error: expected ; but got \'" + 
		scanner.currentToken() + "\'.");
		}
		scanner.nextToken();//consume ;
	}

	@Override
	public void printCode(StringBuilder builder, int indentation) {
		builder.append(parser.getSpaces(indentation));
		builder.append(id.substring("ID[".length(), id.length()-1));
		builder.append(":=");
		expr.printCode(builder, indentation);
		builder.append(";\n");
	}

	@Override
	public int execute() throws Exception {
		int val = expr.execute();
		if(!parser.setIDValue(id, val)){
			throw new Exception("ERROR: id \'"+id+"\' hasn't been declared.");
		}
		return 0;
	}
	
}
