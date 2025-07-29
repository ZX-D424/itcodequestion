<template>

  <footer>
    <p>&copy; 湘ICP备2024092148号-3   2025 星云编程快速学习平台. 版权所有.</p>
    <p>
      |
      <a href="http://www.nebulas.ink" >湖南星云科技</a> |
      <a href="http://soft.nebulas.ink"  title="星云软件发布平台" target="_blank">软件发布平台</a> |
      <a  href="http://code.nebulas.ink" title="星云编程快速学习平台" target="_blank" >编程学习平台</a>|
      <a  href="http://nb.nebulas.ink" title="星云社区" target="_blank" >社区平台</a>|
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
