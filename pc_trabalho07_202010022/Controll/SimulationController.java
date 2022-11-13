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
import java.util.concurrent.Semaphore;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/*********************************************************************
 * Classe: SimulationController
 * Funcao: Controla o fxml da simulacao
 * Parametros: objetos do fxml e variaveis de controle
 *********************************************************************/
public class SimulationController implements Initializable {

  @FXML
  private Button home_Button, adjusts_Button, start_Button;

  @FXML
  private Slider slider_car08, slider_car05, slider_car15, slider_car10, slider_car20, slider_car01, slider_car11;

  @FXML
  private VBox car20, car10, car11, car01, car15, car05, car08;

  @FXML
  private ImageView start_imageView;

  @FXML
  private AnchorPane AnchorPane_screen;

  boolean isStarted;

  // Semaphores
  // created one by one cuz I was still solving the the problem and thiking
  // in the positions in an array could create more problems than solve now
  Semaphore mutex01 = new Semaphore(1);
  Semaphore left_01_10_05 = new Semaphore(1);
  Semaphore right_01_10_05 = new Semaphore(1);
  Semaphore left_01_05 = new Semaphore(1);
  Semaphore right_01_05 = new Semaphore(0);// car05 already starts in a critical region
  Semaphore down_05_10 = new Semaphore(1);
  Semaphore mid_10_08 = new Semaphore(0); // car 10 already starts in this critical section
  Semaphore midLeft_01_10_08 = new Semaphore(1);// car 10 already starts in this critical section
  Semaphore midRight_01_10_08 = new Semaphore(0);
  Semaphore left_05_08 = new Semaphore(1);
  Semaphore right_05_08 = new Semaphore(1);
  Semaphore up_01_08 = new Semaphore(1);
  Semaphore right_01_08 = new Semaphore(1);
  Semaphore left_01_08 = new Semaphore(1);
  Semaphore down_05_10_08 = new Semaphore(1);
  Semaphore rightBottom_15_01 = new Semaphore(1);
  Semaphore bottom_15_01 = new Semaphore(1);
  Semaphore bottom_15_10 = new Semaphore(1);
  Semaphore bottomAndLeft_15_08 = new Semaphore(1);
  Semaphore leftUp_15_05 = new Semaphore(1);
  Semaphore leftUp_15_01 = new Semaphore(1);
  Semaphore rightUp_15_01 = new Semaphore(1);
  Semaphore dot_15_08 = new Semaphore(1);
  Semaphore dotTop_11_08 = new Semaphore(1);
  Semaphore dotRightBottom_11_10or08 = new Semaphore(1);
  Semaphore left_11_01 = new Semaphore(1);
  Semaphore left_11_15 = new Semaphore(1);
  Semaphore left_11_08 = new Semaphore(1);
  Semaphore leftAndBottom_11_10 = new Semaphore(1);
  Semaphore topLeftBottom_11_05 = new Semaphore(1);
  Semaphore rightMid_11_01 = new Semaphore(1);
  Semaphore topRight_20_08 = new Semaphore(1);
  Semaphore topLeft_20_08 = new Semaphore(1);
  Semaphore left_20_08 = new Semaphore(1);
  Semaphore right_20_08 = new Semaphore(1);
  Semaphore top_20_15 = new Semaphore(1);
  Semaphore left_20_15 = new Semaphore(1);
  Semaphore topRight_20_01 = new Semaphore(1);
  Semaphore topLeft_20_01 = new Semaphore(1);
  Semaphore left_20_01 = new Semaphore(1);
  Semaphore bottomLeft_20_01 = new Semaphore(1);
  Semaphore bottomRight_20_01 = new Semaphore(1);
  Semaphore right_20_01 = new Semaphore(1);
  Semaphore top_20_05 = new Semaphore(1);
  Semaphore left_20_05 = new Semaphore(1);
  Semaphore bottom_20_05 = new Semaphore(1);
  Semaphore right_20_05 = new Semaphore(1);
  Semaphore top_20_11 = new Semaphore(1);
  Semaphore mid_20_11 = new Semaphore(1);
  Semaphore bottom_20_11 = new Semaphore(1);
  Semaphore right_20_10 = new Semaphore(1);
  Semaphore left_20_10 = new Semaphore(1);
  Semaphore bottom_20_10 = new Semaphore(1);

  // Cars
  private Car car_01;
  private Car car_05;
  private Car car_10;
  private Car car_08;
  private Car car_11;
  private Car car_15;
  private Car car_20;

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
  }// restart method ends here

  /****************************************************************
   * Metodo: initialize
   * Funcao: Observa quais botoes do programa sao pressinados para executar uma
   * sub rotina em cada caso
   * Parametros: Parametros padroes nao diretamente relacionados a este app
   * Retorno: void
   ****************************************************************/
  @Override
  public void initialize(URL location, ResourceBundle resources) {

    /****************************************************************
     * Sub rotina: start_Button
     * Funcao: Instancia a classe gerente, que instancia threads automaticamente
     * Parametros: Um evento (usuario(a) pressionar o botao)
     * Retorno: void
     ****************************************************************/
    start_Button.setOnAction(event -> {
      if (!isStarted) {
        isStarted = true;
        car_01 = new Car(car01, 1);
        car_05 = new Car(car05, 5);
        car_10 = new Car(car10, 10);
        car_08 = new Car(car08, 8);
        car_11 = new Car(car11, 11);
        car_15 = new Car(car15, 15);
        car_20 = new Car(car20, 20);

        // Manager manager = new Manager();

        car_01.start();
        car_05.start();
        car_10.start();
        car_08.start();
        car_11.start();
        car_15.start();
        car_20.start();
        // manager.start();
        start_imageView.setImage(new Image("Images/close_icon.png"));
      } else { // close button starts here
        isStarted = false;
        restart();
      } // close button ends here
    }); // start button control ends here

    /****************************************************************
     * Sub rotina: home_Button
     * Funcao: Retorna para a home
     * Parametros: Um evento (usuario(a) pressionar o botao)
     * Retorno: void
     ****************************************************************/
    home_Button.setOnAction(event -> { // Inicio do controle do botao home
      try {// Inicio do try
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/View/Home.fxml")); // Chamando o fxml
        AnchorPane_screen.getChildren().setAll(a);
      } catch (Exception e) { // Fim do try, inicio do catch
        System.out.println("Error: Tentativa de mudar a cena \n" + e.getMessage());
      } // Fim do catch
    }); // home_Button control ends here

    // Sliders
    /****************************************************************
     * Sub rotina: slider_car01
     * Funcao: Controla o slider do carro 1
     * Parametros: Um evento (usuario(a) movimentar o slider)
     * Retorno: void
     ****************************************************************/
    slider_car01.valueProperty().addListener(new ChangeListener<Number>() { // Velocity car
      public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) { //
        car_01.setVel(newValue.doubleValue());
      } // method changed ends here
    }); // Listener for slider_car01 ends here

    /****************************************************************
     * Sub rotina: slider_car05
     * Funcao: Controla o slider do carro 5
     * Parametros: Um evento (usuario(a) movimentar o slider)
     * Retorno: void
     ****************************************************************/
    slider_car05.valueProperty().addListener(new ChangeListener<Number>() { // Velocity car
      public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) { //
        car_05.setVel(newValue.doubleValue());
      } // method changed ends here
    }); // Listener for slider_car05 ends here

    /****************************************************************
     * Sub rotina: slider_car10
     * Funcao: Controla o slider do carro 10
     * Parametros: Um evento (usuario(a) movimentar o slider)
     * Retorno: void
     ****************************************************************/
    slider_car10.valueProperty().addListener(new ChangeListener<Number>() { // Velocity car
      public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) { //
        car_10.setVel(newValue.doubleValue());
      } // method changed ends here
    }); // Listener for slider_car10 ends here

    /****************************************************************
     * Sub rotina: slider_car11
     * Funcao: Controla o slider do carro 11
     * Parametros: Um evento (usuario(a) movimentar o slider)
     * Retorno: void
     ****************************************************************/
    slider_car11.valueProperty().addListener(new ChangeListener<Number>() { // Velocity car
      public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) { //
        car_11.setVel(newValue.doubleValue());
      } // method changed ends here
    }); // Listener for slider_car11 ends here

    /****************************************************************
     * Sub rotina: slider_car08
     * Funcao: Controla o slider do carro 8
     * Parametros: Um evento (usuario(a) movimentar o slider)
     * Retorno: void
     ****************************************************************/
    slider_car08.valueProperty().addListener(new ChangeListener<Number>() { // Velocity car
      public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) { //
        car_08.setVel(newValue.doubleValue());
      } // method changed ends here
    }); // Listener for slider_car08 ends here

    /****************************************************************
     * Sub rotina: slider_car15
     * Funcao: Controla o slider do carro 15
     * Parametros: Um evento (usuario(a) movimentar o slider)
     * Retorno: void
     ****************************************************************/
    slider_car15.valueProperty().addListener(new ChangeListener<Number>() { // Velocity car
      public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) { //
        car_15.setVel(newValue.doubleValue());
      } // method changed ends here
    }); // Listener for slider_car15 ends here

    /****************************************************************
     * Sub rotina: slider_car20
     * Funcao: Controla o slider do carro 20
     * Parametros: Um evento (usuario(a) movimentar o slider)
     * Retorno: void
     ****************************************************************/
    slider_car20.valueProperty().addListener(new ChangeListener<Number>() { // Velocity car
      public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) { //
        car_20.setVel(newValue.doubleValue());
      } // method changed ends here
    }); // Listener for slider_car20 ends here
  } // iniatilize method ends here

  /*********************************************************************
   * Classe: Car
   * Funcao: Controla os carros na simulacao
   * Parametros: Vbox que representam os carros e a respectiva id
   *********************************************************************/
  private class Car extends Thread {
    private VBox car;
    private int id;
    private double vel = 10;
    private double xPos;
    private double yPos;

    public Car(VBox car, int id) {
      this.car = car;
      this.id = id;
    } // constructor ends here

    /*********************************************************************
     * Metodo: route01
     * Funcao: Determita a rota 1
     * Parametros: void
     * Retorno: void
     *********************************************************************/
    private void route01() throws InterruptedException {
      while (isStarted) {
        driveTo(70, 320, "down");
        // critical region
        left_11_01.acquire();// requires 11 a big acces
        left_01_05.acquire();// requires access to 05 on left
        left_01_10_05.acquire();// requires access to 10 and 05 on left ********************** rensme
        driveTo(70, 350, "down");
        driveTo(0, 350, "left");
        driveTo(0, 240, "up");
        leftUp_15_01.acquire();
        left_01_08.acquire();
        left_20_01.acquire();// requires access to 20 on left
        driveTo(0, 180, "up");
        left_01_10_05.release(); // releases access to 10 and 05 on left
        driveTo(0, 110, "up");
        left_20_01.release();// releases access to 20 on left
        driveTo(0, 0, "up");
        driveTo(30, 0, "right");
        left_01_08.release(); // releases acess to 08
        driveTo(110, 0, "right");
        topLeft_20_01.acquire();// requires access to 20 on top left
        driveTo(140, 0, "right");
        driveTo(140, 30, "down");
        left_01_05.release(); // realeases access to 05
        leftUp_15_01.release(); // releases access to 15
        driveTo(140, 40, "down");
        up_01_08.acquire();// requires access to 08
        driveTo(140, 70, "down");
        driveTo(170, 70, "right");
        left_11_01.release();// releases 11 a big acces
        topLeft_20_01.release();// releases access to 20 on top left
        driveTo(180, 70, "right");
        right_01_05.acquire();// requires access to 05
        rightUp_15_01.acquire(); // requires accesss to 15 on right top
        topRight_20_01.acquire();// requires acces to 20 on top right
        driveTo(210, 70, "right");
        driveTo(210, 40, "up");
        up_01_08.release();// releases access to 08
        driveTo(210, 30, "up");
        // right_01_05.acquire();// requires access to 05
        driveTo(210, 0, "up");
        driveTo(240, 0, "right");
        rightUp_15_01.release();
        topRight_20_01.release();// releases acces to 20 on top right
        driveTo(350, 0, "right");
        driveTo(350, 40, "down");
        right_01_08.acquire();
        driveTo(350, 110, "down");
        right_20_01.acquire();// requires access to 20 on right
        driveTo(350, 180, "down");
        right_01_10_05.acquire();// requires access to 05 and 10 on right
        driveTo(350, 240, "down");
        right_01_08.release();
        right_20_01.release();// releases access to 20 on right
        driveTo(350, 350, "down");
        driveTo(280, 350, "left");
        driveTo(280, 320, "up");
        right_01_05.release();// releases access to 01 on right
        right_01_10_05.release();// realeases access to 01 and 10 on right
        driveTo(280, 310, "up");
        bottomRight_20_01.acquire();// requires access to 20 on right
        driveTo(280, 240, "up");
        rightBottom_15_01.acquire();
        midRight_01_10_08.acquire();// riquires access to 10 and 08 on mid right
        driveTo(280, 210, "up");
        driveTo(250, 210, "left");
        bottomRight_20_01.release();// releases access to 20 on right
        driveTo(240, 210, "left");
        // rightBottom_15_01.acquire();
        driveTo(210, 210, "left");
        driveTo(210, 180, "up");
        midRight_01_10_08.release(); // releases acess to 10 and 08 on mid right

        driveTo(210, 140, "up");
        driveTo(180, 140, "left");
        rightBottom_15_01.release();// releases access to 15 on 15s right bottom
        driveTo(170, 140, "left");
        rightMid_11_01.acquire();// requires 11 an access in 11 s right mid
        driveTo(140, 140, "left");
        // critical region with 10
        driveTo(140, 130, "down"); // stops on critical section
        midLeft_01_10_08.acquire();// requires access to 10 and 08 on mid left

        bottom_15_01.acquire();
        driveTo(140, 210, "down");
        driveTo(110, 210, "left");
        rightMid_11_01.release();// releases 11 an access on it s right mid
        driveTo(100, 210, "left");
        bottomLeft_20_01.acquire();// requires access to 20 bottom left
        driveTo(70, 210, "left");
        driveTo(70, 240, "down");
        bottom_15_01.release();
        midLeft_01_10_08.release(); // //requires access to 10 and 08 on mid left////
        driveTo(70, 310, "down"); // returns to start position
        bottomLeft_20_01.release();// releases access to 20 bottom left
      } // while statement ends here
    } // ride method ends here

    /*********************************************************************
     * Metodo: route05
     * Funcao: Determita a rota 5
     * Parametros: void
     * Retorno: void
     *********************************************************************/
    private void route05() throws InterruptedException {
      while (isStarted) {
        // round key
        // rightRotaryKey_5_08_10.acquire();
        leftUp_15_05.acquire();// requires acess to 15 on top and left
        top_20_05.acquire();// requires acess to 20 on top
        driveTo(180, 0, "left");
        right_01_05.release(); // release access to 01 on right
        driveTo(170, 0, "left");
        topLeftBottom_11_05.acquire();// requires 11 to access all left side
        left_01_05.acquire(); // require access to 01 on left
        driveTo(110, 0, "left");
        top_20_05.release();// releases acess to 20 on top
        driveTo(0, 0, "left");
        driveTo(0, 40, "down");
        left_05_08.acquire(); // require access to 01 and 08 on left
        driveTo(0, 110, "down");
        left_20_05.acquire();// requires access to 20 on left
        driveTo(0, 180, "down");
        down_05_10.acquire();// require access to 10
        driveTo(0, 240, "down");
        left_05_08.release();// releases access to 01 and 08 on left
        leftUp_15_05.release();
        left_20_05.release();// releases access to 20 on left
        driveTo(0, 350, "down");
        driveTo(100, 350, "right");
        left_01_05.release();// releases access to 01
        driveTo(110, 350, "right");
        bottom_20_05.acquire();// requires access to 20 on bottom
        driveTo(170, 350, "right");
        topLeftBottom_11_05.release();// releases all the left side for 11
        driveTo(240, 350, "right");
        bottom_20_05.release();// releases access to 20 on bottom
        driveTo(250, 350, "right");
        right_01_05.acquire();// require access to 01
        driveTo(350, 350, "right");
        driveTo(350, 240, "up");
        right_05_08.acquire();// require acess to 01 and 08 on the right
        right_20_05.acquire();// require acess to 20 on right
        driveTo(350, 180, "up");
        down_05_10.release(); // releases access to 10 on right
        driveTo(350, 110, "up");
        right_20_05.release();// releases acess to 20 on right
        driveTo(350, 40, "up");
        right_05_08.release(); // releases access to 08
        driveTo(350, 0, "up");
        driveTo(240, 0, "left");// returns to starting position
      } // while ends here
    } // ride method ends here

    /*********************************************************************
     * Metodo: route10
     * Funcao: Determita a rota 10
     * Parametros: void
     * Retorno: void
     *********************************************************************/
    private void route10() throws InterruptedException {
      while (isStarted) {
        driveTo(250, 210, "right");
        right_20_10.acquire();// requires access to 20 on right
        driveTo(310, 210, "right");
        midRight_01_10_08.release(); // realeases access to 01 and 08

        driveTo(320, 210, "right");
        // bottom_15_10.acquire();// require access on 15 s bottom
        down_05_10.acquire(); // acquire access 05 on 05 s bottom
        right_01_10_05.acquire(); // acquire access to 01 and 05

        driveTo(350, 210, "right");
        driveTo(350, 240, "down");
        mid_10_08.release(); // release access to 08
        right_20_10.release();// release access to 20 on right
        driveTo(350, 350, "down");
        driveTo(250, 350, "left");
        right_01_10_05.release(); // releases acces to 01 and 05 on right
        driveTo(240, 350, "left");
        bottom_20_10.acquire();// requires acess to 20 on bottom
        driveTo(170, 350, "left");
        bottom_15_10.acquire();// require access on 15 s bottom
        leftAndBottom_11_10.acquire();// requires access to 11 on 11 s left and bottom
        driveTo(110, 350, "left");
        bottom_20_10.release();// releases access to 20 on bottom
        driveTo(100, 350, "left");
        left_01_10_05.acquire(); // require access to 01 and 05 on left
        driveTo(0, 350, "left");
        driveTo(0, 240, "up");
        mid_10_08.acquire(); // require access to 08
        left_20_10.acquire();// requires acess to 20 on left
        driveTo(0, 210, "up");
        driveTo(30, 210, "right");
        leftAndBottom_11_10.release();// releases access to 11 on 11 s left and bottom
        down_05_10.release(); // releases access to 05
        left_01_10_05.release(); // releases access to 10 and 05 on left 10 and 01

        driveTo(40, 210, "right");
        midLeft_01_10_08.acquire();
        driveTo(100, 210, "right");
        left_20_10.release();// releases 20 on left
        driveTo(110, 210, "right");
        dotRightBottom_11_10or08.acquire();// require 11 to pass for it

        driveTo(170, 210, "right");
        dotRightBottom_11_10or08.release();// releases 11 to pass for
        midLeft_01_10_08.release();

        driveTo(180, 210, "right");
        midRight_01_10_08.acquire();

        driveTo(240, 210, "right"); // returns to start position
        bottom_15_10.release();// releases access on 15 s bottom
      } // While ends here
    } // ride method ends here

    /*********************************************************************
     * Metodo: route08
     * Funcao: Determita a rota 8
     * Parametros: void
     * Retorno: void
     *********************************************************************/
    private void route08() throws InterruptedException {
      while (isStarted) {
        // critical section with 1 happens here
        driveTo(40, 70, "right");
        topLeft_20_08.acquire();// requires acess to 20
        driveTo(110, 70, "right");
        up_01_08.acquire();// requires access to 1 on top
        dotTop_11_08.acquire();// requires 11 to pass for it on it s top
        driveTo(170, 70, "right");
        dotTop_11_08.release();// releases 11 to pass for it on it s top
        topLeft_20_08.release();// releases acess to 20
        driveTo(180, 70, "right");
        topRight_20_08.acquire();// requires access to 20 on top right
        dot_15_08.acquire(); // requires access to dot with 15
        driveTo(240, 70, "right");
        up_01_08.release(); // releases access to 1 on top
        dot_15_08.release();// releases access to dot with 15
        driveTo(310, 70, "right");
        topRight_20_08.release();// releases access to 20 on top right
        driveTo(320, 70, "right");
        left_05_08.acquire();
        mid_10_08.acquire();
        right_05_08.acquire();// requires access to 01 on right
        right_01_08.acquire();// requires access to 05 on right
        driveTo(350, 70, "right");
        // critical section with 1 ad 5 starts here
        driveTo(350, 110, "down");
        right_20_08.acquire();// requires 20 for access on right
        driveTo(350, 210, "down");
        driveTo(320, 210, "left");
        right_01_08.release();
        right_05_08.release();
        driveTo(310, 210, "left");
        midRight_01_10_08.acquire();
        driveTo(240, 210, "left");
        right_20_08.release();// releases access to 20 on right
        driveTo(240, 210, "left");
        bottomAndLeft_15_08.acquire();// requires access to bottom and left to 15
        driveTo(180, 210, "left");
        midRight_01_10_08.release();

        driveTo(170, 210, "left");
        dotRightBottom_11_10or08.acquire(); // require 11 to pass for it
        midLeft_01_10_08.acquire();
        driveTo(110, 210, "left");
        dotRightBottom_11_10or08.release(); // 8 releases 11 to pass for it
        driveTo(100, 210, "left");
        left_20_08.acquire();// requires access to 20 on left
        driveTo(40, 210, "left");
        midLeft_01_10_08.release();
        driveTo(30, 210, "left");
        left_01_08.acquire();
        left_11_08.acquire();// requires access to 11 on it s
        driveTo(0, 210, "left");
        driveTo(0, 180, "up");
        mid_10_08.release();
        driveTo(0, 110, "up");
        left_20_08.release();// requires access to 20 on left
        driveTo(0, 70, "up");
        // critical section with 1 ad 5 ends here
        driveTo(30, 70, "right"); // returnens to start position
        left_01_08.release();// releases access to 01 on left
        left_05_08.release();// releases access to 05 on left
        bottomAndLeft_15_08.release(); // releases access to 15 on left and bottom
        left_11_08.release();// releases access to 11 on it s
      } // While ends here
    } // ride method ends here

    /*********************************************************************
     * Metodo: route15
     * Funcao: Determita a rota 15
     * Parametros: void
     * Retorno: void
     *********************************************************************/
    private void route15() throws InterruptedException {
      while (isStarted) {
        driveTo(210, 110, "down");
        rightBottom_15_01.acquire();// requires access to 01 on botto, right
        driveTo(210, 180, "down");
        leftUp_15_05.acquire();
        bottom_15_10.acquire(); // require access to 10 on the bottom
        bottomAndLeft_15_08.acquire();
        driveTo(210, 210, "down");
        driveTo(180, 210, "left");
        rightBottom_15_01.release(); // releases access to 01 on bottom right
        driveTo(170, 210, "left");
        bottom_15_01.acquire();
        dotRightBottom_11_10or08.acquire();// requires 11 to pass for it
        driveTo(110, 210, "left");
        dotRightBottom_11_10or08.release();// releases 11 to pass for it
        driveTo(100, 210, "left");
        left_20_15.acquire();// requires access to 20 on left
        driveTo(40, 210, "left");
        bottom_15_01.release();
        driveTo(30, 210, "left");
        leftUp_15_01.acquire();
        left_11_15.acquire();// requires access to 15 on it s left
        driveTo(0, 210, "left");
        driveTo(0, 180, "up");
        bottom_15_10.release();// releases accces to 10
        driveTo(0, 110, "up");
        left_20_15.release();// releases access to 20 on left
        driveTo(0, 40, "up");
        bottomAndLeft_15_08.release();
        driveTo(0, 0, "up");
        driveTo(110, 0, "right");
        top_20_15.acquire();// requires access to 20 on top
        driveTo(170, 0, "right");
        leftUp_15_01.release();
        left_11_15.release();// releases access to 11 on its left
        driveTo(180, 0, "right");
        rightUp_15_01.acquire();
        driveTo(210, 0, "right");
        driveTo(210, 30, "down");
        leftUp_15_05.release();
        driveTo(210, 40, "down");
        dot_15_08.acquire();// requires access to the dot for 08
        driveTo(210, 100, "down");// starting position
        dot_15_08.release();// releases access to the dot for 08
        top_20_15.release();// releases access to 20 on top
        rightUp_15_01.release();
      } // while statement ends here
    }// route15 method ends here

    /*********************************************************************
     * Metodo: route11
     * Funcao: Determita a rota 11
     * Parametros: void
     * Retorno: void
     *********************************************************************/
    private void route11() throws InterruptedException {
      while (isStarted) {
        left_11_01.acquire();// requires acces to 1 on 11 s left
        top_20_11.acquire();// requires access to 20 on 11 s right top
        dotTop_11_08.acquire();
        driveTo(140, 40, "up");
        dotTop_11_08.release();
        driveTo(140, 30, "up");
        topLeftBottom_11_05.acquire(); // requires access to top, left and bottom
        left_11_15.acquire();// requires access to 15 on 11 left side
        driveTo(140, 0, "up");
        driveTo(110, 0, "left");
        top_20_11.release();// releases access to 20 on 11 s right top
        driveTo(0, 0, "left");
        driveTo(0, 40, "down");
        leftAndBottom_11_10.acquire(); // requires access to 10 on left and bottom
        left_11_08.acquire();// requires access to 8 on left
        driveTo(0, 110, "down");
        mid_20_11.acquire();// requires access to 20 on 11 s left
        driveTo(0, 180, "down");
        // leftAndBottom_11_10.acquire(); // requires access to 10 on left and bottom
        driveTo(0, 240, "down");
        left_11_15.release();// releases access to 15 on 11 left side
        left_11_08.release();// requires access to 8 on left
        mid_20_11.release();// requires access to 20 on 11 s left
        driveTo(0, 350, "down");
        driveTo(100, 350, "right");
        left_11_01.release();// releases acces to 1 on 11 s left
        driveTo(110, 350, "right");
        bottom_20_11.acquire();// requires acess to 20 o bottom right
        driveTo(140, 350, "right");
        driveTo(70, 320, "up");
        topLeftBottom_11_05.release(); // releases access to top, left and bottom
        leftAndBottom_11_10.release();// releases access to 10 on left and botto
        driveTo(70, 250, "up");
        bottom_20_11.release();// release acess to 20 o bottom rightm
        driveTo(70, 240, "up");
        rightMid_11_01.acquire();// requires acces to 1 on 11 s right mid
        dotRightBottom_11_10or08.acquire();// requires 10 or 08 to pass for them in right bottom
        driveTo(70, 180, "up");
        dotRightBottom_11_10or08.release();// requires 10 or 08 to pass for them in right bottom
        driveTo(70, 110, "up");
        rightMid_11_01.release();// release acces to 1 on 11 s right mid
        driveTo(70, 100, "up"); // returns to starting position
      } // while statement ends here
    }// route11 method ends here

    /*********************************************************************
     * Metodo: route20
     * Funcao: Determita a rota 22
     * Parametros: void
     * Retorno: void
     *********************************************************************/
    private void route20() throws InterruptedException {
      while (isStarted) {
        topRight_20_08.acquire(); // requires acess to 08 on top right
        driveTo(280, 70, "up");
        driveTo(240, 70, "left");
        top_20_11.acquire();// requires access to 11
        top_20_15.acquire();// requires access to 15 on top
        topRight_20_01.acquire();// requires access to 01 on top right
        driveTo(210, 70, "left");
        driveTo(210, 40, "up");
        topRight_20_08.release();// releases access to 08 on top right
        driveTo(210, 30, "up");
        top_20_05.acquire();// requires access to 05 on top
        driveTo(210, 0, "up");
        driveTo(180, 0, "left");
        topRight_20_01.release();// releases access to 01 on top right
        driveTo(170, 0, "left");
        topLeft_20_01.acquire();// requires access to 01 on top left
        driveTo(140, 0, "left");
        driveTo(140, 30, "down");
        top_20_05.release();// releases access to 05 on top
        top_20_15.release();// releases access to 15 on top
        driveTo(140, 40, "down");
        topLeft_20_08.acquire();// requires access to 08 on top left
        driveTo(140, 70, "down");
        driveTo(110, 70, "left");
        topLeft_20_01.release();// releases access to 01 on top left
        top_20_11.release();// releases access to 11 on top
        driveTo(70, 70, "left");
        driveTo(70, 100, "down");
        topLeft_20_08.release();// releases acess to 08 on top left
        driveTo(70, 140, "down");
        driveTo(30, 140, "left");
        left_20_08.acquire();// requires acess to 08 on left
        left_20_15.acquire();// requires acccess to 15 on left
        left_20_01.acquire();// requires access to 01 on left
        left_20_05.acquire();// requrires access to 05 on left
        mid_20_11.acquire();// requires access to 11 on left mid
        driveTo(0, 140, "left");
        driveTo(0, 180, "down");
        left_20_10.acquire();// requires access to 10 on left
        driveTo(0, 210, "down");
        driveTo(30, 210, "right");
        left_20_01.release();// releases access to 01 on left
        left_20_05.release();// releases access to 05 on left
        mid_20_11.release();// releases access to 11 on left
        driveTo(40, 210, "right");
        bottomLeft_20_01.acquire();// requires access to 01 on left
        driveTo(70, 210, "right");
        driveTo(70, 240, "down");
        left_20_15.release();// releases 15 on left
        left_20_08.release();// releases access to 08
        left_20_10.release();// releases access to 10
        driveTo(70, 280, "down");
        driveTo(100, 280, "right");
        bottomLeft_20_01.release();// release access to 01 on left
        driveTo(110, 280, "right");
        bottom_20_10.acquire();// requires access to 10 on bottom
        bottom_20_11.acquire();// requieres access to 11 on bottomz
        driveTo(140, 280, "right");
        driveTo(140, 320, "down");
        bottom_20_05.acquire();// requires access to 05 on bottom
        driveTo(140, 350, "down");
        driveTo(170, 350, "right");
        bottom_20_11.release();// releases access to 11 on bottom
        driveTo(210, 350, "right");
        driveTo(210, 320, "up");
        bottom_20_05.release();// releases access to 05 on bottom
        bottom_20_10.release();// releases access to 10 on bottom
        driveTo(210, 280, "up");
        driveTo(250, 280, "right");
        bottomRight_20_01.acquire();// requires access to 01 on right
        driveTo(280, 280, "right");
        driveTo(280, 240, "up");
        right_20_08.acquire();// requires access to 08 on right
        right_20_10.acquire(); // requires access to 10 on right
        driveTo(280, 210, "up");
        driveTo(310, 210, "right");
        bottomRight_20_01.release();// releases access to 01 on right
        driveTo(320, 210, "right");
        right_20_01.acquire();// requires access to 01 on right
        right_20_05.acquire();// requires access to 05 on iright
        driveTo(350, 210, "right");
        driveTo(350, 180, "up");
        right_20_10.release();// releases access 10 on right
        driveTo(350, 140, "up");
        driveTo(320, 140, "left");
        right_20_08.release();// releases access to 08 on right
        right_20_01.release();// requires access to 01 on right
        right_20_05.release();// releases access to 20 on right
        driveTo(280, 140, "left");
        driveTo(280, 100, "up"); // returns to starting position
      } // while statement ends here
    }// route20 method ends here

    /*********************************************************************
     * Metodo: driveTo
     * Funcao: Dirige ate uma posicao numa dada direcao
     * Parametros: posicoes x e y destino e direcao
     * Retorno: void
     *********************************************************************/
    private void driveTo(int x, int y, String direction) {
      switch (direction) {
        case "right":
          xPos = car.getLayoutX();
          while (xPos < x) {
            xPos++;
            Platform.runLater(() -> { // Prevents from getting java exceptios
              car.setLayoutX(xPos);
            });// runLater ends here
            try {
              sleep((long) (21 - vel));
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          } // while ends here
          break;
        case "left":
          xPos = car.getLayoutX();
          while (xPos > x) {
            xPos--;
            Platform.runLater(() -> { // Prevents from getting java exceptios
              car.setLayoutX(xPos);
            });
            try {
              sleep((long) (21 - vel));
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          } // while ends here
          break;
        case "down":
          yPos = car.getLayoutY();
          while (yPos < y) {
            yPos++;
            Platform.runLater(() -> { // Prevents from getting java exceptios
              car.setLayoutY(yPos);
            });
            try {
              sleep((long) (21 - vel));
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          } // while ends here
          break;
        case "up":
          yPos = car.getLayoutY();
          while (yPos > y) {
            yPos--;
            Platform.runLater(() -> { // Prevents from getting java exceptios
              car.setLayoutY(yPos);
            });
            try {
              sleep((long) (21 - vel));
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          } // while ends here
          break;
        default:
          break;
      }// swith case ends here
    }// driveTo method ends here

    /*********************************************************************
     * Metodo: setVel
     * Funcao: Muda a velocidade dos carros
     * Parametros: Nova velocidade
     * Retorno: void
     *********************************************************************/
    public void setVel(double vel) {
      this.vel = vel;
    }

    /*********************************************************************
     * Metodo: run
     * Funcao: Executa a rotina da thread
     * Parametros: void
     * Retorno: void
     *********************************************************************/
    public void run() {
      switch (id) {// Select the correct routine
        case 1:
          try {
            route01();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          break;
        case 5:
          try {
            route05();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          break;
        case 10:
          try {
            route10();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          break;
        case 8:
          try {
            route08();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        case 15:
          try {
            route15();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        case 11:
          try {
            route11();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        case 20:
          try {
            route20();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        default:
          break;
      }// switch case ends here
    }// run method ends here
  }// car class ends here

  /*********************************************************************
   * Classe: Manager
   * Funcao: Testa o programa automaticamente variando a velocidade dos carros
   * Parametros: null
   *********************************************************************/
  private class Manager extends Thread {
    private int counter = 0;

    /*********************************************************************
     * Metodo: run
     * Funcao: Executa a rotina da thread
     * Parametros: void
     * Retorno: void
     *********************************************************************/
    public void run() {
      while (true) {
        try {
          sleep(2 * 60 * 1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        car_01.setVel((Math.random() * 100) % 20);
        car_05.setVel((Math.random() * 100) % 20);
        car_08.setVel((Math.random() * 100) % 20);
        car_10.setVel((Math.random() * 100) % 20);
        car_11.setVel((Math.random() * 100) % 20);
        car_15.setVel((Math.random() * 100) % 20);
        car_20.setVel((Math.random() * 100) % 20);

        counter++;
        System.out.println(counter);
      } // while ends here
    }// run method ends here
  }// manager class ends here
}// ControllSimulation class ends here