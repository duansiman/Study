### Docker 方式启动
[rocketmq docker 地址](https://hub.docker.com/r/foxiswho/rocketmq)

#### 启动命令
```shell
git clone  https://github.com/foxiswho/docker-rocketmq.git
cd docker-rocketmq
cd rmq
chmod +x  start.sh
./start.sh
```

#### 遇到问题
把broker.conf中的 #brokerIP1=192.168.0.253 前面#号去掉，并且把后面的IP地址改成你的rocketmq容器宿主机IP地址