NEW_FILE_CODE
<template>
  <div class="question-bank-container">
    <!-- 左侧类目列表 -->
    <div class="category-sidebar">
      <div class="sidebar-header">
        <h2>题库类目</h2>
        <el-pagination
          small
          :total="categoryTotal"
          :page-size="categoryPageSize"
          :current-page="categoryPageNum"
          @current-change="handleCategoryPageChange"
          layout="prev, pager, next"
        />
      </div>
      
      <el-tree
        :data="categoryList"
        :props="categoryProps"
        :expand-on-click-node="false"
        @node-click="handleCategoryClick"
        class="category-tree"
      />
    </div>
    
    <!-- 右侧题目列表 -->
    <div class="question-content">
      <div v-if="loading" class="loading-container">
        <el-loading spinning />
      </div>
      
      <div v-else-if="selectedCategoryId">
        <div class="content-header">
          <h2>{{ selectedCategoryName }} (共 {{ questionTotal }} 题)</h2>
          <el-pagination
            :total="questionTotal"
            :page-size="questionPageSize"
            :current-page="questionPageNum"
            @current-change="handleQuestionPageChange"
            layout="prev, pager, next, jumper"
          />
        </div>
        
        <div class="question-list">
          <div 
            v-for="(question, index) in questionList" 
            :key="question.id" 
            class="question-item"
            @click="goToDetail(question.id)"
          >
            <div class="question-number">{{ index + 1 }}. {{ question.title }}</div>
            <div class="question-brief">
              {{ formatQuestionContent(question.content) }}
            </div>
            <div class="question-meta">
              <span class="question-type">
                <dict-tag :options="sys_question_questiontype" :value="question.questionType"/>
              </span>
              <span class="question-status">
                <dict-tag :options="sys_status_question" :value="question.status"/>
              </span>
            </div>
          </div>
        </div>
      </div>
      
      <div v-else class="empty-state">
        <p>请选择左侧的类目查看题目</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRouter } from 'vue-router';
import { listCategory } from '@/api/category/category';
import { listManage } from '@/api/manage/manage';
import { useDict } from '@/utils/dict';

// 路由
const router = useRouter();

// 字典
const { sys_question_questiontype, sys_status_question } = useDict('sys_question_questiontype', 'sys_status_question');

// 类目相关
const categoryList = ref([]);
const categoryTotal = ref(0);
const categoryPageNum = ref(1);
const categoryPageSize = ref(10);
const categoryProps = {
  value: 'id',
  label: 'name',
  children: 'children'
};
const selectedCategoryId = ref(null);
const selectedCategoryName = ref('');

// 题目相关
const questionList = ref([]);
const questionTotal = ref(0);
const questionPageNum = ref(1);
const questionPageSize = ref(10);
const loading = ref(false);

// 获取类目列表
const getCategoryList = async () => {
  try {
    const response = await listCategory({
      pageNum: categoryPageNum.value,
      pageSize: categoryPageSize.value
    });
    categoryList.value = response.data;
    categoryTotal.value = response.total || 0;
  } catch (error) {
    console.error('获取类目列表失败:', error);
  }
};

// 获取题目列表
const getQuestionList = async () => {
  if (!selectedCategoryId.value) return;
  
  loading.value = true;
  try {
    const response = await listManage({
      categoryId: selectedCategoryId.value,
      pageNum: questionPageNum.value,
      pageSize: questionPageSize.value
    });
    questionList.value = response.rows;
    questionTotal.value = response.total || 0;
  } catch (error) {
    console.error('获取题目列表失败:', error);
  } finally {
    loading.value = false;
  }
};

// 处理类目点击
const handleCategoryClick = (data) => {
  selectedCategoryId.value = data.id;
  selectedCategoryName.value = data.name;
  questionPageNum.value = 1; // 重置页码
  getQuestionList();
};

// 处理类目分页变化
const handleCategoryPageChange = (page) => {
  categoryPageNum.value = page;
  getCategoryList();
};

// 处理题目分页变化
const handleQuestionPageChange = (page) => {
  questionPageNum.value = page;
  getQuestionList();
};

// 格式化题目内容（显示部分内容）
const formatQuestionContent = (content) => {
  if (!content) return '';
  // 移除HTML标签
  const plainText = content.replace(/<[^>]+>/g, '');
  // 限制显示长度
  return plainText.length > 100 ? plainText.substring(0, 100) + '...' : plainText;
};

// 跳转到详情页
const goToDetail = (id) => {
  router.push(`/section-bank/questionDetail/${id}`);

};

// 初始化
onMounted(() => {
  getCategoryList();
});
</script>

<style scoped>
.question-bank-container {
  display: flex;
  min-height: calc(100vh - 60px);
}

.category-sidebar {
  width: 300px;
  border-right: 1px solid #e5e7eb;
  padding: 20px;
  background-color: #f9fafb;
}

.sidebar-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.category-tree {
  height: calc(100% - 60px);
  overflow-y: auto;
}

.question-content {
  flex: 1;
  padding: 20px;
  overflow-y: auto;
}

.content-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding-bottom: 10px;
  border-bottom: 1px solid #e5e7eb;
}

.question-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.question-item {
  padding: 15px;
  border: 1px solid #e5e7eb;
  border-radius: 6px;
  cursor: pointer;
  transition: all 0.3s;
}

.question-item:hover {
  border-color: #409eff;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.08);
}

.question-number {
  font-weight: bold;
  margin-bottom: 8px;
  color: #1f2937;
}

.question-brief {
  color: #4b5563;
  margin-bottom: 10px;
  line-height: 1.5;
}

.question-meta {
  display: flex;
  gap: 10px;
  font-size: 12px;
}

.loading-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 300px;
}

.empty-state {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 300px;
  color: #6b7280;
  font-size: 16px;
}
</style>