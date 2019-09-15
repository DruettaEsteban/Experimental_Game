import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.ProgressBar;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.event.EventHandler;


public class TimeBar extends ProgressBar {

    private Timeline timeLine;
    private final double COUNTDOWN_TIME_MILLIS;
    private final Color DEFAULT_COLOR;
    private double currentGreenAmount;
    private double currentTransitionTime;



    public TimeBar(int countdown_time) {
        DEFAULT_COLOR = Color.rgb(160, 255,0);
        COUNTDOWN_TIME_MILLIS = countdown_time;
    }

    private Color mapColor(){

        double weirdFactor = 1.0f - (this.currentTransitionTime/COUNTDOWN_TIME_MILLIS);
        currentGreenAmount =  (DEFAULT_COLOR.getGreen() * weirdFactor);
        System.out.println(weirdFactor +"    "+currentTransitionTime+ " / " + COUNTDOWN_TIME_MILLIS);
        return Color.rgb((int) (DEFAULT_COLOR.getRed()*255f), (int) (currentGreenAmount*255f), (int) (DEFAULT_COLOR.getBlue()*255f));
    }

    private void resetColor(){
        this.currentGreenAmount =  (int) (255 *DEFAULT_COLOR.getGreen());
    }

    volatile boolean  isFinished;

    void startCountdown(){
        isFinished = false;
        final long startTime = System.nanoTime();
        final TimeBar thisObject = this;


        Thread transitionThread = new Thread(() -> {
            while (!isFinished && currentTransitionTime != COUNTDOWN_TIME_MILLIS){
                currentTransitionTime = (int) ((System.nanoTime() - startTime)/1000000);
                Color newColor = mapColor();
                String colorRGB =  (int) (255f *newColor.getRed())+"," +(int) (255f*newColor.getGreen()) + ","+ (int) (255f*newColor.getBlue());
                Platform.runLater(()-> thisObject.setStyle("-fx-accent: rgb("+colorRGB+");"));
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });

        EventHandler<ActionEvent> handler = event -> stopCountdown();


        Platform.runLater(()->{
            this.timeLine = new Timeline();
            timeLine.setCycleCount(1);
            timeLine.getKeyFrames().addAll(
                    new KeyFrame(Duration.ZERO, new KeyValue(this.progressProperty(), 1)),
                    new KeyFrame(Duration.millis(COUNTDOWN_TIME_MILLIS),handler, new KeyValue(this.progressProperty(), 0
                    ))
            );
            timeLine.playFromStart();
        });
        transitionThread.start();

    }


    void stopCountdown(){
        Platform.runLater(() -> this.timeLine.stop());
        resetColor();
        currentTransitionTime = 0;
        isFinished = true;
    }


}
