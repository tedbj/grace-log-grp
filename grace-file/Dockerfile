#基于哪个镜像
FROM openjdk:8-jdk-alpine
#将本地文件夹挂在到当前容器
VOLUME /tmp
#复制文件到容器
ADD target/grace-file-*.jar /app.jar
#声明需要暴露的端口
EXPOSE 11001
#解决转换中文乱码的问题
#RUN mkdir /usr/share/fonts
#COPY fonts/ /usr/share/fonts
#RUN apk add mkfontscale mkfontdir && cd /usr/share/fonts;mkfontscale && cd /usr/share/fonts;mkfontdir;rm -fr /var/cache/apk/*
#配置容器启动后执行的命令
ENTRYPOINT ["java","-jar","-Duser.timezone=GMT+8","/app.jar"]
