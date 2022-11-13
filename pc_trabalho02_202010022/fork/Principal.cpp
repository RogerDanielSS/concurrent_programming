/* *************************************************************** 
* Autor............: Roger Daniel Santana Simoes
* Matricula........: 202010022
* Inicio...........: 09/02/2022
* Ultima alteracao.: 03/03/2022
* Nome.............: Arvore genealogica
* Funcao...........: Mostra, no terminal, a arvore genealogica de uma familha (pre determinada) utilizando threads
*************************************************************** */

#include <iostream>
#include <time.h>
#include <unistd.h>
#include <signal.h>
#include <sys/wait.h>
#include <errno.h>

using namespace std;

int main() {//Inicio da main
  //Variaveis

  time_t begin, end;
  time(&begin);
  int deltaTime, id;
  int jose;
  pid_t id1, id2, id3, id4, id5, id6 = 999;
  int pastDeltaTime = 0;

  
  jose = getpid();//Armazena o pid do primeiro proceso
  cout << "Nascimento do processo 1, com pid " << jose << endl;

  while( (time(&end) - begin) < 99){// inixio so while
    deltaTime = (time(&end) - begin);

   if(deltaTime != pastDeltaTime and jose == getpid()) 
      cout << " Ano: " << 1999 + deltaTime << endl;
    
    if(deltaTime==22 and deltaTime!=pastDeltaTime){ //Nascimento de Pedro, filho 1
      id1 = fork();

    switch(id1){//Inicio do Switch
      case -1: //Erro
        perror("fork");
        exit(EXIT_FAILURE);
      case 0: //Processo filho - Pedro
        cout << "Nasce Pedro, o primeiro filho de Jose, com id "<< getpid() << endl;
        break;
      default: // processo pai - jose
        cout << "Jose, com id " << getpid() << ", tem o primeiro filho" << endl;
        break;
      }//Fim do switch
    }// Fim do nascimento de Pedro, filho 1


    if(deltaTime==25 and deltaTime!=pastDeltaTime and jose == getpid()){ //Nascimento de Tiago, filho 2
      id2 = fork();

    switch(id2){//Inicio do Switch
      case -1: //Erro
        perror("fork");
        exit(EXIT_FAILURE);
      case 0: //Processo filho - Pedro
        cout << "Nasce Tiago, o segundo filho de Jose, com id "<< getpid() << endl;
        break;
      default: // processo pai - jose
        cout << "Jose, com id " << getpid() << ", tem o segundo filho" << endl;
        break;
      }//Fim do switch
    }// Fim do nascimento de Tiago filho 2

    if(deltaTime==32 and deltaTime!=pastDeltaTime and jose == getpid()){ //Nascimento de Joao, filho 3
      id3 = fork();

    switch(id3){//Inicio do Switch
      case -1: //Erro
        perror("fork");
        exit(EXIT_FAILURE);
      case 0: //Processo filho - Joao
        cout << "Nasce Joao, o terceiro filho de Jose, com id "<< getpid() << endl;
        break;
      default: // processo pai - jose
        cout << "Jose, com id " << getpid() << ", tem o terceiro filho" << endl;
        break;
      }//Fim do switch
    }// Fim do nascimento de Joao, filho 3


    if(deltaTime==38 and deltaTime!=pastDeltaTime and id1 == 0 and id5!=0){ //Nascimento de Pedro Jr, neto 1
      id4 = fork();

    switch(id4){//Inicio do Switch
      case -1: //Erro
        perror("fork");
        exit(EXIT_FAILURE);
      case 0: //Processo filho - Pedro Jr
        cout << "Nasce Pedro Jr, o primeiro neto de Jose, com id "<< getpid() << endl;
        break;
      default: // processo pai - jose
        cout << "Pedro, com id " << getpid() << ", tem o primeiro filho" << endl;
        break;
      }//Fim do switch
    }// Fim do nascimento de Pedro Jr, neto 1


    if(deltaTime==45 and deltaTime!=pastDeltaTime and id2 == 0 and id5!=0){ //Nascimento de Tiago Jr, neto 2
      id5 = fork();

    switch(id5){//Inicio do Switch
      case -1: //Erro
        perror("fork");
        exit(EXIT_FAILURE);
      case 0: //Processo filho - Tiago Jr
        cout << "Nasce Tiago Jr, o segundo neto de Jose, com id "<< getpid() << endl;
        break;
      default: // processo pai - jose
        cout << "Tiago, com id " << getpid() << ", tem o primeiro filho" << endl;
        break;
      }//Fim do switch
    }// Fim do nascimento de Tiago Jr, neto 2

    if(deltaTime==68 and deltaTime!=pastDeltaTime and id4 == 0 and id6!=0){ //Nascimento de Pedro Neto, bisneto 1
      id6 = fork();

    switch(id6){//Inicio do Switch
      case -1: //Erro
        perror("fork");
        exit(EXIT_FAILURE);
      case 0: //Processo filho - Tiago Jr
        cout << "Nasce Pedro Neto, o primeiro bisneto de Jose, com id "<< getpid() << endl;
        break;
      default: // processo pai - jose
        cout << "Pedro Jr, com id " << getpid() << ", tem o primeiro filho" << endl;
        break;
      }//Fim do switch
    }// Fim do nascimento de Pedro Neto, bisneto 1


    if(deltaTime==73 and deltaTime!=pastDeltaTime and id4 == 0 and id6!=0){ //Inicio da morte de Pedro Jr, neto 1
      cout << "Morre Pedro Jr com pid " << getpid() << endl;  
      kill(getpid(), SIGKILL);    
    }//Fim da morte de Pedro Jr, neto 1


    if(deltaTime==78 and deltaTime!=pastDeltaTime and id5 == 0){ //Morte de Tiago Jr, neto 2
      cout << "Morre Tiago Jr com pid " << getpid() << endl;  
      kill(getpid(), SIGKILL);    
    }//Fim da morte de Tiago Jr, neto 2


    if(deltaTime==80 and deltaTime!=pastDeltaTime and (id6 == 0 or id2==0 and id5!=0)){ //Morte de Pedro Neto e de Tiago, bisneto 1 e filho 2
      if(id6==0)
        cout << "Morre Pedro Neto, com pid " << getpid() << endl;  

      if(id2==0)
        cout << "Morre Tiago, com pid " << getpid() << endl;  
      kill(getpid(), SIGKILL);    
    }//Fim da morte de  Pedro Neto, bisneto 1


    if(deltaTime==83 and deltaTime!=pastDeltaTime and id1==0 and id4 !=0){ //Morte de Pedro, filho 1
      int temp = getpid();
      cout << "Morre Pedro com pid " << temp << endl;  
      kill(temp, SIGKILL);    
    }//Fim da morte de  Pedro, filho 1


    if(deltaTime==87 and deltaTime!=pastDeltaTime and id3==0){ //Morte de Joao, filho 3
      int temp = getpid();
      cout << "Morre Joao com pid " << temp << endl;  
      kill(temp, SIGKILL);    
    }//Fim da morte de  Joao, filho 3


    if(deltaTime==90 and deltaTime!=pastDeltaTime and id1!=0 and id2!=0 and id3!=0){ //Morte de Jose
      int temp = getpid();
      cout << "Morre Jose com pid " << temp << endl;  
      kill(temp, SIGKILL);    
    }//Fim da morte de  Jose

    pastDeltaTime = deltaTime;
  
  }//Fim do while

  while(wait(NULL) != -1 || errno!= ECHILD){} //Esperar por todas as crinacas
  return 0;
}// fim da main
