/****************************************************************
* Autor: Cristiano Costa
* Matricula: 201911918
* Inicio: 15/05/2021
* Ultima alteracao: 04/09/2021
* Nome: ConsumidorThread.java
* Funcao: Controlar todos os processos do meu consumidor
****************************************************************/
package threads;

import controller.PrincipalController;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class ConsumidorThread extends Thread {
  private PrincipalController controller;
  private boolean vivo; // variavel q irah definir a condicao do loop do meu consumidor
  private int nConsumidor; // para saber com qual produtor estou trabalhando
  @FXML private ImageView consumidor;


  public ConsumidorThread(PrincipalController controller, int nConsumidor){
    this.controller = controller;
    this.nConsumidor = nConsumidor;
    consumidor = this.controller.adicionarImagemConsumidor(this.nConsumidor );   
    vivo = true;
  }  

  @Override
  public void run(){
    while(vivo){
      try {
        Thread.sleep(1000);
        irAteBalcao();
        retirarCerveja(); 
        consumirCerveja();
      } catch (InterruptedException e) {}
    }
  }

  private void irAteBalcao() throws InterruptedException{
    // Decremento as posicoes cheias do meu balcao 
    PrincipalController.balcaoCheio.acquire();
    PrincipalController.mutex.acquire();

    // Mudo a posicao do meu consumidor para perto do balcao 
    consumidor.setImage(null);
    controller.setImgConsumidorRetirando(nConsumidor, false);  

    Thread.sleep(500);
  }

  private void retirarCerveja(){
    PrincipalController.mutex.release();
    // Incremento as posicoes vazias do meu balcao
    PrincipalController.balcaoVazio.release();
    // Mudo a posicao do meu consumidor para a mesa, onde ele irah consumir a cerveja
    controller.setImgConsumidorRetirando(nConsumidor, true);
    consumidor = controller.adicionarImagemConsumidor(nConsumidor);
    controller.adicionarCervejaNaMesa(nConsumidor);
    
  }

  private void consumirCerveja() throws InterruptedException{
    Thread.sleep(controller.getVelocidadeConsumidor() * 500);
    controller.removerCervejaDaMesa(nConsumidor);
  }

  public void setVivo(boolean vivo){
    this.vivo = vivo;
  }
}
