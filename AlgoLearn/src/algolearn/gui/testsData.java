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
    		5, // Heap
    		5, // L1List
    		5, // L2List
    		4, // Bucket Sort
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
    		{	// heap
    			{true, false, false, false},
    			{false, false, false, true},
    			{true, false, false, true},
    			{true, false, false, false},
    			{false, false, true, false},
    		},
    		{	// L1List
    			{true, false, false, false},
    			{false, false, false, true},
    			{false, true, false, false},
    			{true, false, false, false},
    			{false, true, false, false},
    		},
    		{	// L2List
    			{false, true, false, false},
    			{false, false, true, false},
    			{false, true, false, false},
    			{false, true, true, false},
    			{false, false, false, true},
    		},
    		{	// Bucket sort
    			{true, false, false, false},
    			{true, false, true, false},
    			{false, false, true, false},
    			{false, false, false, true},
    		},
    };
    
    protected static String [][] questionsHTML = {
    		{	// heap
    			"tests/binary_heap/question1.html",
    			"tests/binary_heap/question2.html",
    			"tests/binary_heap/question3.html",
    			"tests/binary_heap/question4.html",
    			"tests/binary_heap/question5.html",
    		},
    		{	// L1List
    			"tests/L1List/question1.html",
    			"tests/L1List/question2.html",
    			"tests/L1List/question3.html",
    			"tests/L1List/question4.html",
    			"tests/L1List/question5.html",
    		},
    		{	// L2List
    			"tests/L2List/question1.html",
    			"tests/L2List/question2.html",
    			"tests/L2List/question3.html",
    			"tests/L2List/question4.html",
    			"tests/L2List/question5.html",
    		},
    		{	// Bucket Sort
    			"tests/bucket_sort/question1.html",
    			"tests/bucket_sort/question2.html",
    			"tests/bucket_sort/question3.html",
    			"tests/bucket_sort/question4.html",
    		},
    };
}
