package algolearn.gui.info;

public class errors {
	public String WrongInput = 
			"Wprowadzono złą wartość!\n"
		  + "\n"
		  + "Nasza wizualizacja akceptuje wartości węzła : 0<x<100 \n"
		  + "Wprowadź liczbę która znaduje się w tym przedziale! \n"
		  + "\n"
		  + "Pamiętaj że jest to zakres użytkowy naszej Aplikacji! \n"
		  + "Nie doszukuj się w tym działania danego algorytmu!";
	
	public String OnlyNumeric = "Dane pole przyjmuje jedynie liczby z przedziału 0<x<100 jako input!";
	public String pathTNotDone = "Musisz zaczekać na wykonanie się animacji!";
	public String nodeAlredyExists = "Węzel który chcesz dodać już istnieje!";
	public String nodeNotSelected = 
			  "Musisz wybrać węzeł którego wartość próbujesz obecnie odgadnąć!"
			+ "\n\n"
			+ "Aby to dokonać wybierz dany węzeł z okna wizualizacji.";
	public String randomizedBlock = 
			     "Aby wykonać tę operację odgadnij ukryte wartości!"
			   + "\n\n"
			   + "Podczas odgadywania wartości usuwanie oraz wyszukiwanie wartości jest zablokowane!"
			   + "\n\n"
			   + "Pamiętaj, że możesz dodać nowy węzeł jako pomoc do odgadnięcia wartości!";
	
	public String infoDef = "Informacja: ";
	public String finishAnimation = "Dokończ przejście animacji aby kontynuować dalej!";
	
	public String addNode = "Dodano węzeł do drzewa";
	public String searchNode = "Wyszukano węzeł o danej wartości";
	public String searchRoot = "Wyszukano korzeń o danej wartości";
	public String searchNotFound = "Wyszukiwany węzeł nie istnieje";
	public String searchHidden = "Wyszukiwany węzeł jest ukryty";
	public String generateTree = "Wygenerowano losowe drzewo";
	
	public String selectedBinnaryTree = "Wybrano typ drzewa binarnego!";
	public String minTree = "Kopiec zorientowany na minimum";
	public String maxTree = "Kopiec zoreintowany na maksimum";
	public String generateHeap = "Wygenerowano losowy kopiec binarny!";
	public String restartHeap = "Zrestartowano kopiec binarny!";
	public String addNodeHeap = "Dodano element do kopca binarnego!";
	public String searchFoundHeap = "Znaleziono wyszukiwany element w kopcu!";
	public String searchNotFoundHeap = "Nie znaleziono wyszukiwanego elementu w kopcu!";
	public String deleteHeap = "Usuwanie elementu z kopca binarnego!";
	public String deleteNotFoundHeap = "Nie znaleziono usuwanego elementu w kopcu!";
	
	public String waitUntilDone = "Poczakaj na wykonanie się bieżącej operacji!";
	public String addUnsorted = "Dodano element do nieposortowanego zbioru!";
	public String generateUnsortedArray = "Wygenerowano losowy nieposortowany zbiór!";
	public String restartHeapSort = "Zrestartowano wizualizacje sortowania przez kopcowanie!";

	public String step = "Krok: ";
	
	public String beginHeapBuild = "Rozpoczęto buidowanie kopca binarnego!";
	public String heapBuildInProgress = "Trwa budowanie kopca binarnego!";
	public String endHeapSort = "Budowanie kopca binarnego zakończyło się powodzeniem!";
	
	public String beginSort = "Rozpoczęto sortowanie przez kopcowanie!";
	public String sortInProgress = "Trwa sortowanie przez kopcowanie!";
	public String endSort = "Sortowanie przez kopcowanie zakończyło się powodzeniem!";
	
	public String foundSuccess = "Znaleziono wyszukiwaną wartość!";
	public String foundFailure = "Nie znaleziono wyszukiwanego elementu!";
	

	//<--------------------Linked-list------------------------->
	public String InputIsString = "Wprowadzona wartość nie jest liczbą!";
	public String IsEmpty = "Nie ma elementów w wizualizacji!";
	public String IsFull = "Maksymalna ilość elementów w wizualizacji to 11";
	public String CannotBeFound = "Nie można znaleźć podanego węzła!";
	public String IsNotExisting = "Podany węzęł nie istnieje!";
	public String HaveDuplicate = "Nie są przyjmowane duplikaty";

	public String setupInformation(String info) {
		return (infoDef+info);
	}
	public String setupOperationInfo(String info, String value) {
		return (infoDef+info+" [ "+value+" ]");
	}
	
	public String getStep(int id, int max) {
		return step+Integer.toString(id)+"/"+Integer.toString(max);
	}
	
	public String maxHeightBST = "Osiągnięto maksymalną wysokość drzewa - Ograniczenia aplikacji!";
	
	public String delInProgress = "Zaczekaj aż usuwanie zakończy działanie!";
	public String delMin = "Wyszukiwanie minimum w prawym potomku usuwanego węzła!";
	public String delSwap = "Zamiana węzła o minimalnej wartości z węzłem usuwanym!";
	
	
	public String searchToDelete = "Wyszukiwanie węzła do usunięcia!";
	public String deleteNode = "Usuwanie węzła";
	
	public String successUnknownValue = "Pomyślnie odganięto ukrytą wartość!";
}
