import javax.swing.*;
import java.awt.*;


public class BrowserPanel extends JPanel {
    SearchBar searchBar;
    BrowserEditorPane editorPane;
    public BrowserPanel () {
        super(new BorderLayout());
        setPreferredSize(new Dimension(600,600));
        editorPane = new BrowserEditorPane();
        searchBar = new SearchBar();
        BrowserHistory history = new BrowserHistory();
        history.setSearch(searchBar);
        BrowserButton buttons = new BrowserButton(history, editorPane);
        searchBar.setButtons(buttons);
        searchBar.setEditor(editorPane);
        searchBar.setHistory(history);
        editorPane.setSearch(searchBar);
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(history);
        menuBar.add(buttons.backward);
        menuBar.add(buttons.forward);
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.add(searchBar, BorderLayout.PAGE_START);
        searchPanel.add(menuBar);
        JPanel editorPanePanel = new JPanel(new BorderLayout());
        JScrollPane scrollEditorPane = new JScrollPane(editorPanePanel);
        scrollEditorPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        editorPanePanel.add(editorPane, BorderLayout.CENTER);
        add(searchPanel, BorderLayout.PAGE_START);
        add(scrollEditorPane, BorderLayout.CENTER);

    }
    /*public class SearchBar extends TextField implements ActionListener {
        BrowserEditorPane editor;

        public void setEditor(BrowserEditorPane editor) {
            this.editor = editor;
        }

        public SearchBar () {
            super("http://www.csc.kth.se/utbildning/kth/kurser/DD1346/oopk14/laborationer/Lab5/labb5.html", 50);
            addActionListener(this);
            try {
                editorPane.setPage(getText());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Searchbar was used");
            try {
                editorPane.setPage(getText());
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
    }
    public class BrowserEditorPane extends JEditorPane {
        SearchBar search;

        public void setSearch(SearchBar search) {
            this.search = search;
        }

        public BrowserEditorPane () {
            super();
            setEditable(false);
            addHyperlinkListener(e -> {
                if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {

                    try {
                        search.setText(e.getURL().toString());
                        setPage(e.getURL());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            });
        }
    }*/
}
