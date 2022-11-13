/* ***************************************************************
* Autor............: Roger Daniel Santana Simões
* Matricula........: 202010022
* Inicio...........: 23/03/2022
* Ultima alteracao.: 26/03/2022
* Nome.............: Simulacao de tunel de trem
* Funcao...........: Faz a simulacao de dois trens que tentam acessar os mesmos trilhos utilizando threads
****************************************************************/

package Controll;

import java.io.File;

import java.net.URL;
import java.util.ResourceBundle;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/*********************************************************************
 * Metodo: HomeController
 * Funcao: Controla o fxml da home
 * Parametros: objetos do fxml e variaveis de controle
 * Retorno: void
 *********************************************************************/
public class HomeController implements Initializable {

  @FXML
  private Button Iniciar_Button_in;

  @FXML
  private AnchorPane AnchorPane_TelaIncial;

  // Objetos
  File screen = new File("Audio/Screen.wav");// Carrega o arquivo de audio de transicao de tela

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
      try {
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(screen);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);

        clip.start();

      } catch (Exception e) {
        System.out.println("Error" + e.getMessage());
      }
      try {// Inicio do try
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/View/Simulacao.fxml")); // Chamando o fxml
        AnchorPane_TelaIncial.getChildren().setAll(a);
      } catch (Exception e) { // Fim do try, inicio do catch
        System.out.println("Error: Tentativa de mudar a cena para a tela de simulacao \n" + e.getMessage());
      } // Fim do catch

    }); // Fim do controle do botao Iniciar
  } // Fim do metodo initialize
} // Fim da classe HomeController 
