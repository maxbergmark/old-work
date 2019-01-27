import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BrowserButton implements ActionListener {
    ArrayList<String> history;
    JButton forward, backward;
    BrowserHistory browserHistory;
    BrowserEditorPane editorPane;
    int index;
    public BrowserButton(BrowserHistory browserHistory, BrowserEditorPane editorPane) {
        this.browserHistory = browserHistory;
        this.editorPane = editorPane;
        backward = new JButton("<-");
        backward.setHorizontalTextPosition(AbstractButton.LEADING);
        backward.addActionListener(this);
        backward.setEnabled(false);
        forward = new JButton("->");
        forward.setHorizontalTextPosition(AbstractButton.TRAILING);
        forward.addActionListener(this);
        forward.setEnabled(false);
    }
    public void update() {
        
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == backward) {

        }
        if(e.getSource() == forward) {

        }
    }
}
