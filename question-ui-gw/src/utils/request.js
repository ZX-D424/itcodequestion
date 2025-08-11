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



// import axios from 'axios';
// // isTokenExpired,
//   import { getToken,  removeToken,  setToken } from './auth';
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




// 111111代

// import axios from 'axios';
// // isTokenExpired,
//   import { getToken,  removeToken,  setToken } from './auth'
// const request = axios.create({
//   baseURL: '/api', // 根据实际情况设置
//   timeout: 5000,
//   withCredentials: true,
// });

// // 请求拦截器
// request.interceptors.request.use(config => {
//   const token = getToken(); // 使用auth.js统一管理的token
//   if (token) {
//     config.headers['Authorization'] = `Bearer ${token}`;
//   }
//   return config;
// });

// // 响应拦截器
// // request.interceptors.response.use(
// //   response => response,
// //   async error => {
// //     const originalRequest = error.config;
    
// //     // 处理token过期
// //     if (error.response?.status === 401 && !originalRequest._retry) {
// //       originalRequest._retry = true;
      
// //       try {
// //         // 这里添加刷新token的逻辑（需要后端接口支持）
// //         const refreshToken = getRefreshToken();
// //         if (!refreshToken) throw new Error('No refresh token');
        
// //         const refreshResponse = await axios.post('/auth/refresh-token', { refreshToken });
// //         const { accessToken } = refreshResponse.data;
        
// //         setToken(accessToken);
// //         request.defaults.headers['Authorization'] = `Bearer ${accessToken}`;
        
// //         return request(originalRequest);
// //       } catch (refreshError) {
// //         removeToken();
// //         router.push('/login');
// //         return Promise.reject(refreshError);
// //       }
// //     }
    
// //     return Promise.reject(error);
// //   }
// // );

// export default request;





// 222222222222代
import axios from 'axios';
import { getToken, removeToken, setToken,} from './auth';
// import { getToken, removeToken, setToken, getRefreshToken } from './auth';
import router from '@/router';
// 假设使用 Element Plus 的 Message 组件做提示，根据实际 UI 库调整
import { ElMessage } from 'element-plus';

const request = axios.create({
  baseURL: '/api',
  timeout: 10000,
  withCredentials: true,
});

// 请求拦截器
request.interceptors.request.use(config => {
  const token = getToken();
  if (token) {
    config.headers['Authorization'] = `Bearer ${token}`;
  }
  return config;
});

// 刷新 token 的 axios 实例（避免触发拦截器死循环）
const refreshInstance = axios.create({
  baseURL: '/api',
  timeout: 10000,
  withCredentials: true,
});

// 响应拦截器
request.interceptors.response.use(
  response => {
    // 处理成功响应
    return response.data; // 直接返回数据体，简化调用
  },
  async error => {
    const originalRequest = error.config;
    
    // 处理网络错误
    if (!error.response) {
      // ElMessage.error(response.data)
      ElMessage.error('网络异常，请检查网络连接');
      return Promise.reject(error);
    }

    const { status, data } = error.response;

    // 处理 401 未授权
    if (status === 401 && !originalRequest._retry) {
      originalRequest._retry = true; // 标记已重试，防止无限循环
      
      try {
        // 尝试刷新 token
        const refreshToken = getRefreshToken();
        if (!refreshToken) {
          throw new Error('没有刷新令牌');
        }

        const res = await refreshInstance.post('/auth/refresh-token', {
          refreshToken
        });

        // 刷新成功，更新 token 并重试原请求
        const { accessToken } = res.data;
        setToken(accessToken);
        request.defaults.headers['Authorization'] = `Bearer ${accessToken}`;
        originalRequest.headers['Authorization'] = `Bearer ${accessToken}`;
        return request(originalRequest);

      } catch (refreshError) {
        // 刷新失败，强制登出
        ElMessage.error('登录已过期，请重新登录');
        removeToken();
        router.push('/login');
        return Promise.reject(refreshError);
      }
    }

    // 处理其他常见错误状态码
    switch (status) {
      case 403:
        ElMessage.error(data.msg || '权限不足，无法访问');
        break;
      case 404:
        ElMessage.error('请求的资源不存在');
        break;
      case 500:
        ElMessage.error('服务器内部错误，请稍后重试');
        break;
      default:
        ElMessage.error(data.msg || `请求错误：${status}`);
    }

    return Promise.reject(error);
  }
);

export default request;
