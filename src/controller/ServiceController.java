package controller;

import model.CafeteriaService;
import model.InterpreterService;
import model.JanitorService;
import model.ServiceRequest;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import controller.Main;

public class ServiceController {
    // Attributes
    private String serviceNeeded;
    private ServiceRequest service;

    @FXML
    private MenuButton EmployeeDropdown, FoodDropdown,
            LocationDropdown, RequestServiceDropdown;

    @FXML
    private Button removeNodeSelector;

    @FXML
    private JFXTextField NotesTextFeild;

    @FXML
    private MenuItem AdmGrant;

    // Getters
    public String getServiceNeeded() {
        return this.serviceNeeded;
    }

    public ServiceRequest getService() {
        return this.service;
    }

    // Setters
    public void setServiceNeeded(String service){
        this.serviceNeeded = service;
    }

    public void setService (){
        String needed = this.RequestServiceDropdown.getText();

        if(needed.toUpperCase() == "INTERPRETER"){
            service = new InterpreterService();
        } else if(needed.toUpperCase() == "JANITOR"){
            service = new JanitorService();
        } else {
            service = new CafeteriaService();
        }
    }

    // FXML Methods
    @FXML
    void BackToUIV1( ) {
        Main.getCurrStage().setScene(Main.getService());
    }

    @FXML
    void SubmitRequest( ) {

    }

    @FXML
    void MateninceItem() {
        RequestServiceDropdown.setText("Maintenance");
    }

    @FXML
    void InterpreterItem( ) {

        RequestServiceDropdown.setText("Interpreter");
    }

    @FXML
    void NoodlesItem( ) {
        FoodDropdown.setText("Noodles");
    }

    @FXML
    void BurgerItem( ) {
        FoodDropdown.setText("Burger");
    }

    @FXML
    void TapiocaItem( ) {
        FoodDropdown.setText("Tapioca");
    }


    @FXML
    void WaitingRoomItem( ) {
        LocationDropdown.setText("WaitingRoom");
    }

    @FXML
    void XrayItem( ) {
        LocationDropdown.setText("Xray");

    }

    @FXML
    void DrDembskiItem( ) {
        EmployeeDropdown.setText("Dr. Dembski");
    }

    @FXML
    void AdmGrantItem( ) {
        EmployeeDropdown.setText("Admin Grant");

    }
}
