<!-- NEW_FILE_CODE
<template>
  <div class="register-container">
    <div class="register-form">
      <h2>用户注册</h2>
      <form @submit.prevent="handleRegister">
        <div class="form-group">
          <label for="username">用户名</label>
          <input 
            type="text" 
            id="username" 
            v-model="registerForm.username" 
            required
            placeholder="请输入用户名"
          />
        </div>
        
        <div class="form-group">
          <label for="email">邮箱</label>
          <input 
            type="email" 
            id="email" 
            v-model="registerForm.email" 
            required
            placeholder="请输入邮箱"
          />
        </div>

        <div class="form-group">
        <label for="mobile">手机号</label>
        <input v-model="registerForm.mobile" type="tel" id="mobile" pattern="\\d{11}" />
      </div>
        
        <div class="form-group">
          <label for="password">密码</label>
          <input 
            type="password" 
            id="password" 
            v-model="registerForm.password" 
            required
            placeholder="请输入密码"
          />
        </div>
        
        <div class="form-group">
          <label for="confirmPassword">确认密码</label>
          <input 
            type="password" 
            id="confirmPassword" 
            v-model="registerForm.confirmPassword" 
            required
            placeholder="请再次输入密码"
          />
        </div>
        
        <button type="submit" :disabled="loading">
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
import { ref, computed } from 'vue';
import { useRouter } from 'vue-router';
import authApi from '@/api/user';


const router = useRouter();

const registerForm = ref({
  username: '',
  email: '',
  password: '',
  confirmPassword: '',
  userType: '00',
  mobile: ''
});

const loading = ref(false);
const error = ref('');
const success = ref('');

// 验证密码是否一致
const passwordsMatch = computed(() => {
  return registerForm.value.password === registerForm.value.confirmPassword;
});

// 处理注册
const handleRegister = async () => {
  // 重置消息
  error.value = '';
  success.value = '';
  
  // 验证密码一致性
  if (!passwordsMatch.value) {
    error.value = '两次输入的密码不一致';
    return;
  }
  
  loading.value = true;
  
  try {
    await authApi.register({
      username: registerForm.value.username,
      password: registerForm.value.password,
      email: registerForm.value.email,
      mobile: registerForm.value.mobile,
      userType: registerForm.value.userType
    });
    
    success.value = '注册成功！请登录您的账号';
    
    registerForm.value = {
      username: '',
      email: '',
      password: '',
      confirmPassword: '',
      mobile: '',
      userType: '00'
    };
    
    setTimeout(() => {
      router.push('/login');
    }, 3000);
  } catch (err) {
    // error.value = '注册失败，请稍后重试';
     // 处理错误响应
    const errorMsg = err.response?.data?.msg || '注册失败，请稍后重试';
    error.value = errorMsg;
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
  min-height: 400px;
  padding: 20px;
  height: auto;
}

.register-form {
  width: 100%;
  max-width: 400px;
  padding: 30px;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.register-form h2 {
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

.success-message {
  margin-top: 15px;
  padding: 10px;
  background-color: #e8f5e9;
  color: #2e7d32;
  border-radius: 4px;
  text-align: center;
}
</style> -->






<!-- 111111111111111111111111111111111 -->

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

        <!-- 邮箱验证码 -->
        <div class="form-group">
          <label for="emailCode">邮箱验证码</label>
          <div class="code-container">
            <input 
              type="text" 
              id="emailCode" 
              v-model="registerForm.emailCode" 
              placeholder="请输入邮箱验证码"
              @blur="validateField('emailCode')"
              :class="{ 'invalid': errors.emailCode }"
            />
            <button 
              type="button" 
              class="get-code-btn" 
              @click="getEmailCode"
              :disabled="isCodeSending || !isEmailValid"
            >
              {{ isCodeSending ? `${countDown}s后重新获取` : '获取验证码' }}
            </button>
          </div>
          <span class="error-tip" v-if="errors.emailCode">{{ errors.emailCode }}</span>
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
      
      <div v-if="error" class="error-message" :class="{ shake: errorShake }">
        {{ error }}
      </div>
      
      <div v-if="success" class="success-message">
        {{ success }}
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, watch, onUnmounted, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import authApi from '@/api/user';
import { setToken, getToken } from '@/utils/auth';
// import { useUserStore } from '@/store/user';
// import { useCaptchaStore } from '@/store/captcha';

const router = useRouter();

// 表单数据
const registerForm = ref({
  username: '',
  email: '',
  emailCode: '',
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
const isCodeSending = ref(false); // 验证码发送状态
const countDown = ref(60); // 倒计时秒数
const codeTimer = ref(null); // 计时器
const errorShake = ref(false); // 错误抖动动画

// 错误信息存储
const errors = ref({
  username: '',
  email: '',
  emailCode: '',
  mobile: '',
  password: '',
  confirmPassword: ''
});

// 邮箱是否有效（用于控制获取验证码按钮状态）
const isEmailValid = computed(() => {
  return !errors.value.email && registerForm.value.email.trim() !== '';
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
      
    case 'emailCode':
      if (!value.trim()) {
        errors.value.emailCode = '请输入邮箱验证码';
      } else if (value.length < 4) {
        errors.value.emailCode = '验证码格式不正确';
      } else {
        errors.value.emailCode = '';
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
  // 包含数字 加1分
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
    && registerForm.value.emailCode
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

// 监听邮箱变化，清空验证码
watch(
  () => registerForm.value.email,
  () => {
    registerForm.value.emailCode = '';
    errors.value.emailCode = '';
  }
);

// 获取邮箱验证码
// const getEmailCode = async () => {
//   // 再次校验邮箱
//   validateField('email');
//   if (!isEmailValid.value) return;

//   try {
//     isCodeSending.value = true;
//     // 调用发送验证码接口
//     // await authApi.sendEmailCode({ 
//     //   email: registerForm.value.email 
//     // });
//     // 修改后（直接传递邮箱字符串，而非对象）
// await authApi.sendEmailCode(registerForm.value.email);
//     // 开始倒计时
//     codeTimer.value = setInterval(() => {
//       countDown.value--;
//       if (countDown.value <= 0) {
//         clearInterval(codeTimer.value);
//         isCodeSending.value = false;
//         countDown.value = 60;
//       }
//     }, 1000);
    
//     // 显示提示
//     error.value = '';
//     success.value = '验证码已发送，请查收邮件';
//     setTimeout(() => {
//       success.value = '';
//     }, 3000);
//   } catch (err) {
//     error.value = err.response?.data?.msg || '验证码发送失败，请重试';
//     triggerErrorShake();
//     // 失败后重置状态
//     isCodeSending.value = false;
//     clearInterval(codeTimer.value);
//     countDown.value = 60;
//   }
// };

const getEmailCode = async () => {
  // 再次校验邮箱
  validateField('email');
  if (!isEmailValid.value) return;

  try {
    isCodeSending.value = true;
    // 调用发送验证码接口
    // await authApi.sendEmailCode({ 
    //   email: registerForm.value.email 
    // });
    // 修改后（直接传递邮箱字符串，而非对象）
await authApi.sendEmailCode(registerForm.value.email
  , {
      timeout: 200000  // 单独设置超时时间，单位毫秒
    }
);
    // 开始倒计时
    codeTimer.value = setInterval(() => {
      countDown.value--;
      if (countDown.value <= 0) {
        clearInterval(codeTimer.value);
        isCodeSending.value = false;
        countDown.value = 60;
      }
    }, 1000);
    
    // 显示提示
    error.value = '';
    success.value = '验证码已发送，请查收邮件';
    setTimeout(() => {
      success.value = '';
    }, 3000);
  } catch (err) {
    error.value = err.response?.msg || '验证码发送失败，请重试';
    triggerErrorShake();
    // 失败后重置状态
    isCodeSending.value = false;
    clearInterval(codeTimer.value);
    countDown.value = 60;
  }
};
// 获取邮箱验证码
// const getEmailCode = async () => {
//   // 再次校验邮箱
//   validateField('email');
//   if (!isEmailValid.value) return;

//   try {
//     isCodeSending.value = true;
//     // 调用发送验证码接口
//     // await authApi.sendEmailCode({ 
//     //   email: registerForm.value.email 
//     // });
//     // 修改后（直接传递邮箱字符串，而非对象）
// await authApi.sendEmailCode(registerForm.value.email);
//     // 开始倒计时
//     codeTimer.value = setInterval(() => {
//       countDown.value--;
//       if (countDown.value <= 0) {
//         clearInterval(codeTimer.value);
//         isCodeSending.value = false;
//         countDown.value = 60;
//       }
//     }, 1000);
    
//     // 显示提示
//     error.value = '';
//     success.value = '验证码已发送，请查收邮件';
//     setTimeout(() => {
//       success.value = '';
//     }, 3000);
//   } catch (err) {
//     error.value = err.response?.msg || '验证码发送失败，请重试';
//     triggerErrorShake();
//     // 失败后重置状态
//     isCodeSending.value = false;
//     clearInterval(codeTimer.value);
//     countDown.value = 60;
//   }
// };

// 触发错误抖动动画
const triggerErrorShake = () => {
  errorShake.value = true;
  setTimeout(() => {
    errorShake.value = false;
  }, 500);
};

// 处理注册
const handleRegister = async () => {
  // 提交前全量校验
  ['username', 'email', 'emailCode', 'mobile', 'password', 'confirmPassword'].forEach(field => {
    validateField(field);
  });
  
  if (!isFormValid.value) {
    triggerErrorShake();
    return;
  }
  
  loading.value = true;
  error.value = '';
  success.value = '';
  
  try {
    const response = await authApi.register({
      username: registerForm.value.username,
      password: registerForm.value.password,
      email: registerForm.value.email,
      emailCode: registerForm.value.emailCode,
      mobile: registerForm.value.mobile,
      userType: registerForm.value.userType
    });
    
    // 如果后端返回消息，使用后端消息
    if (response.msg) {
      success.value = response.msg;
    } else {
      success.value = '注册成功！即将跳转到登录页面...';
    }
    
    // 3秒后跳转登录页
    setTimeout(() => {
      router.push('/login');
    }, 3000);
  } catch (err) {
    error.value = err.response?.msg || '注册失败，请稍后重试';
    triggerErrorShake();
    // 注册失败后清空敏感字段
    registerForm.value.password = '';
    registerForm.value.confirmPassword = '';
    registerForm.value.emailCode = '';
  } finally {
    loading.value = false;
  }
};

// 跳转到登录页面

//   try {
//     const response = await authApi.register({
//       username: registerForm.value.username,
//       password: registerForm.value.password,
//       email: registerForm.value.email,
//       emailCode: registerForm.value.emailCode,
//       mobile: registerForm.value.mobile,
//       userType: registerForm.value.userType
//     });
    
//     // 如果后端返回消息，使用后端消息
//     if (response.data.msg) {
//       success.value = response.msg;
//     } else {
//       success.value = '注册成功！即将跳转到登录页面...';
//     }
    
//     // 3秒后跳转登录页
//     setTimeout(() => {
//       router.push('/login');
//     }, 3000);
//   } catch (err) {
//     error.value = err.response?.msg || '注册失败，请稍后重试';
//     triggerErrorShake();
//     // 注册失败后清空敏感字段
//     registerForm.value.password = '';
//     registerForm.value.confirmPassword = '';
//     registerForm.value.emailCode = '';
//   } finally {
//     loading.value = false;
//   }
// };

const goToLogin = () => {
  router.push('/login');
};


// 在onMounted中添加已登录检查
onMounted(async () => {
  if (getToken()) {
    // 显示已登录提示
    alert('用户已登录，请先退出登录');
    router.push('/profile');
  } else {
    // refreshCaptcha();
  }
});

// 组件卸载时清除计时器
onUnmounted(() => {
  if (codeTimer.value) {
    clearInterval(codeTimer.value);
  }
});


</script>

<style scoped>
.register-container {
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

.register-form {
  width: 100%;
  max-width: 400px;
  padding: 36px;
  border: 1px solid #e5e7eb;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.05);
  background-color: white;
}

.register-form h2 {
  text-align: center;
  margin-bottom: 30px;
  color: #333;
  font-weight: 600;
  font-size: 24px;
}

.form-group {
  margin-bottom: 22px;
  position: relative;
}

.form-group label {
  display: block;
  margin-bottom: 6px;
  font-weight: 500;
  color: #555;
  font-size: 14px;
}

.form-group input {
  width: 100%;
  padding: 12px 15px;
  border: 1px solid #ddd;
  border-radius: 6px;
  font-size: 16px;
  box-sizing: border-box;
  transition: border-color 0.3s, box-shadow 0.3s;
}

.form-group input:focus {
  border-color: #0066ff;
  outline: none;
  box-shadow: 0 0 0 2px rgba(0, 102, 255, 0.1);
}

.form-group input::placeholder {
  color: #c9cdD4;
}

/* 错误状态样式 */
.form-group input.invalid {
  border-color: #ff4d4f;
}

.form-group input.invalid:focus {
  box-shadow: 0 0 0 2px rgba(255, 77, 79, 0.1);
}

.error-tip {
  display: block;
  margin-top: 4px;
  color: #ff4d4f;
  font-size: 12px;
  line-height: 1.4;
  height: 16px;
}

/* 验证码容器样式 */
.code-container {
  display: flex;
  gap: 10px;
}

.get-code-btn {
  flex-shrink: 0;
  width: auto;
  padding: 0 15px;
  background-color: #f0f2f5;
  color: #4e5969;
  font-size: 14px;
  border: 1px solid #ddd;
  border-radius: 6px;
  cursor: pointer;
  transition: all 0.2s;
  height: 46px;
}

.get-code-btn:disabled {
  background-color: #e5e6eb;
  color: #c9cdD4;
  cursor: not-allowed;
}

.get-code-btn:not(:disabled):hover {
  background-color: #e5e6eb;
  color: #0066ff;
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
  transition: background-color 0.3s, width 0.3s;
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
button[type="submit"] {
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
}

button[type="submit"]:hover:not(:disabled) {
  background-color: #0052cc;
}

button[type="submit"]:disabled {
  background-color: #ccc;
  cursor: not-allowed;
  opacity: 0.8;
}

/* 底部链接 */
.form-footer {
  text-align: center;
  margin-top: 24px;
  color: #666;
  font-size: 14px;
}

.form-footer a {
  color: #0066ff;
  text-decoration: none;
  margin-left: 4px;
  transition: color 0.2s;
}

.form-footer a:hover {
  color: #0052cc;
  text-decoration: underline;
}

/* 消息提示 */
.error-message, .success-message {
  margin-top: 16px;
  padding: 10px 12px;
  border-radius: 6px;
  text-align: center;
  font-size: 14px;
  transition: all 0.2s;
}

.error-message {
  background-color: #fff1f0;
  color: #ff4d4f;
}

.success-message {
  background-color: #e8f5e9;
  color: #2e7d32;
}

/* 错误抖动动画 */
.shake {
  animation: shake 0.5s cubic-bezier(0.36, 0.07, 0.19, 0.97) both;
}

@keyframes shake {
  10%, 90% { transform: translateX(-1px); }
  20%, 80% { transform: translateX(2px); }
  30%, 50%, 70% { transform: translateX(-3px); }
  40%, 60% { transform: translateX(3px); }
}

/* 响应式调整 */
@media (max-width: 480px) {
  .register-form {
    padding: 24px;
  }
  
  .code-container {
    flex-direction: column;
  }
  
  .get-code-btn {
    width: 100%;
  }
}
</style>
