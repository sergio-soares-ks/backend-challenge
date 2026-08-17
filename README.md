# password-validator-api

API REST em Java/Spring Boot que valida se uma senha atende a um conjunto de
regras de negócio, expostas através de um único endpoint HTTP.

## Como executar

Pré-requisitos: JDK 17+ e Maven 3.9+ (o projeto usa apenas o `mvn` da máquina;
não há wrapper commitado).

```bash
# rodar a suíte de testes (56 testes: unitários + integração)
mvn test

# subir a aplicação em http://localhost:8080
mvn spring-boot:run

# alternativa: empacotar e rodar o jar
mvn package
java -jar target/password-validator-api-0.1.0.jar
```

### Chamando a API

```bash
curl -X POST http://localhost:8080/api/v1/password-validations \
  -H "Content-Type: application/json" \
  -d '{"password":"AbTp9!fok"}'
# {"valid":true}
```

`POST` foi escolhido em vez de `GET` propositalmente: a senha viaja no corpo
da requisição, nunca em query string, para que não fique registrada em logs
de acesso, histórico do navegador ou proxies intermediários.

## Regras de validação

Uma senha é válida quando, simultaneamente:

1. Possui 9 ou mais caracteres;
2. Contém ao menos 1 dígito;
3. Contém ao menos 1 letra minúscula;
4. Contém ao menos 1 letra maiúscula;
5. Contém ao menos 1 caractere especial do conjunto `!@#$%^&*()-+`;
6. Todos os caracteres são únicos (sem repetição);
7. Não contém nenhum caractere fora dos conjuntos acima (em particular,
   espaços em branco não são aceitos).

## Decisões de design

### Uma regra = uma classe (Strategy + Composite implícito)

Cada critério de validação é uma implementação isolada de
`PasswordRule` (`domain/rule/*Rule.java`), com um único método
`isSatisfiedBy(String password)`. O `RuleBasedPasswordValidator`
(`domain/RuleBasedPasswordValidator.java`) apenas recebe a lista completa de
regras — injetada automaticamente pelo Spring, que coleta todo bean
`PasswordRule` do contexto — e considera a senha válida se **todas**
forem satisfeitas.

Isso foi escolhido em vez de um único método com vários `if`s porque:

- **SRP**: cada regra tem um único motivo para mudar (ex.: alterar o
  tamanho mínimo não toca a regra de caracteres especiais).
- **OCP**: adicionar uma nova regra de negócio (ex.: "não pode conter o
  nome do usuário") significa criar uma nova classe `@Component` — nenhuma
  classe existente precisa ser alterada.
- **Testabilidade**: cada regra é testada isoladamente, com casos de borda
  próprios, sem depender das demais.
- **DIP**: o controller depende da abstração `PasswordValidator`, não de
  `RuleBasedPasswordValidator` nem das regras concretas.

### `AllowedCharactersRule` como guarda explícita

O enunciado destaca que espaços em branco não devem ser considerados
caracteres válidos. Em vez de deixar isso como um efeito colateral de outra
regra (ex.: a senha só "por acaso" falhar por não ter caractere especial),
existe uma regra dedicada que restringe cada caractere da senha a letras,
dígitos ou ao conjunto especial aceito. Isso deixa a intenção explícita no
código e cobre qualquer símbolo fora do conjunto (não só espaço).

### Estrutura de pacotes

```
com.validaccess.passwordvalidator
├── api                     camada HTTP (controller, DTOs, tratamento de erro)
│   ├── dto
│   └── error
└── domain                  regras de negócio, sem nenhuma dependência do Spring MVC
    └── rule
```

O pacote `domain` não importa nada de `api`, então as regras de validação
poderiam ser reaproveitadas por qualquer outra interface (CLI, mensageria,
etc.) sem alteração.

## Premissas assumidas

O enunciado não deixa 100% explícitas algumas decisões; seguem as premissas
adotadas e o racional de cada uma:

- **`senha == null` é inválida.** O enunciado só define o comportamento para
  `""`, mas tratar `null` como inválido (em vez de lançar erro) é consistente
  com o exemplo `IsValid("") // false` e evita expor uma exceção 500 para um
  campo `password` simplesmente ausente no JSON.
- **A checagem de caracteres repetidos é *case-sensitive*.** `'A'` e `'a'`
  contam como caracteres diferentes. Essa é a leitura mais natural dado que
  as demais regras (maiúscula/minúscula) também tratam os dois casos como
  categorias distintas; os exemplos do enunciado não desambiguam esse ponto
  diretamente.
- **O conjunto especial é a lista literal de 12 caracteres**
  `! @ # $ % ^ & * ( ) - +`, e não um intervalo `-` a `+` na tabela ASCII.
- **JSON malformado no corpo da requisição retorna `400 Bad Request`** com
  uma mensagem curta, em vez do corpo padrão de erro do Spring — é um erro de
  transporte (a requisição não pôde nem ser interpretada), diferente de uma
  senha inválida, que é `200 OK` com `"valid": false`.

## Testes

- **Unitários por regra** (`domain/rule/*Test.java`): cada `PasswordRule` é
  testada isoladamente, incluindo os limites de cada critério.
- **Unitário do validador** (`RuleBasedPasswordValidatorTest`): compõe todas
  as regras reais e reproduz exatamente os exemplos do enunciado, incluindo
  `null`.
- **Contrato HTTP** (`PasswordControllerTest`, com `MockMvc` e o validador
  mockado): garante que o controller serializa/desserializa corretamente e
  trata corpo ausente/malformado, sem depender da lógica de negócio.
- **Integração ponta a ponta** (`PasswordValidationApiIntegrationTest`, com
  `@SpringBootTest(webEnvironment = RANDOM_PORT)` + `TestRestTemplate`):
  sobe o contexto Spring real e faz chamadas HTTP reais contra os mesmos
  exemplos do enunciado, validando controller + validador + regras juntos.

```bash
mvn test
```

## Stack

Java 17, Spring Boot 3.3.4 (`spring-boot-starter-web`), JUnit 5, AssertJ e
Mockito (via `spring-boot-starter-test`).
