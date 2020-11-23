package dll.dll;

public class DLL {
	int lSize=0;
	Node head;
	Node tail; 
	
	class Node {
	    int data;  
	    Node next;  
	    Node prev; 
		Node(int insertData) {
			data = insertData;
		}
	}
		
	public void pushFront(int insertData) {  
	    Node newNode = new Node(insertData);   
	    newNode.next = head;  
	    newNode.prev = null; 
	  
	    if (head!= null && lSize!=0)  {
	        head.prev = newNode; 	    	
	    }


	    if(tail == null) {
	        tail = newNode;
	    } 
	 
	    head = newNode;
	    lSize++;  
	}  
	
	public void pushBack(int insertData) {  
	    Node newNode = new Node(insertData);  
	    Node last = head; 
	    if (last == null) {  
	        newNode.prev = null;
	        newNode.next = null;   
	        head = newNode;
	        tail = newNode;          
	    } 
	    else {
	        while (last.next != null) {
	            last = last.next; 
	        }
	        last.next = newNode;  
	        newNode.prev = last;
	        tail = newNode;
	    } 
	    lSize++;
	}
	
	public void popFront() {
		if (lSize > 0) {
			head = head.next;
			lSize--;
		}
	}

	void popBack() {    
		if (lSize > 0) {
	        if(tail.prev == null) {
	            head = null;
	            tail = null; 
	        }
	        else {
	 		   tail = tail.prev; 
			   tail.next = null;         
	        }
			lSize--;
		}
	}
	
	void insertAt(int insertAfter, int insertData) {  
	    Node tmp = head;

	    int x=1;
	    while(tmp != null) {
	        if(x == insertAfter) {
	            Node newNode = new Node(insertData);
	            newNode.next = tmp.next;
	            newNode.prev = tmp;
	            tmp.next = newNode;
	            if (newNode.next != null) { 
	                tmp.next.prev = newNode; 
	                tail.prev = newNode;
	            }  
	            break;
	        }
	        tmp = tmp.next;
	        x++;
	    }
	    lSize++;
	}  

	void printList() {  
	    Node tmp = head;
	    System.out.println("Size: " + lSize);
	    while (tmp != null) {  
	        System.out.print(tmp.data + " ");
	        tmp = tmp.next;  
	    }
        System.out.println();
	}  

	public static void main(String[] args) {
		DLL dll = new DLL();
		dll.pushFront(6);
		dll.printList(); 
		dll.pushBack(5);
		dll.printList();
		dll.insertAt(1,4);
		dll.printList();    
		dll.popBack();    
		dll.printList(); 
		dll.popFront();
		dll.printList();
	}
}