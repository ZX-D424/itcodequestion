

<template>
  <nav class="navbar">
    <div class="logo">
      <a  @click="toModuleIndex('/index')" target="_blank">
        <img src="../assets/homePage/images/nebulas.png" width="80px" height="60px" alt="NeBuLas">
      </a>
    </div>
    <div class="nav-links">
      <a @click="toModuleIndex('/index',1)"  :class="{ active: activeIndex === 1 }">首页</a>
      <a href="#" :class="{ active: activeIndex === 2 }">项目案例</a>
      <a href="#" :class="{ active: activeIndex === 3 }">开源项目</a>
      <a href="#" :class="{ active: activeIndex === 4 }">视频教程</a>
      <a @click="toModuleIndex('/contactUs',5)" :class="{ active: activeIndex === 5}"> 联系我们</a>
      <a @click="toModuleIndex('/projectMember',6)" :class="{ active: activeIndex === 6 }" target="_blank">项目成员</a>
      <a @click="toModuleIndex('/projectIndex',7)" :class="{ active: activeIndex === 7 }" target="_blank">实战项目</a>
      <a @click="toModuleIndex('/projectLeaderboard',8)" :class="{ active: activeIndex === 8 }" target="_blank">  排行榜 </a>
      <a href="#" title="星云社区" :class="{ active: activeIndex === 9 }">星云社区</a>
      <template v-if="user.token">
      <div id="user-center">
        <el-image
            class="user-avatar"
            :src=" user.avatar"
            :preview-src-list="[user.avatar] " title="头像" >
        </el-image>
        <span  class="user-name" :title="user.nickName">
          {{user.nickName}}
        </span>
        <!-- 退出需要做定位-->
        <!--
        <a @click="user.logOut()" class="logOut" >
          退出
        </a>
        -->
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
import login from "./userLogin.vue"
import userRegister from "./userRegister.vue"
import userStore from "@/store/modules/user"

const user = userStore();

console.log("userStore---------------->:",user)

const router = useRouter();
const activeIndex = ref(1);
const open = ref(false);
const registerOpen = ref(false);

function toModuleIndex( routerName,index) {
  router.push({ path: routerName });
  activeIndex.value = index;
  console.log("activeIndex.value--------------------->",activeIndex.value);
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

</script>

<style>
#user-center{
  display: flex;
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
</style>
