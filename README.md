# shopping-cart
Uma simples API de carrinho de compras, desenvolvida como um desafio

# Escolhas de modelagem
Esta API foi desenvolvida utilizando SpringBoot e MongoDB

Algumas escolhas de funcionamento do carrinho foram tomadas arbitrariamente. Como para o controle de estoque dos produtos, a quantidade de um produto em estoque será descontada apenas quando a compra é finalizada. Desta forma, dando preferência a clientes que finalizem primeiro a compra e não para os clientes que estão com o produto apenas no carrinho. Outra escolha foi de apenas um cupom poder ser adicionado no carrinho por vez. E os carrinhos são diretamente relacionados aos usuários, de forma que a busca por carrinho se dá pelo id do usuário.

Foram adicionados CRUD's básicos para controle de usuários, produtos e cupons. Deixando a aplicação principal do carrinho na rota */cart* .

Para os testes unitários foi utilizado o JUnit, dando cobertura sobre controller e service da aplicação principal (cart). Para executar os testes unitários é preciso abrir o projeto em uma IDE de sua escolha (IDE utilizada para o desenvolvimento foi o STS4, outra IDE recomendada é o IntelliJ), e rodar o projeto como JUnit Test.

# Como executar
O projeto utiliza de docker para facilitar a execução do mesmo. Com isso, o único pré-requisito para a execução do projeto em uma nova máquina, é que esta máquina possua o docker instalado.

A máquina precisa estar com as portas 8080, 8081 e 9060 disponíveis para que o projeto possa ser executado.

**Passo 1**
Fazer o download do zip através do github, e extrair o projeto

**Passo 2**
Abrir um terminal dentro da pasta raiz do projeto */shopping-cart*

**Passo 3**
Executar no terminal o comando: `docker-compose up`

A API agora estará executando na porta 9060.
O banco MongoDB estará executando na porta 8080.
E a interface de banco Mongo Express estará executando na porta 8081.

Para começar a utilizá-lo, acesse a url *http://localhost:9060/shop/swagger-ui.html#/*

Além disso, você pode verificar a utilização do MongoDB através da url *http://localhost:8081*

# Como utilizar
Uma vez com o projeto executando, pode ser feita sua utilização.

Através do Swagger estão disponibilizadas as rotas da API, contendo CRUD's básicos para o controle dos produtos (*product-controller*), usuários (*user-controller*) e cupons (*coupon-controller*), e a aplicação principal de carrinho (*cart-controller*). 

Além da utilização via Swagger, no repositório existe uma collection do Postman que pode ser importada e utilizada.

Como recomendação para utilização da API, primeiro utilize o *user-controller* para adicionar um usuário novo ao sistema, em seguida utilize o *product-controller* para inserir um novo produto. Com estes dois elementos, o carrinho pode ser criado, utilizando a rota */cart/product/{idUser}/{idProduct}/{quantity}*. Para adicionar um cupom, é preciso primeiro criá-lo utilizando o *coupon-controller*, e em seguida adicionar o cupom ao carrinho.
