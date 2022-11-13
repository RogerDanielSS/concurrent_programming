/* *************************************************************** 
* Autor............: Roger Daniel Santana Simoes
* Matricula........: 202010022
* Inicio...........: 19/04/2022
* Ultima alteracao.: 24/04/2022
* Nome.............: O jantar dos filosofos 
* Funcao...........: Simula a solucao com semaforos para o problema classico do jantar dos filosofos utilizando uma interface grafica
*************************************************************** */

package Controll;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Semaphore;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import Models.Philosopher;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/****************************************************************
 * Classe: SimulationController
 * Funcao: Controla o fxml e fornece as variaveis e semaforos compartilhados pelas threads das filosofas
 * Parametros: Objetos do fxml
 ****************************************************************/
public class SimulationController implements Initializable {// Inicio da classe SimulacaoController
  // Atributos do fxml
  @FXML
  private Button home_Button, start_Button, adjusts_Button, tela_Button;

  @FXML
  private ImageView plate0_ImageView, plate1_ImageView, plate2_ImageView, plate3_ImageView, plate4_ImageView;

  @FXML
  private ImageView philos_0_imageView, philos_1_imageView, philos_2_imageView, philos_3_imageView, philos_4_imageView;

  @FXML
  private ImageView fork_0_imageView, fork_1_imageView, fork_2_imageView, fork_3_imageView, fork_4_imageView;

  @FXML
  private ImageView state_0_imageView, state_1_imageView, state_2_imageView, state_3_imageView, state_4_imageView;

  @FXML
  private Slider p0_thinking_slider, p1_thinking_slider, p2_thinking_slider, p3_thinking_slider, p4_thinking_slider;

  @FXML
  private Slider p0_eating_slider, p1_eating_slider, p2_eating_slider, p3_eating_slider, p4_eating_slider;

  @FXML
  private AnchorPane AnchorPane_screen, animation_AnchorPane;

  @FXML
  private Label p0_thinking_label, p1_thinking_label, p2_thinking_label, p3_thinking_label, p4_thinking_label;

  @FXML
  private Label p0_eating_label, p1_eating_label, p2_eating_label, p3_eating_label, p4_eating_label;

  // Variaveis de controle
  private final int philosophersNumber = 5;
  public Semaphore mutex = new Semaphore(1);
  public Semaphore semaphores[] = new Semaphore[philosophersNumber];
  public Philosopher philosopher[] = new Philosopher[philosophersNumber];
  public Image statusImages[] = new Image[3];
  public ImageView philosopherStateImage[] = new ImageView[philosophersNumber];
  public int timeThink[] = new int[philosophersNumber];
  public int timeEat[] = new int[philosophersNumber];
  public ImageView plate[] = new ImageView[philosophersNumber];
  public Fork fork[] = new Fork[philosophersNumber];
  public boolean isStated;
  private File start = new File("Audio/Start.wav"); // Carrega o arquivo de audio de inicio de simulacao
  File screen = new File("Audio/Screen.wav"); // Carrega o arquivo de audio de mudanca de tela

  /*********************************************************************
   * Metodo: initializeArrays
   * Funcao: inicializa os arrays que contem os semaforos e os filosofos
   * Parametros: void
   * Retorno: void
   *********************************************************************/
  private void initializeArrays() { // Inicio do metodo initilizaArrays
    // inicializa os arrays de semaforos e de filosofos, instanciando os filosofos
    for (int i = 0; i < philosophersNumber; i++) { // inicio do laco for
      semaphores[i] = new Semaphore(0);
      philosopher[i] = new Philosopher(i, this);
      timeEat[i] = 5;
      timeThink[i] = 5;
    } // fim do laco for

    // inicializa array que armazena as imagens que serao utilizadas pelas threads
    statusImages[0] = new Image("Images/thinking_emoji.png");
    statusImages[1] = new Image("Images/hungry_emoji.png");
    statusImages[2] = new Image("Images/eating_emoji.png");

    // inicializa array que controla as imagens de estados do fxml
    philosopherStateImage[0] = state_0_imageView;
    philosopherStateImage[1] = state_1_imageView;
    philosopherStateImage[2] = state_2_imageView;
    philosopherStateImage[3] = state_3_imageView;
    philosopherStateImage[4] = state_4_imageView;

    // inicializa o array de imagens dos pratos das filosofas
    plate[0] = plate0_ImageView;
    plate[1] = plate1_ImageView;
    plate[2] = plate2_ImageView;
    plate[3] = plate3_ImageView;
    plate[4] = plate4_ImageView;

    // inicializa o array de imagens dos garfos
    fork[0] = new Fork(fork_0_imageView);
    fork[1] = new Fork(fork_1_imageView);
    fork[2] = new Fork(fork_2_imageView);
    fork[3] = new Fork(fork_3_imageView);
    fork[4] = new Fork(fork_4_imageView);
  }// Inicio do metodo initilizaArrays

  /****************************************************************
   * Metodo: startPhilosophers
   * Funcao: Inicializa todas as filosofas
   * Parametros: null
   * Retorno: void
   ****************************************************************/
  private void startPhilosophers() { // Inicio do metodo initilizaArrays
    for (int i = 0; i < philosophersNumber; i++) // inicio do laco for
      philosopher[i].start();
  }// Inicio do metodo initilizaArrays

  /****************************************************************
   * Metodo: getPhilosophersNumber
   * Funcao: Retorna a quantidade de filosofas definida numa constante local
   * Parametros: null
   * Retorno: O numero de filosofas
   ****************************************************************/
  public int getPhilosophersNumber() {
    return this.philosophersNumber;
  }

  /****************************************************************
   * Metodo: setForksPosToPlatePos
   * Funcao: Coloca os garfos no prato da filosofa que chamou o metodo
   * Parametros: as identificacoes da filosofa e do garfo direito
   * Retorno: void
   ****************************************************************/
  public void setForksPosToPlatePos(int rightFork, int id) {// Inicio do metodo setForksPosToPlatePos
    Platform.runLater(() -> { // Inicio do metodo runLater -> Ordena a execucao do fxml para que nao ocorra excecoes
      this.fork[id].setLayoutX(this.plate[id].getLayoutX() + 11);
      this.fork[id].setLayoutY(this.plate[id].getLayoutY() + 11);
      this.fork[rightFork].setLayoutX(this.plate[id].getLayoutX() + 11);
      this.fork[rightFork].setLayoutY(this.plate[id].getLayoutY() + 11);
    });// Fim do metodo runLater
  } // Fim do metodo setForksPosToPlatePos

  /****************************************************************
   * Metodo: setForksPosToOriginal
   * Funcao: Coloca os garfos em sua posicao original
   * Parametros: as identificacoes da filosofa e do garfo direito
   * Retorno: void
   ****************************************************************/
  public void setForksPosToOriginal(int id, int right) { // Inicio do metodo setForksPosToOriginal
    Platform.runLater(() -> { // Inicio do metodo runLater -> Ordena a execucao do fxml para que nao ocorra excecoes
      this.fork[id].setXPosToOriginal();
      this.fork[id].setYPosToOriginal();
      this.fork[right].setXPosToOriginal();
      this.fork[right].setYPosToOriginal();
    });// Fim do metodo runLater
  } // Fim do metodo setForksPosToOriginal

  /****************************************************************
   * Metodo: initizize
   * Funcao: Observa quais botoes do programa sao pressinados para executar uma sub rotina em cada caso
   * Parametros: Parametros padroes nao diretamente relacionados a este app
   * Retorno: void
   ****************************************************************/
  @Override
  public void initialize(URL location, ResourceBundle resources) { // Inicio do metodo initialize
    // Controle dos botoes
	
    /****************************************************************
     * Sub rotina: start_Button
     * Funcao: inicializa e inicia as filosofas
     * Parametros: Um evento (usuario(a) pressionar o botao)
     * Retorno: void
     ****************************************************************/
    start_Button.setOnAction(event -> { // Inicio do controle do botao start
      if (!isStated) { //Inicio do if
        try { //Reproduz o som do botao
          AudioInputStream audioStream = AudioSystem.getAudioInputStream(start);
          Clip clip = AudioSystem.getClip();
          clip.open(audioStream);
          clip.start();

        } catch (Exception e) {
          System.out.println("Error" + e.getMessage());
        } //Fim do try/catch

		//executa acao do botao
        isStated = true;
        this.initializeArrays();
        this.startPhilosophers();
      } //Fim do if
    }); // Fim do controle do botao start

    /****************************************************************
     * Sub rotina: home_Button
     * Funcao: finaliza o processo das filosofas e muda para a tela inicial
     * Parametros: Um evento (usuario(a) pressionar o botao)
     * Retorno: void
     ****************************************************************/
    home_Button.setOnAction(event -> { // Inicio do controle do botao home
      this.isStated = false;

      try {// Inicio do try
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/View/Home.fxml")); // Chamando o fxml
        AnchorPane_screen.getChildren().setAll(a);

        AudioInputStream audioStream = AudioSystem.getAudioInputStream(screen); // Audio de mudanca relevante de tela
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);

        clip.start();
      } catch (Exception e) { // Fim do try, inicio do catch
        System.out.println("Error: Tentativa de mudar a cena \n" + e.getMessage());
      } // Fim do catch
    }); // Fim do controle do botao home

    /****************************************************************
     * Sub rotina: adjusts_Button
     * Funcao: Mostra as opcoes de ajustes de tempo de comer e pensar das filsofas
     * Parametros: Um evento (usuario(a) pressionar o botao)
     * Retorno: void
     ****************************************************************/
    adjusts_Button.setOnAction(event -> { // Inicio do controle do botao start
      try { // Audio de mudanca relevante de tela
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(screen);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);

        clip.start();

      } catch (Exception e) {
        System.out.println("Error" + e.getMessage());
      }
      this.animation_AnchorPane.setOpacity(0.5);
      this.tela_Button.setVisible(true);
    }); // Fim do controle do botao start

    /****************************************************************
     * Sub rotina: tela_Button
     * Funcao: Oculta as opcoes de ajustes de tempo de comer e pensar das filsofas
     * Parametros: Um evento (usuario(a) pressionar o botao)
     * Retorno: void
     ****************************************************************/
    tela_Button.setOnAction(event -> { // Inicio do controle do botao start
      try { // Audio de mudanca relevante de tela
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(screen);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);

        clip.start();

      } catch (Exception e) {
        System.out.println("Error" + e.getMessage());
      } 

      this.animation_AnchorPane.setOpacity(1);
      this.tela_Button.setVisible(false);
    }); // Fim do controle do botao start

    // controle dos sliders
    /****************************************************************
     * Sub rotina: p0_thinking_slider
     * Funcao: Controla quantos segundos a filosofa 0 (do array) passa pensando
     * Parametros: Um evento (modificacao no slider)
     * Retorno: void
     ****************************************************************/
    p0_thinking_slider.valueProperty().addListener(new ChangeListener<Number>() { // Inicio do listener do Slider do tempo de pensamento do phi 0
      @Override
      public void changed(ObservableValue<? extends Number> ov, Number valorAntigo, Number valorNovo) { //
        timeThink[0] = (int) valorNovo.doubleValue(); // Atualiza o valor da variavel de controle
        p0_thinking_label.setText("T. pensando: " + timeThink[0] + " s");
      } // Fim do metodo que verifica se o valor do slider mudou
    }); // Fim do listener do Slider do tempo de pensamento do phi 0

    /****************************************************************
     * Sub rotina: p1_thinking_slider
     * Funcao: Controla quantos segundos a filosofa 1 (do array) passa pensando
     * Parametros: Um evento (modificacao no slider)
     * Retorno: void
     ****************************************************************/
    p1_thinking_slider.valueProperty().addListener(new ChangeListener<Number>() { // Inicio do listener do Slider do tempo de pensamento do phi 1
      @Override
      public void changed(ObservableValue<? extends Number> ov, Number valorAntigo, Number valorNovo) { //
        timeThink[1] = (int) valorNovo.doubleValue(); // Atualiza o valor da variavel de controle
        p1_thinking_label.setText("T. pensando: " + timeThink[1] + " s");
      } // Fim do metodo que verifica se o valor do slider mudou
    }); // Fim do listener do Slider do tempo de pensamento do phi 1

    /****************************************************************
     * Sub rotina: p2_thinking_slider
     * Funcao: Controla quantos segundos a filosofa 2 (do array) passa pensando
     * Parametros: Um evento (modificacao no slider)
     * Retorno: void
     ****************************************************************/
    p2_thinking_slider.valueProperty().addListener(new ChangeListener<Number>() { // Inicio do listener do Slider do tempo de pensamento do phi 2
      @Override
      public void changed(ObservableValue<? extends Number> ov, Number valorAntigo, Number valorNovo) { //
        timeThink[2] = (int) valorNovo.doubleValue(); // Atualiza o valor da variavel de controle
        p2_thinking_label.setText("T. pensando: " + timeThink[2] + " s");
      } // Fim do metodo que verifica se o valor do slider mudou
    }); // Fim do listener do Slider do tempo de pensamento do phi 2

    /****************************************************************
     * Sub rotina: p3_thinking_slider
     * Funcao: Controla quantos segundos a filosofa 3 (do array) passa pensando
     * Parametros: Um evento (modificacao no slider)
     * Retorno: void
     ****************************************************************/
    p3_thinking_slider.valueProperty().addListener(new ChangeListener<Number>() { // Inicio do listener do Slider do tempo de pensamento do phi 3
      @Override
      public void changed(ObservableValue<? extends Number> ov, Number valorAntigo, Number valorNovo) { //
        timeThink[3] = (int) valorNovo.doubleValue(); // Atualiza o valor da variavel de controle
        p3_thinking_label.setText("T. pensando: " + timeThink[3] + " s");
      } // Fim do metodo que verifica se o valor do slider mudou
    }); // Fim do listener do Slider do tempo de pensamento do phi 3

    /****************************************************************
     * Sub rotina: p4_thinking_slider
     * Funcao: Controla quantos segundos a filosofa 4 (do array) passa pensando
     * Parametros: Um evento (modificacao no slider)
     * Retorno: void
     ****************************************************************/
    p4_thinking_slider.valueProperty().addListener(new ChangeListener<Number>() { // Inicio do listener do Slider do tempo de pensamento do phi 4
      @Override
      public void changed(ObservableValue<? extends Number> ov, Number valorAntigo, Number valorNovo) { //
        timeThink[4] = (int) valorNovo.doubleValue(); // Atualiza o valor da variavel de controle
        p4_thinking_label.setText("T. pensando: " + timeThink[4] + " s");
      } // Fim do metodo que verifica se o valor do slider mudou
    }); // Fim do listener do Slider do tempo de pensamento do phi 4

    /****************************************************************
     * Sub rotina: p0_eating_slider
     * Funcao: Controla quantos segundos a filosofa 0 (do array) passa comendo
     * Parametros: Um evento (modificacao no slider)
     * Retorno: void
     ****************************************************************/
    p0_eating_slider.valueProperty().addListener(new ChangeListener<Number>() { // Inicio do listener do Slider do tempo de comer do phi 0
      @Override
      public void changed(ObservableValue<? extends Number> ov, Number valorAntigo, Number valorNovo) { //
        timeEat[0] = (int) valorNovo.doubleValue(); // Atualiza o valor da variavel de controle
        p0_eating_label.setText("T. comendo: " + timeEat[0] + " s");
      } // Fim do metodo que verifica se o valor do slider mudou
    }); // Fim do listener do Slider do tempo de comer do phi 0

    /****************************************************************
     * Sub rotina: p1_eating_slider
     * Funcao: Controla quantos segundos a filosofa 1 (do array) passa comendo
     * Parametros: Um evento (modificacao no slider)
     * Retorno: void
     ****************************************************************/
    p1_eating_slider.valueProperty().addListener(new ChangeListener<Number>() { // Inicio do listener do Slider do tempo de comer do phi 1
      @Override
      public void changed(ObservableValue<? extends Number> ov, Number valorAntigo, Number valorNovo) { //
        timeEat[1] = (int) valorNovo.doubleValue(); // Atualiza o valor da variavel de controle
        p1_eating_label.setText("T. comendo: " + timeEat[1] + " s");
      } // Fim do metodo que verifica se o valor do slider mudou
    }); // Fim do listener do Slider do tempo de comer do phi 1

    /****************************************************************
     * Sub rotina: p2_eating_slider
     * Funcao: Controla quantos segundos a filosofa 2 (do array) passa comendo
     * Parametros: Um evento (modificacao no slider)
     * Retorno: void
     ****************************************************************/
    p2_eating_slider.valueProperty().addListener(new ChangeListener<Number>() { // Inicio do listener do Slider do tempo de comer do phi 2
      @Override
      public void changed(ObservableValue<? extends Number> ov, Number valorAntigo, Number valorNovo) { //
        timeEat[2] = (int) valorNovo.doubleValue(); // Atualiza o valor da variavel de controle
        p2_eating_label.setText("T. comendo: " + timeEat[2] + " s");
      } // Fim do metodo que verifica se o valor do slider mudou
    }); // Fim do listener do Slider do tempo de comer do phi 2

    /****************************************************************
     * Sub rotina: p3_eating_slider
     * Funcao: Controla quantos segundos a filosofa 3 (do array) passa comendo
     * Parametros: Um evento (modificacao no slider)
     * Retorno: void
     ****************************************************************/
    p3_eating_slider.valueProperty().addListener(new ChangeListener<Number>() { // Inicio do listener do Slider do tempo de comer do phi 3
      @Override
      public void changed(ObservableValue<? extends Number> ov, Number valorAntigo, Number valorNovo) { //
        timeEat[3] = (int) valorNovo.doubleValue(); // Atualiza o valor da variavel de controle
        p3_eating_label.setText("T. comendo: " + timeEat[3] + " s");
      } // Fim do metodo que verifica se o valor do slider mudou
    }); // Fim do listener do Slider do tempo de comer do phi 3

    /****************************************************************
     * Sub rotina: p4_eating_slider
     * Funcao: Controla quantos segundos a filosofa 4 (do array) passa comendo
     * Parametros: Um evento (modificacao no slider)
     * Retorno: void
     ****************************************************************/
    p4_eating_slider.valueProperty().addListener(new ChangeListener<Number>() { // Inicio do listener do Slider do tempo de comer do phi 4
      @Override
      public void changed(ObservableValue<? extends Number> ov, Number valorAntigo, Number valorNovo) { //
        timeEat[4] = (int) valorNovo.doubleValue(); // Atualiza o valor da variavel de controle
        p4_eating_label.setText("T. comendo: " + timeEat[4] + " s");
      } // Fim do metodo que verifica se o valor do slider mudou
    }); // Fim do listener do Slider do tempo de comer do phi 4

  } // Fim do metodo initialize

  /****************************************************************
   * Classe: Fork
   * Funcao: Armezema um garfo, sua posicao original e modifica a posicao do garfo
   * Parametros: Um evento (modificacao no slider)
   * Retorno: void
   ****************************************************************/
  private class Fork { // Inicio da classe Fork
    public ImageView fork; // Amazena o garfo
    // Armazena a posicao do garfo
    public final double originalXPos;
    public final double originalYPos;

    /****************************************************************
     * Metodo: Construtor
     * Funcao: instancia o objeto com os parametros necessarios
     * Parametros: o ImageView do garfo a ser armazenado
     * Retorno: void
     ****************************************************************/
    public Fork(ImageView fork) { //Inicio do construtor 
      this.fork = fork;
      this.originalXPos = fork.getLayoutX();
      this.originalYPos = fork.getLayoutY();
    } // Fim do construtor

    /****************************************************************
     * Metodo: setLayoutX
     * Funcao: Modifica a posicao do garfo em relacao ao eixo x
     * Parametros: Novo valor de x
     * Retorno: void
     ****************************************************************/
    public void setLayoutX(double value) { //Inicio do metodo setLayoutX
      Platform.runLater(() -> { // Inicio do metodo runLater -> Ordena a execucao do fxml para que nao ocorra excecoes
        this.fork.setLayoutX(value);
      });// Fim do metodo runLater
    } //Fim do metodo setLayoutX

    /****************************************************************
     * Metodo: setLayoutY
     * Funcao: Modifica a posicao do garfo em relacao ao eixo y
     * Parametros: Novo valor de y
     * Retorno: void
     ****************************************************************/
    public void setLayoutY(double value) { //Inicio do metodo setLayoutY
      Platform.runLater(() -> { // Inicio do metodo runLater -> Ordena a execucao do fxml para que nao ocorra excecoes
        this.fork.setLayoutY(value);
      });// Fim do metodo runLater
    } //Fim do metodo setLayoutY


    /****************************************************************
     * Metodo: setXPosToOriginal
     * Funcao: Coloca o garfo em sua posicao original em x
     * Parametros: null
     * Retorno: void
     ****************************************************************/
    public void setXPosToOriginal() { //Inicio do metodo setXPosToOriginal
      Platform.runLater(() -> { // Inicio do metodo runLater -> Ordena a execucao do fxml para que nao ocorra excecoes
        this.fork.setLayoutX(originalXPos);
      });// Fim do metodo runLater
    } //Fim do metodo setXPosToOriginal


    /****************************************************************
     * Metodo: setXPosToOriginal
     * Funcao: Coloca o garfo em sua posicao original em x
     * Parametros: null
     * Retorno: void
     ****************************************************************/
    public void setYPosToOriginal() { //Inicio do metodo setYPosToOriginal
      Platform.runLater(() -> { // Inicio do metodo runLater -> Ordena a execucao do fxml para que nao ocorra excecoes
        this.fork.setLayoutY(originalYPos);
      });// Fim do metodo runLater
    } //Fim do metodo setYPosToOriginal
  } // Fim da classe Fork
} // Fim do da classe SimulacaoController