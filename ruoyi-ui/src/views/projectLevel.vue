
<template>

  <!--导航栏-->
  <nav class="navbar" >
    <div class="logo">
      <a  @click="router.push({path : '/homePage'})" target="_blank">
        <img src="../assets/homePage/images/nebulas.png" width="80px" height="60px" alt="NeBuLas">
      </a>
    </div>
    <div class="nav-links">
      <a @click="router.push({path : '/homePage'})" target="_parent" >首页</a>
      <a href="#">项目案例</a>
      <a href="#">开源项目</a>
      <a href="#">视频教程</a>
      <a href="#">联系我们</a>
      <a @click="toModuleIndex('/projectMember')" >项目成员</a>
      <a  @click="toModuleIndex('/projectIndex')"  class="active" target="_blank">实战项目</a>
      <a href="#" >  排行榜 </a>
      <a href="#" >  奖杯-证书 </a>
      <a href="#" title="星云社区">星云社区</a>
      <a  @click="toModuleIndex('/login')" target="_blank">登录</a>
    </div>
  </nav>

  <!-- 视频容器 -->
  <div class="video-background">
    <video autoplay muted loop>
      <source src="@/assets/homePage/video/HDAHKHFEC8afY2e.mp4" type="video/mp4">
      您的浏览器不支持视频标签。
    </video>
  </div>


  <div class="level">
    <section class="section">
      <h2>{{moduleName}} </h2>
      <div class="cards">
        <div class="left-list" >
          <div class="title">开始闯关</div>

        </div>
        <div class="right-list"  > 右边</div>
      </div>
    </section>
  </div>

  <footer>
    <p>&copy; 湘ICP备2024092148号-3   2025 星云网站. 版权所有.</p>
    <p>
      <a href="#">隐私政策</a> |
      <a href="#">使用条款</a> |
      <a href="#">联系我们</a>
    </p>
  </footer>


</template>


<script setup name="projectLevel">

const route = useRoute()
const router = useRouter();

const moduleId = ref(route.query.id);
const moduleName = ref(route.query.name);


function toModuleIndex( routerName) {
  router.push({ path: routerName });
}


</script>

<style  >
@import  "@/assets/homePage/css/index.css";
</style>
