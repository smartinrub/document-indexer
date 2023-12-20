# Documents Index Service

Service that provides an API for indexing documents and getting words matching scores

The task took me around 3 hours (I stopped after 3 hours as advised)

## Improvements

- Integration and black box tests are missing
- Directory verifier tests are missing
- More validation on the provided documents like the document type
- Better definition of what a word is. I just considered a word anything that is in between whitespaces

## Run locally

1. Build application:
    ```shell
    mvn clean install -DskipTests
    ```

2. Build Docker image

    ```shell
    docker build -t interview .
    ```

   or if you are running docker on ARM:

    ```shell
    docker build --platform linux/amd64 -t interview .
    ```

3. Run docker compose:

    ```
    docker-compose up --build
    ```

## Hitting Endpoints

// TODO: maybe use something like OpenAPI or Swagger

### Index Documents

```
curl -X POST http://localhost:8080/v1/documents -H 'Content-Type: application/json' -d '{"directory": "/Users/sergio/test" }'
```

request body:

```json
{
   "directory": "Foo Bar"
}
```

### Get Scores:

```shell
curl -X GET http://localhost:8080/v1/scores?search=hello%20world
```

returns:

```json

[
  {
    "worlds": "hello world foo",
    "fileName": "doc1.txt",
    "score": 75.0
  },
  {
    "worlds": "hello world",
    "fileName": "doc3.txt",
    "score": 50.0
  },
  {
    "worlds": "world",
    "fileName": "doc2.txt",
    "score": 25.0
  }
]
```# vonage-interview
