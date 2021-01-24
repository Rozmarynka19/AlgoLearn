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
	
	//<--------------------Bucket-Sort------------------------->
	public String exceedValueBS = "Zakres wartości od 1-16!";
	public String addedTo = "Dodano wartość ";
	public String toUnsortedList = " do nieposortowanej listy";
	public String addedToBucket = "Do kubełka ";
	public String addedValueToBucket = " dodano wartość ";
	public String bucketsAreSorted = "Posortowano wartości w kubełkach i wypisano posortowaną listę";
	public String addedToBucketNotExist = "Wszystkie elementy z listy zostały już umieszczone w kubełkach!";
	public String notFoundInBucket = "Podana wartość nie znajduje się w kubełku!";
	public String isFound = "Odnaleziono wartość ";
	public String notFoundInUnsortedList = "Podana wartość nie istnieje!";
	public String isRemoved = "Usunięto wartość ";
	public String isReloaded = " z nieposortowanej listy";
	public String isReloadedAndInBucket = "oraz kubełka";
	public String isEmptyList = "Lista jest pusta!";
	
	//<-------------------- RBT ------------------------->

	public static class rbtMsg
	{
		public rbtMsg() {
			// TODO Auto-generated constructor stub
		}
		public String setupInformation(String info) {
			return (infoDef+info);
		}
		//---------------- general -------------------------
		public String infoDef = "Informacja: ";
		public String msgTypeCongrats = "congrats";
		public String msgTypeError = "error";
		
		//--------------------- headers ------------------
		public String msgCongratsHeader = "Brawo!";
		public String msgErrorHeader = "Błąd!";
		
		//----------------- guess game ------------------------
		public String finishedGuessGame = "Brawo, Mistrzu, za odgadnięcie wartości wszystkich węzłów!";
		public String selectNodeFirst = "Najpierw zaznacz węzeł, którego wartość chcesz odgadnąć!";
		public String goodCall = "Brawo! Tu faktycznie powinna być liczba ";
		public String wrongCall = "Niestety, tutaj nie może być liczba ";
		
		//--------------- buttons -----------------------------
		public String generateDone = "Wygenerowano nowy przykład.";
		public String restartDone = "Zrestartowano wizualizację.";
		public String gameModeOn = "Tryb gry włączony.";
		public String gameModeOff = "Tryb gry wyłączony.";
		
		//--------------- generate -----------------------------
		public String delInProgress = "Zaczekaj, aż usuwanie zakończy działanie!";
		public String pathTNotDone = "Musisz zaczekać na wykonanie się animacji!";
		
		//--------------- search -----------------------------
		public String nodeNotFound = "Nie znaleziono węzła o kluczu ";
		public String nodeFound = "Znaleziono węzeł o kluczu ";
		public String emptyTree = "Drzewo jest puste!";
		
		//--------------- deletion -----------------------------
		public String nodeDeletedPart1 = "Węzeł ";
		public String nodeDeletedPart2 = " został zastąpiony przez swojego poprzednika, a następnie usunięty!";
		
		//--------------- insertion -----------------------------
		public String nodeAlredyExistsPart1 = "Węzeł o kluczu ";
		public String nodeAlredyExistsPart2 = " już istnieje!";
		public String nodeInserted = "Wstawiono węzeł o kluczu ";
		public String valueNotGiven = "Nie podano wartości węzła!";
		public String acceptableValues = "Dozwolone wartości węzła: liczby całkowite z przedziału <1,99>";
		public String unknownNodeSelected = "Zaznaczono węzeł z niewiadomą wartością klucza.";
		public String knownNodeSelected = "Zaznaczono węzeł z o kluczu ";
		
	}
	
	public static class grahamScanMsg
	{
		public grahamScanMsg() {
			// TODO Auto-generated constructor stub
		}
		public String setupInformation(String info) {
			return (infoDef+info);
		}
		//---------------- general -------------------------
		public String infoDef = "Informacja: ";
		public String msgTypeError = "error";
		
		//--------------------- headers ------------------
		public String msgErrorHeader = "Błąd!";
		
		//--------------- buttons -----------------------------
		public String generateDone = "Wygenerowano nowy przykład.";
		public String restartDone = "Zrestartowano wizualizację.";
		
		//--------------- generate -----------------------------
		public String delInProgress = "Zaczekaj, aż usuwanie zakończy działanie!";
		public String pathTNotDone = "Musisz zaczekać na wykonanie się animacji!";
		
		//--------------- deletion -----------------------------
		public String nodeDeletedPart1 = "Węzeł ";
		public String nodeDeletedPart2 = " został usunięty!";
		public String noSuchNode = "Nie ma węzła o kluczu ";
		
		//--------------- insertion -----------------------------
		public String nodeAlredyExistsPart1 = "Węzeł o kluczu ";
		public String nodeAlredyExistsPart2 = " już istnieje!";
		public String nodeInserted = "Wstawiono węzeł o kluczu ";
		public String valueNotGiven = "Nie podano wartości węzła!";
		public String acceptableValues = "Dozwolone wartości węzła: liczby całkowite z przedziału <1,99>";
		public String tooManyNodes = "Za dużo węzłów w grafie!";
		
		
		//--------------- algorithm -----------------------------
		public String emptyGraph = "Graf jest pusty!";
		public String startNodeFound = "Znaleziono węzeł początkowy! Przestawiono układ współrzędnych!";
		public String nodesSorted = "Posortowano węzły według kąta!";
		public String firstNodesAdded = "Wstawiono punkt początkowy i pierwszy z listy do powłoki!";
		public String turnLeft = "Zakręt w lewo - ok!";
		public String turnRight = "Zakręt w prawo - usuwamy przedostatni węzeł";
		public String finished = "Algorytm zakończył pracę!";
		public String nodeInTheHullInserted = "Wstawiono nowy węzeł do powłoki - klucz: ";
		
	}
}
