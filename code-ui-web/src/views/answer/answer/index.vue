<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="90px">
      <el-form-item label="关联题目" prop="questionId">
        <el-select v-model="queryParams.questionId" placeholder="请选择标签" clearable style="width: 200px">
            <el-option
              v-for="title in titleList"
              :key="title.id"
              :label="title.name"
              :value="title.id"
            />
        </el-select>
      </el-form-item>
      <el-form-item label="答案类型" prop="answerType">
        <el-select v-model="queryParams.answerType" placeholder="请选择答案类型" clearable style="width: 200px">
          <el-option
            v-for="dict in sys_question_answertype"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="多选题编码" prop="optionCodes" >
        <el-input
          v-model="queryParams.optionCodes"
          placeholder="请输入多选题正确答案编码"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="答案权重" prop="scoreWeight">
        <el-input
          v-model="queryParams.scoreWeight"
          placeholder="请输入答案权重"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="答案排序" prop="sort">
        <el-input
          v-model="queryParams.sort"
          placeholder="请输入答案排序"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="编程语言" prop="language">
        <el-input
          v-model="queryParams.language"
          placeholder="请输入编程语言"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="版本号" prop="version">
        <el-input
          v-model="queryParams.version"
          placeholder="请输入版本号"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['answer:answer:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['answer:answer:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['answer:answer:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['answer:answer:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="answerList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="答案主键ID" align="center" prop="id" />
      <el-table-column label="关联题目" align="center" prop="question" />
      <el-table-column label="答案类型" align="center" prop="answerType">
        <template #default="scope">
          <dict-tag :options="sys_question_answertype" :value="scope.row.answerType"/>
        </template>
      </el-table-column>
      <el-table-column label="答案内容" align="center" prop="answerContent" />
      <el-table-column label="答案解析" align="center" prop="explanation" />
      <el-table-column label="多选题正确答案编码" align="center" prop="optionCodes" />
      <el-table-column label="答案权重" align="center" prop="scoreWeight" />
      <el-table-column label="答案排序" align="center" prop="sort" />
      <el-table-column label="编程语言" align="center" prop="language" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="版本号" align="center" prop="version" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['answer:answer:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['answer:answer:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改题库答案（支持多题型答案存储，题目删除需级联删除答案）对话框 -->
    <el-dialog :title="title" v-model="open" width="60%" append-to-body>
      <el-form ref="answerRef" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="关联题目" prop="questionId">
          <el-select v-model="form.questionId" placeholder="请选择标签" clearable style="width: 200px">
            <el-option
              v-for="title in titleList"
              :key="title.id"
              :label="title.name"
              :value="title.id"
            />
        </el-select>
        </el-form-item>
        <el-form-item label="答案类型" prop="answerType">
          <el-select v-model="form.answerType" placeholder="请选择答案类型">
            <el-option
              v-for="dict in sys_question_answertype"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="答案内容">
          <editor v-model="form.answerContent" :min-height="192"/>
        </el-form-item>
        <el-form-item label="答案解析">
          <editor v-model="form.explanation" :min-height="192"/>
        </el-form-item>
        <el-form-item label="多选题正确答案编码" prop="optionCodes">
          <el-input v-model="form.optionCodes" placeholder="请输入多选题正确答案编码" />
        </el-form-item>
        <el-form-item label="答案权重" prop="scoreWeight">
          <el-input v-model="form.scoreWeight" placeholder="请输入答案权重" />
        </el-form-item>
        <el-form-item label="答案排序" prop="sort">
          <el-input-number v-model="form.sort" controls-position="right" :min="0" />
        </el-form-item>
        <el-form-item label="编程语言" prop="language">
          <el-input v-model="form.language" placeholder="请输入编程语言" />
        </el-form-item>
        <el-form-item label="备注">
          <editor v-model="form.remark" :min-height="192"/>
        </el-form-item>
        <el-form-item label="版本号" prop="version">
          <el-input v-model="form.version" placeholder="请输入版本号" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Answer">
import { listAnswer, getAnswer, delAnswer, addAnswer, updateAnswer } from "@/api/answer/answer"
import { listManage} from "@/api/manage/manage"
import { ref, reactive, toRefs, getCurrentInstance} from 'vue'

const { proxy } = getCurrentInstance()
const { sys_question_answertype } = proxy.useDict('sys_question_answertype')

const answerList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")
const titleList = ref([])

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    questionId: null,
    answerType: null,
    answerContent: null,
    explanation: null,
    optionCodes: null,
    scoreWeight: null,
    sort: null,
    language: null,
    version: null
  },
  rules: {
    questionId: [
      { required: true, message: "关联题目不能为空", trigger: "blur" }
    ],
    answerType: [
      { required: true, message: "答案类型：1-标准答案 2-参考答案 3-多解法 4-评分要点不能为空", trigger: "change" }
    ],
    answerContent: [
      { required: true, message: "答案内容不能为空", trigger: "blur" }
    ],
    sort: [
      { required: true, message: "答案排序不能为空", trigger: "blur" }
    ],
    createBy: [
      { required: true, message: "创建者不能为空", trigger: "blur" }
    ],
    createTime: [
      { required: true, message: "创建时间不能为空", trigger: "blur" }
    ],
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询题库答案（支持多题型答案存储，题目删除需级联删除答案）列表 */
function getList() {
  loading.value = true
  listAnswer(queryParams.value).then(response => {
    answerList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}


/** 查询tagNameList 列表 */
function getTitleList() {
  loading.value = true;
  listManage(queryParams.value).then(response => {
    titleList.value = response.rows.map(item => ({
      id: item.id,
      name: item.title || item.name  // 根据实际返回数据结构调整
    }));
    loading.value = false;
  }).catch(error => {
    console.error('获取标签列表失败:', error);
    titleList.value = [];
    loading.value = false;
  });
}



// 取消按钮
function cancel() {
  open.value = false
  reset()
}

// 表单重置
function reset() {
  form.value = {
    id: null,
    questionId: null,
    answerType: null,
    answerContent: null,
    explanation: null,
    optionCodes: null,
    scoreWeight: null,
    sort: null,
    language: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null,
    remark: null,
    version: null
  }
  proxy.resetForm("answerRef")
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef")
  handleQuery()
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.id)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加题库答案（支持多题型答案存储，题目删除需级联删除答案）"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _id = row.id || ids.value
  getAnswer(_id).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改题库答案（支持多题型答案存储，题目删除需级联删除答案）"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["answerRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateAnswer(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addAnswer(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功")
          open.value = false
          getList()
        })
      }
    }
  })
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _ids = row.id || ids.value
  proxy.$modal.confirm('是否确认删除题库答案（支持多题型答案存储，题目删除需级联删除答案）编号为"' + _ids + '"的数据项？').then(function() {
    return delAnswer(_ids)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('answer/answer/export', {
    ...queryParams.value
  }, `answer_${new Date().getTime()}.xlsx`)
}

getList()
getTitleList()
</script>
