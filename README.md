# Pokedex

O aplicativo é, como seu nome diz, uma pokedex, que permite consultar todos os pokemons existentes!

Ao abrir o aplicativo o usuário deve escolher entre realizar uma pesquisa por nome ou id do pokemon em um search dialog, ou descobrir os pokemons na lista.

A pesquisa ocorre no cache do aplicativo, então é necessário que o usuário descubra os pokemons primeiro!

A tela principal da aplicação disponibiliza uma lista com todos os pokemons, onde seu carregamento é feito por endless scroll.
Ao clicar em algum pokemon, a tela de detalhes é aberta, e disponibiliza várias abas com About, Stats, Evolution Chain e Others (Types e Abilities), além de um carousel com imagens do pokemon (basta deslizar para o lado para mudar de imagem).

Ao clicar em alguma descrição, rapidamente aparecerá sua descrição.
Ao clicar em um tipo, aparecerá uma lista com todos os pokemons do mesmo tipo.
Ao clicar na descrição do pokemon, se abrirá uma janela com a sua descrição completa.

## Algumas screenshots
![first_screen](https://github.com/jsouza678/pokedex/blob/master/screenshots/resized/first_screen.png) 
![menu](https://github.com/jsouza678/pokedex/blob/master/screenshots/resized/menu.png) 
![details](https://github.com/jsouza678/pokedex/blob/master/screenshots/resized/details_root.png)
![details_stats](https://github.com/jsouza678/pokedex/blob/master/screenshots/resized/details_stats.png)
![details_other_types](https://github.com/jsouza678/pokedex/blob/master/screenshots/resized/details_other_types.png)
![error_search](https://github.com/jsouza678/pokedex/blob/master/screenshots/resized/error_.png)

## Ambiente de instalação
* 1: Instale o Android Studio;
* 2: Abra a aplicação;
* 3: Sincronize o projeto;
* 4: Rode o aplicativo em um simulador ou em um device externo.

## API
<p>A API utilizada é a POKE.API V2.</p>
(https://pokeapi.co/)

## API de Imagens
<p>A API de imagens é a Pokeres!</p>
(https://pokeres.bastionbot.org/)

## Automação
Ktlint - a task valida se o padrão do código está de acordo com o lint. 
O `./gradlew ktlint` realiza a verificação de todos os componentes do projeto, e retorna o resultado.

KtlintFormat - esta tarefa modifica o código para que ele siga o padrão do lint. 
O `./gradlew ktlintFormat` roda uma rotina que formata o código de acordo com o máximo que o lint pode fazer de modificações para que o código esteja no seu padrão.

 ## Arquitetura
 A aplicação busca o desacoplamento e a escalabilidade em sua arquitetura, fazendo uso do Clean Architecture e do MVVM.

 ## Principais dependências

**Coroutines** - _lidando com threads e assincronismo_
 <p>Abordagem sugerida pela Google e com um bom funcionamento com o Live Data, faz bom uso das threads e da Thread Pool do dispositivo, melhorando a performance da aplicação.</p>

**Room** - _persistência de dados_
 <p>Camada de abstração sobre o SQLite, o Room é um facilitador para persistir dados no banco do aparelho. 
 É importante ressaltar que a utilização do Room e Coroutines necessita de uma forma de verificar as queries do banco de dados, já que elas devem ser feitas de forma async.
 O LiveData foi utilizado neste caso para tornar as consultas reativas, e permitirem a execução fora da main thread. (Dispatchers.Main, e sem o problemático .allowMainThreadQueries).

**Retrofit** - _requisições HTTP_
 <p>Retrofit é a biblioteca mais difundida por encapsular e lidar com requisições HTTP, além de possuir uma fácil implementação.</p>
 
 **Material Design** - _layout intuitivo e clean_
 <p>O aplicativo segue os padrões do MaterialDesign para uma melhor experiência do usuário em sua utilização.</p>
 
 ** Em Andamento **
 Injeção de Dependência com Koin.
 
## O que eu gostaria de ter feito

 * _criado testes unitários;_
 
 * _criado testes de ui;_
 
 * _implementado injeção de dependência_
 
 * _implementado animações e transições entre as telas da aplicação;_  
 
 * _implementado search na API por nome e ID_
