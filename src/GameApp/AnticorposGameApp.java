/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameApp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import util.ResourceManager;
import util.Sprite;

/**
 *
 * @author iTuhh Z
 */
public class AnticorposGameApp extends Application {

    int score;
    int inScore;
    String nomeJogador = "Lee";
    int chances;
    File file = new File("ScoreList");
    ArrayList<String> scoreList;
    ObservableList<String> scoreShow;
    ListView<String> scoreView;
    

    @Override
    public void start(Stage primaryStage) throws Exception {
        startGame(primaryStage);
    }

    public void startGame(Stage mainStage) throws Exception {
        mainStage.setTitle("Anticorpos");
        mainStage.setResizable(false);
        scoreList = new ArrayList<String>();
        VBox telaInicial = new VBox(0);
        Button start = new Button("JOGAR");
        start.setId("btStart");
        Button placar = new Button("PLACAR");
        placar.setId("btStart");
        Button sair = new Button("SAIR");
        sair.setId("btStart");
        telaInicial.getChildren().addAll(start, placar, sair);
        telaInicial.setAlignment(Pos.CENTER);
        Scene inicial = new Scene(telaInicial, 800, 600);
        inicial.getStylesheets().addAll(this.getClass().getResource("Inicial.css").toExternalForm());

        //Tela pega nome jogador;
        Button jogar = new Button("Jogar");
        jogar.setId("btJogar");
        Button voltar = new Button("Voltar");
        voltar.setId("btVoltar");
        Label infoNome = new Label("Digite seu nickname:");
        infoNome.setId("label");
        TextField textNome = new TextField();
        textNome.setMaxWidth(220);
        textNome.setPromptText("Ex: Batutinha123");
        VBox telaNome = new VBox(20);
        HBox alinharBts = new HBox(5);
        alinharBts.getChildren().addAll(jogar, voltar);
        alinharBts.setAlignment(Pos.CENTER);
        telaNome.getChildren().addAll(infoNome, textNome, alinharBts);
        telaNome.setAlignment(Pos.CENTER);
        Scene cenaNome = new Scene(telaNome, 800, 600);
        cenaNome.getStylesheets().addAll(this.getClass().getResource("TelaNome.css").toExternalForm());

        //Tela de Game Over e Score;
        VBox telaScore = new VBox(20);
        telaScore.setAlignment(Pos.CENTER);
        Button mainMenu = new Button("Menu Principal");
        mainMenu.setId("btMainMenu");
        Label scoreJogadores = new Label("Score dos Jogadores");
        scoreJogadores.setId("label");
        scoreShow = FXCollections.observableArrayList(scoreList);
        scoreView = new ListView<String>();
        scoreView.setItems(scoreShow);
        scoreView.setMaxSize(400, 500);
        telaScore.getChildren().addAll(scoreJogadores, scoreView, mainMenu);
        Scene telaDeScore = new Scene(telaScore, 800, 600);
        telaDeScore.getStylesheets().addAll(this.getClass().getResource("Placar.css").toExternalForm());

        //Tela do Gameplay
        BorderPane root = new BorderPane();
        Scene mainScene = new Scene(root);

        Canvas canvas = new Canvas(800, 600);
        GraphicsContext context = canvas.getGraphicsContext2D();
        root.setCenter(canvas);

        ArrayList<String> keyPressedList = new ArrayList<String>();
        //handle discret inputs
        ArrayList<String> keyJustPressedList = new ArrayList<String>();

        mainScene.setOnKeyPressed(
                (KeyEvent event) -> {
                    String keyName = event.getCode().toString();
                    // evita duplicatas na lista
                    if (!keyPressedList.contains(keyName)) {
                        keyPressedList.add(keyName);
                        keyJustPressedList.add(keyName);
                    }
                }
        );

        mainScene.setOnKeyReleased(
                (KeyEvent event) -> {
                    String keyName = event.getCode().toString();
                    // evita duplicatas na lista
                    if (keyPressedList.contains(keyName)) {
                        keyPressedList.remove(keyName);
                    }
                }
        );

        Sprite background = new Sprite("imagens/background1.png");
        background.postion.set(400, 300);

        Sprite spaceship = new Sprite("imagens/antiShip.png");
        spaceship.postion.set(100, 300);

        ArrayList<Sprite> laserList = new ArrayList<Sprite>();
        ArrayList<Sprite> covidList = new ArrayList<Sprite>();

        int covidCount = 6;
        for (int n = 0; n < covidCount; n++) {
            Sprite covid = new Sprite("imagens/covid.png");
            double x = 500 * Math.random() + 300;
            double y = 400 * Math.random() + 100;
            covid.postion.set(x, y);
            double angle = 360 * Math.random();
            covid.velocity.setLength(50);
            covid.velocity.setAngle(angle);
            covidList.add(covid);
        }

        score = 0;
        inScore = 0;
        chances = 3;

        AnimationTimer gameloop = new AnimationTimer() {
            @Override
            public void handle(long nanoTime) {
                //Teste de Criação de novos covids
                if (covidList.isEmpty()) {
                    inScore += inScore + 1;
                    int covidCount = (inScore + 1) + 3;
                    for (int n = 0; n < covidCount; n++) {
                        Sprite covid = new Sprite("imagens/covid.png");
                        double x = 500 * Math.random() + 300;
                        double y = 400 * Math.random() + 100;
                        covid.postion.set(x, y);
                        double angle = 360 * Math.random();
                        covid.velocity.setLength(50);
                        covid.velocity.setAngle(angle);
                        covidList.add(covid);
                    }
                }

                //process user input
                if (keyPressedList.contains("LEFT")) {
                    spaceship.rotation -= 3;
                }

                if (keyPressedList.contains("RIGHT")) {
                    spaceship.rotation += 3;
                }

                if (keyPressedList.contains("UP")) {
                    spaceship.velocity.setLength(150);
                    spaceship.velocity.setAngle(spaceship.rotation);
                } else {
                    spaceship.velocity.setLength(0);
                }

                if (keyJustPressedList.contains("SPACE")) {
                    Sprite laser = new Sprite("imagens/gun.png");
                    laser.postion.set(spaceship.postion.x, spaceship.postion.y);
                    laser.velocity.setLength(400);
                    laser.velocity.setAngle(spaceship.rotation);
                    laserList.add(laser);
                }

                //after processing user input, clear jusPressedList.
                keyJustPressedList.clear();

                spaceship.update(1 / 60.0);
                for (Sprite covid : covidList) {
                    covid.update(1 / 60.0);
                }
                // update laser destroy if passed 2 seconds.
                for (int n = 0; n < laserList.size(); n++) {
                    Sprite laser = laserList.get(n);
                    laser.update(1 / 60.0);
                    if (laser.elapsedTime > 1.25) {
                        laserList.remove(n);
                    }
                }

                //quando o disparo atinge o covid ambos são apagados.
                for (int laserNum = 0; laserNum < laserList.size(); laserNum++) {
                    Sprite laser = laserList.get(laserNum);
                    for (int covidNum = 0; covidNum < covidList.size(); covidNum++) {
                        Sprite covid = covidList.get(covidNum);
                        if (laser.overlaps(covid)) {
                            laserList.remove(laserNum);
                            covidList.remove(covidNum);
                            score += 100;
                        }
                    }
                }

                for (int covidNum = 0; covidNum < covidList.size(); covidNum++) {
                    Sprite covid = covidList.get(covidNum);
                    if (spaceship.overlaps(covid)) {
                        try {
                            //chances = chances-1;
                            loadLists();
                            scoreList.add(nomeJogador + " | " + score + "pts");
                            save();
                            loadLists();
                            cleanup(this);
                            mainStage.setScene(telaDeScore);
                        } catch (Exception ex) {
                            Logger.getLogger(AnticorposGameApp.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

                background.render(context);
                spaceship.render(context);
                for (Sprite laser : laserList) {
                    laser.render(context);
                }
                for (Sprite covid : covidList) {
                    covid.render(context);
                }

                //Nome Jogador
                context.setFill(Color.WHITE);
                context.setStroke(Color.GREEN);
                context.setFont(new Font("Arial Black", 20));
                context.setLineWidth(1);
                String textName = nomeJogador + " | Pontos: " + score;
                int textNameX = 50;
                int textNameY = 50;
                context.fillText(textName, textNameX, textNameY);
                context.strokeText(textName, textNameX, textNameY);
            }

        };

        mainStage.setScene(inicial);
        //Buttons actions
        start.setOnAction(e -> mainStage.setScene(cenaNome));
        jogar.setOnAction(e -> {
            if (textNome.getText().isEmpty() || textNome.getText().length() == 0) {
                Alert alerta = new Alert(AlertType.WARNING);
                alerta.setContentText("Você precisa se indentificar. Escreva seu nome jogador.");
                alerta.showAndWait();
            } else {
                nomeJogador = textNome.getText();
                mainStage.setScene(mainScene);
                gameloop.start();
            }
        });
        mainMenu.setOnAction(e -> {
            try {
                restart(mainStage, gameloop);
            } catch (Exception ex) {
                Logger.getLogger(AnticorposGameApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        placar.setOnAction(e ->{
            try { 
                loadLists();
            } catch (Exception ex) {
                Logger.getLogger(AnticorposGameApp.class.getName()).log(Level.SEVERE, null, ex);
            }
            mainStage.setScene(telaDeScore);
             
        });
        
        voltar.setOnAction(e ->mainStage.setScene(inicial));
        sair.setOnAction(e -> System.exit(0));
        

        mainStage.show();
    }

    public void restart(Stage stage, AnimationTimer anima) throws Exception {
        cleanup(anima);
        startGame(stage);
    }

    public void cleanup(AnimationTimer anima) {
        anima.stop();
    }

    public ArrayList<String> load() throws Exception {
        try {
            if (file.exists() == false) {
                file.createNewFile();
                ResourceManager.save(scoreList, "ScoreList");
            }
        } catch (IOException e) {
        }
        return (ArrayList<String>) ResourceManager.load("ScoreList");
    }

    public void save() throws Exception {
        ResourceManager.save(scoreList, "ScoreList");
    }

    public void loadLists() throws Exception{
        scoreList = load();
        scoreShow = FXCollections.observableArrayList(scoreList);
        scoreView.setItems(scoreShow);
    }
    

    public static void main(String[] args) {
        try {
            launch(args);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

}
