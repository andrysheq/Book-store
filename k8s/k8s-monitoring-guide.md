# === МОНИТОРИНГ ===
### 1. Установить helm и проверить:
```
curl https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3 | bash
helm version
```
### 2. Добавить репу prometheus:
```
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm repo update
```
### 3. Создать namespace и установить prometheus stack:
```
kubectl create namespace monitoring

helm install prometheus prometheus-community/kube-prometheus-stack \
  --namespace monitoring \
  --set prometheus.prometheusSpec.serviceMonitorSelectorNilUsesHelmValues=false \
  --set grafana.adminPassword=admin
```
### 4. Проверить поды, что все установилось:
```
kubectl -n monitoring get pods
```
### 5. Открыть доступ к grafana в первом терминале:
```
kubectl -n monitoring port-forward svc/prometheus-grafana 3000:80
```
### 6. Открыть доступ к grafana во втором терминале:
```
kubectl -n monitoring port-forward svc/prometheus-kube-prometheus-prometheus 9090:9090
```
### 7. Открыть в браузере:
```
http://localhost:3000
```
креды: 
admin
admin
### 8. Добавить prometheus как data source:
```
Left menu -> Connections -> Data sources
Add data source -> Prometheus
URL: http://prometheus-kube-prometheus-prometheus.monitoring.svc.cluster.local:9090
Save & test
```
### 9. Импортнуть готовые дашборды:
```
6417 - Kubernetes Cluster Monitoring (ГЛАВНЫЙ)
1860 - Node Exporter Metrics
12114 - Kubernetes Deployment Metrics
```
### Нагрузка Round-robin:
```
while true; do
  curl -X GET http://localhost:8080/api/book-store/authors \
    -H "Content-Type: application/json" \
    -d '{}' > /dev/null 2>&1
done
```

### Нагрузка ip-hash:
```
while true; do
  curl -X GET http://localhost:8080/iphash/book-store/authors \
    -H "Content-Type: application/json" \
    -H "X-Forwarded-For: 100.123.4.100" \
    -d '{}' > /dev/null 2>&1
done
```

```
while true; do
  curl -X GET http://localhost:8080/iphash/book-store/authors \
    -H "Content-Type: application/json" \
    -H "X-Forwarded-For: 999.999.9.999" \
    -d '{}' > /dev/null 2>&1
done
```

```
while true; do
curl -X GET http://localhost:8080/iphash/book-store/authors \
-H "Content-Type: application/json" \
-H "X-Forwarded-For: 192.168.1.101" \
-d '{}' > /dev/null 2>&1
done
```

### Нагрузка least-conn:
```
while true; do
  curl -X GET http://localhost:8080/least-conn/book-store/authors \
    -H "Content-Type: application/json" \
    -d '{}' > /dev/null 2>&1
done
```