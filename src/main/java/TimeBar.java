import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.application.Platform;
import javafx.scene.control.ProgressBar;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class TimeBar extends ProgressBar {

    private Timeline timeLine;
    private final int COUNTDOWN_TIME_MILLIS;


    public TimeBar(int countdown_time) {
        COUNTDOWN_TIME_MILLIS = countdown_time;
    }


    void startCoundown(){
        Platform.runLater(()->{
            this.timeLine = new Timeline();
            timeLine.setCycleCount(Timeline.INDEFINITE);
            timeLine.getKeyFrames().addAll(
                    new KeyFrame(Duration.ZERO, new KeyValue(this.progressProperty(), 1)),
                    new KeyFrame(Duration.millis(COUNTDOWN_TIME_MILLIS), new KeyValue(this.progressProperty(), 0
                    ))
            );

            timeLine.playFromStart();
        });
    }

    void stopCountdown(){
        Platform.runLater(() -> this.timeLine.stop());
    }


}
