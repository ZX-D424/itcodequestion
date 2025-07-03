
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


  <div class="level">
    <div class="flex">
      <div class="card-list">
        <div class="right-aside">
          <h2> {{moduleName}} </h2>
          <div class="right-aside-item">
            <ul >
              <div class="catalog-item-step"></div>

              <template v-for="level in levelDataList">
              <li v-if="level.levelType === '1'"   @click="toPassLevel(level.id)">
                <div>第  {{level.levelCode}} 关</div>
                <input type="radio" readonly> &nbsp;&nbsp; {{level.levelTitle}}
              </li>
              </template>

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

                    <tr v-for="i in 5">
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

                <tr v-for="i in 5">
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
    <div v-if="qrCode.length>0" class="levelTypes">
      <p>
      <el-image
          class="imgCss"
          :src=" url+qrCode[0].imgUrl"
          :preview-src-list="[url+qrCode[0].imgUrl] ">
      </el-image>
      </p>
      <p class="html" v-html="qrCode[0].levelInfo"></p>
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


  <el-dialog :title="title"  v-model="open" class="passLevel" width="70%" center>
      <el-form ref="quetionsRef" :model="quetionsForm"  rules="rules" label-width="80px">
        <el-form-item label="标题" class="levelLabel">
          <p >{{levelData.levelTitle}}</p>
        </el-form-item>
        <el-form-item label="详情" class="levelLabel">
          <p v-html="levelData.levelInfo"></p>
        </el-form-item>
        <div class="flex-list">
          <el-form-item label="示列" class="levelLabel" v-if="levelData.imgUrl">
            <el-image
                class="imgCss"
                :src=" url+levelData.imgUrl"
                :preview-src-list="[url+levelData.imgUrl] ">
            </el-image>
          </el-form-item>
          <el-form-item label="效果图" prop="imgUrl">
            <image-upload v-model="quetionsForm.imgUrl" :limit="1" required/>
          </el-form-item>
          <el-form-item label="附件zip" prop="fileUrl">
            <file-upload v-model="quetionsForm.fileUrl" :limit="1" required/>
          </el-form-item>
        </div>
        <el-form-item label="代码" class="levelLabel" prop="mark">
          <editor v-model="quetionsForm.mark" :min-height="192" required/>
        </el-form-item>
      </el-form>


    <span slot="footer" class="dialog-footer">
      <el-button @click="open = false">取 消</el-button>
      <el-button type="primary" @click="submitPassLevel" >提 交</el-button>
    </span>
  </el-dialog>



</template>


<script setup name="projectLevel">
import {getLevelDataList,getLevel} from "@/api/www/level"
import {addQuestions} from "@/api/www/questions"
import userStore from "@/store/modules/user"

const route = useRoute()
const router = useRouter();

const user = userStore();

const { proxy } = getCurrentInstance()
const query = ref( {moduleId: route.query.id});
const moduleName = ref(route.query.name);
const levelDataList = ref([]);
const levelData = ref([]);
const qrCode = ref([])
const url = ref(import.meta.env.VITE_APP_BASE_API);

const open = ref(false);
const title = ref("");




const quetionsForm = ref({
  moduleId: null,
  levelId: null,
  imgUrl: null,
  mark: null,
  fileUrl: null,
})

//必填校验
const rules = {
  mark: [{ required: true, trigger: "blur", message: "请输入代码" }],
  imgUrl: [{ required: true, trigger: "blur", message: "请上传效果图" }],
  fileUrl: [{ required: true, trigger: "change", message: "请请上传代码文件压缩包" }]
}

// 表单重置
function reset() {
  quetionsForm.value = {
    moduleId: null,
    levelId: null,
    imgUrl: null,
    mark: null,
    fileUrl: null,
  }
  proxy.resetForm("quetionsRef")
}

//路由跳转
function toModuleIndex( routerName) {
  router.push({ path: routerName });
}
//加载题关卡
function initLevelDataList() {
  getLevelDataList(query.value).then(response => {
    // 先将响应数据赋值给 levelDataList
    levelDataList.value = response.data;
    // 筛选出 levelType 等于 2 的数据
    qrCode.value = levelDataList.value.filter(item => item.levelType === '2');

    // 从 levelDataList 中移除 levelType 等于 2 的数据
    levelDataList.value = levelDataList.value.filter(item => item.levelType !== '2');
  });
}

//查询关卡详情
function toPassLevel(id) {
  reset();
  getLevel(id).then(response => {
    if(response.code === 200){
      console.log(" user.userType------------------------>");
      console.log(" user.userType------------------------>", user.userType);
      if(user.userType === '00'){
        proxy.$modal.msgError("系统用户不允许闯关")
        return;
      }
      //当前关卡已通关
      if(response.data.atPassLevel){
        proxy.$modal.msgError("当前关卡已通关无需重复闯关")
        return;
        //判断如果上一关未通关则不打开
      }else if(response.data.atPassLevel ===  false &&  response.data.upPassLevel === false){
        proxy.$modal.msgError("不可跳过关卡，请通过上一关")
        return;
      }
      else if(response.data.atPassLevel ===  false &&  response.data.upPassLevel === true  ){
        open.value = true;
        levelData.value = response.data;
        title.value = "第 "+levelData.value.levelCode+" 关 ";
        quetionsForm.value.moduleId = levelData.value.moduleId;
        quetionsForm.value.levelId = levelData.value.id;
      }
    }

  });
}

//提交闯关
function submitPassLevel() {
  console.log("quetionsForm.value------------------------>",quetionsForm.value);
  addQuestions(quetionsForm.value).then(response => {
     if (response.code === 200){
       proxy.$modal.msgSuccess(response.msg);
     }else{
       proxy.$modal.msgError(response.msg);
     }
  });
}

//初始化数据
initLevelDataList();




</script>

<style  >
@import  "@/assets/homePage/css/level.css";
@import  "@/assets/homePage/css/index.css";


</style>
