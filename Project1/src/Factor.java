
public class Factor implements CoreToken {

	private Scanner scanner;
	private Parser parser;
	private int constantInt;
	private Expr expr;
	private String id;
	private enum FactorType{ID, CONST, EXPR}
	private FactorType type;
	
	public Factor(Scanner scan, Parser parse){
		scanner = scan;
		parser = parse;
	}
	
	
	@Override
	public void parse() throws Exception {
		if(scanner.currentToken().equals("LPAREN")){
			type = FactorType.EXPR;
			scanner.nextToken();//Consume (
			expr = new Expr(scanner, parser);
			expr.parse();
			if(!scanner.currentToken().equals("RPAREN")){
				throw new Exception("ERROR: syntax error: expected \'(\' but got \'"
						+ scanner.currentToken() + "\'.");
			}
		}else if(scanner.currentToken().startsWith("ID[")){
			type = FactorType.ID;
			id = scanner.currentToken();
		}else if(scanner.currentToken().startsWith("CONST[")){
			type = FactorType.CONST;
			String constantString = scanner.currentToken();
			constantString = constantString.substring("CONST[".length(), constantString.length()-1);
			constantInt = Integer.parseInt(constantString);
		}else{
			throw new Exception("ERROR: syntax error: expected a constant, id, or expression but"
					+ "got \'" + scanner.currentToken() + "\'.");
		}
		scanner.nextToken();//consume ), or id, or const.
	}

	public int execute() throws Exception{
		int val = 0;
		switch(type){
		case ID:
			val = parser.getIDValue(id);
			break;
		case CONST:
			val = constantInt;
			break;
		case EXPR:
			val = expr.execute();
			break;
		default:
			break;
		}
		return val;
	}

	@Override
	public void printCode(StringBuilder builder, int indentation) {
		switch(type){
		case ID:
			builder.append(id.substring("ID[".length(), id.length()-1));
			break;
		case CONST:
			builder.append(constantInt);
			break;
		case EXPR:
			builder.append("(");
			expr.printCode(builder, indentation);
			builder.append(")");
			break;
		default:
			break;
		}
		
	}

}
