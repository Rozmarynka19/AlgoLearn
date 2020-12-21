package algolearn.gui;

import java.util.ArrayList;
import java.util.List;

public class SavedValues {
    private static SavedValues instance;
    public int savedRealId = 0;
    boolean resizeFlag = false;
    //instrukcja podpięcia własnych plików:
    //1.Nadaj plikom nazwy unikalne dla algorytmu (struktury danych czy co tam to jest)
    //2.dodaj same nazwy plików do poniższych list w wiersz zgodny z oznaczeniem
    //3.Niczym się nie przejmuj
    //Uwaga, z pewnych powodów nazwy tak długie jak linked_list_description_fxml.fxml powodują błąd więc sugeruję krótsze
    public List<List<String>> paths = addToList(
            asList("introduction_fxml.fxml","description_fxml.fxml","visualisation_fxml.fxml"), // kopiec binarny
            asList("linked_list_introduction.fxml","linked_list_description.fxml","linked_list_visualization.fxml"), //lista jednokierunkowa
            asList("linked_list2_introduction.fxml","description_fxml.fxml","linked_list2_visualization.fxml"), // lista dwukierunkowa
            asList("introduction_fxml.fxml","description_fxml.fxml","visualisation_fxml.fxml"), // sortowanie kubełkowe
            asList("introduction_fxml.fxml","description_fxml.fxml","visualisation_fxml.fxml"), // Sortowanie przez zliczanie
            asList("introduction_fxml.fxml","description_fxml.fxml","visualisation_fxml.fxml"), // Sortowanie przez kopcowanie
            asList("introduction_fxml.fxml","description_fxml.fxml","visualisation_fxml.fxml"), // fft
            asList("introduction_fxml.fxml","description_fxml.fxml","visualisation_fxml.fxml"), // lista z przeskokami
            asList("BST_introduction.fxml","description_fxml.fxml","bst_visualisation.fxml"), // bst
            asList("introduction_fxml.fxml","description_fxml.fxml","visualisation_fxml.fxml"), // tablica mieszająca
            asList("introduction_fxml.fxml","description_fxml.fxml","visualisation_fxml.fxml"), // floyd warshall
            asList("introduction_fxml.fxml","description_fxml.fxml","visualisation_fxml.fxml"), // Union Find
            asList("introduction_fxml.fxml","description_fxml.fxml","visualisation_fxml.fxml"), // algorytm grahama
            asList("RBT_introduction.fxml","description_fxml.fxml","rbt_visualisation.fxml") //  red-black tree
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

}