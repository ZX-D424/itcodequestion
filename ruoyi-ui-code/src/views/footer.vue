<template>

  <footer>
    <p>&copy; 湘ICP备2024092148号-3   2025 星云编程快速学习平台. 版权所有.</p>
    <p>
      |
      <a href="#">湖南星云科技</a> |
      <a href="#">星云软件发布平台</a> |
      <a href="#">星云编程交流社区平台</a> |
      <a @click="router.push({path : '/contactUs'})">联系我们</a> |
      <a @click="router.push({path : '/joinUs'})">加入我们</a>|
      <a href="#">隐私政策</a> |
      <a href="#">使用条款</a> |

    </p>
  </footer>
</template>

<script setup>

const router = useRouter();

function toModuleIndex( routerName) {
  router.push({ path: routerName });
}
</script>
