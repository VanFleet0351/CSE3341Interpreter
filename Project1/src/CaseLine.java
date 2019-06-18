import java.util.ArrayList;

public class CaseLine implements CoreToken {

	private Scanner scanner;
	private Parser parser;
	private ArrayList<Integer> constList = new ArrayList<Integer>();
	private Expr expr;
	private CaseLineFollow caseLineFollow;
	
	public CaseLine(Scanner scan, Parser parse){
		scanner = scan;
		parser = parse;
	}
	
	@Override
	public void parse() throws Exception {
		if(!scanner.currentToken().startsWith("CONST[")){
			throw new Exception("ERROR: syntax error: expected a constant but got \'"
					+scanner.currentToken()+ "\'.");
		}
		while(scanner.currentToken().startsWith("CONST[")){
			String constantString = scanner.currentToken();
			constantString = constantString.substring("CONST[".length(), constantString.length()-1);
			constList.add(Integer.parseInt(constantString));
			scanner.nextToken();//consume CONST
			if(scanner.currentToken().startsWith("COMMA")){
				scanner.nextToken();// consume COMMA
			}
		}
		if(!scanner.currentToken().equals("COLON")){
			throw new Exception("ERROR: syntax error: expected : but got \'"
					+scanner.currentToken()+ "\'.");
		}
		scanner.nextToken();//Consume colon
		expr = new Expr(scanner, parser);
		expr.parse();
		if(scanner.currentToken().equals("BAR")){
			caseLineFollow = new CaseLineFollow(scanner, parser);
			caseLineFollow.parse();
		}
	}

	@Override
	public void printCode(StringBuilder builder, int indentation) {
		for(Integer num: constList){
			builder.append(num);
			builder.append(",");
		}
		builder.deleteCharAt(builder.length() -1);//delete last comma
		builder.append(" : ");
		expr.printCode(builder, 0);
		if(caseLineFollow != null){
			caseLineFollow.printCode(builder, indentation);
		}
	}

	@Override
	public int execute() throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public int execute(String id) throws Exception{
		int assigned = -1;
		int idVal = parser.getIDValue(id);
		if(constList.contains(idVal)){
			parser.setIDValue(id, expr.execute());
			assigned = 1;
		}else if(caseLineFollow != null){
			assigned = caseLineFollow.execute(id);
		}
		return assigned;
		
	}

}
