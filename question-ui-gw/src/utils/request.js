// // import axios from 'axios';

// // const request = axios.create({
// //   baseURL: '/api', // 根据实际情况设置
// //   timeout: 5000,
// // });

// // export default request;

// import axios from 'axios';


// const request = axios.create({
//   baseURL: '/api', // 根据实际情况设置
//   timeout: 5000,
// });

// // 添加请求拦截器
// request.interceptors.request.use(
//   config => {
//     // 从 Cookie 中获取 Admin-Token
//     // const token = getCookie('Admin-Token');
//     const token = localStorage.getItem('Admin-Token'); 
//     if (token) {
//       // 将 token 添加到请求头中
//       config.headers['Authorization'] = 'Bearer ' + token;
//     }
//     return config;
//   },
//   error => {
//     // 对请求错误做些什么
//     return Promise.reject(error);
//   }
// );

// // 从 Cookie 中获取指定名称的值
// function getCookie(name) {
//   const value = `; ${document.cookie}`;
//   const parts = value.split(`; ${name}=`);
//   if (parts.length === 2) return parts.pop().split(';').shift();
// }

// export default request;



import axios from 'axios';
  import {getToken} from './auth'; // ✅ 正确导入
// import { useRouter } from 'vue-router';
// const router = useRouter();

//    request.interceptors.response.use(
//      response => response,
//      error => {
//        if (error.response?.status === 401) {
//          removeToken();
//          router.push('/login');
//        }
//        return Promise.reject(error);
//      }
//    );

const request = axios.create({
  baseURL: '/api', // 根据实际情况设置
  timeout: 5000,
  withCredentials: true,
});

// 请求拦截器
request.interceptors.request.use(config => {
  const token = getToken(); // 使用auth.js统一管理的token
  if (token) {
    config.headers['Authorization'] = `Bearer ${token}`;
  }
  return config;
});

export default request;

// import axios from 'axios';

// const request = axios.create({
//   baseURL: '/api', // 根据实际情况设置
//   timeout: 5000,
// });

// // 添加请求拦截器
// request.interceptors.request.use(
//   config => {
//     // 从 localStorage 中获取 Admin-Token
//     const token = localStorage.getItem('Admin-Token');
//     if (token) {
//       // 将 token 添加到请求头中
//       config.headers['Authorization'] = 'Bearer ' + token;
//     }
//     return config;
//   },
//   error => {
//     // 对请求错误做些什么
//     return Promise.reject(error);
//   }
// );

// // 从 localStorage 中获取指定名称的值
// // function getLocalStorageItem(name) {
// //   const value = `; ${localStorage.getItem('Admin-Token')}`;
// //   const parts = value.split(`; ${name}=`);
// //   if (parts.length === 2) return parts.pop().split(';').shift();
// // }

// export default request;