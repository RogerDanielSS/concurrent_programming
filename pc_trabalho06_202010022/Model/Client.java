/* *************************************************************** 
* Autor............: Roger Daniel Santana Simoes
* Matricula........: 202010022
* Inicio...........: 05/05/2022
* Ultima alteracao.: 07/05/2022
* Nome.............: Leitores e escritores
* Funcao...........: Simula a solucao com semaforos para o problema classico dos leitores e escritores usando uma interface grafica
*************************************************************** */

package Model;

import Controll.SimulationController;
import javafx.application.Platform;;

/****************************************************************
 * Classe: Client
 * Funcao: Verifica se ha poltronas disponivels e as reserva
 * Parametros: tempo de visualizacao e objeto da classe de controle
 * compartilhada
 ****************************************************************/
public class Client extends Thread {
  int timeViewing = 1;
  SimulationController controller;

  /*********************************************************************
   * Metodo: Client
   * Funcao: Constroi novos objetos do tipo Client
   * Parametros: tempo de visualizacao e objeto da classe de controle
   * compartilhada
   * Retorno: void
   *********************************************************************/
  public Client(SimulationController controller, int timeViewing) {
    this.controller = controller;
    this.timeViewing = timeViewing;
  }

  /*********************************************************************
   * Metodo: read
   * Funcao: Le os banco de dados
   * Parametros: null
   * Retorno: void
   *********************************************************************/
  private void read() {
    boolean buy;
    // while (true) {
    try {
      controller.readerMutex.acquire(); // protects the reader counter
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    controller.increaseReaderCounter(); // increments the reader counter
    Platform.runLater(() -> { // Prevents from getting java exceptios
      controller.updateViewingTextfield();
    });// runLater ends here
    if (controller.getReaderCounter() == 1) // the first reader get acess to all readers
      try {
        controller.dataBaseMutex.acquire();// protects access to the data base
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    controller.readerMutex.release(); // ends protection of the reader counter

    buy = searchForAvaliableArmchair(); // accesses the counter of avaliable chairs

    try { // Define a time for clients to spent viewing just to make the simulation more
          // realistic
      sleep(1000 * timeViewing);
    } catch (InterruptedException e1) {
      e1.printStackTrace();
    }

    try {
      controller.readerMutex.acquire();// protects the reader counter
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    controller.decreaseReaderCounter();// decrements the reader counter
    Platform.runLater(() -> { // Prevents from getting java exceptios
      controller.updateViewingTextfield();
    });// runLater ends here
    if (controller.getReaderCounter() == 0) // just the last reader realease the access to data base
      controller.dataBaseMutex.release(); // ends protection of the data base
    controller.readerMutex.release();// ends protection of the reader counter
    // } // While ends here
    if (buy) // if there is any avaliable armchair
      write();
  }// read method ends here

  /*********************************************************************
   * Metodo: write
   * Funcao: Altera os banco de dados
   * Parametros: null
   * Retorno: void
   *********************************************************************/
  private void write() {
    // while (true) {
    try {
      controller.writerMutex.acquire(); // protects the writer counter
      controller.increaseWriterCounter();
      Platform.runLater(() -> { // Prevents from getting java exceptios
        controller.updateBuyingTextfield();
      });// runLater ends here
      controller.writerMutex.release();// ends protection of the writer counter

      controller.dataBaseMutex.acquire(); // protects the data base
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    buyAvaliableArmchair(); // critical section

    controller.dataBaseMutex.release();// ends protection of the data base
    // }
    System.out.println("it wroted");

    try {
      controller.writerMutex.acquire();// protects the writer counter
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    controller.decreaseWriterCounter();
    Platform.runLater(() -> { // Prevents from getting java exceptios
      controller.updateBuyingTextfield();
    });// runLater ends here
    controller.writerMutex.release();// ends protection of the writer counter
  }

  /*********************************************************************
   * Metodo: searchForAvaliableArmchair
   * Funcao: Pesquisa por uma poltrona disponivel 
   * Parametros: null
   * Retorno: boolean
   *********************************************************************/
  public boolean searchForAvaliableArmchair() { // accesses the counter of avaliable chairs
    if (controller.getAvalibleArmchairCounter() > 0)
      return true;
    return false;
  }

    /*********************************************************************
   * Metodo: buyAvaliableArmchair
   * Funcao: Reserva uma poltrona disponivel 
   * Parametros: null
   * Retorno: void
   *********************************************************************/
  public void buyAvaliableArmchair() {
    System.out.println(controller.getAvalibleArmchairCounter());
    if (controller.getAvalibleArmchairCounter() > 0) { // accesses the counter of avaliable chairs
      controller.getNextAvaliableChair(); // it is a critical section
    }
  }

  /*********************************************************************
   * Metodo: run
   * Funcao: Executa a rotina da thread
   * Parametros: void
   * Retorno: void
   *********************************************************************/
  public void run() {
    read();
  }
} // Client class ends here
