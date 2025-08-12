<template>
  <!--导航栏-->
  <nav class="navbar" >
    <div class="logo">
      <a  @click="router.push({path : '/index'})" target="_blank">
        <img src="../assets/homePage/images/nebulas.png" width="80px" height="60px" alt="NeBuLas">
      </a>
    </div>
    <div class="nav-links">
      <template v-for="(item, index) in menuDataList">
        <a
            @click="toModuleIndex('/index', item.id,item.name,index)"
            v-bind:class="{ active: activeIndex === index }"
        >
          {{ item.name }}
        </a>
      </template>

    </div>
  </nav>

  <!-- 视频容器 -->
  <div class="video-background">
    <video autoplay muted loop>
      <source src="@/assets/homePage/video/HDAHKHFEC8afY2e.mp4" type="video/mp4">
      您的浏览器不支持视频标签。
    </video>
  </div>

  <el-dialog title="用户登录" v-model="open" width="30%" center>
    <login :closeDialog="() => open = false" />
  </el-dialog>

  <el-dialog title="用户注册" v-model="registerOpen" width="50%" center>
    <userRegister :closeDialog="() => registerOpen = false" />
  </el-dialog>

</template>

<script setup>
import login from "./userLogin.vue"
import userRegister from "./userRegister.vue"
import { ref } from 'vue';
import userStore from "@/store/modules/user"
import { useRouter } from 'vue-router';
import {getMenuDataList} from "@/api/www/menu"

import { onMounted, onUnmounted } from 'vue';

const open = ref(false);
const registerOpen = ref(false)

const handleShowLoginModal = () => {
  open.value = true;
};

const handleShowRegisterModal = () => {
  registerOpen.value = true;
};

onMounted(() => {
  // 添加事件监听
  window.addEventListener('show-login-modal', handleShowLoginModal);
  window.addEventListener('show-register-modal', handleShowRegisterModal);
  initMenuDataList();
});

onUnmounted(() => {
  // 移除事件监听（传入相同的函数）
  window.removeEventListener('show-login-modal', handleShowLoginModal);
  window.removeEventListener('show-register-modal', handleShowRegisterModal);
});

const user = userStore();

const route = useRoute()
const router = useRouter();
const menuDataList = ref([]);
const activeIndex = Number(route.query.index);


function toModuleIndex( routerName,id,name,index) {
  router.push({
    path: routerName,
    query: {menuId: id,   menuName: name,menuIndex: index}
  });

}
//加载菜单
function initMenuDataList() {
  getMenuDataList().then(response => {
    menuDataList.value = response.data;
  });
}

</script>

<style>

</style>
