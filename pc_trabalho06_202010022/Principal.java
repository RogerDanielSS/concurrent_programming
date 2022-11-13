/* *************************************************************** 
* Autor............: Roger Daniel Santana Simoes
* Matricula........: 202010022
* Inicio...........: 05/05/2022
* Ultima alteracao.: 07/05/2022
* Nome.............: Leitores e escritores
* Funcao...........: Simula a solucao com semaforos para o problema classico dos leitores e escritores usando uma interface grafica
*************************************************************** */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Controll.HomeController;
import Controll.SimulationController;

/****************************************************************
 * Classe: Principal
 * Funcao: Executa o programa
 * Parametros: Parametros inerentes do Java
 ****************************************************************/
public class Principal extends Application { // Inicio da classe Principal
  /*
   * ***************************************************************
   * Metodo: main
   * Funcao: metodo padrao que executa o programa
   * Parametros: Vetor de Strings chamado args
   * Retorno: vazio
   */
  public static void main(String[] args) throws Exception { // inicio do metodo main
    launch(args);

    // Criando variaveis das classes de controle dentro da main para que o comando
    // javac busque e compile esses arquivoss
    // HomeController hc = new HomeController();
    SimulationController sc = new SimulationController();
    HomeController hc = new HomeController();
  }// fim do metodo main

  /*
   * ***************************************************************
   * Metodo: start
   * Funcao: metodo de Aplication sobrescrito que carrega o palco
   * Parametros: Um Stage, do javaFX
   * Retorno: vazio
   */
  @Override
  public void start(Stage stage) throws Exception { // inicio do metodo start
    Parent root = FXMLLoader.load(getClass().getResource("View/Home.fxml"));
    Scene Scene = new Scene(root);
    stage.setScene(Scene);
    stage.setResizable(false);
    stage.show();
  } // fim do metodo start
} // Fim da classe Principal