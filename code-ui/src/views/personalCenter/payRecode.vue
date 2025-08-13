<template>
    <div class="answer-record-container">
        <!-- 搜索条件 -->
        <div class="search-bar">
            <el-form :inline="true" :model="searchForm">
                <el-form-item label="标题">
                    <input v-model="searchForm.title" placeholder="请输入标题" clearable />
                </el-form-item>
                <el-form-item label="时间">
                    <el-date-picker
                            v-model="searchForm.timeRange"
                            type="daterange"
                            range-separator="至"
                            start-placeholder="开始时间"
                            end-placeholder="结束时间"
                            value-format="YYYY-MM-DD"
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
                    <span class="module">所属模块：{{ item.module }}</span>
                    <span class="title">{{ item.title }}</span>
                </div>
                <div class="record-time">答题时间：{{ item.answerTime }}</div>
            </div>

            <div class="empty-tip">
                没有更多答题记录啦~~~
            </div>
        </div>
    </div>
</template>

<script setup>
    import { ref } from 'vue'

    // 搜索表单
    const searchForm = ref({
        title: '',
        timeRange: []
    })

    // 答题记录列表
    const recordList = ref([
        { module: 'xxx', title: '标题bbbbbbbbbbbbbbb', answerTime: '2023-10-01 10:30' },
        { module: 'xxx', title: '标题bbbbbbbbbbbbbbb', answerTime: '2023-10-02 11:20' },
        { module: 'xxx', title: '标题bbbbbbbbbbbbbbb', answerTime: '2023-10-03 14:15' },
        { module: 'xxx', title: '标题bbbbbbbbbbbbbbb', answerTime: '2023-10-04 09:45' },
        { module: 'xxx', title: '标题bbbbbbbbbbbbbbb', answerTime: '2023-10-05 16:30' }
    ])

    // 搜索方法
    const handleSearch = () => {
        console.log('搜索条件:', searchForm.value)
        // 这里应该调用API获取数据
        // 模拟API调用
        // const params = {
        //   title: searchForm.value.title,
        //   startTime: searchForm.value.timeRange[0],
        //   endTime: searchForm.value.timeRange[1]
        // }
        // api.getAnswerRecords(params).then(res => {
        //   recordList.value = res.data
        // })
    }

    // 重置方法
    const handleReset = () => {
        searchForm.value = {
            title: '',
            timeRange: []
        }
        // 重置后重新加载数据
        handleSearch()
    }
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
        width: 200px;
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
        color: #666;
        margin-right: 15px;
        min-width: 100px;
    }

    .title {
        color: #333;
        font-weight: 500;
    }

    .record-time {
        color: #999;
    }

    .empty-tip {
        text-align: center;
        padding: 20px;
        color: #999;
    }
</style>

我希望页面中的内容调用：
export function listQuestions(query) {
return request({
url: '/www/questions/list',
method: 'get',
params: query
})
}
展示内容为：模块名称：moduleName，关卡标题levelTitle,答题时间submitTime