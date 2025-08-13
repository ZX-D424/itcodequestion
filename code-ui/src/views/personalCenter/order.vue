<template>
    <div class="order-container">
        <!-- 搜索栏 -->
        <div class="search-bar">
            <input
                    type="text"
                    placeholder="请输入订单编号"
                    v-model="searchText"
                    class="search-input"
            >
            <button @click="searchOrders" class="search-btn">搜索</button>
            <button @click="resetSearch" class="reset-btn">重置</button>
        </div>

        <!-- 状态筛选 -->
        <div class="status-filter">
            <button
                    v-for="status in orderStatuses"
                    :key="status.value"
                    @click="filterByStatus(status.value)"
                    :class="{ 'active': activeStatus === status.value }"
            >
                {{ status.label }}
            </button>
        </div>

        <!-- 订单列表 -->
        <div class="order-list">
            <div
                    v-for="(order, index) in filteredOrders"
                    :key="index"
                    class="order-item"
            >
                <h3>{{ order.name }}</h3>
                <p class="order-desc">{{ order.description }}</p>
                <p class="order-time">{{ order.time }}</p>
            </div>

            <div v-if="filteredOrders.length === 0" class="no-orders">
                <p>没有更多订单啦~~~</p>
            </div>
        </div>
    </div>
</template>

<script setup>
    import { ref, computed } from 'vue'

    // 模拟订单数据
    const orders = ref([
        {
            id: '1001',
            name: '码神称号证书',
            description: '您的证书订单已经到达贵阳市，请尽快取件。',
            status: '待取件',
            time: '2025-08-01 14:30:22'
        },
        {
            id: '1002',
            name: '牛马黑奴证书',
            description: '您的牛马黑奴证书订单已经申请成功，正在加急制作中。',
            status: '待发货',
            time: '2025-08-02 09:15:10'
        }
    ])

    // 订单状态选项
    const orderStatuses = [
        { value: 'all', label: '全部' },
        { value: '待取件', label: '待取件' },
        { value: '待发货', label: '待发货' }
    ]

    const searchText = ref('')
    const activeStatus = ref('all')

    // 过滤订单
    const filteredOrders = computed(() => {
        let result = orders.value

        // 按状态筛选
        if (activeStatus.value !== 'all') {
            result = result.filter(order => order.status === activeStatus.value)
        }

        // 按搜索文本筛选
        if (searchText.value.trim()) {
            const searchTerm = searchText.value.toLowerCase().trim()
            result = result.filter(order =>
                order.id.includes(searchTerm) ||
                order.name.toLowerCase().includes(searchTerm)
            )
        }

        return result
    })

    // 搜索订单
    const searchOrders = () => {
        console.log('搜索订单:', searchText.value)
    }

    // 重置搜索
    const resetSearch = () => {
        searchText.value = ''
    }

    // 按状态筛选
    const filterByStatus = (status) => {
        activeStatus.value = status
    }
</script>

<style scoped>
    .order-container {
        padding: 20px;
        font-family: 'PingFang SC', 'Microsoft YaHei', sans-serif;
    }

    .search-bar {
        display: flex;
        gap: 10px;
        margin-bottom: 20px;
    }

    .search-input {
        flex: 1;
        padding: 8px 12px;
        border: 1px solid #ddd;
        border-radius: 4px;
    }

    .search-btn, .reset-btn {
        padding: 8px 16px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        transition: background-color 0.3s;
    }

    .search-btn {
        background-color: #1890ff;
        color: white;
    }

    .reset-btn {
        background-color: #f5f5f5;
        color: #666;
    }

    .status-filter {
        display: flex;
        gap: 10px;
        margin-bottom: 20px;
    }

    .status-filter button {
        padding: 6px 12px;
        border: 1px solid #ddd;
        border-radius: 4px;
        background: white;
        cursor: pointer;
        transition: all 0.3s;
    }

    .status-filter button.active {
        background-color: #1890ff;
        color: white;
        border-color: #1890ff;
    }

    .order-list {
        display: flex;
        flex-direction: column;
        gap: 15px;
    }

    .order-item {
        padding: 15px;
        border: 1px solid #eee;
        border-radius: 6px;
        background-color: #f9f9f9;
    }

    .order-item h3 {
        margin: 0 0 8px 0;
        color: #333;
        font-size: 16px;
    }

    .order-desc {
        margin: 0 0 5px 0;
        color: #666;
        font-size: 14px;
    }

    .order-time {
        margin: 0;
        color: #999;
        font-size: 12px;
        text-align: right;
    }

    .no-orders {
        text-align: center;
        padding: 30px;
        color: #999;
        font-size: 16px;
        background-color: #f9f9f9;
        border-radius: 6px;
    }
</style>