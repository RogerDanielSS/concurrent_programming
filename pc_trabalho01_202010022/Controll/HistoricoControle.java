package Controll;

import Model.Historico;

public class HistoricoControle {// Inicio da classe
  // Metodos

  /*
   * ***************************************************************
   * Metodo: Guardar
   * Funcao: Cria um objeto do tipo historico e usa o metodo guardar da classe historico para armazenar os dados recebidos no parametro no arquivo
   * Parametros: Strings contendo informacoes das partidas
   * Retorno: vazio
   */
  public void guardar(String time1, String time2, String placar1, String placar2, String data) throws Exception {// inicio do metodo guardar
    Historico historico = new Historico(time1, time2, placar1, placar2, data);
    historico.guardar();
  } // Fim do metodo guardar


   /*
   * ***************************************************************
   * Metodo: Limpar
   * Funcao: Cria um objeto do tipo historico e usa o metodo limpar da classe historico para apagar o arquivo que armazena todos os dados
   * Parametros: vazio
   * Retorno: vazio
   */
  public void limpar() throws Exception { // Inicio do metodo limpar
    Historico historico = new Historico();
    historico.limpar();
  }// Fim do metodo limpar


   /*
   * ***************************************************************
   * Metodo: ler
   * Funcao: Cria um objeto do tipo historico e usa o metodo print da classe historico, que retorna uma string contendo os dados armazenados no arquivo
   * Parametros: vazio
   * Retorno: Dados de todas as partidos salvas no historico
   */
  public String ler() throws Exception {// Inicio do metodo print
    Historico historico = new Historico();
    return historico.print();
  }// Fim do metodo print

}// Fim da classe