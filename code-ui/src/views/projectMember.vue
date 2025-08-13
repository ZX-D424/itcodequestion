<template>
  <div class="app-container">
    <!-- 视频背景 -->
    <div class="video-background">
      <video autoplay muted loop>
        <source src="@/assets/homePage/video/HDAHKHFEC8afY2e.mp4" type="video/mp4">
        您的浏览器不支持视频标签。
      </video>
    </div>

    <!-- 会员资料面板 -->
    <div class="member-panel">
      <!-- 左侧菜单 -->
      <div class="member-menu">
        <el-menu
                background-color="#f8f9fa"
                text-color="#333"
                active-text-color="#409eff"
                :default-active="activeMenu"
        >
          <el-menu-item index="personal">
            <el-icon><User /></el-icon>
            <span>个人资料</span>
          </el-menu-item>
          <el-menu-item index="records">
            <el-icon><Video /></el-icon>
            <span>我的答题记录</span>
          </el-menu-item>
          <el-menu-item index="purchase">
            <el-icon><ShoppingCart /></el-icon>
            <span>我的购买记录</span>
          </el-menu-item>
          <el-menu-item index="certificate">
            <el-icon><Certificate /></el-icon>
            <span>我的证书</span>
          </el-menu-item>
          <el-menu-item index="orders">
            <el-icon><DocumentCopy /></el-icon>
            <span>我的订单</span>
          </el-menu-item>
          <el-menu-item index="friends">
            <el-icon><Friends /></el-icon>
            <span>我的好友</span>
          </el-menu-item>
          <el-menu-item index="logout">
            <el-icon><Logout /></el-icon>
            <span>退出系统</span>
          </el-menu-item>
        </el-menu>
      </div>

      <!-- 右侧内容 -->
      <div class="member-content">
        <div class="member-header">
          <h2>我的资料</h2>
        </div>

        <div class="member-form">
          <!-- 头像上传 -->
          <div class="avatar-section">
            <div class="avatar-label">我的头像：</div>
            <div class="avatar-container">
              <img v-if="memberData.avatar" :src="memberData.avatar" class="avatar" />
              <el-icon v-else class="avatar-placeholder"><User /></el-icon>
              <el-upload
                      class="upload-btn"
                      action="#"
                      :show-file-list="false"
                      :on-success="handleAvatarSuccess"
                      :before-upload="beforeAvatarUpload"
              >
                <el-button type="primary" size="small">上传图片</el-button>
              </el-upload>
            </div>
          </div>

          <!-- 基本信息表单 -->
          <el-form :model="memberData" label-width="100px" label-position="left">
            <el-form-item label="我的姓名：">
              <el-input v-model="memberData.name" disabled />
            </el-form-item>
            <el-form-item label="我的昵称：">
              <el-input v-model="memberData.nickname" />
            </el-form-item>
            <el-form-item label="我的邮箱：">
              <el-input v-model="memberData.email" />
            </el-form-item>
            <el-form-item label="我的性别：">
              <el-select v-model="memberData.gender" placeholder="请选择">
                <el-option label="男" value="男" />
                <el-option label="女" value="女" />
              </el-select>
            </el-form-item>
            <el-form-item label="我的年龄：">
              <el-input v-model="memberData.age" type="number" />
            </el-form-item>
            <el-form-item label="我的电话：">
              <el-input v-model="memberData.phone" />
            </el-form-item>
            <el-form-item label="加入时间：">
              <el-input v-model="memberData.joinTime" disabled />
            </el-form-item>
          </el-form>

          <!-- 保存按钮 -->
          <div class="form-footer">
            <el-button type="primary" @click="saveMemberData">更新个人资料</el-button>
          </div>
        </div>
      </div>
    </div>

    <footerVue></footerVue>
  </div>
</template>

<script setup name="projectMember">
  import { ref, onMounted } from 'vue'
  import { User, Video, ShoppingCart, Certificate, DocumentCopy, Friends, Logout } from '@element-plus/icons-vue'
  import { updateMemberData, getMemberData } from "@/api/www/member"
  import footerVue from "./footer.vue"
  import headerVue from "./header.vue"

  const memberData = ref({
    avatar: '',
    name: '林桃子',
    nickname: '秋秋子',
    email: '124234235@qq.com',
    gender: '女',
    age: 18,
    phone: '2143243215',
    joinTime: '2025-07-30 22:22:22'
  })

  // 菜单激活状态
  const activeMenu = ref('personal')

  // 上传头像成功处理
  const handleAvatarSuccess = (res, file) => {
    memberData.value.avatar = URL.createObjectURL(file.raw)
  }

  // 上传前校验
  const beforeAvatarUpload = (file) => {
    const isImage = file.type.indexOf('image/') === 0
    const isLt2M = file.size / 1024 / 1024 < 2

    if (!isImage) {
      ElMessage.error('上传头像图片只能是 JPG/PNG 格式!')
    }
    if (!isLt2M) {
      ElMessage.error('上传头像图片大小不能超过 2MB!')
    }
    return isImage && isLt2M
  }

  // 保存会员资料
  const saveMemberData = () => {
    // 这里调用API更新数据
    updateMemberData(memberData.value).then(() => {
      ElMessage.success('资料更新成功')
    })
  }

  // 获取会员数据
  onMounted(() => {
    // 如果是编辑场景，这里应该调用API获取已有数据
    // getMemberData().then(response => {
    //   memberData.value = response.data
    // })
  })
</script>

<style scoped>
  .app-container {
    position: relative;
    height: 100vh;
    overflow: hidden;
  }

  .video-background {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    z-index: -1;
    overflow: hidden;
  }

  .video-background video {
    object-fit: cover;
    width: 100%;
    height: 100%;
  }

  .member-panel {
    display: flex;
    height: calc(100vh - 80px); /* 减去页头和页脚的高度 */
    margin-top: 60px; /* 为视频背景留出空间 */
    margin-bottom: 60px;
    position: relative;
    z-index: 1;
  }

  .member-menu {
    width: 240px;
    min-height: 100%;
    border-right: 1px solid #eee;
    padding-top: 20px;
  }

  .member-content {
    flex: 1;
    padding: 20px;
    overflow-y: auto;
    background-color: rgba(255, 255, 255, 0.9);
    border-radius: 8px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  }

  .member-header {
    margin-bottom: 20px;
  }

  .member-form {
    max-width: 600px;
  }

  .avatar-section {
    margin-bottom: 20px;
    display: flex;
    align-items: center;
  }

  .avatar-label {
    margin-right: 15px;
  }

  .avatar-container {
    position: relative;
    display: inline-block;
  }

  .avatar {
    width: 120px;
    height: 120px;
    border-radius: 6px;
    object-fit: cover;
  }

  .avatar-placeholder {
    width: 120px;
    height: 120px;
    line-height: 120px;
    text-align: center;
    border: 1px dashed #ddd;
    border-radius: 6px;
  }

  .upload-btn {
    margin-left: 15px;
  }

  .form-footer {
    text-align: right;
    margin-top: 30px;
  }

  /* 菜单样式自定义 */
  .el-menu--popup {
    min-width: 240px;
  }
</style>