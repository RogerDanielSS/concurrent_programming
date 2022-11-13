package Controll;

/* ***************************************************************
* Autor............: Roger Daniel Santana Simões
* Matricula........: 202010022
* Inicio...........: 10/03/2022
* Ultima alteracao.: 12/03/2022
* Nome.............: HomeControler
* Funcao...........: Controla o FXML da Home do programa
*************************************************************** */

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class HomeController implements Initializable {

  @FXML
  private Button Iniciar_Button_in;

  @FXML
  private AnchorPane AnchorPane_TelaIncial;

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    Iniciar_Button_in.setOnAction(event -> { // Inicio do controle do botao Iniciar
      try {// Inicio do try
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/View/Arvore.fxml")); // Chamando o fxml
        AnchorPane_TelaIncial.getChildren().setAll(a);
      } catch (Exception e) { // Fim do try, inicio do catch
        System.out.println("Error: Tentativa de mudar a cena para o historico \n" + e.getMessage());
      } // Fim do catch

    }); // Fim do controle do botao Iniciar
  }
}
