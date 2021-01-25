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
    protected static int questionsCount[] = {
    		2,
    		10,
    		10,
    		10,
    		10,
    		10,
    		10,
    		10,
    		10,
    		10,
    		10,
    		10,
    		10,
    		10,
    };
    
    protected static boolean [][][] correctAnswers = {
    		{	
    			{true, false, false, false},
    			{false, false, false, true},
    		},
    };
    
    protected static String [][] questionsHTML = {
    		{	
    			"tests/binary_heap/zadanie1.html",
    			"html/RBT.html",
    		}
    };
}
