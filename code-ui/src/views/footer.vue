<template>

  <footer>
    <p>&copy; 湘ICP备2024092148号-3   2025 星云编程快速学习平台. 版权所有.</p>
    <p>
      |
      <a href="http://www.nebulas.ink" title="湖南星云科技"  target="_blank">湖南星云科技</a> |
      <a  href="http://code.nebulas.ink" title="星云编程快速学习平台"  target="_blank">星云编程快速学习平台</a>|
      <a  href="http://it.nebulas.ink" title="星云IT题库平台" target="_blank"> 星云IT题库平台</a>|
      <a href="http://soft.nebulas.ink"  title="星云软件项目发布平台" target="_blank">星云软件项目发布平台</a> |
      <a  href="http://nb.nebulas.ink" title="星云编程社区交流平台" target="_blank" >星云编程社区交流平台</a>|
      <a @click="router.push({path : '/contactUs'})">联系我们</a> |
      <a @click="router.push({path : '/joinUs'})">加入我们</a>|

    </p>
  </footer>
</template>

<script setup>
import { useRouter } from 'vue-router';
const router = useRouter();

</script>
