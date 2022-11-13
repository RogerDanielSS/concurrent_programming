/* ***************************************************************
* Autor............: Roger Daniel Santana Simões
* Matricula........: 202010022
* Inicio...........: 17/01/2022
* Ultima alteracao.: 17/01/2022
* Nome.............: Hitorico (Model)
* Funcao...........: Configura o modelo de historico a ser utilizado
*************************************************************** */

package Model;

import Data.HistoricoDados;

public class Historico { // inicio da classe
  // Atributos

  private String dados;

  // Contrutores
  /*
   * ***************************************************************
   * Metodo: historico
   * Funcao: Constroi um objeto vazio da classe historico
   * Parametros: void
   * Retorno: um objeto vazio da classe historico
   */
  public Historico() {
  }

  /*
   * ***************************************************************
   * Metodo: historico
   * Funcao: Constroi um objeto da classe historico
   * Parametros: String
   * Retorno: um objeto da classe historico
   */
  public Historico(String time1, String time2, String placar1, String placar2, String data) {
    this.dados = ("" + time1 + "    " + placar1 + " X " + placar2 + "    " + time2 + "            " + data);
  }

  // Metodos

  /****************************************************************
   * Metodo: setDados
   * Funcao: Salva a String recebida no espaço de memoria do atributo da classe
   * Parametros: String
   * Retorno: um objeto da classe historico
   */
  public void setDados(String time1, String time2, String placar1, String placar2, String data) {
    this.dados = ("" + time1 + "    " + placar1 + " X " + placar2 + "    " + time2 + "            " + data);
  }

  /****************************************************************
   * Metodo: print
   * Funcao: Retorna os dados do historico que a classe Historico tem acesso a
   * partir da classe HistoricoDados
   * Parametros: Vazio
   * Retorno: Uma String contendo o historico das partidas jogadas
   */
  public String print() throws Exception {// inicio do metodo print
    HistoricoDados arq = new HistoricoDados();
    String historico = arq.ler();

    return historico;
  } // Fim do metodo print

  /****************************************************************
   * Metodo: limpar
   * Funcao: Apaga o historico
   * Parametros: Vazio
   * Retorno: vazio
   */
  public void limpar() throws Exception { // Inicio do metodo limpar
    HistoricoDados arq = new HistoricoDados();
    arq.limpar();
  } // Fim do metodo limpar

  /****************************************************************
   * Metodo: Guardar
   * Funcao: Passa os dados da classe para serem salvos no arquivo de historico
   * Parametros: Vazio
   * Retorno: vazio
   */
  public void guardar() throws Exception { // Inicio do metodo guardar
    HistoricoDados arq = new HistoricoDados();
    arq.guardar(this.dados);
  }// Fim do metodo guardar
} // fim da classe