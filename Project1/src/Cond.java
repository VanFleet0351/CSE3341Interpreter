
public class Cond implements CoreToken {

	private Scanner scanner;
	private Parser parser;
	private Cond cond;
	private Cmpr cmpr;
	private enum Operation{ OR, NEGATE, NOP}
	private Operation op;
	
	public Cond(Scanner scan, Parser parse){
		scanner = scan;
		parser = parse;
	}
	
	@Override
	public void parse() throws Exception {
		if(scanner.currentToken().equals("NEGATE")){
			if(!scanner.nextToken().equals("LPAREN")){
				throw new Exception("ERROR: syntax error: expected \'(\' but got \'"
						+ scanner.currentToken() + "\'.");
			}
			op = Operation.NEGATE;
			scanner.nextToken();//Consume (
			cond = new Cond(scanner, parser);
			cond.parse();
			if(!scanner.currentToken().equals("RPAREN")){
				throw new Exception("ERROR: syntax error: expected \')\' but got \'"
						+scanner.currentToken()+ "\'.");
			}
			scanner.nextToken();//Consume )
		}else{
			cmpr = new Cmpr(scanner, parser);
			cmpr.parse();
			if(scanner.currentToken().equals("OR")){
				op = Operation.OR;
				scanner.nextToken();//Consume OR
				cond = new Cond(scanner, parser);
				cond.parse();
			}else{
				op = Operation.NOP;
			}
		}
	}

	@Override
	public void printCode(StringBuilder builder, int indentation) {
		switch(op){
		case OR:
			cmpr.printCode(builder, indentation);
			builder.append("or");
			cond.printCode(builder, indentation);
			break;
		case NEGATE:
			builder.append("!(");
			cond.printCode(builder, indentation);
			builder.append(")");
			break;
		case NOP:
			cmpr.printCode(builder, indentation);
			break;
		default:
			break;
		}
		
	}

	@Override
	public int execute() throws Exception {
		int val = -1;
		switch(op){
		case OR:
			int cmprVal = cmpr.execute();
			int condVal = cond.execute();
			if(cmprVal > 0 || condVal > 0)
				val = 1;
			break;
		case NEGATE:
			val = cond.execute() * -1;
			break;
		case NOP:
			val = cmpr.execute();
			break;
		default:
			throw new Exception("ERROR: this should never be thrown");
		}
		return val;
	}
}
