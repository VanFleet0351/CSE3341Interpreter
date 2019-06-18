
public class StmtSeq implements CoreToken{
	private Scanner scanner;
	private	Parser parser;
	private Stmt stmt;
	private StmtSeq stmtSeq;
	
	
	public StmtSeq(Scanner scan, Parser parse){
		scanner = scan;
		parser = parse;
	}
	
	public void parse() throws Exception{
		stmt = new Stmt(scanner, parser);
		stmt.parse();
		if(!scanner.currentToken().equals("END") && !scanner.currentToken().equals("ENDWHILE")
				&& !scanner.currentToken().equals("ENDIF") && !scanner.currentToken().equals("ELSE")){
			stmtSeq = new StmtSeq(scanner, parser);
			stmtSeq.parse();
		}
	}

	@Override
	public void printCode(StringBuilder builder, int indentation) {
		stmt.printCode(builder, indentation);
		if(stmtSeq != null)
			stmtSeq.printCode(builder, indentation);
	}

	@Override
	public int execute() throws Exception {
		stmt.execute();
		if(stmtSeq != null)
			stmtSeq.execute();
		return 0;
	}
}
