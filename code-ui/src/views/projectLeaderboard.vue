<template>
  <div class="leaderboard-page">
    <headerVue />

    <!-- Video Background -->
    <div class="video-background">
      <video autoplay muted loop>
        <source src="@/assets/homePage/video/HDAHKHFEC8afY2e.mp4" type="video/mp4">
        您的浏览器不支持视频标签。
      </video>
    </div>

    <main class="leaderboard-container">
      <section class="ranking-section">
        <div class="section-header">
          <h3>总排行</h3>
        </div>

        <div class="ranking-content">
          <div class="ranking-header">
            <div class="header-col rank-col">排名</div>
            <div class="header-col user-col">用户</div>
            <div class="header-col score-col">积分</div>
          </div>

          <div class="ranking-items">
            <!-- 当前用户排名 -->
            <div
                    v-if="personalTotalRank"
                    class="ranking-item current-user"
            >
              <div class="rank-col">
                <span class="rank-badge" :class="getRankClass(personalTotalRank.totalRank)">
                  {{ personalTotalRank.totalRank }}
                </span>
              </div>
              <div class="user-col">{{ personalTotalRank.nickName }}</div>
              <div class="score-col">{{ personalTotalRank.totalScore }}分</div>
            </div>

            <!-- 其他用户排名 -->
            <div
                    v-for="(item, index) in totalRanking"
                    :key="'total-'+index"
                    class="ranking-item"
            >
              <div class="rank-col">
                <span class="rank-badge" :class="getRankClass(index + 1)">
                  {{ index + 1 }}
                </span>
              </div>
              <div class="user-col">{{ item.nickName }}</div>
              <div class="score-col">{{ item.totalScore }}分</div>
            </div>
          </div>
        </div>

        <button class="view-more" @click="loadMoreTotalRank">查看更多</button>
      </section>

      <section class="ranking-section">
        <div class="section-header">
          <h3>模块排名</h3>
        </div>

        <div class="module-tags">
          <span
                  v-for="tag in moduleTags"
                  :key="tag.id"
                  class="tag"
                  :class="{ 'active': activeModule === tag.id }"
                  @click="changeModule(tag)"
          >
            {{ tag.name }}
          </span>
        </div>

        <div class="ranking-content">
          <div class="ranking-header">
            <div class="header-col rank-col">排名</div>
            <div class="header-col user-col">用户</div>
            <div class="header-col score-col">积分</div>
          </div>

          <div class="ranking-items">
            <!-- 当前用户模块排名 -->
            <div
                    v-if="personalModuleRank"
                    class="ranking-item current-user"
            >
              <div class="rank-col">
                <span class="rank-badge" :class="getRankClass(personalModuleRank.moduleRank)">
                  {{ personalModuleRank.moduleRank }}
                </span>
              </div>
              <div class="user-col">{{ personalModuleRank.nickName }}</div>
              <div class="score-col">{{ personalModuleRank.score }}分</div>
            </div>

            <!-- 其他用户模块排名 -->
            <div
                    v-for="(item, index) in moduleRanking"
                    :key="'module-'+index"
                    class="ranking-item"
            >
              <div class="rank-col">
                <span class="rank-badge" :class="getRankClass(index + 1)">
                  {{ index + 1 }}
                </span>
              </div>
              <div class="user-col">{{ item.nickName }}</div>
              <div class="score-col">{{ item.score }}分</div>
            </div>
          </div>
        </div>

        <button class="view-more" @click="loadMoreModuleRank">查看更多</button>
      </section>
    </main>

    <footerVue />
  </div>
</template>

<script setup>
  import { ref, onMounted } from 'vue'
  import { useRoute, useRouter } from 'vue-router'
  import footerVue from "./footer.vue"
  import headerVue from "./header.vue"
  import { getTotalRank, getModuleRank, getPersonalTotalRank, getPersonalModuleRank } from '@/api/www/rank'
  import { listModule } from '@/api/www/module'

  const route = useRoute()
  const router = useRouter()

  // 数据状态
  const activeModule = ref(null)
  const moduleTags = ref([])
  const totalRanking = ref([])
  const moduleRanking = ref([])
  const personalTotalRank = ref({ totalRank: '未上榜', nickName: '当前用户', totalScore: 0 })
  const personalModuleRank = ref({ moduleRank: '未上榜', nickName: '当前用户', score: 0 })
  const loading = ref({
    total: false,
    module: false
  })

  // 初始化数据
  onMounted(async () => {
    await Promise.all([
      fetchPersonalTotalRank(),
      fetchTotalRank(),
      fetchModules()
    ])
  })

  // 获取个人总排名
  const fetchPersonalTotalRank = async () => {
    try {
      loading.value.total = true
      const res = await getPersonalTotalRank()
      console.log(res)
      if (res.data) {
        personalTotalRank.value = {
          totalRank: res.data.totalRank || '未上榜',
          nickName: res.data.nickName || '当前用户',
          totalScore: res.data.totalScore || 0
        }
      }
    } catch (error) {
      console.error('获取个人总排名失败:', error)
    } finally {
      loading.value.total = false
    }
  }

  // 获取个人模块排名
  const fetchPersonalModuleRank = async (moduleId) => {
    console.log(moduleId)
    if (!moduleId) return
    try {
      loading.value.module = true
      const res = await getPersonalModuleRank(moduleId)
      console.log("((((((((")
      console.log(res)
      if (res.data) {
        personalModuleRank.value = {
          moduleRank: res.data.moduleRank || '未上榜',
          nickName: res.data.nickName || '当前用户',
          score: res.data.score || 0
        }
      }
    } catch (error) {
      console.error('获取个人模块排名失败:', error)
    } finally {
      loading.value.module = false
    }
  }

  // 获取总排名列表
  const fetchTotalRank = async () => {
    try {
      loading.value.total = true
      const res = await getTotalRank()
      totalRanking.value = (res.rows || []).map(item => ({
        nickName: item.nickName || '匿名用户',
        totalScore: item.totalScore || 0,
        userId: item.userId
      })).filter(item => item.userId) // 过滤掉无效用户
    } catch (error) {
      console.error('获取总排名失败:', error)
    } finally {
      loading.value.total = false
    }
  }

  // 获取模块列表
  const fetchModules = async () => {
    try {
      const res = await listModule({
        type: 2,
        isDisabled: 0
      })
      moduleTags.value = res.rows || []
      console.log("@@@@@@@@@@")
      console.log(res)
      if (moduleTags.value.length > 0) {
        activeModule.value = moduleTags.value[0].id
        await fetchModuleRank(activeModule.value)
      }
    } catch (error) {
      console.error('获取模块列表失败:', error)
    }
  }

  // 获取模块排名
  const fetchModuleRank = async (moduleId) => {
    if (!moduleId) return

    try {
      loading.value.module = true
      const res = await getModuleRank(4)
      console.log(res)
      moduleRanking.value = (res.rows || []).map(item => ({
        nickName: item.nickName || '匿名用户',
        score: item.score || 0,
        userId: item.userId
      })).filter(item => item.userId) // 过滤掉无效用户

      // 获取个人模块排名
      await fetchPersonalModuleRank(moduleId)
    } catch (error) {
      console.error('获取模块排名失败:', error)
    } finally {
      loading.value.module = false
    }
  }

  // 切换模块
  const changeModule = async (module) => {
    console.log(module)
    if (!module || !module.moduleId) return
    activeModule.value = module.moduleId
    await fetchModuleRank(activeModule.value)
  }

  // 加载更多
  const loadMoreTotalRank = async () => {
    // 实现分页加载逻辑
  }

  const loadMoreModuleRank = async () => {
    // 实现分页加载逻辑
  }

  // 排名样式
  const getRankClass = (rank) => {
    if (rank === 1) return 'rank-top1'
    if (rank === 2) return 'rank-top2'
    if (rank === 3) return 'rank-top3'
    return ''
  }
</script>

<style>
  @import  "@/assets/homePage/css/index.css";
  @import  "@/assets/homePage/css/leaderboard.css";
</style>