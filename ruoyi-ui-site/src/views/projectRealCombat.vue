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
      <a  @click="router.push({path : '/contactUs'})">联系我们</a>
      <a @click="toModuleIndex('/projectMember')" target="_blank"  >项目成员</a>
      <a  @click="toModuleIndex('/projectIndex')" target="_blank" class="active">实战项目</a>
      <a @click="toModuleIndex('/projectLeaderboard')" >  排行榜 </a>
      <a href="#" title="星云社区">星云社区</a>
      <a  @click="toModuleIndex('/userLogin')">登录</a>
    </div>
  </nav>


  <!-- 视频容器 -->
  <div class="video-background">
    <video autoplay muted loop>
      <source src="@/assets/homePage/video/HDAHKHFEC8afY2e.mp4" type="video/mp4">
      您的浏览器不支持视频标签。
    </video>
  </div>

  <template v-for=" item in practiceDataList">
    <section class="practice">

        <div class="team-card">

          <div class="left-container" >
            <div class="left-img">
              <el-image
                  class="cover"
                  :src=" url+item.imgUrl"
                  :preview-src-list="[url+item.imgUrl] ">
              </el-image>
            </div>
          </div>

          <div class="right-container">
            <div  class="text-container">
            <h2 class="name">{{item.name}}  </h2>
            <div class="right-row">
              <div class="info-left">
                <div class="title">
                  <B>价格：</B>
                  <span v-if="item.price ===0" style="color:blue;" >免费 </span>
                  <span v-if="item.price > 0" style="color:red;" > {{item.price }} </span>
                </div>
                <div class="title"> <B>Q Q：</B>{{item.qqNumber}}</div>
                <div class="email">  <B>邮箱：</B>{{item.emailNumber}}</div>
                <div class="education">
                  <B>附件：</B>
                  {{item.fileUrl}}
                </div>

            <B>描述：</B><br/>
            <div class="experience-list">
              <div class="experience-item"><p v-html="item.content"></p></div>
            </div>
              </div>
            </div>
            </div>
          </div>
          <div class="info-right" v-if="item.qrCode">
              <el-image
                  :src=" url+item.qrCode"
                  :preview-src-list="[url+item.qrCode] ">
              </el-image>
            <p  v-html="item.mark">
            </p>
          </div>
        </div>
    </section>
  </template>





</template>

<script setup name="projectRealCombat">
const route = useRoute()
const router = useRouter();
import {getPracticeDataList} from "@/api/www/practice"


const query = ref( {moduleId: route.query.id});
const practiceDataList = ref([]);

const url = ref(import.meta.env.VITE_APP_BASE_API);


function toModuleIndex( routerName) {
  router.push({ path: routerName });
}

//加载菜单
function initPracticeDataList() {
  getPracticeDataList(query.value).then(response => {
    practiceDataList.value = response.data;
  });
}
initPracticeDataList();
</script>

<style >
@import  "@/assets/homePage/css/practice.css";
@import  "@/assets/homePage/css/index.css";
</style>
