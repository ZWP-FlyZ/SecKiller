:: windows启动服务的脚本

:: 启动各个簇的结点，若簇不在同一主机中，确保所有结点主机已经启动
:: 若不在同一主机中，请忽略
start cmd /C redis-server ./7010/redis.conf --port 7010
start cmd /C redis-server ./7011/redis.conf --port 7011
start cmd /C redis-server ./7012/redis.conf --port 7012
echo node started! plz don't close the node service.

:: 创建集群
redis-trib create   127.0.0.1:7010 ^
                    127.0.0.1:7011 ^
                    127.0.0.1:7012



