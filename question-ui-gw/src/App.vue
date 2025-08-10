<!-- <script setup>

import Footer from './views/redirect/footer.vue'


</script>



<template>
  <div id="app">
    
    <header class="header">
      <div class="logo">
        <img src="@/assets/homePage/images/nebulas.png" alt="Logo" />
      </div>
      <nav class="nav">
        <ul class="nav-links">
          <li><a @click="router.push('/questionBank')">题库</a></li>
          <li><a @click="router.push('/practice')">刷题</a></li>
          <li><a @click="router.push('/exam')">笔试</a></li>
          <li><a @click="router.push('/exam')">笔试</a></li>
          <li><a @click="router.push('/interview')">应届题</a></li>
          <li><a @click="router.push('/junior')">初级题</a></li>
          <li><a @click="router.push('/senior')">高级题</a></li>
          <li><a @click="router.push('/architecture')">架构题</a></li>
          <li><a @click="router.push('/report')">题库上报</a></li>
        </ul>
      </nav>
    </header>

    
    <main class="main-content">
      <router-view></router-view>
    </main>

    
    <Footer></Footer>
  </div>
</template>

<style scoped>
@import  "@/assets/homePage/css/index.css";

.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px 20px;
  background-color: #fff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.logo img {
  height: 50px;
}

.nav-links {
  list-style: none;
  display: flex;
}

.nav-links li {
  margin: 0 15px;
}

.nav-links a {
  text-decoration: none;
  color: #333;
  font-weight: bold;
}

.main-content {
  padding: 20px;
  min-height: 400px; 
}
</style> -->


<!-- <template>
  <div id="app">

<div class="video-background">
      <video autoplay muted loop playsinline>
        <source src="@/assets/videos/HDAHKHFEC8afY2e.mp4" type="video/mp4">
        您的浏览器不支持视频标签。
      </video>
    </div>




    
    <header class="header">
      <div class="logo">
        <img src="@/assets/homePage/images/nebulas.png" alt="Logo" />
      </div>
      <nav class="nav">
        <ul class="nav-links">
          将原来的导航项整合到新的结构中
          <li :class="{ active: isActive('questionBank'), hover: isHover('questionBank') }" @mouseover="hover('questionBank')" @mouseout="unhover('questionBank')" @click="active('questionBank'); router.push('/questionBank')">
            <a>题库</a>
          </li>
          <li :class="{ active: isActive('practice'), hover: isHover('practice') }" @mouseover="hover('practice')" @mouseout="unhover('practice')" @click="active('practice'); router.push('/practice')">
            <a>刷题</a>
          </li>
          <li :class="{ active: isActive('exam'), hover: isHover('exam') }" @mouseover="hover('exam')" @mouseout="unhover('exam')" @click="active('exam'); router.push('/exam')">
            <a>笔试</a>
          </li>
          <li :class="{ active: isActive('interview'), hover: isHover('interview') }" @mouseover="hover('interview')" @mouseout="unhover('interview')" @click="active('interview'); router.push('/interview')">
            <a>应届题</a>
          </li>
          <li :class="{ active: isActive('junior'), hover: isHover('junior') }" @mouseover="hover('junior')" @mouseout="unhover('junior')" @click="active('junior'); router.push('/junior')">
            <a>初级题</a>
          </li>
          <li :class="{ active: isActive('senior'), hover: isHover('senior') }" @mouseover="hover('senior')" @mouseout="unhover('senior')" @click="active('senior'); router.push('/senior')">
            <a>高级题</a>
          </li>
          <li :class="{ active: isActive('architecture'), hover: isHover('architecture') }" @mouseover="hover('architecture')" @mouseout="unhover('architecture')" @click="active('architecture'); router.push('/architecture')">
            <a>架构题</a>
          </li>
          <li :class="{ active: isActive('report'), hover: isHover('report') }" @mouseover="hover('report')" @mouseout="unhover('report')" @click="active('report'); router.push('/report')">
            <a>题库上报</a>
          </li>
        </ul>
      </nav>
    </header>

    主要内容区域
    <main class="main-content">
      <router-view></router-view>
    </main>

    底部信息
    <Footer></Footer>
  </div>
</template> -->




<template>
  <div id="app">

    <div class="video-background">
      <video autoplay muted loop playsinline>
        <source src="@/assets/videos/HDAHKHFEC8afY2e.mp4" type="video/mp4">
        您的浏览器不支持视频标签。
      </video>
    </div>

    <!-- 顶部导航栏 -->
    <Navbar /> <!-- 使用 Navbar 组件替换原有导航栏 -->

    <!-- 主要内容区域 -->
    <main class="main-content">
      <router-view></router-view>
    </main>

    <!-- 底部信息 -->
    <Footer />
  </div>
</template>



<script setup>
import { ref } from 'vue';
import Footer from './views/redirect/footer.vue';
// 引入 Navbar 组件
import Navbar from './components/Navbar.vue';

const activeItem = ref(null);
const hoverItem = ref(null);

const isActive = (item) => activeItem.value === item;
const isHover = (item) => hoverItem.value === item;

const active = (item) => {
  activeItem.value = item;
};

const hover = (item) => {
  if (!isActive(item)) {
    hoverItem.value = item;
  }
};

const unhover = (item) => {
  if (!isActive(item)) {
    hoverItem.value = null;
  }
};
</script>


<style scoped>
@import "@/assets/homePage/css/index.css";

/* 视频背景样式 */
.video-background {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: -1; /* 确保视频在所有内容后面 */
  overflow: hidden;
}

.video-background video {
  min-width: 100%;
  min-height: 100%;
  width: auto;
  height: auto;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  object-fit: cover; /* 确保视频覆盖整个容器 */
}

.main-content {
  margin-top: 50px;
  padding: 20px;
  min-height: 400px;
  position: relative;
  z-index: 1; /* 确保内容在视频上方 */
  background-color: rgba(255, 255, 255, 0.8); /* 添加半透明背景 */
  border-radius: 10px; /* 添加圆角 */
  height: auto;
}
</style>