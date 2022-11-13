/* *************************************************************** 
* Autor............: Roger Daniel Santana Simoes
* Matricula........: 202010022
* Inicio...........: 23/03/2022
* Ultima alteracao.: 26/03/2022
* Nome.............: Simulacao de tunel de trem
* Funcao...........: Faz a simulacao de dois trens que tentam acessar os mesmos trilhos utilizando threads
*************************************************************** */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Controll.HomeController;
//Demais .java da documentacao
//import Controll.HomeController;
import Controll.SimulacaoController;

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
    SimulacaoController sc = new SimulacaoController();
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