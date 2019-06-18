
public class Decl implements CoreToken{
	private Scanner scanner;
	private	Parser parser;
	private IDList idList;
	
	public Decl(Scanner scan, Parser parse){
		scanner = scan;
		parser = parse;
	}
	
	public void parse() throws Exception{
		if(!scanner.currentToken().equals("INT")){
			throw new Exception("ERROR: syntax error: expected int");
		}
		scanner.nextToken();//Consume INT
		idList = new IDList(scanner, parser);
		idList.parseDeclare();
		if(!scanner.currentToken().equals("SEMICOLON")){
			throw new Exception("ERROR: syntax error: expected \';\' but got \'"
					+scanner.currentToken() + "\'.");
		}
		scanner.nextToken();
	}

	@Override
	public void printCode(StringBuilder builder, int indentation) {
		String spacing = parser.getSpaces(indentation);
		builder.append(spacing);
		builder.append("int ");
		idList.printCode(builder, indentation);
		builder.append(";\n");
	}

	@Override
	public int execute() {
		return idList.execute();
	}
	
}
