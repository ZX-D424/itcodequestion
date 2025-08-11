NEW_FILE_CODE
<template>
  <div class="login-container">
    <div class="login-form">
      <h2>用户登录</h2>
      <form @submit.prevent="handleLogin">
        <div class="form-group">
          <label for="username">用户名</label>
          <input 
            type="text" 
            id="username" 
            v-model="loginForm.username" 
            required
            placeholder="请输入用户名"
          />
        </div>
        
        <div class="form-group">
          <label for="password">密码</label>
          <input 
            type="password" 
            id="password" 
            v-model="loginForm.password" 
            required
            placeholder="请输入密码"
          />
        </div>

        <!-- 验证码 -->
      <div class="form-group captcha-group">
        <label for="code">验证码</label>
        <div class="captcha-container">
          <input v-model="loginForm.code" type="text" id="code" required />
          <img 
            :src="'data:image/png;base64,' + captcha.image" 
            @click="getAuthCode" 
            class="captcha-image"
            v-if="captcha.image"
          />
          <div v-else @click="getAuthCode" class="captcha-placeholder">获取验证码</div>
        </div>
      </div>
        
        <button type="submit" :disabled="loading">
          {{ loading ? '登录中...' : '登录' }}
        </button>
      </form>
      
      <div class="form-footer">
        <p>
          还没有账号？
          <a href="#" @click.prevent="goToRegister">立即注册</a>
        </p>
      </div>
      
      <div v-if="error" class="error-message">
        {{ error }}
      </div>
    </div>
  </div>
</template>

<!-- <script setup>
import { ref, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';

const router = useRouter();
const route = useRoute();

const loginForm = ref({
  username: '',
  password: ''
});

const loading = ref(false);
const error = ref('');

// 处理登录
const handleLogin = async () => {
  loading.value = true;
  error.value = '';
  
  try {
    // 这里应该调用实际的登录API
    // 模拟登录过程
    await new Promise(resolve => setTimeout(resolve, 1000));
    
    // 模拟登录成功，设置token
    localStorage.setItem('Admin-Token', 'eyJhbGciOiJIUzUxMiJ9.sample.token');
    
    // 登录成功后跳转
    const redirect = route.query.redirect || '/profile';
    router.push(redirect);
  } catch (err) {
    error.value = '登录失败，请检查用户名和密码';
  } finally {
    loading.value = false;
  }
};

// 跳转到注册页面
const goToRegister = () => {
  router.push('/register');
};

// 检查是否已经登录
onMounted(() => {
  if (localStorage.getItem('Admin-Token')) {
    // 如果已经登录，直接跳转到个人中心
    router.push('/profile');
  }
});
</script> -->

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { setToken } from '@/utils/auth';
// import authApi from '@/api/user';
import authApi from '@/api/user';
// import { getCodeImg } from '@/api/user';

const router = useRouter();
const route = useRoute();

const loginForm = ref({
  username: '',
  password: '',
   code: '',
  uuid: '',
  userType: '00' // 根据实际需求设置默认值
});

const loading = ref(false);
const error = ref('');
const captcha = ref({
  uuid: '',
  image: ''
});

// / 获取验证码
const getAuthCode = async () => {
  try {
    const response = await authApi.getCodeImg();
    if (response.data.captchaEnabled) {
      captcha.value.uuid = response.data.uuid;
      captcha.value.image = response.data.img;
      loginForm.value.uuid = response.data.uuid; // 同步到登录表单
    }
  } catch (err) {
    error.value = '验证码获取失败';
     captcha.value.uuid = ''; // 清空无效uuid
  loginForm.value.uuid = ''; // 清空登录表单的uuid
  getAuthCode();
  }
};

// 处理登录
const handleLogin = async () => {
  loading.value = true;
  error.value = '';
  

 try {
    // 调用登录接口
    const response = await authApi.login(loginForm.value);
    if (!response?.data?.token) {
      throw new Error('登录失败');
    }
    // 提取token并存储
    const token = response?.data?.token;
    if (token) {
      setToken(token);
      
      // 跳转目标路由
      const redirect = router.currentRoute.value.query.redirect || '/profile';
      router.push(redirect);
    } else {
      error.value = '登录响应缺少token';
    }
  } catch (err) {
    // 处理错误响应
    const errorMsg = err.response?.data?.msg || '登录失败，请检查用户名和密码';
    error.value = errorMsg;
  } finally {
    loading.value = false;
     // 刷新验证码
    getAuthCode();
  }
};





// try {
//     const response = await authApi.login(loginForm.value);
//     setToken(response.data.token);
    
//     const redirect = route.query.redirect || '/profile';
//     router.push(redirect);
//   } catch (err) {
//     error.value = '登录失败，请检查用户名和密码';
//   } finally {
//     loading.value = false;
//   }
// };




// 跳转到注册页面
const goToRegister = () => {
  router.push('/register');
};

// 检查是否已经登录
onMounted(() => {
  if (localStorage.getItem('Admin-Token')) {
    // 如果已经登录，直接跳转到个人中心
    router.push('/profile');
  }
  else {
    getAuthCode();
  }
});
</script>




<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 400px;
  padding: 20px;
}

.login-form {
  width: 100%;
  max-width: 400px;
  padding: 30px;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.login-form h2 {
  text-align: center;
  margin-bottom: 30px;
  color: #333;
}

.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
  color: #555;
}

.form-group input {
  width: 100%;
  padding: 12px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 16px;
  box-sizing: border-box;
}

.form-group input:focus {
  border-color: #0066ff;
  outline: none;
}

button {
  width: 100%;
  padding: 12px;
  background-color: #0066ff;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s;
}

button:hover:not(:disabled) {
  background-color: #0052cc;
}

button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.form-footer {
  text-align: center;
  margin-top: 20px;
}

.form-footer a {
  color: #0066ff;
  text-decoration: none;
}

.form-footer a:hover {
  text-decoration: underline;
}

.error-message {
  margin-top: 15px;
  padding: 10px;
  background-color: #ffebee;
  color: #c62828;
  border-radius: 4px;
  text-align: center;
}


.captcha-group {
  display: flex;
  flex-direction: column;
}

.captcha-container {
  display: flex;
  align-items: center;
  gap: 10px;
}

.captcha-image {
  width: 100px;
  height: 38px;
  cursor: pointer;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.captcha-placeholder {
  width: 100px;
  height: 38px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  border: 1px solid #ddd;
  border-radius: 4px;
  background: #f5f5f5;
  color: #666;
}
</style>