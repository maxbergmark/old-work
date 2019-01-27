import static java.util.Arrays.sort;

public class Ram {
    public static void main(String[] args) {
        Fysiker[] list = new Fysiker[3];
        list[0] = new Fysiker(54,"Max", 2008);
        list[1] = new Fysiker(52,"Fabian", 2010);
        list[2] = new Fysiker(54,"Gustaf", 2009);
        sort(list);
        for (int i = 0; i < list.length; i++) {
            System.out.println(list[i]);
        }
        // Här kan du se att trots att setName() är bortkommenterad i Fysiker(),
        // så får fysikerna ett slumpmässigt namn.
        Human[] list2 = new Human[10];
        for (int i = 0; i < list2.length; i++) {
            list2[i] = new Fysiker();
        }
        for (int i = 0; i < list2.length; i++) {
            System.out.println(list2[i]);
        }
        // Behöver inte fixas i den här labben, för all kod ser egentligen bra ut,
        // men ha som vana att spara lösningarna för alla delproblem för en labb.
    }
}
