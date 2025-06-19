<template>
  <nav class="navbar">
    <div class="logo">
      <a  @click="router.push({path : '/homePage'})" target="_blank">
      <img src="@/assets/homePage/images/nebulas.png"  width="80px" height="60px" alt="NeBuLas">
      </a></div>
    <div class="nav-links">
      <a  @click="router.push({path : '/homePage'})" target="_parent"> 首页</a>
      <a href="#">项目案例</a>
      <a href="#">开源项目</a>
      <a href="#">视频教程</a>
      <a href="#">联系我们</a>
      <a @click="toModuleIndex('/projectMember')" target="_blank" class="active" >团队简介</a>
      <a  @click="toModuleIndex('/projectIndex')" target="_blank">实战项目</a>
      <a href="#" title="星云社区">星云社区</a>

    </div>
  </nav>



  <!-- 视频容器 -->
  <div class="video-background">
    <video autoplay muted loop>
      <source src="@/assets/homePage/video/HDAHKHFEC8afY2e.mp4" type="video/mp4">
      您的浏览器不支持视频标签。
    </video>
  </div>

  <section class="section">

  </section>




  <footer>
    <p>&copy; 湘ICP备2024092148号-3   2025 星云网站. 版权所有.</p>
    <p>
      <a href="#">隐私政策</a> |
      <a href="#">使用条款</a> |
      <a href="#">联系我们</a>
    </p>
  </footer>


</template>

<script setup name="projectMember">
const route = useRoute()
const router = useRouter();

function toModuleIndex( routerName) {
  router.push({ path: routerName });
}

</script>

<style >
@import  "@/assets/homePage/css/index.css";
</style>
