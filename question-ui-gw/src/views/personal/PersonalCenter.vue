<!-- NEW_FILE_CODE
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



     <div class="logout-button">
      <button @click="logout">退出登录</button>
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


// 退出登录方法
const logout = () => {
  // 清除 token
  localStorage.removeItem('Admin-Token');

  // 跳转到登录页
  router.push('/login');

  // 刷新页面
  window.location.reload();
};

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


.logout-button {
  margin-top: 30px;
  text-align: center;
}

.logout-button button {
  padding: 10px 20px;
  background-color: #e53935;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.logout-button button:hover {
  background-color: #c62828;
}
</style> -->





<template>
  <div class="personal-center">
    <div class="left-menu">
      <h2>个人中心</h2>
      <div 
        class="menu-item" 
        v-for="section in sections" 
        :key="section.key"
        @click="showSection(section)"
      >
        {{ section.title }}
      </div>
    </div>
    <div class="right-content">
      <component :is="currentComponent"></component>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import PersonalQuestionBank from './PersonalQuestionBank.vue';
import PersonalExamRecords from './ExamRecords.vue';
import PersonalProjectMembers from './ProjectMembers.vue';
import Login from './Login.vue';
import Register from './Register.vue';
import { useUserStore } from '@/store/user';
import authApi from '@/api/user';

const userStore = useUserStore();


const sections = ref([
  {
    key: 'questionBank',
    title: '个人题库',
    component: PersonalQuestionBank,
    requiresAuth: true
  },
  {
    key: 'examRecords',
    title: '笔试记录',
    component: PersonalExamRecords,
    requiresAuth: true
  },
  {
    key: 'projectMembers',
    title: '项目成员',
    component: PersonalProjectMembers,
    requiresAuth: false
  },
  {
    key: 'login',
    title: '登录',
    component: Login,
    requiresAuth: false
  },
  {
    key: 'register',
    title: '注册',
    component: Register,
    requiresAuth: false
  },
  {
    key: 'logout',
    title: '退出账号',
    requiresAuth: true
  }
]);

const currentComponent = ref(null);

// const showSection = (section) => {
//   if (section.key === 'logout') {
//     logout();
//   } else {
//     currentComponent.value = section.component;
//   }
// };
const showSection = (section) => {
  // 处理登录/注册板块的点击
  if ((section.key === 'login' || section.key === 'register')) {
    if (isLoggedIn()) {
      alert('用户已登录，请先退出登录');
      return; // 已登录状态下不切换组件
    }
  }
  if (section.key === 'logout') {
    logout();
  } else {
    currentComponent.value = section.component;
  }
};

const isLoggedIn = () => {
  return !!localStorage.getItem('Admin-Token');
};

// const logout = () => {
//   localStorage.removeItem('Admin-Token');
//   currentComponent.value = null;
//   window.location.reload();
// };
const logout = async () => {
  try {
    // 调用后端退出接口
    await authApi.logout();
  } catch (error) {
    console.error('退出登录接口调用失败', error);
  } finally {
    // 清除本地和全局的用户信息
    userStore.logout();
    window.location.reload();
    router.push('/login');
    // window.location.reload();

  }
};

onMounted(() => {
  if (isLoggedIn()) {
    currentComponent.value = sections.value[0].component;
  } else {
    currentComponent.value = sections.value[3].component;
  }
});
</script>

<style scoped>
.personal-center {
  display: flex;
  /* height: 100vh; */
  height: auto;
}

.left-menu {
  width: 200px;
  background-color: #f9f9f9;
  padding: 20px;
  border-right: 1px solid #ddd;
}

.left-menu h2 {
  margin-top: 0;
  font-size: 18px;
  margin-bottom: 20px;
}

.menu-item {
  padding: 10px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.menu-item:hover {
  background-color: #eaeaea;
}

.right-content {
  flex: 1;
  padding: 20px;
  height: auto;
}
</style>