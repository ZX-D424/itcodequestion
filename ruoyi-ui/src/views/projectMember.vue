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

  <template v-for=" item in memberDataList">
    <section class="member">
      <div class="container">
        <div class="team-card">
          <div class="avatar-container">
            <div class="avatar">
              <img :src=" url+item.profilePicture"/>


            </div>
          </div>
          <div class="info-container">
            <h2 class="name">{{item.nickName}}

              <el-tag type="danger">
                {{
                    item.memberType == 1 ? '发起人' :
                    item.memberType == 5 ? '后端协作' :
                    item.memberType == 10 ? '前端协作' :
                    item.memberType == 15 ? 'UI设计协作' :
                    item.memberType == 20 ? 'AI算法协作' :
                    item.memberType == 25 ? '运维协作' :'其他协作'
                }}
              </el-tag>
            </h2>

            <div class="info-row">
              <div class="info-left">
                <div class="title">{{item.proficiencyArea}}</div>
                <div class="email">{{item.email}}</div>
                <div class="education">
                  {{item.schoolName}}
                  —
                  <el-tag >
                    {{
                      item.specialty == 1 ? '计算机科学与技术‌' :
                      item.specialty ==2 ? '软件工程' :
                      item.memberType == 3 ? '其他' :'其他'
                    }}
                  </el-tag>
                  —
                  <el-tag >
                    {{
                      item.degree == 1 ? '专科' :
                      item.degree == 5 ? '本科' :
                      item.degree == 10 ? '硕士' :
                      item.degree == 15 ? '其他' :'其他'
                    }}
                  </el-tag>

                </div>
              </div>
              <div class="info-right">
                <div class="link-item">{{item.giteeLink}}</div>
                <div class="link-item">{{item.githubLink}}</div>
                <div class="link-item">{{item.personalSpace}}</div>
              </div>
            </div>
            <div class="experience-list">
              <div class="experience-item"><p v-html="item.contentInfo"></p></div>
            </div>
          </div>
        </div>
      </div>
    </section>
  </template>



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
import {getMemberDataList} from "@/api/www/member"



const memberDataList = ref([]);

const url = ref(import.meta.env.VITE_APP_BASE_API);


function toModuleIndex( routerName) {
  router.push({ path: routerName });
}

//加载菜单
function initMemberDataList() {
  getMemberDataList().then(response => {
    memberDataList.value = response.data;
  });
}
initMemberDataList();
</script>

<style >
@import  "@/assets/homePage/css/member.css";
@import  "@/assets/homePage/css/index.css";
</style>
