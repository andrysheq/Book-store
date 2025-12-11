#!/bin/bash

set -e

echo "Starting deploy in Minikube..."

# Цвета
GREEN='\033[0;32m'
BLUE='\033[0;34m'
NC='\033[0m'

# 1. Создаем namespace
echo -e "${BLUE}1. Создание namespace${NC}"
kubectl apply -f 01-namespace.yaml
sleep 2

# 2. Создаем ConfigMap и Secrets
echo -e "${BLUE}2. Создание ConfigMap и Secrets${NC}"
kubectl apply -f 02-configmap.yaml
kubectl apply -f 03-secret.yaml
sleep 2

# 4. Разворачиваем БД
echo -e "${BLUE}4. Развёртывание базы данных${NC}"
kubectl apply -f book-store-db-deployment.yaml
kubectl -n bookstore wait --for=condition=available --timeout=300s \
    deployment/book-store-database || echo "book-store-db timeout, продолжаем"
sleep 2

# 5. Разворачиваем сервисы
echo -e "${BLUE}6. Развёртывание Auth-Service${NC}"
kubectl apply -f book-author-service-deployment.yaml
kubectl -n bookstore wait --for=condition=available --timeout=300s \
    deployment/book-author-service || echo "book-author-service timeout, продолжаем"
sleep 2

echo -e "${BLUE}7. Развёртывание User-Service${NC}"
kubectl apply -f users-service-deployment.yaml
kubectl -n bookstore wait --for=condition=available --timeout=300s \
    deployment/users-service || echo "users-service timeout, продолжаем"
sleep 2

# 6. Ingress
echo -e "${BLUE}8. Создание Ingress${NC}"
kubectl apply -f ingress.yaml
sleep 2

# 7. HPA
echo -e "${BLUE}9. Создание HorizontalPodAutoscaler${NC}"
kubectl apply -f hpa.yaml || echo "HPA может потребовать metrics-server"

echo ""
echo -e "${GREEN}Деплой - успех!${NC}"
echo ""
echo "Чекнуть статус:"
echo "kubectl -n ebookstore get all"
echo ""
echo "Чекнуть логи:"
echo "kubectl -n ebookstore logs -f deployment/auth-service"