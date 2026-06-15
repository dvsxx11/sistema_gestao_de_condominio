# 🏢 Sistema de Gerenciamento de Condomínios - API

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.5-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-14-blue)
![Spring Security](https://img.shields.io/badge/Spring%20Security-JWT-success)
![Maven](https://img.shields.io/badge/Maven-3.9-red)
![License](https://img.shields.io/badge/License-MIT-yellow)

## 📖 Descrição

A **API de Gerenciamento de Condomínios** é uma aplicação desenvolvida com **Java 17** e **Spring Boot**, destinada à administração de condomínios residenciais.

O sistema permite gerenciar:

* Condomínios
* Unidades
* Moradores
* Veículos
* Encomendas
* Áreas comuns
* Reservas
* Ocorrências
* Controle financeiro
* Usuários e autenticação com JWT

Possui controle de acesso baseado em roles (**ADMIN** e **MORADOR**) utilizando Spring Security.

---

# 🚀 Tecnologias Utilizadas

| Tecnologia         | Versão | Descrição                     |
| ------------------ | ------ | ----------------------------- |
| ☕ Java             | 17     | Linguagem principal           |
| 🌱 Spring Boot     | 3.1.5  | Framework Backend             |
| 🔐 Spring Security | JWT    | Autenticação                  |
| 🗄 PostgreSQL      | 14+    | Banco de dados                |
| 📦 Spring Data JPA | -      | Persistência                  |
| 📚 Lombok          | -      | Redução de código boilerplate |
| 🔨 Maven           | 3.9+   | Gerenciamento de dependências |

---

# ✨ Funcionalidades

| Módulo                | Operações                  |
| --------------------- | -------------------------- |
| 🏢 Condomínios        | CRUD completo              |
| 🚪 Unidades           | CRUD completo              |
| 👤 Moradores          | CRUD completo              |
| 🚗 Veículos           | CRUD completo              |
| 📦 Encomendas         | Cadastro e retirada        |
| 🏊 Áreas Comuns       | CRUD completo              |
| 📅 Reservas           | Cadastro e cancelamento    |
| ⚠️ Ocorrências        | CRUD completo              |
| 💰 Financeiro         | Receitas, despesas e saldo |
| 🔐 Usuários           | Login JWT                  |
| 👮 Controle de Acesso | Roles ADMIN e MORADOR      |

---

# 📋 Pré-requisitos

* Java 17
* Maven 3.9+
* PostgreSQL
* IDE (IntelliJ IDEA ou VS Code)

---

# ⚙️ Instalação e Execução

## 1. Clonar o repositório

```bash
git clone https://github.com/seu-usuario/gestao-condominio-api.git
```

```bash
cd gestao-condominio-api
```

---

## 2. Configurar o PostgreSQL

Criar um banco:

```sql
CREATE DATABASE condominio_db;
```

---

## 3. Configurar o application.properties

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/condominio_db
spring.datasource.username=postgres
spring.datasource.password=sua_senha

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

jwt.secret=sua-chave-secreta
jwt.expiration=86400000
```

---

## 4. Instalar dependências

```bash
mvn clean install
```

---

## 5. Executar a aplicação

```bash
mvn spring-boot:run
```

Servidor:

```text
http://localhost:8080
```

---

# 🔐 Autenticação JWT

Usuário padrão:

```text
Usuário: admin
Senha: admin123
```

### Login

### POST

```text
/api/auth/login
```

Body:

```json
{
    "username": "admin",
    "password": "admin123"
}
```

Resposta:

```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

Utilize o token:

```text
Authorization: Bearer TOKEN
```

---

# 📡 Endpoints da API

## 🏢 Condomínios

| Método | Endpoint              |
| ------ | --------------------- |
| GET    | /api/condominios      |
| POST   | /api/condominios      |
| PUT    | /api/condominios/{id} |
| DELETE | /api/condominios/{id} |

---

## 🚪 Unidades

| Método | Endpoint           |
| ------ | ------------------ |
| GET    | /api/unidades      |
| POST   | /api/unidades      |
| PUT    | /api/unidades/{id} |
| DELETE | /api/unidades/{id} |

---

## 👤 Moradores

| Método | Endpoint            |
| ------ | ------------------- |
| GET    | /api/moradores      |
| POST   | /api/moradores      |
| PUT    | /api/moradores/{id} |
| DELETE | /api/moradores/{id} |

---

## 🚗 Veículos

| Método | Endpoint           |
| ------ | ------------------ |
| GET    | /api/veiculos      |
| POST   | /api/veiculos      |
| PUT    | /api/veiculos/{id} |
| DELETE | /api/veiculos/{id} |

---

## 📦 Encomendas

| Método | Endpoint                     |
| ------ | ---------------------------- |
| GET    | /api/encomendas              |
| POST   | /api/encomendas              |
| PUT    | /api/encomendas/{id}/retirar |
| DELETE | /api/encomendas/{id}         |

---

## 🏊 Áreas Comuns

| Método | Endpoint               |
| ------ | ---------------------- |
| GET    | /api/areas-comuns      |
| POST   | /api/areas-comuns      |
| PUT    | /api/areas-comuns/{id} |
| DELETE | /api/areas-comuns/{id} |

---

## 📅 Reservas

| Método | Endpoint                    |
| ------ | --------------------------- |
| GET    | /api/reservas               |
| POST   | /api/reservas               |
| PUT    | /api/reservas/{id}/cancelar |
| DELETE | /api/reservas/{id}          |

---

## ⚠️ Ocorrências

| Método | Endpoint              |
| ------ | --------------------- |
| GET    | /api/ocorrencias      |
| POST   | /api/ocorrencias      |
| PUT    | /api/ocorrencias/{id} |
| DELETE | /api/ocorrencias/{id} |

---

## 💰 Financeiro

| Método | Endpoint                 |
| ------ | ------------------------ |
| GET    | /api/financeiro/despesas |
| POST   | /api/financeiro/despesas |
| GET    | /api/financeiro/receitas |
| POST   | /api/financeiro/receitas |
| GET    | /api/financeiro/saldo    |

---

# 💻 Frontend

Tecnologias utilizadas:

* HTML5
* CSS3
* Bootstrap 5
* JavaScript Vanilla
* Font Awesome

Principais páginas:

| Página           | Descrição             |
| ---------------- | --------------------- |
| index.html       | Login                 |
| dashboard.html   | Dashboard principal   |
| condominios.html | Gestão de condomínios |
| unidades.html    | Gestão de unidades    |
| moradores.html   | Gestão de moradores   |
| veiculos.html    | Gestão de veículos    |
| encomendas.html  | Gestão de encomendas  |
| reservas.html    | Reservas              |
| ocorrencias.html | Ocorrências           |
| financeiro.html  | Controle financeiro   |

---

# 👮 Controle de Permissões

| Recurso                | ADMIN | MORADOR |
| ---------------------- | ----- | ------- |
| Visualizar dados       | ✅     | ✅       |
| Cadastrar              | ✅     | ❌       |
| Editar                 | ✅     | ❌       |
| Excluir                | ✅     | ❌       |
| Reservar áreas comuns  | ✅     | ✅       |
| Visualizar ocorrências | ✅     | ✅       |
| Controle financeiro    | ✅     | ❌       |

---

# 🗄 Estrutura do Banco de Dados

O sistema possui 11 tabelas:

```text
condominios
unidades
moradores
usuarios
veiculos
areas_comuns
reservas_areas_comuns
encomendas
ocorrencias
despesas
receitas
```

---

# 🔧 Solução de Problemas

### Erro de conexão com PostgreSQL

Verifique:

```properties
spring.datasource.url
spring.datasource.username
spring.datasource.password
```

---

### Porta 8080 em uso

Alterar:

```properties
server.port=8081
```

---

### Erro no Maven

Executar:

```bash
mvn clean install
```

ou

```bash
mvn dependency:resolve
```

---

### Token JWT inválido

Faça login novamente:

```text
POST /api/auth/login
```

---

# 📌 Exemplos de Requisições

## Login

```bash
curl -X POST http://localhost:8080/api/auth/login \
-H "Content-Type: application/json" \
-d '{
"username":"admin",
"password":"admin123"
}'
```

---

## Buscar condomínios

```bash
curl -X GET http://localhost:8080/api/condominios \
-H "Authorization: Bearer TOKEN"
```

---

## Cadastrar condomínio

```bash
curl -X POST http://localhost:8080/api/condominios \
-H "Content-Type: application/json" \
-H "Authorization: Bearer TOKEN" \
-d '{
"nome":"Condomínio Alpha",
"endereco":"Rua Principal, 100"
}'
```

---

## Consultar saldo financeiro

```bash
curl -X GET http://localhost:8080/api/financeiro/saldo \
-H "Authorization: Bearer TOKEN"
```

---

# 📜 Licença

Este projeto está licenciado sob a licença **MIT**.

---

# 👨‍💻 Autor

### Davi

GitHub:

```text
https://github.com/dvsxx11
```

---


