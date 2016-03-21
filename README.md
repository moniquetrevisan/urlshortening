------------------------------------------------------------------------------------------------------
Url Shortening
------------------------------------------------------------------------------------------------------
Conjunto de servi�os para se criar urls encurtadas. 

------------------------------------------------------------------------------------------------------
IMPORTANTE: tive problemas de acesso com a minha inst�ncia ubuntu da Amazon (problemas com a key) e n�o consegui validar os comandos em nenhuma m�quina Linux, portanto pode ter alguma diverg�ncia e/ou passos incompletos. 
------------------------------------------------------------------------------------------------------

------------------------------------------------------------------------------------------------------
Pr�-requisitos 
------------------------------------------------------------------------------------------------------
1. Descompactar o zip do projeto na pasta /usr/local/urlshortening

2. Instala��o do Java
- Download da vers�o 8 73 (jdk-8u73-linux-x64.tar.gz) http://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html
- Descompacte o conte�do na estrutura de pasta: /usr/lib/jvm
- Adicione a vari�vel JAVA_HOME ao path
- vim ~/.bashrc 
- export JAVA_HOME=/usr/ lib/jvm /jdk1.8.0_73  
  export PATH=:/usr/ lib/jvm / jdk1.8.0_73/bin:$PATH  
- valide que deu tudo certo: java -version

3. Intala��o do Tomcat 7
- wget http://mirror.cc.columbia.edu/pub/software/apache/tomcat/tomcat-7/v7.0.54/bin/apache-tomcat-7.0.54.tar.gz 
- tar xvzf apache-tomcat-7.0.54.tar.gz
- mv apache-tomcat-7.0.54 /usr/lib /tomcat
- vim ~/.bashrc
- export CATALINA_HOME=/usr/lib /tomcat
- . ~/.bashrc
- valide que deu tudo certo: echo $CATALINA_HOME =]

4. Instala��o do Banco de Dados HyperSQL 2.3.3
- cd /usr/lib/ 
- wget https://sourceforge.net/projects/hsqldb/files/latest/download/hsqldb-2.3.3.zip
- unzip /usr/lib/hsqldb-2.3.3.zip
Importante: garanta que a extra��o do zip deixou o path da seguinte forma: 
/usr/lib /hsqldb/lib (pois o script de start da aplica��o ir� considerar este path).
- vim ~/.bashrc
- export CLASSPATH; CLASSPATH=/usr/lib/hsqldb/lib/hsqldb.jar	
- cd /usr/lib/hsqldb/lib
- java -cp hsqldb.jar org.hsqldb.server.Server --database.0 file:urlshortening --dbname.0 urlshortening
- abrir outro terminal
- java -cp hsqldb.jar org.hsqldb.util.DatabaseManager
- Setting Name: urlshortening
- type: HSQL Database Engine Server
- Driver: org.hsqldb.jdbcDriver
- url: jdbc:hsqldb:hsql://localhost/urlshortening
- user: AS
- password: (vazio)
- ok
- Na tela que vai abrir, ir at� menu > file>Open Script... , ir at� a pasta que descompactamos os arquivos do projeto (/usr/local/urlshortening)  e selecionar o create_table.sql 
- Ele carregou o script na tela ent�o � s� rodar instru��o por instru��o.
- depois disso efetuar o comando shutdown;

(momento mimimi) Ok, eu sei que n�o ficou muito bonito, voc�s devem estar me xingando, eu percebi que deveria subido o banco pela aplica��o e rodado tudo pelo java, mas n�o tenho mais tempo...  ;[ 

------------------------------------------------------------------------------------------------------
Build da aplica��o
------------------------------------------------------------------------------------------------------
N�o tem necessidade de rodar o build para executar a aplica��o, por�m caso haja necessidade: 
- ir na pasta raiz do projeto (usr/local/urlshortening/projetc/urlshortening  onde temos o pom.xml)
- mvn clean install

------------------------------------------------------------------------------------------------------
Install e Start
------------------------------------------------------------------------------------------------------
- Acredito que o script v� funcionar, por�m para instalar a aplica��o, basta copiar o .war para a pasta webapp do tomcat.
- Para iniciar temos que subir o banco: 
- cd /usr/lib/hsqldb/lib
- java -cp hsqldb.jar org.hsqldb.server.Server --database.0 file:urlshortening --dbname.0
- Subir o tomcat
	- /usr/lib/tomcat/bin/catalina.sh

------------------------------------------------------------------------------------------------------
Backlog Conhecido
------------------------------------------------------------------------------------------------------
- O banco de dados est� acoplado a mesma inst�ncia da aplica��o (certo, eu perdi ponto...eu sei)
- Testes unit�rios 
- Criar um processo de instala��o e configura��o autom�tico
- Navegador est� cacheando algumas chamadas rest
- Melhorar tratamento de erro

------------------------------------------------------------------------------------------------------
Maven Dependencies
------------------------------------------------------------------------------------------------------
- org.hsqldb.2.3.3
- com.google.code.gson.2.6.2
- com.google.guava.18.0
- javax.annotation-api.1.2
- javax.servlet-api.3.1.0
- javaee-web-api.7.0
- jersey-servlet.1.19.1
- jersey-json.1.19.1
- jersey-server.1.19.1

------------------------------------------------------------------------------------------------------
Estrutura do Projeto
------------------------------------------------------------------------------------------------------
Estrutura de Pacotes
- com.moniquetrevisan.urlshortening [Pacote Principal]
- jsonobject [Cont�m os objetos que representam a resposta json]
- persistence [Classes que fazem manipula��o do banco de dados]
- service [Classes que cont�m a l�gica de neg�cio e fazem requisi��es para a camada de persist�ncia]
- rest [Devido a estrutura de EndPoints dada, criei apenas 1 classe com todos os servi�os expostos]
