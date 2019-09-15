import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class FrameMP extends Application{

    private final double ANSWER_HEIGHT = getPercentageHeight(4);
    private final double ANSWER_WIDTH = getPercentageWidth(45);
    private final double ANSWERS_SEPARATION = getPercentageHeight(5);
    private final double ANSWERS_BOTTOM_PADDING = getPercentageHeight(3);
    private final double RESPONSE_QUESTION_TOP_MARGIN = getPercentageHeight(3);
    private final double QUESTION_HEIGHT = getPercentageHeight(10);
    private final double QUESTION_WIDTH = getPercentageWidth(45);
    private final double QUESTION_CONTAINER_SPACING = getPercentageWidth(0.2);
    private final int FADE_TIME_MILLIS = 1000;
    private final int RESIZE_TIME_MILLIS = 1000;
    private final int FADE_COLOR_TIME_MILLIS = 1000;
    private final int COUNTDOWN_BAR_RIGHT_MARGIN = (int) getPercentageWidth(0);
    private final int COUNTDOWN_BAR_TOP_MARGIN = (int) getPercentageHeight(0);
    private final int COUNTDOWN_TIME_MILLIS = 20000;
    private final int COUNTDOWN_PADDING = (int) getPercentageHeight(3);


    private Label optionA, optionB, optionC, optionD, question;
    private LinkedList<Label> options;
    private VBox answersContainer;
    private VBox questionContainer;
    private TimeBar countdownBar;


    public void start(Stage primaryStage) {
        optionA = new Label();
        optionB = new Label();
        optionC = new Label();
        optionD = new Label();

        options = new LinkedList<>();
        Collections.addAll(options, optionA, optionB, optionC, optionD);

        options.forEach(e -> e.setWrapText(true));
        options.forEach(e -> e.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,20)));

        answersContainer = new VBox();
        answersContainer.setSpacing(ANSWERS_SEPARATION);
        answersContainer.setAlignment(Pos.BOTTOM_CENTER);


        VBox.setMargin(options.getLast(), new Insets(0, 0, ANSWERS_BOTTOM_PADDING,0));

        options.forEach(option -> option.setStyle("-fx-background-color: white; -fx-border-color: black;  -fx-border-width: 2; -fx-border-style: solid inside; -fx-border-radius: 50;"));
        options.forEach(option -> option.setMaxSize(ANSWER_WIDTH*2, ANSWER_HEIGHT*2));
        options.forEach(option -> option.setMinSize(ANSWER_WIDTH*2, ANSWER_HEIGHT*2));
        options.forEach(option -> option.setPrefSize(ANSWER_WIDTH*2, ANSWER_HEIGHT*2));
        options.forEach(option -> option.setAlignment(Pos.CENTER));

        ObservableList<javafx.scene.Node> list = answersContainer.getChildren();
        list.addAll(optionA, optionB,optionC, optionD);

        //QUESTION SETTINGS
        questionContainer = new VBox();
        questionContainer.setSpacing(QUESTION_CONTAINER_SPACING);
        questionContainer.setAlignment(Pos.TOP_CENTER);
        question = new Label();
        VBox.setMargin(question, new Insets(RESPONSE_QUESTION_TOP_MARGIN, 0, 0,0));
        question.setStyle("-fx-border-color: black;  -fx-border-width: 2; -fx-border-style: solid inside; -fx-border-radius: 50;");
        question.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,30));
        question.setMaxSize(QUESTION_WIDTH*2, QUESTION_HEIGHT*2);
        question.setMinSize(QUESTION_WIDTH*2, QUESTION_HEIGHT*2);
        question.setPrefSize(QUESTION_WIDTH*2, QUESTION_HEIGHT* 2);
        question.setWrapText(true);
        question.setAlignment(Pos.CENTER);

        StackPane mainContainer = new StackPane();
        mainContainer.getChildren().addAll(questionContainer, answersContainer);

        //EXPERIMENTAL
        countdownBar = new TimeBar(COUNTDOWN_TIME_MILLIS);
        mainContainer.setAlignment(Pos.TOP_CENTER);
        StackPane.setMargin(countdownBar, new Insets(COUNTDOWN_BAR_TOP_MARGIN, COUNTDOWN_BAR_RIGHT_MARGIN, 0 , 0));
        mainContainer.getChildren().add(countdownBar);
        countdownBar.setPrefWidth(getPercentageWidth(100));
        countdownBar.setMinWidth(getPercentageWidth(100));
        countdownBar.setMaxWidth(getPercentageWidth(100));
        countdownBar.setPrefHeight(getPercentageHeight(5));
        countdownBar.setStyle("-fx-accent: rgb(114,255,0);");

        countdownBar.startCountdown();

        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        executor.schedule(new Runnable() {
            @Override
            public void run() {
                countdownBar.startCountdown();
            }
        }, 28, TimeUnit.SECONDS);



        Scene scene = new Scene(mainContainer);

        primaryStage.setFullScreen(true);
        primaryStage.setTitle("Testing game");
        primaryStage.setScene(scene);
        primaryStage.show();




    }



    //public long getMillisRemainingTime(){ return (long) this.countdownBar.remainingTime().toMillis(); }


    /*public int getCountdownMaxTime(){
        return this.COUNTDOWN_TIME_MILLIS;
    }*/

    private static boolean isAddingQuestions = false;

    public static void main(String[] args){
        launch();
    }

    private static double getWidth(){
        Rectangle2D rectangle2D = Screen.getPrimary().getVisualBounds();
        return rectangle2D.getWidth();
    }

    private static double getHeight(){
        Rectangle2D rectangle2D = Screen.getPrimary().getVisualBounds();
        return rectangle2D.getHeight();
    }

    private static double getPercentageWidth(double percentage){
        return ((getWidth() / 100f) * percentage);
    }

    private static double getPercentageHeight(double percentage){
        return ((getHeight() / 100f) * percentage);
    }




    //ITALIANISIMO
    //Sonidos italianos


}
