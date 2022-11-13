package Controll;

//Javafx
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

//Documentacao
import Controll.HistoricoControle;

public class HistoricoController implements Initializable {// Inicio da classe HistoricoController
    @FXML
    private Button Voltar_Button_ChangeScene;

    @FXML
    private AnchorPane AnchorPane_Principal;

    @FXML
    private TextArea Historico_TextArea_out;

    @FXML
    private Button LimparHistorico_Button_in;
  /*
   * ***************************************************************
   * Metodo: Initialize
   * Funcao: Monitora o programa para detectar eventos que venham a acontecer,
   * neste caso, botoes sendo pressionados
   * Parametros: vazio
   * Retorno: vazio
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) { // Inicio do metodo Initialize
  
    Historico_TextArea_out.setText(setHistorico());

    Voltar_Button_ChangeScene.setOnAction(event -> { // Inicio do controle do botao voltar
      try { // inicio do try
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/View/Home.fxml"));
        AnchorPane_Principal.getChildren().setAll(a);
      } catch (Exception e) { // fim do try / Inicio do catch
        System.out.println("erro ao tentar retornar para a Home a partir da tela de historico");
      } // Fim do catch
    }); // Fim do controle do botao voltar

    LimparHistorico_Button_in.setOnAction(event -> { // Inicio do controle do botao de limpar historico
      try { // inicio do try
        HistoricoControle historico = new HistoricoControle();
        historico.limpar();
		
		 Historico_TextArea_out.setText("Historico vazio");
      } catch (Exception e) { // fim do try / Inicio do catch
        System.out.println("erro ao tentar limpar o historico");
      } // Fim do catch
    });// Fim do controle do botao de limpar historico

  }// Fim do metodo Initialize

  /*
   * ***************************************************************
   * Metodo: setHistorico
   * Funcao: Iguala o TextArea ao metodo ler() da classe HistoricoDados
   * Parametros: vazio
   * Retorno: vazio
   */
  public String setHistorico() {// inicio do metodo setHistorico
    try { // inicio try
	  HistoricoControle historico = new HistoricoControle();
      //System.out.println(historico.ler()); //#Para fins de teste
	  
	  if(historico.ler() == "")
		return "Historico vazio";
	  else
        return (historico.ler());
    } catch (Exception e) {// Fim try / inicio catch
      System.out.println("Erro ao tentar imprimir o historico na textArea: " + e);
    } // Fim catch
	
	return ("Erro ao tentar imprimir o historico");
  }// Fim do metodo setHistorico
}// Fim da classe HistoricoController