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
            @click="toModuleIndex('/index', item.id,item.name,index)"
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


</template>

<script setup>
import { ref } from 'vue';
import userStore from "@/store/modules/user"
import { useRouter } from 'vue-router';
import {getMenuDataList} from "@/api/www/menu"

const user = userStore();

const route = useRoute()
const router = useRouter();
const menuDataList = ref([]);
const activeIndex = Number(route.query.index);


function toModuleIndex( routerName,id,name,index) {
  router.push({
    path: routerName,
    query: {menuId: id,   menuName: name,menuIndex: index}
  });

}
//加载菜单
function initMenuDataList() {
  getMenuDataList().then(response => {
    menuDataList.value = response.data;
  });
}
initMenuDataList();
</script>

<style>

</style>
