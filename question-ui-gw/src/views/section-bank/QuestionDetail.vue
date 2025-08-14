<!-- NEW_FILE_CODE
<template>
  <div class="question-detail-container">
    <el-card>
      <div slot="header" class="detail-header">
        <el-button icon="ArrowLeft" @click="handleBack">返回</el-button>
        <h1>{{ question.title }}</h1>
      </div>
      
      <div v-if="loading" class="loading-container">
        <el-loading spinning />
      </div>
      
      <div v-else-if="question" class="question-detail-content">
        <div class="question-meta">
          <span>
            类目：{{ question.category }}
          </span>
          <span>
            类型：<dict-tag :options="sys_question_questiontype" :value="question.questionType"/>
          </span>
          <span>
            状态：<dict-tag :options="sys_status_question" :value="question.status"/>
          </span>
          <span>
            来源：{{ question.source || '未知' }}
          </span>
        </div>
        
        <div class="question-content">
          <h3>题目内容：</h3>
          <div v-html="question.content"></div>
        </div>
        
        
        <div v-if="question.questionType === 1 || question.questionType === 5" class="question-options">
          <h3>选项：</h3>
          <el-radio-group v-model="selectedOption" v-if="question.questionType === 1 || question.questionType === 5">
            <el-radio 
              v-for="option in question.options" 
              :key="option.id" 
              :label="option.id"
              class="option-item"
            >
              <span class="option-code">{{ option.optionCode }}.</span>
              <span v-html="option.optionContent"></span>
            </el-radio>
          </el-radio-group>
        </div>
        
        <div v-if="question.questionType === 2" class="question-blank">
          <h3>填空题：</h3>
          <div v-html="formattedBlankContent"></div>
          <el-form ref="blankForm" :model="blankAnswers" class="blank-form">
            <el-form-item 
              v-for="(answer, index) in blankAnswers" 
              :key="index"
              :label="`答案 ${index + 1}：`"
            >
              <el-input v-model="answer.value" placeholder="请输入答案"></el-input>
            </el-form-item>
          </el-form>
        </div>
        
        <div v-if="question.questionType === 3" class="question-essay">
          <h3>简答题：</h3>
          <el-input
            type="textarea"
            v-model="essayAnswer"
            rows="6"
            placeholder="请输入答案"
          ></el-input>
        </div>
        
        <div v-if="question.questionType === 4" class="question-code">
          <h3>编程题：</h3>
          <el-input
            type="textarea"
            v-model="codeAnswer"
            rows="10"
            placeholder="请输入代码"
            monaco-editor
            language="java"
          ></el-input>
        </div>
        
        <div v-if="question.questionType === 2" class="question-options">
          <h3>多选题：</h3>
          <el-checkbox-group v-model="selectedMultiOptions">
            <el-checkbox 
              v-for="option in question.options" 
              :key="option.id" 
              :label="option.id"
              class="option-item"
            >
              <span class="option-code">{{ option.optionCode }}.</span>
              <span v-html="option.optionContent"></span>
            </el-checkbox>
          </el-checkbox-group>
        </div>
        
        <div class="question-answer" v-if="showAnswer">
          <h3>参考答案：</h3>
          <div v-html="question.answer?.content"></div>
          
          <div v-if="question.questionType === 1 || question.questionType === 2 || question.questionType === 5">
            <h4>正确选项：</h4>
            <div v-for="option in question.options" :key="option.id" v-if="option.isCorrect">
              <span class="option-code">{{ option.optionCode }}.</span>
              <span v-html="option.optionContent"></span>
            </div>
          </div>
        </div>
        
        <div class="action-buttons">
          <el-button @click="showAnswer = !showAnswer">
            {{ showAnswer ? '隐藏答案' : '查看答案' }}
          </el-button>
          <el-button type="primary" @click="handleSubmit">提交答案</el-button>
        </div>
      </div>
      
      <div v-else class="error-state">
        <p>获取题目详情失败，请刷新页面重试</p>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { getManage } from '@/api/manage/manage';
import { getOptionByQuestionId } from '@/api/option/option';
import { getAnswerByQuestionId } from '@/api/option/option';
import { useDict } from '@/utils/dict';

// 路由
const route = useRoute();
const router = useRouter();

// 字典
const { sys_question_questiontype, sys_status_question } = useDict('sys_question_questiontype', 'sys_status_question');

// 数据
const question = ref(null);
const loading = ref(false);
const showAnswer = ref(false);

// 不同题型的答案
const selectedOption = ref('');
const selectedMultiOptions = ref([]);
const blankAnswers = ref([]);
const essayAnswer = ref('');
const codeAnswer = ref('');

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
    if ([1, 2, 5].includes(question.value.questionType)) {
      const optionRes = await getOptionByQuestionId(id);

      question.value.options = optionRes.data;
    }
    
    // 获取答案
    const answerRes = await getAnswerByQuestionId(id);

    question.value.answer = answerRes.data;
    
    // 初始化填空题答案框
    if (question.value.questionType === 2) {
      // 假设根据题目内容中的下划线数量来创建答案框
      const blankCount = (question.value.content.match(/_+/g) || []).length;
      blankAnswers.value = Array(blankCount).fill().map(() => ({ value: '' }));
    }
  } catch (error) {
    console.error('获取题目详情失败:', error);
  } finally {
    loading.value = false;
  }
};

// 格式化填空题内容
const formattedBlankContent = computed(() => {
  if (!question.value || question.value.questionType !== 2) return '';
  
  let content = question.value.content;
  // 将下划线替换为可点击的标记
  return content.replace(/_+/g, (match, index) => {
    return `<span class="blank-placeholder">【${index/ match.length + 1}】</span>`;
  });
});

// 返回
const handleBack = () => {
  router.back();
};

// 提交答案
const handleSubmit = () => {
  // 这里可以添加答案提交逻辑
  // 根据题型不同，处理不同的答案格式
  let answerData = {};
  
  switch (question.value.questionType) {
    case 1: // 单选题
    case 5: // 判断题
      answerData = { optionId: selectedOption.value };
      break;
    case 2: // 多选题
      answerData = { optionIds: selectedMultiOptions.value };
      break;
    case 3: // 简答题
      answerData = { content: essayAnswer.value };
      break;
    case 4: // 编程题
      answerData = { code: codeAnswer.value };
      break;
  }
  
  console.log('提交答案:', answerData);
  // 实际项目中这里会调用API提交答案
  ElMessage.success('答案已提交');
};

// 初始化
onMounted(() => {
  getQuestionDetail();
});
</script>

<style scoped>
.question-detail-container {
  padding: 20px;
}

.detail-header {
  display: flex;
  align-items: center;
  gap: 15px;
  margin-bottom: 20px;
}

.question-meta {
  display: flex;
  flex-wrap: wrap;
  gap: 15px;
  margin-bottom: 20px;
  padding-bottom: 10px;
  border-bottom: 1px solid #e5e7eb;
  color: #6b7280;
  font-size: 14px;
}

.question-content {
  margin-bottom: 25px;
  line-height: 1.8;
}

.question-options {
  margin-bottom: 25px;
}

.option-item {
  margin-bottom: 10px;
  line-height: 1.6;
}

.option-code {
  font-weight: bold;
  margin-right: 5px;
}

.question-blank {
  margin-bottom: 25px;
}

.blank-placeholder {
  display: inline-block;
  width: 100px;
  height: 30px;
  border-bottom: 2px solid #ccc;
  margin: 0 5px;
  text-align: center;
  line-height: 30px;
  color: #666;
}

.blank-form {
  margin-top: 20px;
}

.question-essay, .question-code {
  margin-bottom: 25px;
}

.question-answer {
  margin-bottom: 25px;
  padding: 15px;
  background-color: #f9fafb;
  border-radius: 6px;
}

.action-buttons {
  display: flex;
  gap: 10px;
  margin-top: 30px;
}

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
</style> -->




<template>
  <div class="question-detail-container">
    <el-card>
      <div slot="header" class="detail-header">
        <el-button icon="ArrowLeft" @click="handleBack">返回</el-button>
        <h1>{{ question?.title }}</h1>
      </div>
      
      <div v-if="loading" class="loading-container">
        <el-loading spinning />
      </div>
      
      <div v-else-if="question" class="question-detail-content">
        <div class="question-meta">
          <span>
            类目：{{ question?.category }}
          </span>
          <span>
            类型：<dict-tag :options="sys_question_questiontype" :value="question?.questionType"/>
          </span>
          <span>
            状态：<dict-tag :options="sys_status_question" :value="question?.status"/>
          </span>
          <span>
            来源：{{ question?.source || '未知' }}
          </span>
        </div>
        
        <div class="question-content">
          <h3>题目内容：</h3>
          <div v-html="question?.content"></div>
        </div>
        
        不同题型的展示
        <div v-if="[1, 5].includes(question?.questionType)" class="question-options">
          <h3>选项：</h3>
          <el-radio-group v-model="selectedOption">
            <el-radio 
              v-for="option in question?.options" 
              :key="option?.id" 
              :label="option?.id"
              class="option-item"
            >
              <span class="option-code">{{ option?.optionCode }}.</span>
              <span v-html="option?.optionContent"></span>
            </el-radio>
          </el-radio-group>
        </div>
        
        <div v-if="question?.questionType === 2" class="question-blank">
          <h3>填空题：</h3>
          <div v-html="formattedBlankContent"></div>
          <el-form ref="blankForm" :model="blankAnswers" class="blank-form">
            <el-form-item 
              v-for="(answer, index) in blankAnswers" 
              :key="index"
              :label="`答案 ${index + 1}：`"
            >
              <el-input v-model="answer.value" placeholder="请输入答案"></el-input>
            </el-form-item>
          </el-form>
        </div>
        
        <div v-if="question?.questionType === 3" class="question-essay">
          <h3>简答题：</h3>
          <el-input
            type="textarea"
            v-model="essayAnswer"
            rows="6"
            placeholder="请输入答案"
          ></el-input>
        </div>
        
        <div v-if="question?.questionType === 4" class="question-code">
          <h3>编程题：</h3>
          <el-input
            type="textarea"
            v-model="codeAnswer"
            rows="10"
            placeholder="请输入代码"
            monaco-editor
            language="java"
          ></el-input>
        </div>
        
        <div v-if="question?.questionType === 2" class="question-options">
          <h3>多选题：</h3>
          <el-checkbox-group v-model="selectedMultiOptions">
            <el-checkbox 
              v-for="option in question?.options" 
              :key="option?.id" 
              :label="option?.id"
              class="option-item"
            >
              <span class="option-code">{{ option?.optionCode }}.</span>
              <span v-html="option?.optionContent"></span>
            </el-checkbox>
          </el-checkbox-group>
        </div>
        
        <div class="question-answer" v-if="showAnswer">
          <h3>参考答案：</h3>
          <div v-html="question?.answer?.answerContent"></div>
          
          <div v-if="[1, 2, 5].includes(question?.questionType)">
            <h4>正确选项：</h4>
            <div v-for="option in question?.options" :key="option?.id" v-if="option?.isCorrect">
              <span class="option-code">{{ option?.optionCode }}.</span>
              <span v-html="option?.optionContent"></span>
            </div>
          </div>
        </div>
        
        <div class="action-buttons">
          <el-button @click="showAnswer = !showAnswer">
            {{ showAnswer ? '隐藏答案' : '查看答案' }}
          </el-button>
          <el-button type="primary" @click="handleSubmit">提交答案</el-button>
        </div>
      </div>
      
      <div v-else class="error-state">
        <p>获取题目详情失败，请刷新页面重试</p>
      </div>
    </el-card>
  </div>
</template>




<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { getManage } from '@/api/manage/manage';
import { getOptionByQuestionId } from '@/api/option/option';
import { getAnswerByQuestionId } from '@/api/option/option';
import { useDict } from '@/utils/dict';
import { ElMessage } from 'element-plus';

// 路由
const route = useRoute();
const router = useRouter();

// 字典
const { sys_question_questiontype, sys_status_question } = useDict('sys_question_questiontype', 'sys_status_question');

// 数据
const question = ref(null);
const loading = ref(false);
const showAnswer = ref(false);

// 不同题型的答案
const selectedOption = ref('');
const selectedMultiOptions = ref([]);
const blankAnswers = ref([]);
const essayAnswer = ref('');
const codeAnswer = ref('');

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
    if ([1, 2, 5].includes(question.value?.questionType)) {
      const optionRes = await getOptionByQuestionId(id);
      question.value.options = optionRes.rows
       .sort((a, b) => Number(a.sort) - Number(b.sort)); 
    }
    
    // 获取答案
    const answerRes = await getAnswerByQuestionId(id);
    question.value.answer = answerRes.rows?.[0] || null;
    
    // 初始化填空题答案框
    if (question.value?.questionType === 2) {
      // 假设根据题目内容中的下划线数量来创建答案框
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
  if (!question.value || question.value.questionType !== 2) return '';
  
  let content = question.value.content;
  // 将下划线替换为可点击的标记
  return content.replace(/_+/g, (match, index) => {
    return `<span class="blank-placeholder">【${(index / match.length) + 1}】</span>`;
  });
});

// 返回
const handleBack = () => {
  router.back();
};

// 提交答案
const handleSubmit = () => {
  if (!question.value) return;
  
  // 根据题型不同，处理不同的答案格式
  let answerData = {};
  
  switch (question.value.questionType) {
    case 1: // 单选题
    case 5: // 判断题
      answerData = { optionId: selectedOption.value };
      break;
    case 2: // 多选题
      answerData = { optionIds: selectedMultiOptions.value };
      break;
    case 3: // 简答题
      answerData = { content: essayAnswer.value };
      break;
    case 4: // 编程题
      answerData = { code: codeAnswer.value };
      break;
  }
  
  console.log('提交答案:', answerData);
  // 实际项目中这里会调用API提交答案
  ElMessage.success('答案已提交');
};

// 初始化
onMounted(() => {
  getQuestionDetail();
});
</script>

<style scoped>
.question-detail-container {
  padding: 20px;
}

.detail-header {
  display: flex;
  align-items: center;
  gap: 15px;
  margin-bottom: 20px;
}

.question-meta {
  display: flex;
  flex-wrap: wrap;
  gap: 15px;
  margin-bottom: 20px;
  padding-bottom: 10px;
  border-bottom: 1px solid #e5e7eb;
  color: #6b7280;
  font-size: 14px;
}

.question-content {
  margin-bottom: 25px;
  line-height: 1.8;
}

.question-options {
  margin-bottom: 25px;
}

.option-item {
  margin-bottom: 10px;
  line-height: 1.6;
}

.option-code {
  font-weight: bold;
  margin-right: 5px;
}

.question-blank {
  margin-bottom: 25px;
}

.blank-placeholder {
  display: inline-block;
  width: 100px;
  height: 30px;
  border-bottom: 2px solid #ccc;
  margin: 0 5px;
  text-align: center;
  line-height: 30px;
  color: #666;
}

.blank-form {
  margin-top: 20px;
}

.question-essay, .question-code {
  margin-bottom: 25px;
}

.question-answer {
  margin-bottom: 25px;
  padding: 15px;
  background-color: #f9fafb;
  border-radius: 6px;
}

.action-buttons {
  display: flex;
  gap: 10px;
  margin-top: 30px;
}

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
</style>
