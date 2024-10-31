# Spring Cloud Contract OpenAPI 3.0 Examples

[![CircleCI](https://circleci.com/gh/mzielinski/spring-cloud-contract-oa3-examples.svg?style#svg)](https://circleci.com/gh/mzielinski/spring-cloud-contract-oa3-examples)

## Main repository: [mzielinski/spring-cloud-contract-oa3](https://github.com/mzielinski/spring-cloud-contract-oa3)

## Single Source of Truth:

All artifacts generated from single Open API 3.0 with x-contracts:

* model + api code
* contract tests → for producer
* contract stub → for consumer

## How to build and run

```bash
./mvnw clean install
```

or separately

```bash
./mvnw -f sccoa3-producer/pom.xml clean install
./mvnw -f sccoa3-consumer/pom.xml clean package
```
