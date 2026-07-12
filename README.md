# Preços Atualizar Job
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![AWS](https://img.shields.io/badge/AWS-232F3E?style=for-the-badge&logo=amazonwebservices&logoColor=white)

Job automatizado em Java/Spring Boot para processamento de eventos de negociações concluídas. Responsável por recalcular preços de ativos com base nas regras de negócio, registrar novas cotações no histórico e manter a base de preços da plataforma sempre sincronizada.

## Escopo e Responsabilidades

Este job é o componente responsável pela atualização dinâmica dos preços dos ativos. Suas principais funções são:

- **Processamento de Eventos**: Consome mensagens da fila de negociações concluídas, acionando o recálculo de preço para cada ativo negociado.
- **Recálculo de Preço**: Aplica a lógica de formação de preço para determinar o novo valor unitário do ativo.
- **Registro Histórico**: Insere uma nova cotação no histórico de precificação, garantindo a rastreabilidade da evolução dos preços.
- **Atualização da Base**: Persiste o preço mais recente no domínio de precificação, mantendo a informação sempre disponível para consultas.

## Arquitetura e Tecnologias

Desenvolvido em **Java** com **Spring Boot**, este job é projetado para execução na **AWS** e segue o modelo de processamento orientado a eventos.

- **Modelo de Execução**: Job scheduler ou trigger baseado em eventos (mensageria).
- **Comunicação**: Consome eventos de uma fila SQS e interage com o banco de dados do domínio de precificação.
- **Infraestrutura**: Containerizado com **Docker** e configurado para deploy em ambiente cloud. Inclui pipeline de integração contínua (GitHub Actions).

## Contexto do Sistema

Este job é uma serviço do ecossistema de microsserviços da plataforma de negociação, atuando no domínio de precificação. Ele garante que os preços dos ativos reflitam as condições de mercado em tempo real, processando as negociações assim que são concluídas. O sistema foi desenvolvido no contexto da disciplina **ACH2147 - Desenvolvimento de Sistemas de Informação Distribuídos** (EACH-USP).
