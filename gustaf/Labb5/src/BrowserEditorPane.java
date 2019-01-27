import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.event.ActionEvent;
import java.io.IOException;

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
                search.setText(e.getURL().toString());
                search.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
            }
        });
    }
}
