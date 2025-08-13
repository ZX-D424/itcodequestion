<template>
    <div class="certificate-container">
        <div class="search-bar">
            <div class="search-input">
                <span>名称：</span>
                <input type="text" placeholder="请输入证书名称" v-model="searchText">
            </div>
            <div class="search-buttons">
                <button @click="searchCertificates">搜索</button>
                <button @click="resetSearch">重置</button>
            </div>
        </div>

        <div class="certificate-list" v-if="filteredCertificates.length > 0">
            <div class="certificate-item" v-for="(certificate, index) in filteredCertificates" :key="index">
                <h3>{{ certificate.name }}</h3>
                <p class="certificate-date">{{ certificate.date }}</p>
            </div>
        </div>

        <div class="no-certificate" v-else>
            <p>亲，你暂时还没有获取到证书哦~</p>
        </div>
    </div>
</template>

<script setup>
    import { ref, computed } from 'vue'

    // 模拟证书数据
    const certificates = ref([
        { name: '码神称号证书', date: '2025-07-31 22:22:22' },
        { name: '码神称号证书', date: '2025-07-31 22:22:22' },
        { name: '码神称号证书', date: '2025-07-31 22:22:22' },
        { name: '码神称号证书', date: '2025-07-31 22:22:22' }
    ])

    const searchText = ref('')

    // 过滤证书
    const filteredCertificates = computed(() => {
        if (!searchText.value) return certificates.value
        return certificates.value.filter(cert =>
            cert.name.includes(searchText.value)
        )
    })

    // 搜索证书
    const searchCertificates = () => {
        // 这里可以添加实际的搜索逻辑
        console.log('搜索:', searchText.value)
    }

    // 重置搜索
    const resetSearch = () => {
        searchText.value = ''
    }
</script>

<style scoped>
    .certificate-container {
        padding: 20px;
        font-family: 'PingFang SC', 'Microsoft YaHei', sans-serif;
    }

    .search-bar {
        display: flex;
        justify-content: space-between;
        margin-bottom: 20px;
        align-items: center;
    }

    .search-input {
        display: flex;
        align-items: center;
    }

    .search-input span {
        margin-right: 10px;
        color: #666;
    }

    .search-input input {
        padding: 8px 12px;
        border: 1px solid #ddd;
        border-radius: 4px;
        width: 200px;
    }

    .search-buttons button {
        padding: 8px 16px;
        margin-left: 10px;
        background-color: #1890ff;
        color: white;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        transition: background-color 0.3s;
    }

    .search-buttons button:hover {
        background-color: #40a9ff;
    }

    .certificate-list {
        display: grid;
        grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
        gap: 20px;
    }

    .certificate-item {
        padding: 15px;
        border: 1px solid #eee;
        border-radius: 6px;
        background-color: #f9f9f9;
    }

    .certificate-item h3 {
        margin: 0 0 10px 0;
        color: #333;
        font-size: 16px;
    }

    .certificate-date {
        margin: 0;
        color: #999;
        font-size: 14px;
    }

    .no-certificate {
        text-align: center;
        padding: 40px;
        color: #999;
        font-size: 16px;
        background-color: #f9f9f9;
        border-radius: 6px;
    }
</style>