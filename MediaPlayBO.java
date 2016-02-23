/*
Brandon Oden

References: 
MediaDemo.java from p.663-664
FlagAnthem.java, p.666-667
https://docs.oracle.com/javafx/2/api/javafx/scene/media/MediaPlayer.html
https://docs.oracle.com/javase/8/javafx/api/javafx/scene/media/MediaPlayer.html
http://docs.oracle.com/javafx/2/ui_controls/button.htm
https://docs.oracle.com/javase/8/docs/api/java/awt/event/MouseListener.html
*/

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

public class MediaPlayBO extends Application {
 private final static int NUMBER_OF_MEDIA = 20;
 private final static String URLBase = "http://cdn.clipcanvas.com/clips/medium/";
 private int currentIndex = 0;
 private boolean isShuffled = false;

 @Override // Override the start method in the Application class
 public void start(Stage primaryStage) {
  Media[] media = new Media[NUMBER_OF_MEDIA];
  MediaPlayer[] mediaPlayer = new MediaPlayer[NUMBER_OF_MEDIA];
  MediaView mediaView = new MediaView(mediaPlayer[currentIndex]);

  for (int i = 0; i < NUMBER_OF_MEDIA; i++) {
   media[i] = new Media(URLBase + (10000 + i) + ".mp4");
   mediaPlayer[i] = new MediaPlayer(new Media(URLBase + (10000 + i) + ".mp4"));
  }

  ImageView play = new ImageView(new Image("/mpboImage/play.png"));
  ImageView playG = new ImageView(new Image("/mpboImage/playG.png"));
  ImageView pause = new ImageView(new Image("/mpboImage/pause.png"));
  ImageView back = new ImageView(new Image("/mpboImage/back.png"));
  ImageView backY = new ImageView(new Image("/mpboImage/backY.png"));
  ImageView stop = new ImageView(new Image("/mpboImage/stop.png"));
  ImageView stopR = new ImageView(new Image("/mpboImage/stopR.png"));
  ImageView ff = new ImageView(new Image("/mpboImage/ff.png"));
  ImageView ffY = new ImageView(new Image("/mpboImage/ffY.png"));
  ImageView rewind = new ImageView(new Image("/mpboImage/rwd.png"));
  ImageView rewindY = new ImageView(new Image("/mpboImage/rwdY.png"));
  ImageView next = new ImageView(new Image("/mpboImage/fwd.png"));
  ImageView nextY = new ImageView(new Image("/mpboImage/fwdY.png"));
  ImageView shuffle = new ImageView(new Image("/mpboImage/shuffle.png"));
  ImageView shuffleO = new ImageView(new Image("/mpboImage/shuffleO.png"));

  Button btPlay = new Button();
  Button btBack = new Button();
  Button btStop = new Button();
  Button btFF = new Button();
  Button btRwd = new Button();
  Button btNext = new Button();
  Button btShuffle = new Button();

  btPlay.setOnMouseEntered(e -> {
   if (btPlay.getGraphic() == play)
    btPlay.setGraphic(playG);
  });
  btPlay.setOnMouseExited(e -> {
   if (btPlay.getGraphic() == playG)
    btPlay.setGraphic(play);
  });
  btPlay.setGraphic(play);
  btPlay.setOnAction(e -> {
   btStop.setGraphic(stop);
   if (btPlay.getGraphic() == play || btPlay.getGraphic() == playG) {
    mediaPlayer[currentIndex].play();
    btPlay.setGraphic(pause);
   } else {
    mediaPlayer[currentIndex].pause();
    btPlay.setGraphic(play);
   }
  });

  btBack.setGraphic(back);
  btBack.setOnMouseEntered(e -> btBack.setGraphic(backY));
  btBack.setOnMouseExited(e -> btBack.setGraphic(back));

  btNext.setGraphic(next);
  btNext.setOnMouseEntered(e -> btNext.setGraphic(nextY));
  btNext.setOnMouseExited(e -> btNext.setGraphic(next));

  btStop.setGraphic(stopR);
  btStop.setOnMouseEntered(e -> btStop.setGraphic(stopR));
  btStop.setOnMouseExited(e -> {
   if (btPlay.getGraphic() == pause)
    btStop.setGraphic(stop);
   else
    btStop.setGraphic(stopR);
  });
  btStop.setOnAction(e -> {
   mediaPlayer[currentIndex].stop();
   btPlay.setGraphic(play);
   btStop.setGraphic(stopR);
  });

  btFF.setGraphic(ff);
  btFF.setOnMouseEntered(e -> btFF.setGraphic(ffY));
  btFF.setOnMouseExited(e -> btFF.setGraphic(ff));
  btFF.setOnAction(e ->
   mediaPlayer[currentIndex].seek(mediaPlayer[currentIndex].getCurrentTime().add(Duration.millis(5000))));

  btRwd.setGraphic(rewind);
  btRwd.setOnMouseEntered(e -> btRwd.setGraphic(rewindY));
  btRwd.setOnMouseExited(e -> btRwd.setGraphic(rewind));
  btRwd.setOnAction(e ->
   mediaPlayer[currentIndex].seek(mediaPlayer[currentIndex].getCurrentTime().subtract(Duration.millis(5000))));

  Slider slVolume = new Slider();
  slVolume.setPrefWidth(150);
  slVolume.setMaxWidth(Region.USE_PREF_SIZE);
  slVolume.setMinWidth(30);
  slVolume.setValue(50);
  mediaPlayer[currentIndex].volumeProperty().bind(slVolume.valueProperty().divide(100));

  ComboBox < String > cboMedia = new ComboBox < > ();
  ObservableList < String > videos = FXCollections.observableArrayList();

  for (int i = 0; i < NUMBER_OF_MEDIA; i++) {
   videos.add((10000 + i) + ".mp4");
  }

  btShuffle.setOnAction(e -> {
   if (isShuffled == false) {
    mediaPlayer[currentIndex].stop();
    mediaPlayer[currentIndex].dispose();
    cboMedia.setValue(null);
    cboMedia.getItems().clear();
    FXCollections.shuffle(videos);
    cboMedia.getItems().addAll(videos);
    isShuffled = true;
   } else {
    mediaPlayer[currentIndex].stop();
    mediaPlayer[currentIndex].dispose();
    cboMedia.setValue(null);
    cboMedia.getItems().clear();
    FXCollections.sort(videos);
    cboMedia.getItems().addAll(videos);
    isShuffled = false;
   }
  });

  // I had trouble getting the alerts to work:
  // Alert alert = new Alert(AlertType.INFORMATION);
  // alert.setTitle("Sorry");
  // alert.setHeaderText("This button is under construction.");
  // alert.setContentText("Currently, the shuffle/sort button only works BEFORE playing videos.");
  // alert.showAndWait();

  cboMedia.getItems().addAll(videos);
  cboMedia.setValue(null);

  cboMedia.setOnAction(e -> {
   mediaPlayer[currentIndex].stop();
   currentIndex = videos.indexOf(cboMedia.getValue());
   mediaView.setMediaPlayer(mediaPlayer[currentIndex]);
   cboMedia.setValue(videos.get(currentIndex));
   mediaPlayer[currentIndex].play();
   btPlay.setGraphic(pause);
  });

  btShuffle.setGraphic(shuffle);
  btShuffle.setOnMouseEntered(e -> btShuffle.setGraphic(shuffleO));
  btShuffle.setOnMouseExited(e -> {
   if (isShuffled == false) {
    btShuffle.setGraphic(shuffle);
   }
  });

  btNext.setOnAction(e -> {
   mediaPlayer[currentIndex].seek(Duration.INDEFINITE);
   mediaPlayer[currentIndex].stop();
   currentIndex += 1;

   if (currentIndex > videos.size() - 1) {
    currentIndex = 0;
   }
   mediaView.setMediaPlayer(mediaPlayer[currentIndex]);
   mediaPlayer[currentIndex].play();
   cboMedia.setValue(videos.get(currentIndex));
  });

  btBack.setOnAction(e -> {
   mediaPlayer[currentIndex].seek(Duration.ZERO);
   mediaPlayer[currentIndex].stop();
   currentIndex -= 1;

   if (currentIndex < 0) {
    currentIndex = videos.size() - 1;
   }
   mediaView.setMediaPlayer(mediaPlayer[currentIndex]);
   mediaPlayer[currentIndex].play();
   cboMedia.setValue(videos.get(currentIndex));
  });

  HBox hBox = new HBox(10);
  hBox.setAlignment(Pos.CENTER);
  hBox.getChildren().addAll(btBack, btRwd, btPlay, btFF, btNext, btStop, btShuffle,
   new Label("Volume"), slVolume, new Label("Select media: "), cboMedia);
  hBox.setStyle("-fx-border-color: black");

  BorderPane pane = new BorderPane();
  pane.setCenter(mediaView);
  pane.setBottom(hBox);
  pane.setStyle("-fx-border-color: black");

  mediaView.isSmooth();
  mediaView.setFitWidth(16 * 60);
  mediaView.setFitHeight(9 * 60);
  mediaView.setPreserveRatio(true);
  mediaView.setStyle("-fx-border-color: black");

  Scene scene = new Scene(pane, 16 * 70, 9 * 70);
  primaryStage.setTitle("Brandon's Media Player");
  primaryStage.setScene(scene);
  primaryStage.show();
 }

 public static void main(String[] args) {
  launch(args);
 }
}
