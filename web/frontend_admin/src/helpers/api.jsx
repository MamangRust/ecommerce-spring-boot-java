import axios from 'axios';

// export const myApi = axios.create({
//   baseURL: 'https://t67y.egoq.lyr.id/api',
// });

export const myApi = axios.create({
  baseURL: 'http://localhost:8080/api',
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json'
  }
});
