
public class CaseLineFollow implements CoreToken {

	private Scanner scanner;
	private Parser parser;
	private CaseLine caseLine;
	
	public CaseLineFollow(Scanner scan, Parser parse){
		scanner = scan;
		parser = parse;
	}
	
	@Override
	public void parse() throws Exception {
		scanner.nextToken();//Consume BAR
		caseLine = new CaseLine(scanner, parser);
		caseLine.parse();
	}

	@Override
	public void printCode(StringBuilder builder, int indentation) {
		builder.append("\n");
		builder.append(parser.getSpaces(indentation));
		builder.append("|");
		caseLine.printCode(builder, indentation);
	}

	@Override
	public int execute() throws Exception {
		return 0;
	}

	public int execute(String id) throws Exception{
		return caseLine.execute(id);
	}
}
