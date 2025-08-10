// question-ui-gw/src/api/user.js
import axios from 'axios';
import request from '@/utils/request'; 
// import { getToken, setToken, removeToken } from '../utils/auth';

const apiClient = axios.create({
  baseURL: '/api',
  timeout: 10000,
});

// // 响应拦截器
// apiClient.interceptors.response.use(
//   response => response,
//   error => {
//     if (error.response.status === 401) {
//       removeToken();
//       router.push('/login');
//     }
//     return Promise.reject(error);
//   }
// );

// // 请求拦截器
// apiClient.interceptors.request.use(config => {
//   const token = getToken();
//   if (token) {
//     config.headers.Authorization = `Bearer ${token}`;
//   }
//   return config;
// });

export default {
  login(data) {
    return request.post('/login', data);
  },
  register(data) {
    return request.post('/register', data);
  }
};
