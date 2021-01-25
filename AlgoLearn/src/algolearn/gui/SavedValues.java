package algolearn.gui;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SavedValues {
    private static SavedValues instance;
    public int savedRealId = 0;
    boolean resizeFlag = true;
    //instrukcja podpięcia własnych plików:
    //1.Nadaj plikom nazwy unikalne dla algorytmu (struktury danych czy co tam to jest)
    //2.dodaj same nazwy plików do poniższych list w wiersz zgodny z oznaczeniem
    //3.Niczym się nie przejmuj
    //Uwaga, z pewnych powodów nazwy tak długie jak linked_list_description_fxml.fxml powodują błąd więc sugeruję krótsze
    public List<List<String>> paths = addToList(
            asList("binaryHeap_introduction.fxml","binaryHeap_description.fxml","bin_heap_visualisation.fxml"), // kopiec binarny
            asList("linked_list_introduction.fxml","linked_list_description.fxml","linked_list_visualisation.fxml"), //lista jednokierunkowa
            asList("linked_list2_introduction.fxml","linked_list2_description.fxml","linked_list2_visualisation.fxml"), // lista dwukierunkowa
            asList("bucketSort_introduction.fxml","bucketSort_description.fxml","03_bucket_sort_visualisation.fxml"), // sortowanie kubełkowe
            asList("countingSort_introduction.fxml","countingSort_description.fxml","04_counting_sort_visualisation.fxml"), // Sortowanie przez zliczanie
            asList("heapSort_introduction.fxml","heapSort_description.fxml","heap_sort_visualisation.fxml"), // Sortowanie przez kopcowanie
            asList("FFT_introduction.fxml","FFT_description.fxml","visualisation_fxml.fxml"), // fft
            asList("skip_list_introduction.fxml","skip_list_description.fxml","skip_list_visualisation.fxml"), // lista z przeskokami
            asList("BST_introduction.fxml","BST_description.fxml","bst_visualisation.fxml"), // bst
            asList("hashTable_introduction.fxml","hashTable_description.fxml","hash_table_visualisation.fxml"), // tablica mieszająca
            asList("FloydWarshall_introduction.fxml","FloydWarshall_description.fxml","visualisation_fxml.fxml"), // floyd warshall
            asList("UFKruskal_introduction.fxml","UFKruskal_description.fxml","UFKruskal_visualisation.fxml"), // Union Find
            asList("GrahamScan_introduction.fxml","GrahamScan_description.fxml","GrahamScan_visualisation.fxml"), // algorytm grahama
            asList("RBT_introduction.fxml","RBT_description.fxml","RBT_visualisation.fxml") //  red-black tree
    );

    private static <String> List<String> asList(String ... items) {
        List<String> list = new ArrayList();
        for (String item : items) {
            list.add((String) ("fxml/"+item));
        }
        return list;
    }
    private static <String> List<String> addToList(String ... items) {
        List<String> list = new ArrayList();
        for (String item : items) {
            list.add(item);
        }
        return list;
    }

    public static SavedValues getInstance() {
        if (instance == null) {
            instance = new SavedValues();
        }
        return instance;
    }
    
    @FXML private Button clickedBTN = null;
    public void SetClickedBTN(Button btn) {this.clickedBTN = btn;}
    public Button GetClickedBTN() { return this.clickedBTN;}
    
    public String getID() {
    	if(clickedBTN == null)
    		return "NULL";
    	else
    		return clickedBTN.getId();
    }
    
    protected testsController TestController = null;
    

}