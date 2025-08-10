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
// isTokenExpired,
  import { getToken,  removeToken,  setToken } from './auth';
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

// 响应拦截器
// request.interceptors.response.use(
//   response => response,
//   async error => {
//     const originalRequest = error.config;
    
//     // 处理token过期
//     if (error.response?.status === 401 && !originalRequest._retry) {
//       originalRequest._retry = true;
      
//       try {
//         // 这里添加刷新token的逻辑（需要后端接口支持）
//         const refreshToken = getRefreshToken();
//         if (!refreshToken) throw new Error('No refresh token');
        
//         const refreshResponse = await axios.post('/auth/refresh-token', { refreshToken });
//         const { accessToken } = refreshResponse.data;
        
//         setToken(accessToken);
//         request.defaults.headers['Authorization'] = `Bearer ${accessToken}`;
        
//         return request(originalRequest);
//       } catch (refreshError) {
//         removeToken();
//         router.push('/login');
//         return Promise.reject(refreshError);
//       }
//     }
    
//     return Promise.reject(error);
//   }
// );

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