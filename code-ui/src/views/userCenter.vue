<template>
    <headerVue></headerVue>

    <!-- 视频容器 -->
    <div class="video-background">
        <video autoplay muted loop>
            <source src="@/assets/homePage/video/HDAHKHFEC8afY2e.mp4" type="video/mp4">
            您的浏览器不支持视频标签。
        </video>
    </div>

    <!-- 会员中心内容 -->
    <div class="member-container">
        <div class="member-sidebar">
            <h2>全部菜单</h2>
            <ul>
                <li
                        v-for="(item, index) in menuItems"
                        :key="index"
                        @click="handleMenuClick(item)"
                        :class="{ 'active': activeTab === item.component }"
                >
                    <img :src="item.icon"> {{ item.name }}
                </li>
            </ul>
        </div>
        <div class="member-content">
            <h2>{{ getActiveMenuName() }}</h2>
            <component :is="components[activeTab]"></component>
        </div>
    </div>

    <footerVue></footerVue>
</template>

<script setup>
    import { ref } from 'vue'
    import footerVue from "./footer.vue"
    import headerVue from "./header.vue"
    import userStore from "@/store/modules/user"
    import profileVue from "./personalCenter/profile.vue"
    import questionRecodeVue from "./personalCenter/questionRecode.vue"
    import payRecodeVue from "./personalCenter/payRecode.vue"
    import certificateVue from "./personalCenter/certificate.vue"
    import orderVue from "./personalCenter/order.vue"
    import friendVue from "./personalCenter/friend.vue"
    import dashboardIcon from '@/assets/icons/svg/dashboard.svg'


    const user = userStore();

    // 定义菜单项
    const menuItems = [
        { name: '个人资料', icon: dashboardIcon, component: 'profileVue' },
        { name: '我的答题记录', icon: dashboardIcon, component: 'questionRecodeVue' },
        { name: '我的购买记录', icon: dashboardIcon, component: 'payRecodeVue' },
        { name: '我的证书', icon: dashboardIcon, component: 'certificateVue' },
        { name: '我的订单', icon: dashboardIcon, component: 'orderVue' },
        { name: '我的好友', icon: dashboardIcon, component: 'friendVue' },
        { name: '退出系统', icon: dashboardIcon, component: 'logout', isLogout: true }
    ]

    const components = {
        profileVue,
        questionRecodeVue,
        payRecodeVue,
        certificateVue,
        orderVue,
        friendVue
    }

    const activeTab = ref('profileVue')

    const switchTab = (componentName) => {
        activeTab.value = componentName
    }

    const getActiveMenuName = () => {
        const activeItem = menuItems.find(item => item.component === activeTab.value)
        return activeItem ? activeItem.name : ''
    }

    const handleMenuClick = (item) => {
        if (item.isLogout) {
            user.logOut();
        } else {
            switchTab(item.component)
        }
    }

</script>

<style>
    @import "@/assets/homePage/css/member.css";
    @import "@/assets/homePage/css/index.css";

    /* 会员中心样式 */
    .member-container {
        display: flex;
        max-width: 1100px;
        margin: 20px auto;
        gap: 20px; /* 增加两个模块之间的间距 */
    }

    .member-sidebar {
        width: 250px;
        height: 450px;
        padding: 25px;
        background: #fff;
        border-radius: 8px;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
        margin-left: 20px;
        background: rgba(255, 255, 245, 0.5);
    }

    .member-sidebar h2 {
        font-size: 18px;
        margin-bottom: 15px;
        color: #333;
        font-weight: normal;
        border-bottom: 1px solid #575757;
        padding: 0 0 10px 0;
    }

    .member-sidebar ul {
        list-style: none;
        padding: 0;
    }

    .member-sidebar li {
        padding: 12px 0;
        cursor: pointer;
        border-bottom: 1px dashed #eee;
        color: #333;
        display: flex;
        align-items: center;
        transition: all 0.3s ease;
    }

    .member-sidebar li.active {
        color: #1890ff;
        background: rgba(255, 255, 255, 0);
    }

    .member-sidebar li.active img {
        filter: invert(39%) sepia(92%) saturate(1745%) hue-rotate(194deg) brightness(97%) contrast(101%); /* 只修改图片颜色 */
    }

    .member-sidebar li img {
        width: 20px;
        height: 18px;
        margin-right: 12px;
        opacity: 0.8;
        transition: all 0.3s ease;
    }

    .member-sidebar li:hover {
        color: #1890ff;
    }

    .member-sidebar li:hover img {
        filter: invert(39%) sepia(92%) saturate(1745%) hue-rotate(194deg) brightness(97%) contrast(101%);
    }

    .member-content {
        flex: 1;
        padding: 25px;
        background: #fff;
        border-radius: 8px;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
        margin-right: 20px;
        background: rgba(255, 255, 245, 0.5);
    }
    .member-content h2 {
        font-size: 18px;
        margin-bottom: 15px;
        color: #333;
        font-weight: normal;
        border-bottom: 1px solid #575757;
        padding: 0 0 10px 0;
    }
</style>