<template>
  <div class="register-container">
    <div class="register-form">
      <h2>用户注册</h2>
      <form @submit.prevent="handleRegister">
        <!-- 用户名 -->
        <div class="form-group">
          <label for="username">用户名</label>
          <input 
            type="text" 
            id="username" 
            v-model="registerForm.username" 
            required
            placeholder="请输入3-20位用户名（字母/数字/下划线）"
            @blur="validateField('username')"
            :class="{ 'invalid': errors.username }"
          />
          <span class="error-tip" v-if="errors.username">{{ errors.username }}</span>
        </div>
        
        <!-- 邮箱 -->
        <div class="form-group">
          <label for="email">邮箱</label>
          <input 
            type="email" 
            id="email" 
            v-model="registerForm.email" 
            required
            placeholder="请输入有效的邮箱地址"
            @blur="validateField('email')"
            :class="{ 'invalid': errors.email }"
          />
          <span class="error-tip" v-if="errors.email">{{ errors.email }}</span>
        </div>

        <!-- 手机号 -->
        <div class="form-group">
          <label for="mobile">手机号</label>
          <input 
            v-model="registerForm.mobile" 
            type="tel" 
            id="mobile" 
            placeholder="请输入11位手机号"
            maxlength="11"
            @input="formatMobile"
            @blur="validateField('mobile')"
            :class="{ 'invalid': errors.mobile }"
          />
          <span class="error-tip" v-if="errors.mobile">{{ errors.mobile }}</span>
        </div>
        
        <!-- 密码 -->
        <div class="form-group">
          <label for="password">密码</label>
          <div class="password-wrapper">
            <input 
              :type="showPassword ? 'text' : 'password'" 
              id="password" 
              v-model="registerForm.password" 
              required
              placeholder="请输入6-20位密码（含字母和数字）"
              @input="checkPasswordStrength"
              @blur="validateField('password')"
              :class="{ 'invalid': errors.password }"
            />
            <button 
              type="button" 
              class="toggle-password" 
              @click.prevent="showPassword = !showPassword"
            >
              {{ showPassword ? '隐藏' : '显示' }}
            </button>
          </div>
          <!-- 密码强度提示 -->
          <div class="password-strength" v-if="registerForm.password">
            <div 
              class="strength-bar" 
              :class="passwordStrengthClass"
            ></div>
            <span class="strength-text">{{ passwordStrengthText }}</span>
          </div>
          <span class="error-tip" v-if="errors.password">{{ errors.password }}</span>
        </div>
        
        <!-- 确认密码 -->
        <div class="form-group">
          <label for="confirmPassword">确认密码</label>
          <input 
            :type="showConfirmPassword ? 'text' : 'password'" 
            id="confirmPassword" 
            v-model="registerForm.confirmPassword" 
            required
            placeholder="请再次输入密码"
            @blur="validateField('confirmPassword')"
            :class="{ 'invalid': errors.confirmPassword }"
          />
          <button 
            type="button" 
            class="toggle-password" 
            @click.prevent="showConfirmPassword = !showConfirmPassword"
          >
            {{ showConfirmPassword ? '隐藏' : '显示' }}
          </button>
          <span class="error-tip" v-if="errors.confirmPassword">{{ errors.confirmPassword }}</span>
        </div>
        
        <button type="submit" :disabled="loading || !isFormValid">
          {{ loading ? '注册中...' : '注册' }}
        </button>
      </form>
      
      <div class="form-footer">
        <p>
          已有账号？
          <a href="#" @click.prevent="goToLogin">立即登录</a>
        </p>
      </div>
      
      <div v-if="error" class="error-message">
        {{ error }}
      </div>
      
      <div v-if="success" class="success-message">
        {{ success }}
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, watch } from 'vue';
import { useRouter } from 'vue-router';
import authApi from '@/api/user';

const router = useRouter();

// 表单数据
const registerForm = ref({
  username: '',
  email: '',
  password: '',
  confirmPassword: '',
  mobile: '',
  userType: '00' // 默认用户类型
});

// 状态管理
const loading = ref(false);
const error = ref('');
const success = ref('');
const showPassword = ref(false);
const showConfirmPassword = ref(false);
const passwordStrength = ref(0); // 0-未输入 1-弱 2-中 3-强

// 错误信息存储
const errors = ref({
  username: '',
  email: '',
  mobile: '',
  password: '',
  confirmPassword: ''
});

// 格式化手机号输入（过滤非数字）
const formatMobile = () => {
  registerForm.value.mobile = registerForm.value.mobile.replace(/[^\d]/g, '');
};

// 校验单个字段
const validateField = (field) => {
  const value = registerForm.value[field];
  switch (field) {
    case 'username':
      if (!value) {
        errors.value.username = '用户名不能为空';
      } else if (!/^[a-zA-Z0-9_]{3,20}$/.test(value)) {
        errors.value.username = '用户名需为3-20位（字母/数字/下划线）';
      } else {
        errors.value.username = '';
      }
      break;
      
    case 'email':
      if (!value) {
        errors.value.email = '邮箱不能为空';
      } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value)) {
        errors.value.email = '请输入有效的邮箱地址';
      } else {
        errors.value.email = '';
      }
      break;
      
    case 'mobile':
      if (!value) {
        errors.value.mobile = '手机号不能为空';
      } else if (!/^1[3-9]\d{9}$/.test(value)) {
        errors.value.mobile = '请输入有效的11位手机号';
      } else {
        errors.value.mobile = '';
      }
      break;
      
    case 'password':
      if (!value) {
        errors.value.password = '密码不能为空';
      } else if (value.length < 6 || value.length > 20) {
        errors.value.password = '密码长度需为6-20位';
      } else if (!/^(?=.*[A-Za-z])(?=.*\d).+$/.test(value)) {
        errors.value.password = '密码需包含字母和数字';
      } else {
        errors.value.password = '';
      }
      break;
      
    case 'confirmPassword':
      if (!value) {
        errors.value.confirmPassword = '请确认密码';
      } else if (value !== registerForm.value.password) {
        errors.value.confirmPassword = '两次输入的密码不一致';
      } else {
        errors.value.confirmPassword = '';
      }
      break;
  }
};

// 校验密码强度
const checkPasswordStrength = () => {
  const password = registerForm.value.password;
  if (!password) {
    passwordStrength.value = 0;
    return;
  }
  
  let strength = 0;
  // 长度 >= 8 加1分
  if (password.length >= 8) strength++;
  // 包含小写字母 加1分
  if (/[a-z]/.test(password)) strength++;
  // 包含大写字母 加1分
  if (/[A-Z]/.test(password)) strength++;
  // 包含数字 加1分（已在基础校验中要求，这里可作为额外加分）
  if (/[0-9]/.test(password)) strength++;
  // 包含特殊字符 加1分
  if (/[^A-Za-z0-9]/.test(password)) strength++;
  
  // 映射到1-3级强度
  passwordStrength.value = Math.min(strength, 3);
};

// 密码强度样式和文本
const passwordStrengthClass = computed(() => {
  switch (passwordStrength.value) {
    case 1: return 'weak';
    case 2: return 'medium';
    case 3: return 'strong';
    default: return '';
  }
});

const passwordStrengthText = computed(() => {
  switch (passwordStrength.value) {
    case 1: return '弱（建议增加长度或特殊字符）';
    case 2: return '中（可增加大写字母提升安全性）';
    case 3: return '强（密码安全性良好）';
    default: return '';
  }
});

// 表单整体有效性
const isFormValid = computed(() => {
  return Object.values(errors.value).every(error => !error) 
    && registerForm.value.username 
    && registerForm.value.email 
    && registerForm.value.mobile 
    && registerForm.value.password 
    && registerForm.value.confirmPassword;
});

// 实时监听确认密码变化
watch(
  () => registerForm.value.confirmPassword,
  () => {
    if (registerForm.value.confirmPassword) {
      validateField('confirmPassword');
    }
  }
);

// 处理注册
const handleRegister = async () => {
  // 提交前全量校验
  ['username', 'email', 'mobile', 'password', 'confirmPassword'].forEach(field => {
    validateField(field);
  });
  
  if (!isFormValid.value) return;
  
  loading.value = true;
  error.value = '';
  success.value = '';
  
  try {
    await authApi.register({
      username: registerForm.value.username,
      password: registerForm.value.password,
      email: registerForm.value.email,
      mobile: registerForm.value.mobile,
      userType: registerForm.value.userType
    });
    
    success.value = '注册成功！即将跳转到登录页面...';
    
    // 3秒后跳转登录页
    setTimeout(() => {
      router.push('/login');
    }, 3000);
  } catch (err) {
    error.value = err.response?.data?.msg || '注册失败，请稍后重试';
    // 注册失败后清空密码相关字段，避免明文残留
    registerForm.value.password = '';
    registerForm.value.confirmPassword = '';
  } finally {
    loading.value = false;
  }
};

// 跳转到登录页面
const goToLogin = () => {
  router.push('/login');
};
</script>

<style scoped>
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  padding: 20px;
  background-color: #f5f7fa;
}

.register-form {
  width: 100%;
  max-width: 400px;
  padding: 30px;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 2px 15px rgba(0, 0, 0, 0.05);
  background-color: white;
}

.register-form h2 {
  text-align: center;
  margin-bottom: 30px;
  color: #333;
  font-weight: 600;
}

.form-group {
  margin-bottom: 20px;
  position: relative;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  font-weight: 500;
  color: #555;
  font-size: 14px;
}

.form-group input {
  width: 100%;
  padding: 12px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 16px;
  box-sizing: border-box;
  transition: border-color 0.3s;
}

.form-group input:focus {
  border-color: #0066ff;
  outline: none;
  box-shadow: 0 0 0 2px rgba(0, 102, 255, 0.1);
}

/* 错误状态样式 */
.form-group input.invalid {
  border-color: #ff4d4f;
}

.error-tip {
  display: block;
  margin-top: 4px;
  color: #ff4d4f;
  font-size: 12px;
  line-height: 1.4;
}

/* 密码显示切换按钮 */
.password-wrapper {
  position: relative;
}

.toggle-password {
  position: absolute;
  right: 10px;
  top: 50%;
  transform: translateY(-50%);
  background: none;
  border: none;
  color: #666;
  font-size: 12px;
  padding: 4px 6px;
  cursor: pointer;
  width: auto;
  height: auto;
  transition: color 0.2s;
}

.toggle-password:hover {
  color: #0066ff;
  background: none;
}

/* 密码强度样式 */
.password-strength {
  margin-top: 6px;
}

.strength-bar {
  height: 4px;
  width: 100%;
  border-radius: 2px;
  transition: background-color 0.3s;
}

.strength-bar.weak {
  background-color: #ff4d4f;
  width: 33%;
}

.strength-bar.medium {
  background-color: #faad14;
  width: 66%;
}

.strength-bar.strong {
  background-color: #52c41a;
  width: 100%;
}

.strength-text {
  display: block;
  margin-top: 4px;
  font-size: 12px;
  color: #666;
}

/* 按钮样式 */
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
  opacity: 0.8;
}

/* 底部链接 */
.form-footer {
  text-align: center;
  margin-top: 20px;
  color: #666;
  font-size: 14px;
}

.form-footer a {
  color: #0066ff;
  text-decoration: none;
  margin-left: 4px;
}

.form-footer a:hover {
  text-decoration: underline;
}

/* 全局消息提示 */
.error-message, .success-message {
  margin-top: 15px;
  padding: 10px;
  border-radius: 4px;
  text-align: center;
  font-size: 14px;
}

.error-message {
  background-color: #ffebee;
  color: #c62828;
}

.success-message {
  background-color: #e8f5e9;
  color: #2e7d32;
}
</style>