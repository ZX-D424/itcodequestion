<template>
    <div class="profile-section">

        <div class="profile-item">
            <label>我的头像：</label>
            <div class="avatar-upload">
                <img :src="url+profile.avatar || defaultAvatar"
                     class="avatar-preview"
                     @click="openAvatarUpload">
                <input type="file"
                       ref="avatarInput"
                       accept="image/*"
                       style="display: none"
                       @change="handleAvatarChange">
            </div>
        </div>

        <!-- 姓名 -->
        <div class="profile-item">
            <label>我的账号：</label>
            <span>{{ profile.userName }}</span>
        </div>

        <!-- 昵称 -->
        <div class="profile-item">
            <label>我的昵称：</label>
            <div class="editable-field" @click="startEditing('nickName')">
                <span v-if="!editing.nickName">{{ profile.nickName }}</span>
                <input v-else type="text" v-model="profile.nickName" @keyup.enter="updateProfile">
            </div>
        </div>

        <!-- 邮箱（不可编辑） -->
        <div class="profile-item">
            <label>我的邮箱：</label>
            <span>{{ profile.email }}</span>
        </div>

        <!-- 性别 -->
        <div class="profile-item">
            <label>我的性别：</label>
            <div class="editable-field" @click="startEditing('sex')">
                <span v-if="!editing.sex">{{ profile.sex === '0' ? '女' : '男' }}</span>
                <div v-else class="gender-options" @click.stop>
                    <label><input type="radio" v-model="profile.sex" value="1"> 男</label>
                    <label><input type="radio" v-model="profile.sex" value="0"> 女</label>
                </div>
            </div>
        </div>

        <!-- 电话 -->
        <div class="profile-item">
            <label>我的电话：</label>
            <div class="editable-field" @click="startEditing('phonenumber')">
                <span v-if="!editing.phonenumber">{{ profile.phonenumber }}</span>
                <input v-else type="text" v-model="profile.phonenumber" @keyup.enter="updateProfile">
            </div>
        </div>

        <!-- 加入时间 -->
        <div class="profile-item">
            <label>加入时间：</label>
            <span>{{ profile.createTime }}</span>
        </div>

        <!-- 操作按钮 -->
        <div class="profile-actions">
            <button class="btn-update" @click="updateProfile">更新资料</button>
            <button class="btn-change-pwd" @click="showChangePwdDialog = true">修改密码</button>
        </div>
    </div>

    <!-- 修改密码弹窗 -->
    <div class="dialog-overlay" v-if="showChangePwdDialog">
        <div class="change-pwd-dialog">
            <h3>修改密码</h3>
            <div class="form-item">
                <label>旧密码：</label>
                <input type="password" v-model="passwordForm.oldPassword" placeholder="请输入旧密码">
            </div>
            <div class="form-item">
                <label>新密码：</label>
                <input type="password" v-model="passwordForm.newPassword" placeholder="请输入新密码">
            </div>
            <div class="form-item">
                <label>确认密码：</label>
                <input type="password" v-model="passwordForm.confirmPassword" placeholder="请再次输入新密码">
            </div>
            <div class="dialog-actions">
                <button class="btn-cancel" @click="showChangePwdDialog = false">取消</button>
                <button class="btn-confirm" @click="changePassword">确认修改</button>
            </div>
        </div>
    </div>
</template>

<script setup>
    import { ref, onMounted } from 'vue'
    import { getUserProfile, updateUserProfile, updateUserPwd, uploadAvatar } from '@/api/www/user'
    import { ElMessage } from 'element-plus'
    import defaultAvatar from '@/assets/images/profile.jpg'
    import userStore from "@/store/modules/user"
    import { getToken } from '@/utils/auth'
    import { useRouter } from 'vue-router';

    const user = userStore();


    const router = useRouter();
    const url = import.meta.env.VITE_APP_IMG_API;

    // 用户资料数据
    const profile = ref({
        avatar: '',
        userName: '',
        nickName: '',
        email: '',
        sex: '1',
        phonenumber: '',
        createTime: ''
    })

    // 编辑状态
    const editing = ref({
        nickName: false,
        sex: false,
        phonenumber: false
    })

    // 修改密码相关
    const showChangePwdDialog = ref(false)
    const passwordForm = ref({
        oldPassword: '',
        newPassword: '',
        confirmPassword: ''
    })

    // 头像上传
    const avatarInput = ref(null)

    // 加载用户资料
    const loadUserProfile = async () => {
        try {
            const res = await getUserProfile()
            profile.value = res.data
        } catch (error) {
            ElMessage.error('获取用户资料失败')
        }
    }

    // 开始编辑
    const startEditing = (field) => {
        editing.value[field] = true
    }

    // 更新资料
    const updateProfile = async () => {
        try {
            // 构建只包含修改过的字段的请求数据
            const changedData = {}
            if (editing.value.nickName) changedData.nickName = profile.value.nickName
            if (editing.value.sex) changedData.sex = profile.value.sex
            if (editing.value.phonenumber) changedData.phonenumber = profile.value.phonenumber

            // 只有有修改的字段才发送请求
            if (Object.keys(changedData).length > 0) {
                await updateUserProfile(changedData)
            }

            // 重置编辑状态
            Object.keys(editing.value).forEach(key => {
                editing.value[key] = false
            })

            ElMessage.success('资料更新成功')
            loadUserProfile()
        } catch (error) {
            if (error.message.includes('手机号码已存在')) {
                ElMessage.error('该手机号已被注册，请使用其他号码')
            } else {
                ElMessage.error(error.message || '更新资料失败')
            }
        }
    }

    // 打开头像上传
    const openAvatarUpload = () => {
        avatarInput.value.click()
    }

    // 处理头像上传
    const handleAvatarChange = async (e) => {
        const file = e.target.files[0]
        if (!file) return

        const formData = new FormData()
        formData.append('avatarfile', file)

        try {
            const res = await uploadAvatar(formData)
            profile.value.avatar = res.imgUrl
            ElMessage.success('头像更新成功')
        } catch (error) {
            ElMessage.error('头像上传失败')
        }
    }

    // 修改密码
    const changePassword = async () => {
        if (passwordForm.value.newPassword !== passwordForm.value.confirmPassword) {
            ElMessage.error('两次输入的新密码不一致')
            return
        }

        try {
            await updateUserPwd(passwordForm.value.oldPassword, passwordForm.value.newPassword)
            ElMessage.success('密码修改成功')
            showChangePwdDialog.value = false
            passwordForm.value = {
                oldPassword: '',
                newPassword: '',
                confirmPassword: ''
            }
        } catch (error) {
            ElMessage.error(error.message || '密码修改失败')
        }
    }

    onMounted(() => {
        loadUserProfile()
    })
</script>

<style>
    @import "@/assets/homePage/css/member.css";
    @import "@/assets/homePage/css/index.css";

    .profile-section {
        padding: 20px;
    }

    .profile-item {
        margin-bottom: 15px;
        display: flex;
        align-items: center;
    }

    .profile-item label {
        width: 100px;
        text-align: right;
        margin-right: 15px;
        color: #666;
    }

    .profile-item span,
    .profile-item a {
        color: #333;
    }

    .profile-item a {
        color: #1890ff;
        text-decoration: none;
    }

    .profile-item a:hover {
        text-decoration: underline;
    }

    .avatar-upload {
        display: flex;
        align-items: center;
    }

    .avatar-preview {
        width: 60px;
        height: 60px;
        border-radius: 50%;
        background: #eee;
        margin-right: 10px;
    }

    .gender-options {
        display: flex;
        align-items: center;
    }

    .gender-options label {
        display: flex;
        align-items: center;
        margin-right: 15px;
        cursor: pointer;
        width: auto;
    }

    .gender-options input {
        margin-right: 5px;
    }

    .editable-field {
        flex: 1;
        cursor: pointer;
        padding: 5px 5px;
        border-radius: 4px;
        transition: background-color 0.2s;
        min-height: 32px;
        display: flex;
        align-items: center;
    }

    .editable-field:hover {
        background-color: #f5f5f5;
    }

    .editable-field input {
        width: 100%;
        padding: 5px 10px;
        border: 1px solid #ddd;
        border-radius: 4px;
        cursor: auto;
    }

    /* 操作按钮 */
    .profile-actions {
        display: flex;
        justify-content: center;
        margin-top: 30px;
        gap: 20px;
    }

    .btn-update, .btn-change-pwd {
        padding: 8px 20px;
        border-radius: 4px;
        cursor: pointer;
        transition: all 0.3s;
    }

    .btn-update {
        background-color: #1890ff;
        color: white;
        border: none;
    }

    .btn-update:hover {
        background-color: #40a9ff;
    }

    .btn-change-pwd {
        background-color: #fff;
        color: #1890ff;
        border: 1px solid #1890ff;
    }

    .btn-change-pwd:hover {
        background-color: #e6f7ff;
    }

    /* 修改密码弹窗 */
    .dialog-overlay {
        position: fixed;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background-color: rgba(0, 0, 0, 0.5);
        display: flex;
        justify-content: center;
        align-items: center;
        z-index: 1000;
    }

    .change-pwd-dialog {
        background: white;
        padding: 20px;
        border-radius: 8px;
        width: 400px;
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    }

    .change-pwd-dialog h3 {
        margin-bottom: 20px;
        text-align: center;
        color: #333;
    }

    .form-item {
        margin-bottom: 15px;
        display: flex;
        align-items: center;
    }

    .form-item label {
        width: 80px;
        text-align: right;
        margin-right: 10px;
        color: #777;
    }

    .form-item input {
        flex: 1;
        padding: 10px;
        border: 1px solid #ddd;
        border-radius: 4px;
    }

    .dialog-actions {
        display: flex;
        justify-content: center;
        margin-top: 20px;
        gap: 15px;
    }

    .btn-cancel, .btn-confirm {
        padding: 8px 20px;
        border-radius: 4px;
        cursor: pointer;
    }

    .btn-cancel {
        background: #fff;
        border: 1px solid #ddd;
    }

    .btn-cancel:hover {
        background: #f5f5f5;
    }

    .btn-confirm {
        background: #1890ff;
        color: white;
        border: none;
    }

    .btn-confirm:hover {
        background: #40a9ff;
    }

    /* 头像样式 */
    .avatar-preview {
        width: 60px;
        height: 60px;
        border-radius: 50%;
        object-fit: cover;
        cursor: pointer;
        border: 1px solid #eee;
    }

</style>