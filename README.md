# MatchUp API (Estilo "Appito") ⚽

O MatchUp é uma API RESTful desenvolvida em Spring Boot (Java 21) projetada para facilitar a administração, marcação e controle logístico de partidas de futebol (ou outros esportes). Com uma arquitetura segura, o projeto consolida todo o ciclo do usuário — desde o cadastro, passando por autenticação segura via **Tokens JWT**, até a gestão total dos Jogos de forma que "apenas o dono da partida" consiga alterá-la ou cancelá-la.

## 🚀 Tecnologias Utilizadas
- **Java 21 + Spring Boot 3**
- **Spring Security + Auth0 JWT** (Proteção de endpoints e autenticação Stateless)
- **PostgreSQL** (Banco de dados relacional oficial, persistido via Docker)
- **Spring Data JPA & Flyway** (Mapeamento ORM e Migrações eficientes)
- **Swagger / OpenAPI** (Documentação padronizada e interativa)
- **Lombok** (Produtividade de código sem Boilerplate)

## 📦 Como rodar o projeto localmente

### 1. Ferramentas Necessárias
- [Docker](https://www.docker.com/) e Docker-Compose instalados nativamente na máquina.
- Seu JDK / JRE do Java 21 ou superior.

### 2. Configurando o Ambiente
Crie ou renomeie o arquivo `.env.exemple` contido na raiz para simplesmente **`.env`**. Preencha nele as credenciais padrão relativas ao provisionamento de banco de dados do contêiner e o segredo criptográfico:
```env
POSTGRES_DB=matchup
POSTGRES_USER=postgres
POSTGRES_PASSWORD=password
POSTGRES_PORT=5433
SECRET=a-sua-chave-secreta-gigante-do-jwt-usada-pelo-servidor
```

### 3. Subindo o Banco de Dados
A infraestrutura real de persistência do PostgreSQL está "dockerizada". Para inicializá-lo em segundo plano ligando a ponte da porta selecionada, no Terminal execute:
```bash
docker-compose up -d
```

### 4. Ligando a Aplicação Spring
Basta rodar a sua classe oficial `Application.java` dentro da sua IDE preferida. Como padrão utilitário, caso deseje compilar e colocar no ar pelo terminal:
```bash
# Usuários Windows (Powershell)
.\mvnw.cmd clean spring-boot:run
```

## 📘 Documentação Completa (Swagger API)
Para facilitar seu entendimento e brincar com criação de partidas na hora sem a necessidade do Postman, deixamos todo o Swagger interativo conectado e mastigado. Com a API online, abra no navegador:

👉 **[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)**

### Restrições e Autorização Segura
Sendo um ambiente altamente blindado por rotas no padrão Spring Security, para enviar requisições seguras para a arquitetura de Partidas Internas (`/api/games`):
1. **(POST)** Crie sua persona em `/api/auth/register` emitindo suas senhas originais.
2. **(POST)** Dispare um login real em `/api/auth/login`. O JSON Retornado possuirá sua *pulseira VIP*: O **Token**.
3. Na tela principal do site do referenciado do Swagger, procure pelo ícone global **"Authorize 🔓"** e cole somente a assinatura extensa do JWT lá, engatilhando os disparos nativos autorizados!

## 🧱 Padrão Arquitetural Inserido
O Repositório preza de uma estrutura moderna com `Camadas Controladoras` totalmente "magras", focadas estritamente em lidar o I/O HTTP, enquanto sub-módulos `Service` encorpam a pesada regra de negócios. Todas as validações visam consistências via chaves estrangeiras (**UUIDs**) e protegem o Backend (Autorização de Nível de Objeto).
