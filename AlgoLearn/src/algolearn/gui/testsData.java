/**
 * 
 */
package algolearn.gui;

/**
 * @author Algolearn Team
 *
 */
public class testsData {

	private static testsData instance;
	
    public static testsData getInstance() {
        if (instance == null) {
            instance = new testsData();
        }
        return instance;
    }
    
    // Ilosc pytan w testach
    protected String questionsCount[] = {
    		"11",
    		"10",
    		"10",
    		"10",
    		"10",
    		"10",
    		"10",
    		"10",
    		"10",
    		"10",
    		"10",
    		"10",
    		"10",
    		"22",
    };
}
