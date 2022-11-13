package Controll;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

public class ArvoreController implements Initializable {

  @FXML
  private AnchorPane TiagoJr_AnchorPane_out;

  @FXML
  private Line Jose_Pedro_Line_out;

  @FXML
  private TextField idade_Jose_TextField_out;

  @FXML
  private TextField idade_Pedro_TextField_out;

  @FXML
  private TextField idade_PedroJr_TextField_out;

  @FXML
  private AnchorPane Joao_AnchorPane_out;

  @FXML
  private TextField idade_Joao_TextField_out;

  @FXML
  private Button Restart_Button_in;

  @FXML
  private AnchorPane PedroNeto_AnchorPane_out;

  @FXML
  private AnchorPane Tiago_AnchorPane_out;

  @FXML
  private TextField idade_PedroNeto_TextField_out;

  @FXML
  private AnchorPane AnchorPane_Arvore;

  @FXML
  private Line TiagoTiagoJr_Line_out;

  @FXML
  private AnchorPane Pedro_AnchorPane_out;

  @FXML
  private Line JoseTiago_Line_out;

  @FXML
  private TextField idade_Tiago_TextField_out;

  @FXML
  private Line PedroJr_PedroNeto_Line_out;

  @FXML
  private Line JoseJoao_Line_out;

  @FXML
  private TextField idade_TiagoJr_TextField_out;

  @FXML
  private Line Pedro_PedroJr_Line_out;

  @FXML
  private AnchorPane PedroJr_AnchorPane_out;

  @FXML
  private Button Voltar_Button_in;

  @FXML
  private AnchorPane Jose_AncherPane_out;

  @FXML
  private TextField ano_TextField_out;

  Jose jose = new Jose();
  int Time = 0;

  private synchronized void setTime(int t) {
    Time = t;
  }

  private synchronized int getTime() {
    return Time;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) { // Inicio do metodo Initializ
    Restart_Button_in.setOnAction(event -> { // Inicio do controle do botao Iniciar

      Jose_AncherPane_out.setVisible(true);
      jose.start();

    }); // Fim do controle do botao Iniciar
  }// Fim do metodo initialize

  class Jose extends Thread { // Inicio da classe Jose
    // Atributos
    String nome = "Jose";

    public void run() {
      for (int idade = 0; idade < 90; idade++) { // Inicio do laco while responsavel pela contagem do tempo
        try {
          sleep(1000);
        } catch (InterruptedException e) {
          System.out.println("O erro foi aqui");
        }
        setTime(idade);
        idade_Jose_TextField_out.setText("idade: " + idade + " anos");
        ano_TextField_out.setText("" + (2000 + idade));

        if (idade == 22) { // Faz com que quado jose tiver 22 anos, tenha seu primeiro fiho
          Pedro pedro = new Pedro();
          pedro.setPriority(4);
          pedro.start();
          Pedro_AnchorPane_out.setVisible(true);
          Jose_Pedro_Line_out.setVisible(true);
        }

        if (idade == 25) {
          Tiago tiago = new Tiago();
          tiago.setPriority(4);
          tiago.start();
          JoseTiago_Line_out.setVisible(true);
          Tiago_AnchorPane_out.setVisible(true);
        }

        if (idade == 32) {
          Joao joao = new Joao();
          joao.setPriority(4);
          joao.start();
          JoseJoao_Line_out.setVisible(true);
          Joao_AnchorPane_out.setVisible(true);
        }
      } // Fim do laco for responsavel pela contagem do tempo

      idade_Jose_TextField_out.setText("Morto aos 90"); // Informa que Jose morreu no local onde era impresso a sua
                                                        // idade

      // System.out.println("Jose Morre");
    }// fim do metodo run
  }// fim da classe Jose

  class Pedro extends Thread { // Inicio da classe Pedro
    // Atributos
    String nome = "Pedro";

    public void run() {
      int idade = getTime();
      int pastIdade = idade - 1;
      while (idade < 83) { // Inicio do laco while responsavel pela contagem do tempo
        idade = getTime();
        if (idade != pastIdade) {
          idade_Pedro_TextField_out.setText("idade: " + (idade - 22) + " anos"); // Mostra a idade de Pedro

          if (idade == 38) {
            PedroJr pedroJr = new PedroJr();
            pedroJr.setPriority(4);
            pedroJr.start();
            Pedro_PedroJr_Line_out.setVisible(true);
            PedroJr_AnchorPane_out.setVisible(true);
          }
        }

        pastIdade = idade;
      } // Fim do laco while responsavel pela contagem do tempo
      idade_Pedro_TextField_out.setText("Morto aos 61"); // Informa que Pedro morreu no local onde era impresso a sua
                                                         // idade
    }// fim do metodo run
  }// fim da classe Pedro

  class PedroJr extends Thread { // Inicio da classe Pedro Jr
    // Atributos
    String nome = "Pedro Jr";

    public void run() {
      int idade = getTime();
      int pastIdade = idade - 1;
      while (idade < 73) { // Inicio do laco while responsavel pela contagem do tempo
        idade = getTime();
        if (idade != pastIdade) {
          idade_PedroJr_TextField_out.setText("idade: " + (idade - 38) + "anos"); // Mostra a idade de Pedro Jr

          if (idade == 68) {
            PedroNeto pedroNeto = new PedroNeto();
            pedroNeto.setPriority(4);
            pedroNeto.start();
            PedroJr_PedroNeto_Line_out.setVisible(true);
            PedroNeto_AnchorPane_out.setVisible(true);
          }
        }

        pastIdade = idade;
      } // Fim do laco while responsavel pela contagem do tempo

      idade_PedroJr_TextField_out.setText("Morto aos 35"); // Informa que Pedro Jr morreu no local onde era impresso a
                                                           // sua idade
    }// fim do metodo run

  }// fim da classe Pedro Jr

  class PedroNeto extends Thread { // Inicio da classe Pedro Neto
    // Atributos
    String nome = "Pedro Neto";

    public void run() {
      int idade = getTime();
      int pastIdade = idade - 1;
      while (idade < 80) { // Inicio do laco while responsavel pela contagem do tempo
        idade = getTime();
        if (idade != pastIdade)
          idade_PedroNeto_TextField_out.setText("idade: " + (idade - 68) + "anos"); // Mostra a idade de Pedro Neto

        pastIdade = idade;
      } // Fim do laco while responsavel pela contagem do tempo

      idade_PedroNeto_TextField_out.setText("Morto aos 12"); // Informa que Pedro Neto morreu no local onde era impresso
                                                             // a sua idade
    }// fim do metodo run

  }// fim da classe Pedro Neto

  class Tiago extends Thread { // Inicio da classe Tiago
    // Atributos
    String nome = "Tiago";

    public void run() {
      int idade = getTime();
      int pastIdade = idade - 1;
      while (idade < 80) { // Inicio do laco while responsavel pela contagem do tempo
        idade = getTime();
        if (idade != pastIdade) {
          idade_Tiago_TextField_out.setText("idade: " + (idade - 25) + "anos"); // Mostra a idade de Tiago

          if (idade == 45) {
            TiagoJr tiagoJr = new TiagoJr();
            tiagoJr.setPriority(4);
            tiagoJr.start();
            TiagoTiagoJr_Line_out.setVisible(true);
            TiagoJr_AnchorPane_out.setVisible(true);
          }
        }

        pastIdade = idade;
      } // Fim do laco while responsavel pela contagem do tempo

      idade_Tiago_TextField_out.setText("Morto aos 55"); // Informa que Tiago morreu no local onde era impresso a sua
                                                         // idade
    }// fim do metodo run

  }// fim da classe Tiago

  class TiagoJr extends Thread { // Inicio da classe Tiago Jr
    // Atributos
    String nome = "Tiago Jr";

    public void run() {
      int idade = getTime();
      int pastIdade = idade - 1;
      while (idade < 78) { // Inicio do laco while responsavel pela contagem do tempo
        idade = getTime();
        if (idade != pastIdade) {
          idade_TiagoJr_TextField_out.setText("idade: " + (idade - 45) + "anos"); // Mostra a idade de Tiago Jr
        }
        pastIdade = idade;
      } // Fim do laco while responsavel pela contagem do tempo

      idade_TiagoJr_TextField_out.setText("Morto aos 33"); // Informa que Tiago Jr morreu no local onde era impresso a
                                                           // sua idade
    }// fim do metodo run

  }// fim da classe Tiago Jr

  class Joao extends Thread { // Inicio da classe Joao
    // Atributos
    String nome = "Joao";

    public void run() {
      int idade = getTime();
      int pastIdade = idade - 1;
      while (idade < 87) { // Inicio do laco while responsavel pela contagem do tempo
        idade = getTime();
        if (idade != pastIdade) {
          idade_Joao_TextField_out.setText("idade: " + (idade - 32) + "anos"); // Mostra a idade de Joao
        }
        pastIdade = idade;
      } // Fim do laco while responsavel pela contagem do tempo
      // Fim do laco for responsavel pela contagem do tempo

      idade_Joao_TextField_out.setText("Morto aos 55"); // Informa que Joao morreu no local onde era impresso a sua
                                                        // idade
    }// fim do metodo run

  }// fim da classe Joao

}// Fim da classe ArvoreController