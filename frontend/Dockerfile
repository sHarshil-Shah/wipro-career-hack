#stage 1
FROM node:12.8.0 as node
RUN mkdir app
WORKDIR app
COPY . .
RUN npm install
RUN npm run build --prod

#stage 2
FROM nginx:alpine

COPY --from=node /app/dist/frontend /usr/share/nginx/html