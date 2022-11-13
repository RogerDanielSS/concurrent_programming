/* *************************************************************** 
* Autor............: Roger Daniel Santana Simoes
* Matricula........: 202010022
* Inicio...........: 09/02/2022
* Ultima alteracao.: 03/03/2022
* Nome.............: Arvore genealogica
* Funcao...........: Mostra a arvore genealogica de uma familha (pre determinada) utilizando threads
*************************************************************** */

package Controll;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

public class ArvoreController implements Initializable {
  // Atributos FXML
  @FXML
  private AnchorPane TiagoJr_AnchorPane_out;

  @FXML
  private Label Joao_morto;

  @FXML
  private TextField idade_Pedro_TextField_out;

  @FXML
  private AnchorPane Joao_AnchorPane_out;

  @FXML
  private AnchorPane PedroNeto_AnchorPane_out;

  @FXML
  private Label PedroJr_morto;

  @FXML
  private AnchorPane AnchorPane_Arvore;

  @FXML
  private Line TiagoTiagoJr_Line_out;

  @FXML
  private Line JoseTiago_Line_out;

  @FXML
  private TextField idade_Joao_TextField_out;

  @FXML
  private Line JoseJoao_Line_out;

  @FXML
  private TextField idade_PedroJr_TextField_out;

  @FXML
  private Label PedroNeto_morto;

  @FXML
  private Button Voltar_Button_in;

  @FXML
  private Label TiagoJr_morto;

  @FXML
  private Line Jose_Pedro_Line_out;

  @FXML
  private TextField idade_Jose_TextField_out;

  @FXML
  private Button Restart_Button_in;

  @FXML
  private AnchorPane Tiago_AnchorPane_out;

  @FXML
  private TextField idade_PedroNeto_TextField_out;

  @FXML
  private Label Pedro_morto;

  @FXML
  private Label Jose_morto;

  @FXML
  private AnchorPane Pedro_AnchorPane_out;

  @FXML
  private TextField ano_TextField_out;

  @FXML
  private Label Tiago_morto;

  @FXML
  private Line PedroJr_PedroNeto_Line_out;

  @FXML
  private TextField idade_TiagoJr_TextField_out;

  @FXML
  private Line Pedro_PedroJr_Line_out;

  @FXML
  private AnchorPane PedroJr_AnchorPane_out;

  @FXML
  private AnchorPane Jose_AncherPane_out;

  @FXML
  private TextField idade_Tiago_TextField_out;

  // Metodos

  /****************************************************************
   * Metodo: setToZero
   * Funcao: Retorna o programa para a tela de inicio
   * Parametros: void
   * Retorno: void
   ****************************************************************/
  private void setToZero() {
    Jose_AncherPane_out.setVisible(false);
    Pedro_AnchorPane_out.setVisible(false);
    Jose_Pedro_Line_out.setVisible(false);
    JoseTiago_Line_out.setVisible(false);
    Tiago_AnchorPane_out.setVisible(false);
    JoseJoao_Line_out.setVisible(false);
    Joao_AnchorPane_out.setVisible(false);
    Pedro_PedroJr_Line_out.setVisible(false);
    PedroJr_AnchorPane_out.setVisible(false);
    PedroJr_PedroNeto_Line_out.setVisible(false);
    PedroNeto_AnchorPane_out.setVisible(false);
    TiagoTiagoJr_Line_out.setVisible(false);
    TiagoJr_AnchorPane_out.setVisible(false);
    Joao_morto.setVisible(false);
    Jose_morto.setVisible(false);
    Pedro_morto.setVisible(false);
    PedroJr_morto.setVisible(false);
    PedroNeto_morto.setVisible(false);
    Tiago_morto.setVisible(false);
    TiagoJr_morto.setVisible(false);
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
     * Sub rotina: botao restart
     * Funcao: Inicia a rodar a arvore
     * Parametros: Um evento (o botao ser pressionado)
     * Retorno: void
     ****************************************************************/
    Restart_Button_in.setOnAction(event -> { // Inicio do controle do botao Iniciar
      setToZero();
      Voltar_Button_in.setVisible(false);
      Restart_Button_in.setVisible(false);
      Jose jose = new Jose();
      jose.setPriority(5);
	  ano_TextField_out.setVisible(true);
      Jose_AncherPane_out.setVisible(true);
      jose.start();
    }); // Fim do controle do botao Iniciar

    /****************************************************************
     * Sub rotina: botao voltar
     * Funcao: Retorna para a tela inicial
     * Parametros: Um evento (o botao ser pressionado)
     * Retorno: void
     ****************************************************************/
    Voltar_Button_in.setOnAction(event -> { // Inicio do controle do botao Voltar
      try {// Inicio do try
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/View/Home.fxml")); // Chamando o fxml
        AnchorPane_Arvore.getChildren().setAll(a);
      } catch (Exception e) { // Fim do try, inicio do catch
        System.out.println("Error: Tentativa de mudar a cena para o historico \n" + e.getMessage());
      } // Fim do catch
    }); // Fim do controle do botao Voltar
  }// Fim do metodo initialize

  /*
   * ***************************************************************
   * Sub rotina: Jose
   * Funcao: Cria 3 novas threads e atualiza o contador de idade (jose) e de tempo do da arvore genealogica
   * Parametros: void
   * Retorno: void
   ****************************************************************/
  class Jose extends Thread { // Inicio da classe Jose
    // Atributos
    int idade;

  
    /*********************************************************************
     * Metodo: run
     * Funcao: executa a thread
     * Parametros: void
     * Retorno: void
     ****************************************************************/
    public void run() {
      for (idade = 0; idade < 90; idade++) { // Inicio do laco while responsavel pela contagem do tempo
        try {// inicio do trycatch
          sleep(1020); // Faz com que a thread espere 1 segundo para continuar executando
        } catch (Exception e) {
          System.out.println("O erro foi em Jose");
        } // fim do trycatch

        Platform.runLater(() -> { // Inicio do metodo runLater -> Ordena a execucao do fxml para que nao ocorra excecoes
          idade_Jose_TextField_out.setText("Idade: " + idade + " anos");
          ano_TextField_out.setText("" + (2000 + idade));
        });// Fim do metodo runLater

        if (idade == 22) { // verifica a idade certa de criar uma nova thread
          //inicia uma nova thread
		  Pedro pedro = new Pedro();
		  pedro.setPriority(10);
          pedro.start();
          Platform.runLater(() -> { // Inicio do metodo runLater -> Ordena a execucao do fxml para que nao ocorra excecoes
            Pedro_AnchorPane_out.setVisible(true);
            Jose_Pedro_Line_out.setVisible(true);
          });// Fim do metodo runLater
        } // Fim da verificacao if

        if (idade == 25) { // verifica a idade certa de criar uma nova thread
          //inicia uma nova thread
		  Tiago tiago = new Tiago();
		  tiago.setPriority(10);
          tiago.start();
          Platform.runLater(() -> { // Inicio do metodo runLater -> Ordena a execucao do fxml para que nao ocorra
                                    // excecoes
            JoseTiago_Line_out.setVisible(true);
            Tiago_AnchorPane_out.setVisible(true);
          });// Fim do metodo runLater
        } // Fim da verificacao if

        if (idade == 32) { // verifica a idade certa de criar uma nova thread
          //inicia uma nova thread
		  
          Joao joao = new Joao();
		  joao.setPriority(10);
          joao.start();
          Platform.runLater(() -> { // Inicio do metodo runLater -> Ordena a execucao do fxml para que nao ocorra excecoes
            JoseJoao_Line_out.setVisible(true);
            Joao_AnchorPane_out.setVisible(true);
          });// Fim do metodo runLater
        } // Fim da verificacao if
      } // Fim do laco for responsavel pela contagem do tempo

      Platform.runLater(() -> { // Inicio do metodo runLater -> Ordena a execucao do fxml para que nao ocorra excecoes
        idade_Jose_TextField_out.setText("Morto aos 90"); // Informa que Jose morreu no local onde era impresso a sua idade
        Jose_morto.setVisible(true);// Imprime um x na frente da foto de Jose
        ano_TextField_out.setVisible(true);
        Voltar_Button_in.setVisible(true);
        Restart_Button_in.setVisible(true);
      });// Fim do metodo runLater
    }// fim do metodo run
  }// fim da classe Jose

  /****************************************************************
   * Sub rotina: Pedro
   * Funcao: Cria 1 nova threads e atualiza o contador de idade (Pedro) na arvore genealogica
   * Parametros: void
   * Retorno: void
   ****************************************************************/
  class Pedro extends Thread { // Inicio da classe Pedro
    // Atributos
    int idade;

    /*********************************************************************
     * Metodo: run
     * Funcao: executa a thread
     * Parametros: void
     * Retorno: void
     ****************************************************************/
    public void run() {
      for (idade = 0; idade < 61; idade++) { // Inicio do laco while responsavel pela contagem do tempo
        try {// inicio do trycatch
          sleep(1000); // Faz com que a thread espere 1 segundo para continuar executando
        } catch (Exception e) {
          System.out.println("O erro foi em Pedro");
        } // fim do trycatch

        Platform.runLater(() -> { // Inicio do metodo runLater -> Ordena a execucao do fxml para que nao ocorra  excecoes
          idade_Pedro_TextField_out.setText("Idade: " + idade + " anos");
        });// Fim do metodo runLater

        if (idade == 16) { // verifica a idade certa de criar uma nova thread
          //inicia uma nova thread
		  PedroJr pedroJr = new PedroJr();
		  pedroJr.setPriority(10);
          pedroJr.start();
          Platform.runLater(() -> { // Inicio do metodo runLater -> Ordena a execucao do fxml para que nao ocorra excecoes
            Pedro_PedroJr_Line_out.setVisible(true);
            PedroJr_AnchorPane_out.setVisible(true);
          });// Fim do metodo runLater
        } // Fim da verificacao if

      } // Fim do laco while responsavel pela contagem do tempo
      Platform.runLater(() -> { // Inicio do metodo runLater -> Ordena a execucao do fxml para que nao ocorra excecoes
        idade_Pedro_TextField_out.setText("Morto aos 61"); // Informa que Pedro morreu no local onde era impresso a sua idade
        Pedro_morto.setVisible(true);// Imprime um x na frente da foto de Pedro
      });// Fim do metodo runLater
    }// fim do metodo run
  }// fim da classe Pedro

  /****************************************************************
   * Sub rotina: Pedro Jr
   * Funcao: Cria 1 nova threads e atualiza o contador de idade (Pedro Jr) na arvore
   * genealogica
   * Parametros: void
   * Retorno: void
   ****************************************************************/
  class PedroJr extends Thread { // Inicio da classe Pedro Jr
    // Atributos
    int idade;
     
    /*********************************************************************
     * Metodo: run
     * Funcao: executa a thread
     * Parametros: void
     * Retorno: void
     ****************************************************************/
    public void run() {
      for (idade = 0; idade < 35; idade++) { // Inicio do laco while responsavel pela contagem do tempo
        try {// inicio do trycatch
          sleep(1000); // Faz com que a thread espere 1 segundo para continuar executando
        } catch (Exception e) {
          System.out.println("O erro em Pedro Jr");
        } // fim do trycatch

        Platform.runLater(() -> { // Inicio do metodo runLater -> Ordena a execucao do fxml para que nao ocorra excecoes
          idade_PedroJr_TextField_out.setText("idade: " + idade + " anos"); // Mostra a idade de Pedro Jr
        });// Fim do metodo runLater

        if (idade == 30) { // verifica a idade certa de criar uma nova thread
          //inicia uma nova thread
		  PedroNeto pedroNeto = new PedroNeto();
		  pedroNeto.setPriority(10);
          pedroNeto.start();
          Platform.runLater(() -> { // Inicio do metodo runLater -> Ordena a execucao do fxml para que nao ocorra excecoes
            PedroJr_PedroNeto_Line_out.setVisible(true);
            PedroNeto_AnchorPane_out.setVisible(true);
          });// Fim do metodo runLater
        } // Fim da verificacao if
      } // Fim do laco while responsavel pela contagem do tempo

      Platform.runLater(() -> { // Inicio do metodo runLater -> Ordena a execucao do fxml para que nao ocorra excecoes
        idade_PedroJr_TextField_out.setText("Morto aos 35"); // Informa que Pedro Jr morreu no local onde era impresso a sua idade
        PedroJr_morto.setVisible(true);// Imprime um x na frente da foto de PedroJr
      });// Fim do metodo runLater
    }// fim do metodo run

  }// fim da classe Pedro Jr

  /****************************************************************
   * Sub rotina: Pedro Neto
   * Funcao: atualiza o contador de idade (Pedro Neto) na arvore genealogica
   * Parametros: void
   * Retorno: void
   ****************************************************************/
  class PedroNeto extends Thread { // Inicio da classe Pedro Neto
    // Atributos
    int idade;

    /*********************************************************************
     * Metodo: run
     * Funcao: executa a thread
     * Parametros: void
     * Retorno: void
     ****************************************************************/
    public void run() {
      for (idade = 0; idade < 12; idade++) { // Inicio do laco while responsavel pela contagem do tempo
        try {// inicio do trycatch
          sleep(1000); // Faz com que a thread espere 1 segundo para continuar executando
        } catch (Exception e) {
          System.out.println("O erro foi em Pedro Neto ");
        } // fim do trycatch

        Platform.runLater(() -> { // Inicio do metodo runLater -> Ordena a execucao do fxml para que nao ocorra excecoes
          idade_PedroNeto_TextField_out.setText("Idade: " + idade + " anos"); // Mostra a idade de Pedro Neto
        });// Fim do metodo runLater
      } // Fim do laco while responsavel pela contagem do tempo

      Platform.runLater(() -> { // Inicio do metodo runLater -> Ordena a execucao do fxml para que nao ocorra excecoes
        idade_PedroNeto_TextField_out.setText("Morto aos 12"); // Informa que Pedro Neto morreu no local onde era impresso a sua idade
        PedroNeto_morto.setVisible(true);// Imprime um x na frente da foto de PedroNeto
      });// Fim do metodo runLater
    }// fim do metodo run

  }// fim da classe Pedro Neto

  /****************************************************************
   * Sub rotina: Tiago
   * Funcao: Cria 1 nova threads e atualiza o contador de idade (Tiago) na arvore
   * genealogica
   * Parametros: void
   * Retorno: void
   */
  class Tiago extends Thread { // Inicio da classe Tiago
    // Atributos
    int idade;

    /*********************************************************************
     * Metodo: run
     * Funcao: executa a thread
     * Parametros: void
     * Retorno: void
     */
    public void run() {
      for (idade = 0; idade < 55; idade++) { // Inicio do laco while responsavel pela contagem do tempo
        try {// inicio do trycatch
          sleep(1000); // Faz com que a thread espere 1 segundo para continuar executando
        } catch (Exception e) {
          System.out.println("O erro em Jose");
        } // fim do trycatch

        Platform.runLater(() -> { // Inicio do metodo runLater -> Ordena a execucao do fxml para que nao ocorra excecoes
          idade_Tiago_TextField_out.setText("Idade: " + idade + " anos");
        });// Fim do metodo runLater

        if (idade == 20) {// verifica a idade certa de criar uma nova thread
          //inicia uma nova thread
		  TiagoJr tiagoJr = new TiagoJr();
		  tiagoJr.setPriority(10);
          tiagoJr.start();
          Platform.runLater(() -> { // Inicio do metodo runLater -> Ordena a execucao do fxml para que nao ocorra
                                    // excecoes
            TiagoTiagoJr_Line_out.setVisible(true);
            TiagoJr_AnchorPane_out.setVisible(true);
          });// Fim do metodo runLater
        }//Fim da verificacao if 

      } // Fim do laco while responsavel pela contagem do tempo

      Platform.runLater(() -> { // Inicio do metodo runLater -> Ordena a execucao do fxml para que nao ocorra excecoes
        idade_Tiago_TextField_out.setText("Morto aos 55"); // Informa que Tiago morreu no local onde era impresso a sua
                                                           // idade
        Tiago_morto.setVisible(true);// Imprime um x na frente da foto de Tiago
      });// Fim do metodo runLater
    }// fim do metodo run

  }// fim da classe Tiago

  /****************************************************************
   * Sub rotina: Tiago Jr
   * Funcao: atualiza o contador de idade (Tiago Jr) na arvore genealogica
   * Parametros: void
   * Retorno: void
   ****************************************************************/
  class TiagoJr extends Thread { // Inicio da classe Tiago Jr
    // Atributos
    int idade;

    /*********************************************************************
     * Metodo: run
     * Funcao: executa a thread
     * Parametros: void
     * Retorno: void
    ****************************************************************/
    public void run() {
      for (idade = 0; idade < 33; idade++) { // Inicio do laco while responsavel pela contagem do tempo
        try {// inicio do trycatch
          sleep(1000); // Faz com que a thread espere 1 segundo para continuar executando
        } catch (Exception e) {
          System.out.println("O erro em Tiago Jr");
        } // fim do trycatch

        Platform.runLater(() -> { // Inicio do metodo runLater -> Ordena a execucao do fxml para que nao ocorra excecoes
          idade_TiagoJr_TextField_out.setText("Idade: " + idade + " anos"); // Mostra a idade de Tiago Jr
        });// Fim do metodo runLater

      } // Fim do laco while responsavel pela contagem do tempo

      Platform.runLater(() -> { // Inicio do metodo runLater -> Ordena a execucao do fxml para que nao ocorra excecoes
        idade_TiagoJr_TextField_out.setText("Morto aos 33"); // Informa que Tiago Jr morreu no local onde era impresso a sua idade
        TiagoJr_morto.setVisible(true);// Imprime um x na frente da foto de TiagoJr
      });// Fim do metodo runLater
    }// fim do metodo run

  }// fim da classe Tiago Jr

  /****************************************************************
   * Sub rotina: Joao
   * Funcao: atualiza o contador de idade (Joao) na arvore
   * genealogica
   * Parametros: void
   * Retorno: void
   ****************************************************************/
  class Joao extends Thread { // Inicio da classe Joao
    // Atributos
    int idade;

    /*********************************************************************
     * Metodo: run
     * Funcao: executa a thread
     * Parametros: void
     * Retorno: void
    ****************************************************************/
    public void run() {
      for (idade = 0; idade < 55; idade++) { // Inicio do laco while responsavel pela contagem do tempo
        try {// inicio do trycatch
          sleep(1000); // Faz com que a thread espere 1 segundo para continuar executando
        } catch (Exception e) {
          System.out.println("O erro em Joao");
        } // fim do trycatch

        Platform.runLater(() -> { // Inicio do metodo runLater -> Ordena a execucao do fxml para que nao ocorra excecoes
          idade_Joao_TextField_out.setText("idade: " + idade + " anos"); // Mostra a idade de Joao
        });// Fim do metodo runLater

      } // Fim do laco while responsavel pela contagem do tempo
      // Fim do laco for responsavel pela contagem do tempo

      Platform.runLater(() -> { // Inicio do metodo runLater -> Ordena a execucao do fxml para que nao ocorra excecoes
        idade_Joao_TextField_out.setText("Morto aos 55"); // Informa que Joao morreu no local onde era impresso a sua idade
        Joao_morto.setVisible(true);// Imprime um x na frente da foto de Joao
      }); // Fim do metodo runLater
    }// fim do metodo run

  }// fim da classe Joao
}// Fim da classe ArvoreController