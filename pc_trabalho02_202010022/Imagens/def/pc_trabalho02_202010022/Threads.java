import Controll.HomeController;
/*=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= 
Autor....: Roger Daniel Santana Simoes Matrícula:
Inicio...: 17 de agosto de 2012 
Ultima Alteracao: 17 de agosto de 2012 
Nome.....: Principal.java 
Funcao...: Exemplo da criacao de Threads em Java
=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=*/
import java.lang.Thread;

class Jose extends Thread { // Inicio da classe Jose
  // Atributos
  String nome = "Jose";

  public void run() {
    // Counter counter = new Counter();

    int lastCounter = 0; // Cria o contador de tempo anterior para fins de controle
    for (int time = 0; time <= 90; time++) { // Inicio do laco while responsavel pela contagem do tempo
      try {
        sleep(1000);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      // time = counter.getTime(); // Cria a variavel time para fazer um menor uso do
      // processador (nao utilizar o
      // metodo repetidas vezes)

      // boolean oneSecondIsGone = (time != lastCounter); // Como esse laco se repete
      // varias vezes em um segundo, um
      // segundo so eh registrado quando os contadores estao
      // diferentes

      // if (oneSecondIsGone)
      System.out.println("idade de Jose: " + time + "id de Jose " + toString());// Imprime o tempo ####
                                                                                // teste ####

      if (time == 22) {
        Pedro pedro = new Pedro();
        pedro.setPriority(1);
        pedro.start();
      }

      if (time == 25) {
        Tiago tiago = new Tiago();
        tiago.setPriority(1);
        tiago.start();
      }

      if (time == 32) {
        Joao joao = new Joao();
        joao.setPriority(1);
        joao.start();
      }

      // lastCounter = time; // atualiza o contador de tempo anterior para realizar a
      // verificacao de tempo
      // corretamente
    } // Fim do laco while responsavel pela contagem do tempo

    System.out.println("Jose Morre");
  }// fim do metodo run

}// fim da classe Jose

class Pedro extends Thread { // Inicio da classe Pedro
  // Atributos
  String nome = "Pedro";

  public void run() {
    Counter counter = new Counter();

    int lastCounter = 0; // Cria o contador de tempo anterior para fins de controle
    while (counter.getTime() <= 61) { // Inicio do laco while responsavel pela contagem do tempo
      int time = counter.getTime(); // Cria a variavel time para fazer um menor uso do processador (nao utilizar o
                                    // metodo repetidas vezes)

      boolean oneSecondIsGone = (time != lastCounter); // Como esse laco se repete varias vezes em um segundo, um
                                                       // segundo so eh registrado quando os contadores estao diferentes

      if (oneSecondIsGone)
        System.out.println("idade de Pedro: " + time);// Imprime o tempo #### teste ####

      if (oneSecondIsGone && time == 16) {
        PedroJr pedroJr = new PedroJr();
        pedroJr.setPriority(1);
        pedroJr.start();
      }

      lastCounter = time; // atualiza o contador de tempo anterior para realizar a verificacao de tempo
                          // corretamente
    } // Fim do laco while responsavel pela contagem do tempo

    System.out.println("Pedro Morre");
  }// fim do metodo run

}// fim da classe Pedro

class PedroJr extends Thread { // Inicio da classe Pedro Jr
  // Atributos
  String nome = "Pedro Jr";

  public void run() {
    Counter counter = new Counter();

    int lastCounter = 0; // Cria o contador de tempo anterior para fins de controle
    while (counter.getTime() <= 35) { // Inicio do laco while responsavel pela contagem do tempo
      int time = counter.getTime(); // Cria a variavel time para fazer um menor uso do processador (nao utilizar o
                                    // metodo repetidas vezes)

      boolean oneSecondIsGone = (time != lastCounter); // Como esse laco se repete varias vezes em um segundo, um
                                                       // segundo so eh registrado quando os contadores estao diferentes

      if (oneSecondIsGone)
        System.out.println("idade de Pedro Jr: " + time);// Imprime o tempo #### teste ####

      if (oneSecondIsGone && time == 30) {
        PedroNeto pedroNeto = new PedroNeto();
        pedroNeto.setPriority(1);
        pedroNeto.start();
      }

      lastCounter = time; // atualiza o contador de tempo anterior para realizar a verificacao de tempo
                          // corretamente
    } // Fim do laco while responsavel pela contagem do tempo

    System.out.println("Pedro Jr Morre");
  }// fim do metodo run

}// fim da classe Pedro Jr

class PedroNeto extends Thread { // Inicio da classe Pedro Neto
  // Atributos
  String nome = "Pedro Neto";

  public void run() {
    Counter counter = new Counter();

    int lastCounter = 0; // Cria o contador de tempo anterior para fins de controle
    while (counter.getTime() <= 12) { // Inicio do laco while responsavel pela contagem do tempo
      int time = counter.getTime(); // Cria a variavel time para fazer um menor uso do processador (nao utilizar o
                                    // metodo repetidas vezes)

      boolean oneSecondIsGone = (time != lastCounter); // Como esse laco se repete varias vezes em um segundo, um
                                                       // segundo so eh registrado quando os contadores estao diferentes

      if (oneSecondIsGone)
        System.out.println("idade de Pedro Neto: " + time);// Imprime o tempo #### teste ####

      lastCounter = time; // atualiza o contador de tempo anterior para realizar a verificacao de tempo
                          // corretamente
    } // Fim do laco while responsavel pela contagem do tempo

    System.out.println("Pedro Neto Morre");
  }// fim do metodo run

}// fim da classe Pedro Neto

class Tiago extends Thread { // Inicio da classe Tiago
  // Atributos
  String nome = "Tiago";

  public void run() {
    Counter counter = new Counter();

    int lastCounter = 0; // Cria o contador de tempo anterior para fins de controle
    while (counter.getTime() <= 55) { // Inicio do laco while responsavel pela contagem do tempo
      int time = counter.getTime(); // Cria a variavel time para fazer um menor uso do processador (nao utilizar o
                                    // metodo repetidas vezes)

      boolean oneSecondIsGone = (time != lastCounter); // Como esse laco se repete varias vezes em um segundo, um
                                                       // segundo so eh registrado quando os contadores estao diferentes

      if (oneSecondIsGone)
        System.out.println("idade de Tiago: " + time);// Imprime o tempo #### teste ####

      if (oneSecondIsGone && time == 20) {
        TiagoJr tiagoJr = new TiagoJr();
        tiagoJr.setPriority(1);
        tiagoJr.start();
      }

      lastCounter = time; // atualiza o contador de tempo anterior para realizar a verificacao de tempo
                          // corretamente
    } // Fim do laco while responsavel pela contagem do tempo

    System.out.println("Tiago Morre");
  }// fim do metodo run

}// fim da classe Tiago

class TiagoJr extends Thread { // Inicio da classe Tiago Jr
  // Atributos
  String nome = "Tiago Jr";

  public void run() {
    Counter counter = new Counter();

    int lastCounter = 0; // Cria o contador de tempo anterior para fins de controle
    while (counter.getTime() <= 33) { // Inicio do laco while responsavel pela contagem do tempo
      int time = counter.getTime(); // Cria a variavel time para fazer um menor uso do processador (nao utilizar o
                                    // metodo repetidas vezes)

      boolean oneSecondIsGone = (time != lastCounter); // Como esse laco se repete varias vezes em um segundo, um
                                                       // segundo so eh registrado quando os contadores estao diferentes

      if (oneSecondIsGone)
        System.out.println("idade de Tiago Jr: " + time);// Imprime o tempo #### teste ####

      lastCounter = time; // atualiza o contador de tempo anterior para realizar a verificacao de tempo
                          // corretamente
    } // Fim do laco while responsavel pela contagem do tempo

    System.out.println("Tiago Jr Morre");
  }// fim do metodo run

}// fim da classe Tiago Jr

class Joao extends Thread { // Inicio da classe Joao
  // Atributos
  String nome = "Joao";

  public void run() {
    Counter counter = new Counter();

    int lastCounter = 0; // Cria o contador de tempo anterior para fins de controle
    while (counter.getTime() <= 55) { // Inicio do laco while responsavel pela contagem do tempo
      int time = counter.getTime(); // Cria a variavel time para fazer um menor uso do processador (nao utilizar o
                                    // metodo repetidas vezes)

      boolean oneSecondIsGone = (time != lastCounter); // Como esse laco se repete varias vezes em um segundo, um
                                                       // segundo so eh registrado quando os contadores estao diferentes

      if (oneSecondIsGone)
        System.out.println("idade de Joao: " + time);// Imprime o tempo #### teste ####

      lastCounter = time; // atualiza o contador de tempo anterior para realizar a verificacao de tempo
                          // corretamente
    } // Fim do laco while responsavel pela contagem do tempo

    System.out.println("Joao Morre");
  }// fim do metodo run

}// fim da classe Joao

class Counter {
  // atributos
  private final long creationMoment = System.currentTimeMillis();

  int getTime() {
    return (int) ((System.currentTimeMillis() - creationMoment) / 1000);
  }
}

public class Threads {// Inicio da classe Principal

  public static void main(String args[]) { // Inicio do metodo main
    Counter counter = new Counter();

    int lastCounter = 0; // Cria o contador de tempo anterior para fins de controle
    while (counter.getTime() < 101) { // Inicio do laco while responsavel pela contagem do tempo
      int time = counter.getTime(); // Cria a variavel time para fazer um menor uso do processador (nao utilizar o
                                    // metodo repetidas vezes)

      boolean oneSecondIsGone = (time != lastCounter); // Como esse laco se repete varias vezes em um segundo, um
                                                       // segundo so eh registrado quando os contadores estao
                                                       // diferentes

      if (oneSecondIsGone)
        System.out.println("Ano" + time);// Imprime o tempo #### teste ####

      if (oneSecondIsGone && time == 5) {
        Jose jose = new Jose();
        jose.setPriority(1);
        jose.start();
      }

      lastCounter = time; // atualiza o contador de tempo anterior para realizar a verificacao de tempo
                          // corretamente
    } // Fim do laco while responsavel pela contagem do tempo

  } // Fim do metodo main
} // Fim da classe Principal