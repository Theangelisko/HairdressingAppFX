package HairdressingApp.fx;

import HairdressingApp.data.Client;
import HairdressingApp.data.Hairdressing;
import HairdressingApp.data.Personal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Button deleteAppointment;
    @FXML
    private ListView<String> listAppointments;
    @FXML
    private Button makeAppointment;
    @FXML
    private ComboBox<String> appointmentTime;
    @FXML
    private ComboBox<String>  appointmentService;
    @FXML
    private ComboBox<String> appointmentDate;
    @FXML
    private TextField searchUsername;
    @FXML
    private Label fullName;
    @FXML
    private Button btnCheckUsername;
    @FXML
    private Label registeredUser;
    @FXML
    private AnchorPane appointmentsProcess;
    @FXML
    private AnchorPane appointmentsPanel;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private Button btnLogin;

    ArrayList<Personal> personals = new ArrayList<>();
    ArrayList<Client> clients = new ArrayList<>();
    Alert dialog = new Alert(Alert.AlertType.ERROR);
    int clientPosition = -1;
    int actualAppointment = 0;
    ArrayList<Hairdressing> appointments = new ArrayList<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try{
            BufferedReader loadPersonal =
                    new BufferedReader(new FileReader("personal.csv"));
            String line;
            String[] data;
            int counter = 0;
            while((line=loadPersonal.readLine()) != null) {
                data = line.split(":");
                personals.add(new Personal(data[0],data[1],data[2],data[3],
                        Integer.parseInt(data[4]),data[5],Integer.parseInt(data[6])));
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }

        try{
            BufferedReader loadClient =
                    new BufferedReader(new FileReader("client.csv"));
            String line;
            String[] data;
            int counter = 0;
            while((line=loadClient.readLine()) != null) {
                data = line.split(":");
                clients.add(new Client(data[0],data[1],data[2],data[3],
                        Integer.parseInt(data[4]),data[5]));
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }

        appointmentsPanel.setVisible(false);
        appointmentsProcess.setVisible(false);
        appointmentService.getItems().addAll("Hair wash - 30 min","Haircut - 30 min.",
                "Hair Color - 1H", "Hairstyle (Women) - 2H");
        appointmentDate.getItems().addAll("17/05/21");
        appointmentTime.getItems().addAll("09:00","09:30","10:00","10:30","11:00",
                "11:30","12:00","12:30","13:00","16:00","16:30","17:00","17:30",
                "18:00","18:30","19:00","19:30","20:00");
    }

    public void checkLogin(ActionEvent actionEvent) {

        boolean userFound = false;
        String username = "";

        for(int i = 0; i < personals.size(); i++) {
            if(personals.get(i).getUsername().equals(txtUsername.getText()) &&
                    personals.get(i).getPassword().equals(txtPassword.getText())){
                userFound = true;
                username = personals.get(i).getUsername();
            }
        }

        if(txtUsername.getText().isEmpty() || txtPassword.getText().isEmpty()){
            dialog.setTitle("Error");
            dialog.setHeaderText("Error login");
            dialog.setContentText("Fields can't be empty");
            dialog.showAndWait();
        }
        else {
            if(userFound){
                txtUsername.setText("");
                txtPassword.setText("");
                appointmentsPanel.setVisible(true);
                appointmentsProcess.setVisible(true);
                registeredUser.setText(username);
            }
            else if(txtUsername.getText().equals("admin") && txtPassword.getText().equals("admin")){
                dialog.setTitle("Error");
                dialog.setHeaderText("Admin disable");
                dialog.setContentText("Each personal has their own username :)");
                dialog.showAndWait();
            }
            else {
                dialog.setTitle("Error");
                dialog.setHeaderText("Error login");
                dialog.setContentText("Wrong username or password");
                dialog.showAndWait();
            }
        }
    }

    public void checkUsername(ActionEvent actionEvent) {
        boolean clientFound = false;

        for(int i = 0; i < clients.size(); i++) {
            if(clients.get(i).getUsername().equals(searchUsername.getText())){
                clientFound = true;
                clientPosition = i;
                fullName.setText(clients.get(i).getSurnames() + ", " +
                        clients.get(i).getName());
            }
        }
        if(!clientFound){
            dialog.setTitle("Error");
            dialog.setHeaderText("Error Search Client");
            dialog.setContentText("Client not found");
            dialog.showAndWait();
        }
    }

    public void btnMakeAppointment(ActionEvent actionEvent) {
        String service = appointmentService.getSelectionModel().getSelectedItem();
        String date = appointmentDate.getSelectionModel().getSelectedItem();
        String time = appointmentTime.getSelectionModel().getSelectedItem();
        int deleteTimes = 0;
        boolean timesNotFound = false;

        if(service == null || date == null || time == null ||
                fullName.getText().isEmpty()) {
            dialog.setTitle("Error");
            dialog.setHeaderText("Error Making the Appointment");
            dialog.setContentText("You have to fill in all the fields");
            dialog.showAndWait();
        }
        else{
            String typeService = service.substring(0,service.indexOf("-")-1);
            boolean error = false;

            for (int count = 0; count < appointmentTime.getItems().size(); count++){
                if(time == appointmentTime.getItems().get(count)){
                    deleteTimes = count;
                }
            }

            int lockTime = deleteTimes;

            if(typeService.equals("Hair wash") || typeService.equals("Haircut")){
                if(!appointmentTime.getItems().get(deleteTimes).equals("--:--")){
                    appointmentTime.getItems().remove(deleteTimes);
                    appointmentTime.getItems().add(lockTime,"--:--");
                }
                else{
                    messageTimesNotFound();
                    timesNotFound = true;
                }
            }
            else if(typeService.equals("Hair Color")){
                if(appointmentTime.getItems().size() >= 2){
                    if(appointmentTime.getItems().size() >= 2 && time.equals("13:00") ||
                            appointmentTime.getItems().size() >= 2 && time.equals("20:00")){
                        error = true;
                    }
                    else{
                        timesNotFound = checkTimes(2, time,deleteTimes);

                        if(!timesNotFound){
                            for(int count = 0; count < 2; count++){
                                appointmentTime.getItems().remove(deleteTimes+count);
                                appointmentTime.getItems().add(lockTime+count,"--:--");
                            }
                        }
                    }
                }
                else {
                    messageTimesNotFound();
                }
            }
            else if(typeService.equals("Hairstyle (Women)")){
                if(appointmentTime.getItems().size() >= 4){
                    if(appointmentTime.getItems().size() >= 4 && time.equals("12:00") ||
                            appointmentTime.getItems().size() >= 4 && time.equals("12:30") ||
                            appointmentTime.getItems().size() >= 4 && time.equals("13:00") ||
                            appointmentTime.getItems().size() >= 4 && time.equals("19:00") ||
                            appointmentTime.getItems().size() >= 4 && time.equals("19:30") ||
                            appointmentTime.getItems().size() >= 4 && time.equals("20:00")){
                        error = true;
                    }
                    else {
                        timesNotFound = checkTimes(4, time,deleteTimes);

                        if(!timesNotFound){
                            for(int count = 0; count < 4; count++){
                                appointmentTime.getItems().remove(deleteTimes+count);
                                appointmentTime.getItems().add(lockTime+count,"--:--");
                            }
                        }
                    }
                }
                else {
                    messageTimesNotFound();
                }
            }
            if(error){
                dialog.setTitle("Error");
                dialog.setHeaderText("Error Making the Appointment");
                dialog.setContentText("Respect the limits of the hours that " +
                        "people need to rest");
                dialog.showAndWait();
            }
            else{
                if(!timesNotFound){
                    appointments.add(new Hairdressing(clients.get(clientPosition),date,time,typeService));
                    listAppointments.getItems().add(appointments.get(actualAppointment).toString());
                    actualAppointment++;
                }
            }
        }
    }

    public void btnDeleteAppointment(ActionEvent actionEvent) {
        int selected = listAppointments.getSelectionModel().getSelectedIndex();

        if(selected >= 0){
            String service = appointments.get(selected).getService();
            String time = appointments.get(selected).getTime();
            int addTimes = 0;

            if(service.equals("Hair wash") || service.equals("Haircut")){
                addTimes = 1;
            }
            else if(service.equals("Hair Color")){
                addTimes = 2;
            }
            else if(service.equals("Hairstyle (Women)")){
                addTimes = 4;
            }

            listAppointments.getItems().remove(selected);

            switch (time){
                case "09:00":
                    addTimes(0,addTimes,time);
                    break;
                case "09:30":
                    addTimes(1,addTimes,time);
                    break;
                case "10:00":
                    addTimes(2,addTimes,time);
                    break;
                case "10:30":
                    addTimes(3,addTimes,time);
                    break;
                case "11:00":
                    addTimes(4,addTimes,time);
                    break;
                case "11:30":
                    addTimes(5,addTimes,time);
                    break;
                case "12:00":
                    addTimes(6,addTimes,time);
                    break;
                case "12:30":
                    addTimes(7,addTimes,time);
                    break;
                case "13:00":
                    addTimes(8,addTimes,time);
                    break;
                case "16:00":
                    addTimes(9,addTimes,time);
                    break;
                case "16:30":
                    addTimes(10,addTimes,time);
                    break;
                case "17:00":
                    addTimes(11,addTimes,time);
                    break;
                case "17:30":
                    addTimes(12,addTimes,time);
                    break;
                case "18:00":
                    addTimes(13,addTimes,time);
                    break;
                case "18:30":
                    addTimes(14,addTimes,time);
                    break;
                case "19:00":
                    addTimes(15,addTimes,time);
                    break;
                case "19:30":
                    addTimes(16,addTimes,time);
                    break;
                case "20:00":
                    addTimes(17,addTimes,time);
                    break;
            }
        }
        else{
            dialog.setTitle("Error");
            dialog.setHeaderText("Error deleting Appointment");
            dialog.setContentText("No Appointment selected");
            dialog.showAndWait();
        }
    }
    public void addTimes(int position, int addTimes, String time){
        String formatTime = time;
        String minutes;
        String hours;
        int hoursInt;
        int unlockTime = position;
        for(int count = 0; count < addTimes; count++){
            appointmentTime.getItems().remove(unlockTime+count);
            appointmentTime.getItems().add(position+count,formatTime);

            minutes = formatTime.substring(3);
            hours = formatTime.substring(0,2);
            if(minutes.equals("30")){
                minutes = "00";
                hoursInt = Integer.parseInt(hours);
                hoursInt = hoursInt + 1;
                hours = "" + hoursInt;
            }
            else{
                minutes = "30";
            }
            formatTime = hours + ":" + minutes;
        }
    }
    public boolean checkTimes(int number, String time, int deleteTimes){
        String formatTime = time;
        String minutes;
        String hours;
        int hoursInt;
        boolean timeNotFound = false;

        for(int count = 0; count < number; count++){
            minutes = formatTime.substring(3);
            hours = formatTime.substring(0,2);
            if(minutes.equals("30")){
                minutes = "00";
                try{
                    hoursInt = Integer.parseInt(hours);
                    hoursInt = hoursInt + 1;
                    hours = "" + hoursInt;
                }
                catch(Exception e){
                    timeNotFound = true;
                }

            }
            else{
                minutes = "30";
            }
            if(!formatTime.equals(appointmentTime.getItems().get(deleteTimes+count))){
                timeNotFound = true;
            }
            formatTime = hours + ":" + minutes;
        }

        if(timeNotFound){
            messageTimesNotFound();
        }

        return timeNotFound;
    }
    public void messageTimesNotFound(){
        dialog.setTitle("Error");
        dialog.setHeaderText("Error Making the Appointment");
        dialog.setContentText("Not enough time blocks available");
        dialog.showAndWait();
    }
}
