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
              <!-- 优先使用字典数据，失败使用硬编码映射 -->
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
          
          <!-- 状态 -->
          <el-descriptions-item label="状态">
            <dict-tag 
              :options="sys_status_question" 
              :value="question?.status"
              fallback="未知状态"
            />
          </el-descriptions-item>
          
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
          
          <!-- 填空题 -->
          <div v-else-if="question?.questionType === 4" class="question-blank">
            <h3 class="section-title">填空题：</h3>
            <div class="blank-content" v-html="formattedBlankContent"></div>
            <el-form ref="blankForm" :model="blankAnswers" class="blank-form">
              <el-row :gutter="20">
                <el-col 
                  v-for="(answer, index) in blankAnswers" 
                  :key="index"
                  :span="12"
                >
                  <el-form-item :label="`答案 ${index + 1}：`" class="blank-item">
                    <el-input v-model="answer.value" placeholder="请输入答案"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-form>
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
const showAnswer = ref(false);
const activeCollapse = ref([]);

// 不同题型的答案
const selectedOption = ref('');
const selectedMultiOptions = ref([]);
const blankAnswers = ref([]);
const essayAnswer = ref('');
const codeAnswer = ref('');

// 硬编码备选映射（当字典未加载时使用）
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
  
  // 确保值为字符串类型
  const targetValue = String(question.value.questionType);
  
  // 优先从字典中查找
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
    
    // 初始化填空题答案框
    if (question.value.questionType === 4) {
      const blankCount = (question.value.content.match(/_+/g) || []).length;
      blankAnswers.value = Array(blankCount).fill().map(() => ({ value: '' }));
    }
  } catch (error) {
    console.error('获取题目详情失败:', error);
    ElMessage.error('获取题目详情失败');
  } finally {
    loading.value = false;
  }
};

// 格式化填空题内容
const formattedBlankContent = computed(() => {
  if (!question.value || question.value.questionType !== 4) return '';
  
  let content = question.value.content;
  return content.replace(/_+/g, (match, index) => {
    return `<span class="blank-placeholder">【${Math.floor(index / match.length) + 1}】</span>`;
  });
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
    // 模拟提交
    submitting.value = true;
    setTimeout(() => {
      submitting.value = false;
      ElMessage.success('答案提交成功');
    }, 1000);
  }).catch(() => {
    ElMessage.info('取消提交');
  });
};

// 初始化
onMounted(() => {
  getQuestionDetail();
});

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

/* 选项容器：统一每个选项的布局 */
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

/* 鼠标悬浮反馈 */
.option-item:hover {
  background-color: #f0f9ff;
  border-color: #409eff;
  cursor: pointer;
}

/* 选项编码：固定宽度，让内容对齐 */
.option-code {
  display: inline-block;
  width: 1em;
  text-align: right;
  margin-right: 8px;
  color: #409eff;
  font-weight: bold;
  font-size: x-large;
}

/* 选项内容：自适应剩余空间 */
.option-text {
  flex: 1;
  line-height: 1.4;
  font-size: large;
}

/* 选中状态强化 */
:deep(.el-radio.is-checked .option-item, .el-checkbox.is-checked .option-item) {
  background-color: #e0f2ff;
  border-color: #409eff;
}

:deep(.el-radio__label) {
  display: flex; 
  align-items: center;
  height: 40px; 
  padding: 0 12px; 
  border-radius: 8px;
  cursor: pointer; 
  transition: all 0.3s;
}

/* 题型标签样式 */
.question-type {
  padding: 4px 10px;
  border-radius: 4px;
  font-size: 14px;
  background-color: #f0f9ff;
}

.question-type.single-choice { color: #1a73e8; }
.question-type.multiple-choice { color: #34a853; }
.question-type.judge { color: #9c27b0; }
.question-type.fill { color: #fb8c00; }
.question-type.short-answer { color: #e53935; }
.question-type.code { color: #2e7d32; }

/* 选项容器 */
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

/* 鼠标悬浮反馈 */
.option-item:hover {
  background-color: #f0f9ff;
  border-color: #409eff;
  cursor: pointer;
}

/* 选项编码：固定宽度，让内容对齐 */
.option-code {
  display: inline-block;
  width: 1em;
  text-align: right;
  margin-right: 8px;
  color: #409eff;
  font-weight: bold;
  font-size: x-large;
}

/* 选项内容：自适应剩余空间 */
.option-text {
  flex: 1;
  line-height: 1.4;
  font-size: large;
}

/* 加载状态样式 */
.text-muted {
  color: #999;
  font-style: italic;
}
</style>