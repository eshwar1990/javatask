package com.bansira.javatask.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * 
 * @author J Bharat Eshwar Reddy
 * 
 * Client used for command line based interface for testing the Restful end points
 *
 */
public class LibraryMenu {
	
	private static final String BASE_URL = "http://localhost:8080/books";
	
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Library Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. Search Book by Title");
            System.out.println("4. Search Book by Author");
            System.out.println("5. List All Books");
            System.out.println("6. List All Available Books");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    addBook(scanner);
                    break;
                case 2:
                    removeBook(scanner);
                    break;
                case 3:
                    searchBookByTitle(scanner);
                    break;
                case 4:
                    searchBookByAuthor(scanner);
                    break;
                case 5:
                    listAllBooks();
                    break;
                case 6:
                    listAvailableBooks();
                    break;
                case 7:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void addBook(Scanner scanner) {
        try {
            System.out.print("Enter title: ");
            String title = scanner.nextLine();
            System.out.print("Enter author: ");
            String author = scanner.nextLine();
            System.out.print("Enter ISBN: ");
            String isbn = scanner.nextLine();
            System.out.print("Enter genre: ");
            String genre = scanner.nextLine();
            System.out.print("Enter publication year: ");
            int publicationYear = scanner.nextInt();
            System.out.print("Is the book available? (true/false): ");
            boolean available = scanner.nextBoolean();
            System.out.print("Enter department ID: ");
            long departmentId = scanner.nextLong();
            scanner.nextLine();  // Consume newline

            String bookJson = String.format(
                    "{\"title\":\"%s\",\"author\":\"%s\",\"isbn\":\"%s\",\"genre\":\"%s\",\"publicationYear\":%d,\"available\":%b,\"departmentId\":%d}",
                    title, author, isbn, genre, publicationYear, available, departmentId);

            URL url = new URL(BASE_URL + "/add");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = bookJson.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            if(conn.getResponseCode() == HttpURLConnection.HTTP_OK)
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
            }
            else {
            	try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    System.out.println(response.toString());
                }
            }

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void removeBook(Scanner scanner) {
        try {
            System.out.print("Enter ISBN of the book to remove: ");
            String isbn = scanner.nextLine();

            URL url = new URL(BASE_URL + "/remove?isbn=" + URLEncoder.encode(isbn, StandardCharsets.UTF_8.toString()));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Book removed successfully.");
            } else {
                System.out.println("Failed to remove book. Response code: " + responseCode);
            }

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void searchBookByTitle(Scanner scanner) {
        try {
            System.out.print("Enter title: ");
            String title = scanner.nextLine();

            URL url = new URL(BASE_URL + "/title?name=" + URLEncoder.encode(title, StandardCharsets.UTF_8.toString()));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
            }

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void searchBookByAuthor(Scanner scanner) {
        try {
            System.out.print("Enter author: ");
            String author = scanner.nextLine();

            URL url = new URL(BASE_URL + "/author?name=" + URLEncoder.encode(author, StandardCharsets.UTF_8.toString()));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
            }

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void listAllBooks() {
        try {
            URL url = new URL(BASE_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
            }

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void listAvailableBooks() {
        try {
            URL url = new URL(BASE_URL + "/available");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
            }

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
