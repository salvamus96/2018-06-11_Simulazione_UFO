/**
 * Sample Skeleton for 'Ufo.fxml' Controller Class
 */

package it.polito.tdp.ufo;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.ufo.model.AnnoCount;
import it.polito.tdp.ufo.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class UfoController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxAnno"
    private ComboBox<AnnoCount> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxStato"
    private ComboBox<String> boxStato; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    
    @FXML
    void handleAvvistamenti(ActionEvent event) {
    	this.txtResult.clear();
    	this.boxStato.getItems().clear();
    	
    	AnnoCount anno = this.boxAnno.getValue();
    	if (anno == null) {
    		this.txtResult.appendText("ERRORE: selezionare un anno!\n");
    		return;
    	}
    	
    	model.creaGrafo(anno.getAnno());
    	
    	this.boxStato.getItems().addAll(model.getStati());
    }

    @FXML
    void handleAnalizza(ActionEvent event) {
    	
    	String stato = this.boxStato.getValue();
    	
    	if (stato == null) {
    		this.txtResult.appendText("ERRORE: selezionare uno stato!\n");
    		return;
    	}
    	
    	List <String> precedenti = model.getStatiPrecedenti(stato);
       	List <String> successivi = model.getStatiSuccessivi(stato);
       	List <String> raggiungibili = model.getStatiRaggiungibili(stato);
       	
       	this.txtResult.appendText("Stati PRECEDENTI \n");
     	this.txtResult.appendText(precedenti.toString() + "\n");

       	this.txtResult.appendText("Stati SUCCESSIVI \n");
     	this.txtResult.appendText(successivi.toString() + "\n");
     	
       	this.txtResult.appendText("Stati RAGGIUNGIBILI \n");
     	this.txtResult.appendText(raggiungibili.toString() + "\n");
    }
    
    @FXML
    void handleSequenza(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Ufo.fxml'.";
        assert boxStato != null : "fx:id=\"boxStato\" was not injected: check your FXML file 'Ufo.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Ufo.fxml'.";

    }
    
    public void setModel (Model model) {
    	this.model = model;
    	
    	this.boxAnno.getItems().addAll(model.getAnniAvvistamenti());
    }
}
