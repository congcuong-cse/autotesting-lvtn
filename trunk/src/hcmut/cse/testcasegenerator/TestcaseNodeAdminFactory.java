package hcmut.cse.testcasegenerator;

/**
 * Factory for the node administrator
 * 
 * @author Aldi Alimucaj
 * 
 */
public class TestcaseNodeAdminFactory {
	private static TestcaseNodeAdmin nodeAdmin = new TestcaseNodeAdmin();

	private TestcaseNodeAdminFactory() {
	}

	public static TestcaseNodeAdmin getInstance() {
		return nodeAdmin;
	}

}
