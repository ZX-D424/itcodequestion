NEW_FILE_CODE
<template>
  <div class="exam-records">
    <h1>笔试记录</h1>
    <div v-if="isLoggedIn">
      <p>这里是笔试记录内容区域</p>
      <!-- 在这里添加笔试记录的具体内容 -->
    </div>
    <div v-else>
      <p>请先登录以查看笔试记录</p>
      <button @click="goToLogin">前往登录</button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const isLoggedIn = ref(false);

// 检查登录状态
const checkLoginStatus = () => {
  isLoggedIn.value = !!localStorage.getItem('Admin-Token');
};

// 跳转到登录页面
const goToLogin = () => {
  router.push('/login');
};

onMounted(() => {
  checkLoginStatus();
});
</script>

<style scoped>
.exam-records {
  padding: 20px;
}

.exam-records h1 {
  color: #333;
  margin-bottom: 20px;
}

button {
  padding: 10px 20px;
  background-color: #0066ff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #0052cc;
}
</style>