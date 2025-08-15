<template>
  <div class="question-detail-container">
    <el-card class="question-card">
      <div slot="header" class="detail-header">
        <el-button icon="ArrowLeft" @click="handleBack">返回</el-button>
        <h1>{{ question?.title }}</h1>
      </div>
      
      <div v-if="loading" class="loading-container">
        <el-loading spinning />
      </div>
      
      <div v-else-if="question" class="question-detail-content">
        <!-- 题目元信息 -->
        <el-descriptions 
          class="question-meta" 
          :column="4" 
          border
          :labelStyle="{ width: '90px', textAlign: 'right' }"
        >
          <!-- 题型展示优化 -->
          <el-descriptions-item label="题型">
            <template v-if="question?.questionType">
              <span v-if="sys_question_questiontype.loaded">
                {{ questionTypeLabel || '未知题型' }}
              </span>
              <span v-else>
                {{ fallbackMap[question.questionType] || '未知题型' }}
              </span>
            </template>
            <span v-else>未设置</span>
          </el-descriptions-item>
          
          <!-- 类目 -->
          <el-descriptions-item label="类目">{{ question?.category }}</el-descriptions-item>
          
          <!-- 来源 -->
          <el-descriptions-item label="来源">{{ question?.source || '未知' }}</el-descriptions-item>
        </el-descriptions>
        
        <!-- 题目内容 -->
        <div class="question-section">
          <h3 class="section-title">题目内容：</h3>
          <div class="question-content" v-html="question?.content"></div>
        </div>
        
        <!-- 题型专属区域 -->
        <div class="answer-section">
          <!-- 单选题 & 判断题 -->
          <div v-if="[1, 3].includes(question?.questionType)" class="question-options">
            <h3 class="section-title">选项：</h3>
            <el-radio-group v-model="selectedOption" class="options-group">
              <div 
                v-for="option in question?.options" 
                :key="option?.id" 
                class="option-item"
              >
                <el-radio :label="option?.id">
                  <span class="option-code">{{ option?.optionCode }}.</span>
                  <span class="option-text" v-html="option?.optionContent"></span>
                </el-radio>
              </div>
            </el-radio-group>
          </div>
          
          <!-- 多选题 -->
          <div v-else-if="question?.questionType === 2" class="question-multi">
            <h3 class="section-title">多选题选项：</h3>
            <el-checkbox-group v-model="selectedMultiOptions" class="options-group">
              <div 
                v-for="option in question?.options" 
                :key="option?.id" 
                class="option-item"
              >
                <el-checkbox :label="option?.id">
                  <span class="option-code">{{ option?.optionCode }}.</span>
                  <span class="option-text" v-html="option?.optionContent"></span>
                </el-checkbox>
              </div>
            </el-checkbox-group>
          </div>
          
          <!-- 填空题（使用简答题输入框替代） -->
          <div v-else-if="question?.questionType === 4" class="question-blank">
            <h3 class="section-title">填空题：</h3>
            <div class="blank-content" v-html="formattedBlankContent"></div>
            
            <!-- 复用简答题的输入框 -->
            <el-input
              type="textarea"
              v-model="blankAnswerText"
              :rows="6"
              placeholder="请输入各空答案，用逗号分隔（如：答案1,答案2,答案3）"
              class="essay-textarea"
            ></el-input>
          </div>
          
          <!-- 简答题 -->
          <div v-else-if="question?.questionType === 5" class="question-essay">
            <h3 class="section-title">简答题：</h3>
            <el-input
              type="textarea"
              v-model="essayAnswer"
              :rows="8"
              placeholder="请输入答案"
              class="essay-textarea"
            ></el-input>
          </div>
          
          <!-- 编程题 -->
          <div v-else-if="question?.questionType === 6" class="question-code">
            <h3 class="section-title">编程题：</h3>
            <div class="code-editor">
              <el-input
                type="textarea"
                v-model="codeAnswer"
                :rows="10"
                placeholder="请输入代码"
                monaco-editor
                language="java"
                :options="{
                  theme: 'vs-dark',
                  automaticLayout: true,
                  minimap: { enabled: false },
                  scrollBeyondLastLine: false
                }"
                class="monaco-editor"
              ></el-input>
            </div>
          </div>
        </div>
        
        <!-- 参考答案 -->
        <el-collapse v-model="activeCollapse" class="answer-collapse">
          <el-collapse-item name="answer" v-if="question?.answer">
            <template #title>
              <h3 class="answer-title">
                <el-icon name="document" />
                <span>参考答案</span>
              </h3>
            </template>
            <div class="answer-content" v-html="question?.answer?.answerContent"></div>
            
            <div v-if="[1, 2, 3].includes(question?.questionType)">
              <h4>正确选项：</h4>
              <div class="correct-options">
                <el-tag 
                  v-for="option in question?.options" 
                  :key="option?.id" 
                  v-if="option?.isCorrect"
                  type="success"
                  class="correct-option"
                >
                  <span class="option-code">{{ option?.optionCode }}.</span>
                  <span v-html="option?.optionContent"></span>
                </el-tag>
              </div>
            </div>
          </el-collapse-item>
        </el-collapse>
        
        <!-- 操作按钮 -->
        <div class="action-buttons">
          <el-button-group>
            <el-button 
              type="primary" 
              @click="handleSubmit"
              :loading="submitting"
            >
              <el-icon name="document-checked" />
              <span>提交答案</span>
            </el-button>
          </el-button-group>
        </div>
      </div>
      
      <div v-else class="error-state">
        <el-empty description="获取题目详情失败，请刷新页面重试" />
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted, computed, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { getManage } from '@/api/manage/manage';
import { getOptionByQuestionId } from '@/api/option/option';
import { getAnswerByQuestionId } from '@/api/option/option';
import { useDict } from '@/utils/dict';
import { ElMessage, ElMessageBox } from 'element-plus';

// 路由
const route = useRoute();
const router = useRouter();

// 字典
const { sys_question_questiontype, sys_status_question } = useDict('sys_question_questiontype', 'sys_status_question');

// 数据
const question = ref(null);
const loading = ref(false);
const submitting = ref(false);
const activeCollapse = ref([]);

// 不同题型的答案
const selectedOption = ref('');
const selectedMultiOptions = ref([]);
const essayAnswer = ref('');
const codeAnswer = ref('');
// 填空题使用的临时输入变量（核心修改）
const blankAnswerText = ref('');

// 硬编码备选映射
const fallbackMap = {
  1: '单选题',
  2: '多选题', 
  3: '判断题',
  4: '填空题',
  5: '简答题',
  6: '编程题'
};

// 计算属性：题型标签
const questionTypeLabel = computed(() => {
  if (!question.value?.questionType) return null;
  
  const targetValue = String(question.value.questionType);
  const matched = sys_question_questiontype.value?.find(
    item => String(item.dictValue) === targetValue
  );
  
  return matched?.dictLabel || null;
});

// 获取题目详情
const getQuestionDetail = async () => {
  const { id } = route.params;
  if (!id) return;
  
  loading.value = true;
  try {
    // 获取题目基本信息
    const questionRes = await getManage(id);
    question.value = questionRes.data;
    
    // 获取选项
    if ([1, 2, 3].includes(question.value.questionType)) {
      const optionRes = await getOptionByQuestionId(id);
      question.value.options = optionRes.rows
       .sort((a, b) => Number(a.sort) - Number(b.sort)); 
    }
    
    // 获取答案
    const answerRes = await getAnswerByQuestionId(id);
    question.value.answer = answerRes.rows?.[0] || null;
    
  } catch (error) {
    console.error('获取题目详情失败:', error);
    ElMessage.error('获取题目详情失败');
  } finally {
    loading.value = false;
  }
};

// 格式化填空题内容（仅显示占位符）
const formattedBlankContent = computed(() => {
  if (!question.value || question.value.questionType !== 4) return '';
  
  // 根据下划线数量生成对应占位符
  return (question.value.content.match(/_+/g) || []).map((_, index) => {
    return `<span class="blank-placeholder">【${index + 1}】</span>`;
  }).join(' ');
});

// 返回
const handleBack = () => {
  router.back();
};

// 提交答案
const handleSubmit = () => {
  if (!question.value) return;
  
  ElMessageBox.confirm(
    '确定提交答案吗？',
    '提示',
    { type: 'info' }
  ).then(() => {
    submitting.value = true;
    
    // 构建答案数据
    let answerData = {};
    switch (question.value.questionType) {
      case 1: // 单选题
      case 3: // 判断题
        answerData = { optionId: selectedOption.value };
        break;
      case 2: // 多选题
        answerData = { optionIds: selectedMultiOptions.value };
        break;
      case 4: // 填空题（处理逗号分隔的答案）
        answerData = {
          answers: blankAnswerText.value.split(',').map(item => item.trim())
        };
        break;
      case 5: // 简答题
        answerData = { content: essayAnswer.value };
        break;
      case 6: // 编程题
        answerData = { code: codeAnswer.value };
        break;
    }
    
    // 模拟提交
    setTimeout(() => {
      console.log('提交答案:', answerData);
      submitting.value = false;
      ElMessage.success('答案提交成功');
    }, 1000);
  }).catch(() => {
    ElMessage.info('取消提交');
  });
};

// 题型调试信息
watch(() => sys_question_questiontype.loaded, (loaded) => {
  if (loaded && question.value?.questionType) {
    const target = String(question.value.questionType);
    const matched = sys_question_questiontype.value?.find(
      item => String(item.dictValue) === target
    );
    console.log('题型匹配结果:', {
      questionType: question.value.questionType,
      matchedLabel: matched?.dictLabel,
      fallback: fallbackMap[question.value.questionType]
    });
  }
});

// 初始化
onMounted(() => {
  getQuestionDetail();
});
</script>

<style scoped>
.question-detail-container {
  padding: 20px;
  max-width: 1000px;
  margin: 0 auto;
}

.question-card {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  border-radius: 12px;
}

.detail-header {
  display: flex;
  align-items: center;
  gap: 15px;
  margin-bottom: 20px;
  border-bottom: 1px solid #e5e7eb;
  padding-bottom: 15px;
}

.question-meta {
  margin-bottom: 20px;
}

.question-section {
  margin-bottom: 25px;
}

.section-title {
  margin-bottom: 15px;
  color: #1f2937;
  font-weight: 600;
}

.question-content {
  padding: 15px;
  background-color: #f9fafb;
  border-radius: 8px;
  line-height: 1.8;
  border: 1px solid #e5e7eb;
}

.answer-section {
  margin-top: 20px;
  margin-bottom: 25px;
  padding: 20px;
  background-color: #f8fafc;
  border-radius: 10px;
  border: 1px solid #e5e7eb;
}

.options-group {
  padding: 10px 0;
  gap: 20px;
}

/* 选项容器样式 */
.option-item {
  display: flex;
  align-items: center; 
  margin-bottom: 12px;
  padding: 12px 16px;
  background-color: #fff;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  transition: all 0.3s ease;
}

.option-item:hover {
  background-color: #f0f9ff;
  border-color: #409eff;
  cursor: pointer;
}

.option-code {
  display: inline-block;
  width: 1em;
  text-align: right;
  margin-right: 8px;
  color: #409eff;
  font-weight: bold;
  font-size: x-large;
}

.option-text {
  flex: 1;
  line-height: 1.4;
  font-size: large;
}

/* 选中状态样式 */
:deep(.el-radio.is-checked .option-item, .el-checkbox.is-checked .option-item) {
  background-color: #e0f2ff;
  border-color: #409eff;
}

:deep(.el-radio__label),
:deep(.el-checkbox__label) {
  display: flex; 
  align-items: center;
  height: 40px; 
  padding: 0 12px; 
  border-radius: 8px;
  cursor: pointer; 
  transition: all 0.3s;
}

/* 填空占位符样式 */
.blank-placeholder {
  display: inline-block;
  min-width: 80px;
  height: 30px;
  margin: 0 5px;
  text-align: center;
  line-height: 30px;
  color: #666;
  border-bottom: 2px solid #ccc;
  cursor: text;
}

/* 简答题/填空题输入框样式 */
.essay-textarea {
  width: 100%;
  margin-top: 10px;
}

/* 参考答案区域 */
.answer-collapse {
  margin-top: 20px;
  background-color: #f0f9ff;
  border: 1px solid #d0eaff;
  border-radius: 8px;
}

.answer-title {
  display: flex;
  align-items: center;
  font-size: 16px;
}

.answer-content {
  padding: 15px;
  background-color: #f8fafc;
  border-radius: 6px;
  border: 1px solid #e5e7eb;
  margin-top: 10px;
}

.correct-options {
  margin-top: 10px;
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.correct-option {
  padding: 8px 12px;
  border-radius: 6px;
}

/* 操作按钮区域 */
.action-buttons {
  display: flex;
  justify-content: flex-end;
  margin-top: 30px;
  margin-bottom: 15px;
}

/* 加载和错误状态 */
.loading-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 300px;
}

.error-state {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 300px;
  color: #ef4444;
}

/* 编程题编辑器样式 */
.code-editor {
  margin-top: 10px;
}

.monaco-editor {
  font-family: 'Courier New', monospace;
  background-color: #1e1e1e !important;
  color: #d4d4d4 !important;
}

.monaco-editor .el-textarea__inner {
  background-color: #1e1e1e !important;
  color: #d4d4d4 !important;
  border: 1px solid #333 !important;
  font-size: 14px !important;
  line-height: 1.5 !important;
  padding: 10px !important;
}
</style>
