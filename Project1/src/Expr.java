
public class Expr implements CoreToken {

	private Scanner scanner;
	private Parser parser;
	private Expr expr;
	private Term term;
	private enum Operation{ PLUS, MINUS, NOP}
	private Operation op;
	
	public Expr(Scanner scan, Parser parse){
		scanner = scan;
		parser = parse;
	}
	
	@Override
	public void parse() throws Exception {
		term = new Term(scanner, parser);
		term.parse();
		if(scanner.currentToken().equals("PLUS")){
			scanner.nextToken();//consume Plus
			op = Operation.PLUS;
			expr = new Expr(scanner, parser);
			expr.parse();
		}else if(scanner.currentToken().equals("MINUS")){
			scanner.nextToken();//consume minus
			op = Operation.MINUS;
			expr = new Expr(scanner, parser);
			expr.parse();
		}else{
			op = Operation.NOP;
		}

	}
	
	public int execute() throws Exception{
		int val;
		switch(op){
		case PLUS:
			val = term.execute() + expr.execute();
			break;
		case MINUS:
			val = term.execute() - expr.execute();
			break;
		default:
			val = term.execute();
			break;
		}
		return val;
	}
	
	@Override
	public void printCode(StringBuilder builder, int indentation) {
		term.printCode(builder, indentation);
		switch(op){
		case PLUS:
			builder.append("+");
			expr.printCode(builder, indentation);
			break;
		case MINUS:
			builder.append("-");
			expr.printCode(builder, indentation);
			break;
		default:
			break;
		}
	}

}
