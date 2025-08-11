<template>
  <div class="login-container">
    <div class="login-form">
      <div class="login-header">
        <h2>用户登录</h2>
        <p class="login-desc">请输入账号信息登录系统</p>
      </div>
      
      <form @submit.prevent="handleLogin">
        <!-- 用户名 -->
        <div class="form-group">
          <label for="username">用户名</label>
          <input 
            type="text" 
            id="username" 
            v-model="loginForm.username" 
            placeholder="请输入用户名"
            @blur="validateField('username')"
            :class="{ 'invalid': errors.username }"
            autocomplete="username"
          />
          <span class="error-tip" v-if="errors.username">{{ errors.username }}</span>
        </div>
        
        <!-- 密码 -->
        <div class="form-group">
          <label for="password">密码</label>
          <div class="password-wrapper">
            <input 
              :type="showPassword ? 'text' : 'password'" 
              id="password" 
              v-model="loginForm.password" 
              placeholder="请输入密码"
              @blur="validateField('password')"
              :class="{ 'invalid': errors.password }"
              autocomplete="current-password"
            />
            <button 
              type="button" 
              class="toggle-password" 
              @click.prevent="showPassword = !showPassword"
              aria-label="切换密码可见性"
            >
              <i class="icon" :class="showPassword ? 'icon-eye-off' : 'icon-eye'"></i>
            </button>
          </div>
          <span class="error-tip" v-if="errors.password">{{ errors.password }}</span>
        </div>

        <!-- 验证码 -->
        <div class="form-group captcha-group">
          <label for="code">验证码</label>
          <div class="captcha-container">
            <input 
              v-model="loginForm.code" 
              type="text" 
              id="code" 
              placeholder="请输入验证码"
              @blur="validateField('code')"
              :class="{ 'invalid': errors.code }"
            />
            <div class="captcha-image-container">
              <img 
                :src="'data:image/png;base64,' + captcha.image" 
                @click="refreshCaptcha" 
                class="captcha-image"
                v-if="captcha.image"
                alt="验证码图片，点击刷新"
              />
              <div 
                v-else 
                @click="refreshCaptcha" 
                class="captcha-placeholder"
              >
                获取验证码
              </div>
              <div class="captcha-refresh" @click="refreshCaptcha">
                <i class="icon icon-refresh"></i>
              </div>
            </div>
          </div>
          <span class="error-tip" v-if="errors.code">{{ errors.code }}</span>
        </div>
        
        <button type="submit" :disabled="loading || !isFormValid" class="login-btn">
          <span v-if="loading" class="loading-spinner"></span>
          {{ loading ? '登录中...' : '登录' }}
        </button>
      </form>
      
      <div class="form-footer">
        <p>
          还没有账号？
          <a href="#" @click.prevent="goToRegister" class="link">立即注册</a>
        </p>
        <p class="forgot-password">
          <a href="#" @click.prevent="goToForgotPassword" class="link">忘记密码？</a>
        </p>
      </div>
      
      <div v-if="error" class="error-message" :class="{ shake: errorShake }">
        {{ error }}
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed, watch } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { setToken, getToken } from '@/utils/auth';
import authApi from '@/api/user';

const router = useRouter();
const route = useRoute();

// 表单数据
const loginForm = ref({
  username: '',
  password: '',
  code: '',
  uuid: '',
  userType: '00' // 用户类型默认值
});

// 状态管理
const loading = ref(false);
const error = ref('');
const errorShake = ref(false);
const showPassword = ref(false);
const captcha = ref({
  uuid: '',
  image: ''
});

// 错误信息存储
const errors = ref({
  username: '',
  password: '',
  code: ''
});

// 验证单个字段
const validateField = (field) => {
  const value = loginForm.value[field];
  switch (field) {
    case 'username':
      if (!value.trim()) {
        errors.value.username = '请输入用户名';
      } else {
        errors.value.username = '';
      }
      break;
      
    case 'password':
      if (!value) {
        errors.value.password = '请输入密码';
      } else {
        errors.value.password = '';
      }
      break;
      
    case 'code':
      if (!value.trim()) {
        errors.value.code = '请输入验证码';
      } else {
        errors.value.code = '';
      }
      break;
  }
};

// 表单整体有效性
const isFormValid = computed(() => {
  return Object.values(errors.value).every(error => !error) 
    && loginForm.value.username.trim() 
    && loginForm.value.password 
    && loginForm.value.code.trim()
    && captcha.value.uuid; // 确保验证码uuid有效
});

// 刷新验证码
const refreshCaptcha = async () => {
  // 清空验证码输入
  loginForm.value.code = '';
  errors.value.code = '';
  
  try {
    // 显示加载状态
    const captchaContainer = document.querySelector('.captcha-image-container');
    if (captchaContainer) captchaContainer.classList.add('loading');
    
    const response = await authApi.getCodeImg();
    if (response.data.captchaEnabled) {
      captcha.value.uuid = response.data.uuid;
      captcha.value.image = response.data.img;
      loginForm.value.uuid = response.data.uuid; // 同步到登录表单
    }
  } catch (err) {
    error.value = '验证码获取失败，请重试';
    triggerErrorShake();
    captcha.value.uuid = '';
    loginForm.value.uuid = '';
  } finally {
    // 移除加载状态
    const captchaContainer = document.querySelector('.captcha-image-container');
    if (captchaContainer) captchaContainer.classList.remove('loading');
  }
};

// 触发错误抖动动画
const triggerErrorShake = () => {
  errorShake.value = true;
  setTimeout(() => {
    errorShake.value = false;
  }, 500);
};

// 处理登录
const handleLogin = async () => {
  // 提交前全量校验
  ['username', 'password', 'code'].forEach(field => {
    validateField(field);
  });
  
  if (!isFormValid.value) {
    triggerErrorShake();
    return;
  }
  
  loading.value = true;
  error.value = '';
  
  try {
    const response = await authApi.login(loginForm.value);
    if (!response?.data?.token) {
      throw new Error('登录失败，未获取到token');
    }
    
    // 存储token
    setToken(response.data.token);
    
    // 获取跳转地址，优先使用路由参数，其次使用默认地址
    const redirect = route.query.redirect || '/profile';
    router.push(redirect);
  } catch (err) {
    // 处理错误响应
    error.value = err.response?.data?.msg || '登录失败，请检查账号信息';
    triggerErrorShake();
  } finally {
    loading.value = false;
    // 无论成功失败都刷新验证码
    refreshCaptcha();
  }
};

// 跳转到注册页面
const goToRegister = () => {
  router.push('/register');
};

// 跳转到忘记密码页面
const goToForgotPassword = () => {
  router.push('/forgot-password');
};

// 监听验证码输入变化
watch(
  () => loginForm.value.code,
  (value) => {
    if (value) {
      validateField('code');
    }
  }
);

// 页面加载时检查登录状态
onMounted(() => {
  // 使用封装的getToken方法检查登录状态，与setToken保持一致
  if (getToken()) {
    router.push('/profile');
  } else {
    // 未登录则获取验证码
    refreshCaptcha();
  }
});
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  padding: 20px;
  background-color: #f5f7fa;
  background-image: 
    radial-gradient(circle at 10% 20%, rgba(0, 102, 255, 0.05) 0%, transparent 20%),
    radial-gradient(circle at 90% 80%, rgba(0, 102, 255, 0.05) 0%, transparent 20%);
}

.login-form {
  width: 100%;
  max-width: 400px;
  padding: 36px;
  border: 1px solid #e5e7eb;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.05);
  background-color: white;
}

.login-header {
  text-align: center;
  margin-bottom: 30px;
}

.login-header h2 {
  margin-bottom: 8px;
  color: #1d2129;
  font-size: 24px;
  font-weight: 600;
}

.login-desc {
  color: #86909c;
  font-size: 14px;
}

.form-group {
  margin-bottom: 22px;
  position: relative;
}

.form-group label {
  display: block;
  margin-bottom: 6px;
  color: #4e5969;
  font-size: 14px;
  font-weight: 500;
}

.form-group input {
  width: 100%;
  padding: 12px 15px;
  border: 1px solid #d0d3d9;
  border-radius: 6px;
  font-size: 15px;
  transition: all 0.2s;
}

.form-group input::placeholder {
  color: #c9cdD4;
}

.form-group input:focus {
  border-color: #0066ff;
  outline: none;
  box-shadow: 0 0 0 3px rgba(0, 102, 255, 0.1);
}

/* 错误状态样式 */
.form-group input.invalid {
  border-color: #ff4d4f;
}

.form-group input.invalid:focus {
  box-shadow: 0 0 0 3px rgba(255, 77, 79, 0.1);
}

.error-tip {
  display: block;
  margin-top: 4px;
  color: #ff4d4f;
  font-size: 12px;
  line-height: 1.4;
  height: 16px;
}

/* 密码显示切换按钮 */
.password-wrapper {
  position: relative;
}

.toggle-password {
  position: absolute;
  right: 12px;
  top: 50%;
  transform: translateY(-50%);
  background: none;
  border: none;
  color: #86909c;
  cursor: pointer;
  width: auto;
  height: auto;
  padding: 4px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.toggle-password:hover .icon {
  color: #0066ff;
}

.icon {
  display: inline-block;
  width: 18px;
  height: 18px;
  position: relative;
}

.icon-eye::after {
  content: '';
  position: absolute;
  top: 50%;
  left: 50%;
  width: 8px;
  height: 8px;
  background-color: currentColor;
  border-radius: 50%;
  transform: translate(-50%, -50%);
}

.icon-eye::before {
  content: '';
  position: absolute;
  top: 50%;
  left: 50%;
  width: 14px;
  height: 14px;
  border: 2px solid currentColor;
  border-radius: 50%;
  transform: translate(-50%, -50%);
}

.icon-eye-off::after {
  content: '';
  position: absolute;
  top: 50%;
  left: 50%;
  width: 16px;
  height: 2px;
  background-color: currentColor;
  transform: translate(-50%, -50%) rotate(-45deg);
}

.icon-refresh {
  width: 16px;
  height: 16px;
  position: relative;
}

.icon-refresh::before {
  content: '';
  position: absolute;
  top: 0;
  left: 50%;
  width: 2px;
  height: 8px;
  background-color: currentColor;
  transform: translateX(-50%);
}

.icon-refresh::after {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 16px;
  height: 16px;
  border: 2px solid currentColor;
  border-top-color: transparent;
  border-radius: 50%;
}

/* 验证码样式 */
.captcha-group {
  display: flex;
  flex-direction: column;
}

.captcha-container {
  display: flex;
  align-items: center;
  gap: 10px;
}

.captcha-image-container {
  position: relative;
  flex-shrink: 0;
}

.captcha-image {
  width: 120px;
  height: 44px;
  cursor: pointer;
  border: 1px solid #d0d3d9;
  border-radius: 6px;
  transition: border-color 0.2s;
  object-fit: contain;
}

.captcha-image:hover {
  border-color: #0066ff;
}

.captcha-placeholder {
  width: 120px;
  height: 44px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  border: 1px solid #d0d3d9;
  border-radius: 6px;
  background: #f5f5f5;
  color: #86909c;
  font-size: 13px;
  transition: all 0.2s;
}

.captcha-placeholder:hover {
  border-color: #0066ff;
  color: #0066ff;
}

.captcha-refresh {
  position: absolute;
  right: -8px;
  top: -8px;
  width: 20px;
  height: 20px;
  background-color: white;
  border: 1px solid #d0d3d9;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #86909c;
  cursor: pointer;
  font-size: 10px;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
  transition: all 0.2s;
}

.captcha-refresh:hover {
  color: #0066ff;
  transform: rotate(180deg);
}

.captcha-image-container.loading::after {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(255, 255, 255, 0.7);
  border-radius: 6px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.captcha-image-container.loading::before {
  content: '';
  position: absolute;
  top: 50%;
  left: 50%;
  width: 20px;
  height: 20px;
  border: 2px solid #e5e7eb;
  border-top-color: #0066ff;
  border-radius: 50%;
  transform: translate(-50%, -50%);
  animation: spin 1s linear infinite;
  z-index: 1;
}

/* 登录按钮 */
.login-btn {
  width: 100%;
  padding: 13px;
  background-color: #0066ff;
  color: white;
  border: none;
  border-radius: 6px;
  font-size: 16px;
  font-weight: 500;
  cursor: pointer;
  transition: background-color 0.3s;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
}

.login-btn:hover:not(:disabled) {
  background-color: #0052cc;
}

.login-btn:disabled {
  background-color: #c9cdD4;
  cursor: not-allowed;
  opacity: 0.8;
}

.loading-spinner {
  width: 16px;
  height: 16px;
  border: 2px solid rgba(255, 255, 255, 0.3);
  border-top-color: white;
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

/* 底部链接 */
.form-footer {
  margin-top: 24px;
  text-align: center;
  color: #86909c;
  font-size: 14px;
}

.form-footer p {
  margin: 8px 0;
}

.forgot-password {
  margin-top: 12px;
}

.link {
  color: #0066ff;
  text-decoration: none;
  transition: color 0.2s;
}

.link:hover {
  color: #0052cc;
  text-decoration: underline;
}

/* 错误消息 */
.error-message {
  margin-top: 16px;
  padding: 10px 12px;
  background-color: #fff1f0;
  color: #ff4d4f;
  border-radius: 6px;
  text-align: center;
  font-size: 14px;
  transition: all 0.2s;
}

/* 错误抖动动画 */
.shake {
  animation: shake 0.5s cubic-bezier(0.36, 0.07, 0.19, 0.97) both;
}

/* 动画定义 */
@keyframes spin {
  to {
    transform: rotate(360deg);
  }
}

@keyframes shake {
  10%, 90% {
    transform: translateX(-1px);
  }
  20%, 80% {
    transform: translateX(2px);
  }
  30%, 50%, 70% {
    transform: translateX(-3px);
  }
  40%, 60% {
    transform: translateX(3px);
  }
}

/* 响应式调整 */
@media (max-width: 480px) {
  .login-form {
    padding: 24px;
  }
  
  .captcha-image,
  .captcha-placeholder {
    width: 100px;
  }
}
</style>
