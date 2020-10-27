# api-mock-transacoes
API para testes utilizando transações criadas de forma aleatória


Essa API é um serviço Rest feito em Java com o intuito de ser uma API de mock para testes. Dado um conjunto de parâmetros, será retornado uma lista de transações geradas aleatoriamente. 

### Entidade transação
A transacao é formada por 4 informações:
- **Descricao**: 
  - Descrição aleatória legível com base no arquivo [PalavrasAleatorias.txt](https://github.com/nilson-paiao/api-mock-transacoes/blob/main/src/main/resources/PalavrasAleatorias.txt)
  - Tipo: String
- **Data**: 
  - Data aleatória no formato timestamp
  - Tipo: Long
- **Valor**:
  - Valor aleatório onde os dois últimos digitos representam os centavos.
  - Tipo: Integer
- **Duplicated**:
  - Indica que esta transação está duplicada na lista retornada
  - Tipo: Boolean

### Serviço para listagem de transações
- A requisição segue o seguinte formato:
 ```sh 
 [GET] /<id>/transacoes/<ano>/<mes> 
 ```
- Os parâmetros são:
  - id
  - ano
  - mes

### Premissas
- **Requisição**:
  - O campo id deve estar entre 1.000 e 100.000.000.
- **Transação**:
  - O campo descrição deve ser legível e estar entre 10 e 60 caracteres.
  - O campo valor deve estar entre -9.999.999 e 9.999.999.
  - O campo data deve estar sempre dentro do período de mês e ano informados.
  - O campo duplicated será considerado quando houver duas ou mais transações duplicadas (descrição, data e valor iguais) em uma mesma requisição, dessa forma uma das transações deverá estar marcado como false e as demais duplicadas como true. Dentro do mesmo ano, no mínimo 3 meses deve ter transações duplicadas.
- **Serviço de listagem de transações**:
  - Cada mês haverá ao menos uma transação
  - A quantidade de transações deverá variar entre os meses
  - Dado duas requisições iguais, a listagem de transações retornada deverá ser igual também.
- Caso seja chamado um serviço inexistente deverá ser retornado uma mensagem de erro informando o ocorrido.

### Docker
O arquivo [Dockerfile](https://github.com/nilson-paiao/api-mock-transacoes/blob/main/Dockerfile) fornece o necessário para iniciar este projeto em um docker utilizando Debian como sistema operacional e Glassfish como servidor aplicacional.

**Passos para iniciar o docker**
- Acessar pasta do Dockerfile
- Criar a imagem:
```sh
docker build --tag api-mock-transacoes .
```
- Criar container a partir da imagem anterior
```sh
docker run -d -ti -p 8080:8080 api-mock-transacoes
```
- Após iniciar o container basta acessar o endereço http://localhost:8080/api-mock-transacoes-1.0/

# Exemplo de requisição/retorno

### Serviço de listagem de transações:

**Id abaixo do mínimo**
```sh
[GET] /10/transacoes/2020/10
```
```sh
Ocorreu um erro: 400 - Bad Request
O parametro "id" deve ser um numero entre 1.000 e 100.000.000!
====================
Servicos disponiveis:
Busca Transacoes: [GET] /<id>/transacoes/<ano>/<mes>
====================
Para maiores informacoes acesse: https://github.com/nilson-paiao/api-mock-transacoes
```

**Mês abaixo do mínimo**
```sh
[GET] /10000/transacoes/2020/0
```
```sh
Ocorreu um erro: 400 - Bad Request
O parametro "mes" deve ser um numero entre 1 e 12!
====================
Servicos disponiveis:
Busca Transacoes: [GET] /<id>/transacoes/<ano>/<mes>
====================
Para maiores informacoes acesse: https://github.com/nilson-paiao/api-mock-transacoes
```

**Serviço inexistente**
```sh
[GET] /paginainvalida
```
```sh
Ocorreu um erro: 404 - Not Found
Requisicao invalida
====================
Servicos disponiveis:
Busca Transacoes: [GET] /<id>/transacoes/<ano>/<mes>
====================
Para maiores informacoes acesse: https://github.com/nilson-paiao/api-mock-transacoes
```

**Página inicial**
```sh
[GET] /
```
```sh
Bem vindo a API mock de transacoes! 
====================
Servicos disponiveis:
Busca Transacoes: [GET] /<id>/transacoes/<ano>/<mes>
====================
Para maiores informacoes acesse: https://github.com/nilson-paiao/api-mock-transacoes
```

**Requisição válida**
```sh
[GET] /10000/transacoes/2020/3
```
```sh
[
      {
      "data": 1584167799776,
      "descricao": "concrete discipline",
      "duplicated": false,
      "valor": -6347407
   },
      {
      "data": 1585031799776,
      "descricao": "dictionary",
      "duplicated": false,
      "valor": -8939406
   }
]
```



