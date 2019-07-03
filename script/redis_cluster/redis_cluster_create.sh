

# 开启三个redis节点
gnome-terminal -x bash -c "redis-server ./7010/redis.conf --port 7010;"
gnome-terminal -x bash -c "redis-server ./7011/redis.conf --port 7011;"
gnome-terminal -x bash -c "redis-server ./7012/redis.conf --port 7012;"
echo node started! plz don\'t close the node service.

# 创建redis 集群
redis-cli --cluster create 127.0.0.1:7010 127.0.0.1:7011  127.0.0.1:7012



