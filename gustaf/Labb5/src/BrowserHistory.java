import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;

public class BrowserHistory extends JMenu {
    ArrayList<String> history;
    SearchBar search;

    public void setSearch(SearchBar search) {
        this.search = search;
        history = new ArrayList<>();
    }

    public BrowserHistory () {
        super("History");
    }

}
