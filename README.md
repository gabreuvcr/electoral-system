## Planejamento

![Planejamento](/planejamento.png)


##  Decisões de projeto 
- Adicionado padrão Singleton na classe `ElectionController` por existir apenas uma eleição por execução
- Adicionado padrão Repository. Agora as classes apenas possuem um contrato via interface das funções disponíveis, fazendo com que as implementações de persistência de dados sejam facilmente alteradas
- Adicionado arquitetura em 3 camadas: Há a classe `UrnaCli` que atua como a nossa View. A lógica está toda na pasta `src/domain`, sendo a classe `ElectionControler` a principal responsável. A persistência de dados está na pasta `src/data` e possui várias classes seguindo o padrão Repository.
  
## Adições na implementação do sistema existente
- Correção de indentações para 4 espaços
- Adição de chaves " { } " em estruturas condicionais
- Simplificação de verificações para redução de estruturas condicionais
- Adicionado Singleton
- Separação dos arquivos em pastas no padrão 3 camadas.
- Início do sistema pela classe Application, que possui apenas o método main e instancia as classes concretas e injeta as dependências
- Corrigido bug onde votos brancos para Deputados Federais estavam contando como nulo.
- Todas pré-carregamentos (Presidentes, Deputados, Eleitores) agora são realizados nos seus respectivos repositorios
- Removido padrão Builder pré-existente da classe ElectionController (não fazia muito sentido)
- Todos repositorios estão na classe ElectionController. Agora a Urna (que precisa ser o "frontend") apenas utiliza os serviços fornecidos por ElectionController e pelos modelos.
- Resultado da eleição agora é gerado por uma classe específica nomeada ElectionResult invocada por ElectionController.
- Removido da classe Voter a responsabilidade de votar chamando a propria eleição (?). Agora TODOS os votos sao armazenados pela classe IVoteRepository e orquestrados por ElectionController.
- Removido da classe TSEEmployee a responsabilidade de adicionar/remover candidatos chamando a propria eleição (?). Agora a adição e remoção é controlada por ElectionController.
- Removido da classe CertifiedProfessional a responsabilidade de iniciar/finalizar uma eleicoa chamando a propria eleição (?). Agora o inicio e fim é controlado por ElectionController.
- Adicionado metodo login em TSEProfessional.
- ElectionController possui um metodo para cada tipo de voto, que sao utilizados pela UrnaCli.
- Se para um cargo não houver votos válidos, nenhum candidato será eleito.

## Produtos da LPS 
- Presidente/Deputado Federal com Exibição de Candidatos
- Presidente/Deputado Federal sem Exibição de Candidatos
- Governador/Deputador Estadual com Exibição de Candidatos
- Governador/Deputador Estadual sem Exibição de Candidatos
  
## Dependencias

- [Java Developer Kit (JDK) 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- Make

## Comandos make

- `make` ou `make full`: Buildar + Executar
- `make build`: Buildar
- `make run`: Executar
- `make clean`: Limpa os arquivos `.class` gerados no build

## Como rodar

- Na root do repositório use o comando `make` para buildar e executar o programa

## Como utilizar

OBS:

- O sistema já vem inicializado com 2 candidatos a presidente e 3 a deputado federal
- O sistema já vem com os dois gestores (de sessão e de candidaturas)
- O sistema já vem com todos os eleitores possíveis para utilizá-los basta checar o arquivo `voterLoad.txt`

No menu inicial para gerenciar candidatos e eleição siga pela opção 2:

- User: `emp` , Password: `12345` -> Cadastro e remoção de candidatos da eleição
- User: `cert` , Password: `54321` -> Inicialização/finalização da eleição (liberar pra poder votar) e mostrar o resultado ao final da eleição.

Além da senha de usuário é necessário a senha da eleição para completar operações relacionadas a gestão da eleição ou candidatos. Essa senha é a palavra `password`

Para votar também existe um eleitor com o título de eleitor nº 123456789012 que pode votar nos candidatos pré-cadastrados

## Execução teste

Para uma execução teste podemos seguir o seguinte passo:

- Ao iniciar a aplicação selecionar a opção 2 e logar com o user `cert`
- Escolher a opção 1 e inserir a senha da urna (`password`) para iniciar a votação
- Escolher a opção 0 para voltar ao menu inicial
- Escolher votar (opção 1) e inserir o nº `123456789012` do eleitor de teste
- Selecionar sim e votar respectivamente `123` , `12345` e `br`
- Escolher votar (opção 1) e inserir o nº `268888719264` (outro eleitor de teste)
- Selecionar sim e votar respectivamente `123` , `54321` e `12345`
- Escolher votar (opção 1) e inserir o nº `638991919941` (outro eleitor de teste)
- Selecionar sim e votar respectivamente `000` , `12345` e `00000`
- Escolher votar (opção 1) e inserir o nº `965575671024` (outro eleitor de teste)
- Selecionar sim e votar respectivamente `123` , `12345` e `00000`
- No menu inicial, selecionar a opção 2 e logar com o user `cert`
- Escolher a opção 2 e inserir a senha da urna (`password`) para encerrar a votação
- Escolher a opção 3 e inserir a senha da urna (`password`) para mostrar o resultado final da votação
- Escolher a opção 0 duas vezes para encerrar a aplicação
