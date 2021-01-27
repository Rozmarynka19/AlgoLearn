package algolearn.gui;

import algolearn.gui.info.errors;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;


public class VisualisationControllerFFT extends FXMLDocumentController implements Initializable {

    @FXML
    //id for each top AnchorPane element in every window-fxmlFile
    //necessary for switching between windows
    private AnchorPane anchorPaneRoot;


    /**
     * @param anchorPane - AnchorPane to add into root anchorPane
     *                   <p>
     *                   Remove and add specific anchorPane to the sceen
     */
    public void setScreen(AnchorPane anchorPane) {
        anchorPaneRoot.getChildren().clear();
        anchorPaneRoot.getChildren().add(anchorPane);
    }


    @FXML
    public void BackToMainStage(ActionEvent event) {
        mp.pause();
        loadMenu();
    }

    private errors errorMSG = new errors();


    @FXML
    MediaView film;
    MediaPlayer mp;
    @FXML
    Button PlayBtn;
    private boolean stopRequested = false;
    private final boolean repeat = false;
    private boolean atEndOfMedia = false;
    @FXML
    private Label playTime;
    private Duration duration;
    @FXML
    private Slider TimeBar;
    @FXML
    private Slider SoundBar;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        Media media = new Media(""+getClass().getResource("video/fft.mp4"));
        mp = new MediaPlayer(media);
        film.setMediaPlayer(mp);
        PlayBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                MediaPlayer.Status status = mp.getStatus();
                if (status == MediaPlayer.Status.UNKNOWN || status == MediaPlayer.Status.HALTED) {
                    return;
                }

                if (status == MediaPlayer.Status.PAUSED
                        || status == MediaPlayer.Status.READY
                        || status == MediaPlayer.Status.STOPPED) {
                    if (atEndOfMedia) {
                        mp.seek(mp.getStartTime());
                        atEndOfMedia = false;
                    }
                    mp.play();
                } else {
                    mp.pause();
                }
            }
        });
        mp.currentTimeProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                updateValues();
            }
        });

        mp.setOnPlaying(new Runnable() {
            public void run() {
                if (stopRequested) {
                    mp.pause();
                    stopRequested = false;
                } else {
                    PlayBtn.setText("||");
                }
            }
        });

        mp.setOnPaused(new Runnable() {
            public void run() {
                PlayBtn.setText(">");
            }
        });

        mp.setOnReady(new Runnable() {
            public void run() {
                duration = mp.getMedia().getDuration();
                updateValues();
            }
        });

        mp.setCycleCount(repeat ? MediaPlayer.INDEFINITE : 1);
        mp.setOnEndOfMedia(new Runnable() {
            public void run() {
                if (!repeat) {
                    PlayBtn.setText(">");
                    stopRequested = true;
                    atEndOfMedia = true;
                }
            }
        });

        TimeBar.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                if (TimeBar.isValueChanging()) {
                    mp.seek(duration.multiply(TimeBar.getValue() / 100.0));
                }
            }
        });
        SoundBar.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                if (SoundBar.isValueChanging()) {
                    mp.setVolume(SoundBar.getValue() / 100.0);
                }
            }
        });


    }

    protected void updateValues() {
        if (playTime != null && TimeBar != null && SoundBar != null) {
            Platform.runLater(new Runnable() {
                public void run() {
                    Duration currentTime = mp.getCurrentTime();
                    playTime.setText(formatTime(currentTime, duration));
                    TimeBar.setDisable(duration.isUnknown());
                    if (!TimeBar.isDisabled()
                            && duration.greaterThan(Duration.ZERO)
                            && !TimeBar.isValueChanging()) {
                        TimeBar.setValue(currentTime.divide(duration).toMillis()
                                * 100.0);
                    }
                    if (!SoundBar.isValueChanging()) {
                        SoundBar.setValue((int) Math.round(mp.getVolume()
                                * 100));
                    }
                }
            });
        }
    }

    private static String formatTime(Duration elapsed, Duration duration) {
        int intElapsed = (int) Math.floor(elapsed.toSeconds());
        int elapsedHours = intElapsed / (60 * 60);
        if (elapsedHours > 0) {
            intElapsed -= elapsedHours * 60 * 60;
        }
        int elapsedMinutes = intElapsed / 60;
        int elapsedSeconds = intElapsed - elapsedHours * 60 * 60 - elapsedMinutes * 60;

        if (duration.greaterThan(Duration.ZERO)) {
            int intDuration = (int) Math.floor(duration.toSeconds());
            int durationHours = intDuration / (60 * 60);
            if (durationHours > 0) {
                intDuration -= durationHours * 60 * 60;
            }
            int durationMinutes = intDuration / 60;
            int durationSeconds = intDuration - durationHours * 60 * 60 - durationMinutes * 60;
            if (durationHours > 0) {
                return String.format("%d:%02d:%02d/%d:%02d:%02d",
                        elapsedHours, elapsedMinutes, elapsedSeconds,
                        durationHours, durationMinutes, durationSeconds);
            } else {
                return String.format("%02d:%02d/%02d:%02d", elapsedMinutes, elapsedSeconds, durationMinutes, durationSeconds);
            }
        } else {
            if (elapsedHours > 0) {
                return String.format("%d:%02d:%02d", elapsedHours, elapsedMinutes, elapsedSeconds);
            } else {
                return String.format("%02d:%02d", elapsedMinutes, elapsedSeconds);
            }
        }
    }

    @FXML
    public void generateButtonHandler(ActionEvent event) throws IOException {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth()/2;
        double height = screenSize.getHeight()/2;
        Parent root = FXMLLoader.load(getClass().getResource("fxml/fullscreen_fxml.fxml"));
        Scene scene = new Scene(root, width, height);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        //((Stage)film.getScene().getWindow()).setFullScreen(true);
        //by setting this property to true, the Video will be played
        //mediaPlayer.setAutoPlay(true);
        //MainVBox.getChildren().add(mediaView);

    }


}

