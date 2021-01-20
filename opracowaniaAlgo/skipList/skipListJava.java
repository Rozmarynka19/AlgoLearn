package skipList;

public class Node {
	public int key; 

	// Array to hold pointers to node of different level 
	public Node[] forward; 
    public Node(int key, int level) 
    { 
        this.key = key; 
    
        // Allocate memory to forward 
        forward = new Node[level+1]; 
    
        // Fill forward array with 0(NULL) 
        for(int i=0;i<level+1;i++)
        	forward[i]=null;
    };
}

package skipList;

public class SkipList {
	// Maximum level for this skip list 
		private int MAXLVL; 

		// P is the fraction of the nodes with level 
		// i pointers also having level i+1 pointers 
		private float P; 

		// current level of skip list 
		private int level; 

		// pointer to header node 
		private Node header; 
	    public SkipList(int MAXLVL, float P)
	    { 
	        this.MAXLVL = MAXLVL; 
	        this.P = P; 
	        level = 0; 
	    
	        // create header node and initialize key to -1 
	        header = new Node(-1, MAXLVL); 
	    }; 
	    public int randomLevel()
	    { 
	        float r = (float)Math.random(); 
	        int lvl = 0; 
	        while(r < P && lvl < MAXLVL) 
	        { 
	            lvl++; 
	            r = (float)Math.random();
	        } 
	        return lvl; 
	    }; 
	    public Node createNode(int key, int level)
	    { 
	        Node n = new Node(key, level); 
	        return n; 
	    };  
	    public void insertElement(int key)
	    { 
	        Node current = header; 
	    
	        // create update array and initialize it 
	        Node[] update = new Node[MAXLVL+1];
	        for(int i=0;i<MAXLVL+1;i++)
	        	update[i]=null;
	    
	        /* start from highest level of skip list 
	            move the current pointer forward while key 
	            is greater than key of node next to current 
	            Otherwise inserted current in update and 
	            move one level down and continue search 
	        */
	        for(int i = level; i >= 0; i--) 
	        { 
	            while(current.forward[i] != null && 
	                current.forward[i].key < key) 
	                current = current.forward[i]; 
	            update[i] = current; 
	        } 
	    
	        /* reached level 0 and forward pointer to 
	        right, which is desired position to 
	        insert key. 
	        */
	        current = current.forward[0]; 
	    
	        /* if current is NULL that means we have reached 
	        to end of the level or current's key is not equal 
	        to key to insert that means we have to insert 
	        node between update[0] and current node */
	        if (current == null || current.key != key) 
	        { 
	            // Generate a random level for node 
	            int rlevel = randomLevel(); 
	    
	            /* If random level is greater than list's current 
	            level (node with highest level inserted in 
	            list so far), initialize update value with pointer 
	            to header for further use */
	            if(rlevel > level) 
	            { 
	                for(int i=level+1;i<rlevel+1;i++) 
	                    update[i] = header; 
	    
	                // Update the list current level 
	                level = rlevel; 
	            } 
	    
	            // create new node with random level generated 
	            Node n = createNode(key, rlevel); 
	    
	            // insert node by rearranging pointers 
	            for(int i=0;i<=rlevel;i++) 
	            { 
	                n.forward[i] = update[i].forward[i]; 
	                update[i].forward[i] = n; 
	            } 
	            System.out.println("Successfully Inserted key "+String.valueOf(key));
	        } 
	    }; 
		public void deleteElement(int key)
	    { 
	        Node current = header; 
	    
	        // create update array and initialize it 
	        Node[] update = new Node[MAXLVL+1]; 
	        for(int i=0;i<MAXLVL+1;i++)
	        	update[i]=null;
	    
	        /* start from highest level of skip list 
	            move the current pointer forward while key 
	            is greater than key of node next to current 
	            Otherwise inserted current in update and 
	            move one level down and continue search 
	        */
	        for(int i = level; i >= 0; i--) 
	        { 
	            while(current.forward[i] != null && 
	                current.forward[i].key < key) 
	                current = current.forward[i]; 
	            update[i] = current; 
	        } 
	    
	        /* reached level 0 and forward pointer to 
	        right, which is possibly our desired node.*/
	        current = current.forward[0]; 
	    
	        // If current node is target node 
	        if(current != null && current.key == key) 
	        { 
	            /* start from lowest level and rearrange 
	            pointers just like we do in singly linked list 
	            to remove target node */
	            for(int i=0;i<=level;i++) 
	            { 
	                /* If at level i, next node is not target 
	                node, break the loop, no need to move 
	                further level */
	                if(update[i].forward[i] != current) 
	                    break; 
	    
	                update[i].forward[i] = current.forward[i]; 
	            } 
	    
	            // Remove levels having no elements 
	            while(level>0 && header.forward[level] == null) 
	                level--; 
	            System.out.println("Successfully deleted key "+String.valueOf(key));
	        } 
	    };
		public void searchElement(int key) 
	    { 
	        Node current = header; 
	    
	        /* start from highest level of skip list 
	            move the current pointer forward while key 
	            is greater than key of node next to current 
	            Otherwise inserted current in update and 
	            move one level down and continue search 
	        */
	        for(int i = level; i >= 0; i--) 
	        { 
	            while(current.forward[i] != null && 
	                current.forward[i].key < key) 
	                current = current.forward[i]; 
	    
	        } 
	    
	        /* reached level 0 and advance pointer to 
	        right, which is possibly our desired node*/
	        current = current.forward[0]; 
	    
	        // If current node have key equal to 
	        // search key, we have found our target node 
	        if(current!=null && current.key == key)
	        	System.out.println("Found key: "+String.valueOf(key));
	    }; 
	    
	    public void displayList()
	    { 
	    	System.out.println("*****Skip List*****");
	    
	        for(int i=0;i<=level;i++) 
	        { 
	            Node node = header.forward[i];
	            System.out.println("Level "+String.valueOf(i));
	            while(node != null) 
	            {
	            	System.out.println(String.valueOf(node.key)+" ");
	                node = node.forward[i]; 
	            }
	            System.out.println();
	        } 
	    };
}

Na podstawie https://www.geeksforgeeks.org/skip-list-set-3-searching-deletion