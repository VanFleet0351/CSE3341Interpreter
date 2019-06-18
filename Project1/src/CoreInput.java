
public class CoreInput implements CoreToken{
	private Scanner scanner;
	private Parser parser;
	private IDList idList;
	
	public CoreInput(Scanner scan, Parser parse){
		scanner = scan;
		parser = parse;
	}

	public void parse() throws Exception {
		scanner.nextToken();//Consume INPUT
		idList = new IDList(scanner, parser);
		idList.parse();
		if(!scanner.currentToken().equals("SEMICOLON")){
			throw new Exception("ERROR: syntax error: expected \';\'");
		}
		scanner.nextToken();
	}

	@Override
	public void printCode(StringBuilder builder, int indentation) {
		String spacing = parser.getSpaces(indentation);
		builder.append(spacing);
		builder.append("input ");
		idList.printCode(builder, indentation);
		builder.append(";\n");
	}

	@Override
	public int execute() throws Exception {
		String[] ids = idList.getIDs();
		for(String id: ids){
			int val = scanner.nextData();
			if(!parser.setIDValue(id, val)){
				throw new Exception("ERROR: " + id + " has not been declared");
			}
		}
		return 0;
	}
}
