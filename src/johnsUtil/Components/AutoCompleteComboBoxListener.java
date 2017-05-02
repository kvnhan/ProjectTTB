package johnsUtil.Components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import johnsUtil.Controllers.MainMenuController;
import sample.AlcoholData;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Listens for changes to what is displayed by the application.
 */
public class AutoCompleteComboBoxListener implements EventHandler<KeyEvent> {

    private ComboBox comboBox;
    private ObservableList<String> data;
    private boolean moveCaretToPos = false;
    private int caretPos;
    private ObservableList<String> list = FXCollections.observableArrayList();;
    private ArrayList<String> suggestions = new ArrayList<>();
    private int maxAlcoholUsedToGenerateList = 100;
    private List<AlcoholData> allAlcoholEntries = MainMenuController.getAllAlcoholEntries();

    /**
     * Creates an instance of the AutoCompleteComboBoxListener class.
     * @param comboBox Combo Box to create.
     */
    public AutoCompleteComboBoxListener(final ComboBox comboBox) {
        this.comboBox = comboBox;

        createAutocompleteList();

        data = FXCollections.observableArrayList(suggestions);

        this.comboBox.setEditable(true);
        this.comboBox.setOnKeyReleased(AutoCompleteComboBoxListener.this);
    }

    @Override
    /**
     * Handler for events in the UI.
     */
    public void handle(KeyEvent event) {
        if(event.getCode() == KeyCode.UP) {
            caretPos = -1;
            moveCaret(comboBox.getEditor().getText().length());
            return;
        } else if(event.getCode() == KeyCode.DOWN) {
            if(!comboBox.isShowing())
                comboBox.show();

            caretPos = -1;
            moveCaret(comboBox.getEditor().getText().length());
            return;
        }

        if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT
                || event.isControlDown() || event.getCode() == KeyCode.HOME
                || event.getCode() == KeyCode.END || event.getCode() == KeyCode.TAB) {
            return;
        }

        ObservableList<String> oldList = list;
        //comboBox.hide();

        if(event.getCode() == KeyCode.BACK_SPACE) {
            moveCaretToPos = true;
            caretPos = comboBox.getEditor().getCaretPosition();
        } else if(event.getCode() == KeyCode.DELETE) {
            moveCaretToPos = true;
            caretPos = comboBox.getEditor().getCaretPosition();
        }

        list = FXCollections.observableArrayList();
        for (int i=0; i<data.size(); i++) {
            if(data.get(i).toLowerCase().contains(
                    AutoCompleteComboBoxListener.this.comboBox
                            .getEditor().getText().toLowerCase())) {
                list.add(data.get(i));
            }
        }

        String t = comboBox.getEditor().getText();

        comboBox.setItems(list);
        comboBox.getEditor().setText(t);
        if(!moveCaretToPos) {
            caretPos = -1;
        }
        moveCaret(t.length());
        if(!list.isEmpty()) {
            comboBox.hide();
            comboBox.setVisibleRowCount(12);
            comboBox.show();
        }
    }

    /**
     * Changes the position of a caret.
     * @param textLength New text length to set.
     */
    private void moveCaret(int textLength) {
        if(caretPos == -1)
            comboBox.getEditor().positionCaret(textLength);
        else
            comboBox.getEditor().positionCaret(caretPos);

        moveCaretToPos = false;
    }

    private void createAutocompleteList(){
        for(int i = 0; i < maxAlcoholUsedToGenerateList; i++){
            AlcoholData anAlcohol = allAlcoholEntries.get(i);

            if(!(anAlcohol.getName() == null)){
                if(!suggestions.contains(anAlcohol.getName()) && !(anAlcohol.getName().trim().isEmpty()) && !(anAlcohol.getName().equals("n/a"))){
                    suggestions.add(anAlcohol.getName());
                }
            }

            if(!(anAlcohol.getAppellation() == null)){
                if(!suggestions.contains(anAlcohol.getAppellation()) && !(anAlcohol.getAppellation().trim().isEmpty()) && !(anAlcohol.getAppellation().equals("n/a"))){
                suggestions.add(anAlcohol.getAppellation());
            }

            if(!(anAlcohol.getBrandName() == null)){
                if(!suggestions.contains(anAlcohol.getBrandName()) && !(anAlcohol.getBrandName().trim().isEmpty()) && !(anAlcohol.getBrandName().equals("n/a"))){
                    suggestions.add(anAlcohol.getBrandName());
                }
            }

            if( !(anAlcohol.getGrapeVarietal() == null)){
                if(!suggestions.contains(anAlcohol.getGrapeVarietal()) && !(anAlcohol.getGrapeVarietal().trim().isEmpty()) && !(anAlcohol.getGrapeVarietal().equals("n/a"))){
                    suggestions.add(anAlcohol.getGrapeVarietal());
                }
            }

            if(!(anAlcohol.getSourceOfProduct() == null)){
                if(!suggestions.contains(anAlcohol.getSourceOfProduct()) && !(anAlcohol.getSourceOfProduct().trim().isEmpty()) &&  !(anAlcohol.getSourceOfProduct().equals("n/a"))){
                    suggestions.add(anAlcohol.getSourceOfProduct());
                }
            }
            }
        }
    }
}