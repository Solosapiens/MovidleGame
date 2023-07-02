module com.example.odev {
	requires javafx.controls;
	requires javafx.fxml;
	
	
	opens com.example.odev to javafx.fxml;
	exports com.example.odev;
}