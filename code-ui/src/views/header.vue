<template>
  <nav class="navbar">
    <div class="logo">
      <a  href="http://www.nebulas.ink"  target="_blank">
        <img src="../assets/homePage/images/nebulas.png" width="80px" height="60px" alt="NeBuLas">
      </a>
    </div>
    <div class="nav-links">
      <a @click="toModuleIndex('/index',1)"  :class="{ active: activeIndex === 1 }">首页</a>
      <a  href="http://it.nebulas.ink" title="星云IT题库平台" target="_blank"> 题库平台</a>
      <a href="http://soft.nebulas.ink"  title="星云软件项目发布平台" target="_blank">软件项目发布平台</a>
      <a  href="http://nb.nebulas.ink" title="星云编程社区交流平台" target="_blank" >编程社区交流平台</a>
      <a @click=" toModuleIndex('/contactUs',1)"  :class="{ active: activeIndex === 2 }">联系我们</a>
      <a  @click=" toModuleIndex('/joinUs',3)"  :class="{ active: activeIndex === 3 }">加入我们</a>
      <a @click="toModuleIndex('/projectMember',4)" :class="{ active: activeIndex === 4 }" target="_blank">项目成员</a>
      <a @click="toModuleIndex('/projectLeaderboard',5)" :class="{ active: activeIndex === 5 }" target="_blank">  排行榜 </a>
      <a @click="toModuleIndex('/projectLeaderboard',6)" :class="{ active: activeIndex === 6 }" target="_blank">  证书背调 </a>
      <template v-if="user.token">
        <div id="user-center">
          <el-image
              class="user-avatar"
              :src=" user.avatar"
              :preview-src-list="[user.avatar] " title="头像" >
          </el-image>
          <span  class="user-name" :title="user.nickName" @mouseenter="showDropdown = true" @mouseleave="showDropdown = false">
          {{user.nickName}}
        </span>
          <!-- 下拉列表 -->
          <ul v-if="showDropdown" class="dropdown-list" @mouseenter="showDropdown = true" @mouseleave="showDropdown = false">
            <li @click="handleOptionClick('logout')">退  出</li>
          </ul>
        </div>
      </template>
      <template v-else>
        <a @click="handlerLogin" target="_blank">登录</a>
        <a @click="handlerUserRegister" target="_blank">注册</a>
      </template>
    </div>
  </nav>

  <el-dialog title="用户登录"  v-model="open" width="30%" center>
    <login :closeDialog="closeDialog" ></login>
  </el-dialog>

  <el-dialog title="用户注册"  v-model="registerOpen" width="40%" center>
    <userRegister :closeDialogRegister="closeDialogRegister"></userRegister>
  </el-dialog>
</template>

<script setup>
import { ref } from 'vue';
import login from "./userLogin.vue"
import userRegister from "./userRegister.vue"
import userStore from "@/store/modules/user"
import { useRouter } from 'vue-router';

const user = userStore();


const router = useRouter();
const activeIndex = ref(1);
const open = ref(false);
const registerOpen = ref(false);
const showDropdown = ref(false);
const { proxy } = getCurrentInstance()

function toModuleIndex( routerName,index) {
  router.push({ path: routerName });
  activeIndex.value = index;
}

function handlerLogin(){
  open.value = true;
}
function handlerUserRegister(){
  registerOpen.value = true;
}
function closeDialog() {
  open.value = false;
  router.push({ path: '/index' });
}
function closeDialogRegister() {
  registerOpen.value = false;
  router.push({ path: '/' });
}

function handleOptionClick(option) {
  switch (option) {
    case 'logout':
      // 处理退出逻辑
      user.logOut();
      break;
    default:
      break;
  }
  showDropdown.value = false;
}
</script>

<style>
#user-center{
  display: flex;
  position: relative;
}
#user-center .user-avatar{
  width: 40px;
  height: 40px;
  border-radius: 10px;
}
#user-center .user-avatar:hover{
  cursor: pointer;
  background-color: blue;
}
.user-name{
  font-size: 18px;
  line-height: 40px;
  color:red;
  margin-left: 10px;
}
.user-name:hover{
  cursor: pointer;
  border-radius: 10px;
  color:red;
  font-size: 18px;
}
.dropdown-list {
  position: absolute;
  top: 40px;
  left: 20px;
  list-style: none;
  z-index: 1000;
  text-align: center;
}
.dropdown-list li {
  cursor: pointer;
  font-size: 18px;
  line-height: 30px;
  padding-left: 10px;
  padding-right: 10px;
  background-color: rgba(255,202,183,0.2);

}
.dropdown-list li:hover {
  background-color: rgba(255,202,183,0.9);
  border-radius: 5px;
}
</style>
