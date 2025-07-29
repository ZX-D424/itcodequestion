<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="所属模块" prop="moduleId">
        <el-select v-model="queryParams.moduleId" placeholder="请选择所属模块" style="width: 200px;" >
          <el-option
              v-for="module in moduleDataList"
              :key="module.id"
              :label="module.name"
              :value="module.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="关卡ID" prop="levelId">
        <el-input
          v-model="queryParams.levelId"
          placeholder="请输入关卡ID"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户ID" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入用户ID"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="批改人ID" prop="checkUserId">
        <el-input
          v-model="queryParams.checkUserId"
          placeholder="请输入批改人ID"
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
          v-hasPermi="['project:questions:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['project:questions:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['project:questions:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['project:questions:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="questionsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
<!--      <el-table-column label="ID" align="center" prop="id" />-->
      <el-table-column label="模块名称" align="center" prop="moduleName" />
      <el-table-column label="序号" align="center" prop="levelCode" >
        <template #default="scope">
          <el-tag> 第 {{ scope.row.levelCode }} 关</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="关卡标题" align="center" prop="levelName" show-overflow-tooltip/>
      <el-table-column label="用户昵称" align="center" prop="userName" />
      <el-table-column label="代码截图" align="center" prop="imgUrl" width="100">
        <template #default="scope">
          <image-preview :src="scope.row.imgUrl" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="内容" align="center" prop="mark" show-overflow-tooltip />
<!--      <el-table-column label="附件url" align="center" prop="fileUrl" />-->
<!--      <el-table-column label="提交时间" align="center" prop="submitTime" width="180">-->
<!--        <template #default="scope">-->
<!--          <span>{{ parseTime(scope.row.submitTime, '{y}-{m}-{d}') }}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column label="答题开始时间" align="center" prop="startTime" width="180">-->
<!--        <template #default="scope">-->
<!--          <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d}') }}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column label="批改人" align="center" prop="checkUserName" />
      <el-table-column label="批改时间" align="center" prop="checkTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.checkTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="评分" align="center" prop="score" />
      <el-table-column label="评分备注" align="center" prop="checkMark" show-overflow-tooltip />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="200px">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['project:questions:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['project:questions:remove']">删除</el-button>
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

    <!-- 添加或修改答题评分对话框 -->
    <el-dialog :title="title" v-model="open" width="60%" append-to-body>

      <el-form ref="questionsRef" :model="form" :rules="rules" label-width="80px">

      <div style="margin-left: 20px" >
        <table >
          <tr><td class="td-list">关卡序号：</td><td> 第 {{ form.levelCode }} 关</td></tr>
          <tr><td class="td-list">关卡标题：</td><td>  {{ form.levelName }} </td></tr>
          <tr><td class="td-list">关卡内容：</td><td>  <p  style="border: #dadada solid 1px;padding: 0px;margin-left: 0px;border-radius: 5px;" v-html="form.levelInfo"></p> </td></tr>
          <tr><td class="td-list">效果图：</td><td>  <image-preview :src="url+form.imgUrl" :width="100" :height="80"/></td></tr>
          <tr><td class="td-list">代码截图：</td><td>  <image-preview :src="url+form.imgUrl" :width="100" :height="80"/></td></tr>
          <tr><td class="td-list">代码内容：</td><td> <p  style="border: #dadada solid 1px;padding: 0px;margin-left: 0px;border-radius: 5px;"> {{ form.mark }}  </p> </td></tr>
          <tr v-if="form.fileUrl"><td class="td-list">代码附件：</td><td>  {{ form.fileUrl }} </td></tr>
          <tr><td class="td-list">提交时间：</td><td> {{ form.submitTime }} </td></tr>
          <tr><td class="td-list">提交人：</td><td>  {{ form.userName }} </td></tr>
        </table>
      </div>
        <el-form-item label="评分" prop="score">
          <el-input-number min="0" max="10" v-model="form.score" placeholder="请输入评分" />
        </el-form-item>
        <el-form-item label="评分备注" prop="checkMark">
          <textarea v-model="form.checkMark" :min-height="192" style="width: 60%;height: 150px;"/>
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

<script setup name="Questions">
import { listQuestions, getQuestions, delQuestions, addQuestions, updateQuestions } from "@/api/project/questions"
import { getModuleDataList } from "@/api/project/module.js"


const { proxy } = getCurrentInstance()

const questionsList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")
const moduleDataList = ref([])
const moduleTypeParams = ref(2);

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    moduleId: null,
    levelId: null,
    userId: null,
    checkUserId: null,
  },
  rules: {
    checkMark: [
      { required: true, message: "批改备注不能为空", trigger: "blur" }
    ],
    score: [
      { required: true, message: "评分不能为空", trigger: "blur" }
    ],
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询答题评分列表 */
function getList() {
  loading.value = true
  listQuestions(queryParams.value).then(response => {
    questionsList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

// 取消按钮
function cancel() {
  open.value = false
  reset()
}
/** 查询基础闯关模块列表 */
function initModuleDataList() {
  loading.value = true
  getModuleDataList(moduleTypeParams.value).then(response => {
    moduleDataList.value = response.data
    loading.value = false
  })
}

// 表单重置
function reset() {
  form.value = {
    id: null,
    moduleId: null,
    levelId: null,
    userId: null,
    imgUrl: null,
    mark: null,
    fileUrl: null,
    submitTime: null,
    startTime: null,
    checkUserId: null,
    checkTime: null,
    score: null,
    checkMark: null
  }
  proxy.resetForm("questionsRef")
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
  title.value = "添加答题评分"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _id = row.id || ids.value
  getQuestions(_id).then(response => {
    form.value = response.data
    open.value = true
    title.value = "批改答题"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["questionsRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateQuestions(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addQuestions(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除答题评分编号为"' + _ids + '"的数据项？').then(function() {
    return delQuestions(_ids)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('project/questions/export', {
    ...queryParams.value
  }, `questions_${new Date().getTime()}.xlsx`)
}

getList();
initModuleDataList();
</script>
<style scoped>
.td-list{
  font-weight: bold;

}
tr{
  margin-bottom: 50px;
}
</style>
