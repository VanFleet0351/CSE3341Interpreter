import java.io.File;
/**
 * 
 * @author Kyle Van Fleet
 * @Date Jun 18, 2019
 */
public class Interpreter{

	public static void main(String[] args) {
		File codeFile = new File(args[0]);
		File dataFile = new File(args[1]);

		try {
			Scanner coreScanner = new Scanner(codeFile, dataFile);
			Parser coreParser = new Parser(coreScanner);
			coreParser.printCode();
			coreParser.execute();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
	}

}
