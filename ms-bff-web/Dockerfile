FROM node:16-alpine

WORKDIR /app

COPY tsconfig*.json ./

COPY package*.json ./

RUN npm ci

COPY src/ src/

COPY types/ types/

RUN npm run build

EXPOSE 3000

CMD [ "node", "dist/main.js" ]