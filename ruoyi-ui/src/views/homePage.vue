<template>
  <nav class="navbar">
    <div class="logo">
      <a  @click="router.push({path : '/homePage'})" target="_blank">
      <img src="@/assets/homePage/images/nebulas.png"  width="80px" height="60px" alt="NeBuLas">
      </a>
    </div>
    <div class="nav-links">
      <a href="#" class="active">首页</a>
      <a href="#">项目案例</a>
      <a href="#">开源项目</a>
      <a href="#">视频教程</a>
      <a  @click="router.push({path : '/contactUs'})">联系我们</a>
      <a  @click="toModuleIndex('/projectMember')" target="_blank">项目成员</a>
      <a  @click="toModuleIndex('/projectIndex')" target="_blank">实战项目</a>
      <a  @click="toModuleIndex('/projectLeaderboard')"  target="_blank">  排行榜 </a>
      <a href="#" title="星云社区">N B 社 区</a>
      <a  @click="toModuleIndex('/userLogin')" target="_blank">登录</a>
    </div>
  </nav>


  <section class="hero">
    <div class="hero-content">
      <h1>引领未来科技</h1>
      <p>突破创新边界，打造智能化解决方案，助力企业迈向数字化新时代</p>
      <div class="typewriter">探索 · 创新 · 共赢 · 致力打造星空上的云端编程</div>
    </div>
  </section>

  <!-- 视频容器 -->
  <div class="video-background">
    <video autoplay muted loop>
      <source src="@/assets/homePage/video/HDAHKHFEC8afY2e.mp4" type="video/mp4">
      您的浏览器不支持视频标签。
    </video>
  </div>

  <section class="section">
    <h2>我们的产品</h2>
    <p>星云网站凭借顶尖技术与深厚实力，为各行业定制最前沿的产品解决方案。从人工智能、大数据到云计算，我们不断突破，创造无限可能。</p>
    <div class="cards">
      <div class="card">
        <h3>智能物联网</h3>
        <p>通过设备和系统对接，为企业提供远程操控管理系统，让企业实时了解设备状态。</p>
      </div>
      <div class="card">
        <h3>人工智能</h3>
        <p>通过前沿的机器学习与深度学习算法，为企业提供智能化决策支持系统。</p>
      </div>
      <div class="card">
        <h3>大数据分析</h3>
        <p>利用海量数据与精准分析，挖掘商业价值，助力企业优化运营效率。</p>
      </div>
      <div class="card">
        <h3>云计算平台</h3>
        <p>构建高效、灵活、安全的云平台，满足企业数字化转型的各项需求。</p>
      </div>
    </div>
  </section>

  <section class="section">
    <h2>成功案例</h2>
    <p>我们的解决方案已在众多知名企业成功落地，推动行业革新与升级。真实案例见证了星云科技的实力与承诺。</p>
    <div class="cards">
      <div class="card">
        <h3>某科技无人机飞手平台</h3>
        <p>集成无人机飞手培训推广、无人机飞手接单、客户下单等功能。实现数据对接。</p>
      </div>
      <div class="card">
        <h3>某某企业ERP-CS架构转型为Bs架构系统</h3>
        <p>为该企业全面重构、升级系统，实现全面数字化转型与业务升级。</p>
      </div>
      <div class="card">
        <h3>某某企业MES系统</h3>
        <p>构建高性能生产执行制造系统，优化工艺流程，助力企业更高效的产能。</p>
      </div>
      <div class="card">
        <h3>某某教育机构智慧校园系统</h3>
        <p>教学反馈，学生身体健康监测、学生信息管理、等一体化智能系统。</p>
      </div>
    </div>
  </section>


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
