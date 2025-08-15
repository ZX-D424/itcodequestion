<template>
  <div class="exam-container">
    <!-- 页面标题和操作区 -->
    <div class="exam-header">
      <h2>笔试题目组卷</h2>
      <div class="header-actions">
        <el-button type="primary" @click="generatePDF">
          <el-icon><Document /></el-icon> 生成PDF
        </el-button>
        <el-button type="success" @click="saveToMyQuestionBank">
          <el-icon><FolderSaved /></el-icon> 生成（保存到个人题库）
        </el-button>
      </div>
    </div>

    <!-- 类目搜索区域 -->
    <div class="category-search-area">
      <div class="search-header">
        <h3>题目筛选条件</h3>
        <el-button type="primary" size="small" @click="addCategoryRow">
          <el-icon><Plus /></el-icon> 增加类目
        </el-button>
        <el-button 
          type="danger" 
          size="small" 
          @click="deleteSelectedRows" 
          :disabled="selectedRowKeys.length === 0"
        >
          <el-icon><Delete /></el-icon> 删除所选
        </el-button>
      </div>

      <!-- 搜索条件行 -->
      <el-table
        :data="categoryRows"
        border
        :row-key="(row) => row.id"
        :selected-row-keys="selectedRowKeys"
        @selection-change="handleSelectionChange"
        class="search-table"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column label="类目" prop="categoryId" width="200">
          <template #default="scope">
            <el-tree-select
              v-model="scope.row.categoryId"
              :data="categoryOptions"
              :props="{ value: 'id', label: 'name', children: 'children' }"
              value-key="id"
              placeholder="请选择类目"
              check-strictly
              style="width: 100%"
            />
          </template>
        </el-table-column>
        <el-table-column label="标签" prop="tagId" width="180">
          <template #default="scope">
            <el-select v-model="scope.row.tagId" placeholder="请选择标签" clearable style="width: 100%">
              <el-option
                v-for="tag in tagNameList"
                :key="tag.id"
                :label="tag.tagName"
                :value="tag.id"
              />
            </el-select>
          </template>
        </el-table-column>
        <el-table-column label="题型" prop="questionType" width="150">
          <template #default="scope">
            <el-select v-model="scope.row.questionType" placeholder="请选择题型" clearable style="width: 100%">
              <el-option
                v-for="type in questionTypes"
                :key="type.value"
                :label="type.label"
                :value="type.value"
              />
            </el-select>
          </template>
        </el-table-column>
        <el-table-column label="数量" prop="quantity" width="120">
          <template #default="scope">
            <el-input 
              v-model.number="scope.row.quantity" 
              type="number" 
              min="1" 
              placeholder="输入数量"
              style="width: 100%"
            />
          </template>
        </el-table-column>
        <el-table-column label="操作" width="80">
          <template #default="scope">
            <el-button 
              link 
              type="danger" 
              size="small" 
              @click="deleteRow(scope.row.id)"
            >
              <el-icon><Delete /></el-icon>
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 题目展示区域 -->
    <div class="questions-area">
      <div class="questions-header">
        <h3>题目预览</h3>
        <el-button 
          type="primary" 
          size="small" 
          @click="fetchQuestions"
        >
          <el-icon><Refresh /></el-icon> 刷新题目
        </el-button>
      </div>

      <el-card v-if="loading" class="loading-card">
        <div class="loading-container">
          <el-loading spinning size="large" />
          <p>正在加载题目...</p>
        </div>
      </el-card>

      <div v-else-if="allQuestions.length === 0" class="empty-state">
        <el-empty description="请设置筛选条件并点击刷新题目" />
      </div>

      <div v-else class="questions-list">
        <!-- 按题型分组展示 -->
        <div v-for="(typeGroup, type) in groupedQuestions" :key="type" class="question-type-group">
          <h4 class="type-title">
            {{ getQuestionTypeName(type) }} (共 {{ typeGroup.length }} 题)
          </h4>
          
          <div class="type-questions">
            <el-card 
              v-for="(question, index) in typeGroup" 
              :key="question.id" 
              class="question-card"
            >
              <div class="question-header">
                <span class="question-number">{{ index + 1 }}.</span>
                <span class="question-title">{{ question.title }}</span>
              </div>
              
              <div class="question-content" v-html="question.content"></div>
              
              <!-- 不同题型的特有内容 -->
              <div v-if="type === 1" class="question-options">
                <div 
                  v-for="(option, optIndex) in question.options" 
                  :key="option.id" 
                  class="option-item"
                >
                  <span class="option-letter">{{ String.fromCharCode(65 + optIndex) }}.</span>
                  <span class="option-content">{{ option.optionContent }}</span>
                </div>
              </div>
              
              <div v-if="type === 2" class="question-options">
                <div 
                  v-for="(option, optIndex) in question.options" 
                  :key="option.id" 
                  class="option-item"
                >
                  <span class="option-letter">{{ String.fromCharCode(65 + optIndex) }}.</span>
                  <span class="option-content">{{ option.optionContent }}</span>
                </div>
              </div>
              
              <div v-if="type === 3" class="judge-options">
                <div class="option-item">
                  <span class="option-letter">A.</span>
                  <span class="option-content">正确</span>
                </div>
                <div class="option-item">
                  <span class="option-letter">B.</span>
                  <span class="option-content">错误</span>
                </div>
              </div>
              
              <!-- 答案区域（生成PDF时会包含） -->
              <div class="question-answer" ref="answerRefs">
                <el-divider content-position="left">答案</el-divider>
                <div v-if="type === 1 || type === 2" class="answer-content">
                  正确答案: 
                  <span v-for="(option, idx) in question.correctOptions" :key="idx">
                    {{ String.fromCharCode(65 + option.optionIndex) }} {{ idx < question.correctOptions.length - 1 ? ',' : '' }}
                  </span>
                </div>
                <div v-if="type === 3" class="answer-content">
                  {{ question.answerContent || '无标准答案' }}
                </div>
                <div v-if="type === 4" class="answer-content">
                  <pre>{{ question.answerContent || '无标准答案' }}</pre>
                </div>
                <div v-if="type === 5" class="answer-content">
                  正确答案: {{ question.correctAnswer ? '正确' : '错误' }}
                </div>
                <div v-if="type === 6" class="answer-content">
                  <pre>{{ question.answerContent || '无标准答案' }}</pre>
                </div>
              </div>
            </el-card>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue';
import { listCategory } from '@/api/category/category';
import { listManage } from '@/api/manage/manage';
import { getOptionByQuestionId } from '@/api/option/option';
import { listAnswer } from '@/api/answer/answer';
import { listTag } from '@/api/tag/tag'; // 假设存在标签相关接口
import { ElMessage, ElMessageBox } from 'element-plus';
import { Plus, Delete, Refresh,Document } from '@element-plus/icons-vue';
// import { FolderSaved } from '@element-plus/icons-vue';
import html2pdf from 'html2pdf.js'; // 需要安装：npm install html2pdf.js

// 题型定义
const questionTypes = [
  { value: 1, label: '单选题' },
  { value: 2, label: '多选题' },
  { value: 3, label: '填空题' },
  { value: 4, label: '简答题' },
  { value: 5, label: '判断题' },
  { value: 6, label: '编程题' }
];

// 响应式数据
const categoryOptions = ref([]);
const tagNameList = ref([]);
const categoryRows = ref([]);
const selectedRowKeys = ref([]);
const loading = ref(false);
const allQuestions = ref([]);
const answerRefs = ref([]);

// 初始化数据
let rowId = 1;

// 获取题型名称
const getQuestionTypeName = (typeValue) => {
  const type = questionTypes.find(t => t.value === typeValue);
  return type ? type.label : '未知题型';
};

// 按题型分组题目
const groupedQuestions = computed(() => {
  const groups = {};
  allQuestions.value.forEach(question => {
    if (!groups[question.questionType]) {
      groups[question.questionType] = [];
    }
    groups[question.questionType].push(question);
  });
  return groups;
});

// 加载类目和标签数据
const loadCategoryAndTags = async () => {
  try {
    // 加载类目树
    const categoryRes = await listCategory({});
    categoryOptions.value = categoryRes.data || [];
    
    // 加载标签列表
    const tagRes = await listTag({});
    tagNameList.value = tagRes.rows || [];
  } catch (error) {
    console.error('加载类目和标签失败:', error);
    ElMessage.error('加载类目和标签失败');
  }
};

// 添加类目行
const addCategoryRow = () => {
  categoryRows.value.push({
    id: rowId++,
    categoryId: null,
    tagId: null,
    questionType: null,
    quantity: 5 // 默认数量
  });
};

// 处理选择变化
const handleSelectionChange = (selectedRows) => {
  selectedRowKeys.value = selectedRows.map(row => row.id);
};

// 删除行
const deleteRow = (id) => {
  categoryRows.value = categoryRows.value.filter(row => row.id !== id);
  // 如果删除的行在选中列表中，同步更新
  selectedRowKeys.value = selectedRowKeys.value.filter(key => key !== id);
};

// 删除选中行
const deleteSelectedRows = () => {
  ElMessageBox.confirm(
    '确定要删除选中的筛选条件吗？',
    '确认删除',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }
  ).then(() => {
    categoryRows.value = categoryRows.value.filter(
      row => !selectedRowKeys.value.includes(row.id)
    );
    selectedRowKeys.value = [];
    ElMessage.success('删除成功');
  }).catch(() => {
    // 取消删除
  });
};

// 获取题目数据
const fetchQuestions = async () => {
  if (categoryRows.value.length === 0) {
    ElMessage.warning('请添加至少一个筛选条件');
    return;
  }

  loading.value = true;
  allQuestions.value = [];

  try {
    // 遍历每个筛选条件行，获取对应题目
    for (const row of categoryRows.value) {
      if (!row.categoryId || !row.questionType || !row.quantity) {
        ElMessage.warning('请完善筛选条件');
        loading.value = false;
        return;
      }

      // 查询符合条件的题目
      const queryParams = {
        categoryId: row.categoryId,
        tagIds: row.tagId,
        questionType: Number(row.questionType),
        pageSize: 1000 // 一次获取足够多的题目
      };

      const questionRes = await listManage(queryParams);
      let questions = questionRes.rows || [];

      // 随机排序
      questions = questions.sort(() => Math.random() - 0.5);

      // 截取需要的数量
      const takeCount = Math.min(row.quantity, questions.length);
      const selectedQuestions = questions.slice(0, takeCount);

      // 为每个题目获取选项和答案
      for (const question of selectedQuestions) {
         const questionId=question.id;


       // ⚠️ 关键：确保 question.questionType 是数字
      if (typeof question.questionType !== 'number') {
        console.warn('题型非数字:', question.questionType);
        continue; // 跳过该题
      }


        // 获取选项
        if ([1, 2, 3].includes(question.questionType)) {
          const optionRes = await getOptionByQuestionId(questionId);

          question.options = optionRes.rows
       .sort((a, b) => Number(a.sort) - Number(b.sort));
          
          // 对于选择题和判断题，获取正确答案
          const answerRes = await listAnswer({questionId});
          if (answerRes.rows && answerRes.rows.length > 0) {
            const answer = answerRes.rows[0];
            question.correctOptions = [];
            
            // 解析正确选项编码
            if (answer.optionCodes) {
              const correctCodes = answer.optionCodes.split(',');
              question.correctOptions = question.options
                .filter(opt => correctCodes.includes(opt.optionCode))
                .map(opt => ({ ...opt, optionIndex: question.options.findIndex(o => o.id === opt.id) }));
            }
            
            // 判断题特殊处理
            if (question.questionType === 3) {
              question.correctAnswer = answer.optionCodes === 'A';
            }
          }
        } 
        // 获取答案（填空题、简答题、编程题）
        else if ([4, 5, 6].includes(question.questionType)) {
          const answerRes = await listAnswer({ questionId: question.id });
          if (answerRes.rows && answerRes.rows.length > 0) {
            question.answerContent = answerRes.rows[0].answerContent;
          }
        }

        allQuestions.value.push(question);
      }
    }

    ElMessage.success(`共加载 ${allQuestions.value.length} 道题目`);
  } catch (error) {
    console.error('获取题目失败:', error);
    ElMessage.error('获取题目失败');
  } finally {
    loading.value = false;
  }
};

// 保存到个人题库（预留功能）
const saveToMyQuestionBank = () => {
  if (allQuestions.value.length === 0) {
    ElMessage.warning('请先加载题目');
    return;
  }

  // 这里只是模拟保存，实际应调用后端接口
  const saveData = {
    title: `笔试题库_${new Date().getTime()}`,
    questionIds: allQuestions.value.map(q => q.id),
    createTime: new Date()
  };

  // 预留：保存到个人题库的逻辑
  console.log('保存到个人题库的数据:', saveData);
  
  ElMessage.success('已保存到个人题库（模拟）');
};

// 生成PDF
const generatePDF = () => {
  if (allQuestions.value.length === 0) {
    ElMessage.warning('请先加载题目');
    return;
  }

  // 创建一个临时DOM用于生成PDF
  const pdfContainer = document.createElement('div');
  pdfContainer.style.width = '100%';
  pdfContainer.style.padding = '20px';
  
  // 添加标题
  const titleElement = document.createElement('h1');
  titleElement.textContent = '笔试题目及答案';
  titleElement.style.textAlign = 'center';
  titleElement.style.marginBottom = '30px';
  pdfContainer.appendChild(titleElement);
  
  // 添加题目内容
  Object.entries(groupedQuestions.value).forEach(([type, questions]) => {
    const typeTitle = document.createElement('h2');
    typeTitle.textContent = getQuestionTypeName(Number(type));
    typeTitle.style.marginTop = '20px';
    pdfContainer.appendChild(typeTitle);
    
    questions.forEach((question, index) => {
      const questionElement = document.createElement('div');
      questionElement.style.margin = '15px 0';
      
      // 题目序号和标题
      const questionHeader = document.createElement('div');
      questionHeader.style.fontWeight = 'bold';
      questionHeader.style.marginBottom = '10px';
      questionHeader.textContent = `${index + 1}. ${question.title}`;
      questionElement.appendChild(questionHeader);
      
      // 题目内容
      const contentElement = document.createElement('div');
      contentElement.innerHTML = question.content;
      contentElement.style.marginBottom = '10px';
      questionElement.appendChild(contentElement);
      
      // 选项（如果有）
      if ([1, 2, 5].includes(question.questionType) && question.options) {
        const optionsContainer = document.createElement('div');
        optionsContainer.style.marginLeft = '20px';
        
        question.options.forEach((option, optIndex) => {
          const optionElement = document.createElement('div');
          optionElement.style.margin = '5px 0';
          optionElement.textContent = `${String.fromCharCode(65 + optIndex)}. ${option.optionContent}`;
          optionsContainer.appendChild(optionElement);
        });
        
        questionElement.appendChild(optionsContainer);
      }
      
      // 答案
      const answerContainer = document.createElement('div');
      answerContainer.style.marginTop = '10px';
      answerContainer.style.paddingTop = '10px';
      answerContainer.style.borderTop = '1px dashed #ccc';
      
      const answerTitle = document.createElement('div');
      answerTitle.style.fontWeight = 'bold';
      answerTitle.textContent = '答案：';
      answerContainer.appendChild(answerTitle);
      
      const answerContent = document.createElement('div');
      if ([1, 2].includes(question.questionType) && question.correctOptions) {
        answerContent.textContent = question.correctOptions
          .map(opt => String.fromCharCode(65 + opt.optionIndex))
          .join(', ');
      } else if (question.answerContent) {
        answerContent.textContent = question.answerContent;
      } else if (question.questionType === 5) {
        answerContent.textContent = question.correctAnswer ? '正确' : '错误';
      } else {
        answerContent.textContent = '无标准答案';
      }
      answerContainer.appendChild(answerContent);
      
      questionElement.appendChild(answerContainer);
      pdfContainer.appendChild(questionElement);
    });
  });
  
  // 添加到body
  document.body.appendChild(pdfContainer);
  
  // 配置PDF选项
  const opt = {
    margin: 10,
    filename: `笔试题目_${new Date().getTime()}.pdf`,
    image: { type: 'jpeg', quality: 0.98 },
    html2canvas: { scale: 2 },
    jsPDF: { unit: 'mm', format: 'a4', orientation: 'portrait' }
  };
  
  // 生成PDF
  html2pdf().from(pdfContainer).set(opt).save().then(() => {
    // 移除临时元素
    document.body.removeChild(pdfContainer);
  });
};

// 初始化
onMounted(() => {
  loadCategoryAndTags();
  // 默认添加一行
  addCategoryRow();
});
</script>

<style scoped>
.exam-container {
  padding: 20px;
  background-color: #f5f7fa;
  min-height: calc(100vh - 60px);
}

.exam-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding-bottom: 10px;
  border-bottom: 1px solid #e5e7eb;
}

.header-actions {
  display: flex;
  gap: 10px;
}

.category-search-area {
  background-color: #fff;
  border-radius: 6px;
  padding: 15px;
  margin-bottom: 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
}

.search-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.search-table {
  width: 100%;
}

.questions-area {
  background-color: #fff;
  border-radius: 6px;
  padding: 15px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
}

.questions-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
  padding-bottom: 10px;
  border-bottom: 1px solid #e5e7eb;
}

.loading-card {
  text-align: center;
  padding: 50px 0;
}

.loading-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 15px;
}

.empty-state {
  padding: 50px 0;
  text-align: center;
}

.questions-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.question-type-group {
  margin-bottom: 20px;
}

.type-title {
  font-size: 18px;
  color: #1f2329;
  margin-bottom: 15px;
  padding-bottom: 5px;
  border-bottom: 2px solid #409eff;
}

.type-questions {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.question-card {
  transition: all 0.3s;
}

.question-card:hover {
  border-color: #409eff;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.question-header {
  display: flex;
  align-items: flex-start;
  margin-bottom: 10px;
}

.question-number {
  font-weight: bold;
  margin-right: 5px;
  color: #409eff;
}

.question-title {
  font-weight: bold;
  flex: 1;
}

.question-content {
  margin-bottom: 15px;
  line-height: 1.6;
  color: #303133;
}

.question-options {
  margin-left: 20px;
  margin-bottom: 15px;
}

.option-item {
  display: flex;
  align-items: flex-start;
  margin: 8px 0;
}

.option-letter {
  font-weight: bold;
  margin-right: 8px;
  min-width: 20px;
}

.option-content {
  flex: 1;
}

.judge-options {
  display: flex;
  gap: 20px;
  margin-left: 20px;
  margin-bottom: 15px;
}

.question-answer {
  margin-top: 15px;
  padding-top: 10px;
  border-top: 1px dashed #e5e7eb;
}

.answer-content {
  margin-top: 10px;
  line-height: 1.6;
}

.answer-content pre {
  background-color: #f5f5f5;
  padding: 10px;
  border-radius: 4px;
  overflow-x: auto;
}
</style>