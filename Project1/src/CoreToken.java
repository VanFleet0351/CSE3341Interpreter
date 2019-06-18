
public interface CoreToken {
	public void parse() throws Exception;
	public void printCode(StringBuilder builder, int indentation);
	public int execute() throws Exception;
}
