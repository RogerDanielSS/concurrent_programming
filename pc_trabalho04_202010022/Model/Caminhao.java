package Model;

import java.io.File;

import Controll.SimulacaoController;
import javafx.application.Platform;

public class Caminhao extends Thread { // Inicio da Thread trem1
  // Atributos
  File motor = new File("Audio/Motor.wav");// Pega o arquivo de audio de flag
  private boolean produtor = false;
  private SimulacaoController controle;
  private int xPos;

  // Construtor
  /****************************************************************
   * Metodo: Construtor
   * Funcao: instancia a thread com os parametros necessarios
   * Parametros: informacao se eh ou nao produtor e a classe de controle do fxml
   * Retorno: void
   ****************************************************************/
  public Caminhao(boolean isProducer, SimulacaoController controle) {// Inicio do construtor
    this.produtor = isProducer;
    this.controle = controle;
  }// Fim do construtor

  /*********************************************************************
   * Metodo: run
   * Funcao: executa a thread
   * Parametros: void
   * Retorno: void
   *********************************************************************/
  public void run() { // inicio do metodo run
    if (produtor) { // Verifica se eh produtor
      while (controle.getProdutorEstaVivo()) { // inicio do laco while
        produzir();
      } // Fim do laco while
    } else { // se nao
      while (controle.getConsumidorEstaVivo()) { // inicio do laco while
        consumir();
      } // Fim do laco while
    } // Fim da verificacao if
  }// Fim do metodo run

  // Metodos de operacao
  /****************************************************************
   * Metodo: produzir
   * Funcao: verifica se o produtor pode ir descarregar a areia no estoque
   * Parametros: null
   * Retorno: void
   ****************************************************************/
  public void produzir() {
    try {
      controle.vazio.acquire();//Verifica se tem espaco vazio
      controle.mutex.acquire();//Pede acesso ao espaco
      estocar();//realiza o processo em questao
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      controle.mutex.release();//libera o acesso para outros processos
      controle.cheio.release();//Informa que tem mais um espaco cheio
    }
  }

  /****************************************************************
   * Metodo: estocar
   * Funcao: movimenta o caminhao e derrama as areias
   * Parametros: null
   * Retorno: void
   ****************************************************************/
  private void estocar() {// Inicio do metodo estocar
    int areiaPos = controle.getAreiaIndisponivelPosX();
    xPos = controle.getProdutorPos(); // guarda a posicao inicial na variavel de controle
    Platform.runLater(() -> { // Inicio do metodo runLater -> Ordena a execucao do fxml para que nao ocorra excecoes
      controle.setCheioProdutor();
    });// Fim do metodo runLater

    // Caminhao indo descarregar
    while (xPos < areiaPos + 90) {
      Platform.runLater(() -> { // Inicio do metodo runLater -> Ordena a execucao do fxml para que nao ocorra excecoes
        controle.getImageViewProdutor().setX(xPos++);
      });// Fim do metodo runLater
      try {
        sleep(300 / controle.getVelProdutor());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    } // Fim do laco while

    // derrama a areia
    Platform.runLater(() -> { // Inicio do metodo runLater -> Ordena a execucao do fxml para que nao ocorra excecoes
      controle.setVazioProdutor();
      controle.setVisibleAreia(areiaPos, true);
    });// Fim do metodo runLater

    // Caminhao voltando
    while (xPos > 0) {
      Platform.runLater(() -> { // Inicio do metodo runLater -> Ordena a execucao do fxml para que nao ocorra excecoes
        controle.getImageViewProdutor().setX(xPos--);
      });// Fim do metodo runLater

      try {
        sleep(300 / controle.getVelProdutor());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    } // Fim do laco while
  }// Fim do metodo estocar

  /****************************************************************
   * Metodo: produzir
   * Funcao: verifica se o produtor pode ir carregar de areia no estoque
   * Parametros: null
   * Retorno: void
   ****************************************************************/
  public void consumir() {
    try {
      controle.cheio.acquire(); //Verifica se tem espaco cheio
      controle.mutex.acquire();//Pede acesso ao espaco
      entregar();//realiza o processo em questao
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      controle.mutex.release();//libera o acesso para outros processos
      controle.vazio.release();;//Informa que tem mais um espaco vazio
    }
  }

  /****************************************************************
   * Metodo: entregar
   * Funcao: movimenta o caminhao e coleta as areias
   * Parametros: null
   * Retorno: void
   ****************************************************************/
  private void entregar() {// Inicio do metodo estocar
    int areiaPos = controle.getAreiaDisponivelPosX();
    xPos = controle.getConsumidorPos(); // guarda a posicao inicial na variavel de controle
    Platform.runLater(() -> { // Inicio do metodo runLater -> Ordena a execucao do fxml para que nao ocorra
      // excecoes
      controle.setVazioConsumidor();
    });// Fim do metodo runLater

    // Caminhao indo carregar
    while (xPos > areiaPos - 20) { // Inicio do laco while
      Platform.runLater(() -> { // Inicio do metodo runLater -> Ordena a execucao do fxml para que nao ocorra excecoes
        controle.getImageViewConsumidor().setLayoutX(xPos--);
      });// Fim do metodo runLater
      try {
        sleep(300 / controle.getVelConsumidor());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    } // Fim do laco while

    Platform.runLater(() -> { // Inicio do metodo runLater -> Ordena a execucao do fxml para que nao ocorra excecoes
      controle.setCheioConsumidor();
      controle.setVisibleAreia(areiaPos, false);
    });// Fim do metodo runLater

    // Caminhao voltando
    while (xPos < 750) {
      Platform.runLater(() -> { // Inicio do metodo runLater -> Ordena a execucao do fxml para que nao ocorra excecoes
        controle.getImageViewConsumidor().setLayoutX(xPos++);
      });// Fim do metodo runLater

      try {
        sleep(300 / controle.getVelConsumidor());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    } // Fim do laco while
  }// Fim do metodo estocar
}// Fim da thread Caminhao