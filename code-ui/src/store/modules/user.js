import router from '@/router'
import { ElMessageBox, } from 'element-plus'
import { login, logout, getInfo } from '@/api/login'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { isHttp, isEmpty } from "@/utils/validate"
import defAva from '@/assets/images/profile.jpg'

// 定义存储在 localStorage 中的键名
const USER_INFO_KEY = 'userInfo'

const useUserStore = defineStore(
    'user',
    {
      state: () => {
        // 尝试从 localStorage 中获取用户信息
        const storedUserInfo = localStorage.getItem(USER_INFO_KEY)
        const initialUserInfo = storedUserInfo ? JSON.parse(storedUserInfo) : {
          id: '',
          name: '',
          nickName: '',
          avatar: '',
        }
        return {
          token: getToken(),
          id: initialUserInfo.id,
          name: initialUserInfo.name,
          nickName: initialUserInfo.nickName,
          avatar: initialUserInfo.avatar,
          roles: [],
          permissions: [],
          userType: '',
        }
      },
      actions: {
        // 登录
        login(userInfo) {
          const username = userInfo.username.trim()
          const password = userInfo.password
          const code = userInfo.code
          const uuid = userInfo.uuid
          const userType = userInfo.userType
          return new Promise((resolve, reject) => {
            login(username, password, code, uuid, userType).then(res => {
              setToken(res.token)
              this.token = res.token
              resolve()
            }).catch(error => {
              reject(error)
            })
          })
        },
        // 获取用户信息
        getInfo() {
          return new Promise((resolve, reject) => {
            getInfo().then(res => {
              const user = res.user
              let avatar = user.avatar || ""
              if (!isHttp(avatar)) {
                avatar = (isEmpty(avatar)) ? defAva : import.meta.env.VITE_APP_BASE_API + avatar
              }
              if (res.roles && res.roles.length > 0) {
                this.roles = res.roles
                this.permissions = res.permissions
              } else {
                this.roles = ['ROLE_DEFAULT']
              }
              this.id = user.userId
              this.name = user.userName
              this.nickName = user.nickName
              this.avatar = avatar
              this.userType = user.userType

              // 将用户信息存储到 localStorage 中
              const userInfo = {
                id: this.id,
                name: this.name,
                nickName: this.nickName,
                avatar: this.avatar,
              }
              localStorage.setItem(USER_INFO_KEY, JSON.stringify(userInfo))

              /* 初始密码提示 */
              if(res.isDefaultModifyPwd) {
                ElMessageBox.confirm('您的密码还是初始密码，请修改密码！',  '安全提示', {  confirmButtonText: '确定',  cancelButtonText: '取消',  type: 'warning' }).then(() => {
                  router.push({ name: 'Profile', params: { activeTab: 'resetPwd' } })
                }).catch(() => {})
              }
              /* 过期密码提示 */
              if(!res.isDefaultModifyPwd && res.isPasswordExpired) {
                ElMessageBox.confirm('您的密码已过期，请尽快修改密码！',  '安全提示', {  confirmButtonText: '确定',  cancelButtonText: '取消',  type: 'warning' }).then(() => {
                  router.push({ name: 'Profile', params: { activeTab: 'resetPwd' } })
                }).catch(() => {})
              }
              resolve(res)
            }).catch(error => {
              reject(error)
            })
          })
        },
        // 退出系统
        logOut() {
          return new Promise((resolve, reject) => {
            logout(this.token).then(() => {
              this.token = ''
              this.roles = []
              this.permissions = []
              this.id = ''
              this.name = ''
              this.nickName = ''
              this.avatar = ''
              removeToken()
              // 清除 localStorage 中的用户信息
              localStorage.removeItem(USER_INFO_KEY)
              resolve()
            }).catch(error => {
              reject(error)
            })
          })
        }
      }
    })

export default useUserStore
