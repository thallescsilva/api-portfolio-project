# api-portfolio-project
API para gerenciamento de dados de portfolio de projetos de uma empresa

## Execute o projeto localmente

API Portfolio Project é uma [Spring Boot](https://spring.io/guides/gs/spring-boot) aplicação criada usando [Maven](https://spring.io/guides/gs/maven/). Poderá construir um ficheiro jar e executá- lo a partir da linha de comandos (deverá funcionar tão bem com o Java 17 ou mais recente):

```bash
git clone https://github.com/thallescsilva/api-portfolio-project.git
cd api-portfolio-project
./mvnw package
java -jar target/*.jar
```

Você pode executá-lo a partir do Maven diretamente usando o plugin Spring Boot Maven. Se o fizer, irá detectar as alterações que fizer no projecto imediatamente (as alterações aos ficheiros de código Java também requerem uma compilação - a maioria das pessoas usa um IDE para isto):

```bash
./mvnw spring-boot:run
```

## Configuração de Banco de Dados

Em sua configuração padrão, o Portfolio Project usa um banco de dados na memória (H2) que
é preenchido no arranque com dados. A consola h2 está exposta em `http:// localhost:8080/h2- console`,
e é possível inspecionar o conteúdo do banco de dados usando o `jdbc:h2:mem:<uuid>` URL. O UUID é impresso na inicialização do console.

## Trabalhando com o Portfolio Project em seu IDE

### Pré-requisitos

Os seguintes itens deverão ser instalados no seu sistema:

- Java 17 ou mais recente (JDK completo, não um JRE)
- [Git command line tool](https://help.github.com/articles/set-up-git)
- Seu IDE preferido
    - Eclipse com o 'plugin' M2e. Nota: quando o M2e estiver disponível, existe um ícone m2 na janela `Ajuda -> Sobre`. Se o M2e estiver
      não há, siga o processo de instalação [here](https://www.eclipse.org/m2e/)
    - [Spring Tools Suite](https://spring.io/tools) (STS)
    - [IntelliJ IDEA](https://www.jetbrains.com/idea/)
    - [VS Code](https://code.visualstudio.com)

### Passo a Passo

1. Na execução da linha de comandos:

    ```bash
    git clone https://github.com/spring-projects/spring-petclinic.git
    ```

1. No Eclipse ou STS:

   Abra o projeto através de `File -> Import -> Maven -> Existing Maven project`, em seguida, selecione o diretório raiz do repositório clonado.


1. No IntelliJ IDEA:

   No menu principal, escolha `Ficheiro -> Abrir` e seleccione a Petclinic [pom.xml](pom.xml). Carregue no botão `Abrir`.

    - Uma configuração de execução chamada `ApiProjectPortfolioApplication` deveria ter sido criada para você se você estiver usando uma versão Ultimate recente. Caso contrário, execute o aplicativo clicando com o botão direito no `ApiProjectPortfolioApplication` main class e escolhendo `Run 'ApiProjectPortfolioApplication'`.

1. Existe uma collection anexada com o projeto com todos os endpoints.