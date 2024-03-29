package Controll;
/* ***************************************************************
* Autor............: Roger Daniel Santana Simões
* Matricula........: 202010022
* Inicio...........: 09/01/2022
* Ultima alteracao.: 10/01/2022
* Nome.............: HomeControler
* Funcao...........: Controla o FXML da Home do programa
*************************************************************** */

//javafx
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.AnchorPane;
//documentacao
import Controll.HistoricoControle;
import Controll.HistoricoController;

//bibliotecas
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.io.File;
import java.net.URL;
import javax.sound.sampled.*;
import java.text.DecimalFormat;

public class HomeControle implements Initializable {
  @FXML
  private Button Time1Mais_Button_In;

  @FXML
  private TextField PlacarTime1TextField_Out;

  @FXML
  private Button FinalizarPartida_Button_In;

  @FXML
  private TextField PlacarTime2TextField_Out;

  @FXML
  private TextField NomeTime1TextField_In;

  @FXML
  private Button Time1Menos_Button_In;

  @FXML
  private Button Time2Mais_Button_In;

  @FXML
  private Button Time2Menos_Button_In;

  @FXML
  private Button APito_Button_In;

  @FXML
  private TextField NomeTime2TextField_In;

  @FXML
  private Button HistoricoButton_ChanceScene;

  @FXML
  private AnchorPane anchorPanePrincipal;

  DecimalFormat noDecimal = new DecimalFormat("#");

  /*
   * ***************************************************************
   * Metodo: Initialize
   * Funcao: Monitora o programa para detectar eventos que venham a acontecer,
   * neste caso, botoes sendo pressionados
   * Parametros: informacoes de locacao, questoes particulares da classe
   * initializable
   * Retorno: vazio
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    /*
     * ***************************************************************
     * Metodo: Time1Mais_Button_In.setOnAction
     * Funcao: Aumenta o placar do time 1
     * Parametros: Um evento do tipo event + codigo da acao
     * Retorno: Executa a acao dada no parametro
     * Nota: Esta sendo chamado um metodo da classe Button que recebe o codigo a ser
     * executado caso o botao seja pressionado. Optei por esse metodo porque ele
     * permite especificar o botao, uma vez que existem varios botoes nesta tela.
     */
    Time1Mais_Button_In.setOnAction(event -> { // Inicio do controle do botao time 1 mais
      double placar1 = Double.parseDouble(PlacarTime1TextField_Out.getText());
      placar1 += 1;

      PlacarTime1TextField_Out.setText("" + noDecimal.format(placar1));
    }); // Fim do controle do botao time 1 mais

    /*
     * ***************************************************************
     * Metodo: Time2Mais_Button_In.setOnAction
     * Funcao: Aumenta o placar do time 2
     * Parametros: Um evento do tipo event + codigo da acao
     * Retorno: Executa a acao dada no parametro
     * Nota: Esta sendo chamado um metodo da classe Button que recebe o codigo a ser
     * executado caso o botao seja pressionado. Optei por esse metodo porque ele
     * permite especificar o botao, uma vez que existem varios botoes nesta tela.
     */

    Time2Mais_Button_In.setOnAction(event -> { // Inicio do controle do botao time 2 mais
      double placar2 = Double.parseDouble(PlacarTime2TextField_Out.getText());

      placar2 += 1;

      PlacarTime2TextField_Out.setText("" + noDecimal.format(placar2));
    }); // Fim do controle do botao time 2 mais

    /*
     * ***************************************************************
     * Metodo: Time1Menos_Button_In.setOnAction
     * Funcao: Diminui o placar do time 1
     * Parametros: Um evento do tipo event + codigo da acao
     * Retorno: Executa a acao dada no parametro
     * Nota: Esta sendo chamado um metodo da classe Button que recebe o codigo a ser
     * executado caso o botao seja pressionado. Optei por esse metodo porque ele
     * permite especificar o botao, uma vez que existem varios botoes nesta tela.
     */
    Time1Menos_Button_In.setOnAction(event -> { // Inicio do controle do botao time 1 menos
      double placar1 = Double.parseDouble(PlacarTime1TextField_Out.getText());

      if (placar1 > 0) {
        placar1 -= 1;

        PlacarTime1TextField_Out.setText("" + noDecimal.format(placar1));
      }
    });// Fim do controle do botao time 1 menos

    /*
     * ***************************************************************
     * Metodo: Time2Menos_Button_In.setOnAction
     * Funcao: Diminui o placar do time 2
     * Parametros: Um evento do tipo event + codigo da acao
     * Retorno: Executa a acao dada no parametro
     * Nota: Esta sendo chamado um metodo da classe Button que recebe o codigo a ser
     * executado caso o botao seja pressionado. Optei por esse metodo porque ele
     * permite especificar o botao, uma vez que existem varios botoes nesta tela.
     */
    Time2Menos_Button_In.setOnAction(event -> { // Inicio do controle do botao time 2 menos
      double placar2 = Double.parseDouble(PlacarTime2TextField_Out.getText());

      if (placar2 > 0) {
        placar2 -= 1;

        PlacarTime2TextField_Out.setText("" + noDecimal.format(placar2));
      }
    }); // Fim do controle do botao time 2 menos

    /*
     * ***************************************************************
     * Metodo: FinalizarPartida_Button_in_Button_In.setOnAction
     * Funcao: Diminui o placar do time 2
     * Parametros: Um evento do tipo event + codigo da acao
     * Retorno: Executa a acao dada no parametro
     * Nota: Esta sendo chamado um metodo da classe Button que recebe o codigo a ser
     * executado caso o botao seja pressionado. Optei por esse metodo porque ele
     * permite especificar o botao, uma vez que existem varios botoes nesta tela.
     */
    FinalizarPartida_Button_In.setOnAction(event -> { // Inicio do controle do botao Finalizar Partida
      try {
        DateTimeFormatter formatacaoData = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String data = formatacaoData.format(LocalDateTime.now());

        HistoricoControle historico = new HistoricoControle();
        historico.guardar(NomeTime1TextField_In.getText(),
            NomeTime2TextField_In.getText(), PlacarTime1TextField_Out.getText(),
            PlacarTime2TextField_Out.getText(), data);

        HistoricoController fxml = new HistoricoController();
        fxml.setHistorico();
        PlacarTime1TextField_Out.setText("0");
        PlacarTime2TextField_Out.setText("0");

      } catch (Exception e) {
        System.out.println("Error");
      }
    });// Fim do controle do botao Finalizar Partida

    /*
     * ***************************************************************
     * Metodo: Apito_Button_in_Button_In.setOnAction
     * Funcao: Toca o som de um apito
     * Parametros: Um evento do tipo event + codigo da acao
     * Retorno: Executa a acao dada no parametro
     * Nota: Esta sendo chamado um metodo da classe Button que recebe o codigo a ser
     * executado caso o botao seja pressionado. Optei por esse metodo porque ele
     * permite especificar o botao, uma vez que existem varios botoes nesta tela.
     */
    APito_Button_In.setOnAction(event -> { // Inicio do controle do botao do apito
      File apito = new File("Audio/Whistle.wav");// Pode dar erro, porque a classe HomeControler nao esta diretamente na
      // mesma pasta que a pasta Audio

      try {
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(apito);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);

        clip.start();

      } catch (Exception e) {
        System.out.println("Error" + e.getMessage());
      }
    });// Fim do controle do botao do apito

    HistoricoButton_ChanceScene.setOnAction(event -> { // Inicio do controle do botao do historico
      try {// Inicio do try
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/View/Historico.fxml")); // Chamando o fxml
        anchorPanePrincipal.getChildren().setAll(a);
      } catch (Exception e) { // Fim do try, inicio do catch
        System.out.println("Error: Tentativa de mudar a cena para o historico \n" + e.getMessage());
      } // Fim do catch

    }); // Fim do controle do botao do historico

  }// Fim do metodo Initialize
}