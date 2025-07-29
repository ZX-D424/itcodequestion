<template>

  <headerVue></headerVue>

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

<footerVue></footerVue>

</template>

<script setup name="contactUs">
import footerVue from "./footer.vue"
import headerVue from "./header.vue"

const route = useRoute()
const router = useRouter();

function toModuleIndex( routerName) {
  router.push({ path: routerName });
}

</script>

<style >
@import  "@/assets/homePage/css/index.css";
</style>
