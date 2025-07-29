<template>

  <levleHeader></levleHeader>



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

import levleHeader from "./levelHeader.vue"
const route = useRoute()
const router = useRouter();
import {getPracticeDataList} from "@/api/www/practice"


const query = ref( {moduleId: route.query.id});
const practiceDataList = ref([]);

const url = ref(import.meta.env.VITE_APP_BASE_API);


//加载实战数据
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
