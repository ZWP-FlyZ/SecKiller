rem 启动服务的脚本

start cmd /C java -jar sk-web.jar --server.port=8081 --spring.redis.lettuce.pool.max-active=16
start cmd /C java -jar sk-web.jar --server.port=8083 --spring.redis.lettuce.pool.max-active=16
start cmd /C java -jar sk-web.jar --server.port=8084 --spring.redis.lettuce.pool.max-active=16


