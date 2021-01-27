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
    
    protected static boolean [][][] correctAnswers = {
    		{	// heap
    			{true, false, false, false},
    			{false, false, false, true},
    			{true, false, false, true},
    			{true, false, false, false},
    			{false, false, true, false},
    		},
    		{ // L1List
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
    			{true, false, false, false},
    		},
    		{	// Counting sort
    			{true, true, false, true},
    			{true, false, false, false},
    			{true, false, false, false},
    			{false, false, false, true},
    			{true, true, true, true},
    		},
    		{	// Heap Sort
    			{true, false, false, false},
    			{false, false, true, false},
    			{false, true, false, false},
    			{false, false, false, true},
    			{true, false, false, true},
    		},
    		{	// FFT
    			{false, true, false, false},
    			{false, true, true, false},
    			{true, false, false, false},
    			{false, true, true, false},
    			{false, false, true, false},
    		},
			//SkipList
    		{
    			{false, true, false, false},
    			{true, false, false, true},
    			{false, false, true, false},
    			{false, false, false, true},
    			{false, false, false, true},
    		},
    		//BST
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
    			{false, true, false, false},
    		},
    		//HashTable
    		{
    			{true, false, false, false},
    			{false, false, true, false},
    			{true, true, true, false},
    			{false, false, false, true},
    			{false, true, false, false},
    		},
    		//FloydWarshall
    		{
    			{true, false, false, false},
    			{false, true, true, false},
    			{false, true, true, true},
    			{true, false, false, false},
    			{false, false, true, false},
    		},
    		//UFKruskal
    		{
    			{true, false, false, false},
    			{false, false, false, true},
    			{false, true, true, false},
    			{true, true, false, false},
    			{true, true, true, true},
//    			{false, false, false, false},
    		},
    		//GrahamScan
    		{
    			{false, true, true, false},
    			{false, false, false, true},
    			{false, true, false, false},
    			{true, false, false, false},
    			{true, false, false, false},
    			{false, true, true, false},
    		},
    		//RBT
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
    			{true, false, false, true},
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
    			"tests/bucket_sort/question5.html",
    		},
    		{	// Counting Sort
    			"tests/counting_sort/question1.html",
    			"tests/counting_sort/question2.html",
    			"tests/counting_sort/question3.html",
    			"tests/counting_sort/question4.html",
    			"tests/counting_sort/question5.html",
    		},
    		{	// Heap Sort
    			"tests/heap_sort/question1.html",
    			"tests/heap_sort/question2.html",
    			"tests/heap_sort/question3.html",
    			"tests/heap_sort/question4.html",
    			"tests/heap_sort/question5.html",
    		},
    		{	// FFT
    			"tests/fft/question1.html",
    			"tests/fft/question2.html",
    			"tests/fft/question3.html",
    			"tests/fft/question4.html",
    			"tests/fft/question5.html",
    		},
    		//SkipList
    		{
    			"tests/SkipList/question1.html",
    			"tests/SkipList/question2.html",
    			"tests/SkipList/question3.html",
    			"tests/SkipList/question4.html",
    			"tests/SkipList/question5.html"
    		},
    		//BST
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
    		//HashTable
    		{
    			"tests/HashTable/question1.html",
    			"tests/HashTable/question2.html",
    			"tests/HashTable/question3.html",
    			"tests/HashTable/question4.html",
    			"tests/HashTable/question5.html"
    		},
    		//FloydWarshall
    		{
    			"tests/FloydWarshall/question1.html",
    			"tests/FloydWarshall/question2.html",
    			"tests/FloydWarshall/question3.html",
    			"tests/FloydWarshall/question4.html",
    			"tests/FloydWarshall/question5.html"
    		},
    		//UFKruskal
    		{
    			"tests/UFKruskal/question1.html",
    			"tests/UFKruskal/question2.html",
    			"tests/UFKruskal/question3.html",
    			"tests/UFKruskal/question4.html",
    			"tests/UFKruskal/question5.html"
    		},
    		//GrahamScan
    		{
    			"tests/GrahamScan/question1.html",
    			"tests/GrahamScan/question2.html",
    			"tests/GrahamScan/question3.html",
    			"tests/GrahamScan/question4.html",
    			"tests/GrahamScan/question5.html",
    			"tests/GrahamScan/question6.html"
    		},
    		//RBT
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
    		},
    };
}
