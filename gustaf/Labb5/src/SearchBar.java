import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class SearchBar extends TextField implements ActionListener {
    BrowserEditorPane editor;
    BrowserHistory browserHistory;
    BrowserButton buttons;

    public void setButtons(BrowserButton buttons) {
        this.buttons = buttons;
    }

    public SearchBar () {
        super("http://www.csc.kth.se/utbildning/kth/kurser/DD1346/oopk14/laborationer/Lab5/labb5.html", 50);
        addActionListener(this);

    }

    public void setHistory(BrowserHistory browserHistory) {
        this.browserHistory = browserHistory;
        JMenuItem item = new JMenuItem(getText());
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setText(e.getActionCommand());
                fireAction();
            }
        });
        browserHistory.add(item);
        browserHistory.history.add(getText());
    }

    public void setEditor(BrowserEditorPane editor) {
        this.editor = editor;
        try {
            editor.setPage(getText());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
    public void fireAction() {
        actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        buttons.update();
        JMenuItem item = new JMenuItem(getText());
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setText(e.getActionCommand());
                fireAction();
            }
        });
        browserHistory.add(item);
        browserHistory.history.add(getText());
        try {
            editor.setPage(getText());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}