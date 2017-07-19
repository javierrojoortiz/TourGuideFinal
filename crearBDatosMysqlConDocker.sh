#! /bin/bash

sudo apt-get install docker.io
sudo usermod -aG docker $USER
docker pull mysql
cd eclipse-workspace/
sudo mkdir datos_sql
docker run --name guidetourdb -v /home/java/eclipse-workspace/datos_sql:/var/lib/mysql -e MYSQL_USER=guidetour_user -e MYSQL_PASSWORD=guidetourabc  -e MYSQL_DATABASE=db_guidetour -e MYSQL_ROOT_PASSWORD=guidetour123 -p 3306:3306 -d mysql:latest




