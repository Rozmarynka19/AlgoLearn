#include <iostream> 
using namespace std; 

struct Node {   
    int data;  
    Node* next;  
    Node* prev;  
};  
size_t lSize=0;
Node* head = nullptr; 
Node* tail = nullptr; 
  
void pushFront(int insertData) {  
    Node* newNode = new Node();  
    newNode->data = insertData;  
    newNode->next = head;  
    newNode->prev = nullptr; 
  
    if (head!= nullptr && lSize!=0)  
        head->prev = newNode; 

    if(tail == nullptr) {
        tail = newNode;
    } 
 
    head = newNode;
    lSize++;  
}  

void pushBack(int insertData) {  
    Node* newNode = new Node();  
    Node* last = head; 
    if (last == nullptr) {  
        newNode->prev = nullptr;
        newNode->next = nullptr; 
        newNode->data = insertData;    
        head = newNode;
        tail = newNode;          
    } 
    else {
        while (last->next != nullptr) {
            last = last->next; 
        }
        newNode->data = insertData;  
        last->next = newNode;  
        newNode->prev = last;
        tail = newNode;
    } 
    lSize++;
}  

void popFront() {
	if (lSize > 0) {
		Node* tmp = head;
		head = head->next;
		delete tmp;
		lSize--;
	}
}

void popBack() {
    std::cout<<tail->data;
    std::cout<<tail->prev->data;    
	if (lSize > 0) {
		Node* tmp = tail;
        if(tail->prev == nullptr) {
            head = nullptr;
            tail = nullptr; 
        }
        else {
 		   tail = tail->prev; 
		   tail->next = nullptr;         
        }
		delete tmp;
		lSize--;
	}
}

void insertAt(int insertAfter, int insertData) {  
    Node* tmp = head;

    int x=1;
    while(tmp !=nullptr) {
        if(x == insertAfter) {
            Node* newNode = new Node();
            newNode->data = insertData;
            newNode->next = tmp->next;
            newNode->prev = tmp;
            tmp->next = newNode;
            if (newNode->next != nullptr) { 
                tmp->next->prev = newNode; 
                tail->prev = newNode;
            }  
            break;
        }
        tmp = tmp->next;
        x++;
    }
    lSize++;
}  

void printList() {  
    Node* tmp = head;
    std::cout << "Size: " << lSize << std::endl; 
    while (tmp != nullptr) {  
        cout<<tmp->data<<" ";   
        tmp = tmp->next;  
    }
    std::cout<<std::endl;
}  

int main() {   
    pushFront(6);
    printList(); 
    pushBack(5);
    printList();
    insertAt(1,4);
    printList();    
    popBack();    
    printList(); 
    popFront();
    printList();  
    return 0;  
}
