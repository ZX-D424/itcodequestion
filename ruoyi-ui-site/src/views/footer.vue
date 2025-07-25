<template>

  <footer>
    <p>&copy; 湘ICP备2024092148号-3   2025 星云编程快速学习平台. 版权所有.</p>
    <p>
      <a href="#">隐私政策</a> |
      <a href="#">使用条款</a> |
      <a @click="router.push({path : '/contactUs'})">联系我们</a>
    </p>
  </footer>
</template>

<script setup>

const router = useRouter();

function toModuleIndex( routerName) {
  router.push({ path: routerName });
}
</script>
