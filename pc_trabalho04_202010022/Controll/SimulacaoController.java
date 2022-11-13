package Controll;

import Model.Caminhao;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Semaphore;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class SimulacaoController implements Initializable {

  @FXML
  private Slider velProdutor_Slider, velConsumidor_Slider;

  @FXML
  private Button pauseProdutor_Button, pauseConsumidor_Button;

  @FXML
  private Button Home_Button, Start_Button;

  @FXML
  private ImageView ProdutorPauseResume_ImageView, ConsumidorPauseResume_ImageView;

  @FXML
  private ImageView areia_ImageView, areia_ImageView1, areia_ImageView2, areia_ImageView3, areia_ImageView4,
      areia_ImageView5, areia_ImageView6, areia_ImageView7, areia_ImageView8, areia_ImageView9;

  @FXML
  private ImageView Produtor_ImageView, Consumidor_ImageView;

  @FXML
  private TextField pressaConsumidor_TextField, pressaProdutor_TextField;

  @FXML
  private Button maisProdutor_Button, menosProdutor_Button, maisConsumidor_Button, menosConsumidor_Button;

  @FXML
  private AnchorPane AnchorPane_Simulacao;

  // Variaveis
  private double velProdutor = 50;
  private double velConsumidor = 50;
  private boolean produtorEstaVivo;
  private boolean consumidorEstaVivo;

  // Semaforos
  public Semaphore cheio = new Semaphore(0); // Controlar a forma de producao
  public Semaphore vazio = new Semaphore(10); // Controlar a forma de consumacao
  public Semaphore mutex = new Semaphore(1); // Controlar o acesso a zona critica
  // instanciando produtor e consumidor fora do initialize para que seja possivel
  // pausar e despausar os processos finalizando-os e criando novos
  Caminhao produtor;
  Caminhao consumidor;

  public ImageView[] areia = new ImageView[10]; // Declara o array de areias para que este fique visivel para todos os
                                                // metodos

  /****************************************************************
   * Metodo: getImageViewProdutor
   * Funcao: retorna a imagem do produtor
   * Parametros: null
   * Retorno: a imagem do produtor
   ****************************************************************/
  public ImageView getImageViewProdutor() {// Inicio do metodo getImageViewProdutor
    return Produtor_ImageView;
  }// Fim do metodo getImageViewProdutor

  /****************************************************************
   * Metodo: getImageViewConsumidor
   * Funcao: retorna a imagem do consumidor
   * Parametros: null
   * Retorno: a imagem do consumidor
   ****************************************************************/
  public ImageView getImageViewConsumidor() {// Inicio do metodo getImageViewConsumidor
    return Consumidor_ImageView;
  }// Fim do metodo getImageViewConsumidor

  /****************************************************************
   * Metodo: getAreiaDisponivelPosX
   * Funcao: pesquisa e retorna a ultima areia derramada
   * Parametros: null
   * Retorno: posicao da ultima areia derramada
   ****************************************************************/
  public int getAreiaDisponivelPosX() {// Inicio do metodo getAreiaDisponivelPosX
    for (int i = areia.length - 1; i >= 0; i--)
      if (areia[i].isVisible())// Verifica se a areia esta derramada
        return (int) areia[i].getLayoutX(); // retorna a posicao da ultima areia derramada
    return -666; // retorna um erro caso nao tenha nenhuma disponivel
  }// Fim do metodo getAreiaDisponivelPosX

  /****************************************************************
   * Metodo: getAreiaIndisponivelPosX
   * Funcao: pesquisa e retorna a primeira posicao vazia para derramar areia
   * Parametros: null
   * Retorno: posicao da primeira posicao vazia para derramar areia
   ****************************************************************/
  public int getAreiaIndisponivelPosX() {// Inicio do metodo getAreiaIndisponivelPosX
    for (int i = 0; i < areia.length; i++)
      if (!areia[i].isVisible()) // Verifica se existe algum espaco vazio
        return (int) areia[i].getLayoutX(); // retorna o primeiro espaco vazio
    return -666; // retorna um erro caso nao tenha nenhuma disponivel
  }// Fim do metodo getAreiaIndisponivelPosX

  /****************************************************************
   * Metodo: getVelConsumidor
   * Funcao: fornece o controlador da velocidade do consumidor
   * Parametros: null
   * Retorno: o valor do controlador da velocidade do consumidor
   ****************************************************************/
  public long getVelConsumidor() {// Inicio do metodo getVelConsumidor
    return (long) this.velConsumidor;
  }// Fim do metodo getVelConsumidor

  /****************************************************************
   * Metodo: getVelProdutor
   * Funcao: fornece o controlador da velocidade do produtor
   * Parametros: null
   * Retorno: o valor do controlador da velocidade do produtor
   ****************************************************************/
  public long getVelProdutor() {// Inicio do metodo getVelProdutor
    return (long) this.velProdutor;
  }// Fim do metodo getVelProdutor

  /****************************************************************
   * Metodo: getProdutorPos
   * Funcao: fornece a posicao do produtor
   * Parametros: null
   * Retorno: a posicao do produtor
   ****************************************************************/
  public int getProdutorPos() {// Inicio do metodo getProdutorPos
    return (int) Produtor_ImageView.getX();
  }// Fim do metodo getProdutorPos

  /****************************************************************
   * Metodo: getConsumidorPos
   * Funcao: fornece a consumidor do produtor
   * Parametros: null
   * Retorno: a posicao do consumidor
   ****************************************************************/
  public int getConsumidorPos() {// Inicio do metodo getConsumidorPos
    return (int) Consumidor_ImageView.getLayoutX();
  }// Fim do metodo getConsumidorPos

  /****************************************************************
   * Metodo: getProdutorEstaVivo
   * Funcao: inform ao produtor se ele ainda esta vivo
   * Parametros: null
   * Retorno: boolean que informa se o produtor ainda esta vivo
   ****************************************************************/
  public boolean getProdutorEstaVivo() {// Inicio do metodo getProdutorEstaVivo
    return this.produtorEstaVivo;
  }// Fim do metodo getProdutorEstaVivo

  /****************************************************************
   * Metodo: getConsumidorEstaVivo
   * Funcao: inform ao consumidor se ele ainda esta vivo
   * Parametros: null
   * Retorno: boolean que informa se o consumidor ainda esta vivo
   ****************************************************************/
  public boolean getConsumidorEstaVivo() {// Inicio do metodo getConsumidorEstaVivo
    return this.consumidorEstaVivo;
  }// Fim do metodo getConsumidorEstaVivo

  /****************************************************************
   * Metodo: setXConsumidor
   * Funcao: Modifica a coordenada x da imagem que representa o consumidor
   * Parametros: Coordenada x passada pela thread
   * Retorno: void
   ****************************************************************/
  public void setXConsumidor(int x) {// Fim do metodo setXConsumidor
    Consumidor_ImageView.setX(x);
  }// Fim do metodo setXConsumidor

  /****************************************************************
   * Metodo: setCheioConsumidor
   * Funcao: Modifica a imagem que representa o consumidor
   * Parametros: Coordenada x passada pela thread
   * Retorno: void
   ****************************************************************/
  public void setCheioConsumidor() {// Fim do metodo setCheioConsumidor
    Consumidor_ImageView.setImage(new Image("Images/Consumidor_cheio.png"));
  }// Fim do metodo setCheioConsumidor

  /****************************************************************
   * Metodo: setVazioConsumidor
   * Funcao: Modifica a imagem que representa o consumidor
   * Parametros: Coordenada x passada pela thread
   * Retorno: void
   ****************************************************************/
  public void setVazioConsumidor() {// Inicio do metodo setVazioConsumidor
    Consumidor_ImageView.setImage(new Image("Images/Consumidor_vazio.png"));
  }// Fim do metodo setVazioConsumidor

  /****************************************************************
   * Metodo: setXProdutor
   * Funcao: Modifica a coordenada x da imagem que representa o produtor
   * Parametros: Coordenada x passada pela thread
   * Retorno: void
   ****************************************************************/
  public void setXProdutor(int x) {// Fim do metodo setXProdutor
    Produtor_ImageView.setX(x);
  }// Fim do metodo setXProdutor

  /****************************************************************
   * Metodo: setCheioProdutor
   * Funcao: Modifica a imagem que representa o Produtor
   * Parametros: Coordenada x passada pela thread
   * Retorno: void
   ****************************************************************/
  public void setCheioProdutor() {// Fim do metodo setCheioProdutor
    Produtor_ImageView.setImage(new Image("Images/Produtor_cheio.png"));
  }// Fim do metodo setCheioProdutor

  /****************************************************************
   * Metodo: setVazioProdutor
   * Funcao: Modifica a imagem que representa o Produtor
   * Parametros: Coordenada x passada pela thread
   * Retorno: void
   ****************************************************************/
  public void setVazioProdutor() {// Inicio do metodo setVazioProdutor
    Produtor_ImageView.setImage(new Image("Images/Produtor_vazio.png"));
  }// Fim do metodo setVazioProdutor

  /****************************************************************
   * Metodo: setVisibleAreia
   * Funcao: Modifica a visibilidade das areias
   * Parametros: Coordenada da imagem e um boolean para visibilidade
   * Retorno: void
   ****************************************************************/
  public void setVisibleAreia(int posicao, boolean visible) {// Inicio do metodo setVisibleAreia
    int p = (posicao - 125) / 50; // Determina a posicao da areia
    areia[p].setVisible(visible); // derrama ou carrega a areia
  }// Fim do metodo setVisibleAreia

  /****************************************************************
   * Metodo: startArray
   * Funcao: incializa o array com as imagens
   * Parametros: null
   * Retorno: void
   ****************************************************************/
  public void startArray() { // Inicio do metodo startArray
    areia[0] = areia_ImageView;
    areia[1] = areia_ImageView1;
    areia[2] = areia_ImageView2;
    areia[3] = areia_ImageView3;
    areia[4] = areia_ImageView4;
    areia[5] = areia_ImageView5;
    areia[6] = areia_ImageView6;
    areia[7] = areia_ImageView7;
    areia[8] = areia_ImageView8;
    areia[9] = areia_ImageView9;
  } // Fim do metodo startArray

  /****************************************************************
   * Metodo: initizize
   * Funcao: Observa quais botoes do programa sao pressinados para executar uma sub rotina em cada caso
   * Parametros: Parametros padroes nao diretamente relacionados a este app
   * Retorno: void
   ****************************************************************/
  @Override
  public void initialize(URL location, ResourceBundle resources) { // Inicio do metodo initialize
    startArray(); // Inicia o array

    // Controle dos botoes
    Home_Button.setOnAction(event -> { // Inicio do controle do botao Home
      produtorEstaVivo = false;
      consumidorEstaVivo = false;
      try {// Inicio do try
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/View/Home.fxml")); // Chamando o fxml
        AnchorPane_Simulacao.getChildren().setAll(a);
      } catch (Exception e) { // Fim do try, inicio do catch
        System.out.println("Error: Tentativa de mudar a cena \n" + e.getMessage());
      } // Fim do catch
    }); // Fim do controle do botao Home

    Start_Button.setOnAction(event -> { // metodo que inicia a simulacao
      if (!produtorEstaVivo && !consumidorEstaVivo) {
        // Intancia as threads
        produtor = new Caminhao(true, this);
        consumidor = new Caminhao(false, this);
		//setta as variaveis de controle
        produtorEstaVivo = true;
        consumidorEstaVivo = true;
		//inicia os processos
        produtor.start();
        consumidor.start();
		//Setta prioridade com o valor inicial do textField
        produtor.setPriority(Integer.parseInt(pressaProdutor_TextField.getText()));
        consumidor.setPriority(Integer.parseInt(pressaConsumidor_TextField.getText()));
		//Para case o usuario utilize este botao para despausar as duas threads
        ProdutorPauseResume_ImageView.setImage(new Image("Images/pause.png"));
        ConsumidorPauseResume_ImageView.setImage(new Image("Images/pause.png"));
      }
    }); // Fim do controle do Play Button

    pauseProdutor_Button.setOnAction(event -> { // Inicio do controle do Pause do produtor
      if (produtorEstaVivo) {//Se o processo esta vivo, mata
        produtorEstaVivo = false;
        ProdutorPauseResume_ImageView.setImage(new Image("Images/resume.png"));
      } else { //Se o processo nao esta vivo, cria
        produtor = new Caminhao(true, this);
        produtorEstaVivo = true;
        produtor.start();
        ProdutorPauseResume_ImageView.setImage(new Image("Images/pause.png"));
      }
    }); // Fim do controle do botao Pause do produtor

    pauseConsumidor_Button.setOnAction(event -> { // Inicio do controle do Pause do consumidor
      if (consumidorEstaVivo) {//Se o processo esta vivo, mata
        consumidorEstaVivo = false;
        ConsumidorPauseResume_ImageView.setImage(new Image("Images/resume.png"));
      } else {//Se o processo nao esta vivo, cria
        consumidor = new Caminhao(false, this);
        consumidorEstaVivo = true;
        consumidor.start();
        ConsumidorPauseResume_ImageView.setImage(new Image("Images/pause.png"));
      }
    });// Fim do controle do Pause do consumidor

    menosProdutor_Button.setOnAction(event -> { // inicio do controle do menosprodutor_Button
      int p = Integer.parseInt(pressaProdutor_TextField.getText()) - 1;
      if (p > 0) {// impede que o limite do setPriority seja ultrapassado
        pressaProdutor_TextField.setText("" + p);
        produtor.setPriority(p);
      } // Fim if
    }); // Fim do controle do menosprodutor_Button

    maisProdutor_Button.setOnAction(event -> { // metodo que inicia a simulacao
      int p = Integer.parseInt(pressaProdutor_TextField.getText()) + 1;
      if (p <= 10) {// impede que o limite do setPriority seja ultrapassado
        pressaProdutor_TextField.setText("" + p);
        produtor.setPriority(p);
      } // Fim if
    }); // Fim do controle do Play Button

    menosConsumidor_Button.setOnAction(event -> { // metodo que inicia a simulacao
      int p = Integer.parseInt(pressaConsumidor_TextField.getText()) - 1;
      if (p > 0) {// impede que o limite do setPriority seja ultrapassado
        pressaConsumidor_TextField.setText("" + p);
        consumidor.setPriority(p);
      } // Fim if
    }); // Fim do controle do Play Button

    maisConsumidor_Button.setOnAction(event -> { // metodo que inicia a simulacao
      int p = Integer.parseInt(pressaConsumidor_TextField.getText()) + 1;
      if (p <= 10) { // impede que o limite do setPriority seja ultrapassado
        pressaConsumidor_TextField.setText("" + p);
        consumidor.setPriority(p);
      } // Fim if
    }); // Fim do controle do Play Button

    velConsumidor_Slider.valueProperty().addListener(new ChangeListener<Number>() { // Inicio do listener do Slider do consumidor
      @Override
      public void changed(ObservableValue<? extends Number> ov, Number valorAntigo, Number valorNovo) { //
        velConsumidor = valorNovo.doubleValue() + 1; // Atualiza o valor da variavel de controle
      } // Fim do metodo que verifica se o valor do slider mudou
    }); // Fim do listener do Slider do consumidor

    velProdutor_Slider.valueProperty().addListener(new ChangeListener<Number>() { // Inicio do listener do Slider do produtor
      @Override
      public void changed(ObservableValue<? extends Number> ov, Number valorAntigo, Number valorNovo) { //
        velProdutor = valorNovo.doubleValue() + 1; // Atualiza o valor da variavel de controle
      } // Fim do metodo que verifica se o valor do slider mudou
    }); // Fim do listener do Slider do consumidor
  } // Fim do metodo initialize
}// Fim da classe SimulacaoController