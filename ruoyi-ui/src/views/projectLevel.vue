
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
      <a  @click="toModuleIndex('/projectIndex')"  class="active" target="_blank">实战项目</a>
      <a @click="toModuleIndex('/projectLeaderboard')" >  排行榜 </a>
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
    <div class="flex">
      <div class="card-list">
        <div class="right-aside">
          <h2> {{moduleName}} </h2>
          <div class="right-aside-item">
            <ul>
              <div class="catalog-item-step"></div>
              <li><div>第 1 关</div><input type="radio" readonly>开始学习HTML标签</li>
              <li><div>第 2 关</div><input type="radio" readonly>添加 &lt;h2&gt; 标签</li>
              <li><div>第 3 关</div><input type="radio" readonly>添加换行</li>
              <li><div>第 4 关</div><input type="radio" readonly>添加&lt;p&gt;标签</li>
              <li><div>第 5 关</div><input type="radio" readonly>添加&lt;em&gt;标签</li>
              <li><div>第 6 关</div><input type="radio" readonly>添加&lt;sub&gt;标签和&lt;sup&gt;标签</li>
              <li><div>第 7 关</div><input type="radio" readonly>添加&lt;b&gt;标签</li>
              <li><div>第 8 关</div><input type="radio" readonly>块级元素与行内元素</li>
              <li><div>第 9 关</div><input type="radio" readonly>元素嵌套</li>
              <li><div>第 10 关</div><input type="radio" readonly>删除HTML的注释</li>
              <li><div>第 11 关</div><input type="radio" readonly>给HTML添加注释</li>
              <li><div>第 12 关</div><input type="radio" readonly >HTML5介绍</li>
              <li><div>第 13 关</div><input type="radio" readonly>添加图片</li>
              <li><div>第 14 关</div><input type="radio" readonly>创建外部链接</li>
              <li><div>第 15 关</div><input type="radio" readonly>给图片添加alt属性</li>
              <li><div>第 16 关</div><input type="radio" readonly>alt文本留空</li>
              <li><div>第 17 关</div><input type="radio" readonly>给图片添加链接</li>
              <li><div>第 18 关</div><input type="radio" readonly>使用a元素实现页面内跳转</li>
              <li><div>第 19 关</div><input type="radio" readonly>使用#符号设置固定标签</li>
              <li><div>第 20 关</div><input type="radio" readonly>创建无序列表</li>
              <li><div>第 21 关</div><input type="radio" readonly>创建有序列表</li>
              <li><div>第 22 关</div><input type="radio" readonly>创建文本输入框</li>
              <li><div>第 23 关</div><input type="radio" readonly>为文本输入框设定预定值</li>
              <li><div>第 24 关</div><input type="radio" readonly>创建表单</li>
              <li><div>第 25 关</div><input type="radio" readonly>为表单添加提交按钮</li>
              <li><div>第 26 关</div><input type="radio" readonly>把表单设置为必填</li>
              <li><div>第 27 关</div><input type="radio" readonly>添加单选框</li>
              <li><div>第 28 关</div><input type="radio" readonly>添加复选框</li>
              <li><div>第 29 关</div><input type="radio" readonly>设置复选框和单选框默认选中</li>
              <li><div>第 30 关</div><input type="radio" readonly>div 元素嵌套</li>
              <li><div>第 31 关</div><input type="radio" readonly>为标签添加ID属性</li>
              <li><div>第 32 关</div><input type="radio" readonly>HTML的body元素</li>
              <li><div>第 33 关</div><input type="radio" readonly>使用标题显示内容的层次关系</li>
            </ul>

          </div>
        </div>
        <div class="left-aside">
          <div class="left-aside-content">
            <h3>实战证书</h3>
            <hr>
            <ul>
              <li>
                <div class="left-aside-item">
                  <img class="img-title1" src="https://www.w3cschool.cn/statics/images/codecamp/certificate.png" alt="">
                  <ul>
                    <li><B>奖杯</B></li>
                    <li>通关HTML基础闯关且评分>80分，可领取</li>
                    <li>纪念价值，学习证明、自我学习激励</li>
                  </ul>
                </div>
              </li>
              <li>
                <div class="left-aside-item">
                  <img class="img-title1" src="https://www.w3cschool.cn/statics/images/codecamp/certificate.png" alt="">
                  <ul>
                    <li><B>证书</B></li>
                    <li>通关HTML基础闯关且评分>80分，可领取</li>
                    <li>根据证书编号查询、学习情况。学习经历、企业背调。凭证书我司可考虑优先聘用。</li>
                  </ul>
                </div>
              </li>

              <li class="flex">
                <div class="get" title="只展示TOP100位领取人">领取榜单</div>
              </li>
              <li class="get-list">
                  <table  width="95%" align="center">
                    <tr>
                      <td>头像</td>
                      <td>昵称</td>
                      <td>领取时间</td>
                    </tr>

                    <tr v-for="i in 20">
                      <td width="20%">头像</td>
                      <td> 大队长</td>
                      <td>2025-06-25 11:22:05</td>
                    </tr>


                  </table>
              </li>

            </ul>
          </div>





          <div class="left-aside-content1">
            <h3 title="排行TOP100">排行榜</h3>
            <hr>
            <div class="ranking" >
              <table   width="95%" align="center">
                <tr class="ranking-list">
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
@import  "@/assets/homePage/css/level.css";
@import  "@/assets/homePage/css/index.css";


</style>
