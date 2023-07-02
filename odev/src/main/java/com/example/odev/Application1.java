package com.example.odev;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.geometry.Pos;

import java.util.Random;

import javafx.scene.layout.HBox;

public class Application1 extends Application {
	
	public static void main(String[] args) {
		
		launch(args);
	}
	
	private int hak = 0;
	private int i = 1;
	
	String[] diziAd = Imdblist.movieName;
	String[] diziYil = Imdblist.movieYear;
	String[] diziTur = Imdblist.movieGenre;
	String[] diziYer = Imdblist.movieOrigin;
	String[] diziYon = Imdblist.movieDirector;
	String[] diziStar = Imdblist.movieStar;
	
	private VBox root = new VBox();
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		TextArea textArea = new TextArea();
		Imdblist.olay(null, textArea);
		
		primaryStage.setTitle("Movidle Movie Guessing Game");
		
		TextField textField = new TextField();
		textField.setPromptText("Bir film tahmin et!");
		textField.setFocusTraversable(false);
		textField.setStyle("-fx-text-fill: grey;");
		textField.setMaxWidth(220.0f);
		
		Button button = new Button("Tahmin!");
		
		Random random = new Random();
		int randomIndex = random.nextInt(250);
		String secilenFilm = Imdblist.RandomFilm(randomIndex);
		
		Label gecersiz = new Label();
		gecersiz.setTextFill(Color.WHITE);
		gecersiz.setAlignment(Pos.CENTER);
		gecersiz.setVisible(false);
		
		Label bilgi = new Label("Oyuna Hoş Geldiniz.\n" +
				"Oyun şöyle oynanır, IMDB ilk 250 filmden birine ihtiyacınız var. Örneğin La La Land yazarak başlayabilirisiniz.\n" +
				"İlk tahmininizi yaptıktan sonra tahmin ettiğiniz filme ait özellikler aşağıda belirecektir.\n" +
				"Eğer ki özellikler kırmızı ise aradığınız filmde bu özellik bulunmuyor demektir.\n" +
				"Eğer ki özellikler yeşilse ise aradığınız filmde bu özellik bulunuyor demektir ve buna göre tahmininizi değiştirmelisiniz.\n" +
				"Sadece 5 tahmin hakkınızın olduğunu unutmayın.\n" +
				"İYİ EĞLENCELER :)"
		);
		bilgi.setTextFill(Color.GOLD);
		bilgi.setAlignment(Pos.CENTER);
		
		HBox inputContainer = new HBox();
		inputContainer.setAlignment(Pos.CENTER);
		inputContainer.setSpacing(10.0);
		inputContainer.getChildren().addAll(textField, button);
		
		
		VBox hboxContainer = new VBox();
		hboxContainer.setSpacing(10.0);
		hboxContainer.setAlignment(Pos.CENTER);
		
		EventHandler<ActionEvent> olayYoneticisi = event -> {
			
			String guessText = textField.getText();
			System.out.println("Entered Text: " + guessText);
			textField.clear();
			gecersiz.setVisible(false);
			
			boolean found = false;
			
			for (int k = 0; k < 250; k++) {
				if (guessText.equalsIgnoreCase(diziAd[k])) {
					found = true;
					break;
				}
				i++;
			}
			
			if (found) {
				System.out.println("Değer dizide bulundu.");
				System.out.println(i);
				i = i - 1;
				System.out.println(diziAd[i]);
				String[][] dizi = {diziAd, diziYil, diziTur, diziYer, diziYon, diziStar};
				
				HBox hbox = new HBox();
				hbox.setSpacing(15.0);
				for (int l = 0; l < 6; l++) {
					Label label = new Label();
					label.setPrefHeight(50.0f);
					label.setPrefWidth(220.0f);
					label.setStyle("-fx-border-color: white");
					label.setTextAlignment(TextAlignment.CENTER);
					label.setText("   " + dizi[l][i]);
					if (dizi[l][i] != null && dizi[l][i].equalsIgnoreCase(dizi[l][randomIndex])) {
						label.setStyle("-fx-border-color: white; -fx-background-color: green;");
					} else {
						label.setStyle("-fx-border-color: white; -fx-background-color: red;");
					}
					hbox.getChildren().addAll(label);
				}
				hboxContainer.getChildren().add(hbox);
				
				i = 1;
				
				if (guessText.equalsIgnoreCase(secilenFilm)) {
					System.out.println("Bildin!");
					GoAlert("Son");
					
				} else {
					if (hak == 4) {
						System.out.println("Deneme hakkı bitti.");
						textField.setDisable(true);
						button.setDisable(true);
						textField.setPromptText("GAME OVER!");
						System.out.println("Doğru film: " + secilenFilm);
						GoAlert(secilenFilm);
						primaryStage.close();
						
					} else {
						System.out.println("Denemeye devam et.");
						gecersiz.setVisible(true);
						gecersiz.setText("Tekrar dene.");
					}
					
					hak++;
				}
				
			} else {
				System.out.println("Lütfen geçerli bir film ismi girin.");
				gecersiz.setVisible(true);
				gecersiz.setText("Geçerli bir tahmininde bulunun.");
				i = 1;
			}
		};
		
		EventHandler<KeyEvent> enterPressedHandler = event -> {
			if (event.getCode() == KeyCode.ENTER) {
				olayYoneticisi.handle(new ActionEvent());
			}
		};
		
		textField.setOnKeyPressed(enterPressedHandler);
		button.setOnAction(olayYoneticisi);
		
		
		root.getChildren().addAll(bilgi, inputContainer, gecersiz, hboxContainer);
		root.setSpacing(10.0);
		
		StackPane.setMargin(root, new Insets(90, 0, 0, 90));
		
		StackPane sp = new StackPane();
		sp.setStyle("-fx-background-image: url('bg.jpeg');"
				+ "-fx-background-size: 800 800");
		
		sp.getChildren().addAll(root);
		
		Scene scene = new Scene(sp, 800, 800);
		primaryStage.setScene(scene);
		
		primaryStage.show();
		
	}
	
	public void GoAlert(String s) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		
		if (s.equals("Son")) {
			alert.setTitle("Kazandın!");
			alert.setHeaderText("Kazandın!");
		} else {
			alert.setTitle("Oyun Bitti");
			alert.setHeaderText("Kaybettin!\n\nDoğru cevap: " + s);
		}
		
		ButtonType cikisButton = new ButtonType("Çıkış");
		ButtonType yenidenOynaButton = new ButtonType("Yeniden Oyna");
		alert.getButtonTypes().setAll(cikisButton, yenidenOynaButton);
		
		alert.showAndWait().ifPresent(buttonType -> {
			
			if (buttonType == cikisButton) {
				Platform.exit();
			} else if (buttonType == yenidenOynaButton) {
				Stage newStage = new Stage();
				hak = -1;
				root.getChildren().clear();
				Platform.runLater(() -> {
					try {
						start(newStage);
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
			}
		});
	}
}