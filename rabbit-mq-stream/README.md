# RabbitMQ

### Enable plugin on docker
```shell
$ docker run -d --name local-rabbit --hostname localhost -p 15672:15672 -p 5672:5672 -p 5552:5552 \
-e RABBITMQ_DEFAULT_USER=admin \
-e RABBITMQ_DEFAULT_PASS=admin \
rabbitmq:3-management
$ docker exec -it local-rabbit bash
$ rabbitmq-plugins enable rabbitmq_stream
```

## References
- https://github.com/devdojobr/rabbitmq-stream
- https://www.youtube.com/watch?v=JqNH6nRANh0&list=PL62G310vn6nF-iJF7v3DWhk5Mngup-sub
