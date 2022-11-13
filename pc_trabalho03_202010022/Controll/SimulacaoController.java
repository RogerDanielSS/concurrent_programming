/* *************************************************************** 
* Autor............: Roger Daniel Santana Simoes
* Matricula........: 202010022
* Inicio...........: 23/03/2022
* Ultima alteracao.: 27/03/2022
* Nome.............: Simulacao de tunel de trem
* Funcao...........: Faz a simulacao de dois trens que tentam acessar os mesmos trilhos utilizando threads
*************************************************************** */

package Controll;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

/*********************************************************************
 * Metodo: SimulacaoController
 * Funcao: Controla o fxml da simulacao
 * Parametros: objetos do fxml e variaveis de controle
 * Retorno: void
 *********************************************************************/
public class SimulacaoController implements Initializable {

  @FXML
  private AnchorPane AnchorPane_Simulacao;

  @FXML
  private Circle RegiaoCritica2_shape;

  @FXML
  private ImageView Train2_ImageView_Out;

  @FXML
  private Slider Train1_Slider_In;

  @FXML
  private ImageView Train1_ImageView_Out;

  @FXML
  private Button Home_Button;

  @FXML
  private Circle RegiaoCritica1_shape;

  @FXML
  private Button Play_Button;

  @FXML
  private Slider Train2_Slider_In;

  @FXML
  private HBox Botoes_button;

  @FXML
  private VBox Colisao_VBox;

  @FXML
  private Button ReiniciarSimulacao_Button;

  // Variaveis
  private double slider1 = 1; // Determina a velocidade do trem 1
  private double slider2 = 1; // Determina a velocidade do trem 2
  private boolean flag1 = false; // Informa se tem alguem no tunel 1
  private boolean flag2 = false; // Informa se tem alguem no tunel 2

  // Objtos
  Trem1 trem1 = new Trem1(); // Inializa o trem para que a Thread de controle consiga ler suas variaveis
  Trem2 trem2 = new Trem2(); // Inializa o trem para que a Thread de controle consiga ler suas variaveis
  File screen = new File("Audio/Screen.wav"); // Carrega o arquivo de audio de mudanca de tela
  File start = new File("Audio/Start.wav"); // Carrega o arquivo de audio de inicio de simulacao
  File flag = new File("Audio/Flag.wav");// Pega o arquivo de audio de flag
  File flagDown = new File("Audio/FlagDown.wav");// Pega o arquivo de audio de flag

  // Metodos
  /****************************************************************
   * Metodo: Colisao
   * Funcao: Reproduz o audio determinado para uma colisao, finaliza os processos e mostra uma mensagem de erro
   * Parametros: null
   * Retorno: void
   ****************************************************************/
  private void colisao() {// Inicio do metodo colisao
    File glitch = new File("Audio/Glitch.wav");// Pega o arquivo de audio

    try {
      AudioInputStream audioStream = AudioSystem.getAudioInputStream(glitch);
      Clip clip = AudioSystem.getClip();
      clip.open(audioStream);

      clip.start();

    } catch (Exception e) {
      System.out.println("Error" + e.getMessage());
    }

    Botoes_button.setVisible(false); // Botoes da parte de cima da tela
    Colisao_VBox.setVisible(true); // Mensagem de erro
    trem1.x = 756; // Finaliza a thread do trem 1
    trem2.x = -49; // Finaliza a thread do trem 2
  }// Fim do metodo colisao

  /****************************************************************
   * Metodo: reiniciar
   * Funcao: instancia novos trens e setta variavel e objetos fxml nas posicoes iniciais
   * Parametros: null
   * Retorno: void
   ****************************************************************/
  private void reiniciar() {// Inicio do metodo reiniciar
    trem1 = new Trem1();
    trem2 = new Trem2();
    flag1 = false;
    flag2 = false;
    RegiaoCritica1_shape.setVisible(false);
    RegiaoCritica2_shape.setVisible(false);
    Botoes_button.setVisible(true);
  }// Fim do metodo reiniciar

  /****************************************************************
   * Metodo: audioFlag
   * Funcao: Reproduz o audio determinado para quando uma bandeira for levantada
   * Parametros: null
   * Retorno: void
   ****************************************************************/
  private void audioFlag() {
    try {
      AudioInputStream audioStream = AudioSystem.getAudioInputStream(flag);
      Clip clip = AudioSystem.getClip();
      clip.open(audioStream);

      clip.start();

    } catch (Exception e) {
      System.out.println("Error" + e.getMessage());
    }
  }

  /****************************************************************
   * Metodo: audioFlag
   * Funcao: Reproduz o audio determinado para quando uma bandeira for levantada
   * Parametros: null
   * Retorno: void
   ****************************************************************/
  private void audioFlagDown() {
    try {
      AudioInputStream audioStream = AudioSystem.getAudioInputStream(flagDown);
      Clip clip = AudioSystem.getClip();
      clip.open(audioStream);

      clip.start();

    } catch (Exception e) {
      System.out.println("Error" + e.getMessage());
    }
  }

  /****************************************************************
   * Metodo: initizize
   * Funcao: Observa quais botoes do programa sao pressinados para executar uma sub rotina em cada caso
   * Parametros: Parametros padroes nao diretamente relacionados a este app
   * Retorno: void
   ****************************************************************/
  @Override
  public void initialize(URL location, ResourceBundle resources) { // Inicio do metodo Initialize
    /****************************************************************
     * Sub rotina: Home_Button
     * Funcao: retorna para a tela inicial
     * Parametros: Um evento (o botao ser pressionado)
     * Retorno: void
     ****************************************************************/
    Home_Button.setOnAction(event -> { // Inicio do controle do botao Home
      try { // Audio de transicao de tela
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(screen);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);

        clip.start();

      } catch (Exception e) {
        System.out.println("Error" + e.getMessage());
      }

      try {// Inicio do try
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/View/Home.fxml")); // Chamando o fxml
        AnchorPane_Simulacao.getChildren().setAll(a);
      } catch (Exception e) { // Fim do try, inicio do catch
        System.out.println("Error: Tentativa de mudar a cena \n" + e.getMessage());
      } // Fim do catch
    }); // Fim do controle do botao Home

    /****************************************************************
     * Sub rotina: Play_Button
     * Funcao: inicia a simulacao
     * Parametros: Um evento (o botao ser pressionado)
     * Retorno: void
     ****************************************************************/
    Play_Button.setOnAction(event -> { // metodo que inicia a simulacao
      try {
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(start);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);

        clip.start();

      } catch (Exception e) {
        System.out.println("Error" + e.getMessage());
      }

      System.out.println("A");
      trem1.start(); // inicia o trem 1
      trem2.start(); // inicia o trem 2

      Controle controle = new Controle(); // Instancia a thread de controle/
      controle.start(); // inicia a thread de controle
      Botoes_button.setVisible(false); // Apaga os botoes temporareamente para limitar o que o usuario pode fazer
    }); // Fim do controle do Play Button

    /****************************************************************
     * Sub rotina: ReiniciarSimulacao_Button
     * Funcao: reinicia a simulacao em caso de colisao
     * Parametros: Um evento (o botao ser pressionado)
     * Retorno: void
     ****************************************************************/
    ReiniciarSimulacao_Button.setOnAction(event -> { // Metodo que reinicia a simulacao em caso de colisao
      Colisao_VBox.setVisible(false); // Apaga a mensagem de erro
      reiniciar(); // Chama o metodo reiniciar
    }); // Fim do controle do botao reiniciar

    /****************************************************************
     * Sub rotina: Train1_Slider_In
     * Funcao: Passa o valor do slider para uma variavel que sera utilizada pela thread do trem 1
     * Parametros: Um evento (modificacao no slider)
     * Retorno: void
     ****************************************************************/
    Train1_Slider_In.valueProperty().addListener(new ChangeListener<Number>() { // Inicio do listener do Slider do trem 1
      @Override
      public void changed(ObservableValue<? extends Number> ov, Number valorAntigo, Number valorNovo) { // inicio do metodo que verifica se o valor mudou
        slider1 = valorNovo.doubleValue() + 1;
      } // Fim do metodo que verifica se o valor mudou

    }); // Fim do listener do Slider do trem 1

    /****************************************************************
     * Sub rotina: Train2_Slider_In
     * Funcao: Passa o valor do slider para uma variavel que sera utilizada pela thread do trem 1
     * Parametros: Um evento (modificacao no slider)
     * Retorno: void
     ****************************************************************/
    Train2_Slider_In.valueProperty().addListener(new ChangeListener<Number>() { // Inicio do listener do Slider do trem 2
      @Override
      public void changed(ObservableValue<? extends Number> ov, Number valorAntigo, Number valorNovo) { // inicio do metodo que verifica se o valor mudou
        slider2 = valorNovo.doubleValue() + 1;
      } // Fim do metodo que verifica se o valor mudou

    }); // Fim do listener do Slider do trem 2
  } // Fim do metodo Initialize

  /****************************************************************
   * Sub rotina: Trem 1
   * Funcao: Cria 1 nova thread que anima a imagem do trem 1
   * Parametros: void
   * Retorno: void
   ****************************************************************/
  class Trem1 extends Thread { // Inicio da Thread trem1
    int x = 1; // Variavel que controle a posicao do trem no eixo x Variaveis que controla a posicao do trem no eixo y
    int y0 = 180; // Trilho individual
    int y1 = 215; // Tunel

    /*********************************************************************
     * Metodo: run
     * Funcao: executa a thread
     * Parametros: void
     * Retorno: void
     ****************************************************************/
    public void run() { // inicio do metodo run
      Platform.runLater(() -> { // Inicio do metodo runLater -> Ordena a execucao do fxml para que nao ocorra excecoes
        Train1_ImageView_Out.setY(y0);
      });// Fim do metodo runLater
      while (x <= 750) {// Inicio do laco while
        try {
          sleep(700 / (long) slider1);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

        x++; // Aumenta o valor da variavel que controla a posicao da imagem do trem
        System.out.println("Trem 1: " + x + "\n");
        while (flag1 && x == 105) { // Espera para entrar no tunel 1
          System.out.println("Trem 1: espando");
          try {
            sleep(700 / (long) slider1);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }

        if (x == 105) { // Levanta a bandeira do tunel 1 antes porque vem de cima
          flag1 = true; // levanta a bandeira do tunel 1 para que outro trem nao entre neste
          audioFlag();
        }
        if (x == 150) { // Entra no Tunel 1
          Platform.runLater(() -> { // Inicio do metodo runLater -> Ordena a execucao do fxml para que nao ocorra excecoes
            Train1_ImageView_Out.setY(y1);
          });// Fim do metodo runLater
        } // Fim do if de entrada do tunel 1

        if (x == 255) { // Sai do tunel 1
          flag1 = false; // abaixa a bandeira do tunel 1 para que outro trem possa entrar neste
          audioFlagDown();
          Platform.runLater(() -> { // Inicio do metodo runLater -> Ordena a execucao do fxml para que nao ocorra excecoes
            Train1_ImageView_Out.setY(y0);
          });// Fim do metodo runLater
        } // Fim do if de saida do tunel 1

        while (flag2 && x == 405) { // Espera para entrar no tunel 2
          System.out.println("Trem 1: esperando");
          try {
            sleep(700 / (long) slider1);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        if (x == 405) { // levata a bandeira do tunel 2 antes porque vem de cima
          flag2 = true;
          audioFlag();
        }

        if (x == 450) { // Entra no tunel 2
          Platform.runLater(() -> { // Inicio do metodo runLater -> Ordena a execucao do fxml para que nao ocorra excecoes
            Train1_ImageView_Out.setY(y1);
          });// Fim do metodo runLater
        } // Fim do if de entrada do tunel 2

        if (x == 555) { // Sai do tunel 2
          flag2 = false; // abaixa a bandeira do tunel 2 para que outro trem possa entrar neste
          audioFlagDown();
          Platform.runLater(() -> { // Inicio do metodo runLater -> Ordena a execucao do fxml para que nao ocorra excecoes
            Train1_ImageView_Out.setY(y0);
          });// Fim do metodo runLater
        } // Fim do if de saida do tunel 2

        Platform.runLater(() -> { // Inicio do metodo runLater -> Ordena a execucao do fxml para que nao ocorra excecoes
          Train1_ImageView_Out.setX(x);

        });// Fim do metodo runLater
      } // Fim do laco while
    }// Fim do metodo run
  }// Fim da thread trem 1

  /****************************************************************
   * Sub rotina: Trem 2
   * Funcao: Cria 1 nova thread que anima a imagem do trem 2
   * Parametros: void
   * Retorno: void
   ****************************************************************/
  class Trem2 extends Thread { // Inicio da Thread trem 2
    int x = 705; // Variavel que controle a posicao do trem no eixo x Variaveis que controla a posicao do trem no eixo y
    int y0 = 252; // Trilho individual
    int y1 = 215; // Tunel

    /*********************************************************************
     * Metodo: run
     * Funcao: executa a thread
     * Parametros: void
     * Retorno: void
     ****************************************************************/
    public void run() { // inicio do metodo run
      Platform.runLater(() -> { // Inicio do metodo runLater -> Ordena a execucao do fxml para que nao ocorra excecoes
        Train2_ImageView_Out.setY(y0);
      });// Fim do metodo runLater
      while (x >= -45) {// Inicio do laco while
        try {
          sleep(700 / (long) slider2);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

        x--; // Diminui o valor da variavel que controla a posicao da imagem do trem

        System.out.println("Trem 2: " + x + "\n");
        while (flag2 == true && x == 600) { // Espera para entrar no tunel 2
          System.out.println("Trem 2: esperando");
          try {
            sleep(700 / (long) slider2);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        } // Fim do laco while 

        if (x == 600) { // Entra no Tunel 2
          flag2 = true; // levanta a bandeira do tunel 2 para que outro trem nao entre neste
          audioFlag();
          Platform.runLater(() -> { // Inicio do metodo runLater -> Ordena a execucao do fxml para que nao ocorra excecoes
            Train2_ImageView_Out.setY(y1);
          });// Fim do metodo runLater
        } // Fim do if de entrada do tunel 2

        if (x == 405) { // Sai do tunel 2
          flag2 = false; // abaixa a bandeira do tunel 2 para que outro trem possa entrar neste
          audioFlagDown();
          Platform.runLater(() -> { // Inicio do metodo runLater -> Ordena a execucao do fxml para que nao ocorra excecoes
            Train2_ImageView_Out.setY(y0);
          });// Fim do metodo runLater
        }  // Fim do if de saida do tunel 2

        while (flag1 && x == 300) { // Espera para entrar no tunel 1
          System.out.println("Trem 2: espando");
          try {
            sleep(700 / (long) slider2);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }

        if (x == 300) { // Entra no tunel 1
          flag1 = true; // levanta a bandeira do tunel 1 para que outro trem nao entre neste
          audioFlag();
          Platform.runLater(() -> { // Inicio do metodo runLater -> Ordena a execucao do fxml para que nao ocorra excecoes
            Train2_ImageView_Out.setY(y1);
          });// Fim do metodo runLater
        }  // Fim do if de entrada do tunel 1

        if (x == 105) { // Sai do tunel 1
          flag1 = false; // abaixa a bandeira do tunel 1 para que outro trem possa entrar neste
          audioFlagDown();
          Platform.runLater(() -> { // Inicio do metodo runLater -> Ordena a execucao do fxml para que nao ocorra excecoes
            Train2_ImageView_Out.setY(y0);
          });// Fim do metodo runLater
        }  // Fim do if de saida do tunel 1

        Platform.runLater(() -> { // Inicio do metodo runLater -> Ordena a execucao do fxml para que nao ocorra excecoes
          Train2_ImageView_Out.setX(x);
        });// Fim do metodo runLater
      } // Fim do laco while
    }// Fim do metodo run
  }// Fim da thread trem 2

  /****************************************************************
   * Sub rotina: Controle
   * Funcao: controla o hbox de botoes e verifica se houve uma colisao e verifica se deve ou nao lavantar a bandeira
   * Parametros: void
   * Retorno: void
   ****************************************************************/
  class Controle extends Thread { // Inicio da Thread trem1
    int x1 = 0; // Trilho individual
    int x2 = 750; // Tunel
    boolean flag1rised = false;
    boolean flag2rised = false;
    boolean colisao = false; // Armazena se houve uma colisao para a thread escolher o que fazer

    public void run() { // inicio do metodo run
      while (x1 <= 750 || x2 >= -45) {// Inicio do laco while
        try {
          sleep(300);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        x1 = trem1.x; // Eixo x do trem 1
        x2 = trem2.x; // Eixo x do trem 2

        if (flag1) { // Controla a bandeira do tunel 1
          RegiaoCritica1_shape.setVisible(true);
        } else
          RegiaoCritica1_shape.setVisible(false);

        if (flag2) { // Controla a bandeira do tunel 2
          RegiaoCritica2_shape.setVisible(true);
        } else
          RegiaoCritica2_shape.setVisible(false);

        if (x1 > 105 && x1 < 255 && x2 < 300 && x2 > 105) { // Colisao no tunel 1
          colisao = true;
          colisao();
        }

        if (x1 > 450 && x1 < 555 && x2 < 600 && x2 > 405) { // Colisao no tunel 1
          colisao = true;
          colisao();
        }

        // if (x1 > 150) { // Colisao de teste de codigo
        // colisao = true;
        // colisao();
        // }
      } // Fim do laco while
      if (!colisao) // Reinicia automaticamente somente se nao houve uma colisao
        reiniciar();
    }// Fim do metodo run
  }// Fim da thread de controle
} // Fim da classe ControleSimulacao
