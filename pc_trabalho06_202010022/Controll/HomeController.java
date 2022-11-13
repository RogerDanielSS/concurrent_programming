/* *************************************************************** 
* Autor............: Roger Daniel Santana Simoes
* Matricula........: 202010022
* Inicio...........: 05/05/2022
* Ultima alteracao.: 07/05/2022
* Nome.............: Leitores e escritores
* Funcao...........: Simula a solucao com semaforos para o problema classico dos leitores e escritores usando uma interface grafica
*************************************************************** */


package Controll;

import java.io.File;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/*********************************************************************
 * Classe: HomeController
 * Funcao: Controla o fxml da home
 * Parametros: objetos do fxml e variaveis de controle
 *********************************************************************/
public class HomeController implements Initializable {

  @FXML
  private Button Ajuda_Button_in, Iniciar_Button_in, explicacao_Button;

  @FXML
  private GridPane botoes;

  @FXML
  private AnchorPane AnchorPane_TelaIncial;

  @FXML
  private TextArea Explicacao_TextArea;

  /****************************************************************
   * Metodo: initialize
   * Funcao: Observa quais botoes do programa sao pressinados para executar uma sub rotina em cada caso
   * Parametros: Parametros padroes nao diretamente relacionados a este app
   * Retorno: void
   ****************************************************************/
  @Override
  public void initialize(URL location, ResourceBundle resources) {

    /****************************************************************
     * Sub rotina: botao iniciar
     * Funcao: Vai para a tela da arvore
     * Parametros: Um evento (o botao ser pressionado)
     * Retorno: void
     ****************************************************************/
    Iniciar_Button_in.setOnAction(event -> { // Inicio do controle do botao Iniciar
      
      try {// Inicio do try
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/View/Simulation.fxml")); // Chamando o fxml
        AnchorPane_TelaIncial.getChildren().setAll(a);
      } catch (Exception e) { // Fim do try, inicio do catch
        System.out.println("Error: Tentativa de mudar a cena para a tela de simulacao \n" + e.getMessage());
      } // Fim do catch

    }); // Fim do controle do botao Iniciar

    /****************************************************************
     * Sub rotina: Ajuda_Button_in
     * Funcao: Mostra a tela com texto explicativo
     * Parametros: Um evento (usuario(a) pressionar o botao)
     * Retorno: void
     ****************************************************************/
    Ajuda_Button_in.setOnAction(event -> { // Inicio do controle do botao ajuda
      explicacao_Button.setVisible(true);
      Explicacao_TextArea.setVisible(true);
      botoes.setVisible(false);
    }); // Fim do controle do botao ajuda

    /****************************************************************
     * Sub rotina: explicacao_Button
     * Funcao: Oculta a tela com texto explicativo
     * Parametros: Um evento (usuario(a) pressionar o botao)
     * Retorno: void
     ****************************************************************/
    explicacao_Button.setOnAction(event -> { // Inicio do controle do botao/tela de explicacao
      explicacao_Button.setVisible(false);
      Explicacao_TextArea.setVisible(false);
      botoes.setVisible(true);
    }); // Fim do controle do botao/tela de explicacao
  } // Fim do metodo initialize
} // Fim da classe HomeController
