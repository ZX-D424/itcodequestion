<template>

  <!--导航栏-->
  <nav class="navbar" >
    <div class="logo">
      <a  @click="router.push({path : '/index'})" target="_blank">
        <img src="../assets/homePage/images/nebulas.png" width="80px" height="60px" alt="NeBuLas">
      </a>
    </div>
    <div class="nav-links">
      <template v-for="(item, index) in menuDataList">
        <a
            @click="initModule(item.id, item.name,index)"
            v-bind:class="{ active: activeIndex === index }"
        >
          {{ item.name }}
        </a>
      </template>
    </div>
  </nav>

  <!-- 视频容器 -->
  <div class="video-background">
    <video autoplay muted loop>
      <source src="@/assets/homePage/video/HDAHKHFEC8afY2e.mp4" type="video/mp4">
      您的浏览器不支持视频标签。
    </video>
  </div>


  <div class="project">
    <section class="section">
      <h2>{{menuName}} 实战</h2>
      <div class="cards">
        <template v-for="item in moduleDataList">
          <div class="card">
            <h3> {{item.name}}</h3>
            <p v-html="item.description"></p>
            <p>
              <a v-if="item.type===1" @click="toLevelLink(item.id,item.name,'/projectRealCombat')">去实战</a>
              <a v-if="item.type===2" @click="toLevelLink(item.id,item.name,'/projectLevel')">去闯关</a>
              <a v-if="item.type===3" @click="proxy.$modal.msgError('建设中')">去刷题</a>
            </p>
          </div>
        </template>
      </div>
    </section>
  </div>



  <footerVue></footerVue>

</template>


<script setup >
import footerVue from "./footer.vue"
import {getMenuDataList} from "@/api/www/menu"
import {getModuleDataListByMenuId} from "@/api/www/module"

const route = useRoute()
const router = useRouter();
const menuDataList = ref([]);
const moduleDataList = ref([]);
const menuName = ref("");
const activeIndex = ref(0);

const { proxy } = getCurrentInstance()
//加载菜单
function initMenuDataList() {
  getMenuDataList().then(response => {
    menuDataList.value = response.data;
    initModule(menuDataList.value[0].id,menuDataList.value[0].name,0);
  });
}

function toLevelLink(id, name, path) {
  router.push({
    path: path,
    query: {id: id,   name: name}
  });
}
//
function initModule(menuId, name,index) {
  menuName.value = name;
  activeIndex.value = index;
  getModuleDataListByMenuId(menuId).then(response => {
    moduleDataList.value = response.data;
  });
}
initMenuDataList();
</script>

<style  >
@import  "@/assets/homePage/css/project.css";
@import  "@/assets/homePage/css/index.css";
</style>
