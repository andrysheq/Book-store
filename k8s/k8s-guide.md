# === УСТАНОВКА ===
### 1. Сначала надо установить докер (особенно если это пустая убунту под виндой):
```
curl -fsSL https://get.docker.com -o get-docker.sh && sudo sh get-docker.sh && sudo usermod -aG docker $USER && newgrp docker
```
## Если ранее не установлен кубер на винду:
### 1.1 Установка Chocolatey под винду (powershell от админа):
```
Set-ExecutionPolicy Bypass -Scope Process -Force; [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072; iex ((New-Object System.Net.WebClient).DownloadString('https://community.chocolatey.org/install.ps1'))
```

### 1.2 Установить Minikube и kubectl
```
choco install minikube kubernetes-cli
```
# установка на линукс:
### 1.3 Установить Minikube
```
curl -LO https://github.com/kubernetes/minikube/releases/latest/download/minikube-linux-amd64 && chmod +x minikube-linux-amd64 && sudo mv minikube-linux-amd64 /usr/local/bin/minikube
```

### 1.4 Установить kubectl
```
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl" && chmod +x kubectl && sudo mv kubectl /usr/local/bin/
```

# Запуск minikube:
### 2. Запустить Minikube
```
minikube start --driver=docker
```
### 2.1. Если памяти оч мало, то можно ограничить двумя гигами:
```
minikube start --driver=docker --memory=2200mb --cpus=2
```

# === РАБОТА С ПРОГОЙ (подготовка) ===
### 1. Перейти в корень проекта через (mnt для винды):
```
cd /mnt/c/javaprojects/Book-store
```
```
cd /mnt/c/javaprojects/Book-store/k8s/manifests
```
# === ЗАПУСТИТЬ КУБЫ ===
```
minikube status
minikube stop
minikube start --driver=docker
```

### 2. дальше можно собрать Java приложения прямо в IDEA через:
```
mvn clean package
```
### 3. обратно в терминале сбилдить докер образы сервисов, например:
```
eval $(minikube docker-env)
docker build -t auth-service:latest .
docker build -t user-service:latest .
`````````````````````````````````````````````````````````````````````````````````````````````
### БИЛД ДОКЕР ОБРАЗОВ
```
eval $(minikube docker-env)
docker load -i /mnt/c/javaprojects/Book-store/users-service.tar
docker load -i /mnt/c/javaprojects/Book-store/book-author-service.tar
docker images | grep -E "service"
```
### 3.1 либо пересобрать для загрузки в minikube (более правильно):
```
cd auth-service && docker build -t auth-service:1.0 . && cd ..
cd user-service && docker build -t user-service:1.0 . && cd ..
```
### 3.2 Проверить образы:
```
docker images | grep -E "auth-service|user-service"
```

# === ДЕПЛОЙ ===
### 1. Запустить bash-скрипт, где все поднимается с логами:
```
chmod +x deploy-all.sh
./deploy-all.sh
```
### 1.1 /или/ Применить манифесты (руками):
```
cd k8s-manifests
kubectl apply -f namespace.yaml
kubectl apply -f configmap.yaml
kubectl apply -f secret.yaml
kubectl apply -f book-store-db-deployment.yaml
kubectl apply -f book-author-service-deployment.yaml
kubectl apply -f users-service-deployment.yaml

kubectl apply -f ingress.yaml
kubectl apply -f https://github.com/kubernetes-sigs/metrics-server/releases/latest/download/components.yaml
kubectl get deployment metrics-server -n kube-system

kubectl apply -f hpa.yaml
```
### 2. Проверка подов:
```
kubectl -n bookstore get pods
```

### 3. Проверка работы сервисов:
```
kubectl -n ebookstore get svc
```
### 4. Проверка deployments:
```
kubectl -n ebookstore get deployment
```

# ПРОВЕРКА РАБОТЫ СЕРВИСА ---------------------------------------------
# Пробрось порт
```
kubectl -n bookstore port-forward svc/users-service 8089:8089
```
# Затем в другом терминале проверь health:
```
curl http://localhost:8089/actuator/health
```
# Логи в реальном времени
```
kubectl -n bookstore logs -l app=users-service -f
```

# Обновление deployments
```
kubectl -n bookstore apply -f users-service-deployment.yaml
kubectl -n bookstore apply -f book-author-service-deployment.yaml
```

# Перезапуск пода в minikube
```
kubectl -n bookstore rollout restart deployment users-service
kubectl -n bookstore rollout restart deployment book-author-service
```

# Ограничить количество реплик
```
kubectl -n bookstore scale deployment users-service --replicas=3
kubectl -n bookstore scale deployment book-author-service --replicas=3
kubectl -n bookstore get pods
```


# ПРОВЕРКА РАБОТЫ СЕРВИСА ---------------------------------------------
### 3. Проверить таблицу/таблицы:
```
\dt
\d users
\d+ credentials

\q
```


# === ТЕСТИРОВАНИЕ ===
### 1. Пробросить порты. В первом терминале пробросить users-service:
```
kubectl -n bookstore port-forward svc/users-service 8089:8089
```
### 2. Во втором терминале пробросить book-author-service:
```
kubectl -n bookstore port-forward svc/book-author-service 8088:8088
```
### 2.1 Можно включить аддон-ingress и обойтись без пробрасывания портов (+ проверить статус ingress):
```
minikube addons enable ingress
kubectl -n bookstore get ingress
```

### 3. Открыть входную дверь в кластер (она же ingress). Сама по себе она не сработает, поэтому надо сделать туннель для полчения внешнего ip:
```
minikube tunnel
```

### 4. Посмотреть ip у ingress и проверить конфиг ingress:
```
kubectl -n bookstore get ingress bookstore-ingress
kubectl -n bookstore describe ingress bookstore-ingress
```
### 4.1 Если не сработает, то пробросить все же порт на ingress контроллер (может не работать из-за wsl2 + docker desktop):
```
kubectl -n ingress-nginx port-forward service/ingress-nginx-controller 8080:80
```
### 4. В postman закинуть запрос на добытый выше айпишник:
```
curl http://localhost:8080/api/book-store/books
```
# === УДАЛЕНИЕ ===
### Удаление пода и проверка происходящего:
```
kubectl -n ebookstore delete pod auth-service-123
kubectl -n ebookstore get pods -w
```

### Мгновенное удаление namespace (не рекомендуется, только в экстренных случаях или когда все подвисло):
```
kubectl get namespace ebookstore -o json \
  | tr -d "\n" \
  | sed "s/\"kubernetes\"//g" \
  | kubectl replace --raw /api/v1/namespaces/ebookstore/finalize -f -
```

# === ПЕРЕЗАПУСК НОДЫ  ===
### 1. Остановить minikube:
```
minikube stop
```
### 2. Поды должны исчезнуть, т.к. они живут только на ноде.
```
kubectl -n ebookstore get pods
```
### 3. Обратно запустить minikube:
```
minikube start --driver=docker
```
### 4. Через пару минут глянуть инфу о кластере:
```
kubectl cluster-info
```
### 5. Проверить запущенные поды:
```
kubectl -n ebookstore get pods
```


# === СИМУЛЯЦИЯ ОТКАЗА УЗЛА ===
### 1. Посмотреть ноды:
```
kubectl get nodes
```
### 2. Перекрыть доступ к узлу (новые поды туда не пойдут)
```
kubectl cordon minikube
```
### 3. Еще раз проверить статус узла
```
kubectl get nodes
```
должно быть бSchedulingDisabled

### 4. Принудительное пересоздание подов
```
kubectl -n bookstore rollout restart deployment users-service
```
### 5. Поды попытаются запуститься, но не смогут (узел заблокирован)
```
kubectl -n bookstore get pods
```
поды должны быть в статусе Pending

### 6. Разблокировать узел (ноду)
```
kubectl uncordon minikube
```
### 7. Проверить:
```
kubectl -n bookstore get pods -w
```


# === МАСШТАБИРОВАНИЕ ===
### 1. Увеличить число реплик до 5:
```
kubectl -n ebookstore scale deployment auth-service --replicas=5
```
### 2. Проверить:
```
kubectl -n ebookstore get pods
```
### 3. Вернуть на место:
```
kubectl -n ebookstore scale deployment auth-service --replicas=3
```