# Workout Auth API

API REST desenvolvida em Java com Spring Boot para **autenticação, autorização e cadastro de treinos**, utilizando **JWT** para segurança e controle de acesso.

O projeto foi pensado como uma base sólida para aplicações fitness, controle de treinos ou estudos avançados de **Spring Security**, seguindo boas práticas de arquitetura em camadas e uso de DTOs.

---

## Funcionalidades

* Cadastro de usuários
* Autenticação com geração de token JWT
* Autorização baseada em token
* Cadastro de treinos
* Atualização de treinos
* Exclusão de treinos
* Listagem de treinos
* Geração de relatório de treinos

---

## Tecnologias Utilizadas

* Java 17+
* Spring Boot
* Spring Web
* Spring Security
* JWT (JSON Web Token)
* Lombok
* JPA / Hibernate
* Banco de dados relacional

---

## Arquitetura do Projeto

O projeto segue uma arquitetura em camadas:

* **Controller**: Exposição dos endpoints REST
* **Service**: Regras de negócio
* **DTOs**: Transferência de dados entre camadas
* **Entities**: Representação das tabelas do banco
* **Security**: Configuração de autenticação e autorização com JWT

---

## Autenticação e Autorização

A autenticação é baseada em **JWT**.

Após o login ou registro, a API retorna um token JWT válido, que deve ser enviado no header das requisições protegidas.

### Header esperado

```
Authorization: Bearer <token>
```

O token é validado verificando sua estrutura (3 partes separadas por ponto), garantindo integridade antes do processamento.

---

## Endpoints

### Autenticação

#### Registrar usuário

```
POST /register
```

**Request Body**

```json
{
  "username": "string",
  "password": "string"
}
```

**Response**

* Retorna o token JWT no body e no header `Authorization`

---

#### Login

```
POST /login
```

**Request Body**

```json
{
  "username": "string",
  "password": "string"
}
```

**Response**

* Retorna o token JWT no body e no header `Authorization`

---

### Treinos

#### Criar treino

```
POST /workout
```

**Request Body**

```json
{
  "title": "Treino A",
  "note": "Treino focado em peito",
  "type": "Musculação",
  "category": "Superior",
  "duration": "60",
  "scheduledDateTime": "2026-01-26T18:00:00",
  "exercises": [
    {
      "name": "Supino",
      "sets": 4,
      "reps": 10,
      "weight": 80.0
    }
  ]
}
```

---

#### Atualizar treino

```
PUT /workout/{id}
```

---

#### Deletar treino

```
DELETE /workout/{id}
```

---

#### Listar treinos

```
GET /workout
```

---

#### Gerar relatório

```
GET /workout/report
```

Retorna um relatório textual com informações dos treinos cadastrados.

---

## DTOs

### AuthDTO

Responsável por transportar dados de autenticação.

* `username`
* `password`

---

### WorkoutDTO

Representa um treino completo, incluindo exercícios.

Campos principais:

* id
* title
* note
* type
* category
* duration
* scheduledDateTime
* isCompleted
* exercises

---

### ExerciseDTO

Representa um exercício dentro de um treino.

Campos:

* id
* name
* sets
* reps
* weight

---

## Como Executar o Projeto

1. Clone o repositório
2. Configure o banco de dados no `application.properties`
3. Execute a aplicação

```
./mvnw spring-boot:run
```

A aplicação estará disponível em:

```
http://localhost:8080
```

---

## Observações

* Projeto ideal para estudo de **Spring Security + JWT**
* Estrutura preparada para expansão (roles, permissões, relatórios mais complexos)
* Uso de `record` para DTOs, garantindo imutabilidade e código limpo

---

## Autor

Aníbal

Projeto desenvolvido para fins de estudo e p
