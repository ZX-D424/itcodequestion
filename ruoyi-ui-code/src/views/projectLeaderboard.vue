
<template>
  <headerVue></headerVue>

  <!-- 视频容器 -->
  <div class="video-background">
    <video autoplay muted loop>
      <source src="@/assets/homePage/video/HDAHKHFEC8afY2e.mp4" type="video/mp4">
      您的浏览器不支持视频标签。
    </video>
  </div>


  <div class="leaderboard">
    <div class="flex">
        <div class="left-aside-content">
          <h3 title="排行TOP100">HTML基础闯关排行榜</h3>
          <hr>
          <div class="ranking" >
            <table   width="95%" align="center">
              <tr >
                <td>排名</td>
                <td>昵称</td>
                <td>评分</td>
              </tr>
              <tr v-for="i in 20">
                <td ><img src="https://www.w3cschool.cn/statics/images/codecamp/first.png" alt=""></td>
                <td >大队长</td>
                <td> 80</td>
              </tr>
            </table>
          </div>
        </div>

      <div class="left-aside-content">
        <h3 title="排行TOP100">HTML基础证书榜单</h3>
        <hr>
        <div class="ranking" >
          <table   width="95%" align="center">
            <tr >
              <td>排名</td>
              <td>昵称</td>
              <td>评分</td>
            </tr>
            <tr v-for="i in 20">
              <td ><img src="https://www.w3cschool.cn/statics/images/codecamp/first.png" alt=""></td>
              <td >大队长</td>
              <td> 80</td>
            </tr>
          </table>
        </div>
      </div>

      <div class="left-aside-content">
        <h3 title="排行TOP100">HTML基础证书榜单</h3>
        <hr>
        <div class="ranking" >
          <table   width="95%" align="center">
            <tr >
              <td>排名</td>
              <td>昵称</td>
              <td>评分</td>
            </tr>
            <tr v-for="i in 20">
              <td ><img src="https://www.w3cschool.cn/statics/images/codecamp/first.png" alt=""></td>
              <td >大队长</td>
              <td> 80</td>
            </tr>
          </table>
        </div>
      </div>

      <div class="left-aside-content">
        <h3 title="排行TOP100">HTML基础证书榜单</h3>
        <hr>
        <div class="ranking" >
          <table   width="95%" align="center">
            <tr >
              <td>排名</td>
              <td>昵称</td>
              <td>评分</td>
            </tr>
            <tr v-for="i in 20">
              <td ><img src="https://www.w3cschool.cn/statics/images/codecamp/first.png" alt=""></td>
              <td >大队长</td>
              <td> 80</td>
            </tr>
          </table>
        </div>
      </div>





    </div>
  </div>

  <footerVue></footerVue>

</template>


<script setup name="projectLevel">
import footerVue from "./footer.vue"
import headerVue from "./header.vue"
const route = useRoute()
const router = useRouter();

const moduleId = ref(route.query.id);
const moduleName = ref(route.query.name);


function toModuleIndex( routerName) {
  router.push({ path: routerName });
}


</script>

<style  >
@import  "@/assets/homePage/css/leaderboard.css";
@import  "@/assets/homePage/css/index.css";
</style>
