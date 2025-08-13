<template>
    <div class="answer-record-container">
        <!-- 搜索条件 -->
        <div class="search-bar">
            <el-form :inline="true" :model="searchForm">
                <el-form-item label="标题" >
                    <input v-model="searchForm.levelName" placeholder="请输入标题" clearable />
                </el-form-item>
                <el-form-item label="时间">
                    <el-date-picker
                            v-model="searchForm.timeRange"
                            type="daterange"
                            range-separator="至"
                            start-placeholder="开始时间"
                            end-placeholder="结束时间"
                            value-format="YYYY-MM-DD"
                            style="width: 240px;"
                    />
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="handleSearch">搜索</el-button>
                    <el-button @click="handleReset">重置</el-button>
                </el-form-item>
            </el-form>
        </div>

        <!-- 答题记录列表 -->
        <div class="record-list">
            <div v-for="(item, index) in recordList" :key="index" class="record-item">
                <div class="record-info">
                    <span class="module">《{{ item.moduleName }}》</span>
                    <span class="title" :levelName="item.levelName">{{ item.levelName }}</span>
                </div>
                <div class="record-time">{{ item.submitTime }}</div>
            </div>

            <div v-if="recordList.length === 0" class="empty-tip">
                没有更多答题记录啦~~~
            </div>
        </div>
    </div>
</template>

<script setup>
    import { ref, onMounted } from 'vue'
    import { listQuestions } from '@/api/www/questions'

    // 搜索表单
    const searchForm = ref({
        levelName: '',
        timeRange: []
    })

    // 答题记录列表
    const recordList = ref([])

    // 加载答题记录
    const loadAnswerRecords = async () => {
        try {
            const params = {
                levelName: searchForm.value.levelName
            }
            if (searchForm.value.timeRange && searchForm.value.timeRange.length === 2) {
                params.startDay = searchForm.value.timeRange[0]
                params.endDay = searchForm.value.timeRange[1]
            }
            const res = await listQuestions(params)
            recordList.value = res.rows
        } catch (error) {
            console.error('获取答题记录失败:', error)
            recordList.value = []
        }
    }

    // 搜索方法
    const handleSearch = () => {
        loadAnswerRecords()
    }

    // 重置方法
    const handleReset = () => {
        searchForm.value = {
            levelName: '',
            timeRange: []
        }
        loadAnswerRecords()
    }

    // 组件挂载时加载数据
    onMounted(() => {
        loadAnswerRecords()
    })
</script>

<style scoped>

    .answer-record-container {
        padding: 20px;
    }
    .search-bar {
        margin-bottom: 20px;
    }
    .search-bar input {
        padding: 8px 10px;
        border: 1px solid #ddd;
        border-radius: 4px;
        width: 150px;
    }

    .record-list {
        border-top: 1px solid #eee;
    }

    .record-item {
        padding: 15px 0;
        border-bottom: 1px solid #eee;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .record-info {
        display: flex;
        align-items: center;
    }

    .module {
        color: #333;
        margin-right: 10px;
        min-width: 100px;
    }

    .record-time {
        width: 100px;
        color: #555;
    }

    .empty-tip {
        text-align: center;
        padding: 20px;
        color: #999;
    }

    .title {
        color: #333;
        font-weight: 500;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
        max-width: 430px;
        display: inline-block;
        vertical-align: middle;
    }

    .title:hover {
        white-space: normal;
        overflow: visible;
        text-overflow: clip;
        max-width: none;
        position: relative;
        z-index: 1;
    }
</style>