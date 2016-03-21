------------------------------------------------------------------------------------------------------
Url Shortening
------------------------------------------------------------------------------------------------------
Conjunto de serviços para se criar urls encurtadas. 

------------------------------------------------------------------------------------------------------
IMPORTANTE: tive problemas de acesso com a minha instância ubuntu da Amazon (problemas com a key) e não consegui validar os comandos em nenhuma máquina Linux, portanto pode ter alguma divergência e/ou passos incompletos. 
------------------------------------------------------------------------------------------------------

------------------------------------------------------------------------------------------------------
Pré-requisitos 
------------------------------------------------------------------------------------------------------
1. Descompactar o zip do projeto na pasta /usr/local/urlshortening

2. Instalação do Java
- Download da versão 8 73 (jdk-8u73-linux-x64.tar.gz) http://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html
- Descompacte o conteúdo na estrutura de pasta: /usr/lib/jvm
- Adicione a variável JAVA_HOME ao path
- vim ~/.bashrc 
- export JAVA_HOME=/usr/ lib/jvm /jdk1.8.0_73  
  export PATH=:/usr/ lib/jvm / jdk1.8.0_73/bin:$PATH  
- valide que deu tudo certo: java -version

3. Intalação do Tomcat 7
- wget http://mirror.cc.columbia.edu/pub/software/apache/tomcat/tomcat-7/v7.0.54/bin/apache-tomcat-7.0.54.tar.gz 
- tar xvzf apache-tomcat-7.0.54.tar.gz
- mv apache-tomcat-7.0.54 /usr/lib /tomcat
- vim ~/.bashrc
- export CATALINA_HOME=/usr/lib /tomcat
- . ~/.bashrc
- valide que deu tudo certo: echo $CATALINA_HOME =]

4. Instalação do Banco de Dados HyperSQL 2.3.3
- cd /usr/lib/ 
- wget https://sourceforge.net/projects/hsqldb/files/latest/download/hsqldb-2.3.3.zip
- unzip /usr/lib/hsqldb-2.3.3.zip
Importante: garanta que a extração do zip deixou o path da seguinte forma: 
/usr/lib /hsqldb/lib (pois o script de start da aplicação irá considerar este path).
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
- Na tela que vai abrir, ir até menu > file>Open Script... , ir até a pasta que descompactamos os arquivos do projeto (/usr/local/urlshortening)  e selecionar o create_table.sql 
- Ele carregou o script na tela então é só rodar instrução por instrução.
- depois disso efetuar o comando shutdown;

(momento mimimi) Ok, eu sei que não ficou muito bonito, vocês devem estar me xingando, eu percebi que deveria subido o banco pela aplicação e rodado tudo pelo java, mas não tenho mais tempo...  ;[ 

------------------------------------------------------------------------------------------------------
Build da aplicação
------------------------------------------------------------------------------------------------------
Não tem necessidade de rodar o build para executar a aplicação, porém caso haja necessidade: 
- ir na pasta raiz do projeto (usr/local/urlshortening/projetc/urlshortening  onde temos o pom.xml)
- mvn clean install

------------------------------------------------------------------------------------------------------
Install e Start
------------------------------------------------------------------------------------------------------
- Acredito que o script vá funcionar, porém para instalar a aplicação, basta copiar o .war para a pasta webapp do tomcat.
- Para iniciar temos que subir o banco: 
- cd /usr/lib/hsqldb/lib
- java -cp hsqldb.jar org.hsqldb.server.Server --database.0 file:urlshortening --dbname.0
- Subir o tomcat
	- /usr/lib/tomcat/bin/catalina.sh

------------------------------------------------------------------------------------------------------
Backlog Conhecido
------------------------------------------------------------------------------------------------------
- O banco de dados está acoplado a mesma instância da aplicação (certo, eu perdi ponto...eu sei)
- Testes unitários 
- Criar um processo de instalação e configuração automático
- Navegador está cacheando algumas chamadas rest
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
- jsonobject [Contém os objetos que representam a resposta json]
- persistence [Classes que fazem manipulação do banco de dados]
- service [Classes que contém a lógica de negócio e fazem requisições para a camada de persistência]
- rest [Devido a estrutura de EndPoints dada, criei apenas 1 classe com todos os serviços expostos]
