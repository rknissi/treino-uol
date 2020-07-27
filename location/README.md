Aplicação principal

Para compilar, apenas usar o mvn clean install

Essa aplicação usa o rabbitMQ, para rodar ele no docker:

docker run -p 5672:5672 -d --hostname my-rabbit rabbitmq:3