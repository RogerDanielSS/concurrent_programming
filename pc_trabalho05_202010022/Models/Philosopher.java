/* *************************************************************** 
* Autor............: Roger Daniel Santana Simoes
* Matricula........: 202010022
* Inicio...........: 19/04/2022
* Ultima alteracao.: 24/04/2022
* Nome.............: O jantar dos filosofos 
* Funcao...........: Simula a solucao com semaforos para o problema classico do jantar dos filosofos utilizando uma interface grafica
*************************************************************** */

package Models;


import Controll.SimulationController;
import javafx.application.Platform;

/****************************************************************
 * Classe: Philosopher
 * Funcao: Estancia as filosofas e as faz pegar e soltar os garfos ordenadamente
 * Parametros: A id e a classe de controle do fxml
 ****************************************************************/
public class Philosopher extends Thread {// Inicio da classe Philosopher
  // Atributos
  private SimulationController control;
  private final int THINKING = 0, HUNGRY = 1, EATING = 2, LEFT, RIGHT;
  private int id, counter = 0, status = THINKING;

  // Construtor
  /****************************************************************
   * Metodo: Construtor
   * Funcao: instancia a thread com os parametros necessarios
   * Parametros: A id e a classe de controle do fxml
   * Retorno: void
   ****************************************************************/
  public Philosopher(int id, SimulationController control) { // Inicio do construtor
    this.id = id;
    this.control = control;

    int n = control.getPhilosophersNumber();

    this.RIGHT = (id + 1) % n;
    this.LEFT = (id + n - 1) % n;
  }// Fim do contrutor

  // metodos
  /*********************************************************************
   * Metodo: getFork
   * Funcao: testa se eh possivel e pega os garfos
   * Parametros: void
   * Retorno: void
   *********************************************************************/
  private void getFork() { //Inicio do metodo getFork
    try { // Entra na zona critica
      control.mutex.acquire();
    } catch (InterruptedException e) {
      System.out.println("Error: philosopher " + id + " couldnt acquire mutex semaphore while trying to get the forks");
      e.printStackTrace();
    } // Fim do trycatch

    status = HUNGRY; // muda o estado para com fome
    this.updateStatusImage();
    this.testsForks(); // testa os garfos
    control.mutex.release(); // sai da zona critica

    try { // incio do trycatch
      control.semaphores[this.id].acquire(); // muda para o estado de espera caso o filosofo nao consiga pegar os dois
                                             // garfos
      control.setForksPosToPlatePos(RIGHT, id);
    } catch (InterruptedException e) {
      System.out
          .println("Error: philosopher " + id + " couldnt acquire his own semaphore while trying to get the forks");
      e.printStackTrace();
    } // fim do trycatch
  } //Fim do metodo getFork

  /*********************************************************************
   * Metodo: releaseForks
   * Funcao: devolve os garfos e verifica se as outras filosofas querem pegar estes garfos
   * Parametros: void
   * Retorno: void
   *********************************************************************/
  private void releaseForks() { //Inicio do metodo releaseForks
    try { // Entra na zona critica
      control.mutex.acquire();
    } catch (InterruptedException e) {
      System.out
          .println("Error: philosopher " + id + " couldnt acquire mutex semaphore while trying to release the forks");
      e.printStackTrace();
    } // Fim do trycatch

    status = THINKING;
    this.control.setForksPosToOriginal(id, RIGHT);
    this.updateStatusImage();

    control.philosopher[LEFT].testsForks();
    control.philosopher[RIGHT].testsForks();

    control.mutex.release(); // sai da zona critica
  } //Fim do metodo releaseForks
  
  /*********************************************************************
   * Metodo: testsForks
   * Funcao: testa se eh possivel pegar os garfos
   * Parametros: void
   * Retorno: void
   *********************************************************************/
  private void testsForks() { // inicio do metodo testsFork
    if (this.status == HUNGRY && control.philosopher[LEFT].status != EATING
        && control.philosopher[RIGHT].status != EATING) { // verifica se o filosofo pode pegar os dois garfos
      this.status = EATING;
      this.updateStatusImage();
      control.semaphores[this.id].release();
    } // fim da verificacao if
  }// fim do metodo testsFork

  /*********************************************************************
   * Metodo: updateStatusImage
   * Funcao: Atualiza a imagem que mostra o estado da filosofa
   * Parametros: void
   * Retorno: void
   *********************************************************************/
  private void updateStatusImage() { //Inicio do metodo updateStatusImage
    Platform.runLater(() -> { // Inicio do metodo runLater -> Ordena a execucao do fxml para que nao ocorra excecoes
      control.philosopherStateImage[id].setImage(control.statusImages[this.status]);
    });// Fim do metodo runLater
  } //Fim do metodo updateStatusImage

  /*********************************************************************
   * Metodo: eat
   * Funcao: Pega os garfos, come e devolve os garfos
   * Parametros: void
   * Retorno: void
   *********************************************************************/
  private void eat() { //Inicio do metodo eat
    getFork(); //Pega os garfos
    int temp = (int) this.control.timeEat[id]; //Armazena quanto tempo a filosofa gasta comendo
    for (int i = 0; i < temp; i++) //Faz com que ela coma pelo tempo determinado
      try { //Faz a thread dormir por um segundo
        sleep(1000);
      } catch (InterruptedException e) {
        System.out.println("Exception in sleep method (eat)");
        e.printStackTrace();
      } //Fim do try/catch
    releaseForks();  //solta os garfos
  } //Fim do metodo eat

  /*********************************************************************
   * Metodo: run
   * Funcao: Executa a rotina da thread
   * Parametros: void
   * Retorno: void
   *********************************************************************/
  public void run() {// inicio do metodo run
    while (this.control.isStated) {// incio do laco while
      try { //Faz a thread dormir por um segundo
        sleep(1000);
      } catch (InterruptedException e) {
        System.out.println("Exception in sleep method (run)");
        e.printStackTrace();
      } //fim do try catch

      counter++;
      System.out.println(id + ": " + counter);
      if (counter % (int) this.control.timeThink[id] == 0) { // Nao utilzar somente o sleep porque o usuario teria que esperar ate o processo acordar novamente para notar alguma mudanca
        eat();
      } //Fim do if
    } // fim do laco while
  } // fim do metodo run
}// fim da classe Philosopher
