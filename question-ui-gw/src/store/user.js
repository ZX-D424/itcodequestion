import { defineStore } from 'pinia';
import authApi from '@/api/user';
import { getToken, removeToken } from '@/utils/auth';

export const useUserStore = defineStore('user', {
  state: () => ({
    userInfo: null,  // 用户信息
    token: getToken(),  // 从本地存储获取token
    isLoading: false  // 加载状态
  }),
  
  actions: {
    // 获取用户信息
    async fetchUserInfo() {
      if (!this.token) return null;
      
      this.isLoading = true;
      try {
        const response = await authApi.getInfo();
        this.userInfo = response.data;
        return this.userInfo;
      } catch (error) {
        console.error('获取用户信息失败:', error);
        this.logout(); // 信息获取失败，自动登出
        return null;
      } finally {
        this.isLoading = false;
      }
    },
    
    // 登录成功后设置token
    setToken(token) {
      this.token = token;
    },
    
    // 登出
    logout() {
      this.userInfo = null;
      this.token = null;
      removeToken();
    }
  },
  
  getters: {
    // 判断是否已登录
    isLoggedIn: (state) => !!state.token
  }
});