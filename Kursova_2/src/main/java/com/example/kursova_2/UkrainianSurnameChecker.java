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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

public class UkrainianSurnameChecker extends Application {

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
            animateCircleMovement(circle, 5000 + new Random().nextInt(5000), screenWidth, screenHeight);
        }

        // Додавання заголовка
        Label titleLabel = new Label("Перевірка чи Ваше прізвище українське");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        titleLabel.setTextFill(Color.DARKBLUE);
        titleLabel.setLayoutX(20);
        titleLabel.setLayoutY(40);
        root.getChildren().add(titleLabel);

        // Додавання поля для введення прізвища
        TextField surnameField = new TextField();
        surnameField.setPromptText("Введіть прізвище");
        surnameField.setStyle("-fx-background-radius: 20;");
        surnameField.setLayoutX(20);
        surnameField.setLayoutY(100);
        root.getChildren().add(surnameField);

        // Додавання кнопки "Перевірка"
        Button checkButton = new Button("Перевірка");
        checkButton.setStyle("-fx-background-radius: 20;");
        checkButton.setLayoutX(20);
        checkButton.setLayoutY(150);
        root.getChildren().add(checkButton);

        // Відображення результату перевірки
        Label resultLabel = new Label();
        resultLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        resultLabel.setTextFill(Color.BLACK);
        resultLabel.setLayoutX(20);
        resultLabel.setLayoutY(200);
        root.getChildren().add(resultLabel);

        // Обробник події для кнопки "Перевірка"
        checkButton.setOnAction(event -> {
            String surname = surnameField.getText().trim().toLowerCase();
            boolean isUkrainian = checkUkrainianSurname(surname);

            if (isUkrainian) {
                resultLabel.setText("Так, прізвище українське");
            } else {
                resultLabel.setText("Ні, ваше прізвище має інакше походження");
            }
        });

        primaryStage.setTitle("Перевірка українського прізвища");
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

    // Метод для перевірки українського походження прізвища
    private boolean checkUkrainianSurname(String surname) {
        // Приклад: перевірка за суфіксом "ко"
        return surname.endsWith("ко");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
