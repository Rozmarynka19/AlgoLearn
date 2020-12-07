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
}
