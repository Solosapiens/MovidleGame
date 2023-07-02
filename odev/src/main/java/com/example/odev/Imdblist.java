package com.example.odev;

import java.io.*;
import java.lang.System;
import java.io.BufferedReader;

import javafx.scene.control.TextArea;
import javafx.event.ActionEvent;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;


public class Imdblist {
	public static String[][] movieList = new String[251][8];
	public static String[] movieName = new String[250];
	public static String[] movieYear = new String[250];
	public static String[] movieGenre = new String[250];
	public static String[] movieOrigin = new String[250];
	public static String[] movieDirector = new String[250];
	public static String[] movieStar = new String[250];
	
	public static String normalizeToLatin(String input) {
		String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
		normalized = normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
		return normalized;
	}
	
	public static void olay(ActionEvent event, TextArea textArea) {
		textArea.setEditable(false);
		
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(new FileInputStream("src/main/resources/imdb_top_250.csv"), StandardCharsets.ISO_8859_1))) {
			String line;
			
			int movieCount = 0;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(";");
				
				for (String part : parts) {
					textArea.appendText(part + "\n");
				}
				
				for (int i = 0; i < 251; i++) {
					System.arraycopy(parts, 0, movieList[movieCount], 0, parts.length);
				}
				movieCount++;
			}
			
			
			for (int i = 1; i < 251; i++) {
				String mNo = movieList[i][0];
				String mTitle = normalizeToLatin(movieList[i][1]);
				String mYear = movieList[i][2];
				String mGenre = movieList[i][3];
				String mOrigin = movieList[i][4];
				String mDirector = movieList[i][5];
				String mStar = movieList[i][6];
				String mLink = movieList[i][7];
				
				System.out.println("Movie Number: " + mNo);
				System.out.println("Movie Title: " + mTitle);
				System.out.println("Movie Year: " + mYear);
				System.out.println("Movie Genre: " + mGenre);
				System.out.println("Movie Origin: " + mOrigin);
				System.out.println("Movie Director: " + mDirector);
				System.out.println("Movie Star: " + mStar);
				System.out.println("Movie Link: " + mLink);
				System.out.println("------------------------");
				
				movieName[(i - 1)] = movieList[i][1];
				movieYear[(i - 1)] = movieList[i][2];
				movieGenre[(i - 1)] = movieList[i][3];
				movieOrigin[(i - 1)] = movieList[i][4];
				movieDirector[(i - 1)] = movieList[i][5];
				movieStar[(i - 1)] = movieList[i][6];
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String RandomFilm(int randomIndex) {
		
		String rFilm = movieName[randomIndex];
		
		return rFilm;
	}
}
	