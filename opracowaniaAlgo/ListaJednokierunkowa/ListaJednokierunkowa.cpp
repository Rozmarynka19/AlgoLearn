#include <iostream>
#include <string>
using namespace std;
struct KoloRatunkowe {
    int topielec;
    KoloRatunkowe* nastepneKoloRatunkowe = 0;
};

struct ciagKolRatunkowych {
    KoloRatunkowe* brzeg_HEAD = 0;
    void dodajKoloRatunkowe(int klucz) {
        KoloRatunkowe* tworzonyElement = new KoloRatunkowe;
        tworzonyElement->topielec = klucz;
        if (brzeg_HEAD == 0){
            brzeg_HEAD = tworzonyElement;//rzucamyz linê brzegu 
        }
        else {
            KoloRatunkowe* iterator = brzeg_HEAD;
            while (iterator->nastepneKoloRatunkowe > 0) {
                iterator = iterator->nastepneKoloRatunkowe;
            }
            iterator->nastepneKoloRatunkowe = tworzonyElement;
            tworzonyElement->nastepneKoloRatunkowe = 0;
        }
    }
    void wyswietlCi¹g() {
        KoloRatunkowe* iterator = brzeg_HEAD;
        while (iterator) {
            cout << iterator->topielec << endl;
            iterator = iterator->nastepneKoloRatunkowe;
        }
    }
    void usuñKoloRatunkowe(int numerKolejnosci) {
        if (numerKolejnosci == 1) {
            KoloRatunkowe* wskaŸnik = brzeg_HEAD;
            brzeg_HEAD = wskaŸnik->nastepneKoloRatunkowe;
        }
        else if (numerKolejnosci >= 2) {
            int iteratorLiczbowy = 1;
            KoloRatunkowe* iteratorPoElementach = brzeg_HEAD;
            while (iteratorPoElementach >0) {
                if ((iteratorLiczbowy + 1) == numerKolejnosci) break;
                iteratorPoElementach = iteratorPoElementach->nastepneKoloRatunkowe;
                iteratorLiczbowy++;
            }
            if (iteratorPoElementach->nastepneKoloRatunkowe->nastepneKoloRatunkowe == 0) {
                delete iteratorPoElementach->nastepneKoloRatunkowe;
                iteratorPoElementach->nastepneKoloRatunkowe = 0;
            }
            else {
                KoloRatunkowe* usuwanyElement = iteratorPoElementach->nastepneKoloRatunkowe;
                iteratorPoElementach->nastepneKoloRatunkowe = iteratorPoElementach->nastepneKoloRatunkowe->nastepneKoloRatunkowe;
            }
        }
    }
};
int main(){
    ciagKolRatunkowych* ciag1 = new ciagKolRatunkowych;
    
    ciag1->dodajKoloRatunkowe(1);
    ciag1->dodajKoloRatunkowe(2);
    ciag1->dodajKoloRatunkowe(3);
    ciag1->dodajKoloRatunkowe(4);
    ciag1->wyswietlCi¹g();
    ciag1->usuñKoloRatunkowe(2);
    ciag1->wyswietlCi¹g();

    delete ciag1;
    return 0;
}