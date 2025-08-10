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
  },
  // 获取用户详细信息
  getInfo() {
    return request({
      url: '/getInfo',
      method: 'get'
    })
  },

// 退出方法
logout() {
  return request({
    url: '/logout',
    method: 'post'
  })
},

// 获取验证码
getCodeImg() {
  return request({
    url: '/captchaImage',
    headers: {
      isToken: false
    },
    method: 'get',
    timeout: 20000
  })
},


// 修改：适配后端的GET请求和路径参数
  sendEmailCode(email) {
    return request({
      url: `/getEmailCode/${email}`, // 使用路径参数传递邮箱
      method: 'get', // 后端是@GetMapping，这里用get请求
      headers: {
        isToken: false // 发送验证码不需要登录状态
      }
    })
  }

};
