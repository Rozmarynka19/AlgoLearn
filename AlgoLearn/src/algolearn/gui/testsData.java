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
    		5,
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
    			{true, false, false, true},
    			{true, false, false, false},
    			{false, false, true, false},
    		},
    };
    
    protected static String [][] questionsHTML = {
    		{	
    			"tests/binary_heap/question1.html",
    			"tests/binary_heap/question2.html",
    			"tests/binary_heap/question3.html",
    			"tests/binary_heap/question4.html",
    			"tests/binary_heap/question5.html",
    		}
    };
}
