
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
      <a  @click="router.push({path : '/contactUs'})">联系我们</a>
      <a @click="toModuleIndex('/projectMember')" >项目成员</a>
      <a  @click="toModuleIndex('/projectIndex')"   target="_blank">实战项目</a>
      <a href="#" class="active" >  排行榜 </a>
      <a href="#" title="星云社区">星云社区</a>
      <a  @click="toModuleIndex('/userLogin')" >登录</a>
    </div>
  </nav>

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

  <footer>
    <p>&copy; 湘ICP备2024092148号-3   2025 星云网站. 版权所有.</p>
    <p>
      <a href="#">隐私政策</a> |
      <a href="#">使用条款</a> |
      <a @click="router.push({path : '/contactUs'})">联系我们</a>
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
@import  "@/assets/homePage/css/leaderboard.css";
@import  "@/assets/homePage/css/index.css";
</style>
