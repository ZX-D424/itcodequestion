<script setup>

</script>

<template>
  <!-- 右侧漂浮元素 -->
  <div class="floating-links">
    <a href="#" class="floating-link">项目成员</a>
    <a href="#" class="floating-link">闯关榜单</a>
    <a href="#" class="floating-link">联系我们</a>
    <a href="#" class="floating-link">证书申请</a>
    <a href="#" class="floating-link">证书背调</a>
    <a href="#" class="floating-link">简历指导</a>
    <a href="#" class="floating-link">面试模拟</a>
    <a href="#" class="floating-link">加入我们</a>
  </div>
</template>

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
  background-color: #42b983;
  color: white;
  padding: 10px 15px;
  text-align: center;
  text-decoration: none;
  border-radius: 5px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
  transition: background-color 0.3s;
}

.floating-link:hover {
  background-color: #33a06f;
}
</style>
