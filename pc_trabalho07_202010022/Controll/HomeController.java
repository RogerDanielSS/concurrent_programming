/* *************************************************************** 
* Autor............: Roger Daniel Santana Simoes
* Matricula........: 202010022
* Inicio...........: 15/05/2022
* Ultima alteracao.: 22/05/2022
* Nome.............: Transito automato
* Funcao...........: Simula um transito automato usando a classe Semaphore e uma interface grafica
*************************************************************** */


package Controll;

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
  private Button Iniciar_Button_in;

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
  } // Fim do metodo initialize
} // Fim da classe HomeController
