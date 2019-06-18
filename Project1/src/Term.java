
public class Term implements CoreToken {

	private Scanner scanner;
	private Parser parser;
	private Factor factor;
	private Term term;
	private enum Operation{ MULT, NOP}
	private Operation op;
	
	public Term(Scanner scan, Parser parse){
		scanner = scan;
		parser = parse;
	}
	
	@Override
	public void parse() throws Exception {
		factor = new Factor(scanner, parser);
		factor.parse();
		if(scanner.currentToken().equals("MULT")){
			op = Operation.MULT;
			term = new Term(scanner, parser);
			term.parse();
		}else{
			op = Operation.NOP;
		}
	}
	
	public int execute() throws Exception{
		int val = factor.execute();
		if(op.equals(Operation.MULT))
			val = val * term.execute();
		return val;
	}

	@Override
	public void printCode(StringBuilder builder, int indentation) {
		factor.printCode(builder, indentation);
		if(op.equals(Operation.MULT)){
			builder.append("*");
			term.printCode(builder, indentation);
		}
	}

}
