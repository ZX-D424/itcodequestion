
<template>

  <!--导航栏-->
  <nav class="navbar" >
    <div class="logo">
      <a  @click="router.push({path : '/homePage'})" target="_blank">
        <img src="../assets/homePage/images/nebulas.png" width="80px" height="60px" alt="NeBuLas">
      </a>
    </div>
    <div class="nav-links">
      <template  v-for="item in menuDataList" >
        <a @click="initModule(item.id, item.name)" >{{item.name}}</a>
      </template>
    </div>
  </nav>

  <!-- 漂浮图标 -->
  <div class="floating-icons">
    <a href="#" class="feedback-icon">反馈</a>
    <a href="#" class="business-icon">合作</a>
    <a href="#" class="consult-icon">咨询</a>
  </div>

  <section class="section">
    <h2>{{menuName}} 实战</h2>
    <div class="cards">
      <template v-for="item in moduleDataList">
        <div class="card">
          <h3> {{item.name}}</h3>
          <p v-html="item.description"></p>
          <p>

            <a v-if="item.type===1" href="#">去实战</a>
            <a v-if="item.type===2" href="#">去闯关</a>
          </p>
        </div>
      </template>
    </div>
  </section>



  <footer>
    <p>&copy; 湘ICP备2024092148号-3   2025 星云网站. 版权所有.</p>
    <p>
      <a href="#">隐私政策</a> |
      <a href="#">使用条款</a> |
      <a href="#">联系我们</a>
    </p>
  </footer>


</template>


<script setup >


import {getMenuDataList} from "@/api/www/menu"
import {getModuleDataListByMenuId} from "@/api/www/module"

const route = useRoute()
const router = useRouter();
const menuDataList = ref([]);
const moduleDataList = ref([]);
const menuName = ref("");

//加载菜单
function initMenuDataList() {
  getMenuDataList().then(response => {
    menuDataList.value = response.data;
    initModule(menuDataList.value[0].id,menuDataList.value[0].name);
  });
}

function toModuleList(){

}
//
function initModule(menuId, name) {
  menuName.value = name;
  getModuleDataListByMenuId(menuId).then(response => {
    moduleDataList.value = response.data;
  });
}
initMenuDataList();
</script>

<style  >
@import  "@/assets/homePage/css/project.css";
</style>
