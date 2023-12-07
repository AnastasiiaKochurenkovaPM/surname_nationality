package com.example.kursova_2;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class SurnameChecker extends Application {

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root);

        // Отримання розміру екрану
        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getVisualBounds().getWidth();
        double screenHeight = screen.getVisualBounds().getHeight();

        // Додавання світло-жовтого фону
        scene.setFill(Color.LIGHTYELLOW);

        // Кількість кульок
        int numOfCircles = 10;

        // Додавання кульок до сцени
        for (int i = 0; i < numOfCircles; i++) {
            Circle circle = createCircle(screenWidth, screenHeight, 15 + new Random().nextInt(200), Color.LIGHTBLUE, 0.4);
            root.getChildren().add(circle);

            // Анімація руху кульок
            animateCircleMovement(circle, 3000 + new Random().nextInt(5000), screenWidth, screenHeight);
        }

        // Додавання заголовка
        Label titleLabel = new Label("Checking the origin of your surname");
        titleLabel.setFont(Font.font("Segoe Print", FontWeight.BOLD, 38));
        titleLabel.setTextFill(Color.web("#336699"));
        titleLabel.setLayoutX(screenWidth/10);
        titleLabel.setLayoutY(screenHeight/3.5);
        root.getChildren().add(titleLabel);

        // Додавання поля для введення прізвища
        TextField surnameField = new TextField();
        surnameField.setPromptText("Enter your last name");
        surnameField.setStyle("-fx-background-radius: 40; -fx-pref-width: 400px; -fx-pref-height: 50px;");
        surnameField.setLayoutX(screenWidth/10);
        surnameField.setLayoutY(screenHeight/2.2);
        root.getChildren().add(surnameField);

        // Додавання кнопки "Перевірка"
        Button checkButton = new Button("Сheck");
        checkButton.setStyle("-fx-background-radius: 40; -fx-pref-width: 100px; -fx-pref-height: 50px; -fx-background-color: rgba(51,102,153,0.83); -fx-text-fill: white; -fx-font-weight: bold");


        checkButton.setOnMouseEntered(e -> {
            checkButton.setStyle("-fx-background-radius: 40; -fx-pref-width: 100px; -fx-pref-height: 50px; -fx-background-color: #41b3d7; -fx-text-fill: white; -fx-font-weight: bold");
        });


        checkButton.setOnMouseExited(e -> {
            checkButton.setStyle("-fx-background-radius: 40; -fx-pref-width: 100px; -fx-pref-height: 50px; -fx-background-color: rgba(51,102,153,0.83); -fx-text-fill: white; -fx-font-weight: bold");
        });
        checkButton.setLayoutX(screenWidth/2.6);
        checkButton.setLayoutY(screenHeight/2.2);
        root.getChildren().add(checkButton);

        // Відображення результату перевірки
        Label resultLabel = new Label();
        resultLabel.setFont(Font.font("Comic Sans MS", 20));
        resultLabel.setTextFill(Color.web("#336699"));
        resultLabel.setLayoutX(screenWidth/10);
        resultLabel.setLayoutY(screenHeight/1.65);
        resultLabel.setWrapText(true); // Додайте цей рядок
        root.getChildren().add(resultLabel);

        Label resultLabelTitle = new Label();
        resultLabelTitle.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 20));
        resultLabelTitle.setTextFill(Color.web("#336699"));
        resultLabelTitle.setLayoutX(screenWidth/10);
        resultLabelTitle.setLayoutY(screenHeight/1.77);
        root.getChildren().add(resultLabelTitle);

        // Обробник події для кнопки "Перевірка"
        checkButton.setOnAction(event -> {
            String surname = surnameField.getText().trim().toLowerCase();

            String pythonScriptPath = "C:\\Users\\KV-User\\Desktop\\surname_nationality\\Kursova_2\\server\\predict.py";
            String argument = surname;

            try {
                ProcessBuilder processBuilder = new ProcessBuilder("python", pythonScriptPath, argument);
                Process p = processBuilder.start();

                BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
                BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

                // Collect the output of the command
                StringBuilder output = new StringBuilder();

                resultLabelTitle.setText("Origin of your surname:");
                StringBuilder stringBuilder = new StringBuilder();

                String s;
                while ((s = stdInput.readLine()) != null) {
                    stringBuilder.append(s).append("\n");
                    resultLabel.setText(stringBuilder.toString());
                }

                System.out.println(stringBuilder.append(s).append("\n"));

                // read any errors from the attempted command
                System.out.println("Here is the standard error of the command (if any):\n");
                while ((s = stdError.readLine()) != null) {
                    System.err.println(s);
                }

                // You can now use the 'output' StringBuilder as needed
            } catch (IOException e) {
                System.out.println("Exception happened - here's what I know: ");
                e.printStackTrace();
                System.exit(-1);
            }
        });

        surnameField.setOnKeyTyped(event -> {
            resultLabel.setText(""); // Очищення мітки з інформацією про походження
            resultLabelTitle.setText(""); // Очищення заголовка мітки
        });

        primaryStage.setTitle("Checking the origin of the surname");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Додаємо обробник події для кнопки "esc"
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                primaryStage.close();
            }
        });

        // Встановлюємо, щоб вікно завжди було на повний екран
        primaryStage.setFullScreen(true);
        primaryStage.setTitle("Checking the origin of the surname");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Метод для створення кола з заданими параметрами
    private Circle createCircle(double screenWidth, double screenHeight, double radius, Color color, double opacity) {
        Circle circle = new Circle(radius, color);
        circle.setOpacity(opacity);
        circle.setTranslateX(screenWidth - circle.getRadius()); // Рухаються лише біля правого краю
        circle.setTranslateY(screenHeight * Math.random()); // Випадкове положення по вертикалі
        return circle;
    }

    // Метод для анімації руху кульок
    private void animateCircleMovement(Circle circle, int durationMillis, double screenWidth, double screenHeight) {
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        // Генерування випадкового напрямку руху (вгору або вниз)
        double direction = (Math.random() > 0.5) ? -1 : 1;
        double endY = circle.getTranslateY() + (direction * 50);

        KeyValue kvY = new KeyValue(circle.translateYProperty(), endY);
        KeyFrame kf = new KeyFrame(Duration.millis(durationMillis), kvY);
        timeline.getKeyFrames().add(kf);
        timeline.setAutoReverse(true); // Рухаються в обидва напрямки
        timeline.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

