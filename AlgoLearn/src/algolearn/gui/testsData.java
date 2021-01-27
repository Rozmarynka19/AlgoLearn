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
    		5,
    		10,
    		5,
    		5,
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
    			{false, true, false, false},
    			{true, false, false, true},
    			{false, false, true, false},
    			{false, false, false, true},
    			{false, false, false, true}
    		},
    		//9
    		{
    			{true, false, true, false},
    			{false, true, false, false},
    			{true, false, false, false},
    			{false, true, true, false},
    			{false, false, false, true},
    			{true, false, false, false},
    			{false, true, false, false},
    			{false, false, true, false},
    			{true, false, false, false},
    			{false, true, false, false}
    		},
    		//10
    		{
    			{true, false, false, false},
    			{false, false, true, false},
    			{true, true, true, false},
    			{false, false, false, true},
    			{false, true, false, false},
    		},
    		//11
    		{
    			{true, false, false, false},
    			{false, true, true, false},
    			{false, true, true, true},
    			{true, false, false, false},
    			{false, false, true, false},
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
    			"tests/SkipList/question1.html",
    			"tests/SkipList/question2.html",
    			"tests/SkipList/question3.html",
    			"tests/SkipList/question4.html",
    			"tests/SkipList/question5.html"
    		},
    		//9
    		{
    			"tests/BST/question1.html",
    			"tests/BST/question2.html",
    			"tests/BST/question3.html",
    			"tests/BST/question4.html",
    			"tests/BST/question5.html",
    			"tests/BST/question6.html",
    			"tests/BST/question7.html",
    			"tests/BST/question8.html",
    			"tests/BST/question9.html",
    			"tests/BST/question10.html"
    		},
    		//10
    		{
    			"tests/HashTable/question1.html",
    			"tests/HashTable/question2.html",
    			"tests/HashTable/question3.html",
    			"tests/HashTable/question4.html",
    			"tests/HashTable/question5.html"
    		},
    		//11
    		{
    			"tests/FloydWarshall/question1.html",
    			"tests/FloydWarshall/question2.html",
    			"tests/FloydWarshall/question3.html",
    			"tests/FloydWarshall/question4.html",
    			"tests/FloydWarshall/question5.html"
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
