import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            SetWindowScene(primaryStage,"calculator","ЗПА Розробник ПО",300,400).setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.F7) {
                    try {
                        SecondZPA();
                        SetWindow(new Stage(), "next","Пояснення",350,20);
                        SpawnViruses();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });



        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void SpawnViruses() throws Exception {
        Rectangle2D screenSize = Screen.getPrimary().getBounds();
        var sinusVirus = SetWindow(new Stage(),"virus","sinusVirus",200,100);
        AtomicInteger sinusVirusXdirection = new AtomicInteger(1);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(10), event -> {
                    // Update sinusVirus
                    double x = sinusVirus.getX();
                    if(x> (screenSize.getMaxX()-200)||x<0){
                        sinusVirusXdirection.set(sinusVirusXdirection.get() * -1);}
                    sinusVirus.setX(x + sinusVirusXdirection.get());
                    var yScreenSize=screenSize.getMaxY()/2-100;
                    sinusVirus.setY(Math.cos(x/150)*yScreenSize+yScreenSize);
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE); // Бесконечный цикл
        timeline.play();

        var circleVirus = SetWindow(new Stage(), "virus", "circleVirus", 314, 314);
        AtomicReference<Float> circlePos = new AtomicReference<>((float) 0);

        Timeline circleTimeline = new Timeline(
                new KeyFrame(Duration.millis(10), event -> {
                    circlePos.getAndSet(circlePos.get() + 0.007f);
                    // Круговая траектория по координатам X и Y
                    var yScreenSize = screenSize.getMaxY() / 2-157;
                    var xScreenSize = screenSize.getMaxX() / 2-157;
                    circleVirus.setX(Math.cos(circlePos.get()) * xScreenSize+xScreenSize);
                    circleVirus.setY(Math.sin(circlePos.get()) * yScreenSize+yScreenSize);
                })
        );
        var squishVirus = SetWindow(new Stage(), "virus", "squishVirus", 44, 44);
        AtomicInteger squishAmount = new AtomicInteger(1);
        Timeline cursorCheckTimeline = new Timeline(
                new KeyFrame(Duration.millis(1), event -> {
                    double buttonX = squishVirus.getX() + squishVirus.getWidth() - 46; // Позиция крестика
                    double buttonY = squishVirus.getY();

                    // Получаем координаты курсора
                    double cursorX = MouseInfo.getPointerInfo().getLocation().x;
                    double cursorY = MouseInfo.getPointerInfo().getLocation().y;

                    // Проверяем, находится ли курсор над областью крестика или заголовка
                    if (cursorX >= buttonX && cursorX <= buttonX + 40 && cursorY <= buttonY + 25&&cursorY >= buttonY) {
                        squishVirus.setWidth(squishVirus.getWidth() + squishAmount.get());
                        if(squishVirus.getWidth()>screenSize.getMaxX()) squishAmount.set(-2);
                        else if(squishVirus.getWidth()<44) squishAmount.set(0);
                    }
                })
        );

        cursorCheckTimeline.setCycleCount(Timeline.INDEFINITE); // Бесконечный цикл
        cursorCheckTimeline.play();

        circleTimeline.setCycleCount(Timeline.INDEFINITE);
        circleTimeline.play();
        var goldenRatioVirus = SetWindow(new Stage(), "virus", "goldenRatioVirus", 164,100 );
        SetGoldenRatioImmortality(goldenRatioVirus);
    }

    private void SetGoldenRatioImmortality(Stage goldenRatioVirus) {
        goldenRatioVirus.setOnCloseRequest(event -> {
            event.consume();
            try {
                splitWindow(goldenRatioVirus);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    Scene SetWindowScene(Stage stage, String name, String title, int sizeX, int sizeY) throws Exception
    {
        stage.setTitle(title);
        var scene = new Scene(FXMLLoader.load(getClass().getResource("InterfacePages/"+name+".fxml")), sizeX, sizeY);
        stage.setScene(scene);
        stage.show();
        return scene;
    }
    Stage SetWindow(Stage stage, String name, String title, int sizeX, int sizeY) throws Exception{
        SetWindowScene(stage,name,title,sizeX,sizeY);
        return stage;
    }
    public static void main(String[] args) {
        launch();
    }
    private static final double GOLDEN_RATIO = (1 + Math.sqrt(5)) / 2;

    private void splitWindow(Stage stage) throws Exception {
        double width = stage.getWidth();
        double height = stage.getHeight();
        double x = stage.getX();
        double y = stage.getY();
        stage.close();
        System.out.println(Math.abs(width / height - GOLDEN_RATIO));
        System.out.println(Math.abs(height / width - GOLDEN_RATIO));
        if (!(Math.abs(width / height - GOLDEN_RATIO) < 0.6f) && !(Math.abs(height / width - GOLDEN_RATIO) < 0.6f)) return;// Создаем два новых окна
            Stage rectStage = new Stage();
            Stage squareStage = new Stage();

            // Прямоугольник по золотому сечению
            double newWidth = Math.min(width, height) / GOLDEN_RATIO;
            double newHeight = Math.min(width, height);
            SetGoldenRatioImmortality(rectStage);
            // Окно с прямоугольником
            SetWindow(rectStage, "virus", "goldenRatioVirus", (int) newWidth, (int) newHeight);
            SetWindow(squareStage, "virus", "goldenRatioLeftover", (int) Math.max(newWidth, newHeight), (int) Math.max(newWidth, newHeight));
            rectStage.setX(x);
            rectStage.setY(y);

            squareStage.setY(y);
            squareStage.setX(x+Math.min(newWidth, newHeight));
            rectStage.show();
            squareStage.show();

    }
    private void SecondZPA(){

        try (InputStream inputStream = Main.class.getResourceAsStream("ZPA/Розробник інтернет-контенту.pptx")) {
            Path desktopPath = Path.of(System.getProperty("user.home"), "Searches","Desktop","Друга частина.pptx");


            Files.copy(inputStream, desktopPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Файл скопирован на рабочий стол: " + desktopPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
