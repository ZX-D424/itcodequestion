<template>
    <div class="friend-container">

        <!-- 新增搜索框 -->
        <div class="friend-search">
            <input
                    type="text"
                    placeholder="请输入好友名字/账号"
                    v-model="searchText"
                    @keyup.enter="searchFriends"
            >
            <button @click="searchFriends">搜索</button>
        </div>

        <div class="friend-list">
            <div v-for="(friend, index) in filteredFriends" :key="index" class="friend-item">
                <div class="friend-avatar">
                    <span>{{ getInitial(friend.name) }}</span>
                </div>
                <div class="friend-info">
                    <h3>{{ friend.name }}</h3>
                    <p class="friend-nickname">昵称：{{ friend.nickname }}</p>
                </div>
            </div>

            <div v-if="filteredFriends.length === 0" class="no-friends">
                <p v-if="searchText">未找到匹配的好友</p>
                <p v-else>没有更多好友啦~~~</p>
            </div>
        </div>
    </div>
</template>

<script setup>
    import { ref, computed } from 'vue'

    // 模拟好友数据
    const friends = ref([
        {
            name: '大队长',
            nickname: '大队长',
            account: 'daiduizhang'
        },
        {
            name: '皮蛋',
            nickname: '皮蛋瘦肉粥',
            account: 'pidan'
        }
    ])

    const searchText = ref('')

    // 获取名字首字母用于头像
    const getInitial = (name) => {
        return name.charAt(0)
    }

    // 搜索好友
    const searchFriends = () => {
        console.log('搜索:', searchText.value)
    }

    // 过滤好友列表
    const filteredFriends = computed(() => {
        if (!searchText.value.trim()) return friends.value

        const searchTerm = searchText.value.toLowerCase().trim()
        return friends.value.filter(friend =>
            friend.name.toLowerCase().includes(searchTerm) ||
            friend.nickname.toLowerCase().includes(searchTerm) ||
            friend.account.toLowerCase().includes(searchTerm)
        )
    })
</script>

<style scoped>
    .friend-container {
        padding: 20px;
        font-family: 'PingFang SC', 'Microsoft YaHei', sans-serif;
    }

    /* 新增搜索框样式 */
    .friend-search {
        display: flex;
        gap: 10px;
        margin-bottom: 20px;
    }

    .friend-search input {
        flex: 1;
        padding: 8px 12px;
        border: 1px solid #ddd;
        border-radius: 4px;
    }

    .friend-search button {
        padding: 8px 16px;
        background-color: #1890ff;
        color: white;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        transition: background-color 0.3s;
    }

    .friend-search button:hover {
        background-color: #40a9ff;
    }

    .friend-list {
        display: flex;
        flex-direction: column;
        gap: 15px;
    }

    .friend-item {
        display: flex;
        align-items: center;
        padding: 12px;
        background-color: #f9f9f9;
        border-radius: 8px;
        transition: all 0.3s ease;
    }

    .friend-item:hover {
        background-color: #f0f0f0;
        transform: translateY(-2px);
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    }

    .friend-avatar {
        width: 40px;
        height: 40px;
        border-radius: 50%;
        background-color: #1890ff;
        color: white;
        display: flex;
        align-items: center;
        justify-content: center;
        margin-right: 15px;
        font-weight: bold;
    }

    .friend-info h3 {
        margin: 0;
        color: #333;
        font-size: 16px;
    }

    .friend-nickname {
        margin: 4px 0 0 0;
        color: #666;
        font-size: 14px;
    }

    .no-friends {
        text-align: center;
        padding: 30px;
        color: #999;
        font-size: 16px;
        background-color: #f9f9f9;
        border-radius: 8px;
    }
</style>