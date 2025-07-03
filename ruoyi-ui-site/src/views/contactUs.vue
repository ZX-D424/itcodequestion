<template>
  <nav class="navbar">
    <div class="logo">
      <a  @click="router.push({path : '/homePage'})" target="_blank">
      <img src="@/assets/homePage/images/nebulas.png"  width="80px" height="60px" alt="NeBuLas">
      </a>
    </div>
    <div class="nav-links">
      <a @click="router.push({path : '/homePage'})">首页</a>
      <a href="#">项目案例</a>
      <a href="#">开源项目</a>
      <a href="#">视频教程</a>
      <a href="#" class="active">联系我们</a>
      <a @click="toModuleIndex('/projectMember')" target="_blank">项目成员</a>
      <a  @click="toModuleIndex('/projectIndex')" target="_blank">实战项目</a>
      <a @click="toModuleIndex('/projectLeaderboard')" target="_blank">  排行榜 </a>
      <a href="#" title="星云社区">星云社区</a>
      <a  @click="toModuleIndex('/login')" target="_blank">登录</a>
    </div>
  </nav>


  <section class="hero">
    <div class="hero-content">
      <h1>引领未来科技</h1>
      <p>突破创新边界，打造智能化解决方案，助力企业迈向数字化新时代</p>
      <br/>
      <div class="typewriter">地址：广东省广州市增城区新塘镇长风国际8栋801</div>
      <br/>  <br/>
      <div class="typewriter">邮箱地址：305930712@qq.com</div>
    </div>
  </section>


  <!-- 视频容器 -->
  <div class="video-background">
    <video autoplay muted loop>
      <source src="@/assets/homePage/video/HDAHKHFEC8afY2e.mp4" type="video/mp4">
      您的浏览器不支持视频标签。
    </video>
  </div>


  <footer>
    <p>&copy; 湘ICP备2024092148号-3   2025 星云网站. 版权所有.</p>
    <p>
      <a href="#">隐私政策</a> |
      <a href="#">使用条款</a> |
      <a @click="router.push({path : '/contactUs'})">联系我们</a>
    </p>
  </footer>


</template>

<script setup name="homePage">
const route = useRoute()
const router = useRouter();

function toModuleIndex( routerName) {
  router.push({ path: routerName });
}

</script>

<style >
@import  "@/assets/homePage/css/index.css";
</style>
