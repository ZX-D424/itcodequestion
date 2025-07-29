<template>
  <nav class="navbar">
    <div class="logo">
      <a  @click="toModuleIndex('/index')" target="_blank">
        <img src="../assets/homePage/images/nebulas.png" width="80px" height="60px" alt="NeBuLas">
      </a>
    </div>
    <div class="nav-links">
      <a @click="toModuleIndex('/index',1)"  :class="{ active: activeIndex === 1 }">首页</a>
      <a  @click="proxy.$modal.msgError('建设中')" :class="{ active: activeIndex === 2 }">项目案例</a>
      <a  @click="proxy.$modal.msgError('建设中')" :class="{ active: activeIndex === 3 }">开源项目</a>
      <a  @click="proxy.$modal.msgError('建设中')" :class="{ active: activeIndex === 4 }">视频教程</a>
      <a @click="toModuleIndex('/contactUs',5)" :class="{ active: activeIndex === 5}"> 联系我们</a>
      <a  @click="proxy.$modal.msgError('建设中')" title="加入我们" :class="{ active: activeIndex === 6 }">加入我们</a>
      <a  href="http://soft.nebulas.ink" title="星云软件发布平台" target="_blank">软件发布平台</a>
      <a  href="http://code.nebulas.ink" title="星云编程快速学习平台" target="_blank" >编程学习平台</a>
      <a  href="http://nb.nebulas.ink" title="星云社区" target="_blank" >社区平台</a>
    </div>
  </nav>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const activeIndex = ref(1);
const { proxy } = getCurrentInstance()

function toModuleIndex( routerName,index) {
  router.push({ path: routerName });
  activeIndex.value = index;
  //console.log("activeIndex.value--------------------->",activeIndex.value);
}

</script>
