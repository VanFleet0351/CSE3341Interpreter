import java.util.ArrayList;

public class IDList implements CoreToken{

	private Scanner scanner;
	private	Parser parser;
	private ArrayList<String> idList = new ArrayList<String>();
	
	public IDList(Scanner scan, Parser parse){
		scanner = scan;
		parser = parse;
	}
	
	@Override
	public void parse() throws Exception {
		while(!scanner.currentToken().equals("SEMICOLON")){
			if(!scanner.currentToken().startsWith("ID[")){
				throw new Exception("ERROR: syntax error: expected and id");
			}
			if(parser.declareID(scanner.currentToken())){
				throw new Exception("ERROR: "+ scanner.currentToken()+" hasnt been declared");
			}
			idList.add(scanner.currentToken());
			scanner.nextToken();
			if(scanner.currentToken().equals("COMMA")){
				scanner.nextToken();
			}
		}
	}
	
	public void parseDeclare() throws Exception{
		while(!scanner.currentToken().equals("SEMICOLON")){
			if(!scanner.currentToken().startsWith("ID[")){
				throw new Exception("ERROR: syntax error: expected and id");
			}
			if(idList.contains(scanner.currentToken())){
				throw new Exception("ERROR: duplicate id declaration of \"" +
						scanner.currentToken() + "\".");
			}
			parser.declareID(scanner.currentToken());
			idList.add(scanner.currentToken());
			scanner.nextToken();
			if(scanner.currentToken().equals("COMMA")){
				scanner.nextToken();
			}
		}
	}

	@Override
	public void printCode(StringBuilder builder, int indentation) {
		//String constantString = scanner.currentToken();
		//constantString = constantString.substring("CONST[".length(), constantString.length()-1);
		//constantInt = Integer.parseInt(constantString);
		for(String id: idList){
			builder.append(id.substring("ID[".length(), id.length()-1));
			builder.append(",");
		}
		builder.deleteCharAt(builder.length() -1);//delete last comma
		
	}

	@Override
	public int execute() {
		return 0;
	}
	
	public String[] getIDs(){
		String[] ids = new String[1];
		return idList.toArray(ids);
	}

	
}
