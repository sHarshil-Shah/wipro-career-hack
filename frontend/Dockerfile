#stage 1
FROM node:12.8.0 as node
RUN mkdir app
WORKDIR app
COPY . .
RUN npm install
RUN npm run build --prod

#stage 2
FROM nginx:1.15.2-alpine

COPY --from=node /app/dist/frontend /var/www
COPY nginx.conf /etc/nginx/nginx.conf
EXPOSE 80
ENTRYPOINT ["nginx", "-g", "daemon off;"]
