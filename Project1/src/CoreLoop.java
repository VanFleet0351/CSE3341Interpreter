
public class CoreLoop implements CoreToken {

	private Scanner scanner;
	private Parser parser;
	private Cond cond;
	private StmtSeq stmtSeq;
	
	public CoreLoop(Scanner scan, Parser parse){
		scanner = scan;
		parser = parse;
	}
	
	
	@Override
	public void parse() throws Exception {
		scanner.nextToken();//Consume WHILE
		cond = new Cond(scanner, parser);
		cond.parse();
		if(!scanner.currentToken().equals("BEGIN")){
			throw new Exception("ERROR: syntax error: expected begin but got \'"
					+ scanner.currentToken() + "\'.");
		}
		scanner.nextToken();//Consume BEGIN
		stmtSeq = new StmtSeq(scanner, parser);
		stmtSeq.parse();
		if(!scanner.currentToken().equals("ENDWHILE")){
			throw new Exception("ERROR: syntax error: expected endwhile but got \'"
					+ scanner.currentToken() + "\'.");
		}
		scanner.nextToken();//Consume ENDWHILE
		if(!scanner.currentToken().equals("SEMICOLON")){
			throw new Exception("ERROR: syntax error: expected \';\' but"
					+ scanner.currentToken() + "\'.");
		}
		scanner.nextToken();//Consume ;
	}


	@Override
	public void printCode(StringBuilder builder, int indentation) {
		String spacing = parser.getSpaces(indentation);
		builder.append(spacing);
		builder.append("while ");
		cond.printCode(builder, indentation);
		builder.append(" begin\n");
		stmtSeq.printCode(builder, indentation + 1);
		builder.append(spacing);
		builder.append("endwhile;\n");
	}


	@Override
	public int execute() throws Exception {
		while(cond.execute() > 0){
			stmtSeq.execute();
		}
		return 0;
	}

}
