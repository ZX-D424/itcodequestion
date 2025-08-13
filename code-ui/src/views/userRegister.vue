<template>
  <div class="register">
    <el-form ref="registerRef" :model="registerForm" :rules="registerRules" class="register-form">

        <el-form-item label="帐号" prop="username">
          <el-input v-model="registerForm.username" placeholder="请输入帐号" />
        </el-form-item>

        <el-form-item label="邮箱" prop="email">
          <el-input v-model="registerForm.email" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="邀请码" prop="yqCode">
          <el-input v-model="registerForm.yqCode" placeholder="请输入帐号" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="registerForm.password" placeholder="请输入密码"  type="password"   show-password/>
        </el-form-item>

        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input v-model="registerForm.confirmPassword" placeholder="请输入确认密码" type="password"   show-password/>
        </el-form-item>
      <el-form-item label="邮箱验证码" prop="emailCode">
        <el-input v-model="registerForm.emailCode" placeholder="请输入邮箱验证码"  style="width: 50%" />
        <el-button  style="margin-left: 30px;" @click="loadEmailCode" :disabled="isButtonDisabled">获取验证码</el-button>
      </el-form-item>

      <el-form-item style="width:100%;">
        <el-button
          :loading="loading"
          size="large"
          type="primary"
          style="width:100%;"
          @click.prevent="handleRegister"
        >
          <span v-if="!loading">注 册</span>
          <span v-else>注 册 中...</span>
        </el-button>

      </el-form-item>
    </el-form>

  </div>
</template>

<script setup>
import { ElMessageBox } from "element-plus"
import {  register ,getEmailCode} from "@/api/login"

// const title = import.meta.env.VITE_APP_TITLE
// const router = useRouter()
const { proxy } = getCurrentInstance()

const isButtonDisabled = ref(false);

const registerForm = ref({
  username: "",
  password: "",
  confirmPassword: "",
  code: "",
  uuid: "",
  email: "",
  emailCode: "",
  yqCode: "",
  userType:"11"
})

const equalToPassword = (rule, value, callback) => {
  if (registerForm.value.password !== value) {
    callback(new Error("两次输入的密码不一致"))
  } else {
    callback()
  }
}

const registerRules = {
  username: [
    { required: true, trigger: "blur", message: "请输入您的账号" },
    { min: 2, max: 16, message: "用户账号长度必须介于 2 和 16 之间", trigger: "blur" }
  ],
  email: [
    {
      required: true,type: "email", message: "请输入正确的邮箱地址", trigger: ["blur", "change"]
    }
  ],
  password: [
    { required: true, trigger: "blur", message: "请输入您的密码" },
    { min: 5, max: 20, message: "用户密码长度必须介于 5 和 20 之间", trigger: "blur" },
    { pattern: /^[^<>"'|\\]+$/, message: "不能包含非法字符：< > \" ' \\\ |", trigger: "blur" }
  ],
  confirmPassword: [
    { required: true, trigger: "blur", message: "请再次输入您的密码" },
    { required: true, validator: equalToPassword, trigger: "blur" }
  ],
  emailCode: [{ required: true, trigger: "change", message: "请输入邮箱验证码" }]
}

// const codeUrl = ref("")
const loading = ref(false)
// const captchaEnabled = ref(true)

const props = defineProps({
  closeDialogRegister: {
    type: Function,
    required: true
  }
});

function handleRegister() {
  proxy.$refs.registerRef.validate(valid => {
    if (valid) {
      loading.value = true
      register(registerForm.value).then(res => {
        if (res.code === 200){
          const username = registerForm.value.username
          ElMessageBox.alert("<font color='red'>恭喜你，您的账号 " + username + " 注册成功！</font>", "系统提示", {
            dangerouslyUseHTMLString: true,
            type: "success",
          }).then(() => {
            loading.value = false
            props.closeDialogRegister ();
          }).catch(() => {
            loading.value = false
            props.closeDialogRegister ();

          })
        }else{
          loading.value = false
        }
      }).catch(() => {
        loading.value = false
      })
    }
  })
}
//
// function getCode() {
//   getCodeImg().then(res => {
//     captchaEnabled.value = res.captchaEnabled === undefined ? true : res.captchaEnabled
//     if (captchaEnabled.value) {
//       codeUrl.value = "data:image/gif;base64," + res.img
//       registerForm.value.uuid = res.uuid
//     }
//   })
// }
//getCode()

function loadEmailCode() {
  if (registerForm.value.email === null || registerForm.value.email === undefined || registerForm.value.email === "") {
    proxy.$modal.msgError("请输入邮箱地址")
    return;
  }
  getEmailCode(registerForm.value.email).then(res => {
    if (res.code == 200) {
      isButtonDisabled.value = true;
      ElMessage.success("验证码发送成功！");
    } else {
      ElMessage.error("验证码发送失败！");
    }
  })
}


</script>

<style lang='scss' scoped>
.register {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  width: 100%;
}
.title {
  margin: 0px auto 30px auto;
  text-align: center;
  color: #707070;
}

.register-form {
  border-radius: 6px;
  background: #ffffff;
  padding: 25px 25px 5px 25px;
  .el-input {
    height: 40px;
    input {
      height: 40px;
    }
  }
  .input-icon {
    height: 39px;
    width: 14px;
    margin-left: 0px;
  }
}
.register-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}
.register-code {
  width: 33%;
  height: 40px;
  float: right;
  img {
    cursor: pointer;
    vertical-align: middle;
  }
}
.el-register-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #fff;
  font-family: Arial;
  font-size: 12px;
  letter-spacing: 1px;
}
.register-code-img {
  height: 40px;
  padding-left: 12px;
}
</style>
