/* *************************************************************** 
* Autor............: Roger Daniel Santana Simoes
* Matricula........: 202010022
* Inicio...........: 05/05/2022
* Ultima alteracao.: 07/05/2022
* Nome.............: Leitores e escritores
* Funcao...........: Simula a solucao com semaforos para o problema classico dos leitores e escritores usando uma interface grafica
*************************************************************** */

package Controll;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Semaphore;

import Model.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/****************************************************************
 * Classe: SimulationController
 * Funcao: Controla o fxml e fornece as variaveis e semaforos compartilhados
 * Parametros: Objetos do fxml
 ****************************************************************/
public class SimulationController implements Initializable {

  @FXML
  private AnchorPane AnchorPane_screen;

  @FXML
  private ImageView a1_ImageView, a2_ImageView, a3_ImageView, a4_ImageView, a5_ImageView, a6_ImageView, a7_ImageView,
      a8_ImageView, b1_ImageView, b2_ImageView, b3_ImageView, b4_ImageView, b5_ImageView, b6_ImageView, b7_ImageView,
      b8_ImageView, c1_ImageView, c2_ImageView, c3_ImageView, c4_ImageView, c5_ImageView, c6_ImageView, c7_ImageView,
      c8_ImageView, d1_ImageView, d2_ImageView, d3_ImageView, d4_ImageView, d5_ImageView, d6_ImageView, d7_ImageView,
      d8_ImageView, start_imageView;

  @FXML
  private AnchorPane animation_AnchorPane;

  @FXML
  private Button start_Button, adjusts_Button, home_Button, adjustsScreen_button, generateClients_button;

  @FXML
  private TextField viewing_textField, buying_textField;

  @FXML
  private Slider setTimeSpentViewing_button, viewingTimeIsRamdomized_button, ClientsGeneratedAutomatically_button;

  @FXML
  private GridPane generateClients_gridpane, timeViewingUser_gridpane, timeViewingRamdom_gridpane;

  @FXML
  private Label timeViewing_label, totalAccess_label;

  public ArmChair[][] armChair = new ArmChair[4][8];
  public Semaphore readerMutex = new Semaphore(1);
  public Semaphore dataBaseMutex = new Semaphore(1);
  public Semaphore writerMutex = new Semaphore(1);
  private int readersCounter = 0, writerCounter = 0, accessCounter = 0, AvalibleArmchairsCounter = 32, row = 0,
      column = 0;
  private boolean autoManage = true, timeViewingIsRamdomized = true, isStarted;
  private double timeViewingByUser = 1;

  /*********************************************************************
   * Metodo: initializeArrays
   * Funcao: inicializa os arrays que contem as poltronas
   * Parametros: void
   * Retorno: void
   *********************************************************************/
  public void InitializableArrays() {
    armChair[0][0] = new ArmChair(a1_ImageView);
    armChair[0][1] = new ArmChair(a2_ImageView);
    armChair[0][2] = new ArmChair(a3_ImageView);
    armChair[0][3] = new ArmChair(a4_ImageView);
    armChair[0][4] = new ArmChair(a5_ImageView);
    armChair[0][5] = new ArmChair(a6_ImageView);
    armChair[0][6] = new ArmChair(a7_ImageView);
    armChair[0][7] = new ArmChair(a8_ImageView);
    armChair[1][0] = new ArmChair(b1_ImageView);
    armChair[1][1] = new ArmChair(b2_ImageView);
    armChair[1][2] = new ArmChair(b3_ImageView);
    armChair[1][3] = new ArmChair(b4_ImageView);
    armChair[1][4] = new ArmChair(b5_ImageView);
    armChair[1][5] = new ArmChair(b6_ImageView);
    armChair[1][6] = new ArmChair(b7_ImageView);
    armChair[1][7] = new ArmChair(b8_ImageView);
    armChair[2][0] = new ArmChair(c1_ImageView);
    armChair[2][1] = new ArmChair(c2_ImageView);
    armChair[2][2] = new ArmChair(c3_ImageView);
    armChair[2][3] = new ArmChair(c4_ImageView);
    armChair[2][4] = new ArmChair(c5_ImageView);
    armChair[2][5] = new ArmChair(c6_ImageView);
    armChair[2][6] = new ArmChair(c7_ImageView);
    armChair[2][7] = new ArmChair(c8_ImageView);
    armChair[3][0] = new ArmChair(d1_ImageView);
    armChair[3][1] = new ArmChair(d2_ImageView);
    armChair[3][2] = new ArmChair(d3_ImageView);
    armChair[3][3] = new ArmChair(d4_ImageView);
    armChair[3][4] = new ArmChair(d5_ImageView);
    armChair[3][5] = new ArmChair(d6_ImageView);
    armChair[3][6] = new ArmChair(d7_ImageView);
    armChair[3][7] = new ArmChair(d8_ImageView);
  } //InitializableArrays method ends here

  /****************************************************************
   * Metodo: getReaderCounter
   * Funcao: Retorna o contador de clientes visualizando
   * Parametros: null
   * Retorno: int
   ****************************************************************/
  public int getReaderCounter() {
    return this.readersCounter;
  }//getReaderCounter method ends here

  /****************************************************************
   * Metodo: increaseWriterCounter
   * Funcao: Aumenta o contador de clientes visualizando
   * Parametros: null
   * Retorno: void
   ****************************************************************/
  public void increaseReaderCounter() {
    readersCounter++;
    increaseAccessCounter();
  }//increaseReaderCounter method ends here


  /****************************************************************
   * Metodo: decreaseReaderCounter
   * Funcao: Diminui o contador de clientes visualizando
   * Parametros: null
   * Retorno: void
   ****************************************************************/
  public void decreaseReaderCounter() {
    readersCounter--;
  }//decreaseReaderCounter method ends here


  /****************************************************************
   * Metodo: getWriterCounter
   * Funcao: Retorna o contador de clientes tentando comprar
   * Parametros: null
   * Retorno: int
   ****************************************************************/
  public int getWriterCounter() {
    return this.writerCounter;
  }//getWriterCounter method ends here


  /****************************************************************
   * Metodo: increaseWriterCounter
   * Funcao: Aumenta o contador de clientes tentando comprar
   * Parametros: null
   * Retorno: void
   ****************************************************************/
  public void increaseWriterCounter() {
    writerCounter++;
  }//increaseWriterCounter method ends here


  /****************************************************************
   * Metodo: decreaseWriterCounter
   * Funcao: Diminui o contador de clientes tentando comprar
   * Parametros: null
   * Retorno: void
   ****************************************************************/
  public void decreaseWriterCounter() {
    writerCounter--;
  }//decreaseWriterCounter method ends here


  /****************************************************************
   * Metodo: getAvalibleArmchairCounter
   * Funcao: Retorna o contador de poltronas disponivels
   * Parametros: null
   * Retorno: int
   ****************************************************************/
  public int getAvalibleArmchairCounter() {
    return this.AvalibleArmchairsCounter;
  }//getAvalibleArmchairCounter method ends here


  /****************************************************************
   * Metodo: decreaseAvalibleArmchairCounter
   * Funcao: Diminui o contador de poltronas disponivels
   * Parametros: null
   * Retorno: void
   ****************************************************************/
  public void decreaseAvalibleArmchairCounter() {
    AvalibleArmchairsCounter--;
  }//decreaseAvalibleArmchairCounter method ends here

  /****************************************************************
   * Metodo: getAccessCounter
   * Funcao: Retorna o contador de acessos totais
   * Parametros: null
   * Retorno: int
   ****************************************************************/
  public int getAccessCounter() {
    return this.accessCounter;
  }//getAccessCounter method ends here


  /****************************************************************
   * Metodo: increaseAccessCounter
   * Funcao: Aumenta o contador de acessos totais
   * Parametros: null
   * Retorno: void
   ****************************************************************/
  synchronized public void increaseAccessCounter() {
    accessCounter++;
    Platform.runLater(() -> { // Prevents from getting java exceptios
      // updateInformations();
      totalAccess_label.setText("Acessos totais: " + accessCounter);
    });// runLater ends here
  }//increaseAccessCounter method ends here


  /****************************************************************
   * Metodo: decreaseAccessCounter
   * Funcao: Diminiu o contador de acessos totais
   * Parametros: null
   * Retorno: void
   ****************************************************************/
  synchronized public void decreaseAccessCounter() {
    accessCounter--;
  }//decreaseAccessCounter method ends here


  /****************************************************************
   * Metodo: createNewClient
   * Funcao: Instancia um novo Cliente
   * Parametros: null
   * Retorno: void
   ****************************************************************/
  protected void createNewClient() {
    Client client;
    if (timeViewingIsRamdomized) {
      double a = (int) (((Math.random() * 10) % 5) + 1);
      System.out.println("Time reading: " + a);
      client = new Client(this, (int) (a));
    } else
      client = new Client(this, (int) timeViewingByUser);
    client.start();
  }//createNewClient method ends here


  /****************************************************************
   * Metodo: getNextAvaliableChair
   * Funcao: Calcula a proxima cadeira disponivel e a reserva
   * Parametros: null
   * Retorno: void
   ****************************************************************/
  public boolean getNextAvaliableChair() {
    ArmChair armchair = armChair[row][column % 8];
    System.out.println("" + row + " " + column);

    if (AvalibleArmchairsCounter > 0 && armchair.avaliable) {
      armchair.setAvaliable(false);
      armchair.setColorRed();
      this.column++;
      if (this.column % 8 == 0 && this.column != 0)
        this.row++;

      this.decreaseAvalibleArmchairCounter();

      return true;
    } //if ends here
    return false;
  }//getNextAvaliableChair method ends here


  /****************************************************************
   * Metodo: restart
   * Funcao: Reiniciar toda a tela para as configuracoes iniciais
   * Parametros: null
   * Retorno: void
   ****************************************************************/
  private void restart() {
    try {// Inicio do try
      AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/View/Simulation.fxml")); // Chamando o fxml
      AnchorPane_screen.getChildren().setAll(a);
    } catch (Exception e) { // Fim do try, inicio do catch
      System.out.println("Error: Tentativa de mudar a cena \n" + e.getMessage());
    } // Fim do catch
  }//restart method ends here

  /****************************************************************
   * Metodo: updateBuyingTextfield
   * Funcao: Atualiza o referente textfield com a quantidade de clientes tentando comprar
   * Parametros: null
   * Retorno: void
   ****************************************************************/
  public void updateBuyingTextfield() {
    buying_textField.setText("" + writerCounter);
  }//updateBuyingTextfield method ends here

  /****************************************************************
   * Metodo: updateViewingTextfield
   * Funcao: Atualiza o referente textfield com a quantidade de clientes vizualizando
   * Parametros: null
   * Retorno: void
   ****************************************************************/
  public void updateViewingTextfield() {
    viewing_textField.setText("" + readersCounter);
  }//updateViewingTextfield method ends here

  /****************************************************************
   * Metodo: initizize
   * Funcao: Observa quais botoes do programa sao pressinados para executar uma sub rotina em cada caso
   * Parametros: Parametros padroes nao diretamente relacionados a este app
   * Retorno: void
   ****************************************************************/
  @Override
  public void initialize(URL location, ResourceBundle resources) { // Initialize method starts here
    InitializableArrays();

    /****************************************************************
     * Sub rotina: home_Button
     * Funcao: Retorna para a home
     * Parametros: Um evento (usuario(a) pressionar o botao)
     * Retorno: void
     ****************************************************************/
    home_Button.setOnAction(event -> { // Inicio do controle do botao home
      this.autoManage = false;

      try {// Inicio do try
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/View/Home.fxml")); // Chamando o fxml
        AnchorPane_screen.getChildren().setAll(a);
      } catch (Exception e) { // Fim do try, inicio do catch
        System.out.println("Error: Tentativa de mudar a cena \n" + e.getMessage());
      } // Fim do catch
    }); // home_Button control ends here

    /****************************************************************
     * Sub rotina: start_Button
     * Funcao: Instancia a classe gerente, que instancia threads automaticamente
     * Parametros: Um evento (usuario(a) pressionar o botao)
     * Retorno: void
     ****************************************************************/
    start_Button.setOnAction(event -> { // Inicio do controle do botao start
      if (!isStarted) {
        Manager manager = new Manager();
        if (autoManage)
          manager.start();
        isStarted = true;
        start_imageView.setImage(new Image("Images/close_icon.png"));
      } else { // close button starts here
        isStarted = false;
        restart();
      } // close button ends here
    }); // start_Button control ends here

    /****************************************************************
     * Sub rotina: adjusts_Button
     * Funcao: Mostra as opcoes de ajustes
     * Parametros: Um evento (usuario(a) pressionar o botao)
     * Retorno: void
     ****************************************************************/
    adjusts_Button.setOnAction(event -> { // Inicio do controle do botao start
      adjustsScreen_button.setVisible(true);
    }); // adjusts_Button control ends here

    /****************************************************************
     * Sub rotina: adjustsScreen_button
     * Funcao: Oculta as opcoes de ajustes
     * Parametros: Um evento (usuario(a) pressionar o botao)
     * Retorno: void
     ****************************************************************/
    adjustsScreen_button.setOnAction(event -> { // Inicio do controle do botao start
      adjustsScreen_button.setVisible(false);
    }); // adjustsScreen_button control ends here

    /****************************************************************
     * Sub rotina: generateClients_button
     * Funcao: Instacia novas Threads da classe cliente
     * Parametros: Um evento (usuario(a) pressionar o botao)
     * Retorno: void
     ****************************************************************/
    generateClients_button.setOnAction(event -> { // Inicio do controle do botao start
      createNewClient();
    }); // generateClients_button control ends here

    // controle dos sliders
    /****************************************************************
     * Sub rotina: ClientsGeneratedAutomatically_button
     * Funcao: Controla se o programa gera clientes automaticamente ou não
     * Parametros: Um evento (modificacao no slider)
     * Retorno: void
     ****************************************************************/
    ClientsGeneratedAutomatically_button.valueProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) { //
        if ((int) newValue.doubleValue() == 0) {
          isStarted = true;
          start_imageView.setImage(new Image("Images/close_icon.png"));
          autoManage = false;
          generateClients_button.setVisible(true);
          ClientsGeneratedAutomatically_button.setValue(0);
          generateClients_gridpane.setOpacity(0.5);
        } else {
          autoManage = true;
          Manager manager = new Manager();
          manager.start();
          generateClients_button.setVisible(false);
          ClientsGeneratedAutomatically_button.setValue(1);
          generateClients_gridpane.setOpacity(1);
        }
      } // Fim do metodo que verifica se o valor do slider mudou
    }); // Fim do listener do Slider do tempo de pensamento do phi 0

    /****************************************************************
     * Sub rotina: viewingTimeIsRamdomized_button
     * Funcao: Controla se o programa gera valores randomicos para o tempo de visualizacao dos clientes
     * Parametros: Um evento (modificacao no slider)
     * Retorno: void
     ****************************************************************/
    viewingTimeIsRamdomized_button.valueProperty().addListener(new ChangeListener<Number>() {
      public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) { //
        if ((int) newValue.doubleValue() == 0) {
          timeViewingIsRamdomized = false;
          viewingTimeIsRamdomized_button.setValue(0);
          timeViewingRamdom_gridpane.setOpacity(0.5);
          timeViewingUser_gridpane.setVisible(true);
        } else {
          timeViewingIsRamdomized = true;
          viewingTimeIsRamdomized_button.setValue(1);
          timeViewingRamdom_gridpane.setOpacity(1);
          timeViewingUser_gridpane.setVisible(false);
        }
      } // Fim do metodo que verifica se o valor do slider mudou
    }); // Fim do listener do Slider do tempo de pensamento do phi 0

    /****************************************************************
     * Sub rotina: setTimeSpentViewing_button
     * Funcao: O usuario determina quanto tempo os clientes passam visualizando
     * Parametros: Um evento (modificacao no slider)
     * Retorno: void
     ****************************************************************/
    setTimeSpentViewing_button.valueProperty().addListener(new ChangeListener<Number>() { // Slider that
      public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) { //
        if (!timeViewingIsRamdomized) {// verirify if it should or not change the value
          timeViewingByUser = newValue.doubleValue();
          timeViewing_label.setText((int) timeViewingByUser + "s");
        }
      } // Fim do metodo que verifica se o valor do slider mudou
    }); // Fim do listener do Slider do tempo de pensamento do phi 0
  } // initilize method ends here

  /****************************************************************
   * Classe: ArmChair
   * Funcao: Armazena a imagem e a informacao sobre disponibilidade
   * Parametros: Imagem da poltrona
   ****************************************************************/
  public class ArmChair {// ArmChair class starts here
    public ImageView armchair;
    boolean avaliable;

    public ArmChair(ImageView image) {
      this.armchair = image;
      avaliable = true;
    }

    /****************************************************************
     * Metodo: getAvaliable
     * Funcao: Informa se essa poltrona esta disponivel
     * Parametros: null
     * Retorno: boolean
     ****************************************************************/
    public boolean getAvaliable() {
      return this.avaliable;
    }

    /****************************************************************
     * Metodo: setAvaliable
     * Funcao: Muda a disponibilidade da poltrona
     * Parametros: null
     * Retorno: void
     ****************************************************************/
    public void setAvaliable(boolean avaliable) {
      this.avaliable = avaliable;
    }

    /****************************************************************
     * Metodo: setColorRed
     * Funcao: Muda a cor da poltrona
     * Parametros: null
     * Retorno: void
     ****************************************************************/
    public void setColorRed() {
      ColorAdjust colorChange = new ColorAdjust();
      colorChange.setSaturation(1);
      armchair.setEffect(colorChange);
    }
  } // ArmChair class ends here

  /****************************************************************
   * Classe: Manager
   * Funcao: Cria novos clientes a cada 2 segundos
   * Parametros: null
   ****************************************************************/
  private class Manager extends Thread { // Thread that create new clients and could do something else
    private int counter = 0;

    /*********************************************************************
     * Metodo: run
     * Funcao: Executa a rotina da thread
     * Parametros: void
     * Retorno: void
     *********************************************************************/
    public void run() {// run method starts here
      while (isStarted) { // while starts here
        try {
          sleep(50);
        } catch (InterruptedException e) {
          e.printStackTrace();
        } // sleep code ends here

        if (autoManage && (counter % 20 == 0)) // create a new client every 2 seconds
          createNewClient();
        this.counter++;
      } // while ends here
    } // run method ends here
  } // Manager class ends here

} //SimulationController class ends here
