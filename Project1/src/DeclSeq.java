
public class DeclSeq implements CoreToken{
	private Scanner scanner;
	private	Parser parser;
	private Decl decl;
	private DeclSeq declSeq;

	public DeclSeq(Scanner scan, Parser parse){
		scanner = scan;
		parser = parse;
	}
	
	public void parse() throws Exception {
		decl = new Decl(scanner, parser);
		decl.parse();
		if(scanner.currentToken().equals("INT")){
			declSeq = new DeclSeq(scanner, parser);
			declSeq.parse();
		}
	}

	@Override
	public void printCode(StringBuilder builder, int indentation) {
		decl.printCode(builder, indentation);
		if(declSeq != null){
			declSeq.printCode(builder, indentation);
		}
	}

	@Override
	public int execute() {
		decl.execute();
		if(declSeq != null)
			declSeq.execute();
		return 0;
		
	}
}
