
public class Prog implements CoreToken{

	private Scanner scanner;
	private Parser parser;
	private DeclSeq declSeq;
	private StmtSeq stmtSeq;
	
	public Prog(Scanner scan, Parser parse){
		scanner = scan;
		parser = parse;
	}
	
	public void parse() throws Exception{
		scanner.nextToken();//consume PROGRAM
		declSeq = new DeclSeq(scanner, parser);
		declSeq.parse();
		if(!scanner.currentToken().equals("BEGIN")){
			throw new Exception("ERROR: Syntax error, expected: begin");
		}
		scanner.nextToken();
		stmtSeq = new StmtSeq(scanner,parser);
		stmtSeq.parse();
		if(!scanner.currentToken().equals("END")){
			throw new Exception("ERROR: Syntax error, expected: End but got"+
		scanner.currentToken());
		}
		if(!scanner.nextToken().equals("EOF")){
			throw new Exception("ERROR: file should not contain anything after \'end\'");
		}
	}

	@Override
	public void printCode(StringBuilder builder, int indentation) {
		String spacing = parser.getSpaces(indentation);
		builder.append(spacing);
		builder.append("program\n");
		declSeq.printCode(builder, indentation + 1);
		builder.append(spacing);
		builder.append("begin\n");
		stmtSeq.printCode(builder, indentation + 1);
		builder.append(spacing);
		builder.append("end\n");
		
	}

	
	public int execute() throws Exception {
		declSeq.execute();
		stmtSeq.execute();
		return 0;
	}
}
