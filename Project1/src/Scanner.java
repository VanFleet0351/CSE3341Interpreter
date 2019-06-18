import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Scanner {
	private ArrayList<String> tokens = new ArrayList<String>();
	private ArrayList<Integer> data = new ArrayList<Integer>();
	private String currentTok;
	private BufferedReader reader;
	private HashMap<String,String> tokenMap = new HashMap<String,String>();
	
	public Scanner(File codeFile, File dataFile) throws Exception{
		this.initialize();
		reader = new BufferedReader(new FileReader(codeFile));
		this.tokenizer();
		reader.close();
		reader = new BufferedReader(new FileReader(dataFile));
		this.getData();
		reader.close();
	}


	private void getData() throws Exception {
		StringBuilder dataBuilder = new StringBuilder();
		while(reader.ready()){
			char nextChar = (char) reader.read();
			if(Character.isDigit(nextChar) || nextChar == '-'){
				dataBuilder.append(nextChar);
				nextChar = (char) reader.read();
				while(Character.isDigit(nextChar)){
					dataBuilder.append(nextChar);
					nextChar = (char) reader.read();
				}
				data.add(Integer.parseInt(dataBuilder.toString()));
				dataBuilder = new StringBuilder();
			}
			if(!Character.isWhitespace(nextChar) && reader.ready()){
				throw new Exception("ERROR: Numbers should be sepparated with "
						+ "spaces and negative number should begin with a \"-\".");
			}
		}
	}


	private void tokenizer() throws Exception {
		StringBuilder builder = new StringBuilder();
		char nextChar = (char) reader.read();
		int lineCount = 1;
		while(reader.ready()){
			if(Character.isLetter(nextChar)){
				while(Character.isLetterOrDigit(nextChar)){
					builder.append(nextChar);
					nextChar = (char) reader.read();
				}
				tokens.add(getToken(builder.toString()));
				builder = new StringBuilder();
			}else if(Character.isDigit(nextChar)){
				builder.append("CONST[");
				while(Character.isDigit(nextChar)){
					builder.append(nextChar);
					nextChar = (char) reader.read();
				}
				builder.append("]");
				tokens.add(getToken(builder.toString()));
				builder = new StringBuilder();
			}else if(":|,;!=+*()<-".indexOf(nextChar) >= 0){
				builder.append(nextChar);
				if(nextChar == ':' || nextChar == '<'){
					nextChar = (char) reader.read();
					if(nextChar == '='){
						builder.append(nextChar);
						nextChar = (char) reader.read();
					}
				}else{
					nextChar = (char) reader.read();
				}
				tokens.add(getToken(builder.toString()));
				builder = new StringBuilder();
			}else if(Character.isWhitespace(nextChar)){
				if(nextChar == '\n')
					lineCount++;
				nextChar = (char) reader.read();
			}else{
				throw new Exception("ERROR: unsupported character \"" + nextChar + "\" was found on line " + lineCount);
			}
		}
	}

	private String getToken(String progString){
		String token;
		if(tokenMap.containsKey(progString)){
			token = tokenMap.get(progString);
		}else{
			if(!progString.contains("CONST[")){
				token = "ID["+progString+"]";
			}else{
				token = progString;
			}
		}
		return token;
	}
	public String nextToken(){
		if(!tokens.isEmpty()){
			currentTok = tokens.remove(0);
		}else{
			currentTok = "EOF";
		}
		
		return currentTok;
	}
	
	public int nextData() throws Exception{
		if(data.isEmpty()){
			throw new Exception("ERROR: No more data available.");
		}
		return data.remove(0);
	}
	
	
	public String currentToken(){
		return currentTok;
	}
	
	public String peekNextToken(){
		String peek;
		if(tokens.isEmpty()){
			peek = "EOF";
		}else{
			peek = tokens.get(0);
		}
		return peek;
	}
	
	private void initialize() {
		tokenMap.put("program", "PROGRAM");
		tokenMap.put("begin", "BEGIN");
		tokenMap.put("end", "END");
		tokenMap.put("int", "INT");
		tokenMap.put(";", "SEMICOLON");
		tokenMap.put(":=", "ASSIGN");
		tokenMap.put("input", "INPUT");
		tokenMap.put("output", "OUTPUT");
		tokenMap.put("if", "IF");
		tokenMap.put("then", "THEN");
		tokenMap.put("endif", "ENDIF");
		tokenMap.put("else", "ELSE");
		tokenMap.put("while", "WHILE");
		tokenMap.put("endwhile", "ENDWHILE");
		tokenMap.put("!", "NEGATE");
		tokenMap.put("(", "LPAREN");
		tokenMap.put(")", "RPAREN");
		tokenMap.put("or", "OR");
		tokenMap.put("=", "EQUAL");
		tokenMap.put("<", "LT");
		tokenMap.put("<=", "LTE");
		tokenMap.put("+", "PLUS");
		tokenMap.put("-", "MINUS");
		tokenMap.put("*", "MULT");
		tokenMap.put("case", "CASE");
		tokenMap.put(":", "COLON");
		tokenMap.put("|", "BAR");
		tokenMap.put(",", "COMMA");
		tokenMap.put("of", "OF");
	}
}
