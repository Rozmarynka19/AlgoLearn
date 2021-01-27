package algolearn.gui.HelpClass;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.pow;

public class HashTable<T> {
    private int csize = 0;
    private int maksSize = 10;
    public class Data{
        String word;
        T element;
        Data next;
        public Data(){
            this.word="";
            this.next = null;
        }
    }
    private List<Data> tab;

    public HashTable(){
        maksSize = 10;
        tab = new ArrayList<>(maksSize);
        for (int i = 0; i < maksSize; i++) {
            tab.add(new Data());
        }
    }

    private void rehash() {
        maksSize *= 2;
        List<Data> tmp_array = new ArrayList<>(maksSize);
        for (int i = 0; i < maksSize; i++) {
            tmp_array.add(new Data());
        }
        for (int i = 0; i < maksSize / 2; i++) {
            if (!tab.get(i).word.equals("")) {

                if (!tmp_array.get(hash(tab.get(i).word)).word.equals("")) {
                    Data ptr = tmp_array.get(hash(tab.get(i).word)).next;
                    while (ptr.next!=null) {
                        ptr = ptr.next;
                    }
                    ptr.next = tab.get(i);
                }
                else {
                    tmp_array.set(hash(tab.get(i).word), tab.get(i));
                }
            }
        }
        tab = tmp_array;
    }

    public int MaksChain() {
        int maks = 0;
        int k = 0;
        for (int i = 0; i < maksSize; i++) {
            k = 0;
            if (!tab.get(i).word.equals("")) {
                if (tab.get(i).next != null) {
                    Data ptr = tab.get(i);
                    while (ptr.next != null) {
                        ptr = ptr.next;
                        k += 1;
                    }
                }
            }
            if (maks < k) {
                maks = k;
            }
        }
        return maks;
    }

    public int hash(String key) {
        int hash = 0;
        int index;
        index = key.length();

        for (int i = 0; i < index; i++) {
            hash += (int)key.charAt(i) * (int)pow(31, index - (i + 1));
        }
        return abs(hash % maksSize);
    }

    public void Add(String key, final T value) {
        int index = hash(key);
        if (tab.get(index).word.equals("")) {
            tab.get(index).word = key;
            tab.get(index).element = value;
            csize++;
            if (csize >= (maksSize * 75) / 100) {
                rehash();
            }
        }
        else {
            String tmp = tab.get(index).word;
            if (tmp.equals(key)) {
                tab.get(index).element = value;
                return;
            }
            else {
                Data ptr = tab.get(index);
                Data n = new Data();
                n.word = key;
                n.element = value;
                n.next = null;
                while (ptr.next != null) {
                    ptr = ptr.next;
                }
                ptr.next = n;
                csize++;
                if (csize >= (maksSize * 75) / 100) {
                    rehash();
                }
            }
        }
    }

    public int NumberOfItems(final int index) {
        int count = 0;

        if (tab.get(index).word.equals("")) {
            return count;
        }
        else {
            count++;
            Data ptr = tab.get(index);
            while (ptr.next != null) {
                count++;
                ptr = ptr.next;
            }

        }
        return count;
    }

    public Data FindElement(final String key) {
        int tmp = hash(key);
        Data ptr = tab.get(tmp);
        if (ptr.word.equals("")) {
            return null;
        }
        return ptr;
    }
    public Boolean DeleteElement(final String key){
        Data ptr = FindElement(key);
        if (ptr == null) {
            return false;
        }
        while (ptr.next != null) {
            ptr = ptr.next;
        }
        ptr.word = "";
        csize--;
        return true;
    }
    public void Clear(){
        tab.clear();
        csize = 0;
    }

    public void Print() {
        int number;
        int count = 0;
        System.out.println("|---------------------------------|");
        System.out.println("Rozmiar maks: " + maksSize + ". Ilosc elementow: " + csize);
        System.out.println("Maksymalny lancuch: " + MaksChain());
        System.out.println("---------------------------------");
        for (int i = 0; i < maksSize; i++) {
            if (!tab.get(i).word.equals("")) {
                count++;
                number = NumberOfItems(i);
                System.out.println("index = " + i);
                System.out.print("{\"" + tab.get(i).word + "\" ; ");
                System.out.println(tab.get(i).element + "} ");
                if (tab.get(i).next!=null) {
                    Data ptr = tab.get(i).next;
                    int k = 1;
                    while (ptr != null) {
                        System.out.println(" -> {" + k + "} {\"" + ptr.word + "\" " + ptr.element + "} ");
                        ptr = ptr.next;
                        k++;
                    }
                }
                System.out.println("\nIlosc elementow w lancuchu: " + number + "\n");
            }
            if (count == 10) {
                break;
            }
        }
    }

    public int getIndex(String key){
        for (int i = 0; i < maksSize; i++) {
            if(tab.get(i).word.equals(key))
                return i;
            else if(tab.get(i).next!=null) {
                Data ptr = tab.get(i).next;
                while (ptr != null) {
                    if(ptr.word.equals(key))
                        return i;
                    ptr = ptr.next;
                }
            }
        }
        return -1;
    }

    public int getCurrentSize(){
        return csize;
    }
    public int getMaksSize(){
        return maksSize;
    }
}


