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
    		5,
    		6,
    		10
    };
    
    protected static boolean [][][] correctAnswers = {
      		//1
    		{	
    			{true, false, false, false},
    			{false, false, false, true},
    		},
    		//2
    		{
    			
    		},
    		//3
    		{
    			
    		},
    		//4
    		{
    			
    		},
    		//5
    		{
    			
    		},
    		//6
    		{
    			
    		},
    		//7
    		{
    			
    		},
    		//8
    		{
    			
    		},
    		//9
    		{
    			
    		},
    		//10
    		{
    			
    		},
    		//11
    		{
    			
    		},
    		//12
    		{
    			{true, false, false, false},
    			{false, false, false, true},
    			{false, true, true, false},
    			{true, true, false, false},
    			{true, true, true, true},
//    			{false, false, false, false},
    		},
    		//13
    		{
    			{false, true, true, false},
    			{false, false, false, true},
    			{false, true, false, false},
    			{true, false, false, false},
    			{true, false, false, false},
    			{false, true, true, false}
    		},
    		//14
    		{
    			{true, false, true, false},
    			{true, false, false, false},
    			{false, false, false, true},
    			{false, false, true, false},
    			{false, true, false, false},
    			{true, false, true, true},
    			{false, true, false, true},
    			{false, false, true, true},
    			{true, true, true, true},
    			{true, false, false, true}
    		}
    };
    
    protected static String [][] questionsHTML = {
    		//1
    		{	
    			"tests/binary_heap/zadanie1.html",
    			"html/RBT.html",
    		},
    		//2
    		{
    			
    		},
    		//3
    		{
    			
    		},
    		//4
    		{
    			
    		},
    		//5
    		{
    			
    		},
    		//6
    		{
    			
    		},
    		//7
    		{
    			
    		},
    		//8
    		{
    			
    		},
    		//9
    		{
    			
    		},
    		//10
    		{
    			
    		},
    		//11
    		{
    			
    		},
    		//12
    		{
    			"tests/UFKruskal/question1.html",
    			"tests/UFKruskal/question2.html",
    			"tests/UFKruskal/question3.html",
    			"tests/UFKruskal/question4.html",
    			"tests/UFKruskal/question5.html"
    		},
    		//13
    		{
    			"tests/GrahamScan/question1.html",
    			"tests/GrahamScan/question2.html",
    			"tests/GrahamScan/question3.html",
    			"tests/GrahamScan/question4.html",
    			"tests/GrahamScan/question5.html",
    			"tests/GrahamScan/question6.html"
    		},
    		//14
    		{
    			"tests/RBT/question1.html",
    			"tests/RBT/question2.html",
    			"tests/RBT/question3.html",
    			"tests/RBT/question4.html",
    			"tests/RBT/question5.html",
    			"tests/RBT/question6.html",
    			"tests/RBT/question7.html",
    			"tests/RBT/question8.html",
    			"tests/RBT/question9.html",
    			"tests/RBT/question10.html"
    		}
    };
}
