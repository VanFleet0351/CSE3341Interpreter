
public class CoreIf implements CoreToken {

	private Scanner scanner;
	private Parser parser;
	private Cond cond;
	private StmtSeq thenStmtSeq;
	private StmtSeq elseStmtSeq;
	
	public CoreIf(Scanner scan, Parser parse){
		scanner = scan;
		parser = parse;
	}
	
	@Override
	public void parse() throws Exception {
		scanner.nextToken();//Consume IF
		cond = new Cond(scanner, parser);
		cond.parse();
		if(!scanner.currentToken().equals("THEN")){
			throw new Exception("ERROR: syntax error: expected then but got \'"
					+scanner.currentToken()+ "\'.");
		}
		scanner.nextToken();//Consume THEN
		thenStmtSeq = new StmtSeq(scanner, parser);
		thenStmtSeq.parse();
		if(scanner.currentToken().equals("ELSE")){
			scanner.nextToken();//Consume ELSE
			elseStmtSeq = new StmtSeq(scanner,parser);
			elseStmtSeq.parse();
		}
		if(!scanner.currentToken().equals("ENDIF")){
			throw new Exception("ERROR: syntax error: expected ednif but got \'"
					+ scanner.currentToken()+ "\'.");
		}
		scanner.nextToken();//Consume ENDIF
		if(!scanner.currentToken().equals("SEMICOLON")){
			throw new Exception("ERROR: syntax error: expected ; but got \'"
					+ scanner.currentToken()+"\'.");
		}
		scanner.nextToken();//Consume ;
	}

	@Override
	public void printCode(StringBuilder builder, int indentation) {
		String spacing = parser.getSpaces(indentation);
		builder.append(spacing);
		builder.append("if ");
		cond.printCode(builder, indentation);
		builder.append(" then\n");
		thenStmtSeq.printCode(builder, indentation+1);
		if(elseStmtSeq != null){
			builder.append(spacing);
			builder.append("else\n");
			elseStmtSeq.printCode(builder, indentation+1);
		}
		builder.append(spacing);
		builder.append("endif;\n");
	}

	@Override
	public int execute() throws Exception {
		if(cond.execute() > 0){
			thenStmtSeq.execute();
		}else if(elseStmtSeq != null){
			elseStmtSeq.execute();
		}
		return 0;
	}

}
