
public class CoreCase implements CoreToken {

	private Scanner scanner;
	private Parser parser;
	private CaseLine caseLine;
	private Expr expr;
	private String id;
	
	public CoreCase(Scanner scan, Parser parse){
		scanner = scan;
		parser = parse;
	}
	
	@Override
	public void parse() throws Exception {
		scanner.nextToken();//Consume CASE
		if(!scanner.currentToken().startsWith("ID[")){
			throw new Exception("ERROR: syntax error: expected an id");
		}
		if(parser.declareID(scanner.currentToken())){
			throw new Exception("ERROR: \"" +
		scanner.currentToken() + "\" hasn't been declared.");
		}
		id = scanner.currentToken();
		scanner.nextToken();//Consume ID
		if(!scanner.currentToken().equals("OF")){
			throw new Exception("ERROR: syntax error: expected of but got \'"
					+scanner.currentToken()+ "\'.");
		}
		scanner.nextToken();//consume OF
		caseLine = new CaseLine(scanner, parser);
		caseLine.parse();
		if(!scanner.currentToken().equals("ELSE")){
			throw new Exception("ERROR: syntax error: expected else but got \'"
					+scanner.currentToken()+ "\'.");
		}
		scanner.nextToken();//Consume ELSE
		expr = new Expr(scanner, parser);
		expr.parse();
		if(!scanner.currentToken().equals("END")){
			throw new Exception("ERROR: syntax error: expected end but got \'"
					+scanner.currentToken()+ "\'.");
		}
		scanner.nextToken();//Consume END
		if(!scanner.currentToken().equals("SEMICOLON")){
			throw new Exception("ERROR: syntax error: expected ; but got \'"
					+scanner.currentToken()+ "\'.");
		}
		scanner.nextToken();//Consume ;
	}

	@Override
	public void printCode(StringBuilder builder, int indentation) {
		String spacing = parser.getSpaces(indentation);
		builder.append(spacing);
		builder.append("case ");
		builder.append(id.substring("ID[".length(), id.length()-1));
		builder.append(" of \n");
		builder.append(parser.getSpaces(indentation+1));
		caseLine.printCode(builder, indentation+1);
		builder.append("\n");
		builder.append(spacing);
		builder.append("else ");
		expr.printCode(builder, indentation+1);
		builder.append("\n" +spacing);
		builder.append("end;\n");
	}

	@Override
	public int execute() throws Exception {
		if(caseLine.execute(id) < 0){
			parser.setIDValue(id, expr.execute());
		}
		return 0;
	}

}
