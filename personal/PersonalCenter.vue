NEW_FILE_CODE
<template>
  <div class="personal-center">
    <h1>个人中心</h1>
    <div class="sections">
      <div 
        class="section-card" 
        v-for="section in sections" 
        :key="section.key"
        @click="navigateTo(section)"
      >
        <h3>{{ section.title }}</h3>
        <p>{{ section.description }}</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';
import { ref, onMounted } from 'vue';

const router = useRouter();

// 检查用户是否已登录
const isLoggedIn = () => {
  return !!localStorage.getItem('Admin-Token');
};

// 板块数据
const sections = ref([
  {
    key: 'questionBank',
    title: '个人题库',
    description: '管理您的个人题库',
    requiresAuth: true,
    path: '/personal/question-bank'
  },
  {
    key: 'examRecords',
    title: '笔试记录',
    description: '查看您的笔试记录',
    requiresAuth: true,
    path: '/personal/exam-records'
  },
  {
    key: 'login',
    title: '登录',
    description: '用户登录',
    requiresAuth: false,
    path: '/login'
  },
  {
    key: 'register',
    title: '注册',
    description: '新用户注册',
    requiresAuth: false,
    path: '/register'
  },
  {
    key: 'projectMembers',
    title: '项目成员',
    description: '查看项目成员信息',
    requiresAuth: false,
    path: '/personal/project-members'
  }
]);

// 导航到指定板块
const navigateTo = (section) => {
  // 对于需要登录的板块，检查登录状态
  if (section.requiresAuth && !isLoggedIn()) {
    // 未登录且需要登录，跳转到登录页面，并携带返回地址
    router.push({
      path: '/login',
      query: { redirect: section.path }
    });
  } else {
    // 已登录或不需要登录，直接跳转
    router.push(section.path);
  }
};

// 检查登录状态
onMounted(() => {
  // 可以在这里添加其他初始化逻辑
});
</script>

<style scoped>
.personal-center {
  padding: 20px;
}

.sections {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
  gap: 20px;
  margin-top: 20px;
}

.section-card {
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 20px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.section-card:hover {
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  transform: translateY(-2px);
}

.section-card h3 {
  margin-top: 0;
  color: #333;
}

.section-card p {
  color: #666;
  font-size: 14px;
}
</style>