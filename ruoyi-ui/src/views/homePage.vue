<template>
  <nav class="navbar">
    <div class="logo"><img src="@/assets/homePage/images/nebulas.png" width="150px" height="100px" alt="NeBuLas"> </div>
    <div class="nav-links">
      <a href="#">首页</a>
      <a href="#">项目案例</a>
      <a href="#">开源项目</a>
      <a href="#">视频教程</a>
      <a href="#">联系我们</a>
      <a  @click="toModuleIndex('/projectIndex')" target="_blank">实战项目</a>
      <a href="#" title="NeBuLas社区">N B 社 区 </a>
    </div>
  </nav>


  <section class="hero">
    <div class="hero-content">
      <h1>引领未来科技</h1>
      <p>突破创新边界，打造智能化解决方案，助力企业迈向数字化新时代</p>
      <div class="typewriter">探索 · 创新 · 共赢</div>
      <br><br>
      <button class="btn">了解更多</button>
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
        <h3>项目Alpha</h3>
        <p>为全球500强企业打造智能化系统，实现全面数字化转型与业务升级。</p>
      </div>
      <div class="card">
        <h3>项目Beta</h3>
        <p>构建高性能大数据平台，助力客户发掘数据背后的商业潜能。</p>
      </div>
      <div class="card">
        <h3>项目Gamma</h3>
        <p>打造安全、稳定的云计算环境，确保客户业务高效运转与数据安全。</p>
      </div>
    </div>
  </section>


  <footer>
    <p>&copy; 2025 星云网站. 版权所有.</p>
    <p>
      <a href="#">隐私政策</a> |
      <a href="#">使用条款</a> |
      <a href="#">联系我们</a>
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
