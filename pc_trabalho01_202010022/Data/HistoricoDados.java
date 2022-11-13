/* ***************************************************************
* Autor............: Roger Daniel Santana Simões
* Matricula........: 202010022
* Inicio...........: 17/01/2022
* Ultima alteracao.: 17/01/2022
* Nome.............: HitoricoDados(Data) 
* Funcao...........: Gerencia o arquivo que armazena o historico de placares
*************************************************************** */

package Data;

import Model.Historico;

import java.io.*;
import java.util.ArrayList;

public class HistoricoDados { // Inicio da classe

  // Metodos

  /****************************************************************
   * Metodo: guardar
   * Funcao: guarda os dados das partidas no arquivo que armazenados o historico
   * Parametros: dados de uma partida
   * Retorno: vazio
   */
  public void guardar(String novosDados) throws Exception { // Inicio do metodo guardar
    String dadosAntigos = ler();
    File arquivo = new File("Arquivo_de_historico.dat"); // Cria um objeto File

    if (!arquivo.exists()) {// inicio da condicional
      arquivo.createNewFile(); // Cria um arquivo com o nome dado como atributo do objeto, caso o arquivo nao
                               // exista
    } // Fim da condicional

    // Cria a Stream responsavel por receber os dados que serao gravados por File no
    // arquivo
    FileOutputStream arq = new FileOutputStream(arquivo);
    DataOutputStream gravarArq = new DataOutputStream(arq);

    // Metodo que grava os dados
    gravarArq.writeUTF(dadosAntigos + novosDados + "\n");

    // Fecha os arquivos pra num dar xabu
    arq.close();
    gravarArq.close();
  }// Fim do metodo guardar

  /****************************************************************
   * Metodo: ler
   * Funcao: le os dados das partidas que estao arquivo que armazenados o
   * historico
   * Parametros: vazio
   * Retorno: dados de todas as partidas salvas
   */
  public String ler() {// inicio do metodo ler
    try {// inicio do try
      File arquivo = new File("Arquivo_de_historico.dat"); // Cria um objeto File
      if (!arquivo.exists()) {// inicio da condicional
        arquivo.createNewFile(); // Cria um arquivo com o nome dado como atributo do objeto, caso o exista
		guardar("");
      } // Fim da condicional

      // Cria a Stream responsavel por ler os dados gravados por File no arquivo
      FileInputStream arq = new FileInputStream(arquivo);
      DataInputStream lerArq = new DataInputStream(arq);

      String dados = "";
	  while(arq.available() > 0){
	    dados += lerArq.readUTF();
	  }

      lerArq.close();
      // retorna a string contendo os dados do historico
      return dados;

    } catch (Exception e) {// FIm do try / inicio do catch
      System.out.println("Erro ao tentar ler arquivo; " + e);
    } // fim do catch

    return "Error";
  } // Fim do metodo ler

  /****************************************************************
   * Metodo: limpar
   * Funcao: deleta dados das partidas que estao arquivo que armazenados o
   * historico
   * Parametros: vazio
   * Retorno: vazio
   */
  public void limpar() throws Exception {// inicio do metodo limpar
    File arquivo = new File("Arquivo_de_historico.dat"); // Cria um objeto File

    if (arquivo.exists()) // Deleta o arquivo inteiro, caso ele exista
      arquivo.delete();
  }// fim do metodo limpar
}// Fim da classe