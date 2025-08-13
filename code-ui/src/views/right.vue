
<template>
  <!-- 右侧漂浮元素 -->
  <div class="floating-links">
    <a @click ="toModuleIndex('/userCenter')" class="floating-link">个人中心</a>
    <a @click ="toModuleIndex('/projectMember')" class="floating-link">项目成员</a>
    <a @click ="toModuleIndex('/userCenter')" class="floating-link">闯关榜单</a>
<!--    <a @click="toModuleIndex('/contactUs')"  class="floating-link">联系我们</a>-->
    <a href="#" class="floating-link">证书申请</a>
    <a href="#" class="floating-link">证书背调</a>
    <a href="#" class="floating-link">简历指导</a>
    <a href="#" class="floating-link">面试模拟</a>
  </div>
</template>

<script setup>
import {useRouter} from "vue-router";

const router = useRouter();

function toModuleIndex( routerName) {
  // 获取完整的路由链接
  const url = router.resolve({ path: routerName }).href;
  // 在新窗口打开链接
  window.open(url, '_blank');
}
</script>


<style scoped lang="scss">

/* 右侧漂浮元素样式 */
.floating-links {
  position: fixed;
  right: 20px;
  top: 50%;
  transform: translateY(-50%);
  display: flex;
  flex-direction: column;
  gap: 10px;
  z-index: 1000;
}

.floating-link {
  background-color: rgba(255, 182, 193,0.2);
  color: white;
  padding: 10px 15px;
  text-align: center;
  text-decoration: none;
  border-radius: 5px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
  transition: background-color 0.3s;
}

.floating-link:hover {
  background-color: rgba(255, 182, 193,1);
}
</style>
